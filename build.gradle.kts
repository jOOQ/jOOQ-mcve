subprojects {
    repositories {
        mavenLocal()
        mavenCentral()
        maven {
            url = uri("https://repo.jooq.org/snapshots")
            credentials {
                username = System.getProperty("jooqUsername")
                password = System.getProperty("jooqPassword")
            }
        }
    }

    group = "org.jooq"
    version = "3.19.9"
}