rootProject.name = "ch.srgssr.launch.ebucoreplus"

dependencyResolutionManagement {

    repositories {
        mavenLocal()
        mavenCentral()
    }

    versionCatalogs {
        create("libs") {
            from(files("libs.versions.toml"))
        }
    }

}
