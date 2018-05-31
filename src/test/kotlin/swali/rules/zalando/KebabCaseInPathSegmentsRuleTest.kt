package swali.rules.zalando

import io.swagger.models.Swagger
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import swali.swaggerWithPaths

class KebabCaseInPathSegmentsRuleTest {
    val testPath1 = "/shipment-order/{shipment_order_id}"
    val testPath2 = "/partner-order/{partner_order_id}"
    val testPath3 = "/partner-order/{partner_order_id}/partner-order/{partner_order_id}"
    val wrongTestPath1 = "/shipment_order/{shipment_order_id}"
    val wrongTestPath2 = "/partner-order/{partner_order_id}/partner-order1/{partner_order_id}"

    val rule = KebabCaseInPathSegmentsRule()

    @Test
    fun emptySwagger() {
        assertThat(rule.validate(Swagger())).isNull()
    }

    @Test
    fun validateNormalPath() {
        val swagger = swaggerWithPaths(testPath1)
        assertThat(rule.validate(swagger)).isNull()
    }

    @Test
    fun validateMultipleNormalPaths() {
        val swagger = swaggerWithPaths(testPath1, testPath2, testPath3)
        assertThat(rule.validate(swagger)).isNull()
    }

    @Test
    fun validateFalsePath() {
        val swagger = swaggerWithPaths(wrongTestPath1)
        val result = rule.validate(swagger)!!
        assertThat(result.paths).hasSameElementsAs(listOf(wrongTestPath1))
    }

    @Test
    fun validateMultipleFalsePaths() {
        val swagger = swaggerWithPaths(wrongTestPath1, testPath2, wrongTestPath2)
        val result = rule.validate(swagger)!!
        assertThat(result.paths).hasSameElementsAs(listOf(wrongTestPath1, wrongTestPath2))
    }

}
