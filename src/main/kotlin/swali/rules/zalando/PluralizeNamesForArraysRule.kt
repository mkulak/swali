package swali.rules.zalando

import io.swagger.models.Swagger
import swali.*
import swali.utils.WordUtils.isPlural
import swali.utils.getAllJsonObjects

class PluralizeNamesForArraysRule : Rule {
    val title = "Array names should be pluralized"
    val violationType = ViolationType.SHOULD
    override val id = "120"

    override fun validate(swagger: Swagger): Violation? {
        val res = swagger.getAllJsonObjects().map { (def, path) ->
            val badProps = def.entries.filter { "array" == it.value.type && !isPlural(it.key) }
            if (badProps.isNotEmpty()) {
                val propsDesc = badProps.map { "'${it.key}'" }.joinToString(",")
                "$path: $propsDesc" to path
            } else null
        }.filterNotNull()

        return if (res.isNotEmpty()) {
            val (desc, paths) = res.unzip()
            Violation(title, desc.joinToString("\n"), violationType, id, paths)
        } else null
    }

}
