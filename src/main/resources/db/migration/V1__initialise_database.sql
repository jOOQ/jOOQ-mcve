DROP SCHEMA IF EXISTS mcve CASCADE;

CREATE SCHEMA mcve;

CREATE TABLE mcve.test (
  id    INT NOT NULL AUTO_INCREMENT,
  value INT,
  
  CONSTRAINT pk_test PRIMARY KEY (id) 
);

CREATE TABLE mcve.test_forced_type (
  id    BIGINT NOT NULL AUTO_INCREMENT,
  value INT,

  CONSTRAINT pk_test_forced_type PRIMARY KEY (id)
);