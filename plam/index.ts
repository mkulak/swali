import * as aws from "@pulumi/aws"
import * as awsx from "@pulumi/awsx"
import * as pulumi from "@pulumi/pulumi"
import {Runtime} from "@pulumi/aws/lambda";
import {FileArchive} from "@pulumi/pulumi/asset/archive";

const role = new aws.iam.Role("lambdaRole", {
    assumeRolePolicy: aws.iam.assumeRolePolicyForPrincipal({ Service: "lambda.amazonaws.com" }),
})
new aws.iam.RolePolicyAttachment("lambdaFullAccess", {
    role: role.name,
    policyArn: aws.iam.ManagedPolicy.LambdaFullAccess,
})

// const image = awsx.ecr.buildAndPushImage("sampleapp", {
//     context: "./app",
// })

// const jsFun = new aws.lambda.Function("helloworld", {
//     packageType: "Image",
//     imageUri: image.imageValue,
//     role: role.arn,
//     timeout: 60,
// })

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
    code: new FileArchive("./jvm/build/distributions/kt-lambda.zip"),
    role: role.arn,
    timeout: 60,
})


const endpoint = new awsx.apigateway.API("hello", {
    routes: [
        {
            path: "/site",
            localPath: "www",
        },
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
            path: "/files/{filekey+}",
            method: "ANY",
            eventHandler: jvmFun,
        },
        // {
        //     path: "/{route+}",
        //     method: "GET",
        //     eventHandler: jsFun,
        // }
    ]
})

const bucket = new aws.s3.Bucket("plum-files", {
    bucket: "plum-files",
    acl: "private"
});

const lambdaS3Policy = new aws.iam.Policy(`post-to-s3-policy`, {
    description: "IAM policy for Lambda to interact with S3",
    path: "/",
    policy: bucket.arn.apply(bucketArn => `{
  "Version": "2012-10-17",
  "Statement": [
    {
      "Action": "s3:PutObject", 
      "Resource": "${bucketArn}/*",
      "Effect": "Allow"
    }
  ]}`)
})

// Attach the policies to the Lambda role
new aws.iam.RolePolicyAttachment(`post-to-s3-policy-attachment`, {
    policyArn: lambdaS3Policy.arn,
    role: role.name
})

export const bucketArn = bucket.arn;
export const url = endpoint.url
