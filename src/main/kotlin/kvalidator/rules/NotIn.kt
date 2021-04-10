package kvalidator.rules

import kotlinx.serialization.json.JsonObject

public class NotIn(public vararg val values: String) : Rule() {
    override val name: String = "not_in"
    public override fun validate(data: JsonObject?, attribute: String): Boolean {
        TODO("Not yet implemented")
//         if (data == null) return true
//         val element = data[attribute]
//
//         val elSize = getSize(element)
//         val userSize = value.toDouble()
//         val userSize2 = value2.toDouble()
//         if (elSize != null && elSize > 0) {
//             return userSize < elSize && elSize < userSize2
//         }
//         return true
    }
}