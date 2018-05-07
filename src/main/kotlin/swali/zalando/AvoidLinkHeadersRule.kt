package swali.zalando

import swali.*


class AvoidLinkHeadersRule(headersWhitelist: Set<String>) : HttpHeadersRule(headersWhitelist) {
    val title = "Avoid Link in Header Rule"
    val violationType = ViolationType.MUST
    override val id = "166"
    private val DESCRIPTION = "Do Not Use Link Headers with JSON entities"

    override fun isViolation(header: String) = header == "Link"

    override fun createViolation(paths: List<String>): Violation =
        Violation(title, DESCRIPTION, violationType, ruleLink(id), paths)
}
