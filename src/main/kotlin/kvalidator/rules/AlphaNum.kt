package kvalidator.rules

import kotlinx.serialization.json.*

public class AlphaNum : Rule() {
    override val name: String = "alpha_num"
    public override fun  validate(data: JsonObject?, attribute: String): Boolean {
        if (data == null) return true
        if (!data.containsKey(attribute)) return true

        return when (val element = data[attribute]) {
            is JsonPrimitive -> {
                when {
                    element.booleanOrNull != null -> false
                    element.isString || element.doubleOrNull != null -> {
                        val regex = Regex(pattern = """[A-Za-zА-Яа-яёЁ0-9]+$""")
                        return regex.matches(input = element.content)
                    }
                    else -> false
                }
            }
            else -> false
        }
    }
}