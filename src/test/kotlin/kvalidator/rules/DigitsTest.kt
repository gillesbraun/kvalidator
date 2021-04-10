package kvalidator.rules

import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.JsonPrimitive
import kvalidator.Validator
import kvalidator.rules
import org.junit.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class DigitsTest {

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
            Validator(data, rules { attribute("notok", "digits:6") }).passes()
        }
        assertFalse {
            Validator(data, rules { attribute("notok", "digits:5") }).passes()
        }
        assertFalse {
            Validator(data, rules { attribute("notok", "digits:4") }).passes()
        }
        assertTrue {
            Validator(data, rules { attribute("double", "digits:4") }).passes()
        }
        assertTrue {
            Validator(data, rules { attribute("value", "digits:4") }).passes()
        }
    }

}