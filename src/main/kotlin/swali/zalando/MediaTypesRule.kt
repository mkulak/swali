package swali.zalando

import io.swagger.models.Swagger
import swali.*
import swali.utils.PatternUtil.isApplicationJsonOrProblemJson
import swali.utils.PatternUtil.isCustomMediaTypeWithVersioning

class MediaTypesRule : Rule {
    val title = "Prefer standard media type names"
    val violationType = ViolationType.SHOULD
    override val id = "172"
    private val DESCRIPTION = "Custom media types should only be used for versioning"

    override fun validate(swagger: Swagger): Violation? {
        val paths = swagger.paths.orEmpty().entries.flatMap { (pathName, path) ->
            path.operationMap.orEmpty().entries.flatMap { (verb, operation) ->
                val mediaTypes = ArrayList<String>() + operation.produces.orEmpty() + operation.consumes.orEmpty()
                val violatingMediaTypes = mediaTypes.filter(this::isViolatingMediaType)
                if (violatingMediaTypes.isNotEmpty()) listOf("$pathName $verb") else emptyList()
            }
        }
        return if (paths.isNotEmpty()) Violation(title, DESCRIPTION, violationType, id, paths) else null
    }

    private fun isViolatingMediaType(mediaType: String) =
        !isApplicationJsonOrProblemJson(mediaType) && !isCustomMediaTypeWithVersioning(mediaType)
}
