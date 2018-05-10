import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent
import com.fasterxml.jackson.module.kotlin.readValue
import org.junit.Test
import swali.*
import kotlin.test.assertEquals

class MainTest {
    @Test
    fun `handle should return linting response`() {
        val req = LintingRequest(
            apiDefinition = "Body",
            apiDefinitionUrl = null,
            ignoreRules = arrayOf("a", "foo")
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
}

class DummyLinter(val f: (String, Set<String>) -> LintingResponse) : Linter {
    override fun doLint(apiDefinition: String, ignoreRules: Set<String>): LintingResponse =
        f(apiDefinition, ignoreRules)
}

