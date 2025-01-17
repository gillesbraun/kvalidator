package kvalidator.rules

import kotlinx.serialization.json.JsonArray
import kotlinx.serialization.json.JsonObject

public class IsArray : Rule() {
    override val name: String = "array"
    public override fun  validate(data: JsonObject?, attribute: String): Boolean {
        if (data == null) return true
        if (!data.containsKey(attribute)) return true

        return when (data[attribute]) {
            is JsonArray -> true
            is JsonObject -> true
            else -> false
        }
    }
}