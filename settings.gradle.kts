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
include("jOOQ-mcve-java-sqlserver")
include("jOOQ-mcve-kotlin-h2")
include("jOOQ-mcve-kotlin-mysql")
include("jOOQ-mcve-kotlin-oracle")
include("jOOQ-mcve-kotlin-postgres")
include("jOOQ-mcve-kotlin-sqlite")
include("jOOQ-mcve-kotlin-sqlserver")
include("jOOQ-mcve-scala-h2")