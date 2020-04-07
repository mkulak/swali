@file:Suppress("INTERFACE_WITH_SUPERCLASS", "OVERRIDING_FINAL_MEMBER", "RETURN_TYPE_MISMATCH_ON_OVERRIDE", "CONFLICTING_OVERLOADS", "EXTERNAL_DELEGATION")
package swagger_parser

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
import swagger_parser.SwaggerParser.`$Refs`
import swagger_parser.SwaggerParser.Options
import swagger_parser.SwaggerParser.ParserOptions
import swagger_parser.SwaggerParser.`T$1`
import swagger_parser.SwaggerParser.`T$2`
import swagger_parser.SwaggerParser.`T$3`
import swagger_parser.SwaggerParser.`T$4`
import swagger_parser.SwaggerParser.ResolverOptions
import swagger_parser.SwaggerParser.FileInfo
import openapi_types.OpenAPIV3.Document

typealias ApiCallback = (err: Error?, api: Document) -> Any

typealias RefsCallback = (err: Error?, `$refs`: `$Refs`) -> Any

external open class SwaggerParser {
    open var api: Document
    open var `$refs`: `$Refs`
    open fun validate(api: String, callback: ApiCallback)
    open fun validate(api: Document, callback: ApiCallback)
    open fun validate(api: String, options: Options, callback: ApiCallback)
    open fun validate(api: Document, options: Options, callback: ApiCallback)
    open fun validate(baseUrl: String, api: String, options: Options, callback: ApiCallback)
    open fun validate(baseUrl: String, api: Document, options: Options, callback: ApiCallback)
    open fun validate(api: String): Promise<Document>
    open fun validate(api: Document): Promise<Document>
    open fun validate(api: String, options: Options): Promise<Document>
    open fun validate(api: Document, options: Options): Promise<Document>
    open fun validate(baseUrl: String, api: String, options: Options): Promise<Document>
    open fun validate(baseUrl: String, api: Document, options: Options): Promise<Document>
    open fun dereference(api: String, callback: ApiCallback)
    open fun dereference(api: Document, callback: ApiCallback)
    open fun dereference(api: String, options: Options, callback: ApiCallback)
    open fun dereference(api: Document, options: Options, callback: ApiCallback)
    open fun dereference(baseUrl: String, api: String, options: Options, callback: ApiCallback)
    open fun dereference(baseUrl: String, api: Document, options: Options, callback: ApiCallback)
    open fun dereference(api: String): Promise<Document>
    open fun dereference(api: Document): Promise<Document>
    open fun dereference(api: String, options: Options): Promise<Document>
    open fun dereference(api: Document, options: Options): Promise<Document>
    open fun dereference(baseUrl: String, api: String, options: Options): Promise<Document>
    open fun dereference(baseUrl: String, api: Document, options: Options): Promise<Document>
    open fun bundle(api: String, callback: ApiCallback)
    open fun bundle(api: Document, callback: ApiCallback)
    open fun bundle(api: String, options: Options, callback: ApiCallback)
    open fun bundle(api: Document, options: Options, callback: ApiCallback)
    open fun bundle(baseUrl: String, api: String, options: Options, callback: ApiCallback)
    open fun bundle(baseUrl: String, api: Document, options: Options, callback: ApiCallback)
    open fun bundle(api: String): Promise<Document>
    open fun bundle(api: Document): Promise<Document>
    open fun bundle(api: String, options: Options): Promise<Document>
    open fun bundle(api: Document, options: Options): Promise<Document>
    open fun bundle(baseUrl: String, api: String, options: Options): Promise<Document>
    open fun bundle(baseUrl: String, api: Document, options: Options): Promise<Document>
    open fun parse(api: String, callback: ApiCallback)
    open fun parse(api: Document, callback: ApiCallback)
    open fun parse(api: String, options: Options, callback: ApiCallback)
    open fun parse(api: Document, options: Options, callback: ApiCallback)
    open fun parse(baseUrl: String, api: String, options: Options, callback: ApiCallback)
    open fun parse(baseUrl: String, api: Document, options: Options, callback: ApiCallback)
    open fun parse(api: String): Promise<Document>
    open fun parse(api: Document): Promise<Document>
    open fun parse(api: String, options: Options): Promise<Document>
    open fun parse(api: Document, options: Options): Promise<Document>
    open fun parse(baseUrl: String, api: String, options: Options): Promise<Document>
    open fun parse(baseUrl: String, api: Document, options: Options): Promise<Document>
    open fun resolve(api: String, callback: `RefsCallback`)
    open fun resolve(api: Document, callback: `RefsCallback`)
    open fun resolve(api: String, options: Options, callback: `RefsCallback`)
    open fun resolve(api: Document, options: Options, callback: `RefsCallback`)
    open fun resolve(baseUrl: String, api: String, options: Options, callback: `RefsCallback`)
    open fun resolve(baseUrl: String, api: Document, options: Options, callback: `RefsCallback`)
    open fun resolve(api: String): Promise<`$Refs`>
    open fun resolve(api: Document): Promise<`$Refs`>
    open fun resolve(api: String, options: Options): Promise<`$Refs`>
    open fun resolve(api: Document, options: Options): Promise<`$Refs`>
    open fun resolve(baseUrl: String, api: String, options: Options): Promise<`$Refs`>
    open fun resolve(baseUrl: String, api: Document, options: Options): Promise<`$Refs`>
    interface `T$1` {
        var json: dynamic /* ParserOptions | Boolean */
            get() = definedExternally
            set(value) = definedExternally
        var yaml: dynamic /* ParserOptions | Boolean */
            get() = definedExternally
            set(value) = definedExternally
        var text: dynamic /* ParserOptions | Boolean */
            get() = definedExternally
            set(value) = definedExternally
        @nativeGetter
        operator fun get(key: String): dynamic /* ParserOptions | Boolean | Nothing? */
        @nativeSetter
        operator fun set(key: String, value: ParserOptions)
        @nativeSetter
        operator fun set(key: String, value: Boolean)
        @nativeSetter
        operator fun set(key: String, value: Nothing?)
    }
    interface `T$2` {
        var external: Boolean?
            get() = definedExternally
            set(value) = definedExternally
        var file: dynamic /* ResolverOptionsPartial | Boolean */
            get() = definedExternally
            set(value) = definedExternally
        var http: dynamic /* HTTPResolverOptions | Boolean */
            get() = definedExternally
            set(value) = definedExternally
    }
    interface `T$3` {
        var circular: dynamic /* Boolean | 'ignore' */
            get() = definedExternally
            set(value) = definedExternally
    }
    interface `T$4` {
        var schema: Boolean?
            get() = definedExternally
            set(value) = definedExternally
        var spec: Boolean?
            get() = definedExternally
            set(value) = definedExternally
    }
    interface Options {
        var parse: `T$1`?
            get() = definedExternally
            set(value) = definedExternally
        var resolve: `T$2`?
            get() = definedExternally
            set(value) = definedExternally
        var dereference: `T$3`?
            get() = definedExternally
            set(value) = definedExternally
        var validate: `T$4`?
            get() = definedExternally
            set(value) = definedExternally
    }
    interface HTTPResolverOptions : ResolverOptions {
        var headers: Any?
            get() = definedExternally
            set(value) = definedExternally
        var timeout: Number?
            get() = definedExternally
            set(value) = definedExternally
        var redirects: Number?
            get() = definedExternally
            set(value) = definedExternally
        var withCredentials: Boolean?
            get() = definedExternally
            set(value) = definedExternally
    }
    interface ResolverOptions {
        var order: Number?
            get() = definedExternally
            set(value) = definedExternally
        var canRead: dynamic /* Boolean | RegExp | String | Array<String> | (file: FileInfo) -> Boolean */
            get() = definedExternally
            set(value) = definedExternally
        fun read(file: FileInfo, callback: (error: Error?, data: String?) -> Any = definedExternally): dynamic /* String | Buffer | Promise<dynamic /* String | Buffer */> */
    }
    interface ResolverOptionsPartial {
        var order: Number?
            get() = definedExternally
            set(value) = definedExternally
        var canRead: dynamic /* Boolean | RegExp | String | Array<String> | (file: FileInfo) -> Boolean */
            get() = definedExternally
            set(value) = definedExternally
        var read: ((file: FileInfo, callback: (error: Error?, data: String?) -> Any) -> dynamic)?
            get() = definedExternally
            set(value) = definedExternally
    }
    interface ParserOptions {
        var order: Number?
            get() = definedExternally
            set(value) = definedExternally
        var allowEmpty: Boolean?
            get() = definedExternally
            set(value) = definedExternally
        var canParse: dynamic /* Boolean | RegExp | String | Array<String> | (file: FileInfo) -> Boolean */
            get() = definedExternally
            set(value) = definedExternally
    }
    interface FileInfo {
        var url: String
        var extension: String
        var data: dynamic /* String | Buffer */
            get() = definedExternally
            set(value) = definedExternally
    }
    open class `$Refs` {
        open var circular: Boolean
        open fun paths(vararg types: String): Array<String>
        open fun values(vararg types: String): Json
        open fun exists(`$ref`: String): Boolean
        open fun get(`$ref`: String): Any
        open fun set(`$ref`: String, value: Any)
    }

    companion object {
        fun validate(api: String, callback: ApiCallback)
        fun validate(api: Document, callback: ApiCallback)
        fun validate(api: String, options: Options, callback: ApiCallback)
        fun validate(api: Document, options: Options, callback: ApiCallback)
        fun validate(baseUrl: String, api: String, options: Options, callback: ApiCallback)
        fun validate(baseUrl: String, api: Document, options: Options, callback: ApiCallback)
        fun validate(api: String): Promise<Document>
        fun validate(api: Document): Promise<Document>
        fun validate(api: String, options: Options): Promise<Document>
        fun validate(api: Document, options: Options): Promise<Document>
        fun validate(baseUrl: String, api: String, options: Options): Promise<Document>
        fun validate(baseUrl: String, api: Document, options: Options): Promise<Document>
        fun dereference(api: String, callback: ApiCallback)
        fun dereference(api: Document, callback: ApiCallback)
        fun dereference(api: String, options: Options, callback: ApiCallback)
        fun dereference(api: Document, options: Options, callback: ApiCallback)
        fun dereference(baseUrl: String, api: String, options: Options, callback: ApiCallback)
        fun dereference(baseUrl: String, api: Document, options: Options, callback: ApiCallback)
        fun dereference(api: String): Promise<Document>
        fun dereference(api: Document): Promise<Document>
        fun dereference(api: String, options: Options): Promise<Document>
        fun dereference(api: Document, options: Options): Promise<Document>
        fun dereference(baseUrl: String, api: String, options: Options): Promise<Document>
        fun dereference(baseUrl: String, api: Document, options: Options): Promise<Document>
        fun bundle(api: String, callback: ApiCallback)
        fun bundle(api: Document, callback: ApiCallback)
        fun bundle(api: String, options: Options, callback: ApiCallback)
        fun bundle(api: Document, options: Options, callback: ApiCallback)
        fun bundle(baseUrl: String, api: String, options: Options, callback: ApiCallback)
        fun bundle(baseUrl: String, api: Document, options: Options, callback: ApiCallback)
        fun bundle(api: String): Promise<Document>
        fun bundle(api: Document): Promise<Document>
        fun bundle(api: String, options: Options): Promise<Document>
        fun bundle(api: Document, options: Options): Promise<Document>
        fun bundle(baseUrl: String, api: String, options: Options): Promise<Document>
        fun bundle(baseUrl: String, api: Document, options: Options): Promise<Document>
        fun parse(api: String, callback: ApiCallback)
        fun parse(api: Document, callback: ApiCallback)
        fun parse(api: String, options: Options, callback: ApiCallback)
        fun parse(api: Document, options: Options, callback: ApiCallback)
        fun parse(baseUrl: String, api: String, options: Options, callback: ApiCallback)
        fun parse(baseUrl: String, api: Document, options: Options, callback: ApiCallback)
        fun parse(api: String): Promise<Document>
        fun parse(api: Document): Promise<Document>
        fun parse(api: String, options: Options): Promise<Document>
        fun parse(api: Document, options: Options): Promise<Document>
        fun parse(baseUrl: String, api: String, options: Options): Promise<Document>
        fun parse(baseUrl: String, api: Document, options: Options): Promise<Document>
        fun resolve(api: String, callback: `RefsCallback`)
        fun resolve(api: Document, callback: `RefsCallback`)
        fun resolve(api: String, options: Options, callback: `RefsCallback`)
        fun resolve(api: Document, options: Options, callback: `RefsCallback`)
        fun resolve(baseUrl: String, api: String, options: Options, callback: `RefsCallback`)
        fun resolve(baseUrl: String, api: Document, options: Options, callback: `RefsCallback`)
        fun resolve(api: String): Promise<`$Refs`>
        fun resolve(api: Document): Promise<`$Refs`>
        fun resolve(api: String, options: Options): Promise<`$Refs`>
        fun resolve(api: Document, options: Options): Promise<`$Refs`>
        fun resolve(baseUrl: String, api: String, options: Options): Promise<`$Refs`>
        fun resolve(baseUrl: String, api: Document, options: Options): Promise<`$Refs`>
    }
}
