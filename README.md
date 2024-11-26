Thank you very much for taking the time to report a bug to us, we greatly appreciate it.
Even more so, since you are about to take the time to create an
[MCVE (Minimal Complete Verifiable Example)](https://stackoverflow.com/help/minimal-reproducible-example).
Thanks to you, we can make jOOQ an even better product!


This project contains —for each supported language (Java, Kotlin, Scala) and SQL dialect (H2, MySQL, Oracle, PostgreSQL, SQL Server)—
an minimal project from which you can derive your MCVE. Each of these projects sits in its own folder and:

- Uses testcontainers (for MySQL, Oracle, PostgreSQL, SQL Server) to set up a database instance, and makes it available to jOOQ and JUnit.
- Installs a sample schema located in `src/main/resources/db/migration` into an file-based H2 database or testcontainers managed MySQL, Oracle, PostgreSQL, SQL Server database.
- Runs jOOQ's code generator on it.
- Runs a simple integration test.

This should work without any additional setup on your side, other than having a JDK, build tool (only when using Maven), and Docker available.
If you need to have the commercial distribution of jOOQ installed, e.g. for using Oracle or SQL Server, that must be installed as well.
In case you do not have Docker, you can still run the H2 based examples directly, ignoring the MySQL, Oracle, PostgreSQL, SQL Server based ones.


## How to use this project to prepare your MCVE


First you need to create a fork of this project (do this from the [GitHub web UI](https://docs.github.com/en/pull-requests/collaborating-with-pull-requests/working-with-forks/fork-a-repo))
so you can publish your MCVE once done. Then clone the repository you just forked and `cd` into it:


```
git clone https://github.com/<your-user-name>/jOOQ-mcve
cd jOOQ-mcve
```

From here you will either make use [Maven](https://maven.apache.org) or [Gradle](https://gradle.org) to build your MCVE.
Therefore, the following instructions are split up per build tool.


### Instructions when using Maven

First `cd` into the folder that fits you combination of language and SQL dialect, and make sure the `verify` task runs successfully:

```
cd <relevant module>
mvn verify
```

#### How to prepare your MCVE

For your MCVE, you will have to adapt a few things, probably. All likely locations that may need adaptation are marked with "TODO". This includes:

- The Java / Kotlin / Scala version: 
  - Go to the relevant `pom.xml` file, search for `java.version`, `kotlin.version`, `scala.version`, and adapt the version there.
- The jOOQ edition and version: 
  - Go to the relevant `pom.xml` file, search for `org.jooq.groupId` and `org.jooq.version`, and adapt the version there.
- The JDBC driver: 
  - Go to the relevant `pom.xml` file, replace the H2, MySQL, Oracle, PostgreSQL, or SQL Server driver `<dependency>` by yours, and adapt `${jooq.codegen.jdbc.url}`, `${jooq.codegen.jdbc.username}`, and `${jooq.codegen.jdbc.password}`.
  - Go to the relevant `pom.xml` file, replace the testcontainers integration, if applicable.
  - Go to the relevant test class (`org.jooq.mcve.test.java.JavaTest`, `org.jooq.mcve.test.kotlin.KotlinTest`, or `org.jooq.mcve.test.scala.ScalaTest`) and replace URL, username, and password there as well, if applicable.
  
In addition to the above, you probably also need to adapt:

- The SQL script
- The code generator configuration in the `pom.xml`
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

```
mvn clean verify
```


### Instructions when using Gradle

First comment out all the subprojects that are not part of your MCVE in `settings.gradle.kts`: you usually work only on one subproject.

Then make sure the following Gradle task runs succesfully:

```
./gradlew :<relevant subproject>:check
```


#### How to prepare your MCVE

For your MCVE, you will have to adapt a few things, probably. This may include:

- The Java version (only if the Java version is important for your MCVE):
  - Go to the relevant subproject's build file (i.e. `$REPO_ROOT/<relevant subproject>/build.gradle.kts`) and add the following line (adapting the version numer) somewhere after the `import` statements:


```kotlin
java.toolchain.languageVersion = JavaLanguageVersion.of(17)
```

- The Kotlin / Scala version: 
  - Go to the relevant subproject's build file (i.e. `$REPO_ROOT/<relevant subproject>/build.gradle.kts`) and adapt the Kotlin (e.g. `kotlin("jvm") version "x.y.z"`) or Scala (e.g. `implementation("org.scala-lang:scala-library:x.y.z")`) version.
- The jOOQ edition and version: 
  - This is done in the main Gradle file, located in `REPO_ROOT/build.gradle.kts`.
- The JDBC driver: 
  - Go to the relevant subproject's build file (i.e. `$REPO_ROOT/<relevant subproject>/build.gradle.kts`):
    - Replace **all** H2, MySQL, Oracle, PostgreSQL, or SQL Server driver dependency specifications (e.g. `mysql:mysql-connector-java:8.0.33` for MySQL) by the version that required for your MCVE.
    - Replace the testcontainers integration, if applicable.
  - Go to the relevant test class (`org.jooq.mcve.test.java.JavaTest`, `org.jooq.mcve.test.kotlin.KotlinTest` or `org.jooq.mcve.test.scala.ScalaTest`) and replace URL, username, and password there as well, if applicable.
  
In addition to the above, you probably also need to adapt:

- The SQL script
- The code generator configuration in `$REPO_ROOT/<relevant subproject>/build.gradle.kts`
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

```
./gradlew :<relevant subproject>:check
```


## How to submit your MCVE

Found a way to reproduce the issue using the above procedure? Excellent! Now, either commit the change to your fork:

```
git add .
git commit -m "MCVE for issue #1234"
git push
```

And include a link to your repository `https://github.com/<your-user-name>/jOOQ-mcve` in your issue report. Or, just attach a zip file of the project to the issue. Done!

Thanks again for taking the time to do this. Looking forward to your MCVE.

