plugins {
    id("java")
    id("maven-publish")
}

group = "the.grid.smp"
version = "1.1"

repositories {
    mavenCentral()
    maven("https://hub.spigotmc.org/nexus/content/repositories/snapshots/")
}

dependencies {
    compileOnly("org.spigotmc:spigot-api:1.19-R0.1-SNAPSHOT")
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            artifactId = "communis"
            from(components["java"])
        }
    }

    repositories {
        mavenLocal()
    }
}

tasks.withType<ProcessResources> {
    val props = mapOf("version" to version)
    inputs.properties(props)

    filesMatching("plugin.yml") {
        expand(props)
    }
}