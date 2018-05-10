package swali.rules.zally

import io.swagger.models.Swagger
import swali.*

class NoProtocolInHostRule : Rule {
    val title = "Host should not contain protocol"
    val violationType = ViolationType.MUST
    override val id = "M008"
    private val desc = "Information about protocol should be placed in schema. Current host value '%s' contains protocol"

    override fun validate(swagger: Swagger): Violation? {
        val host = swagger.host.orEmpty()
        return if ("://" in host) Violation(title, desc.format(host), violationType, "", emptyList()) else null
    }
}
