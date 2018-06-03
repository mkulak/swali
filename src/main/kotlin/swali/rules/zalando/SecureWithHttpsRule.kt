package swali.rules.zalando

import io.swagger.models.Scheme
import io.swagger.models.Swagger
import swali.*


class SecureWithHttpsRule : Rule {
    override val id = "104"
    override val title = "Secure Endpoints with OAuth 2.0"
    override val violationType = ViolationType.MUST
    val desc = "OAuth2 must be only used together with https"

    override fun validate(swagger: Swagger): Violation? {
        val schemeIsHttp = Scheme.HTTP in swagger.schemes.orEmpty()
        return if (schemeIsHttp) Violation(title, desc, violationType, ruleLink(id), emptyList()) else null
    }
}