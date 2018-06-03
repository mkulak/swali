package swali.rules.zalando

import io.swagger.models.Swagger
import swali.*

class LimitNumberOfResourcesRule(val pathsCountLimit: Int) : Rule {
    override val title = "Limit number of Resources"
    override val violationType = ViolationType.SHOULD
    override val id = "146"

    override fun validate(swagger: Swagger): Violation? {
        val paths = swagger.paths.orEmpty()
        val pathsCount = paths.size
        return if (pathsCount > pathsCountLimit) {
            Violation(
                title, "Number of paths $pathsCount is greater than $pathsCountLimit", violationType, id,
                paths.keys.toList()
            )
        } else null
    }
}
