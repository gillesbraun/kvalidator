package kvalidator

import kotlinx.serialization.json.*
import kvalidator.rules.*


internal fun getSize(value: JsonElement?): Double? {
    return when (value) {
        is JsonPrimitive -> {
            when {
                value.isString -> value.content.length.toDouble()
                value.doubleOrNull != null -> value.double
                value.booleanOrNull != null || value is JsonNull -> null
                else -> null
            }
        }
        is JsonObject -> value.size.toDouble()
        is JsonArray -> value.size.toDouble()
        else -> null
    }
}

public fun parseRule(value: String): Rule {
    val parts = value.split(":")
    val args = parts.drop(1).firstOrNull()?.split(",").orEmpty().toTypedArray()
    return when (parts.first().toLowerCase()) {
        "between" -> Between(args.first().toInt(), args.last().toInt())
        "max" -> Max(args.first().toInt())
        "min" -> Min(args.first().toInt())
        "size" -> Size(args.first().toInt())
        "accepted" -> Accepted()
        "digits" -> Digits(args.first().toInt())
        "digits_between" -> DigitsBetween(args.first().toInt(), args.last().toInt())
        "string" -> Accepted()
        "alpha" -> Alpha()
        "alpha_dash" -> AlphaDash()
        "alpha_num" -> AlphaNum()
        "email" -> Email()
        "required" -> Required()
        "not_in" -> NotIn(*args)
        "in" -> IsIn(*args)
        "numeric" -> IsNumeric()
        "date" -> IsDate()
        "boolean" -> IsBoolean()
        "integer" -> IsInteger()
        "url" -> Url()
        else -> throw IllegalArgumentException("unknown rule")
    }
}

public fun parseRules(value: String): List<Rule> {
    return value.split("|").map { parseRule(it.trim()) }
}

public fun stringifyRule(rule: Rule) : String {
    return when(rule) {
        is Between -> "${rule.name}:${rule.min},${rule.max}"
        is Max -> "${rule.name}:${rule.value}"
        is Min -> "${rule.name}:${rule.value}"
        is Size -> "${rule.name}:${rule.value}"
        is Digits -> "${rule.name}:${rule.digits}"
        is DigitsBetween -> "${rule.name}:${rule.min},${rule.max}"
        is NotIn -> "${rule.name}:${rule.values.joinToString(",")}"
        is IsIn -> "${rule.name}:${rule.values.joinToString(",")}"
        else -> rule.name
    }
}

public fun stringifyRules(rules: List<Rule>) : String {
    return rules.map { stringifyRule(it) }.joinToString("|")
}