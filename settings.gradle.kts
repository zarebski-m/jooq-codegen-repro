rootProject.name = "codegen-issue"

include(":model")
include(":migration")
include(":migration-container")

dependencyResolutionManagement {
    repositories {
        mavenCentral()
    }
}
