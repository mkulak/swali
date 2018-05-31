package swali.rules.zalando

import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import swali.*

class AvoidLinkHeadersRuleTest {
    val rule = AvoidLinkHeadersRule(config.headersWhiteList)

    @Test
    fun positiveCaseSpp() {
        val swagger = getFixture("api_spp.json")
        assertThat(rule.validate(swagger)).isNull()
    }

    @Test
    fun positiveCaseSpa() {
        val swagger = getFixture("api_spa.yaml")
        assertThat(rule.validate(swagger)).isNull()
    }

    @Test
    fun negativeCase() {
        val swagger = getFixture("avoidLinkHeaderRuleInvalid.json")
        val violation = rule.validate(swagger)!!
        assertThat(violation.violationType).isEqualTo(ViolationType.MUST)
        assertThat(violation.paths).hasSameElementsAs(
            listOf("/product-put-requests/{product_path}", "/products"))
    }
}
