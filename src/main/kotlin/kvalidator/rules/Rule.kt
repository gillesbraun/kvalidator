package kvalidator.rules

import kotlinx.serialization.json.JsonObject

abstract public class Rule {
    public abstract val name: String
    public abstract fun validate(data: JsonObject?, attribute: String): Boolean
}

