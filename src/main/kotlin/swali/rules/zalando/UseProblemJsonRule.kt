package swali.rules.zalando

import io.swagger.models.*
import io.swagger.models.properties.ObjectProperty
import io.swagger.models.properties.RefProperty
import swali.*

class UseProblemJsonRule : Rule {
    val title = "Use Problem JSON"
    val violationType = ViolationType.MUST
    override val id = "176"
    private val description = "Operations Should Return Problem JSON When Any Problem Occurs During Processing " +
        "Whether Caused by Client Or Server"
    private val requiredFields = setOf("title", "status")

    override fun validate(swagger: Swagger): Violation? {
        val paths = swagger.paths.orEmpty().flatMap { pathEntry ->
            pathEntry.value.operationMap.orEmpty().filter { it.key.shouldContainPayload() }.flatMap { opEntry ->
                opEntry.value.responses.orEmpty().flatMap { responseEntry ->
                    val httpCode = responseEntry.key.toIntOrNull()
                    if (httpCode in 400..599 && !isValidProblemJson(swagger, responseEntry.value, opEntry.value)) {
                        listOf("${pathEntry.key} ${opEntry.key} ${responseEntry.key}")
                    } else emptyList()
                }
            }
        }

        return if (paths.isNotEmpty()) Violation(title, description, violationType, id, paths) else null
    }

    private fun isValidProblemJson(swagger: Swagger, response: Response, operation: Operation) =
        isProblemJson(swagger, response) && producesJson(swagger, operation)

    private fun isProblemJson(swagger: Swagger, response: Response): Boolean {
        val schema = response.schema
        val properties = when (schema) {
            is RefProperty -> getProperties(swagger, swagger.definitions?.get((response.schema as RefProperty).simpleRef))
            is ObjectProperty -> schema.properties?.keys.orEmpty()
            else -> emptySet<String>()
        }
        return properties.containsAll(requiredFields)
    }

    private fun getProperties(swagger: Swagger, definition: Model?): Set<String> = when (definition) {
        is ComposedModel -> definition.allOf.orEmpty().flatMap { getProperties(swagger, it) }.toSet()
        is RefModel -> getProperties(swagger, swagger.definitions[definition.simpleRef])
        else -> definition?.properties?.keys.orEmpty()
    }

    private fun producesJson(swagger: Swagger, operation: Operation) =
        if (operation.produces.orEmpty().isEmpty()) {
            swagger.produces.orEmpty().containsJson()
        } else {
            operation.produces.containsJson()
        }

    // support for application/json also with set charset e.g. "application/json; charset=utf-8"
    private fun List<String>.containsJson() = any { it.startsWith("application/json") }

    private fun HttpMethod.shouldContainPayload(): Boolean = name.toLowerCase() !in listOf("head", "options")
}
