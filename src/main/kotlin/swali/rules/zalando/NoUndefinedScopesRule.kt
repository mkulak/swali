
package swali.rules.zalando

import io.swagger.models.Swagger
import swali.*
import swali.utils.extractAppliedScopes
import swali.utils.getDefinedScopes


class NoUndefinedScopesRule : Rule {
    override val id = "105"
    val title = "Must define and Assign Access Rights (Scopes)"
    val DESC = "Undefined scopes found: "

    override fun validate(swagger: Swagger): Violation? {
        val definedScopes = swagger.getDefinedScopes()
        val pathsAndScopes = swagger.paths.orEmpty().entries.flatMap { (pathKey, path) ->
            path.operationMap.orEmpty().entries.mapNotNull { (method, operation) ->
                val actualScopes = extractAppliedScopes(operation)
                val undefinedScopes = actualScopes - definedScopes
                if (undefinedScopes.isNotEmpty()) {
                    "$pathKey $method" to undefinedScopes.map { it.second }
                } else null
            }
        }
        val (paths, scopes) = pathsAndScopes.unzip()
        return if (!paths.isEmpty()) {
            Violation(title, DESC + scopes.toSet().joinToString(), ViolationType.MUST, id, paths)
        } else null
    }
}
