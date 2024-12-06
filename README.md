Thank you very much for taking the time to report a bug to us, we greatly
appreciate it. Even more so, since you are about to take the time to create an
[MCVE (Minimal Complete Verifiable Example)](https://stackoverflow.com/help/minimal-reproducible-example). Thanks to you, we can make jOOQ an
even better product!

## How to use this project to prepare your MCVE

Create a fork from this project and then

```
git clone https://github.com/<your-user-name>/jOOQ-mcve
cd jOOQ-mcve
```

### If using Maven

```
cd <relevant module>
mvn verify
```

### If using Gradle

```
./gradlew :<relevant subproject>:check
```

## How to prepare your MCVE

For your MCVE, you will have to adapt a few things, probably. All likely locations that may need adaptation are marked with "TODO". This includes:

- The Java / kotlin / scala version (only if the Java version is important for your MCVE): 
  - **Using Maven** Go to the relevant `pom.xml` file, search for `java.version`, `kotlin.version`, `scala.version`, and adapt the version there.
  - **Using Gradle** We're currently working with the defaults only
- The jOOQ edition and version: 
  - **Using Maven** Go to the relevant `pom.xml` file, search for `org.jooq.groupId` and `org.jooq.version`, and adapt the version there.
  - **Using Gradle** Go to the main `build.gradle.kts` file and update `group` and `version` variables
- The JDBC driver: 
  - **Using Maven** Go to the relevant `pom.xml` file, replace the H2, MySQL, Oracle, PostgreSQL, or SQL Server driver `<dependency>` by yours, and adapt `${jooq.codegen.jdbc.url}`, `${jooq.codegen.jdbc.username}`, and `${jooq.codegen.jdbc.password}`, and replace the testcontainers integration, if applicable.
  - **Using Gradle** Go to the relevant `build.gradle.kts` file, replace the H2, MySQL, Oracle, PostgreSQL, or SQL Server driver in `dependencies { .. }` by yours, and adapt `${jooq.codegen.jdbc.url}`, `dbUsername`, and `dbPassword` variables, and replace the testcontainers integration, if applicable.
  - Go to the relevant test class (`org.jooq.mcve.test.java.JavaTest`, `org.jooq.mcve.test.kotlin.KotlinTest`, or `org.jooq.mcve.test.scala.ScalaTest`) and replace URL, username, and password there as well, if applicable
  
In addition to the above, you probably need to adapt also:

- The SQL script
- The code generator configuration in the `pom.xml` or `build.gradle.kts`
- The actual test that is being run in any of (depending on what you're using):
  - `org.jooq.mcve.test.java.h2.JavaTest`
  - `org.jooq.mcve.test.java.mysql.JavaTest`
  - `org.jooq.mcve.test.java.oracle.JavaTest`
  - `org.jooq.mcve.test.java.postgres.JavaTest`
  - `org.jooq.mcve.test.java.sqlserver.JavaTest`
  - `org.jooq.mcve.test.kotlin.h2.KotlinTest`
  - `org.jooq.mcve.test.kotlin.mysql.KotlinTest`
  - `org.jooq.mcve.test.kotlin.oracle.KotlinTest`
  - `org.jooq.mcve.test.kotlin.postgres.KotlinTest`
  - `org.jooq.mcve.test.kotlin.sqlserver.KotlinTest`
  - `org.jooq.mcve.test.scala.h2.ScalaTest`
  - `org.jooq.mcve.test.scala.mysql.ScalaTest`
  - `org.jooq.mcve.test.scala.oracle.ScalaTest`
  - `org.jooq.mcve.test.scala.postgres.ScalaTest`
  - `org.jooq.mcve.test.scala.sqlserver.ScalaTest`

When you've set up your MCVE, run these statements again:

### Using Maven

```
mvn clean verify
```

### Using Gradle

```
./gradlew :<relevant subproject>:check
```

## How to submit your MCVE

Found a way to reproduce the issue using the above procedure? Excellent! Now, either commit the change to your fork:

```
git add .
git commit -m "MCVE for issue jOOQ/jOOQ#1234"
git push
```

And include a link to your repository `https://github.com/<your-user-name>/jOOQ-mcve` in your issue report. Or, just attach a zip file of the project to the issue. Done!

Thanks again for taking the time to do this. Looking forward to your MCVE
