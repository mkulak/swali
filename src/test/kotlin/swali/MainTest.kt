package swali

import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent
import com.fasterxml.jackson.module.kotlin.readValue
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.fail

class MainTest {
    @Test
    fun `handle should return linting response`() {
        val req = LintingRequest(
            apiDefinition = "Body",
            apiDefinitionUrl = null,
            ignoreRules = listOf("a", "foo")
        )
        val apiReq = APIGatewayProxyRequestEvent().apply { body = mapper.writeValueAsString(req) }
        val expectedResponse = LintingResponse(
            "some message",
            listOf(
                Violation(
                    "test title",
                    "some desc",
                    ViolationType.SHOULD,
                    "http://some.url",
                    listOf("path1", "path2")
                )
            ),
            mapOf(ViolationType.MAY to 1, ViolationType.HINT to 10)
        )
        val linter = DummyLinter { apiDef, ignores ->
            assertEquals(req.apiDefinition, apiDef)
            assertEquals(req.ignoreRules.orEmpty().toSet(), ignores)
            expectedResponse
        }
        val res = handle(apiReq, linter, {})
        assertEquals(200, res.statusCode)
        assertEquals(expectedResponse, mapper.readValue(res.body))
    }

    @Test
    fun `bad request`() {
        val apiReq = APIGatewayProxyRequestEvent().apply { body = "invalid request" }
        val linter = DummyLinter { apiDef, ignores -> fail() }
        val res = handle(apiReq, linter, {})
        assertEquals(400, res.statusCode)
        assertThat(res.body).contains("Can't parse request body")
    }

    @Test
    fun `internal server error`() {
        val req = LintingRequest(
            apiDefinition = "Body",
            apiDefinitionUrl = null,
            ignoreRules = listOf("a", "foo")
        )
        val apiReq = APIGatewayProxyRequestEvent().apply { body = mapper.writeValueAsString(req) }
        val linter = DummyLinter { apiDef, ignores -> throw NullPointerException() }
        val res = handle(apiReq, linter, {})
        assertEquals(500, res.statusCode)
        assertThat(res.body).contains("internal server error")
    }
}

class DummyLinter(val f: (String, Set<String>) -> LintingResponse) : Linter {
    override fun doLint(apiDefinition: String, ignoreRules: Set<String>): LintingResponse =
        f(apiDefinition, ignoreRules)
}

