package swali.rules.zalando

import io.swagger.models.Swagger
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import swali.config
import swali.swaggerWithDefinitions

class SnakeCaseInPropNameRuleTest {
    val rule = SnakeCaseInPropNameRule(config.snakeCaseInPropNameWhiteList)

    @Test
    fun emptySwagger() {
        assertThat(rule.validate(Swagger())).isNull()
    }

    @Test
    fun validateNormalProperty() {
        val swagger = swaggerWithDefinitions("ExampleDefinition" to listOf("test_property"))
        assertThat(rule.validate(swagger)).isNull()
    }

    @Test
    fun validateMultipleNormalProperties() {
        val swagger = swaggerWithDefinitions("ExampleDefinition" to listOf("test_property", "test_property_two"))
        assertThat(rule.validate(swagger)).isNull()
    }

    @Test
    fun validateMultipleNormalPropertiesInMultipleDefinitions() {
        val swagger = swaggerWithDefinitions(
            "ExampleDefinition" to listOf("test_property"),
            "ExampleDefinitionTwo" to listOf("test_property_two")
        )
        assertThat(rule.validate(swagger)).isNull()
    }

    @Test
    fun validateFalseProperty() {
        val swagger = swaggerWithDefinitions("ExampleDefinition" to listOf("TEST_PROPERTY"))
        val result = rule.validate(swagger)!!
        assertThat(result.description).contains("TEST_PROPERTY")
        assertThat(result.paths).hasSameElementsAs(listOf("#/definitions/ExampleDefinition"))
    }

    @Test
    fun validateMultipleFalsePropertiesInMultipleDefinitions() {
        val swagger = swaggerWithDefinitions(
            "ExampleDefinition" to listOf("TEST_PROPERTY"),
            "ExampleDefinitionTwo" to listOf("test_property_TWO")
        )
        val result = rule.validate(swagger)!!
        assertThat(result.description).contains("TEST_PROPERTY", "test_property_TWO")
        assertThat(result.paths).hasSameElementsAs(listOf(
            "#/definitions/ExampleDefinition",
            "#/definitions/ExampleDefinitionTwo")
        )
    }

    @Test
    fun notFireOnWhitelistedProperty() {
        val swagger = swaggerWithDefinitions("ExampleDefinition" to listOf("_links"))
        assertThat(rule.validate(swagger)).isNull()
    }
}
