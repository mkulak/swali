package swali.rules.zalando

import io.swagger.models.Swagger
import io.swagger.models.parameters.QueryParameter
import swali.*
import swali.utils.PatternUtil

class SnakeCaseForQueryParamsRule : Rule {
    override val title = "Use snake_case (never camelCase) for Query Parameters"
    override val violationType = ViolationType.MUST
    override val id = "130"

    override fun validate(swagger: Swagger): Violation? {
        val result = swagger.paths.orEmpty().flatMap { (path, pathObject) ->
            pathObject.operationMap.orEmpty().flatMap { (verb, operation) ->
                val badParams =
                    operation.parameters.filter { it is QueryParameter && !PatternUtil.isSnakeCase(it.name) }
                if (badParams.isNotEmpty()) listOf("$path $verb" to badParams) else emptyList()
            }
        }
        return if (result.isNotEmpty()) {
            val (paths, params) = result.unzip()
            val description =
                "Parameters that are not in snake_case: " + params.flatten().map { it.name }.toSet().joinToString(",")
            Violation(title, description, violationType, id, paths)
        } else null
    }
}
