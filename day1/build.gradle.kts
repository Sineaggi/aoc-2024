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
    //estImplementation(platform("org.junit:junit-bom:5.10.0"))
    //estImplementation("org.junit.jupiter:junit-jupiter")
}

application {
    mainClass.set("org.example.Main")
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