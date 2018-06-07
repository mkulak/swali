package swali.rules.zalando

import io.swagger.models.Swagger
import swali.*
import swali.utils.extractHeaders


class AvoidLinkHeadersRule(val headersWhitelist: Set<String>) : Rule {
    override val title = "Avoid Link in Header Rule"
    override val violationType = ViolationType.MUST
    override val id = "166"
    val desc = "Do Not Use Link Headers with JSON entities"

    override fun validate(swagger: Swagger): Violation? {
        val allHeaders = swagger.extractHeaders()
        val (paths, _) = allHeaders
            .filter { it.second !in headersWhitelist && it.second == "Link" }
            .unzip()
        return if (paths.isNotEmpty()) Violation(title, desc, violationType, ruleLink(id), paths.distinct()) else null
    }
}
