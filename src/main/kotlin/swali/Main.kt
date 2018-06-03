package swali

import com.amazonaws.services.lambda.runtime.Context
import com.amazonaws.services.lambda.runtime.RequestHandler
import com.fasterxml.jackson.databind.*
import com.fasterxml.jackson.module.kotlin.KotlinModule
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent as ApiRequest
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent as ApiResponse


class Main : RequestHandler<ApiRequest, ApiResponse> {
    companion object {
        val httpApi = HttpApi(LinterImpl(allRules))
    }

    override fun handleRequest(request: ApiRequest?, context: Context): ApiResponse =
        httpApi.route(request, context.logger::log)
}

val mapper = ObjectMapper().registerModule(KotlinModule())
    .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
    .configure(SerializationFeature.WRITE_DATE_TIMESTAMPS_AS_NANOSECONDS, false)
    .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
    .setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE)


