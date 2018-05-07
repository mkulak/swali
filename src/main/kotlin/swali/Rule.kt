package swali

import io.swagger.models.Swagger
import swali.zalando.*
import swali.zally.*

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
//    SecureWithOAuth2Rule(),
    SnakeCaseForQueryParamsRule(),
    SnakeCaseInPropNameRule(config.snakeCaseInPropNameWhitelIst),
    SuccessResponseAsJsonObjectRule(),
    Use429HeaderForRateLimitRule(),
    UseProblemJsonRule(),
    UseSpecificHttpStatusCodes(config.allowedStatuses),
    VersionInInfoSectionRule()
)