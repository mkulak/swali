@file:JsModule("openapi-types")
@file:JsQualifier("openapi_types.OpenAPI")
@file:Suppress("INTERFACE_WITH_SUPERCLASS", "OVERRIDING_FINAL_MEMBER", "RETURN_TYPE_MISMATCH_ON_OVERRIDE", "CONFLICTING_OVERLOADS", "EXTERNAL_DELEGATION")
package openapi_types.OpenAPI

import kotlin.js.*
import kotlin.js.Json
import org.khronos.webgl.*
import org.w3c.dom.*
import org.w3c.dom.events.*
import org.w3c.dom.parsing.*
import org.w3c.dom.svg.*
import org.w3c.dom.url.*
import org.w3c.fetch.*
import org.w3c.files.*
import org.w3c.notifications.*
import org.w3c.performance.*
import org.w3c.workers.*
import org.w3c.xhr.*

external interface Request {
    var body: Any?
        get() = definedExternally
        set(value) = definedExternally
    var headers: Any?
        get() = definedExternally
        set(value) = definedExternally
    var params: Any?
        get() = definedExternally
        set(value) = definedExternally
    var query: Any?
        get() = definedExternally
        set(value) = definedExternally
}
