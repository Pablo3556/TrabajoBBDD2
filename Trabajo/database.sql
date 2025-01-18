DROP TABLE cantidadIngrediente CASCADE;
DROP TABLE ingredientes CASCADE;
DROP TABLE plato CASCADE;
DROP TABLE menu  CASCADE ;

DROP SEQUENCE seq_menus;
DROP SEQUENCE seq_ingredientes;
DROP SEQUENCE seq_platos;

CREATE TABLE cantidadIngrediente(
    idIng       VARCHAR(5),
    cantidad    NUMERIC(5),
    unidad      VARCHAR(12),
    CONSTRAINT "PK_CANTIDADINGREDIENTE.ID" PRIMARY KEY (idIng),
    CONSTRAINT "CH_CANTIDADINGREDIENTE.UNIDADES" CHECK ( unidad IN ('GRAMOS', 'UNIDADES', 'CENTILITROS')),
    CONSTRAINT "CH_CANTIDADINGREDIENTE.CANTIDAD" CHECK ( cantidad BETWEEN 0 AND 9999)
);

CREATE TABLE ingredientes(
    idI      VARCHAR(5),
    nombreI  VARCHAR(150),
    idIng    VARCHAR(5),
    CONSTRAINT "NN_INGREDIENTE.NOMBREI" CHECK ( nombreI is NOT NULL),
    CONSTRAINT "PK_INGREDIENTES.ID" PRIMARY KEY (idI),
    CONSTRAINT "UK_INGREDIENTES.ID" UNIQUE (nombreI),
    CONSTRAINT "FK_INGREDIENTE.IDING" FOREIGN KEY (idIng) REFERENCES cantidadIngrediente(idIng)
);


CREATE TABLE plato (
    idP         VARCHAR(5),
    idI         VARCHAR(5),
    nombreP     VARCHAR(150),
    descripcion VARCHAR(450),
    precio      NUMERIC(3,2),
    tipo        VARCHAR(10),
    CONSTRAINT "CH_PLATO.TIPO" CHECK ( tipo IN ('ENTRANTE', 'PRINCIPAL', 'POSTRE', 'INFANTIL')),
    CONSTRAINT "NN_PLATO.NOMBRE" CHECK ( nombreP is NOT NULL),
    CONSTRAINT "NN_PLATO.DESCRIPCION" CHECK ( descripcion is NOT NULL),
    CONSTRAINT "NN_PLATO.TIPO" CHECK ( tipo is NOT NULL),
    CONSTRAINT "UK_PLATO.NOMBRE" UNIQUE (nombreP),
    CONSTRAINT "PK_PLATO.IDP" PRIMARY KEY (idP),
    CONSTRAINT "FK_MENU.IDI" FOREIGN KEY (idI) REFERENCES ingredientes(idI)
);

CREATE TABLE menu (
    idM         VARCHAR(5),
    idP         VARCHAR(5),
    nombreM     VARCHAR(100),
    precio      NUMERIC(3,2),
    fechaDesde  DATE,
    fechaHasta  DATE,
    CONSTRAINT "NN_MENU.FECHADESDE" CHECK ( fechaDesde is NOT NULL),
    CONSTRAINT "NN_MENU.FECHAHASTA" CHECK ( fechaHasta is NOT NULL),
    CONSTRAINT "PK_MENU.IDM" PRIMARY KEY (idM),
    CONSTRAINT "NN_MENU.NOMBRE" CHECK ( nombreM is NOT NULL),
    CONSTRAINT "FK_MENU.IdP" FOREIGN KEY (idP) REFERENCES plato(idP)
);

CREATE SEQUENCE seq_menus
    MINVALUE 0
    MAXVALUE 99999
    START WITH 0
    INCREMENT BY 1;

CREATE SEQUENCE seq_platos
    MINVALUE 0
    MAXVALUE 99999
    START WITH 0
    INCREMENT BY 1;

CREATE SEQUENCE seq_ingredientes
    MINVALUE 0
    MAXVALUE 99999
    START WITH 0
    INCREMENT BY 1;
INSERT INTO cantidadIngrediente (idIng, cantidad, unidad)
VALUES ('CI001', 500, 'GRAMOS');
INSERT INTO cantidadIngrediente (idIng, cantidad, unidad)
VALUES ('CI002', 2, 'UNIDADES');
INSERT INTO cantidadIngrediente (idIng, cantidad, unidad)
VALUES ('CI003', 250, 'CENTILITROS');

INSERT INTO ingredientes (idI, nombreI, idIng)
VALUES ('I001', 'Tomate', 'CI001');
INSERT INTO ingredientes (idI, nombreI, idIng)
VALUES ('I002', 'Huevo', 'CI002');
INSERT INTO ingredientes (idI, nombreI, idIng)
VALUES ('I003', 'Leche', 'CI003');

INSERT INTO plato (idP, idI, nombreP, descripcion, precio, tipo)
VALUES ('P001', 'I001', 'Ensalada de Tomate', 'Ensalada fresca con tomate y aderezo.', 5.50, 'ENTRANTE');
INSERT INTO plato (idP, idI, nombreP, descripcion, precio, tipo)
VALUES ('P002', 'I002', 'Huevo Roto', 'Huevo roto con patatas fritas.', 7.00, 'PRINCIPAL');
INSERT INTO plato (idP, idI, nombreP, descripcion, precio, tipo)
VALUES ('P003', 'I003', 'Batido de Leche', 'Batido de leche natural.', 3.00, 'POSTRE');

INSERT INTO menu (idM, idP, nombreM, precio, fechaDesde, fechaHasta)
VALUES ('M001', 'P001', 'Menú de Verano', 5.00, '2023-06-01', '2023-08-31');
INSERT INTO menu (idM, idP, nombreM, precio, fechaDesde, fechaHasta)
VALUES ('M002', 'P002', 'Menú de Invierno', 5.00, '2023-12-01', '2024-02-28');
INSERT INTO menu (idM, idP, nombreM, precio, fechaDesde, fechaHasta)
VALUES ('M003', 'P003', 'Menú Infantil', 4.00, '2023-01-01', '2023-12-31');

