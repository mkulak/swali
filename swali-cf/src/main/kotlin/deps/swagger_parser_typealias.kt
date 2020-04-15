package swagger_parser

import openapi_types.OpenAPIV3.Document

typealias ApiCallback = (err: Error?, api: Document) -> Any

typealias RefsCallback = (err: Error?, `$refs`: SwaggerParser.`$Refs`) -> Any

