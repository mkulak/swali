package swali.zalando

import de.zalando.zally.util.getAllJsonObjects
import io.swagger.models.Swagger
import swali.*
import swali.utils.PatternUtil


class SnakeCaseInPropNameRule(val whitelist: Set<String>) : Rule {
    val title = "snake_case property names"
    val violationType = ViolationType.MUST
    override val id = "118"
    private val description = "Property names must be snake_case: "

    override fun validate(swagger: Swagger): Violation? {
        val result = swagger.getAllJsonObjects().flatMap { (def, path) ->
            val badProps = def.keys.filterNot { PatternUtil.isSnakeCase(it) || it in whitelist }
            if (badProps.isNotEmpty()) listOf(badProps to path) else emptyList()
        }
        return if (result.isNotEmpty()) {
            val (props, paths) = result.unzip()
            val properties = props.flatten().toSet().joinToString(", ")
            Violation(title, description + properties, violationType, id, paths)
        } else null
    }
}
