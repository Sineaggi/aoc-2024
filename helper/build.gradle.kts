plugins {
    `java-library`
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
}

testing {
    suites {
        val test by getting(JvmTestSuite::class) {
            useJUnitJupiter(libs.versions.junit)
            targets.configureEach {
                testTask.configure {
                    jvmArgs("--enable-preview")
                }
            }
        }
    }
}

tasks.withType<JavaCompile>().configureEach {
    options.compilerArgs.add("--enable-preview")
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(23))
    }
}
