package kvalidator.rules

import kotlinx.serialization.json.*

public class IsInteger : Rule() {
    override val name: String = "integer"
    public override fun  validate(data: JsonObject?, attribute: String): Boolean {
        if (data == null) return true
        if (!data.containsKey(attribute)) return true

        return when (val element = data[attribute]) {
            is JsonPrimitive -> when {
                element.floatOrNull != null -> {
                    val converted = element.float
                    return (converted.compareTo(converted.toInt()) == 0)
                }
                element.intOrNull != null -> true
                else -> false
            }
            else -> false
        }
    }
}