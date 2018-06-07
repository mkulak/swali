package swali.rules.zalando

import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import swali.*

class EverySecondPathLevelParameterRuleTest {
    val rule = EverySecondPathLevelParameterRule()

    @Test
    fun positiveCase() {
        val swagger = swaggerWithPaths(
            "/some/{param-1}/path/",
            "/another/{param-1}/path/{param-2}/third",
            "/merchant/{merchant_id}/order/{order_id}",
            "/orders")
        assertThat(rule.validate(swagger)).isNull()
    }

    @Test
    fun negativeCase() {
        val swagger = swaggerWithPaths(
            "/api/some/{param-1}/path/",
            "/another/{param-0}/{param-1}",
            "/okeish-path",
            "/{merchant_id}",
            "/{parcel_id}/info/{update-group}")
        val result = rule.validate(swagger)!!
        assertThat(result.violationType).isEqualTo(ViolationType.MUST)
        assertThat(result.paths).hasSameElementsAs(listOf(
            "/api/some/{param-1}/path/",
            "/another/{param-0}/{param-1}",
            "/{merchant_id}",
            "/{parcel_id}/info/{update-group}"))
    }

    @Test
    fun positiveCaseSpp() {
        val swagger = getFixture("api_spp.json")
        assertThat(rule.validate(swagger)).isNull()
    }

    @Test
    fun negativeCaseSpa() {
        val swagger = getFixture("api_spa.yaml")
        assertThat(rule.validate(swagger)!!.paths).hasSameElementsAs(listOf("/reports/jobs", "/reports/jobs/{job-id}"))
    }
}
