package swali

import com.amazonaws.services.lambda.runtime.Context
import com.amazonaws.services.lambda.runtime.RequestHandler
import com.fasterxml.jackson.databind.*
import com.fasterxml.jackson.module.kotlin.KotlinModule
import com.fasterxml.jackson.module.kotlin.readValue
import java.net.HttpURLConnection
import java.net.URL
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent as ApiRequest
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent as ApiResponse


class Main : RequestHandler<ApiRequest, ApiResponse> {
    override fun handleRequest(request: ApiRequest?, context: Context): ApiResponse =
        handle(request, linter, context.logger::log)
}

val linter = LinterImpl(allRules)

val mapper = ObjectMapper().registerModule(KotlinModule())
    .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
    .configure(SerializationFeature.WRITE_DATE_TIMESTAMPS_AS_NANOSECONDS, false)
    .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
    .setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE)

fun handle(request: ApiRequest?, linter: Linter, log: (String) -> Unit): ApiResponse {
    log("received: $request")
    return try {
        val lintingRequest = readBody(request)
        val apiDefinition = getApiDefinition(lintingRequest)
        val ignoreRules = lintingRequest.ignoreRules.orEmpty().toSet()
        val response = linter.doLint(apiDefinition, ignoreRules)
        ApiResponse().apply {
            statusCode = 200
            headers = mapOf("Content-Type" to "application/json")
            body = mapper.writeValueAsString(response)
        }
    } catch (e: Exception) {
        log("WARN: bad request ${request?.body} | exception: $e")
        val (code, message) = if (e is BadRequestException) 400 to e.message else 500 to "internal server error"
        ApiResponse().apply {
            statusCode = code
            headers = mapOf("Content-Type" to "application/json")
            body = mapper.writeValueAsString(mapOf("status" to 400, "title" to message))
        }
    }
}

fun getApiDefinition(req: LintingRequest) =
    when {
        req.apiDefinition != null -> req.apiDefinition
        req.apiDefinitionUrl != null -> downloadFile(URL(req.apiDefinitionUrl))
        else -> throw BadRequestException("Both api_definition and api_definition_url are absent")
    }

fun readBody(request: ApiRequest?): LintingRequest =
    try {
        mapper.readValue(request?.body.orEmpty())
    } catch (e: Exception) {
        throw BadRequestException("Can't parse request body")
    }


fun downloadFile(url: URL): String =
    try {
        val conn = url.openConnection() as HttpURLConnection
        conn.requestMethod = "GET"
        if (conn.responseCode != 200) {
            throw RuntimeException()
        }
        conn.inputStream.bufferedReader().readText()
    } catch (e: Exception) {
        throw BadRequestException("Failed to download API definition from $url")
    }


class BadRequestException(message: String) : RuntimeException(message)
