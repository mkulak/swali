package swali.rules.zalando

import io.swagger.models.Swagger
import swali.*

class NotSpecifyStandardErrorCodesRule(val standardErrorCodes: Set<Int>) : Rule {
    val title = "Not Specify Standard Error Codes"
    val violationType = ViolationType.HINT
    override val id = "151"
    private val description = "Not Specify Standard Error Status Codes Like 400, 404, 503 " +
            "Unless They Have Another Meaning Or Special Implementation/Contract Detail"

    override fun validate(swagger: Swagger): Violation? {

        val paths = swagger.paths.orEmpty().flatMap { pathEntry ->
            pathEntry.value.operationMap.orEmpty().flatMap { opEntry ->
                opEntry.value.responses.orEmpty().flatMap { responseEntry ->
                    val httpCode = responseEntry.key.toIntOrNull()
                    if (httpCode in standardErrorCodes) {
                        listOf("${pathEntry.key} ${opEntry.key} ${responseEntry.key}")
                    } else emptyList()
                }
            }
        }

        return if (paths.isNotEmpty()) Violation(title, description, violationType, id, paths) else null
    }

}
