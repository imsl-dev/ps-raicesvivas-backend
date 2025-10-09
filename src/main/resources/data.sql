-- Script de datos iniciales para Spring Boot
-- Archivo: src/main/resources/data.sql

-- Insertar provincias argentinas (solo si no existen)
INSERT INTO provincias (nombre)
SELECT * FROM (VALUES
                   ('Buenos Aires'),
                   ('Catamarca'),
                   ('Chaco'),
                   ('Chubut'),
                   ('Córdoba'),
                   ('Corrientes'),
                   ('Entre Ríos'),
                   ('Formosa'),
                   ('Jujuy'),
                   ('La Pampa'),
                   ('La Rioja'),
                   ('Mendoza'),
                   ('Misiones'),
                   ('Neuquén'),
                   ('Río Negro'),
                   ('Salta'),
                   ('San Juan'),
                   ('San Luis'),
                   ('Santa Cruz'),
                   ('Santa Fe'),
                   ('Santiago del Estero'),
                   ('Tierra del Fuego'),
                   ('Tucumán'),
                   ('Ciudad Autónoma de Buenos Aires')
              ) AS v(nombre)
WHERE NOT EXISTS (SELECT 1 FROM provincias WHERE provincias.nombre = v.nombre);

INSERT INTO usuarios (email, password, nombre, apellido, tipo_documento, nro_doc, rol, provincia_id, puntos)
SELECT * FROM (VALUES
                   ('admin@raicesvivas.com', 'admin', 'Carlos', 'Administrador', 'DNI', '12345678', 'ADMIN', 5, 0),
                   ('organizador@raicesvivas.com', 'admin', 'María', 'Organizadora', 'DNI', '23456789', 'ORGANIZADOR', 5, 100),
                   ('usuario@raicesvivas.com', 'admin', 'Juan', 'Participante', 'DNI', '34567890', 'USUARIO', 5, 50)
              ) AS v(email, password, nombre, apellido, tipo_documento, nro_doc, rol, provincia_id, puntos)
WHERE NOT EXISTS (SELECT 1 FROM usuarios WHERE usuarios.email = v.email);