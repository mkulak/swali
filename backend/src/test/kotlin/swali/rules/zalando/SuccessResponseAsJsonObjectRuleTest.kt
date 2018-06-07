package swali.rules.zalando

import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import swali.getFixture

class SuccessResponseAsJsonObjectRuleTest {
    val rule = SuccessResponseAsJsonObjectRule()

    @Test
    fun positiveCase() {
        assertThat(rule.validate(getFixture("successResponseAsJsonObjectValid.json"))).isNull()
    }

    @Test
    fun negativeCase() {
        val result = rule.validate(getFixture("successResponseAsJsonObjectInvalid.json"))!!
        assertThat(result.paths).hasSameElementsAs(listOf("/pets GET 200", "/pets POST 200"))
    }

    @Test
    fun positiveCaseSpp() {
        val swagger = getFixture("api_spp.json")
        assertThat(rule.validate(swagger)).isNull()
    }

    @Test
    fun npeBug() {
        assertThat(rule.validate(getFixture("sample_swagger_api.yaml"))).isNotNull()
    }
}
