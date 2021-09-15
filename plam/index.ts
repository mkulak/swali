import * as aws from "@pulumi/aws"
import * as awsx from "@pulumi/awsx"
import * as pulumi from "@pulumi/pulumi"
import {Runtime} from "@pulumi/aws/lambda";
import {FileArchive} from "@pulumi/pulumi/asset/archive";

const image = awsx.ecr.buildAndPushImage("sampleapp", {
    context: "./app",
})
const role = new aws.iam.Role("lambdaRole", {
    assumeRolePolicy: aws.iam.assumeRolePolicyForPrincipal({ Service: "lambda.amazonaws.com" }),
})
new aws.iam.RolePolicyAttachment("lambdaFullAccess", {
    role: role.name,
    policyArn: aws.iam.ManagedPolicy.LambdaFullAccess,
})

const jsFun = new aws.lambda.Function("helloworld", {
    packageType: "Image",
    imageUri: image.imageValue,
    role: role.arn,
    timeout: 60,
})

const goFun = new aws.lambda.Function("gohello", {
    runtime: Runtime.Go1dx,
    handler: "handler",
    code: new FileArchive("./go/handler.zip"),
    role: role.arn,
    timeout: 60,
})

const jvmFun = new aws.lambda.Function("javahello", {
    runtime: Runtime.Java11,
    handler: "me.mkulak.Handler",
    code: new FileArchive("./jvm//build/distributions/aws-java-simple-http-endpoint-new.zip"),
    role: role.arn,
    timeout: 60,
})


const endpoint = new awsx.apigateway.API("hello", {
    routes: [
        // Serve static files from the `www` folder (using AWS S3)
        {
            path: "/site",
            localPath: "www",
        },

        // Serve a simple REST API on `GET /name` (using AWS Lambda)
        {
            path: "/source",
            method: "GET",
            eventHandler: (req, ctx, cb) => {
                cb(undefined, {
                    statusCode: 200,
                    body: Buffer.from(JSON.stringify({ name: "AWS" }), "utf8").toString("base64"),
                    isBase64Encoded: true,
                    headers: { "content-type": "application/json" },
                })
            }
        },

        {
            path: "/go",
            method: "GET",
            eventHandler: goFun,
        },
        {
            path: "/jvm",
            method: "GET",
            eventHandler: jvmFun,
        },
        {
            path: "/{route+}",
            method: "GET",
            eventHandler: jsFun,
        }
    ]
})

export const url = endpoint.url
