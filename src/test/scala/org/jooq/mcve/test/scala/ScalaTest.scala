package org.jooq.mcve.test.scala

import org.jooq.DSLContext
import org.jooq.impl.DSL
import org.jooq.mcve.java.tables.records.TestRecord
import org.junit.After
import org.junit.Before
import org.junit.Test
import java.sql.Connection
import java.sql.DriverManager
import org.jooq.mcve.java.Tables.TEST
import org.junit.Assert.assertEquals


class ScalaTest {
  var connection: Connection = null
  var ctx: DSLContext = null

  @Before
  def setup(): Unit = {
    connection = DriverManager.getConnection("jdbc:h2:~/mcve", "sa", "")
    ctx = DSL.using(connection)
  }

  @After
  def after(): Unit = {
    ctx = null
    connection.close()
    connection = null
  }

  @Test
  def mcveTest(): Unit = {
    val result = ctx
      .insertInto(TEST)
      .columns(TEST.VALUE)
      .values(42)
      .returning(TEST.ID)
      .fetchOne

    result.refresh
    assertEquals(42, result.getValue)
  }
}
