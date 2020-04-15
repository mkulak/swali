import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.await
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.w3c.fetch.Request
import org.w3c.fetch.Response
import org.w3c.fetch.ResponseInit
import swagger_parser.SwaggerParser
import kotlin.js.Promise

fun main() {
    addEventListener("fetch") { event: FetchEvent ->
        val promise = Promise<Response> { resolve, _ ->
            GlobalScope.launch(Dispatchers.Unconfined) {
                resolve(handleRequest(event.request))
            }
        }
        event.respondWith(promise)
    }
}

suspend fun handleRequest(request: Request): Response {
    val foo = request.headers.get("Foo")
    delay(20)
    val body = request.text().await()
    val response = try {
        val document = SwaggerParser.validate(body).await()
        "${document.info.title} ${document.info.version}"
    } catch (e: Exception) {
        e.toString()
    }
    val url = request.url
//    return Response("Response: $url $foo $body", ResponseInit(headers = mapOf("Content-Type" to "text/html")))
    return Response(response)
}

