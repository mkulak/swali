import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.asDeferred
import kotlinx.coroutines.asPromise
import kotlinx.coroutines.async
import kotlinx.coroutines.await
import kotlinx.coroutines.launch
import org.w3c.fetch.Request
import org.w3c.fetch.Response
import org.w3c.fetch.ResponseInit
import kotlin.js.Promise

fun main() {
    addEventListener("fetch") { event: FetchEvent ->
        Promise<Unit> { resolve, reject ->
            GlobalScope.async(Dispatchers.Unconfined) {
                event.respondWith(handleRequest(event.request))
                resolve(Unit)
            }
        }
    }
}

fun handleRequest(request: Request): Response {
    val foo = request.headers.get("Foo")
//    val body = request.text().await()
    val url = request.url
    val response = Response("Deferred Main $url $foo body", ResponseInit(headers = mapOf("Content-Type" to "text/html")))
    return response
}

