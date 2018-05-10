package swali

import com.amazonaws.services.lambda.runtime.Context
import com.amazonaws.services.lambda.runtime.RequestHandler
import com.fasterxml.jackson.databind.*
import com.fasterxml.jackson.module.kotlin.KotlinModule
import com.fasterxml.jackson.module.kotlin.readValue
import swali.Main.Companion.linter
import swali.Main.Companion.mapper
import java.io.File
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent as ApiRequest
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent as ApiResponse


class Main : RequestHandler<ApiRequest, ApiResponse> {
    companion object {
        val linter = Linter(allRules)

        val mapper = ObjectMapper().registerModule(KotlinModule())
            .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
            .configure(SerializationFeature.WRITE_DATE_TIMESTAMPS_AS_NANOSECONDS, false)
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
            .setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE)
    }

    override fun handleRequest(request: ApiRequest?, context: Context): ApiResponse =
        handle(request, linter, context.logger::log)
}

fun handle(request: ApiRequest?, linter: Linter, log: (String) -> Unit): ApiResponse {
    log("received: $request")
    return try {
        val lintingRequest = mapper.readValue<LintingRequest>(request?.body.orEmpty())
        val response = linter.doLint(lintingRequest)
        ApiResponse().apply {
            statusCode = 200
            headers = mapOf("Content-Type" to "application/json")
            body = mapper.writeValueAsString(response)
        }
    } catch (e: Exception) {
        log("WARN: bad request ${request?.body}")
        ApiResponse().apply {
            statusCode = 400
            headers = mapOf("Content-Type" to "application/json")
            body = mapper.writeValueAsString(mapOf("status" to 400, "title" to "Can't parse request body"))
        }
    }
}

fun main(args: Array<String>) {
    if (args.isEmpty()) {
        println("Specify path to swagger file as first argument")
        return
    }
    val request = ApiRequest().apply { body = File(args.first()).readText() }
    val response = handle(request, linter, {})
    println(response.body)
}

