package me.mkulak

import com.amazonaws.services.lambda.runtime.Context
import com.amazonaws.services.lambda.runtime.RequestHandler
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent
import software.amazon.awssdk.regions.Region
import software.amazon.awssdk.services.s3.model.GetObjectRequest
//import com.amazonaws.services.s3.model.PutObjectRequest
import software.amazon.awssdk.services.s3.model.PutObjectRequest
import software.amazon.awssdk.services.s3.presigner.S3Presigner
import software.amazon.awssdk.services.s3.presigner.model.GetObjectPresignRequest
import software.amazon.awssdk.services.s3.presigner.model.PutObjectPresignRequest
import java.io.IOException
import java.net.URL
import java.time.Duration
import java.util.Date

val bucketName = "arn:aws:s3:::plum-files"
val region = "eu-west-1"

class Handler : RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {
    val presigner = S3Presigner.builder().region(Region.of(region)).build()

    override fun handleRequest(input: APIGatewayProxyRequestEvent, context: Context): APIGatewayProxyResponseEvent {
        val (code, requestBody) =
            try {
                when {
                    !input.path.startsWith("/files/") -> 200 to getTime(input)
                    input.httpMethod == "POST" -> 200 to getUploadUrl(input.path.removePrefix("/files/"))
                    input.httpMethod == "GET" -> 200 to getDownloadUrl(input.path.removePrefix("/files/"))
                    else -> 404 to "Unknown req: path: '${input.path}' method: ${input.httpMethod}"
                }
            } catch (e: Throwable) {
                500 to "Kt internal server error: ${e.stackTraceToString()}"
            }
        return APIGatewayProxyResponseEvent().apply {
            statusCode = code
            body = requestBody
        }
    }

    private fun getTime(input: APIGatewayProxyRequestEvent): String =
        "GET ${input.path} Kotlin says: current time is ${Date()}"

    fun getDownloadUrl(keyName: String): String {
        val req = GetObjectRequest.builder().bucket(bucketName).key(keyName).build()
        val presignRequest = GetObjectPresignRequest.builder()
            .signatureDuration(Duration.ofDays(100))
            .getObjectRequest(req)
            .build()

        val presignedRequest = presigner.presignGetObject(presignRequest)
        return presignedRequest.url().toString()
    }

    fun getUploadUrl(keyName: String): String {
        val objectRequest = PutObjectRequest.builder()
            .bucket(bucketName)
            .key(keyName)
            .contentType("text/plain")
            .build()

        val presignRequest = PutObjectPresignRequest.builder()
            .signatureDuration(Duration.ofMinutes(10))
            .putObjectRequest(objectRequest)
            .build()

        val presignedRequest = presigner.presignPutObject(presignRequest)

        val myURL = presignedRequest.url()

        println("Presigned URL to upload a file to: $myURL")
        println("Which HTTP method needed when uploading a file: ${presignedRequest.httpRequest().method()}")
        return myURL.toString()
    }
}


//val url = presignedRequest.url();
//
//// Create the connection and use it to upload the new object by using the presigned URL
//val connection =  url.openConnection() as HttpURLConnection;
//connection.setDoOutput(true);
//connection.setRequestProperty("Content-Type","text/plain");
//connection.setRequestMethod("PUT");
//OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream());
//out.write("This text was uploaded as an object by using a presigned URL.");
//out.close();
//
//connection.getResponseCode();
//System.out.println("HTTP response code is " + connection.getResponseCode());
//} catch (e: S3Exception) {
// e.getStackTrace()
//} catch (e: IOException) {
// e.getStackTrace();
//}
