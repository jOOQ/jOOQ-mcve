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

import org.jooq.SQLDialect;
import org.jooq.impl.DSL;
import org.junit.Test;

public class MCVETest {

    @Test
    public void testDropForeignKeyParses() {
        String sql = "ALTER TABLE `address` DROP FOREIGN KEY `fk_address_city`;";
        DSL.using(SQLDialect.MYSQL_8_0)
           .parser()
           .parse(sql);
    }

    @Test
    public void testYearDataType() {
        String sql = "CREATE TABLE film (\n" +
                "  film_id SMALLINT UNSIGNED NOT NULL AUTO_INCREMENT,\n" +
                "  title VARCHAR(255) NOT NULL,\n" +
                "  description TEXT DEFAULT NULL,\n" +
                "  release_year YEAR DEFAULT NULL,\n" +
                "  language_id TINYINT UNSIGNED NOT NULL,\n" +
                "  original_language_id TINYINT UNSIGNED DEFAULT NULL,\n" +
                "  rental_duration TINYINT UNSIGNED NOT NULL DEFAULT 3,\n" +
                "  rental_rate DECIMAL(4,2) NOT NULL DEFAULT 4.99,\n" +
                "  length SMALLINT UNSIGNED DEFAULT NULL,\n" +
                "  replacement_cost DECIMAL(5,2) NOT NULL DEFAULT 19.99,\n" +
                "  rating ENUM('G','PG','PG-13','R','NC-17') DEFAULT 'G',\n" +
                "  special_features SET('Trailers','Commentaries','Deleted Scenes','Behind the Scenes') DEFAULT NULL,\n" +
                "  last_update TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,\n" +
                "  PRIMARY KEY  (film_id),\n" +
                "  KEY idx_title (title),\n" +
                "  KEY idx_fk_language_id (language_id),\n" +
                "  KEY idx_fk_original_language_id (original_language_id),\n" +
                "  CONSTRAINT fk_film_language FOREIGN KEY (language_id) REFERENCES language (language_id) ON DELETE RESTRICT ON UPDATE CASCADE,\n" +
                "  CONSTRAINT fk_film_language_original FOREIGN KEY (original_language_id) REFERENCES language (language_id) ON DELETE RESTRICT ON UPDATE CASCADE\n" +
                ")ENGINE=InnoDB DEFAULT CHARSET=utf8;";
        DSL.using(SQLDialect.MYSQL_8_0)
           .parser()
           .parse(sql);
    }
}
