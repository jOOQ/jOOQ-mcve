DROP TABLE IF EXISTS jooq_test;

CREATE TABLE jooq_test (
  id    SERIAL NOT NULL,
  value BIGINT,

  CONSTRAINT pk_test PRIMARY KEY (id)
);