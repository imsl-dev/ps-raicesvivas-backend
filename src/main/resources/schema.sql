-- Script de inicialización para Spring Boot
-- Archivo: src/main/resources/schema.sql

-- Crear tipos ENUM
DO $$ BEGIN
CREATE TYPE tipo_documento AS ENUM ('DNI', 'PASAPORTE');
EXCEPTION
    WHEN duplicate_object THEN null;
END $$;

DO $$ BEGIN
CREATE TYPE rol_usuario AS ENUM ('ADMIN', 'USUARIO', 'ORGANIZADOR');
EXCEPTION
    WHEN duplicate_object THEN null;
END $$;

DO $$ BEGIN
CREATE TYPE tipo_evento AS ENUM ('REFORESTACION', 'RECOLECCION_BASURA', 'JUNTA_ALIMENTOS', 'DONACIONES');
EXCEPTION
    WHEN duplicate_object THEN null;
END $$;

DO $$ BEGIN
CREATE TYPE estado_evento AS ENUM ('ACTIVO', 'INACTIVO');
EXCEPTION
    WHEN duplicate_object THEN null;
END $$;

DO $$ BEGIN
CREATE TYPE estado_inscripcion AS ENUM ('PRESENTE', 'AUSENTE', 'PENDIENTE');
EXCEPTION
    WHEN duplicate_object THEN null;
END $$;

DO $$ BEGIN
CREATE TYPE estado_peticion AS ENUM ('ACEPTADO', 'CANCELADO', 'PENDIENTE');
EXCEPTION
    WHEN duplicate_object THEN null;
END $$;

-- Tabla PROVINCIAS
CREATE TABLE IF NOT EXISTS provincias (
                                          id BIGSERIAL PRIMARY KEY,
                                          nombre VARCHAR(255) NOT NULL
    );

-- Tabla USUARIOS
CREATE TABLE IF NOT EXISTS usuarios (
                                        id BIGSERIAL PRIMARY KEY,
                                        nombre VARCHAR(255) NOT NULL,
    apellido VARCHAR(255) NOT NULL,
    tipo_documento tipo_documento NOT NULL,
    nro_doc VARCHAR(20) NOT NULL UNIQUE,
    rol rol_usuario NOT NULL,
    provincia_id BIGINT NOT NULL,
    puntos INTEGER DEFAULT 0,
    FOREIGN KEY (provincia_id) REFERENCES provincias(id)
    );

-- Tabla CUENTAS_BANCARIAS
CREATE TABLE IF NOT EXISTS cuentas_bancarias (
                                                 id BIGSERIAL PRIMARY KEY,
                                                 cbu BIGINT NOT NULL,
                                                 id_usuario BIGINT NOT NULL,
                                                 FOREIGN KEY (id_usuario) REFERENCES usuarios(id)
    );

-- Tabla SPONSORS
CREATE TABLE IF NOT EXISTS sponsors (
                                        id BIGSERIAL PRIMARY KEY,
                                        nombre VARCHAR(255) NOT NULL,
    ruta_img1 VARCHAR(500),
    ruta_img2 VARCHAR(500)
    );

-- Tabla EVENTOS
CREATE TABLE IF NOT EXISTS eventos (
                                       id BIGSERIAL PRIMARY KEY,
                                       tipo tipo_evento NOT NULL,
                                       estado estado_evento NOT NULL,
                                       organizador_id BIGINT NOT NULL,
                                       cuenta_bancaria_id BIGINT,
                                       provincia_id BIGINT NOT NULL,
                                       nombre VARCHAR(255) NOT NULL,
    descripcion TEXT,
    ruta_img VARCHAR(500),
    hora_inicio TIMESTAMP NOT NULL,
    hora_fin TIMESTAMP NOT NULL,
    puntos_asistencia INTEGER DEFAULT 0,
    direccion VARCHAR(500),
    costo_interno DECIMAL(10,2),
    costo_inscripcion DECIMAL(10,2),
    sponsor_id BIGINT,
    FOREIGN KEY (organizador_id) REFERENCES usuarios(id),
    FOREIGN KEY (cuenta_bancaria_id) REFERENCES cuentas_bancarias(id),
    FOREIGN KEY (provincia_id) REFERENCES provincias(id),
    FOREIGN KEY (sponsor_id) REFERENCES sponsors(id)
    );

-- Tabla INSCRIPCIONES
CREATE TABLE IF NOT EXISTS inscripciones (
                                             id BIGSERIAL PRIMARY KEY,
                                             usuario_id BIGINT NOT NULL,
                                             estado estado_inscripcion NOT NULL,
                                             evento_id BIGINT NOT NULL,
                                             FOREIGN KEY (usuario_id) REFERENCES usuarios(id),
    FOREIGN KEY (evento_id) REFERENCES eventos(id)
    );

-- Tabla PAGOS_USUARIOS
CREATE TABLE IF NOT EXISTS pagos_usuarios (
                                              id BIGSERIAL PRIMARY KEY,
                                              id_usuario BIGINT NOT NULL,
                                              id_evento BIGINT NOT NULL,
                                              FOREIGN KEY (id_usuario) REFERENCES usuarios(id),
    FOREIGN KEY (id_evento) REFERENCES eventos(id)
    );

-- Tabla PETICIONES_ORGANIZADOR
CREATE TABLE IF NOT EXISTS peticiones_organizadores (
                                                        id BIGSERIAL PRIMARY KEY,
                                                        usuario_id BIGINT NOT NULL,
                                                        estado_peticion estado_peticion NOT NULL,
                                                        mensaje_usuario TEXT,
                                                        FOREIGN KEY (usuario_id) REFERENCES usuarios(id)
    );

-- Tabla CANJEABLES
CREATE TABLE IF NOT EXISTS canjeables (
                                          id BIGSERIAL PRIMARY KEY,
                                          id_sponsor BIGINT NOT NULL,
                                          link_pdf_drive VARCHAR(500),
    costo_puntos INTEGER NOT NULL,
    FOREIGN KEY (id_sponsor) REFERENCES sponsors(id)
    );

-- Tabla DONACIONES
CREATE TABLE IF NOT EXISTS donaciones (
                                          id BIGSERIAL PRIMARY KEY,
                                          id_evento BIGINT NOT NULL,
                                          id_usuario BIGINT NOT NULL,
                                          monto DECIMAL(10,2) NOT NULL,
    mensaje TEXT,
    FOREIGN KEY (id_evento) REFERENCES eventos(id),
    FOREIGN KEY (id_usuario) REFERENCES usuarios(id)
    );

-- Crear índices para mejorar el rendimiento
CREATE INDEX IF NOT EXISTS idx_usuarios_provincia ON usuarios(provincia_id);
CREATE INDEX IF NOT EXISTS idx_usuarios_rol ON usuarios(rol);
CREATE INDEX IF NOT EXISTS idx_eventos_organizador ON eventos(organizador_id);
CREATE INDEX IF NOT EXISTS idx_eventos_provincia ON eventos(provincia_id);
CREATE INDEX IF NOT EXISTS idx_inscripciones_usuario ON inscripciones(usuario_id);
CREATE INDEX IF NOT EXISTS idx_inscripciones_evento ON inscripciones(evento_id);
CREATE INDEX IF NOT EXISTS idx_donaciones_usuario ON donaciones(id_usuario);
CREATE INDEX IF NOT EXISTS idx_eventos_sponsor ON eventos(sponsor_id);