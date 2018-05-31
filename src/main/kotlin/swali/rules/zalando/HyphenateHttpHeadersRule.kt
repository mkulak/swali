package swali.rules.zalando

import io.swagger.models.Swagger
import swali.*
import swali.utils.PatternUtil
import swali.utils.extractHeaders


class HyphenateHttpHeadersRule(val headersWhitelist: Set<String>) : Rule {
    val title = "Use Hyphenated HTTP Headers"
    val desc = "Header names should be hyphenated"
    val violationType = ViolationType.MUST
    override val id = "131" //TODO MK: this rule doesn't exist anymore


    override fun validate(swagger: Swagger): Violation? {
        val allHeaders = swagger.extractHeaders()
        val paths = allHeaders
            .filter { it.second !in headersWhitelist && !PatternUtil.isHyphenated(it.second) }
            .map { it.first + " " + it.second }
            .distinct()
        return if (paths.isNotEmpty()) Violation(title, desc, violationType, ruleLink(id), paths) else null
    }
}
