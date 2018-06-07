package swali.rules.zalando

import io.swagger.models.Swagger
import swali.*
import swali.utils.PatternUtil


class AvoidTrailingSlashesRule : Rule {
    override val title = "Avoid Trailing Slashes"
    override val violationType = ViolationType.MUST
    override val id = "136"
    val desc = "Found trailing slashes"

    override fun validate(swagger: Swagger): Violation? {
        val paths = swagger.paths.orEmpty().keys.filter { it != null && PatternUtil.hasTrailingSlash(it) }
        return if (!paths.isEmpty()) Violation(title, desc, violationType, id, paths) else null
    }
}
