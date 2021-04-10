package kvalidator

import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonObject
import kvalidator.i18n.Dictionary
import kvalidator.i18n.Lang
import kvalidator.i18n.en
import kvalidator.rules.Rule

/**
 * Main class that validates JSON objects.
 */
public class Validator(
    public val data: JsonElement,
    public val rules: Map<String, List<Rule>>,
    public val lang: Dictionary = en
) {

    /**
     * Checks if the given data passes the validation rules.
     */
    public fun passes(): Boolean = try {
        validate()
        true
    } catch (ve: ValidationException) {
        false
    }

    /**
     * Validate the given data, throwing a [ValidationException] if validation fails.
     * @throws ValidationException
     */
    public fun validate() {
        if (data !is JsonObject) {
            TODO("Only JsonObject can be validated")
        }

        val failed = rules.mapNotNull { (attribute, ruleItems) ->

            // Do validations and collect failed ones into a list
            val errors = ruleItems.mapNotNull { rule ->
                if (!rule.validate(data, attribute)) {
                    Lang.message(lang, rule, data, attribute)
                } else {
                    null
                }
            }

            if (errors.isNotEmpty()) {
                // Return the failed validations for this attribute
                attribute to errors
            } else {
                null
            }

        }.toMap()

        if (failed.isNotEmpty()) {
            throw ValidationException(failed)
        }
    }
}