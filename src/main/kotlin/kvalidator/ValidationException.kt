package kvalidator

public class ValidationException(
    public val errors: List<FailedValidation>
) : RuntimeException()

public class FailedValidation(
    public val attribute: String,
    public val errors: List<String>
) {

    public operator fun component1(): String = attribute

    public operator fun component2(): List<String> = errors
}