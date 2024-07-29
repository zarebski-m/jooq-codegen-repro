# jooqCodegen issue reproduction

Issue happens when Gradle daemon is enabled (which is the default; moreover, it is impossible to disable the daemon when using IntelliJ IDEA).

It passes on a fresh daemon, i.e. after `./gradlew --stop`, or when the daemon is completely disabled (--no-daemon` flag or a [Gradle property](./gradle.properties)).

## Steps to reproduce

1. Ensure that Gradle daemon is enabled.
2. Run `./gradlew jooqCodegen`. It should finish successfully.
3. Add a new migration file: `migration/src/main/resources/db/migration/V2__dummy.sql` with content:
  ```sql
  SELECT * FROM dummy;
  ```
4. Run `./gradlew jooqCodegen --stacktrace`. It should fail with root cause similar to this:
  ```
  java.io.FileNotFoundException: JAR entry db/migration/V2__dummy.sql not found in [REDACTED]]/migration/build/libs/migration.jar
  ```

Sometimes it might just pass. If it does, just keep adding new migration files and retrying `jooqCodegen`.
