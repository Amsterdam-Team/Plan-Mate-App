plugins {
    kotlin("jvm") version "1.9.0"
    kotlin("plugin.serialization") version "1.9.0"
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

    testImplementation("org.junit.jupiter:junit-jupiter-params:5.10.0")

    implementation("io.ktor:ktor-server-core:2.3.3")
    implementation("io.ktor:ktor-server-netty:2.3.3")

    implementation("io.ktor:ktor-client-cio:2.3.3")
    implementation("io.ktor:ktor-client-core:2.3.3")
    implementation("io.insert-koin:koin-ktor:3.5.3")
    implementation("io.ktor:ktor-server-content-negotiation:2.3.3")
    implementation("io.ktor:ktor-serialization-kotlinx-json:2.3.3")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.0")
    implementation(platform("org.mongodb:mongodb-driver-bom:5.4.0"))

    implementation("org.mongodb:mongodb-driver-kotlin-coroutine")
    implementation("org.mongodb:bson-kotlinx:5.4.0")
    implementation("ch.qos.logback:logback-classic:1.4.11")
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(17)
}