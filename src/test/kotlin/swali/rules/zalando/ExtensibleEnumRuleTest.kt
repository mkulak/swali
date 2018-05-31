package swali.rules.zalando

import io.swagger.models.Swagger
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import swali.getFixture

class ExtensibleEnumRuleTest {
    val rule = ExtensibleEnumRule()

    @Test
    fun returnsNoViolationIfEmptySwagger() {
        assertThat(rule.validate(Swagger())).isNull()
    }

    @Test
    fun returnsViolationIfAnEnumInModelProperty() {
        val swagger = getFixture("enum_in_model_property.yaml")
        val expectedPaths = listOf(
            "#/definitions/CrawledAPIDefinition/properties/status"
        )
        val violation = rule.validate(swagger)

        assertThat(violation).isNotNull()
        assertThat(violation?.paths).hasSameElementsAs(expectedPaths)
    }

    @Test
    fun returnsViolationIfAnEnumInRequestParameter() {
        val swagger = getFixture("enum_in_request_parameter.yaml")
        val violation = rule.validate(swagger)
        val expectedPaths = listOf(
            "#/paths/apis/{api_id}/versions/GET/parameters/lifecycle_state",
            "#/paths/apis/GET/parameters/environment"
        )
        assertThat(violation).isNotNull()
        assertThat(violation?.paths).hasSameElementsAs(expectedPaths)
    }

    @Test
    fun returnsNoViolationIfNoEnums() {
        val swagger = getFixture("no_must_violations.yaml")

        assertThat(rule.validate(swagger)).isNull()
    }

}
