package swali.rules.zalando

import io.swagger.models.Swagger
import swali.*
import swali.ViolationType.MUST


class SecureWithOAuthRule : Rule {
    override val id = "104"
    val title = "Secure Endpoints with OAuth 2.0"
    val description = "No OAuth2 security definitions found"

    override fun validate(swagger: Swagger): Violation? {
        val hasOAuth = swagger.securityDefinitions.orEmpty().values.any { it.type?.toLowerCase() == "oauth2" }
        return if (!hasOAuth) Violation(title, description, MUST, ruleLink(id), emptyList()) else null
    }
}