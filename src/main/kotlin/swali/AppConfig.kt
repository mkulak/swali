package swali



data class AppConfig(
    val headersWhiteList: Set<String>,
    val commonFields: Map<String, Pair<String, String>>,
    val numberFormats: Map<String, List<String>>,
    val pathsCountLimit: Int,
    val subresourcesLimit: Int,
    val standardErrorCodes: Set<Int>,
    val snakeCaseInPropNameWhitelIst: Set<String>,
    val allowedStatuses: Map<String, Set<Int>>
)

val config = AppConfig(
    headersWhiteList = setOf("ETag", "TSV", "TE", "Content-MD5", "DNT", "X-ATT-DeviceId", "X-UIDH", "X-Request-ID",
        "X-Correlation-ID", "WWW-Authenticate", "X-XSS-Protection", "X-Flow-ID", "X-UID", "X-Tenant-ID", "X-Device-OS",
        "X-Trace-ID", "X-RateLimit-Limit", "X-RateLimit-Remaining", "X-RateLimit-Reset"),

    commonFields = mapOf(
        "id" to Pair("string", "null"),
        "created" to Pair("string", "date-time"),
        "modified" to Pair("string", "date-time"),
        "type" to Pair("string", "null")
    ),
    numberFormats = mapOf(
        "integer" to listOf("int32", "int64", "bigint"),
        "number" to listOf("float", "double", "decimal")
    ),
    pathsCountLimit = 8,
    subresourcesLimit = 3,
    standardErrorCodes = setOf(401, 403, 404, 405, 406, 408, 413, 414, 415, 500, 502, 503, 504),
    snakeCaseInPropNameWhitelIst = setOf("_links"),
    allowedStatuses = mapOf(
        "get" to    setOf(304),
        "post" to   setOf(201, 202, 207, 303),
        "put" to    setOf(201, 202, 204, 303, 409, 412, 415, 423),
        "patch" to  setOf(202, 303, 409, 412, 415, 423),
        "delete" to setOf(202, 204, 303, 409, 412, 415, 423),
        "all" to    setOf(200, 301, 400, 401, 403, 404, 405, 406, 408, 410, 428, 429, 500, 501, 503)
    )
)