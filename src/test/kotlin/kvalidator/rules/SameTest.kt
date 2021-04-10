package kvalidator.rules

import kvalidator.ValidationException
import kvalidator.rules
import kvalidator.validate
import org.junit.Test
import kotlin.test.assertFailsWith

class SameTest {

    val data = """
        {
            "username": "username",
            "password_confirmation": "supersecret",
            "password": "supersecret"
        }
    """.trimIndent()

    @Test
    fun `same fails`() {
        val rules = rules {
            attribute("password") {
                same("username")
            }
        }
        assertFailsWith(ValidationException::class) {
            validate(data, rules)
        }
    }

    @Test
    fun `same passes`() {
        val rules = rules {
            attribute("password") {
                same("password_confirmation")
            }
        }
        validate(data, rules)
    }

}