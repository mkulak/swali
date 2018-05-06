package swali

import com.amazonaws.services.lambda.runtime.RequestHandler
import com.amazonaws.services.lambda.runtime.Context
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent


class Main : RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {

    override fun handleRequest(request: APIGatewayProxyRequestEvent?, context: Context): APIGatewayProxyResponseEvent {
        context.logger.log("received : $request")
        return APIGatewayProxyResponseEvent().apply {
            statusCode = 200
            body = "Kak bi response, foo=" + (request?.queryStringParameters?.get("foo"))
        }
    }
}

fun main(args: Array<String>) {
    println("koo!")
}
