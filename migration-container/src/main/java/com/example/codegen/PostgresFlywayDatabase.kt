package com.example.codegen

import org.flywaydb.core.Flyway
import org.jooq.DSLContext
import org.jooq.SQLDialect
import org.jooq.exception.DataAccessException
import org.jooq.impl.DSL
import org.jooq.meta.postgres.PostgresDatabase
import org.testcontainers.containers.PostgreSQLContainer
import org.testcontainers.utility.DockerImageName
import java.nio.charset.StandardCharsets

class PostgresFlywayDatabase : PostgresDatabase() {
    private lateinit var container: PostgreSQLContainer<*>

    override fun create0(): DSLContext {
        if (connection != null) return DSL.using(connection, SQLDialect.POSTGRES)

        try {
            container = PostgreSQLContainer(
                DockerImageName.parse("postgres:16-alpine")
                    .asCompatibleSubstituteFor("postgres")
            )

            container.start()
            connection = container.createConnection("")

            Flyway.configure()
                .dataSource(container.getJdbcUrl(), "test", "test")
                .locations("db/migration")
                .encoding(StandardCharsets.UTF_8)
                .load()
                .migrate()

            return DSL.using(connection, SQLDialect.POSTGRES)
        } catch (e: Exception) {
            throw DataAccessException("Error while preparing schema for code generation", e)
        }
    }

    override fun close() {
        container.close()
        super.close()
    }
}
