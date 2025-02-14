plugins {
    `version-catalog`
    java
    idea
    application
    `maven-publish`
    alias(libs.plugins.lombok)
}

tasks.withType<JavaCompile>().configureEach {
    options.encoding = "UTF-8"
}

java {
    sourceCompatibility = JavaVersion.VERSION_21
}

application {
    mainClass.set("ch.srgssr.launch.ebucoreplus.DomainGenerator")
}

dependencies {
    implementation(libs.slf4j.api)
    implementation(libs.logback.core)
    implementation(libs.logback.classic)
    implementation(libs.apache.jena)
}