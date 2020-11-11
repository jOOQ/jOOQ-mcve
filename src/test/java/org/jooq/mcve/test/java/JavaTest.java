package org.jooq.mcve.test.java;

import org.jooq.mcve.java.tables.records.TestRecord;
import org.junit.Test;

import static org.jooq.mcve.java.Tables.TEST;
import static org.junit.Assert.assertEquals;

public class JavaTest extends AbstractTest {

    @Test
    public void mcveTest() {
        TestRecord result =
        ctx.insertInto(TEST)
           .columns(TEST.VALUE)
           .values(42)
           .returning(TEST.ID)
           .fetchOne();

        result.refresh();
        assertEquals(42, (int) result.getValue());
    }
}
