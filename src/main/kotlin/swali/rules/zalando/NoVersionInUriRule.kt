package swali.rules.zalando

import io.swagger.models.Swagger
import swali.*
import swali.utils.PatternUtil

class NoVersionInUriRule : Rule {
    override val title = "Do Not Use URI Versioning"
    override val violationType = ViolationType.MUST
    override val id = "115"
    val desc = "basePath attribute contains version number"

    override fun validate(swagger: Swagger): Violation? {
        val hasVersion = swagger.basePath != null && PatternUtil.hasVersionInUrl(swagger.basePath)
        return if (hasVersion) Violation(title, desc, violationType, id, emptyList()) else null
    }
}
