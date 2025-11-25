plugins {
    kotlin("jvm") version "2.2.0"
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation("org.junit.jupiter:junit-jupiter:5.10.0")
    testImplementation("org.junit.platform:junit-platform-suite:1.10.0")
    testImplementation("org.jetbrains.kotlin:kotlin-test")

    testImplementation("io.rest-assured:rest-assured:5.4.0")
    testImplementation("io.rest-assured:kotlin-extensions:5.4.0")
}

tasks.test {
    useJUnitPlatform()
    testLogging {
        events("passed", "skipped", "failed") // affiche tous les tests
        exceptionFormat = org.gradle.api.tasks.testing.logging.TestExceptionFormat.FULL // détail des erreurs
        showStandardStreams = true // affiche les println et logs
    }
}
kotlin {
    jvmToolchain(24)
}

