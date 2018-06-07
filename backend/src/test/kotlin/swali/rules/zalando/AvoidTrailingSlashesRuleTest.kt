package swali.rules.zalando

import io.swagger.models.Swagger
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import swali.swaggerWithPaths

class AvoidTrailingSlashesRuleTest {
    val rule = AvoidTrailingSlashesRule()

    @Test
    fun emptySwagger() {
        assertThat(rule.validate(Swagger())).isNull()
    }

    @Test
    fun positiveCase() {
        val testAPI = swaggerWithPaths("/api/test-api")
        assertThat(rule.validate(testAPI)).isNull()
    }

    @Test
    fun negativeCase() {
        val testAPI = swaggerWithPaths("/api/test-api/", "/api/test", "/some/other/path", "/long/bad/path/with/slash/")
        assertThat(rule.validate(testAPI)!!.paths).hasSameElementsAs(
            listOf("/api/test-api/", "/long/bad/path/with/slash/"))
    }
}
