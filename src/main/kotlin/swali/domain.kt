package swali

data class LintingRequest(
    val apiDefinition: String?,
    val apiDefinitionUrl: String?,
    val ignoreRules: List<String>?
)

data class LintingResponse (
    val message: String,
    val violations: List<Violation>,
    val violationsCount: Map<ViolationType, Int>
)


data class Violation(
    val title: String,
    val description: String,
    val violationType: ViolationType,
    val ruleLink: String,
    val paths: List<String>
)

enum class ViolationType {
    MUST,
    SHOULD,
    MAY,
    HINT
}