package swali.rules.zalando

import io.swagger.models.Info
import io.swagger.models.Swagger
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class VersionInInfoSectionRuleTest {
    val rule = VersionInInfoSectionRule()

    fun swaggerWithVersion(versionValue: String) =
        Swagger().apply {
            info = Info().apply {
                version = versionValue
            }
        }


    @Test
    fun emptySwagger() {
        val result = rule.validate(Swagger())!!
        assertThat(result.description).contains("Define API version ")
    }

    @Test
    fun missingVersion() {
        val swagger = Swagger().apply { info = Info() }
        val result = rule.validate(swagger)!!
        assertThat(result.description).contains("Define API version ")
    }

    @Test
    fun wrongVersionFormat() {
        val swagger = swaggerWithVersion("1.2.3-a")
        val result = rule.validate(swagger)!!
        assertThat(result.description).contains("#/info/version has incorrect format")
    }

    @Test
    fun correctVersion() {
        val swagger = swaggerWithVersion("1.2.3")
        assertThat(rule.validate(swagger)).isNull()
    }
}
