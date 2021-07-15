/*
 * This work is dual-licensed
 * - under the Apache Software License 2.0 (the "ASL")
 * - under the jOOQ License and Maintenance Agreement (the "jOOQ License")
 * =============================================================================
 * You may choose which license applies to you:
 *
 * - If you're using this work with Open Source databases, you may choose
 *   either ASL or jOOQ License.
 * - If you're using this work with at least one commercial database, you must
 *   choose jOOQ License
 *
 * For more information, please visit http://www.jooq.org/licenses
 *
 * Apache Software License 2.0:
 * -----------------------------------------------------------------------------
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * jOOQ License and Maintenance Agreement:
 * -----------------------------------------------------------------------------
 * Data Geekery grants the Customer the non-exclusive, timely limited and
 * non-transferable license to install and use the Software under the terms of
 * the jOOQ License and Maintenance Agreement.
 *
 * This library is distributed with a LIMITED WARRANTY. See the jOOQ License
 * and Maintenance Agreement for more details: http://www.jooq.org/licensing
 */
package org.jooq.mcve.test;

import org.flywaydb.core.Flyway;
import org.jooq.DSLContext;
import org.jooq.DeleteConditionStep;
import org.jooq.impl.DSL;
import org.jooq.mcve.tables.records.TRecord;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.DriverManager;

import static org.jooq.mcve.Tables.T;
import static org.jooq.mcve.Tables.U;

/**
 * Because a test container is used to generate sources you need to run <i>RunCodeGenerator</i> first.
 * Which means you might need to comment out compiles errors in here first to get RunCodeGenerator to run.
 * Then uncomment and run this test.
 */
public class MCVETest {

  Connection connection;
  DSLContext ctx;

  @Before
  public void setup() throws Exception {
    var url = "jdbc:tc:mysql:8://localhost/mcve";
    connection = DriverManager.getConnection(url);
    ctx = DSL.using(connection);

    var flyway = Flyway.configure().dataSource(url, "", "").load();
    flyway.migrate();
  }

  @After
  public void after() throws Exception {
    ctx = null;
    connection.close();
    connection = null;
  }

  @Test
  public void mcveTest() {
    var query = ctx.deleteFrom(T)
        .using(T.join(U).on(T.I.eq(U.J)))
        .where(T.I.eq(1));
    System.out.println(query);
    query.execute();
  }
}
