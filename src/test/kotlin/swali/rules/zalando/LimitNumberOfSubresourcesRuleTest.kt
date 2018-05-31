package swali.rules.zalando

import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import swali.config
import swali.getFixture

class LimitNumberOfSubresourcesRuleTest {
    val rule = LimitNumberOfSubresourcesRule(config.subresourcesLimit)

    @Test
    fun positiveCase() {
        val swagger = getFixture("limitNumberOfSubresourcesValid.json")
        assertThat(rule.validate(swagger)).isNull()
    }

    @Test
    fun negativeCase() {
        val swagger = getFixture("limitNumberOfSubresourcesInvalid.json")
        val result = rule.validate(swagger)!!
        assertThat(result.paths).hasSameElementsAs(
            listOf("/items/{some_id}/item_level_1/{level1_id}/item-level-2/{level2_id}/item-level-3/{level3_id}/item-level-4")
        )
    }

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
}
