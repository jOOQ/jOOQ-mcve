subprojects {
    repositories {
        mavenLocal()
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

    group = "org.jooq"
    version = "3.20.0"
}