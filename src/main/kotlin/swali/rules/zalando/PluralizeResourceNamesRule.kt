package swali.rules.zalando

import io.swagger.models.Swagger
import swali.*
import swali.utils.PatternUtil.isPathVariable
import swali.utils.WordUtils.isPlural

class PluralizeResourceNamesRule : Rule {
    val title = "Pluralize Resource Names"
    val violationType = ViolationType.MUST
    override val id = "134"
    private val DESC_PATTERN = "Resources %s are singular (but we are not sure)"

    override fun validate(swagger: Swagger): Violation? {
        val res = swagger.paths.orEmpty().keys.flatMap { path ->
            val partsToCheck = path.split("/".toRegex())
            partsToCheck.filter { s -> !s.isEmpty() && !isPathVariable(s) && !isPlural(s) }.map { it to path }
        }
        return if (res.isNotEmpty()) {
            val desc = res.map { "'${it.first}'" }.toSet().joinToString(", ")
            val paths = res.map { it.second }
            Violation(title, String.format(DESC_PATTERN, desc), violationType, id, paths)
        } else null
    }
}
