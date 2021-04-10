package kvalidator.rules

import kotlinx.serialization.json.JsonObject
import kvalidator.getSize

public class Size(public val value: Int) : Rule() {
    override val name: String = "size"
    public override fun  validate(data: JsonObject?, attribute: String): Boolean {
        val elSize = getSize(data?.get(attribute))
        if (elSize != null && elSize > 0) {
            val userSize = value.toDouble()
            return elSize == userSize
        } else if (elSize == null) {
            return false
        }
        return true
    }
}