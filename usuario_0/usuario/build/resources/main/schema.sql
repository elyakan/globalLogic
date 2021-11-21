DROP TABLE IF EXISTS USUARIO;

CREATE TABLE USUARIO (
  id_usuario varchar(50)  PRIMARY KEY,
  name VARCHAR(50) NOT NULL,
  email VARCHAR(50) NOT NULL,
  password VARCHAR(50) NOT NULL,
  fcreate DATE NOT NULL,
  fmodified DATE NOT NULL,
  last_login DATE NOT NULL,
  isactive INTEGER NOT NULL
);

DROP TABLE IF EXISTS TELEFONO;

CREATE TABLE TELEFONO (
  id_telefono varchar(50)  PRIMARY KEY,
  id_usuario varchar(50)   not null,
  number VARCHAR(50) NOT NULL,
  citycode VARCHAR(50) NOT NULL,
  contrycode VARCHAR(50) NOT NULL
);

ALTER TABLE TELEFONO
    ADD CONSTRAINT fk_p_id_usuario FOREIGN KEY (id_usuario) REFERENCES USUARIO (id_usuario);