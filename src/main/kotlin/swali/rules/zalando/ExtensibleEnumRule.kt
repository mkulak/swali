package swali.rules.zalando

import io.swagger.models.Operation
import io.swagger.models.Swagger
import io.swagger.models.parameters.Parameter
import io.swagger.models.parameters.SerializableParameter
import io.swagger.models.properties.*
import swali.*

class ExtensibleEnumRule : Rule {
    val title = "Prefer Compatible Extensions"
    val violationType = ViolationType.SHOULD
    override val id = "107"

    override fun validate(swagger: Swagger): Violation? {
        val properties = enumProperties(swagger)
        val parameters = enumParameters(swagger)

        val enumNames = (properties.keys + parameters.keys).distinct()
        val enumPaths = (properties.values + parameters.values).distinct()
        return if (enumNames.isNotEmpty()) Violation(title,
                "Properties/Parameters $enumNames are not extensible enums", violationType, ruleLink(id), enumPaths)
        else null
    }

    private fun enumProperties(swagger: Swagger): Map<String, String> =
        swagger.definitions.orEmpty().flatMap { (defName, model) ->
            val enumProps = model.properties.orEmpty().filter { (_, prop) -> prop.isEnum() }
            enumProps.map { (propName, _) -> propName to "#/definitions/$defName/properties/$propName" }
        }.toMap()

    private fun enumParameters(swagger: Swagger): Map<String, String> {
        val pathsOperationsAndEnums = swagger.paths.orEmpty().map { (pathName, path) ->
            pathName to path.operationMap.orEmpty().map { (opName, op) -> opName to op.getEnumParameters() }.toMap()
        }.toMap()

        return pathsOperationsAndEnums
            .filter { (_, opAndEnums) -> opAndEnums.isNotEmpty() }
            .flatMap { (pathName, opAndEnums) -> opAndEnums.map { (op, enums) -> "#/paths$pathName/$op" to enums } }
            .flatMap { (operationPath, enums) -> enums.map { it to "$operationPath/parameters/$it" } }.toMap()
    }

    private fun Property.isEnum(): Boolean = when (this) {
        is StringProperty -> this.enum.hasValues()
        is BinaryProperty -> this.enum.hasValues()
        is DateProperty -> this.enum.hasValues()
        is DateTimeProperty -> this.enum.hasValues()
        is BooleanProperty -> this.enum.hasValues()
        is DoubleProperty -> this.enum.hasValues()
        is EmailProperty -> this.enum.hasValues()
        is FloatProperty -> this.enum.hasValues()
        is IntegerProperty -> this.enum.hasValues()
        is LongProperty -> this.enum.hasValues()
        is PasswordProperty -> this.enum.hasValues()
        else -> false
    }

    private fun <T> List<T>?.hasValues() : Boolean {
        return this.orEmpty().isNotEmpty()
    }

    private fun Operation?.getEnumParameters() = this?.parameters.orEmpty().filter { it.isEnum() }.map { it.name }

    private fun Parameter?.isEnum() = (this as? SerializableParameter)?.enum.orEmpty().isNotEmpty()
}
