@file:JsModule("@cloudflare/workers-types")
@file:JsNonModule
@file:Suppress("INTERFACE_WITH_SUPERCLASS", "OVERRIDING_FINAL_MEMBER", "RETURN_TYPE_MISMATCH_ON_OVERRIDE", "CONFLICTING_OVERLOADS", "EXTERNAL_DELEGATION")
package cloudflare.workers_types

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
import KVValue

external interface `T$7` {
    var expiration: dynamic /* String | Number */
        get() = definedExternally
        set(value) = definedExternally
    var expirationTtl: dynamic /* String | Number */
        get() = definedExternally
        set(value) = definedExternally
}

external interface `T$8` {
    var prefix: String?
        get() = definedExternally
        set(value) = definedExternally
    var limit: Number?
        get() = definedExternally
        set(value) = definedExternally
    var cursor: String?
        get() = definedExternally
        set(value) = definedExternally
}

external interface `T$9` {
    var name: String
    var expiration: Number?
        get() = definedExternally
        set(value) = definedExternally
}

external interface `T$10` {
    var keys: Array<`T$9`>
    var list_complete: Boolean
    var cursor: String
}

external interface KVNamespace {
    fun get(key: String): KVValue<String>
    fun get(key: String, type: String /* 'text' */): KVValue<String>
    fun <ExpectedValue> get(key: String, type: String /* 'json' */): KVValue<ExpectedValue>
    fun get(key: String, type: String /* 'arrayBuffer' */): KVValue<ArrayBuffer>
    fun get(key: String, type: String /* 'stream' */): KVValue<ReadableStream>
    fun put(key: String, value: String, options: `T$7` = definedExternally): Promise<Unit>
    fun put(key: String, value: ReadableStream, options: `T$7` = definedExternally): Promise<Unit>
    fun put(key: String, value: ArrayBuffer, options: `T$7` = definedExternally): Promise<Unit>
    fun put(key: String, value: FormData, options: `T$7` = definedExternally): Promise<Unit>
    fun delete(key: String): Promise<Unit>
    fun list(options: `T$8`): Promise<`T$10`>
}

external class ReadableStream
