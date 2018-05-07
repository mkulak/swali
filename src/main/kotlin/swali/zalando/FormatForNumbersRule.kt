package swali.zalando

import de.zalando.zally.util.getAllJsonObjects
import io.swagger.models.Swagger
import io.swagger.models.parameters.AbstractSerializableParameter
import io.swagger.models.parameters.Parameter
import swali.*

class FormatForNumbersRule(val numberFormats: Map<String, List<String>>) : Rule {
    val title = "Define Format for Type Number and Integer"
    val violationType = ViolationType.MUST
    override val id = "171"
    private val description = """Numeric properties must have valid format specified: """


    override fun validate(swagger: Swagger): Violation? {
        val fromObjects = swagger.getAllJsonObjects().flatMap { (def, path) ->
            val badProps = def.entries.filterNot { (_, prop) -> isValid(prop.type, prop.format) }.map { it.key }
            if (badProps.isNotEmpty()) listOf(badProps to path) else emptyList()
        }
        val fromParams = swagger.parameters.orEmpty().entries.flatMap { (name, param) ->
            if (!param.hasValidFormat()) listOf(listOf(name) to "#/parameters/$name") else emptyList()
        }
        val fromPathParams = swagger.paths.orEmpty().entries.flatMap { (name, path) ->
            path.operations.orEmpty().flatMap { operation ->
                val badParams = operation.parameters.orEmpty().filterNot { it.hasValidFormat() }.map { it.name }
                if (badParams.isNotEmpty()) listOf(badParams to name) else emptyList()
            }
        }
        val result = fromObjects + fromParams + fromPathParams
        return if (result.isNotEmpty()) {
            val (props, paths) = result.unzip()
            val properties = props.flatten().toSet().joinToString(", ")
            Violation(title, description + properties, violationType, id, paths)
        } else null
    }

    private fun Parameter.hasValidFormat(): Boolean =
            this !is AbstractSerializableParameter<*> || isValid(getType(), getFormat())

    private fun isValid(type: String?, format: String?): Boolean = numberFormats[type]?.let { format in it } ?: true
}