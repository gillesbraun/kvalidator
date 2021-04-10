package kvalidator

import kotlinx.serialization.Serializable

@Serializable
public class ValidationException(
    public val errors: Map<String, List<String>>
) : RuntimeException("Validation Failed")
