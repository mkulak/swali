package me.mkulak

import com.amazonaws.services.lambda.runtime.Context
import com.amazonaws.services.lambda.runtime.RequestHandler
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent
import java.util.Date

class Handler : RequestHandler<APIGatewayProxyRequestEvent?, APIGatewayProxyResponseEvent> {
    override fun handleRequest(input: APIGatewayProxyRequestEvent?, context: Context): APIGatewayProxyResponseEvent {
        val responseBody = "Kotlin says: current time is ${Date()}"
        return APIGatewayProxyResponseEvent().apply {
            statusCode = 200
            body = responseBody
        }
    }
}
