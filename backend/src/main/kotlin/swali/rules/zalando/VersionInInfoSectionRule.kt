package swali.rules.zalando

import io.swagger.models.Swagger
import swali.*
import swali.utils.PatternUtil.isVersion


class VersionInInfoSectionRule : Rule {
    override val title = "Use semantic versioning"
    override val id = "116"
    override val violationType = ViolationType.SHOULD

    override fun validate(swagger: Swagger): Violation? {
        val version = swagger.info?.version
        val desc = when {
            version == null -> "Define API version in #/info/version section"
            !isVersion(version) -> "#/info/version has incorrect format. Should be <MAJOR>.<MINOR>.<PATCH>"
            else -> null
        }
        return if (desc != null) Violation(title, desc, violationType, id, emptyList()) else null
    }
}
