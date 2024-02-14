plugins {
    id("java")
    id("maven-publish")
}

group = "the.grid.smp"
version = "1.6.2"

repositories {
    mavenCentral()
    maven("https://repo.codemc.io/repository/maven-public/")
    maven("https://repo.papermc.io/repository/maven-public/")
}

dependencies {
    compileOnly("io.papermc.paper:paper-api:1.19-R0.1-SNAPSHOT")
}

publishing {
    publications {
        register<MavenPublication>("maven") {
            artifactId = "communis"
            from(components["java"])
        }
    }
}

tasks.withType<ProcessResources> {
    val props = mapOf("version" to version)
    inputs.properties(props)

    filesMatching("plugin.yml") {
        expand(props)
    }
}