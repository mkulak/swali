package swali.zalando

import swali.Violation
import swali.ViolationType
import swali.utils.PatternUtil

class PascalCaseHttpHeadersRule(headersWhitelist: Set<String>) : HttpHeadersRule(headersWhitelist) {
    val title = "Prefer Hyphenated-Pascal-Case for HTTP header fields"
    val violationType = ViolationType.SHOULD
    override val id = "132"

    override fun isViolation(header: String) = !PatternUtil.isHyphenatedPascalCase(header)

    override fun createViolation(paths: List<String>): Violation {
        return Violation(title, "Header is not Hyphenated-Pascal-Case", violationType, id, paths)
    }
}
