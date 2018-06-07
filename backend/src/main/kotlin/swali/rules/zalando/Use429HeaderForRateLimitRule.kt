package swali.rules.zalando

import io.swagger.models.Swagger
import io.swagger.models.properties.Property
import swali.*

class Use429HeaderForRateLimitRule : Rule {
    override val title = "Use 429 With Header For Rate Limits"
    override val violationType = ViolationType.MUST
    override val id = "153"
    val desc = "If Client Exceed Request Rate, Response Code Must Contain Header Information Providing Further Details to Client"
    val rateLimitHeaders = listOf("X-RateLimit-Limit", "X-RateLimit-Remaining", "X-RateLimit-Reset")

    override fun validate(swagger: Swagger): Violation? {
        val paths = swagger.paths.orEmpty().flatMap { (path, pathObj) ->
            pathObj.operationMap.orEmpty().entries.flatMap { (verb, operation) ->
                operation.responses.orEmpty().flatMap { (code, response) ->
                    if (code == "429" && !containsRateLimitHeader(response.headers.orEmpty()))
                        listOf("$path $verb $code")
                    else emptyList()
                }
            }
        }
        return if (paths.isNotEmpty()) Violation(title, desc, violationType, id, paths) else null
    }

    private fun containsRateLimitHeader(headers: Map<String, Property>): Boolean =
        headers.containsKey("Retry-After") || headers.keys.containsAll(rateLimitHeaders)
}
