BEGIN TRANSACTION;
CREATE TABLE IF NOT EXISTS "ventas" (
	"categoria"	TEXT,
	"idVehiculo"	INTEGER,
	"marca"	TEXT,
	"modelo"	TEXT,
	"precioCompra"	INTEGER,
	"precioVenta"	INTEGER,
	"beneficio"	INTEGER,
	"dniComprador"	TEXT,
	"idVenta"	INTEGER,
	PRIMARY KEY("idVenta"),
	FOREIGN KEY("dniComprador") REFERENCES "Cliente"("dni")
);
INSERT INTO "ventas" ("categoria","idVehiculo","marca","modelo","precioCompra","precioVenta","beneficio","dniComprador","idVenta") VALUES ('cocheSegundaMano',1,'Corolla','Blanco',NULL,20000,NULL,NULL,1),
 ('cocheSegundaMano',1,'Corolla','Blanco',NULL,20000,NULL,NULL,2),
 ('coche',3,'Civic','Rojo',NULL,22000,NULL,NULL,3),
 ('coche',5,'Model S','Azul',NULL,80000,NULL,NULL,4),
 ('coche',39857821,'XC90','Gris',NULL,75000,NULL,NULL,5);
COMMIT;
