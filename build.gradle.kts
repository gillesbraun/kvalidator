
val LIBRARY_VERSION_NAME = "0.0.7"
val GROUP_ID = "com.github.gillesbraun"
val ARTIFACT_ID = rootProject.name
val KOTLINX_SERIALIZATION_CORE = "1.1.0"
val KOTLINX_DATETIME = "0.1.1"
val SHORT_DESC = """
    Validator for kotlin json serialization.
""".trimIndent()
val VCS_URL = "https://github.com/gillesbraun/kvalidator.git"
val WEBSITE_URL = "https://github.com/gillesbraun/kvalidator"
val ISSUE_TRACKER_URL = "https://github.com/gillesbraun/kvalidator/issues"
val CONTACT_EMAIL = "braun.gilles.111@gmail.com"
val CONTACT_NAME = "Gilles Braun"

plugins {
    kotlin("jvm") version "1.4.30"
    kotlin("plugin.serialization") version "1.4.30"
    id("maven-publish")
}

kotlin {
    explicitApi()
}

java {
    withSourcesJar()
    withJavadocJar()
}

group = GROUP_ID
version = LIBRARY_VERSION_NAME

fun printResults(desc: TestDescriptor, result: TestResult) {
    if (desc.parent != null) {
        val output = result.run {
            "Results: $resultType (" +
                    "$testCount tests, " +
                    "$successfulTestCount successes, " +
                    "$failedTestCount failures, " +
                    "$skippedTestCount skipped" +
                    ")"
        }
        val testResultLine = "|  $output  |"
        val repeatLength = testResultLine.length
        val seperationLine = "-".repeat(repeatLength)
        println(seperationLine)
    }
}

publishing {
    publications {
        create<MavenPublication>("mavenJava") {
            from(components["java"])
            versionMapping {
                usage("java-api") {
                    fromResolutionOf("runtimeClasspath")
                }
                usage("java-runtime") {
                    fromResolutionResult()
                }
            }
            pom {
                name.set(provider { "$GROUP_ID:$ARTIFACT_ID" })
                description.set(provider { project.description ?: SHORT_DESC })
                url.set(VCS_URL)
                developers {
                    developer {
                        name.set(CONTACT_NAME)
                        email.set(CONTACT_EMAIL)
                    }
                }
                scm {
                    connection.set(VCS_URL)
                    developerConnection.set(VCS_URL)
                    url.set(ISSUE_TRACKER_URL)
                }
                licenses {
                    license {
                        name.set("MIT License")
                        url.set("http://www.opensource.org/licenses/mit-license.php")
                    }
                }
            }
        }
    }
}

repositories {
    mavenCentral()
}

dependencies {
    // Align versions of all Kotlin components
    implementation(platform("org.jetbrains.kotlin:kotlin-bom"))

    // components
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-core:$KOTLINX_SERIALIZATION_CORE")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:$KOTLINX_SERIALIZATION_CORE")
    implementation("org.jetbrains.kotlinx:kotlinx-datetime:$KOTLINX_DATETIME")

    // Use the Kotlin test library.
    testImplementation("org.jetbrains.kotlin:kotlin-test")

    // Use the Kotlin JUnit integration.
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit")
}
