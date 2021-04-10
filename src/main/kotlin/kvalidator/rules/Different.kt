package kvalidator.rules

import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.jsonPrimitive

public class Different(public val otherField: String): Rule() {
    override val name: String = "different"

    override fun validate(data: JsonObject?, attribute: String): Boolean {
        return data?.get(attribute)?.jsonPrimitive?.content != data?.get(otherField)?.jsonPrimitive?.content
    }
}