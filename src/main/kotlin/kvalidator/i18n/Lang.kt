package kvalidator.i18n

import kotlinx.serialization.json.*
import kvalidator.rules.*

public typealias Dictionary = Map<String, String>

public object Lang {
    private val fallbackDictionary = en

    private fun missingMessage(rule: Rule, item: JsonElement?): String {
        return "The :attribute has no error message"
    }

    private fun messageByType(dictionary: Dictionary, rule: Rule, item: JsonElement?): String {
        return if (item is JsonArray) {
            dictionary["${rule.name}.array"] ?: fallbackDictionary["${rule.name}.array"]
            ?: missingMessage(rule, item)
        } else if (item is JsonPrimitive && item.isString) {
            dictionary["${rule.name}.string"] ?: fallbackDictionary["${rule.name}.string"]
            ?: missingMessage(rule, item)
        } else if (item is JsonPrimitive && item.doubleOrNull != null) {
            dictionary["${rule.name}.numeric"] ?: fallbackDictionary["${rule.name}.numeric"]
            ?: missingMessage(rule, item)
        } else {
            "The :attribute has no error message [unknown type]"
        }
    }

    public fun message(dictionary: Dictionary, rule: Rule, data: JsonObject, attribute: String): String {
        val item = data[attribute]
        val ruleName = rule.name

        return when (rule) {
            // specific
            // TODO maybe we can move it to rule implementation and leave only common translate?
            is Between -> {
                messageByType(dictionary, rule, item)
                    .replace(":attribute", attribute)
                    .replace(":min", rule.min.toString())
                    .replace(":max", rule.max.toString())
            }
            is Max -> {
                messageByType(dictionary, rule, item)
                        .replace(":attribute", attribute)
                        .replace(":max", rule.value.toString())

            }
            is Min -> {
                messageByType(dictionary, rule, item)
                        .replace(":attribute", attribute)
                        .replace(":min", rule.value.toString())

            }
            is Size -> {
                messageByType(dictionary, rule, item)
                        .replace(":attribute", attribute)
                        .replace(":size", rule.value.toString())

            }
            else -> {
                (dictionary[rule.name] ?: fallbackDictionary[rule.name] ?: missingMessage(rule, item))
                    .replace(":attribute", attribute)
            }
        }
    }
}