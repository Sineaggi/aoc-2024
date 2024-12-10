plugins {
    java
    application
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation(projects.helper)
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

application {
    mainClass.set("org.example.Day2")
}

tasks.withType<JavaCompile>().configureEach {
    options.compilerArgs.add("--enable-preview")
}

tasks.named<JavaExec>("run") {
    jvmArgs("--enable-preview")
}

tasks.test {
    //useJUnitPlatform()
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(23))
    }
}