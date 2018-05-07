package swali.zalando

import io.swagger.models.Swagger
import swali.*
import swali.utils.PatternUtil.isVersion


class VersionInInfoSectionRule : Rule {
    val title = "Provide version information"
    val violationType = ViolationType.SHOULD
    override val id = "116"
    val DESCRIPTION =
        "Only the documentation, not the API itself, needs version information. It should be in the format MAJOR.MINOR.DRAFT."

    override fun validate(swagger: Swagger): Violation? {
        val version = swagger.info?.version
        val desc = when {
            version == null -> "Version is missing"
            !isVersion(version) -> "Specified version has incorrect format: $version"
            else -> null
        }
        return desc?.let { Violation(title, "$DESCRIPTION $it", violationType, id, emptyList()) }
    }
}
