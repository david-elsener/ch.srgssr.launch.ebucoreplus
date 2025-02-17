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
    implementation(libs.owl.api)
    implementation(libs.java.classgenerator)
}

sourceSets {
    main {
        java {
            srcDirs("src/main/java", "src/main/generated")
        }
    }
}