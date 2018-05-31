package swali.rules.zalando

import io.swagger.models.Scheme
import io.swagger.models.Swagger
import swali.*
import swali.ViolationType.MUST


class SecureWithHttpsRule : Rule {
    override val id = "104"
    val title = "Secure Endpoints with OAuth 2.0"
    val description = "OAuth2 must be only used together with https"

    override fun validate(swagger: Swagger): Violation? {
        val containsHttpScheme = Scheme.HTTP in swagger.schemes.orEmpty()
        return if (containsHttpScheme) Violation(title, description, MUST, ruleLink(id), emptyList()) else null
    }
}