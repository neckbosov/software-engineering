plugins {
    kotlin("plugin.serialization")
    kotlin("jvm")
    java
}



repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib"))
    implementation("org.jetbrains.kotlin:kotlin-stdlib:1.5.21")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.3.0")

    implementation("com.auth0:java-jwt:3.18.2")

    // TODO REMOVE AFTER KTOR OAUTH!!!
    implementation(platform("org.http4k:http4k-bom:4.13.1.0")) // todo: used in oauth to catch callbacks, switch to ktor later
    implementation("org.http4k:http4k-core")
    implementation("org.http4k:http4k-server-netty")
    implementation("org.http4k:http4k-client-apache")
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}