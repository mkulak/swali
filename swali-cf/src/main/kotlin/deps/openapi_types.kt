@file:Suppress("INTERFACE_WITH_SUPERCLASS", "OVERRIDING_FINAL_MEMBER", "RETURN_TYPE_MISMATCH_ON_OVERRIDE", "CONFLICTING_OVERLOADS", "EXTERNAL_DELEGATION")
package openapi_types

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

external interface `T$18` {
    @nativeGetter
    operator fun get(name: String): IJsonSchema?
    @nativeSetter
    operator fun set(name: String, value: IJsonSchema)
}

external interface `T$19` {
    @nativeGetter
    operator fun get(name: String): dynamic /* IJsonSchema | Array<String> */
    @nativeSetter
    operator fun set(name: String, value: IJsonSchema)
    @nativeSetter
    operator fun set(name: String, value: Array<String>)
}

external interface IJsonSchema {
    var id: String?
        get() = definedExternally
        set(value) = definedExternally
    var `$schema`: String?
        get() = definedExternally
        set(value) = definedExternally
    var title: String?
        get() = definedExternally
        set(value) = definedExternally
    var description: String?
        get() = definedExternally
        set(value) = definedExternally
    var multipleOf: Number?
        get() = definedExternally
        set(value) = definedExternally
    var maximum: Number?
        get() = definedExternally
        set(value) = definedExternally
    var exclusiveMaximum: Boolean?
        get() = definedExternally
        set(value) = definedExternally
    var minimum: Number?
        get() = definedExternally
        set(value) = definedExternally
    var exclusiveMinimum: Boolean?
        get() = definedExternally
        set(value) = definedExternally
    var maxLength: Number?
        get() = definedExternally
        set(value) = definedExternally
    var minLength: Number?
        get() = definedExternally
        set(value) = definedExternally
    var pattern: String?
        get() = definedExternally
        set(value) = definedExternally
    var additionalItems: dynamic /* Boolean | IJsonSchema */
        get() = definedExternally
        set(value) = definedExternally
    var items: dynamic /* IJsonSchema | Array<IJsonSchema> */
        get() = definedExternally
        set(value) = definedExternally
    var maxItems: Number?
        get() = definedExternally
        set(value) = definedExternally
    var minItems: Number?
        get() = definedExternally
        set(value) = definedExternally
    var uniqueItems: Boolean?
        get() = definedExternally
        set(value) = definedExternally
    var maxProperties: Number?
        get() = definedExternally
        set(value) = definedExternally
    var minProperties: Number?
        get() = definedExternally
        set(value) = definedExternally
    var required: Array<String>?
        get() = definedExternally
        set(value) = definedExternally
    var additionalProperties: dynamic /* Boolean | IJsonSchema */
        get() = definedExternally
        set(value) = definedExternally
    var definitions: `T$18`?
        get() = definedExternally
        set(value) = definedExternally
    var properties: `T$18`?
        get() = definedExternally
        set(value) = definedExternally
    var patternProperties: `T$18`?
        get() = definedExternally
        set(value) = definedExternally
    var dependencies: `T$19`?
        get() = definedExternally
        set(value) = definedExternally
    var enum: Array<Any>?
        get() = definedExternally
        set(value) = definedExternally
    var type: dynamic /* String | Array<String> */
        get() = definedExternally
        set(value) = definedExternally
    var allOf: Array<IJsonSchema>?
        get() = definedExternally
        set(value) = definedExternally
    var anyOf: Array<IJsonSchema>?
        get() = definedExternally
        set(value) = definedExternally
    var oneOf: Array<IJsonSchema>?
        get() = definedExternally
        set(value) = definedExternally
    var not: IJsonSchema?
        get() = definedExternally
        set(value) = definedExternally
}