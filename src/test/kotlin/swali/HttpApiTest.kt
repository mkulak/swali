package swali

import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent
import com.fasterxml.jackson.module.kotlin.readValue
import com.sun.net.httpserver.HttpServer
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import java.net.*
import kotlin.test.assertEquals
import kotlin.test.fail


class HttpApiTest {
    @Test
    fun `handle should return linting response`() {
        val req = LintingRequest(
            apiDefinition = "Body",
            apiDefinitionUrl = null,
            ignoreRules = listOf("a", "foo")
        )
        val apiReq = APIGatewayProxyRequestEvent().apply {
            path = "/violations"
            body = mapper.writeValueAsString(req)
        }
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
        val unit = HttpApi(linter)
        val res = unit.route(apiReq, {})
        assertEquals(200, res.statusCode)
        assertEquals(expectedResponse, mapper.readValue(res.body))
    }

    @Test
    fun `bad request`() {
        val apiReq = APIGatewayProxyRequestEvent().apply {
            path = "/violations"
            body = "invalid request"
        }
        val linter = DummyLinter { apiDef, ignores -> fail() }
        val unit = HttpApi(linter)
        val res = unit.route(apiReq, {})
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
        val apiReq = APIGatewayProxyRequestEvent().apply {
            path = "/violations"
            body = mapper.writeValueAsString(req)
        }
        val linter = DummyLinter { apiDef, ignores -> throw NullPointerException() }
        val unit = HttpApi(linter)
        val res = unit.route(apiReq, {})
        assertEquals(500, res.statusCode)
        assertThat(res.body).contains("internal server error")
    }

    @Test
    fun `download file positive`() {
        val content = "test swagger content"
        val url = startHttpServer("/api.yaml", 200, content)
        val apiReq = APIGatewayProxyRequestEvent().apply {
            path = "/violations"
            body = """{"api_definition_url": "$url"}"""
        }
        val linter = DummyLinter { apiDef, _ ->
            assertEquals(content, apiDef)
            LintingResponse("ok", emptyList(), emptyMap())
        }
        val unit = HttpApi(linter)
        val res = unit.route(apiReq, {})
        println(res)
        assertEquals(200, res.statusCode)
        assertEquals("ok", mapper.readTree(res.body)["message"].asText())
    }
    
    @Test
    fun `download file negative`() {
        val url = startHttpServer("/api.yaml", 500, "")
        val apiReq = APIGatewayProxyRequestEvent().apply {
            path = "/violations"
            body = """{"api_definition_url": "$url"}"""
        }
        val linter = DummyLinter { apiDef, _ -> fail() }
        val unit = HttpApi(linter)
        val res = unit.route(apiReq, {})
        println(res)
        assertEquals(400, res.statusCode)
        assertThat(res.body).contains("Failed to download")
    }

    private fun startHttpServer(path: String, code: Int, body: String): URL {
        val port = getRandomFreePort()
        val server = HttpServer.create(InetSocketAddress(port), 0)
        server.createContext("/api.yaml") {
            it.sendResponseHeaders(code, body.length.toLong())
            it.responseBody.apply {
                write(body.toByteArray())
                close()
            }
        }
        server.executor = null
        server.start()
        return URL("http://localhost:$port$path")
    }

    private fun getRandomFreePort(): Int =
        ServerSocket().use {
            it.bind(InetSocketAddress(0))
            it.localPort
        }
}

class DummyLinter(val f: (String, Set<String>) -> LintingResponse) : Linter {
    override fun doLint(apiDefinition: String, ignoreRules: Set<String>): LintingResponse =
        f(apiDefinition, ignoreRules)

    override val rules: List<Rule> get() = TODO()
}

