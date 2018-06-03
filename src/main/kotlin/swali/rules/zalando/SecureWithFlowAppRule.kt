package swali.rules.zalando

import io.swagger.models.Swagger
import io.swagger.models.auth.OAuth2Definition
import swali.*


class SecureWithFlowAppRule : Rule {
    override val id = "104"
    override val title = "Secure Endpoints with OAuth 2.0"
    override val violationType = ViolationType.MUST
    val desc = "OAuth2 security definitions must use application flow"

    override fun validate(swagger: Swagger): Violation? {
        val hasWrongFlow = swagger
            .securityDefinitions
            .orEmpty()
            .values
            .filter { it.type?.toLowerCase() == "oauth2" }
            .any { (it as OAuth2Definition).flow != "application" }

        return if (hasWrongFlow)
            Violation(title, desc, violationType, ruleLink(id), emptyList())
        else null
    }
}