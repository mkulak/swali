package swali.rules.zalando

import io.swagger.models.Swagger
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import swali.*

class HyphenatePascalCaseHeadersRuleTest {
    val rule = HyphenatePascalCaseHeadersRule(config.headersWhiteList)

    @Test
    fun simplePositiveCase() {
        val swagger = swaggerWithHeaderParams("Right-Name")
        assertThat(rule.validate(swagger)).isNull()
    }

    @Test
    fun simpleNegativeCase() {
        val swagger = swaggerWithHeaderParams("kebap-case-name", "CamelCaseName")
        val result = rule.validate(swagger)!!
        assertThat(result.paths).hasSameElementsAs(listOf("parameters"))
        assertThat(result.description).contains("kebap-case-name", "CamelCaseName")
    }

    @Test
    fun mustAcceptValuesFromWhitelist() {
        val swagger = swaggerWithHeaderParams("ETag", "X-Trace-ID")
        assertThat(rule.validate(swagger)).isNull()
    }

    @Test
    fun mustAcceptZalandoHeaders() {
        val swagger = swaggerWithHeaderParams(
            "X-Flow-ID", "X-UID", "X-Tenant-ID", "X-Sales-Channel", "X-Frontend-Type",
            "X-Device-Type", "X-Device-OS", "X-App-Domain"
        )
        assertThat(rule.validate(swagger)).isNull()
    }

    @Test
    fun mustAcceptRateLimitHeaders() {
        val swagger = swaggerWithHeaderParams("X-RateLimit-Limit", "X-RateLimit-Remaining", "X-RateLimit-Reset")
        assertThat(rule.validate(swagger)).isNull()
    }

    @Test
    fun emptySwaggerShouldPass() {
        val swagger = Swagger()
        assertThat(rule.validate(swagger)).isNull()
    }

    @Test
    fun mustAcceptDigits() {
        val swagger = swaggerWithHeaderParams("X-P1n-Id")
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
