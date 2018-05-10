package swali.rules.zalando

import swali.Violation
import swali.ViolationType
import swali.utils.PatternUtil


class HyphenateHttpHeadersRule(headersWhitelist: Set<String>) : HttpHeadersRule(headersWhitelist) {
    val title = "Use Hyphenated HTTP Headers"
    val violationType = ViolationType.MUST
    override val id = "131"

    override fun isViolation(header: String) = !PatternUtil.isHyphenated(header)

    override fun createViolation(paths: List<String>): Violation =
        Violation(title, "Header names should be hyphenated", violationType, id, paths)
}
