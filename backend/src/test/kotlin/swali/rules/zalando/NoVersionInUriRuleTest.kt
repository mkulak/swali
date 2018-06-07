package swali.rules.zalando

import io.swagger.models.Swagger
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class NoVersionInUriRuleTest {
    val rule = NoVersionInUriRule()

    @Test
    fun returnsViolationsWhenVersionIsInTheBeginingOfBasePath() {
        val swagger = Swagger().apply { basePath = "/v1/tests" }
        assertThat(rule.validate(swagger)).isNotNull()
    }

    @Test
    fun returnsViolationsWhenVersionIsInTheMiddleOfBasePath() {
        val swagger = Swagger().apply { basePath = "/api/v1/tests" }
        assertThat(rule.validate(swagger)).isNotNull()
    }

    @Test
    fun returnsViolationsWhenVersionIsInTheEndOfBasePath() {
        val swagger = Swagger().apply { basePath = "/api/v1" }
        assertThat(rule.validate(swagger)).isNotNull()
    }

    @Test
    fun returnsViolationsWhenVersionIsBig() {
        val swagger = Swagger().apply { basePath = "/v1024/tests" }
        assertThat(rule.validate(swagger)).isNotNull()
    }

    @Test
    fun returnsEmptyViolationListWhenNoVersionFoundInURL() {
        val swagger = Swagger().apply { basePath = "/violations/" }
        assertThat(rule.validate(swagger)).isNull()
    }

    @Test
    fun returnsEmptyViolationListWhenBasePathIsNull() {
        val swagger = Swagger()
        assertThat(rule.validate(swagger)).isNull()
    }
}
