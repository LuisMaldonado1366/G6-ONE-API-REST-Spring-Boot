CREATE TABLE usuarios(
    id bigint NOT NULL AUTO_INCREMENT,
    login VARCHAR(100) NOT NULL,
    clave VARCHAR(100) NOT NULL,

    PRIMARY KEY(id)
);