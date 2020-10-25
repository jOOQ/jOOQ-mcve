package org.jooq.mcve.codegen;

import org.flywaydb.core.Flyway;
import org.jooq.codegen.GenerationTool;
import org.testcontainers.containers.MySQLContainer;

import java.nio.file.Files;
import java.nio.file.Path;

public class RunCodeGenerator {

  public static void main(String[] args) throws Exception {
    var container = new MySQLContainer<>("mysql:8").withDatabaseName("mcve");
    container.start();
    var url = container.getJdbcUrl();

    var flyway = Flyway.configure().dataSource(url, "root", "test").load();
    flyway.migrate();

    var jooqXml = Files.readString(Path.of("jooq.xml"))
        .replace("<url>jdbc:tc:mysql:8://localhost/mcve</url>", "<url>" + url + "</url>");

    GenerationTool.generate(jooqXml);
    container.stop();
  }
}
