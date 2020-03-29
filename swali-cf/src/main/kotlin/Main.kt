external fun addEventListener(type: String, f: (dynamic) -> Unit): String = definedExternally
external class Request
external class Response(body: dynamic, init: dynamic)

fun main() {
    addEventListener("fetch") { event: dynamic ->
        event.respondWith(handleRequest(event.request))
    }
}

fun handleRequest(request: dynamic): dynamic {
    val response = Response("Hello from Kotlin", mapOf("headers" to mapOf("Content-Type" to "text/html")))
    return response
}

