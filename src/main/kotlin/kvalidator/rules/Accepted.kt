package kvalidator.rules

import kotlinx.serialization.json.*


public class Accepted : Rule() {
    override val name: String = "accepted"
    public override fun  validate(data: JsonObject?, attribute: String): Boolean {
        if (data == null) return false
        if (!data.containsKey(attribute)) return false

        return when (val element = data[attribute]) {
            is JsonPrimitive -> {
                when {
                    element.isString && element.content == "true" -> true
                    element.isString && element.content == "yes" -> true
                    element.isString && element.content == "on" -> true
                    element.isString && element.content == "1" -> true
                    element.contentOrNull == "true" -> true
                    element.doubleOrNull != null && element.double == 1.00 -> true
                    else -> false
                }
            }
            else -> false
        }
    }
}