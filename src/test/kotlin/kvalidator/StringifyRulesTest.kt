package kvalidator

import kvalidator.rules.*
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertSame
import kotlin.test.assertTrue

public class StringifyRulesTest : LibraryTest() {
    @Test
    fun parseAndStringify() {
        val rules = listOf(
            Required(),
            Email(),
            Max(15),
            Min(8),
            Size(8),
            IsBoolean(),
            NotIn("admin", "moderator")
        )

        val rulesString = "required|email|max:15|min:8|size:8|boolean|not_in:admin,moderator"
        assertEquals(rulesString, stringifyRules(rules))

        val parsed = parseRules(rulesString)
        val required = parsed[0] as Required
        val email = parsed[1] as Email
        val max = parsed[2] as Max
        val min = parsed[3] as Min
        val size = parsed[4] as Size
        val boolean = parsed[5] as IsBoolean
        val notIn = parsed[6] as NotIn

        assertEquals(max.value, 15)
        assertEquals(min.value, 8)
        assertEquals(size.value, 8)
        assertTrue {
            notIn.values.contentEquals(arrayOf("admin", "moderator"))
        }
    }

    @Test
    fun `dsl rules builder`() {
        val rules = rules {
            attribute("unnamed") {
                required()
                email()
                max(15)
                min(8)
                size(8)
                isBoolean()
                notIn("admin", "moderator")
            }
        }["unnamed"].orEmpty()

        val rulesString = "required|email|max:15|min:8|size:8|boolean|not_in:admin,moderator"
        assertEquals(rulesString, stringifyRules(rules))
    }

    @Test
    fun `dsl rules string`() {
        val rules = rules {
            attribute("unnamed", "required|email|max:15|min:8|size:8|boolean|not_in:admin,moderator")
        }["unnamed"].orEmpty()

        val rulesString = "required|email|max:15|min:8|size:8|boolean|not_in:admin,moderator"
        assertEquals(rulesString, stringifyRules(rules))
    }
}