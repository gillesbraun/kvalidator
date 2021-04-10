package kvalidator.rules

import kotlinx.serialization.json.JsonObject
import kvalidator.getSize

public class Max(public val value: Int) : Rule() {
    override val name: String = "max"
    public override fun  validate(data: JsonObject?, attribute: String): Boolean {
        if (data == null) return true
        val element = data[attribute]

        val elSize = getSize(element)
        val userSize = value.toDouble()
        if (elSize != null) {
            return elSize <= userSize
        }
        return false
    }
}