package swali.zalando

import io.swagger.models.Response
import io.swagger.models.Swagger
import io.swagger.models.parameters.Parameter
import swali.Rule
import swali.Violation

abstract class HttpHeadersRule(val headersWhitelist: Set<String>) : Rule {

    abstract fun createViolation(paths: List<String>): Violation

    abstract fun isViolation(header: String): Boolean

    override fun validate(swagger: Swagger): Violation? {
        fun Collection<Parameter>?.extractHeaders(path: String) =
            orEmpty().filter { it.`in` == "header" }.map { path to it.name }

        fun Collection<Response>?.extractHeaders(path: String) =
            orEmpty().flatMap { it.headers?.keys.orEmpty() }.map { path to it }

        val fromParams = swagger.parameters.orEmpty().values.extractHeaders("parameters")
        val fromPaths = swagger.paths.orEmpty().entries.flatMap { (name, path) ->
            path.parameters.extractHeaders(name) + path.operations.flatMap { operation ->
                operation.parameters.extractHeaders(name) + operation.responses.values.extractHeaders(name)
            }
        }
        val allHeaders = fromParams + fromPaths
        val paths = allHeaders
            .filter { it.second !in headersWhitelist && isViolation(it.second) }
            .map { "${it.first} ${it.second}" }
            .toSet()
            .toList()
        return if (paths.isNotEmpty()) createViolation(paths) else null
    }
}
