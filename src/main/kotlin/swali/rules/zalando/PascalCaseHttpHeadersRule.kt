package swali.rules.zalando

import io.swagger.models.Swagger
import swali.*
import swali.utils.PatternUtil
import swali.utils.extractHeaders

class PascalCaseHttpHeadersRule(val headersWhitelist: Set<String>) : Rule {
    val title = "Prefer Hyphenated-Pascal-Case for HTTP header fields"
    val desc = "Header is not Hyphenated-Pascal-Case"
    val violationType = ViolationType.SHOULD
    override val id = "132"

    override fun validate(swagger: Swagger): Violation? {
        val allHeaders = swagger.extractHeaders()
        val paths = allHeaders
            .filter { it.second !in headersWhitelist && !PatternUtil.isHyphenatedPascalCase(it.second) }
            .map { it.first + " " + it.second }
            .distinct()
        return if (paths.isNotEmpty()) Violation(title, desc, violationType, ruleLink(id), paths) else null
    }
}
