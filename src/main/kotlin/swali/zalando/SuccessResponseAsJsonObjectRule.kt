package swali.zalando

import io.swagger.models.*
import io.swagger.models.properties.Property
import io.swagger.models.properties.RefProperty
import swali.*

class SuccessResponseAsJsonObjectRule : Rule {
    val title = "Response As JSON Object"
    val violationType = ViolationType.MUST
    override val id = "110"
    private val DESCRIPTION = "Always Return JSON Objects As Top-Level Data Structures To Support Extensibility"

    override fun validate(swagger: Swagger): Violation? {
        val paths = ArrayList<String>()
        for ((key, value) in swagger.paths.orEmpty()) {
            for ((method, operation) in value.operationMap) {
                for ((code, response) in operation.responses) {
                    val httpCodeInt = code.toIntOrNull() ?: 0
                    if (httpCodeInt in 200..299) {
                        val schema = response.schema
                        if (schema != null && "object" != schema.type && !schema.isRefToObject(swagger)) {
                            paths.add("$key $method $code")
                        }
                    }
                }
            }
        }

        return if (paths.isNotEmpty()) Violation(title, DESCRIPTION, violationType, id, paths) else null
    }

    private fun Property?.isRefToObject(swagger: Swagger) =
        if (this is RefProperty && swagger.definitions != null) {
            val model = swagger.definitions[simpleRef]
            (model is ModelImpl && model.type == "object") || model is ComposedModel
        } else false
}
