package kvalidator.rules

import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.JsonPrimitive
import kvalidator.LibraryTest
import kvalidator.Validator
import kotlin.test.Test
import kotlin.test.assertTrue

public class AlphaTest : LibraryTest() {
    @Test
    fun testValidAlpha() {
        val testJson = JsonObject(mapOf("alpha" to JsonPrimitive("AAbbDDDcc")))
        val rule = mapOf<String, List<Rule>>("alpha" to listOf(Alpha()))
        assertTrue(Validator(testJson, rule).passes(), """result for ${testJson["alpha"]} should return true""")
    }
}