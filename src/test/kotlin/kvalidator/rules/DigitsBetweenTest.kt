package kvalidator.rules

import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.JsonPrimitive
import kvalidator.Validator
import kvalidator.rules
import org.junit.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class DigitsBetweenTest {

    private val data = JsonObject(
        mapOf(
            "notok" to JsonPrimitive(
                1234.56
            ),
            "double" to JsonPrimitive(
                1234.00
            ),
            "value" to JsonPrimitive(
                1234
            )
        )
    )

    @Test
    fun digits() {
        assertFalse {
            Validator(data, rules { attribute("notok", "digits_between:0,10") }).passes()
        }
        assertFalse {
            Validator(data, rules { attribute("notok", "digits_between:5,10") }).passes()
        }
        assertFalse {
            Validator(data, rules { attribute("notok", "digits_between:0,3") }).passes()
        }
        assertTrue {
            Validator(data, rules { attribute("double", "digits_between:3,5") }).passes()
        }
        assertTrue {
            Validator(data, rules { attribute("value", "digits_between:3,5") }).passes()
        }
    }

}