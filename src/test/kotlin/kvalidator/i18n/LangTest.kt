package kvalidator.i18n

import kotlinx.serialization.json.jsonObject
import kvalidator.LibraryTest
import kvalidator.ValidationException
import kvalidator.Validator
import kvalidator.rules.Email
import kvalidator.rules.IsBoolean
import kvalidator.rules.Max
import kvalidator.rules.Min
import kvalidator.rules.Required
import kvalidator.rules.Rule
import kvalidator.rules.Size
import kotlin.test.assertFalse
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

public class LangTest : LibraryTest() {
    private val invalidData = data.getValue("invalid_data").jsonObject

    @Test
    fun testInvalidateRulesHasCorrectMessage() {
        val rules = mapOf<String, List<Rule>>(
            "short_5_email" to listOf(
                Required(),
                Email(),
                Max(15),
                Min(8),
                Size(8),
                IsBoolean()
            )
        )

        val validator = Validator(invalidData, rules, lang = en)

        val exception = assertFailsWith(ValidationException::class) {
            validator.validate()
        }

        exception.errors.forEach { (_, errors) ->
            assertEquals(errors[0], "The short_5_email must be at least 8 characters.")
            assertEquals(errors[1], "The short_5_email must be 8 characters.")
            assertEquals(errors[2], "The short_5_email field must be true or false.")
        }
    }
}