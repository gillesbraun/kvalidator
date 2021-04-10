# **kvalidator**

[![](https://jitpack.io/v/gillesbraun/kvalidator.svg)](https://jitpack.io/#gillesbraun/kvalidator)

⚠ This library is classified as experimental. This means that functionality can be not fully working and change at
any time without notice ⚠

## Features

* Readable and declarative validation rules.
* Error messages with multilingual support.
 
## Installation
Required dependencies
- kotlin 1.4
- kotlinx.serialization: min 1.1.0
- kotlinx.datetime: min 0.1.1

```kts
repositories {
    maven { url 'https://jitpack.io' }
}

dependencies {
    implementation "com.github.gillesbraun:kvalidator:0.0.6"
}
```

## Usage

#### Validate using DSL and json as String

```kt
val testJson = """
    {
        "clientId": 65000,
        "aNumber": 15,
        "uid": "2f-bb-11-ef"
    }
""".trimIndent()

validate(testJson) {
    attribute("clientId") {
        required()
        isInteger()
    }
    attribute("aNumber") {
        required()
        isInteger()
    }
    attribute("uid") {
        required()
        isString()
    }
}
```

#### Example usage in Ktor

```kt
val json = Json {
    ignoreUnknownKeys = true
}

fun Application.module(testing: Boolean = false) {

    install(ContentNegotiation) {
        json(json)
    }

    install(StatusPages) {
        exception<ValidationException> { cause ->
            call.respond(HttpStatusCode(422, "Validation Failed"), cause)
        }
    }

    routing {
        post("/test") {
            val jsonText = call.receiveText()
            validate(jsonText, TestObject.rules)
            val obj = json.decodeFromString<TestObject>(jsonText)
            call.respond(obj)
        }
    }
}

@Serializable
data class TestObject(
    val clientId: Int,
    val aNumber: Int,
    val uid: String,
) {
    companion object {
        val rules = rules {
            attribute("clientId") {
                required()
                isInteger()
            }
            attribute("aNumber") {
                required()
                isInteger()
            }
            attribute("uid") {
                required()
                isString()
            }
        }
    }
}
```

#### Validate
```kt

val testJson = JsonObject(mapOf(
        "name" to JsonPrimitive("John"),
        "age" to JsonPrimitive(28),
        "isHuman" to JsonPrimitive(true),
))

val rules = mapOf<String, List<Rule>>(
        "name" to listOf(Alpha(), Required()),
        "age" to listOf(IsInteger(), Between(25, 48)),
        "isHuman" to listOf(IsBoolean()),
)

val validator = Validator(testJson, rules, /* lang = en */)
try {
    validator.validate()
} catch(e: ValidationException) {
    e.errors.forEach {(attribute, messages)->
        messages.forEach { msg ->
            println("Error: $attribute has error by $msg")
        }
    }
}

```

#### Utils
```kt
parseRule("max:255"): Rule
parseRules("required|max:255"): List<Rule>
stringifyRules("required|email|max:15|min:8|size:8|boolean")
stringifyRule("max:15")
```


## Available Rules

All rules will be implemented the same as they are in Laravel. Use their documentation for checking how exactly they work.

|laravel|kvalidator|
|-------|----------|
|Accepted|✅ Accepted|
|Active URL||
|After (Date)|✅ AfterDate|
|After Or Equal (Date)||
|Alpha|✅ Alpha|
|Alpha Dash|✅ AlphaDash|
|Alpha Numeric|✅ AlphaNum|
|Array|✅ IsArray|
|Bail||
|Before (Date)||
|Before Or Equal (Date)||
|Between|✅ Between|
|Boolean|✅ IsBoolean|
|Confirmed|✅ Confirmed|
|Date|✅ IsDate|
|Date Equals||
|Date Format||
|Different||
|Digits||
|Digits Between||
|Dimensions (Image Files)||
|Distinct||
|Email|✅ Email|
|Ends With||
|Exclude If||
|Exclude Unless||
|Exists (Database)||
|File||
|Filled||
|Greater Than||
|Greater Than Or Equal||
|Image (File)||
|In||
|In Array||
|Integer|✅ IsInteger|
|IP Address||
|JSON||
|Less Than||
|Less Than Or Equal||
|Max|✅ Max|
|MIME Types||
|MIME Type By File Extension||
|Min|✅ Min|
|Multiple Of||
|Not In|NotIn|
|Not Regex||
|Nullable||
|Numeric|✅ IsNumeric|
|Password||
|Present||
|Prohibited||
|Prohibited If||
|Prohibited Unless||
|Regular Expression||
|Required|✅ Required|
|Required If||
|Required Unless||
|Required With||
|Required With All||
|Required Without||
|Required Without All||
|Same||
|Size|✅ Size|
|Sometimes||
|Starts With||
|String|✅ IsString|
|Timezone||
|Unique (Database)||
|URL|✅ Url|
|UUID||


## Links
- [Available Validation Rules](https://laravel.com/docs/master/validation#available-validation-rules)
- [kotlinx.serialization](https://github.com/Kotlin/kotlinx.serialization)

## License
 MIT
