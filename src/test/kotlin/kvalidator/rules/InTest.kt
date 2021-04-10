package kvalidator.rules

import kotlinx.serialization.json.Json
import kotlinx.serialization.json.jsonObject
import kvalidator.Validator
import kvalidator.rules
import org.junit.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class InTest {

    private val data = """
        {
            "zone": "eu-west-1",
            "id": 0,
            "amount": 13.37
        }
    """.trimIndent().let(Json.Default::parseToJsonElement).jsonObject

    @Test
    fun `test not in`() {
        val rules = rules {
            attribute("zone") {
                IsIn("us-east-1", "eu-west-1", "us-east-2")
            }
        }
        assertTrue { Validator(data, rules).passes() }
    }

    @Test
    fun `not in with numeric types`() {
        val rule1 = rules {
            attribute("id") {
                isIn("0")
            }
        }
        val rule2 = rules {
            attribute("amount") {
                isIn("13.37")
            }
        }
        assertTrue { Validator(data, rule1).passes() }
        assertTrue { Validator(data, rule2).passes() }
    }

    @Test
    fun `test not in fails`() {
        val rules = rules {
            attribute("zone") {
                isIn("us-east-1", "us-east-2")
            }
            attribute("a_mount") {
                isIn("null", "0", "")
            }
        }
        assertFalse { Validator(data, rules).passes() }
    }
}