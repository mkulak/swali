package swali.rules.zalando

import io.swagger.models.Swagger
import swali.*
import swali.utils.PatternUtil

class KebabCaseInPathSegmentsRule : Rule {

    val title = "Lowercase words with hyphens"
    val violationType = ViolationType.MUST
    override val id = "129"
    private val description = "Use lowercase separate words with hyphens for path segments"

    override fun validate(swagger: Swagger): Violation? {
        val paths = swagger.paths.orEmpty().keys.filterNot {
            val pathSegments = it.split("/").filter { it.isNotEmpty() }
            pathSegments.filter { !PatternUtil.isPathVariable(it) && !PatternUtil.isLowerCaseAndHyphens(it) }.isEmpty()
        }
        return if (paths.isNotEmpty()) Violation(title, description, violationType, id, paths) else null
    }
}
