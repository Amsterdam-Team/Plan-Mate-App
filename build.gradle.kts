plugins {
    kotlin("jvm") version "1.9.0"
}

group = "org.amsterdam.planmate"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))


    implementation("io.insert-koin:koin-core:4.0.2")
    implementation("org.jetbrains.kotlinx:kotlinx-datetime:0.5.0")
    implementation("com.jsoizo:kotlin-csv-jvm:1.10.0")
    testImplementation("org.junit.jupiter:junit-jupiter:5.8.1")
    testImplementation("io.mockk:mockk:1.14.0")
    testImplementation ("com.google.truth:truth:1.4.4")
    implementation("io.insert-koin:koin-core:4.0.2")
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(17)
}