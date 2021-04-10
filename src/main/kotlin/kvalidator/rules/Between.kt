package kvalidator.rules

import kotlinx.serialization.json.JsonObject
import kvalidator.getSize

public class Between(public val min: Int, public val max: Int) : Rule() {
    override val name: String = "between"

    public override fun  validate(data: JsonObject?, attribute: String): Boolean {
        if (data == null) return true
        val element = data[attribute]
        val elSize = getSize(element)
        return (elSize != null && min.toDouble() <= elSize && elSize <= max.toDouble())
    }
}