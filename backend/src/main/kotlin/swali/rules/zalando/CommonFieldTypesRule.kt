package swali.rules.zalando

import io.swagger.models.Swagger
import io.swagger.models.properties.Property
import swali.*
import swali.utils.getAllJsonObjects

class CommonFieldTypesRule(val commonFields: Map<String, Pair<String, String>>) : Rule {
    override val title = "Use common field names"
    override val violationType = ViolationType.MUST
    override val id = "174"

    fun checkField(name: String, property: Property): String? =
        commonFields[name.toLowerCase()]?.let { (type, format) ->
            if (property.type != type)
                "field '$name' has type '${property.type}' (expected type '$type')"
            else if (property.format != format && format != "null")
                "field '$name' has type '${property.type}' with format '${property.format}' (expected format '$format')"
            else null
        }

    override fun validate(swagger: Swagger): Violation? {
        val res = swagger.getAllJsonObjects().map { (def, path) ->
            val badProps = def.entries.map { checkField(it.key, it.value) }.filterNotNull()
            if (badProps.isNotEmpty())
                (path + ": " + badProps.joinToString(", ")) to path
            else null
        }.filterNotNull()

        return if (res.isNotEmpty()) {
            val (desc, paths) = res.unzip()
            Violation(title, desc.joinToString(", "), violationType, id, paths)
        } else null
    }
}
