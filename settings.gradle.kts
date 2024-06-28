pluginManagement {
    repositories {
        mavenLocal()
        gradlePluginPortal()
        mavenCentral()
        maven {
            url = uri("https://repo.jooq.org/repo")
            credentials {
                username = System.getProperty("jooqUsername")
                password = System.getProperty("jooqPassword")
            }
        }
        maven {
            url = uri("https://repo.jooq.org/snapshots")
            credentials {
                username = System.getProperty("jooqUsername")
                password = System.getProperty("jooqPassword")
            }
        }
    }
}

rootProject.name = "jOOQ-mcve"
include("jOOQ-mcve-java-h2")
include("jOOQ-mcve-java-mysql")
include("jOOQ-mcve-java-oracle")
include("jOOQ-mcve-java-postgres")
include("jOOQ-mcve-java-sqlite")