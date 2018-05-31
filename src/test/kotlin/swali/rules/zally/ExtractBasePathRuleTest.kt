package swali.rules.zally

import io.swagger.models.Swagger
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import swali.getFixture
import swali.swaggerWithPaths

class ExtractBasePathRuleTest {
    val rule = ExtractBasePathRule()

    @Test
    fun validateEmptyPath() {
        assertThat(rule.validate(Swagger())).isNull()
    }

    @Test
    fun simplePositiveCase() {
        val swagger = swaggerWithPaths("/orders/{order_id}", "/orders/{updates}", "/merchants")
        assertThat(rule.validate(swagger)).isNull()
    }

    @Test
    fun singlePathShouldPass() {
        val swagger = swaggerWithPaths("/orders/{order_id}")
        assertThat(rule.validate(swagger)).isNull()
    }

    @Test
    fun simpleNegativeCase() {
        val swagger = swaggerWithPaths(
            "/shipment/{shipment_id}",
            "/shipment/{shipment_id}/status",
            "/shipment/{shipment_id}/details"
        )
        assertThat(rule.validate(swagger)).isNotNull()
    }

    @Test
    fun multipleResourceNegativeCase() {
        val swagger = swaggerWithPaths(
            "/queue/models/configs/{config-id}",
            "/queue/models/",
            "/queue/models/{model-id}",
            "/queue/models/summaries"
        )
        assertThat(rule.validate(swagger)).isNotNull()
    }

    @Test
    fun shouldMatchWholeSubresource() {
        val swagger = swaggerWithPaths(
            "/api/{api_id}/deployments",
            "/api/{api_id}/",
            "/applications/{app_id}",
            "/applications/"
        )
        assertThat(rule.validate(swagger)).isNull()
    }

    @Test
    fun positiveCaseSpp() {
        val swagger = getFixture("api_spp.json")
        assertThat(rule.validate(swagger)).isNull()
    }

    @Test
    fun positiveCaseTinbox() {
        val swagger = getFixture("api_tinbox.yaml")
        assertThat(rule.validate(swagger)).isNull()
    }
}
