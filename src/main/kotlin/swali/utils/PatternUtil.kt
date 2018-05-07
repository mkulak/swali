package swali.utils

object PatternUtil {
    private val LOWER_CASE_HYPHENS_PATTERN = "^[a-z-]*$".toRegex()
    private val CAMEL_CASE_PATTERN = "^[a-z]+(?:[A-Z][a-z]+)*$".toRegex()
    private val PASCAL_CASE_PATTERN = "^[A-Z][a-z]+(?:[A-Z][a-z]+)*$".toRegex()
    private val HYPHENATED_CAMEL_CASE_PATTERN = "^[a-z]+(?:-[A-Z][a-z]+)*$".toRegex()
    private val HYPHENATED_PASCAL_CASE_PATTERN = "^[A-Z][a-z0-9]*(?:-[A-Z][a-z0-9]+)*$".toRegex()
    private val HYPHENATED_PATTERN = "^[A-Za-z0-9.]+(-[A-Za-z0-9.]+)*$".toRegex()
    private val SNAKE_CASE_PATTERN = "^[a-z0-9]+(?:_[a-z0-9]+)*$".toRegex()
    private val KEBAB_CASE_PATTERN = "^[a-z]+(?:-[a-z]+)*$".toRegex()
    private val VERSION_IN_URL_PATTERN = "(.*)/v[0-9]+(.*)".toRegex()
    private val PATH_VARIABLE_PATTERN = "\\{.+\\}$".toRegex()
    private val GENERIC_VERSION_PATTERN = "^\\d+\\.\\d+\\.\\d+$".toRegex()
    private val PATTERN_APPLICATION_PROBLEM_JSON = "^application/(problem\\+)?json$".toRegex()
    private val PATTERN_CUSTOM_WITH_VERSIONING = "^\\w+/[-+.\\w]+;v(ersion)?=\\d+$".toRegex()

    fun hasTrailingSlash(input: String): Boolean = input.trim { it <= ' ' }.endsWith("/")

    fun isLowerCaseAndHyphens(input: String): Boolean = input.matches(LOWER_CASE_HYPHENS_PATTERN)

    fun isPathVariable(input: String): Boolean = input.matches(PATH_VARIABLE_PATTERN)

    fun isCamelCase(input: String): Boolean = input.matches(CAMEL_CASE_PATTERN)

    fun isPascalCase(input: String): Boolean = input.matches(PASCAL_CASE_PATTERN)

    fun isHyphenatedCamelCase(input: String): Boolean = input.matches(HYPHENATED_CAMEL_CASE_PATTERN)

    fun isHyphenatedPascalCase(input: String): Boolean = input.matches(HYPHENATED_PASCAL_CASE_PATTERN)

    fun isSnakeCase(input: String): Boolean = input.matches(SNAKE_CASE_PATTERN)

    fun isKebabCase(input: String): Boolean = input.matches(KEBAB_CASE_PATTERN)

    fun isHyphenated(input: String): Boolean = input.matches(HYPHENATED_PATTERN)

    fun hasVersionInUrl(input: String): Boolean = input.matches(VERSION_IN_URL_PATTERN)

    fun isVersion(input: String): Boolean = input.matches(GENERIC_VERSION_PATTERN)

    fun isApplicationJsonOrProblemJson(mediaType: String): Boolean = mediaType.matches(PATTERN_APPLICATION_PROBLEM_JSON)

    fun isCustomMediaTypeWithVersioning(mediaType: String): Boolean = mediaType.matches(PATTERN_CUSTOM_WITH_VERSIONING)
}
