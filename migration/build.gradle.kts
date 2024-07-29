plugins {
    kotlin("jvm")
}

tasks.jar {
    isReproducibleFileOrder = true
    isPreserveFileTimestamps = false
    outputs.cacheIf { true }
}
