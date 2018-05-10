package swali

import io.swagger.parser.Swagger20Parser


interface Linter {
    fun doLint(apiDefinition: String, ignoreRules: Set<String>): LintingResponse
}

class LinterImpl(val rules: List<Rule>) : Linter {
    override fun doLint(apiDefinition: String, ignoreRules: Set<String>): LintingResponse {
        val swagger = try {
            Swagger20Parser().parse(apiDefinition)
        } catch (e: Exception) {
            return listOf(invalidApiViolation).toResponse()
        }
        val rulesToApply = rules.filter { it.id !in ignoreRules }
        val violations = rulesToApply.mapNotNull { it.validate(swagger) }
        return violations.toResponse()
    }
}

val invalidApiViolation =
    Violation("OpenAPI 2.0 schema", "Given file is not OpenAPI 2.0 compliant", ViolationType.MUST, "101", emptyList())

fun List<Violation>.toResponse() =
    LintingResponse("", this, this.groupBy { it.violationType }.mapValues { it.value.size })

fun ruleLink(id: String) = "https://zalando.github.io/restful-api-guidelines/#$id"

