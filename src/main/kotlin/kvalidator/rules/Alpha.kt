package kvalidator.rules

import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.JsonPrimitive
import kotlinx.serialization.json.booleanOrNull

public class Alpha : Rule() {
    override val name: String = "alpha"
    public override fun  validate(data: JsonObject?, attribute: String): Boolean {
        if (data == null) return true
        if (!data.containsKey(attribute)) return true

        return when (val element = data[attribute]) {
            is JsonPrimitive -> {
                when {
                    element.booleanOrNull != null -> false
                    element.isString -> {
                        val regex = Regex(pattern = """[A-Za-zА-Яа-яёЁ]+$""")
                        return regex.matches(input = element.content)
                    }
                    else -> false
                }
            }
            else -> false
        }
    }
}