package swali.rules.zalando

import io.swagger.models.Swagger
import swali.*
import swali.utils.PatternUtil.isPathVariable

class NestedPathsMayBeRootPathsRule : Rule {
    override val title = "Consider Using (Non-) Nested URLs"
    override val violationType = ViolationType.MAY
    override val id = "145"
    val desc = "Nested paths / URLs may be top-level resource"

    override fun validate(swagger: Swagger): Violation? {
        val paths = swagger.paths.orEmpty().keys.filter {
            val pathSegments = it.split("/".toRegex())
            // we are only interested in paths that have sub-resource followed by a param: /path1/{param1}/path2/{param2}
            pathSegments.size > 4 && isPathVariable(pathSegments.last())
        }
        return if (paths.isNotEmpty()) Violation(title, desc, violationType, id, paths) else null
    }
}
