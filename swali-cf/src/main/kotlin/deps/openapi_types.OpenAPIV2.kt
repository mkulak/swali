@file:JsModule("openapi-types")
@file:JsQualifier("openapi_types.OpenAPIV2")
@file:Suppress("INTERFACE_WITH_SUPERCLASS", "OVERRIDING_FINAL_MEMBER", "RETURN_TYPE_MISMATCH_ON_OVERRIDE", "CONFLICTING_OVERLOADS", "EXTERNAL_DELEGATION")
package openapi_types.OpenAPIV2

import openapi_types.`T$18`
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

external interface Document {
    operator fun get(key: String): Any?
    operator fun set(key: String, value: Any?)
    var basePath: String?
        get() = definedExternally
        set(value) = definedExternally
    var consumes: MimeTypes?
        get() = definedExternally
        set(value) = definedExternally
    var definitions: DefinitionsObject?
        get() = definedExternally
        set(value) = definedExternally
    var externalDocs: ExternalDocumentationObject?
        get() = definedExternally
        set(value) = definedExternally
    var host: String?
        get() = definedExternally
        set(value) = definedExternally
    var info: InfoObject
    var parameters: ParametersDefinitionsObject?
        get() = definedExternally
        set(value) = definedExternally
    var paths: PathsObject
    var produces: MimeTypes?
        get() = definedExternally
        set(value) = definedExternally
    var responses: ResponsesDefinitionsObject?
        get() = definedExternally
        set(value) = definedExternally
    var schemes: Array<String>?
        get() = definedExternally
        set(value) = definedExternally
    var security: Array<SecurityRequirementObject>?
        get() = definedExternally
        set(value) = definedExternally
    var securityDefinitions: SecurityDefinitionsObject?
        get() = definedExternally
        set(value) = definedExternally
    var swagger: String
    var tags: Array<TagObject>?
        get() = definedExternally
        set(value) = definedExternally
}

external interface TagObject {
    var name: String
    var description: String?
        get() = definedExternally
        set(value) = definedExternally
    var externalDocs: ExternalDocumentationObject?
        get() = definedExternally
        set(value) = definedExternally
}

external interface SecuritySchemeObjectBase {
    var type: String /* 'basic' | 'apiKey' | 'oauth2' */
    var description: String?
        get() = definedExternally
        set(value) = definedExternally
}

external interface SecuritySchemeBasic : SecuritySchemeObjectBase {
    override var type: String /* 'basic' */
}

external interface SecuritySchemeApiKey : SecuritySchemeObjectBase {
    override var type: String /* 'apiKey' */
    var name: String
    var `in`: String
}

external interface ScopesObject {
    @nativeGetter
    operator fun get(index: String): Any?
    @nativeSetter
    operator fun set(index: String, value: Any)
}

external interface SecuritySchemeOauth2Base : SecuritySchemeObjectBase {
    override var type: String /* 'oauth2' */
    var flow: String /* 'implicit' | 'password' | 'application' | 'accessCode' */
    var scopes: ScopesObject
}

external interface SecuritySchemeOauth2Implicit : SecuritySchemeOauth2Base {
    override var flow: String /* 'implicit' */
    var authorizationUrl: String
}

external interface SecuritySchemeOauth2AccessCode : SecuritySchemeOauth2Base {
    override var flow: String /* 'accessCode' */
    var authorizationUrl: String
    var tokenUrl: String
}

external interface SecuritySchemeOauth2Password : SecuritySchemeOauth2Base {
    override var flow: String /* 'password' */
    var tokenUrl: String
}

external interface SecuritySchemeOauth2Application : SecuritySchemeOauth2Base {
    override var flow: String /* 'application' */
    var tokenUrl: String
}

external interface SecurityDefinitionsObject {
    @nativeGetter
    operator fun get(index: String): dynamic /* SecuritySchemeBasic | SecuritySchemeApiKey | SecuritySchemeOauth2Implicit | SecuritySchemeOauth2AccessCode | SecuritySchemeOauth2Password | SecuritySchemeOauth2Application */
    @nativeSetter
    operator fun set(index: String, value: SecuritySchemeBasic)
    @nativeSetter
    operator fun set(index: String, value: SecuritySchemeApiKey)
    @nativeSetter
    operator fun set(index: String, value: SecuritySchemeOauth2Implicit)
    @nativeSetter
    operator fun set(index: String, value: SecuritySchemeOauth2AccessCode)
    @nativeSetter
    operator fun set(index: String, value: SecuritySchemeOauth2Password)
    @nativeSetter
    operator fun set(index: String, value: SecuritySchemeOauth2Application)
}

external interface SecurityRequirementObject {
    @nativeGetter
    operator fun get(index: String): Array<String>?
    @nativeSetter
    operator fun set(index: String, value: Array<String>)
}

external interface ReferenceObject {
    var `$ref`: String
}

external interface ResponsesDefinitionsObject {
    @nativeGetter
    operator fun get(index: String): ResponseObject?
    @nativeSetter
    operator fun set(index: String, value: ResponseObject)
}

external interface ResponseObject {
    var description: String
    var schema: dynamic /* SchemaObject | ReferenceObject */
        get() = definedExternally
        set(value) = definedExternally
    var headers: HeadersObject?
        get() = definedExternally
        set(value) = definedExternally
    var examples: ExampleObject?
        get() = definedExternally
        set(value) = definedExternally
}

external interface HeadersObject {
    @nativeGetter
    operator fun get(index: String): HeaderObject?
    @nativeSetter
    operator fun set(index: String, value: HeaderObject)
}

external interface HeaderObject : ItemsObject

external interface ExampleObject {
    @nativeGetter
    operator fun get(index: String): Any?
    @nativeSetter
    operator fun set(index: String, value: Any)
}

external interface OperationObject {
    var tags: Array<String>?
        get() = definedExternally
        set(value) = definedExternally
    var summary: String?
        get() = definedExternally
        set(value) = definedExternally
    var description: String?
        get() = definedExternally
        set(value) = definedExternally
    var externalDocs: ExternalDocumentationObject?
        get() = definedExternally
        set(value) = definedExternally
    var operationId: String?
        get() = definedExternally
        set(value) = definedExternally
    var consumes: MimeTypes?
        get() = definedExternally
        set(value) = definedExternally
    var produces: MimeTypes?
        get() = definedExternally
        set(value) = definedExternally
    var parameters: Parameters?
        get() = definedExternally
        set(value) = definedExternally
    var responses: ResponsesObject
    var schemes: Array<String>?
        get() = definedExternally
        set(value) = definedExternally
    var deprecated: Boolean?
        get() = definedExternally
        set(value) = definedExternally
    var security: Array<SecurityRequirementObject>?
        get() = definedExternally
        set(value) = definedExternally
    @nativeGetter
    operator fun get(index: String): Any?
    @nativeSetter
    operator fun set(index: String, value: Any)
}

external interface ResponsesObject {
    @nativeGetter
    operator fun get(index: String): dynamic /* ResponseObject | ReferenceObject | Any */
    @nativeSetter
    operator fun set(index: String, value: ResponseObject)
    @nativeSetter
    operator fun set(index: String, value: ReferenceObject)
    @nativeSetter
    operator fun set(index: String, value: Any)
    var default: dynamic /* ResponseObject | ReferenceObject */
        get() = definedExternally
        set(value) = definedExternally
}

external interface InBodyParameterObject : ParameterObject {
    var schema: dynamic /* SchemaObject | ReferenceObject */
        get() = definedExternally
        set(value) = definedExternally
}

external interface GeneralParameterObject : ParameterObject, ItemsObject {
    var allowEmptyValue: Boolean?
        get() = definedExternally
        set(value) = definedExternally
}

external interface PathItemObject {
    var `$ref`: String?
        get() = definedExternally
        set(value) = definedExternally
    var get: OperationObject?
        get() = definedExternally
        set(value) = definedExternally
    var put: OperationObject?
        get() = definedExternally
        set(value) = definedExternally
    var post: OperationObject?
        get() = definedExternally
        set(value) = definedExternally
    var del: OperationObject?
        get() = definedExternally
        set(value) = definedExternally
    var delete: OperationObject?
        get() = definedExternally
        set(value) = definedExternally
    var options: OperationObject?
        get() = definedExternally
        set(value) = definedExternally
    var head: OperationObject?
        get() = definedExternally
        set(value) = definedExternally
    var patch: OperationObject?
        get() = definedExternally
        set(value) = definedExternally
    var parameters: Parameters?
        get() = definedExternally
        set(value) = definedExternally
}

external interface PathsObject {
    @nativeGetter
    operator fun get(index: String): dynamic /* PathItemObject | Any */
    @nativeSetter
    operator fun set(index: String, value: PathItemObject)
    @nativeSetter
    operator fun set(index: String, value: Any)
}

external interface ParametersDefinitionsObject {
    @nativeGetter
    operator fun get(index: String): ParameterObject?
    @nativeSetter
    operator fun set(index: String, value: ParameterObject)
}

external interface ParameterObject {
    var name: String
    var `in`: String
    var description: String?
        get() = definedExternally
        set(value) = definedExternally
    var required: Boolean?
        get() = definedExternally
        set(value) = definedExternally
    @nativeGetter
    operator fun get(index: String): Any?
    @nativeSetter
    operator fun set(index: String, value: Any)
}

external interface DefinitionsObject {
    @nativeGetter
    operator fun get(index: String): SchemaObject?
    @nativeSetter
    operator fun set(index: String, value: SchemaObject)
}

external interface `T$17` {
    @nativeGetter
    operator fun get(name: String): SchemaObject?
    @nativeSetter
    operator fun set(name: String, value: SchemaObject)
}

external interface SchemaObject : openapi_types.IJsonSchema {
    @nativeGetter
    operator fun get(index: String): Any?
    @nativeSetter
    operator fun set(index: String, value: Any)
    var discriminator: String?
        get() = definedExternally
        set(value) = definedExternally
    var readOnly: Boolean?
        get() = definedExternally
        set(value) = definedExternally
    var xml: XMLObject?
        get() = definedExternally
        set(value) = definedExternally
    var externalDocs: ExternalDocumentationObject?
        get() = definedExternally
        set(value) = definedExternally
    var example: Any?
        get() = definedExternally
        set(value) = definedExternally
    var default: Any?
        get() = definedExternally
        set(value) = definedExternally
    override var items: ItemsObject?
        get() = definedExternally
        set(value) = definedExternally
    override var properties: `T$18`?
        get() = definedExternally
        set(value) = definedExternally
}

external interface ExternalDocumentationObject {
    @nativeGetter
    operator fun get(index: String): Any?
    @nativeSetter
    operator fun set(index: String, value: Any)
    var description: String?
        get() = definedExternally
        set(value) = definedExternally
    var url: String
}

external interface ItemsObject {
    var type: String
    var format: String?
        get() = definedExternally
        set(value) = definedExternally
    var items: ItemsObject?
        get() = definedExternally
        set(value) = definedExternally
    var collectionFormat: String?
        get() = definedExternally
        set(value) = definedExternally
    var default: Any?
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
    var maxItems: Number?
        get() = definedExternally
        set(value) = definedExternally
    var minItems: Number?
        get() = definedExternally
        set(value) = definedExternally
    var uniqueItems: Boolean?
        get() = definedExternally
        set(value) = definedExternally
    var enum: Array<Any>?
        get() = definedExternally
        set(value) = definedExternally
    var multipleOf: Number?
        get() = definedExternally
        set(value) = definedExternally
    var `$ref`: String?
        get() = definedExternally
        set(value) = definedExternally
}

external interface XMLObject {
    @nativeGetter
    operator fun get(index: String): Any?
    @nativeSetter
    operator fun set(index: String, value: Any)
    var name: String?
        get() = definedExternally
        set(value) = definedExternally
    var namespace: String?
        get() = definedExternally
        set(value) = definedExternally
    var prefix: String?
        get() = definedExternally
        set(value) = definedExternally
    var attribute: Boolean?
        get() = definedExternally
        set(value) = definedExternally
    var wrapped: Boolean?
        get() = definedExternally
        set(value) = definedExternally
}

external interface InfoObject {
    var title: String
    var description: String?
        get() = definedExternally
        set(value) = definedExternally
    var termsOfService: String?
        get() = definedExternally
        set(value) = definedExternally
    var contact: ContactObject?
        get() = definedExternally
        set(value) = definedExternally
    var license: LicenseObject?
        get() = definedExternally
        set(value) = definedExternally
    var version: String
}

external interface ContactObject {
    var name: String?
        get() = definedExternally
        set(value) = definedExternally
    var url: String?
        get() = definedExternally
        set(value) = definedExternally
    var email: String?
        get() = definedExternally
        set(value) = definedExternally
}

external interface LicenseObject {
    var name: String
    var url: String?
        get() = definedExternally
        set(value) = definedExternally
}
