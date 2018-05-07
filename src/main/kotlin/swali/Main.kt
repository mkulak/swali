package swali

import com.amazonaws.services.lambda.runtime.Context
import com.amazonaws.services.lambda.runtime.RequestHandler
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent
import com.fasterxml.jackson.databind.*
import com.fasterxml.jackson.module.kotlin.KotlinModule
import com.fasterxml.jackson.module.kotlin.readValue

class Main : RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {
    val apiLinter = Linter()

    val mapper = ObjectMapper().registerModule(KotlinModule())
        .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
        .configure(SerializationFeature.WRITE_DATE_TIMESTAMPS_AS_NANOSECONDS, false)
        .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
        .setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE)

    override fun handleRequest(request: APIGatewayProxyRequestEvent?, context: Context): APIGatewayProxyResponseEvent {
        context.logger.log("received: $request")
        return try {
            val lintingRequest = mapper.readValue<LintingRequest>(request?.body.orEmpty())
            val response = apiLinter.doLint(lintingRequest)
            APIGatewayProxyResponseEvent().apply {
                statusCode = 200
                headers = mapOf("Content-Type" to "application/json")
                body = mapper.writeValueAsString(response)
            }
        } catch (e: Exception) {
            context.logger.log("WARN: bad request ${request?.body}")
            APIGatewayProxyResponseEvent().apply {
                statusCode = 400
                headers = mapOf("Content-Type" to "application/json")
                body = mapper.writeValueAsString(mapOf("status" to 400, "title" to "Can't parse request body"))
            }
        }
    }
}

fun main(args: Array<String>) {
    println("koo!")
}

