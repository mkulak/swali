@file:Suppress("INTERFACE_WITH_SUPERCLASS", "OVERRIDING_FINAL_MEMBER", "RETURN_TYPE_MISMATCH_ON_OVERRIDE", "CONFLICTING_OVERLOADS", "EXTERNAL_DELEGATION")

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
import TsStdLib_Iterator

external interface FetchEvent {
    var passThroughOnException: () -> Unit
    var request: Request
    fun respondWith(f: Response)
    fun respondWith(promise: Promise<Response>)
}

external interface `T$0` {
    var x: Number
    var y: Number
}

external interface `T$1` {
    var width: Number?
        get() = definedExternally
        set(value) = definedExternally
    var height: Number?
        get() = definedExternally
        set(value) = definedExternally
    var fit: String /* 'scale-down' | 'contain' | 'cover' */
    var gravity: dynamic /* 'left' | 'right' | 'top' | 'bottom' | 'center' | `T$0` */
        get() = definedExternally
        set(value) = definedExternally
    var quality: Number?
        get() = definedExternally
        set(value) = definedExternally
    var format: String /* 'webp' | 'json' */
}

external interface `T$2` {
    var javascript: Boolean?
        get() = definedExternally
        set(value) = definedExternally
    var css: Boolean?
        get() = definedExternally
        set(value) = definedExternally
    var html: Boolean?
        get() = definedExternally
        set(value) = definedExternally
}

external interface `T$3` {
    var cacheEverything: Boolean?
        get() = definedExternally
        set(value) = definedExternally
    var scrapeShield: Boolean?
        get() = definedExternally
        set(value) = definedExternally
    var apps: Boolean?
        get() = definedExternally
        set(value) = definedExternally
    var image: `T$1`?
        get() = definedExternally
        set(value) = definedExternally
    var minify: `T$2`?
        get() = definedExternally
        set(value) = definedExternally
    var mirage: Boolean?
        get() = definedExternally
        set(value) = definedExternally
    var resolveOverride: String?
        get() = definedExternally
        set(value) = definedExternally
}

inline var RequestInit.cf: `T$3`? get() = this.asDynamic().cf; set(value) { this.asDynamic().cf = value }

external fun addEventListener(type: String /* 'fetch' */, handler: (event: FetchEvent) -> Unit): dynamic /* Nothing? | Nothing? | Response | Promise<Response> */

external interface `T$4` {
    var certIssuerDNLegacy: String
    var certIssuerDN: String
    var certPresented: String /* '0' | '1' */
    var certSubjectDNLegacy: String
    var certSubjectDN: String
    var certNotBefore: String
    var certNotAfter: String
    var certSerial: String
    var certFingerprintSHA1: String
    var certVerified: String
}

external interface `T$5` {
    var asn: String
    var city: String
    var clientTrustScore: Number
    var colo: String
    var continent: String
    var country: String
    var httpProtocol: String
    var latitude: Number
    var longitude: Number
    var postalCode: String
    var region: String
    var regionCode: String
    var requestPriority: String
    var timezone: String
    var tlsVersion: String
    var tlsCipher: String
    var tlsClientAuth: `T$4`
}

inline var Request.cf: `T$5` get() = this.asDynamic().cf; set(value) { this.asDynamic().cf = value }

external interface ContentOptions {
    var html: Boolean
}

external interface `T$6` {
    var name: String
    var value: String
}

inline var Element.namespaceURI: String get() = this.asDynamic().namespaceURI; set(value) { this.asDynamic().namespaceURI = value }

inline var Element.tagName: String get() = this.asDynamic().tagName; set(value) { this.asDynamic().tagName = value }

inline var Element.attributes: TsStdLib_Iterator<`T$6`> get() = this.asDynamic().attributes; set(value) { this.asDynamic().attributes = value }

inline var Element.removed: Boolean get() = this.asDynamic().removed; set(value) { this.asDynamic().removed = value }

/* extending interface from lib.dom.d.ts */
inline fun Element.getAttribute(name: String): String? = this.asDynamic().getAttribute(name)

/* extending interface from lib.dom.d.ts */
inline fun Element.hasAttribute(name: String): Boolean = this.asDynamic().hasAttribute(name)

/* extending interface from lib.dom.d.ts */
inline fun Element.setAttribute(name: String, value: String): Element = this.asDynamic().setAttribute(name, value)

/* extending interface from lib.dom.d.ts */
inline fun Element.removeAttribute(name: String): Element = this.asDynamic().removeAttribute(name)

/* extending interface from lib.dom.d.ts */
inline fun Element.before(content: String): Element = this.asDynamic().before(content)

inline fun Element.before(content: String, options: ContentOptions): Element = this.asDynamic().before(content, options)

/* extending interface from lib.dom.d.ts */
inline fun Element.after(content: String): Element = this.asDynamic().after(content)

inline fun Element.after(content: String, options: ContentOptions): Element = this.asDynamic().after(content, options)

/* extending interface from lib.dom.d.ts */
inline fun Element.prepend(content: String): Element = this.asDynamic().prepend(content)

inline fun Element.prepend(content: String, options: ContentOptions): Element = this.asDynamic().prepend(content, options)

/* extending interface from lib.dom.d.ts */
inline fun Element.append(content: String): Element = this.asDynamic().append(content)

inline fun Element.append(content: String, options: ContentOptions): Element = this.asDynamic().append(content, options)

/* extending interface from lib.dom.d.ts */
inline fun Element.replace(content: String): Element = this.asDynamic().replace(content)

inline fun Element.replace(content: String, options: ContentOptions): Element = this.asDynamic().replace(content, options)

/* extending interface from lib.dom.d.ts */
inline fun Element.setInnerContent(content: String): Element = this.asDynamic().setInnerContent(content)

inline fun Element.setInnerContent(content: String, options: ContentOptions): Element = this.asDynamic().setInnerContent(content, options)

/* extending interface from lib.dom.d.ts */
inline fun Element.remove(): Element = this.asDynamic().remove()

/* extending interface from lib.dom.d.ts */
inline fun Element.removeAndKeepContent(): Element = this.asDynamic().removeAndKeepContent()

inline var Text.removed: Boolean get() = this.asDynamic().removed; set(value) { this.asDynamic().removed = value }

inline var Text.text: String get() = this.asDynamic().text; set(value) { this.asDynamic().text = value }

inline var Text.lastInTextNode: Boolean get() = this.asDynamic().lastInTextNode; set(value) { this.asDynamic().lastInTextNode = value }

/* extending interface from lib.dom.d.ts */
inline fun Text.before(content: String): Element = this.asDynamic().before(content)

inline fun Text.before(content: String, options: ContentOptions): Element = this.asDynamic().before(content, options)

/* extending interface from lib.dom.d.ts */
inline fun Text.after(content: String): Element = this.asDynamic().after(content)

inline fun Text.after(content: String, options: ContentOptions): Element = this.asDynamic().after(content, options)

/* extending interface from lib.dom.d.ts */
inline fun Text.replace(content: String): Element = this.asDynamic().replace(content)

inline fun Text.replace(content: String, options: ContentOptions): Element = this.asDynamic().replace(content, options)

/* extending interface from lib.dom.d.ts */
inline fun Text.remove(): Element = this.asDynamic().remove()

inline var Comment.removed: Boolean get() = this.asDynamic().removed; set(value) { this.asDynamic().removed = value }

inline var Comment.text: String get() = this.asDynamic().text; set(value) { this.asDynamic().text = value }

/* extending interface from lib.dom.d.ts */
inline fun Comment.before(content: String): Element = this.asDynamic().before(content)

inline fun Comment.before(content: String, options: ContentOptions): Element = this.asDynamic().before(content, options)

/* extending interface from lib.dom.d.ts */
inline fun Comment.after(content: String): Element = this.asDynamic().after(content)

inline fun Comment.after(content: String, options: ContentOptions): Element = this.asDynamic().after(content, options)

/* extending interface from lib.dom.d.ts */
inline fun Comment.replace(content: String): Element = this.asDynamic().replace(content)

inline fun Comment.replace(content: String, options: ContentOptions): Element = this.asDynamic().replace(content, options)

/* extending interface from lib.dom.d.ts */
inline fun Comment.remove(): Element = this.asDynamic().remove()

external interface Doctype {
    var name: String?
        get() = definedExternally
        set(value) = definedExternally
    var publicId: String?
        get() = definedExternally
        set(value) = definedExternally
    var systemId: String?
        get() = definedExternally
        set(value) = definedExternally
}

external interface ElementHandler {
    fun element(element: Element)
    fun comments(comment: Comment)
    fun text(text: Text)
}

external interface DocumentHandler {
    fun doctype(doctype: Doctype)
    fun comments(comment: Comment)
    fun text(text: Text)
}

external open class HTMLRewriter {
    open fun on(selector: String, handlers: ElementHandler): HTMLRewriter
    open fun onDocument(selector: String, handlers: DocumentHandler): HTMLRewriter
    open fun transform(response: Response): Response
}

typealias KVValue<Value> = Promise<Value?>

