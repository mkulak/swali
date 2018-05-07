package swali.zalando

import io.swagger.models.Swagger
import swali.*
import swali.utils.PatternUtil

class NoVersionInUriRule : Rule {
    val title = "Do Not Use URI Versioning"
    val violationType = ViolationType.MUST
    override val id = "115"
    private val description = "basePath attribute contains version number"

    override fun validate(swagger: Swagger): Violation? {
        val hasVersion = swagger.basePath != null && PatternUtil.hasVersionInUrl(swagger.basePath)
        return if (hasVersion) Violation(title, description, violationType, id, emptyList()) else null
    }
}
