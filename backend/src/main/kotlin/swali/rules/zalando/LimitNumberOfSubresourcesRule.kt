package swali.rules.zalando

import io.swagger.models.Swagger
import swali.*
import swali.utils.PatternUtil

class LimitNumberOfSubresourcesRule(val subresourcesLimit: Int) : Rule {
    override val title = "Limit number of Sub-resources level"
    override val violationType = ViolationType.SHOULD
    override val id = "147"
    val desc = "Number of sub-resources should not exceed 3"

    override fun validate(swagger: Swagger): Violation? {
        val paths = swagger.paths.orEmpty().keys.filter { path ->
            path.split("/").filter { it.isNotEmpty() && !PatternUtil.isPathVariable(it) }.size - 1 > subresourcesLimit
        }
        return if (paths.isNotEmpty()) Violation(title, desc, violationType, id, paths) else null
    }
}
