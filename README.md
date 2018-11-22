Thank you very much for taking the time to report a bug to us, we greatly
appreciate it. Even more so, since you are about to take the time to create an
MCVE (Minimal Complete Verifiable Example). Thanks to you, we can make jOOQ an
even better product!

## How to use this project to prepare your MCVE

Create a fork from this project and then

```
git clone https://github.com/<your-user-name>/jOOQ-mcve
cd jOOQ-mcve
mvn clean install
```

It will:

- Use Flyway to install a sample schema located in `src/main/resources/db/migration` into an H2 database
- Run jOOQ's code generator on it
- Run a simple integration test

This should work without any additional setup on your side.

## How to prepare your MCVE

For your MCVE, you will have to adapt a few things, probably. This includes:

- The Java version: 
  - Go to the `pom.xml` file, search for `maven-compiler-plugin`, and adapt the `<source>` and `<target>` version there.
- The jOOQ version: 
  - Go to the `pom.xml` file, search for `org.jooq.version`, and adapt the version there.
- The JDBC driver: 
  - Go to the `pom.xml` file, replace the H2 driver `<dependency>` by yours, and adapt `${db.url}`, `${db.username}`, and `${db.password}`
  - Go to the `org.jooq.mcve.test.MCVETest` class and replace URL, username, and password there as well
  
In addition to the above, you probably need to adapt also:

- The code generator configuration in the `pom.xml`
- The actual test that is being run in `org.jooq.mcve.test.MCVETest`

## How to submit your MCVE

Found a way to reproduce the issue using the above procedure? Excellent! Now:

```
git commit -am "MCVE for issue #1234"
git push
```

And include a link to your repository `https://github.com/<your-user-name>/jOOQ-mcve` in your issue report. Done!

Thanks again for taking the time to do this. Looking forward to your MCVE