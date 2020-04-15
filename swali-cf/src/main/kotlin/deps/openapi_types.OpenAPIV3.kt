@file:JsModule("openapi-types")
@file:Suppress("INTERFACE_WITH_SUPERCLASS", "OVERRIDING_FINAL_MEMBER", "RETURN_TYPE_MISMATCH_ON_OVERRIDE", "CONFLICTING_OVERLOADS", "EXTERNAL_DELEGATION")
package openapi_types.OpenAPIV3

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
    var openapi: String
    var info: InfoObject
    var servers: Array<ServerObject>?
        get() = definedExternally
        set(value) = definedExternally
    var paths: PathsObject
    var components: ComponentsObject?
        get() = definedExternally
        set(value) = definedExternally
    var security: Array<SecurityRequirementObject>?
        get() = definedExternally
        set(value) = definedExternally
    var tags: Array<TagObject>?
        get() = definedExternally
        set(value) = definedExternally
    var externalDocs: ExternalDocumentationObject?
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

external interface `T$0` {
    @nativeGetter
    operator fun get(variable: String): ServerVariableObject?
    @nativeSetter
    operator fun set(variable: String, value: ServerVariableObject)
}

external interface ServerObject {
    var url: String
    var description: String?
        get() = definedExternally
        set(value) = definedExternally
    var variables: `T$0`?
        get() = definedExternally
        set(value) = definedExternally
}

external interface ServerVariableObject {
    var enum: Array<String>?
        get() = definedExternally
        set(value) = definedExternally
    var default: String
    var description: String?
        get() = definedExternally
        set(value) = definedExternally
}

external interface PathsObject {
    @nativeGetter
    operator fun get(pattern: String): PathItemObject?
    @nativeSetter
    operator fun set(pattern: String, value: PathItemObject)
}

external interface PathItemObject {
    var `$ref`: String?
        get() = definedExternally
        set(value) = definedExternally
    var summary: String?
        get() = definedExternally
        set(value) = definedExternally
    var description: String?
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
    var trace: OperationObject?
        get() = definedExternally
        set(value) = definedExternally
    var servers: Array<ServerObject>?
        get() = definedExternally
        set(value) = definedExternally
    var parameters: Array<dynamic /* ReferenceObject | ParameterObject */>?
        get() = definedExternally
        set(value) = definedExternally
}

external interface `T$1` {
    @nativeGetter
    operator fun get(callback: String): dynamic /* ReferenceObject | CallbackObject */
    @nativeSetter
    operator fun set(callback: String, value: ReferenceObject)
    @nativeSetter
    operator fun set(callback: String, value: CallbackObject)
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
    var parameters: Array<dynamic /* ReferenceObject | ParameterObject */>?
        get() = definedExternally
        set(value) = definedExternally
    var requestBody: dynamic /* ReferenceObject | RequestBodyObject */
        get() = definedExternally
        set(value) = definedExternally
    var responses: ResponsesObject?
        get() = definedExternally
        set(value) = definedExternally
    var callbacks: `T$1`?
        get() = definedExternally
        set(value) = definedExternally
    var deprecated: Boolean?
        get() = definedExternally
        set(value) = definedExternally
    var security: Array<SecurityRequirementObject>?
        get() = definedExternally
        set(value) = definedExternally
    var servers: Array<ServerObject>?
        get() = definedExternally
        set(value) = definedExternally
}

external interface ExternalDocumentationObject {
    var description: String?
        get() = definedExternally
        set(value) = definedExternally
    var url: String
}

external interface ParameterObject : ParameterBaseObject {
    var name: String
    var `in`: String
}

external interface HeaderObject : ParameterBaseObject

external interface `T$2` {
    @nativeGetter
    operator fun get(media: String): dynamic /* ReferenceObject | ExampleObject */
    @nativeSetter
    operator fun set(media: String, value: ReferenceObject)
    @nativeSetter
    operator fun set(media: String, value: ExampleObject)
}

external interface `T$3` {
    @nativeGetter
    operator fun get(media: String): MediaTypeObject?
    @nativeSetter
    operator fun set(media: String, value: MediaTypeObject)
}

external interface ParameterBaseObject {
    var description: String?
        get() = definedExternally
        set(value) = definedExternally
    var required: Boolean?
        get() = definedExternally
        set(value) = definedExternally
    var deprecated: Boolean?
        get() = definedExternally
        set(value) = definedExternally
    var allowEmptyValue: Boolean?
        get() = definedExternally
        set(value) = definedExternally
    var style: String?
        get() = definedExternally
        set(value) = definedExternally
    var explode: Boolean?
        get() = definedExternally
        set(value) = definedExternally
    var allowReserved: Boolean?
        get() = definedExternally
        set(value) = definedExternally
    var schema: dynamic /* ReferenceObject | ArraySchemaObject | NonArraySchemaObject */
        get() = definedExternally
        set(value) = definedExternally
    var example: Any?
        get() = definedExternally
        set(value) = definedExternally
    var examples: `T$2`?
        get() = definedExternally
        set(value) = definedExternally
    var content: `T$3`?
        get() = definedExternally
        set(value) = definedExternally
}

external interface ArraySchemaObject : BaseSchemaObject {
    var type: String /* 'array' */
    var items: dynamic /* ReferenceObject | ArraySchemaObject | NonArraySchemaObject */
        get() = definedExternally
        set(value) = definedExternally
}

external interface NonArraySchemaObject : BaseSchemaObject {
    var type: String /* 'null' | 'boolean' | 'object' | 'number' | 'string' | 'integer' */
}

external interface `T$4` {
    @nativeGetter
    operator fun get(name: String): dynamic /* ReferenceObject | ArraySchemaObject | NonArraySchemaObject */
    @nativeSetter
    operator fun set(name: String, value: ReferenceObject)
    @nativeSetter
    operator fun set(name: String, value: ArraySchemaObject)
    @nativeSetter
    operator fun set(name: String, value: NonArraySchemaObject)
}

external interface BaseSchemaObject {
    var title: String?
        get() = definedExternally
        set(value) = definedExternally
    var description: String?
        get() = definedExternally
        set(value) = definedExternally
    var format: String?
        get() = definedExternally
        set(value) = definedExternally
    var default: Any?
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
    var additionalProperties: dynamic /* Boolean | ReferenceObject | ArraySchemaObject | NonArraySchemaObject */
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
    var enum: Array<Any>?
        get() = definedExternally
        set(value) = definedExternally
    var properties: `T$4`?
        get() = definedExternally
        set(value) = definedExternally
    var allOf: Array<dynamic /* ReferenceObject | ArraySchemaObject | NonArraySchemaObject */>?
        get() = definedExternally
        set(value) = definedExternally
    var oneOf: Array<dynamic /* ReferenceObject | ArraySchemaObject | NonArraySchemaObject */>?
        get() = definedExternally
        set(value) = definedExternally
    var anyOf: Array<dynamic /* ReferenceObject | ArraySchemaObject | NonArraySchemaObject */>?
        get() = definedExternally
        set(value) = definedExternally
    var not: dynamic /* ReferenceObject | ArraySchemaObject | NonArraySchemaObject */
        get() = definedExternally
        set(value) = definedExternally
    var nullable: Boolean?
        get() = definedExternally
        set(value) = definedExternally
    var discriminator: DiscriminatorObject?
        get() = definedExternally
        set(value) = definedExternally
    var readOnly: Boolean?
        get() = definedExternally
        set(value) = definedExternally
    var writeOnly: Boolean?
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
    var deprecated: Boolean?
        get() = definedExternally
        set(value) = definedExternally
}

external interface `T$5` {
    @nativeGetter
    operator fun get(value: String): String?
    @nativeSetter
    operator fun set(key: String, value: String)
}

external interface DiscriminatorObject {
    var propertyName: String
    var mapping: `T$5`?
        get() = definedExternally
        set(value) = definedExternally
}

external interface XMLObject {
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

external interface ReferenceObject {
    var `$ref`: String
}

external interface ExampleObject {
    var summary: String?
        get() = definedExternally
        set(value) = definedExternally
    var description: String?
        get() = definedExternally
        set(value) = definedExternally
    var value: Any?
        get() = definedExternally
        set(value) = definedExternally
    var externalValue: String?
        get() = definedExternally
        set(value) = definedExternally
}

external interface `T$6` {
    @nativeGetter
    operator fun get(media: String): EncodingObject?
    @nativeSetter
    operator fun set(media: String, value: EncodingObject)
}

external interface MediaTypeObject {
    var schema: dynamic /* ReferenceObject | ArraySchemaObject | NonArraySchemaObject */
        get() = definedExternally
        set(value) = definedExternally
    var example: Any?
        get() = definedExternally
        set(value) = definedExternally
    var examples: `T$2`?
        get() = definedExternally
        set(value) = definedExternally
    var encoding: `T$6`?
        get() = definedExternally
        set(value) = definedExternally
}

external interface `T$7` {
    @nativeGetter
    operator fun get(header: String): dynamic /* ReferenceObject | HeaderObject */
    @nativeSetter
    operator fun set(header: String, value: ReferenceObject)
    @nativeSetter
    operator fun set(header: String, value: HeaderObject)
}

external interface EncodingObject {
    var contentType: String?
        get() = definedExternally
        set(value) = definedExternally
    var headers: `T$7`?
        get() = definedExternally
        set(value) = definedExternally
    var style: String?
        get() = definedExternally
        set(value) = definedExternally
    var explode: Boolean?
        get() = definedExternally
        set(value) = definedExternally
    var allowReserved: Boolean?
        get() = definedExternally
        set(value) = definedExternally
}

external interface RequestBodyObject {
    var description: String?
        get() = definedExternally
        set(value) = definedExternally
    var content: `T$3`
    var required: Boolean?
        get() = definedExternally
        set(value) = definedExternally
}

external interface ResponsesObject {
    @nativeGetter
    operator fun get(code: String): dynamic /* ReferenceObject | ResponseObject */
    @nativeSetter
    operator fun set(code: String, value: ReferenceObject)
    @nativeSetter
    operator fun set(code: String, value: ResponseObject)
}

external interface `T$8` {
    @nativeGetter
    operator fun get(link: String): dynamic /* ReferenceObject | LinkObject */
    @nativeSetter
    operator fun set(link: String, value: ReferenceObject)
    @nativeSetter
    operator fun set(link: String, value: LinkObject)
}

external interface ResponseObject {
    var description: String
    var headers: `T$7`?
        get() = definedExternally
        set(value) = definedExternally
    var content: `T$3`?
        get() = definedExternally
        set(value) = definedExternally
    var links: `T$8`?
        get() = definedExternally
        set(value) = definedExternally
}

external interface LinkObject {
    var operationRef: String?
        get() = definedExternally
        set(value) = definedExternally
    var operationId: String?
        get() = definedExternally
        set(value) = definedExternally
    var parameters: Json?
        get() = definedExternally
        set(value) = definedExternally
    var requestBody: Any?
        get() = definedExternally
        set(value) = definedExternally
    var description: String?
        get() = definedExternally
        set(value) = definedExternally
    var server: ServerObject?
        get() = definedExternally
        set(value) = definedExternally
}

external interface CallbackObject {
    @nativeGetter
    operator fun get(url: String): PathItemObject?
    @nativeSetter
    operator fun set(url: String, value: PathItemObject)
}

external interface SecurityRequirementObject {
    @nativeGetter
    operator fun get(name: String): Array<String>?
    @nativeSetter
    operator fun set(name: String, value: Array<String>)
}

external interface `T$9` {
    @nativeGetter
    operator fun get(key: String): dynamic /* ReferenceObject | ResponseObject */
    @nativeSetter
    operator fun set(key: String, value: ReferenceObject)
    @nativeSetter
    operator fun set(key: String, value: ResponseObject)
}

external interface `T$10` {
    @nativeGetter
    operator fun get(key: String): dynamic /* ReferenceObject | ParameterObject */
    @nativeSetter
    operator fun set(key: String, value: ReferenceObject)
    @nativeSetter
    operator fun set(key: String, value: ParameterObject)
}

external interface `T$11` {
    @nativeGetter
    operator fun get(key: String): dynamic /* ReferenceObject | RequestBodyObject */
    @nativeSetter
    operator fun set(key: String, value: ReferenceObject)
    @nativeSetter
    operator fun set(key: String, value: RequestBodyObject)
}

external interface `T$12` {
    @nativeGetter
    operator fun get(key: String): dynamic /* ReferenceObject | HttpSecurityScheme | ApiKeySecurityScheme | OAuth2SecurityScheme | OpenIdSecurityScheme */
    @nativeSetter
    operator fun set(key: String, value: ReferenceObject)
    @nativeSetter
    operator fun set(key: String, value: HttpSecurityScheme)
    @nativeSetter
    operator fun set(key: String, value: ApiKeySecurityScheme)
    @nativeSetter
    operator fun set(key: String, value: OAuth2SecurityScheme)
    @nativeSetter
    operator fun set(key: String, value: OpenIdSecurityScheme)
}

external interface ComponentsObject {
    var schemas: `T$4`?
        get() = definedExternally
        set(value) = definedExternally
    var responses: `T$9`?
        get() = definedExternally
        set(value) = definedExternally
    var parameters: `T$10`?
        get() = definedExternally
        set(value) = definedExternally
    var examples: `T$2`?
        get() = definedExternally
        set(value) = definedExternally
    var requestBodies: `T$11`?
        get() = definedExternally
        set(value) = definedExternally
    var headers: `T$7`?
        get() = definedExternally
        set(value) = definedExternally
    var securitySchemes: `T$12`?
        get() = definedExternally
        set(value) = definedExternally
    var links: `T$8`?
        get() = definedExternally
        set(value) = definedExternally
    var callbacks: `T$1`?
        get() = definedExternally
        set(value) = definedExternally
}

external interface HttpSecurityScheme {
    var type: String /* 'http' */
    var description: String?
        get() = definedExternally
        set(value) = definedExternally
    var scheme: String
    var bearerFormat: String?
        get() = definedExternally
        set(value) = definedExternally
}

external interface ApiKeySecurityScheme {
    var type: String /* 'apiKey' */
    var description: String?
        get() = definedExternally
        set(value) = definedExternally
    var name: String
    var `in`: String
}

external interface `T$13` {
    var authorizationUrl: String
    var refreshUrl: String?
        get() = definedExternally
        set(value) = definedExternally
    var scopes: `T$5`
}

external interface `T$14` {
    var tokenUrl: String
    var refreshUrl: String?
        get() = definedExternally
        set(value) = definedExternally
    var scopes: `T$5`
}

external interface `T$15` {
    var authorizationUrl: String
    var tokenUrl: String
    var refreshUrl: String?
        get() = definedExternally
        set(value) = definedExternally
    var scopes: `T$5`
}

external interface `T$16` {
    var implicit: `T$13`?
        get() = definedExternally
        set(value) = definedExternally
    var password: `T$14`?
        get() = definedExternally
        set(value) = definedExternally
    var clientCredentials: `T$14`?
        get() = definedExternally
        set(value) = definedExternally
    var authorizationCode: `T$15`?
        get() = definedExternally
        set(value) = definedExternally
}

external interface OAuth2SecurityScheme {
    var type: String /* 'oauth2' */
    var flows: `T$16`
}

external interface OpenIdSecurityScheme {
    var type: String /* 'openIdConnect' */
    var description: String?
        get() = definedExternally
        set(value) = definedExternally
    var openIdConnectUrl: String
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
