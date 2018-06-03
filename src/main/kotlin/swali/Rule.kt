package swali

import io.swagger.models.Swagger
import swali.rules.zalando.*
import swali.rules.zally.*

interface Rule {
    val id: String
    val title: String
    val violationType: ViolationType

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
    HyphenatePascalCaseHeadersRule(config.headersWhiteList),
    KebabCaseInPathSegmentsRule(),
    LimitNumberOfResourcesRule(config.pathsCountLimit),
    LimitNumberOfSubresourcesRule(config.subresourcesLimit),
    MediaTypesRule(),
    NestedPathsMayBeRootPathsRule(),
    NotSpecifyStandardErrorCodesRule(config.standardErrorCodes),
    NoVersionInUriRule(),
    PluralizeNamesForArraysRule(config.pluralWhitelist),
    PluralizeResourceNamesRule(config.pluralWhitelist),
    QueryParameterCollectionFormatRule(),
    SecureWithHttpsRule(),
    SecureWithFlowAppRule(),
    SecureWithOAuthRule(),
    NoUndefinedScopesRule(),
    NoUnsecuredEndpointsRule(),
    SnakeCaseForQueryParamsRule(),
    SnakeCaseInPropNameRule(config.snakeCaseInPropNameWhiteList),
    SuccessResponseAsJsonObjectRule(),
    Use429HeaderForRateLimitRule(),
    UseProblemJsonRule(),
    UseSpecificHttpStatusCodes(config.allowedStatuses),
    VersionInInfoSectionRule()
)