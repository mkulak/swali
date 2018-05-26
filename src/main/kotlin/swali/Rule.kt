package swali

import io.swagger.models.Swagger
import swali.rules.zalando.*
import swali.rules.zally.*

interface Rule {
    val id: String
    fun validate(swagger: Swagger): Violation?
}

val allRules = listOf(
    ExtractBasePathRule(),
    NoProtocolInHostRule(),
    NoUnusedDefinitionsRule(),
    AvoidLinkHeadersRule(config.headersWhiteList),
    AvoidTrailingSlashesRule(),
    CommonFieldTypesRule(config.commonFields),
    EverySecondPathLevelParameterRule(),
    ExtensibleEnumRule(),
    FormatForNumbersRule(config.numberFormats),
    HyphenateHttpHeadersRule(config.headersWhiteList),
    KebabCaseInPathSegmentsRule(),
    LimitNumberOfResourcesRule(config.pathsCountLimit),
    LimitNumberOfSubresourcesRule(config.subresourcesLimit),
    MediaTypesRule(),
    NestedPathsMayBeRootPathsRule(),
    NotSpecifyStandardErrorCodesRule(config.standardErrorCodes),
    NoVersionInUriRule(),
    PascalCaseHttpHeadersRule(config.headersWhiteList),
    PluralizeNamesForArraysRule(),
    PluralizeResourceNamesRule(),
    QueryParameterCollectionFormatRule(),
    SecureWithHttpsRule(),
    SecureWithFlowAppRule(),
    SecureWithOAuthRule(),
    NoUndefinedScopesRule(),
    SnakeCaseForQueryParamsRule(),
    SnakeCaseInPropNameRule(config.snakeCaseInPropNameWhiteList),
    SuccessResponseAsJsonObjectRule(),
    Use429HeaderForRateLimitRule(),
    UseProblemJsonRule(),
    UseSpecificHttpStatusCodes(config.allowedStatuses),
    VersionInInfoSectionRule()
)