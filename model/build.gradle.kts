plugins {
    kotlin("jvm")
    id("org.jooq.jooq-codegen-gradle") version "3.19.10"
}

dependencies {
    api("org.jooq:jooq:3.19.10")

    jooqCodegen("org.postgresql:postgresql:42.2.23")
    jooqCodegen(project(":migration-container"))
}

tasks {
    compileKotlin {
        dependsOn(jooqCodegen)
    }

    compileJava {
        dependsOn(jooqCodegen)
    }
}

jooq {
    configuration {
        jdbc {
            driver = "org.testcontainers.jdbc.ContainerDatabaseDriver"
            url = ""
        }

        generator {
            name = "org.jooq.codegen.KotlinGenerator"

            database {
                name = "com.example.codegen.PostgresFlywayDatabase"
                inputSchema = "public"
                excludes = "flyway_.*"
                isUnsignedTypes = false
                isOutputCatalogToDefault = true
                isOutputCatalogToDefault = true
            }

            generate {
                isPojos = true
                isPojosAsKotlinDataClasses = true
                isKotlinNotNullPojoAttributes = true
                isKotlinNotNullRecordAttributes = true
                isKotlinNotNullInterfaceAttributes = true
            }

            target {
                packageName = "com.example.model"
            }
        }
    }
}
