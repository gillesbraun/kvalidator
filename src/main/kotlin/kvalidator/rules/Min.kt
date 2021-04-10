package kvalidator.rules

import kotlinx.serialization.json.JsonObject
import kvalidator.getSize

public class Min(public val value: Int) : Rule() {
    override val name: String = "min"
    public override fun  validate(data: JsonObject?, attribute: String): Boolean {
        val elSize = getSize(data?.get(attribute))
        val userSize = value.toDouble()
        if (elSize != null) {
            return elSize >= userSize
        }
        return true
    }
}