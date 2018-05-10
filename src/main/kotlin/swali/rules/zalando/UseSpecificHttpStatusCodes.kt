package swali.rules.zalando

import io.swagger.models.*
import swali.*

class UseSpecificHttpStatusCodes(val allowedStatuses: Map<String, Set<Int>>) : Rule {
    val title = "Use Specific HTTP Status Codes"

    // as a quick fix this rule is only SHOULD (normally MUST), see https://github.com/zalando-incubator/zally/issues/374
    val violationType = ViolationType.SHOULD
    override val id = "150"
    private val description = "Operatons should use specific HTTP status codes"

    override fun validate(swagger: Swagger): Violation? {
        val badPaths = swagger.paths.orEmpty().flatMap { path ->
            path.value.operationMap.orEmpty().flatMap { getNotAllowedStatusCodes(path.key, it) }
        }
        return if (badPaths.isNotEmpty()) Violation(title, description, violationType, id, badPaths) else null
    }

    private fun getNotAllowedStatusCodes(path: String, entry: Map.Entry<HttpMethod, Operation>): List<String> {
        val statusCodes = entry.value.responses.orEmpty().keys.toList()
        val allowedCodes = getAllowedStatusCodes(entry.key)
        val notAllowedCodes = statusCodes.filter { (it.toIntOrNull() ?: 0) !in allowedCodes }
        return notAllowedCodes.map { "$path ${entry.key.name} $it" }
    }

    private fun getAllowedStatusCodes(httpMethod: HttpMethod): Set<Int> =
        allowedStatuses[httpMethod.name.toLowerCase()].orEmpty() + allowedStatuses["all"].orEmpty()
}
