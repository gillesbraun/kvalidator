package kvalidator.rules

import kotlinx.serialization.json.*

/**
 * Validates the length of a number.
 *
 * 123 => 3
 * 123.00 => 3
 * 123.34 => never validates
 */
public class Digits(public val digits: Int) : Rule() {
    override val name: String = "digits"

    override fun validate(data: JsonObject?, attribute: String): Boolean {
        val value = data?.get(attribute)
        if (value !is JsonPrimitive) {
            return false
        }
        if (value.doubleOrNull != null || value.intOrNull != null) {
            // cut off decimal places
            val intVal = value.double.toInt()
            if (intVal.toDouble() != value.double) {
                // check that nothing was cut off
                return false
            }
            return intVal.toString().length == digits
        }
        return false
    }
}