package swali.rules.zalando

import io.swagger.models.Swagger
import swali.*
import swali.utils.PatternUtil.isPathVariable


class EverySecondPathLevelParameterRule : Rule {
    override val title = "Every Second Path Level To Be Parameter"
    override val violationType = ViolationType.MUST
    override val id = "143"
    val desc = "Every second path level must be a path parameter"

    override fun validate(swagger: Swagger): Violation? {
        val paths = swagger.paths.orEmpty().keys.filterNot {
            val pathSegments = it.split("/").filter { it.isNotEmpty() }
            pathSegments.filterIndexed { i, segment -> isPathVariable(segment) == (i % 2 == 0) }.isEmpty()
        }
        return if (paths.isNotEmpty()) Violation(title, desc, violationType, id, paths) else null
    }
}
