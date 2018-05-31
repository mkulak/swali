package swali.rules.zalando

import io.swagger.models.*
import io.swagger.models.parameters.QueryParameter
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import swali.ViolationType

class QueryParameterCollectionFormatRuleTest {
    val rule = QueryParameterCollectionFormatRule()

    fun param(format: String?) = QueryParameter().apply {
        name = "test"
        type = "array"
        collectionFormat = format
    }

    @Test
    fun negativeCaseCollectionFormatNotSupported() {
        val swagger = Swagger().apply {
            parameters = mapOf("test" to param("notSupported"))
        }

        val result = rule.validate(swagger)!!
        assertThat(result.violationType).isEqualTo(ViolationType.SHOULD)
    }

    @Test
    fun negativeCaseCollectionFormatNotSupportedFromPath() {
        val paramList = listOf(param("notSupported"))
        val swagger = Swagger().apply {
            paths = mapOf("/apis" to Path().apply { get = Operation().apply { parameters = paramList } })
        }

        val result = rule.validate(swagger)!!
        assertThat(result.violationType).isEqualTo(ViolationType.SHOULD)
    }

    @Test
    fun negativeCaseCollectionFormatNull() {
        val swagger = Swagger().apply {
            parameters = mapOf("test" to param(null))
        }

        val result = rule.validate(swagger)!!
        assertThat(result.violationType).isEqualTo(ViolationType.SHOULD)
    }

    @Test
    fun negativeCaseCollectionFormatNullFromPath() {
        val paramList = listOf(param(null))
        val swagger = Swagger().apply {
            paths = mapOf("/apis" to Path().apply { get = Operation().apply { parameters = paramList } })
        }

        val result = rule.validate(swagger)!!
        assertThat(result.violationType).isEqualTo(ViolationType.SHOULD)
    }

    @Test
    fun positiveCaseCsv() {
        val swagger = Swagger().apply {
            parameters =
                    mapOf("test" to param("csv"))
        }

        assertThat(rule.validate(swagger)).isNull()
    }

    @Test
    fun positiveCaseCsvFromPath() {
        val paramList = listOf(param("csv"))
        val swagger = Swagger().apply {
            paths = mapOf("/apis" to Path().apply { get = Operation().apply { parameters = paramList } })
        }

        assertThat(rule.validate(swagger)).isNull()
    }

    @Test
    fun positiveCaseMulti() {
        val swagger = Swagger().apply {
            parameters = mapOf("test" to param("multi"))
        }

        assertThat(rule.validate(swagger)).isNull()
    }

    @Test
    fun positiveCaseMultiFromPath() {
        val paramList = listOf(param("multi"))
        val swagger = Swagger().apply {
            paths = mapOf("/apis" to Path().apply { get = Operation().apply { parameters = paramList } })
        }

        assertThat(rule.validate(swagger)).isNull()
    }
}
