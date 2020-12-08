package org.jooq.mcve.test.java;

import org.jooq.DSLContext;
import org.jooq.impl.DSL;
import org.junit.After;
import org.junit.Before;

import java.sql.Connection;
import java.sql.DriverManager;

public abstract class AbstractTest {

    public Connection connection;
    public DSLContext ctx;

    @Before
    public void setup() throws Exception {
        connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/version4007", "postgres", "notnull");
        ctx = DSL.using(connection);
    }

    @After
    public void after() throws Exception {
        ctx = null;
        connection.close();
        connection = null;
    }
}
