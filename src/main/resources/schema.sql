CREATE SCHEMA IF NOT EXISTS usermanager;
DROP TABLE IF EXISTS usermanager.usuario;

DROP SEQUENCE IF EXISTS usermanager.usuario_seq;

CREATE SEQUENCE usermanager.usuario_seq INCREMENT BY 1 START WITH 5;

CREATE TABLE usermanager.usuario (
  ID INT PRIMARY KEY NOT NULL,
  NOMBRE VARCHAR(100) NOT NULL,
  APELLIDOS VARCHAR(100) NOT NULL,
  EMAIL VARCHAR(100) NOT NULL,
  PASSWORD VARCHAR(100) NOT NULL,
  AVATAR VARCHAR(1000),
  ROL Varchar(10) NOT NULL DEFAULT 'USR',
  FECHA_NACIMIENTO DATE NOT NULL,
  FECHA_ALTA TIMESTAMP NOT NULL DEFAULT now()
  FECHA_BAJA TIMESTAMP -- No borramos los usuarios de BBDD cuando se dan de baja ya que se tendrian que preservar (facturas, documentos, etc)
);

-- Password 1234 para todos los usuarios
INSERT into usermanager.usuario(id,nombre,apellidos,email,password,avatar,rol,FECHA_NACIMIENTO) values(0,'admin','admin sistemas','admin@dru-id.com','$2a$10$6QfTBtrR1QIKb1qn6LOW9Or/tzztgtuISmLjBC3ttrXse3QtCrHRC','avatar','ROLE_ADMIN','17-09-1975');
INSERT into usermanager.usuario(id,nombre,apellidos,email,password,avatar,FECHA_NACIMIENTO) values(1,'usuario','usuario Prueba','usuario@dru-id.com','$2a$10$6QfTBtrR1QIKb1qn6LOW9Or/tzztgtuISmLjBC3ttrXse3QtCrHRC','avatar','20-11-1987');
INSERT into usermanager.usuario(id,nombre,apellidos,email,password,avatar,FECHA_NACIMIENTO) values(2,'Manuel','Castillo Perez','manuel@dru-id.com','$2a$10$6QfTBtrR1QIKb1qn6LOW9Or/tzztgtuISmLjBC3ttrXse3QtCrHRC','avatar','07-06-1992');
INSERT into usermanager.usuario(id,nombre,apellidos,email,password,avatar,FECHA_NACIMIENTO) values(3,'Carmen','Romero Martinez','carmen@dru-id.com','$2a$10$6QfTBtrR1QIKb1qn6LOW9Or/tzztgtuISmLjBC3ttrXse3QtCrHRC','avatar','09-01-2002');
INSERT into usermanager.usuario(id,nombre,apellidos,email,password,avatar,FECHA_NACIMIENTO) values(4,'Marta','Casas Boija','marta@dru-id.com','$2a$10$6QfTBtrR1QIKb1qn6LOW9Or/tzztgtuISmLjBC3ttrXse3QtCrHRC','avatar','13-04-1997');


-- Tablas para la gestion de las sesiones 
DROP INDEX IF EXISTS SPRING_SESSION_IX1;
DROP INDEX IF EXISTS SPRING_SESSION_IX2;
DROP INDEX IF EXISTS SPRING_SESSION_IX3;
DROP TABLE IF EXISTS SPRING_SESSION_ATTRIBUTES;
DROP TABLE IF EXISTS SPRING_SESSION;

CREATE TABLE  SPRING_SESSION(
    PRIMARY_ID CHAR(36) NOT NULL,
    SESSION_ID CHAR(36) NOT NULL,
    CREATION_TIME BIGINT NOT NULL,
    LAST_ACCESS_TIME BIGINT NOT NULL,
    MAX_INACTIVE_INTERVAL INT NOT NULL,
    EXPIRY_TIME BIGINT NOT NULL,
    PRINCIPAL_NAME VARCHAR(100),
    CONSTRAINT SPRING_SESSION_PK PRIMARY KEY (PRIMARY_ID)
);

CREATE UNIQUE INDEX SPRING_SESSION_IX1 ON SPRING_SESSION (SESSION_ID);
CREATE INDEX SPRING_SESSION_IX2 ON SPRING_SESSION (EXPIRY_TIME);
CREATE INDEX SPRING_SESSION_IX3 ON SPRING_SESSION (PRINCIPAL_NAME);

CREATE TABLE SPRING_SESSION_ATTRIBUTES (
    SESSION_PRIMARY_ID CHAR(36) NOT NULL,
    ATTRIBUTE_NAME VARCHAR(200) NOT NULL,
    ATTRIBUTE_BYTES BLOB NOT NULL,
    CONSTRAINT SPRING_SESSION_ATTRIBUTES_PK PRIMARY KEY (SESSION_PRIMARY_ID, ATTRIBUTE_NAME),
    CONSTRAINT SPRING_SESSION_ATTRIBUTES_FK FOREIGN KEY (SESSION_PRIMARY_ID) REFERENCES SPRING_SESSION(PRIMARY_ID) ON DELETE CASCADE
);
