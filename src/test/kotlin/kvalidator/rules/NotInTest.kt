package kvalidator.rules

import kotlinx.serialization.json.Json
import kotlinx.serialization.json.jsonObject
import kvalidator.Validator
import kvalidator.rules
import org.junit.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class NotInTest {

    private val data = """
        {
            "zone": "eu-west-1",
            "id": 123,
            "amount": 13.37
        }
    """.trimIndent().let(Json.Default::parseToJsonElement).jsonObject

    @Test
    fun `test not in`() {
        val rules = rules {
            attribute("zone") {
                notIn("us-east-1", "us-east-2")
            }
        }
        assertTrue { Validator(data, rules).passes() }
    }

    @Test
    fun `not in with numeric types`() {
        val rule1 = rules {
            attribute("id") {
                notIn("123")
            }
        }
        val rule2 = rules {
            attribute("amount") {
                notIn("13.37")
            }
        }
        assertFalse { Validator(data, rule1).passes() }
        assertFalse { Validator(data, rule2).passes() }
    }

    @Test
    fun `test not in fails`() {
        val rules = rules {
            attribute("zone") {
                notIn("us-east-1", "eu-west-1")
            }
        }
        assertFalse { Validator(data, rules).passes() }
    }
}