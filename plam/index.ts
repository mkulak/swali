import * as aws from "@pulumi/aws"
import * as awsx from "@pulumi/awsx"
import * as pulumi from "@pulumi/pulumi"

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

const func = new aws.lambda.Function("helloworld", {
    packageType: "Image",
    imageUri: image.imageValue,
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
            path: "/{route+}",
            method: "GET",
            eventHandler: func,
        }
    ]
})

export const url = endpoint.url
export const helloWorldUrl = pulumi.interpolate`${endpoint.url}World`
