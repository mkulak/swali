package swali

import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent
import com.fasterxml.jackson.module.kotlin.readValue
import java.net.HttpURLConnection
import java.net.URL


class HttpApi(val linter: Linter) {
    fun route(request: APIGatewayProxyRequestEvent?, log: (String) -> Unit): APIGatewayProxyResponseEvent {
        log("received: $request")
        return when (request?.path) {
            "/violations" -> serveViolations(request, linter, log)
            "/supported-rules" -> serveSupportedRules(linter.rules)
            "/api.yaml" -> serveApiDef()
            else -> APIGatewayProxyResponseEvent().apply {
                statusCode = 404
                headers = mapOf("Content-Type" to "application/json")
                body = mapper.writeValueAsString(mapOf("status" to 400, "title" to "Path ${request?.path} not found"))
            }
        }
    }

    private fun serveViolations(
        request: APIGatewayProxyRequestEvent?,
        linter: Linter,
        log: (String) -> Unit
    ): APIGatewayProxyResponseEvent =
        try {
            val lintingRequest = readBody(request)
            val apiDefinition = getApiDefinition(lintingRequest)
            val ignoreRules = lintingRequest.ignoreRules.orEmpty().toSet()
            val response = linter.doLint(apiDefinition, ignoreRules)
            APIGatewayProxyResponseEvent().apply {
                statusCode = 200
                headers = mapOf(
                    "Content-Type" to "application/json",
                    "Access-Control-Allow-Origin" to "*",
                    "Cache-Control" to "no-cache"
                )
                body = mapper.writeValueAsString(response)
            }
        } catch (e: Exception) {
            log("WARN: bad request ${request?.body} | exception: $e")
            val (code, message) = if (e is BadRequestException) 400 to e.message else 500 to "internal server error"
            APIGatewayProxyResponseEvent().apply {
                statusCode = code
                headers = mapOf("Content-Type" to "application/json")
                body = mapper.writeValueAsString(mapOf("status" to 400, "title" to message))
            }
        }

    private fun getApiDefinition(req: LintingRequest) =
        when {
            req.apiDefinition != null -> req.apiDefinition
            req.apiDefinitionUrl != null -> downloadFile(URL(req.apiDefinitionUrl))
            else -> throw BadRequestException("Both api_definition and api_definition_url are absent")
        }

    private fun readBody(request: APIGatewayProxyRequestEvent?): LintingRequest =
        try {
            mapper.readValue(request?.body.orEmpty())
        } catch (e: Exception) {
            throw BadRequestException("Can't parse request body")
        }


    private fun downloadFile(url: URL): String =
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


    private fun serveSupportedRules(rules: List<Rule>): APIGatewayProxyResponseEvent {
        val rulesDto = rules.map { RuleDto(it.title, it.id, it.violationType, ruleLink(it.id)) }
        return APIGatewayProxyResponseEvent().apply {
            statusCode = 200
            headers = mapOf("Content-Type" to "application/json")
            body = mapper.writeValueAsString(mapOf("supported_rules" to rulesDto))
        }
    }

    private fun serveApiDef(): APIGatewayProxyResponseEvent =
        APIGatewayProxyResponseEvent().apply {
            statusCode = 200
            headers = mapOf("Content-Type" to "text/yaml")
            body = AppConfig::class.java.getResourceAsStream("api.yaml").bufferedReader().readText()
        }

    class BadRequestException(message: String) : RuntimeException(message)
}