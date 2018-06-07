package swali.utils

import io.swagger.models.*
import io.swagger.models.auth.OAuth2Definition
import io.swagger.models.parameters.BodyParameter
import io.swagger.models.parameters.Parameter
import io.swagger.models.properties.*

data class ObjectDefinition(val definition: Map<String, Property>, val path: String)

fun Swagger.getAllJsonObjects(): List<ObjectDefinition> {
    val visitedPaths = mutableSetOf<String>()
    val fromPaths = paths.orEmpty().entries.flatMap { (path, pathObj) ->
        pathObj.operationMap.orEmpty().flatMap { (verb, operation) ->
            val fromParams = operation.parameters.orEmpty().flatMap { param ->
                if (param is BodyParameter)
                    findJsonObjects(param.schema, "$path $verb ${param.name}", visitedPaths)
                else emptyList()
            }
            val fromResponses = operation.responses.flatMap { (code, response) ->
                findJsonObjects(response.schema, "$path $verb $code", visitedPaths)
            }
            (fromParams + fromResponses)
        }
    }
    val fromDefinitions = definitions.orEmpty().flatMap { (name, def) ->
        findJsonObjects(def, "#/definitions/$name", visitedPaths)
    }
    return (fromPaths + fromDefinitions).toSet().toList()
}

private fun Swagger.findJsonObjects(obj: Map<String, Property>?, path: String, visitedPaths: MutableSet<String>): List<ObjectDefinition> =
    if (path !in visitedPaths) {
        visitedPaths += path
        listOf(ObjectDefinition(obj.orEmpty(), path)) + obj.orEmpty().flatMap { (name, property) ->
            findJsonObjects(property, "$path $name", visitedPaths)
        }
    } else emptyList()

private fun Swagger.findJsonObjects(property: Property?, path: String, visitedPaths: MutableSet<String>): List<ObjectDefinition> =
    when (property) {
        is RefProperty -> {
            val model = definitions.orEmpty()[property.simpleRef]
            findJsonObjects(model, "#/definitions/${property.simpleRef}", visitedPaths)
        }
        is ArrayProperty -> findJsonObjects(property.items, "$path items", visitedPaths)
        is MapProperty -> findJsonObjects(property.additionalProperties, path, visitedPaths)
        is ObjectProperty -> findJsonObjects(property.properties, path, visitedPaths)
        else -> emptyList()
    }

private fun Swagger.findJsonObjects(model: Model?, path: String, visitedPaths: MutableSet<String>): List<ObjectDefinition> =
    when (model) {
        is RefModel ->
            findJsonObjects(model.properties, "#/definitions/${model.simpleRef}", visitedPaths)
        is ArrayModel ->
            findJsonObjects(model.items, "$path items", visitedPaths)
        is ModelImpl ->
            findJsonObjects(model.properties, path, visitedPaths)
        is ComposedModel ->
            model.allOf.orEmpty().flatMap { findJsonObjects(it, path, visitedPaths) } +
                model.interfaces.orEmpty().flatMap { findJsonObjects(it, path, visitedPaths) } +
                findJsonObjects(model.parent, path, visitedPaths) +
                findJsonObjects(model.child, path, visitedPaths)
        else -> emptyList()
    }


fun Swagger.getDefinedScopes(): Set<Pair<String, String>> =
    securityDefinitions.orEmpty().entries.flatMap { (group, def) ->
        (def as? OAuth2Definition)?.scopes.orEmpty().keys.map { scope -> group to scope }
    }.toSet()

fun extractAppliedScopes(operation: Operation): Set<Pair<String, String>> =
    operation.security.orEmpty().flatMap { groupDefinition ->
        groupDefinition.orEmpty().entries.flatMap { (group, scopes) ->
            scopes.orEmpty().map { scope -> group to scope }
        }
    }.toSet()

fun Swagger.hasTopLevelScope(definedScopes: Set<Pair<String, String>>): Boolean =
    security.orEmpty().any { securityRequirement ->
        securityRequirement.requirements.entries.any { (group, scopes) ->
            scopes.any { scope -> (group to scope) in definedScopes }
        }
    }


fun Swagger.extractHeaders(): List<Pair<String, String>> {
    fun Collection<Parameter>?.extractHeaders() =
        orEmpty().filter { it.`in` == "header" }.map { it.name }

    fun Collection<Response>?.extractHeaders() =
        orEmpty().flatMap { it.headers?.keys.orEmpty() }

    val fromParams = parameters.orEmpty().values.extractHeaders().map { "parameters" to it }
    val fromPaths = paths.orEmpty().entries.flatMap { (path, def) ->
        val headerNames = def.parameters.extractHeaders() + def.operations.flatMap { operation ->
            operation.parameters.extractHeaders() + operation.responses.values.extractHeaders()
        }
        headerNames.map { path to it}
    }
    return fromParams + fromPaths
}
