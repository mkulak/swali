package swali.rules.zalando

import io.swagger.models.Swagger
import swali.*
import swali.utils.PatternUtil
import swali.utils.getAllJsonObjects


class SnakeCaseInPropNameRule(val whitelist: Set<String>) : Rule {
    override val title = "snake_case property names"
    override val violationType = ViolationType.MUST
    override val id = "118"
    val desc = "Property names must be snake_case: "

    override fun validate(swagger: Swagger): Violation? {
        val result = swagger.getAllJsonObjects().flatMap { (def, path) ->
            val badProps = def.keys.filterNot { PatternUtil.isSnakeCase(it) || it in whitelist }
            if (badProps.isNotEmpty()) listOf(badProps to path) else emptyList()
        }
        return if (result.isNotEmpty()) {
            val (props, paths) = result.unzip()
            val properties = props.flatten().toSet().joinToString(", ")
            Violation(title, desc + properties, violationType, id, paths)
        } else null
    }
}
