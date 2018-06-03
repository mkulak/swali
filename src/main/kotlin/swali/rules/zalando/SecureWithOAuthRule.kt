package swali.rules.zalando

import io.swagger.models.Swagger
import swali.*


class SecureWithOAuthRule : Rule {
    override val id = "104"
    override val title = "Secure Endpoints with OAuth 2.0"
    override val violationType = ViolationType.MUST
    val desc = "No OAuth2 security definitions found"

    override fun validate(swagger: Swagger): Violation? {
        val hasOAuth = swagger.securityDefinitions.orEmpty().values.any { it.type?.toLowerCase() == "oauth2" }
        return if (!hasOAuth) Violation(title, desc, violationType, ruleLink(id), emptyList()) else null
    }
}