plugins {
    id("java")
    id("maven-publish")
}

group = "the.grid.smp"
version = "1.5"

repositories {
    mavenCentral()
    maven("https://repo.codemc.io/repository/maven-public/")
    maven("https://repo.papermc.io/repository/maven-public/")
}

dependencies {
    compileOnly("io.papermc.paper:paper-api:1.19-R0.1-SNAPSHOT")
    compileOnly("the.grid.smp:communis:1.5")
}

publishing {
    repositories {
        maven {
            name = "GitHub"
            url = uri("https://maven.pkg.github.com/TheGridSMP/communis")

            credentials {
                username = project.findProperty("gpr.user") as String? ?: System.getenv("USERNAME")
                password = project.findProperty("gpr.key") as String? ?: System.getenv("TOKEN")
            }
        }

        mavenLocal()
    }

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