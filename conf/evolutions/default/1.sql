# --- !Ups

CREATE SEQUENCE secret_id_seq;
CREATE TABLE secret (
    id integer NOT NULL DEFAULT nextval('secret_id_seq'),
    secretText varchar(255),
    secretResource varchar(255),
    visited integer
);
 
# --- !Downs
 
DROP TABLE secret;
DROP SEQUENCE secret_id_seq;