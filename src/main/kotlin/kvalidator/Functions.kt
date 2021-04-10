package kvalidator

import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.jsonObject
import kvalidator.i18n.Dictionary
import kvalidator.i18n.en
import kvalidator.rules.Rule

/**
 * Validate a JsonObject with the Rule Builder DSL
 */
public fun validate(
    data: JsonObject,
    language: Dictionary = en,
    op: AttributeBuilder.() -> Unit
) {
    val builder = AttributeBuilder()
    op(builder)
    Validator(data, builder.rules, language).validate()
}

/**
 * Validate a Json String with the Rule Builder DSL
 */
public fun validate(
    data: String,
    language: Dictionary = en,
    json: Json = Json.Default,
    op: AttributeBuilder.() -> Unit
) {
    val dataJson = json.parseToJsonElement(data).jsonObject
    val builder = AttributeBuilder()
    op(builder)
    Validator(dataJson, builder.rules, language).validate()
}

/**
 * Validate a JsonObject against a list of rules
 */
public fun validate(
    data: JsonObject,
    rules: Map<String, List<Rule>>,
    language: Dictionary = en,
) {
    Validator(data, rules, language).validate()
}

/**
 * Validate a Json String against a list of rules
 */
public fun validate(
    data: String,
    rules: Map<String, List<Rule>>,
    language: Dictionary = en,
    json: Json = Json.Default,
) {
    val dataJson = json.parseToJsonElement(data).jsonObject
    Validator(dataJson, rules, language).validate()
}

/**
 * Rule Builder DSL
 */
public fun rules(
    op: AttributeBuilder.() -> Unit
): Map<String, List<Rule>> {
    val builder = AttributeBuilder()
    op(builder)
    return builder.rules
}