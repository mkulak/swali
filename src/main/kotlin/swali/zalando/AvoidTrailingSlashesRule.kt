package swali.zalando

import io.swagger.models.Swagger
import swali.*
import swali.utils.PatternUtil


class AvoidTrailingSlashesRule : Rule {
    val title = "Avoid Trailing Slashes"
    val violationType = ViolationType.MUST
    override val id = "136"
    private val DESCRIPTION = "Rule avoid trailing slashes is not followed"

    override fun validate(swagger: Swagger): Violation? {
        val paths = swagger.paths.orEmpty().keys.filter { it != null && PatternUtil.hasTrailingSlash(it) }
        return if (!paths.isEmpty()) Violation(title, DESCRIPTION, violationType, id, paths) else null
    }
}
