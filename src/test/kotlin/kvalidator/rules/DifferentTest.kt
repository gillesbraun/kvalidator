package kvalidator.rules

import kvalidator.ValidationException
import kvalidator.rules
import kvalidator.validate
import org.junit.Test
import kotlin.test.assertFailsWith

class DifferentTest {

    val data = """
        {
            "password": "supersecret",
            "username": "supersecret",
            "email": "email@example.com"
        }
    """.trimIndent()

    @Test
    fun different() {
        val rules = rules {
            attribute("password") {
                different("username")
            }
        }
        assertFailsWith(ValidationException::class) {
            validate(data, rules)
        }
    }

    @Test
    fun `different passes`() {
        val rules = rules {
            attribute("password") {
                different("email")
            }
        }
        validate(data, rules)
    }

}