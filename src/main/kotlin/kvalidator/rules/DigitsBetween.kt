package kvalidator.rules

import kotlinx.serialization.json.*

/**
 * Validates the length of a number between two values, both inclusive.
 *
 * 123 => 3
 * 123.00 => 3
 * 123.34 => never validates
 */
public class DigitsBetween(public val min: Int, public val max: Int) : Rule() {
    override val name: String = "digits_between"

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
            return IntRange(min, max).contains(intVal.toString().length)
        }
        return false
    }
}