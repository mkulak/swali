package swali.rules.zally

import io.swagger.models.Swagger
import swali.*

class NoProtocolInHostRule : Rule {
    override val title = "Host should not contain protocol"
    override val violationType = ViolationType.MUST
    override val id = "M008"
    val desc = "Information about protocol should be placed in schema. Current host value '%s' contains protocol"

    override fun validate(swagger: Swagger): Violation? {
        val host = swagger.host.orEmpty()
        return if ("://" in host) Violation(title, desc.format(host), violationType, "", emptyList()) else null
    }
}
