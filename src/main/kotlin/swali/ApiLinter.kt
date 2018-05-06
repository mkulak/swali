package swali


class ApiLinter {
    fun doLint(request: LintingRequest): LintingResponse {
        return LintingResponse("test message", emptyList(), mapOf(ViolationType.MAY to 0))
    }
}