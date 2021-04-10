package kvalidator.rules

import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.jsonPrimitive

public class IsIn(public vararg val values: String) : Rule() {
    override val name: String = "not_in"
    public override fun validate(data: JsonObject?, attribute: String): Boolean {
        if (data == null) return true
        val element = data[attribute]?.jsonPrimitive?.content
        return values.contains(element = element)
    }
}