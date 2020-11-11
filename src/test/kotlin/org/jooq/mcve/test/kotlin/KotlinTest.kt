package org.jooq.mcve.test.kotlin

import org.jooq.DSLContext
import org.jooq.impl.DSL
import org.jooq.mcve.java.Tables
import org.jooq.mcve.java.Tables.TEST
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import java.sql.Connection
import java.sql.DriverManager

class KotlinTest {

    var connection: Connection? = null
    var ctx: DSLContext? = null
    fun ctx(): DSLContext = ctx!!

    @Before
    fun setup() {
        connection = DriverManager.getConnection("jdbc:h2:~/mcve", "sa", "")
        ctx = DSL.using(connection)
    }

    @After
    fun after() {
        ctx = null
        connection!!.close()
        connection = null
    }

    @Test
    fun mcveTest() {
        val result = ctx()
                .insertInto(TEST)
                .columns(TEST.VALUE)
                .values(42)
                .returning(TEST.ID)
                .fetchOne()

        result?.refresh()
        assertEquals(42, result?.value)
    }
}