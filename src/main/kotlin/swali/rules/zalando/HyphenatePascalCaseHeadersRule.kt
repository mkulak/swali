package swali.rules.zalando

import io.swagger.models.Swagger
import swali.*
import swali.utils.PatternUtil
import swali.utils.extractHeaders


class HyphenatePascalCaseHeadersRule(val headersWhitelist: Set<String>) : Rule {
    override val title = "Prefer Hyphenated-Pascal-Case for HTTP headers"
    override val violationType = ViolationType.SHOULD
    override val id = "132"
    val desc = "These headers are not Hyphenated-Pascal-Case: "

    override fun validate(swagger: Swagger): Violation? {
        val allHeaders = swagger.extractHeaders()
        val (paths, headers) = allHeaders
            .filter { it.second !in headersWhitelist && !PatternUtil.isHyphenatedPascalCase(it.second) }
            .unzip()
        val namesStr = headers.distinct().joinToString(", ")
        return if (paths.isNotEmpty()) {
            Violation(title, desc + namesStr, violationType, ruleLink(id), paths.distinct())
        } else null
    }
}
