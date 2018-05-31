package swali.rules.zalando

import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import swali.getFixture

class SnakeCaseForQueryParamsRuleTest {
    val rule = SnakeCaseForQueryParamsRule()

    @Test
    fun shouldFindNoViolations() {
        assertThat(rule.validate(getFixture("snakeCaseForQueryParamsValid.json"))).isNull()
    }

    @Test
    fun shouldFindViolationsInLocalRef() {
        val result = rule.validate(getFixture("snakeCaseForQueryParamsInvalidLocalParam.json"))!!
        assertThat(result.paths).hasSameElementsAs(listOf("/items GET"))
    }

    @Test
    fun shouldFindViolationsInInternalRef() {
        val result = rule.validate(getFixture("snakeCaseForQueryParamsInvalidInternalRef.json"))!!
        assertThat(result.paths).hasSameElementsAs(listOf("/items GET"))
    }

    @Test
    fun shouldFindViolationsInExternalRef() {
        val result = rule.validate(getFixture("snakeCaseForQueryParamsInvalidExternalRef.json"))!!
        assertThat(result.paths).hasSameElementsAs(listOf("/items GET"))
    }
}
