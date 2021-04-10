package kvalidator

import kotlinx.serialization.json.*
import kvalidator.i18n.Dictionary
import kvalidator.i18n.Lang
import kvalidator.i18n.en
import kvalidator.rules.Rule

public class Error {
    public val errors: MutableMap<String, MutableList<String>> = mutableMapOf()

    public fun add(attribute: String, reason: String): Unit {
        if (errors.contains(attribute)) {
            errors[attribute]?.add(reason)
        } else {
            errors[attribute] = mutableListOf(reason)
        }
    }

    public fun first(attribute: String): String? {
        if (errors.contains(attribute)) {
            return errors[attribute]?.get(0)
        }
        return null
    }

    public fun get(attribute: String): List<String>? {
        if (errors.contains(attribute)) {
            return errors[attribute]?.toList()
        }
        return null
    }

    public fun all(): MutableMap<String, MutableList<String>>? {
        if (errors.isEmpty()) {
            return null
        }
        return errors
    }

    public val count: Int
        get() = errors.size
}

public class Validator(
        public val data: JsonElement = JsonObject(emptyMap()),
        public val rules: Map<String, List<Rule>> = emptyMap(),
        private val lang: Dictionary = en
) {
    private val inputErrors = Error()

    // attribute_name: [reason1, reason2, reason3]
    public val errors: Map<String, List<String>>
        get() = inputErrors.errors

    public fun validate(): Boolean {
        when (data) {
            is JsonObject -> {
                rules.forEach { (attribute, ruleItems) ->
                    ruleItems.forEach { rule ->
                        val isValid = rule.validate(data, attribute)
                        if (!isValid) {
                            inputErrors.add(
                                attribute = attribute,
                                reason = Lang.message(lang, rule, data, attribute)
                            )
                        }
                    }
                }
            }
            else -> TODO("Only JsonObject can be validated")
        }
        return inputErrors.count == 0
    }
}