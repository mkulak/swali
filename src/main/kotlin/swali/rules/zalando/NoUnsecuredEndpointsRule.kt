package swali.rules.zalando

import io.swagger.models.Swagger
import swali.*
import swali.utils.*


class NoUnsecuredEndpointsRule : Rule {
    override val id = "105"
    val title = "Secure Endpoints with OAuth 2.0"

    override fun validate(swagger: Swagger): Violation? {
        val definedScopes = swagger.getDefinedScopes()
        val hasTopLevelScope = swagger.hasTopLevelScope(definedScopes)
        val paths = if (!hasTopLevelScope) {
            swagger.paths.orEmpty().entries.flatMap { (pathKey, path) ->
                path.operationMap.orEmpty().entries.mapNotNull { (method, operation) ->
                    val actualScopes = extractAppliedScopes(operation)
                    val undefinedScopes = actualScopes - definedScopes
                    val unsecured = undefinedScopes.size == actualScopes.size
                    if (unsecured) "$pathKey $method" else null
                }
            }
        } else emptyList()
        return if (!paths.isEmpty()) {
            Violation(title, "Unsecured endpoints found", ViolationType.MUST, id, paths)
        } else null
    }
}
