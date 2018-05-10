package swali

import io.swagger.parser.Swagger20Parser


class Linter(val rules: List<Rule>) {
    fun doLint(request: LintingRequest): LintingResponse =
        try {
            val swagger = Swagger20Parser().parse(request.apiDefinition)
            val violations = rules.mapNotNull { it.validate(swagger) }
            violations.toResponse()
        } catch (e: Exception) {
            listOf(invalidApiViolation).toResponse()
        }
}

val invalidApiViolation =
    Violation("OpenAPI 2.0 schema", "Given file is not OpenAPI 2.0 compliant", ViolationType.MUST, "101", emptyList())

fun List<Violation>.toResponse() =
    LintingResponse("", this, this.groupBy { it.violationType }.mapValues { it.value.size })

fun ruleLink(id: String) = "https://zalando.github.io/restful-api-guidelines/#$id"

