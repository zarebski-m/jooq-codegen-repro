plugins {
    kotlin("jvm")
}

dependencies {
    api("org.testcontainers:postgresql:1.20.0")
    api("org.jooq:jooq-meta:3.19.10")

    implementation("org.postgresql:postgresql:42.2.23")
    implementation("org.flywaydb:flyway-database-postgresql:10.16.0")
    implementation(project(":migration"))
}
