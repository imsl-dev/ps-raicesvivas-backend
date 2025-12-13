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

-- Insertar usuarios (5 usuarios originales + 10 nuevos usuarios)
INSERT INTO usuarios (id, email, password, nombre, apellido, tipo_documento, nro_doc, rol, ruta_img, provincia_id, puntos)
SELECT * FROM (VALUES
                   -- Usuarios originales
                   (1, 'admin@raicesvivas.com', 'admin', 'Carlos', 'Administrador', 'DNI', '12345678', 'ADMIN', null, 5, 0),
                   (2, 'organizador@raicesvivas.com', 'admin', 'Juan Pablo', 'Bauza', 'DNI', '23456789', 'ORGANIZADOR', 'https://media.licdn.com/dms/image/v2/C4D03AQGVW2fZ-M37fQ/profile-displayphoto-shrink_800_800/profile-displayphoto-shrink_800_800/0/1648048631048?e=1766620800&v=beta&t=JvsdiKhQnWSv93zKTLZxnICqI7GhidugbNkrNzsfkAY', 5, 100),
                   (3, 'organizador2@raicesvivas.com', 'admin', 'Ignacio', 'Solis', 'DNI', '23456789', 'ORGANIZADOR', 'https://media.licdn.com/dms/image/v2/D4E03AQFRFYt3NH1sBQ/profile-displayphoto-shrink_800_800/B4EZWLlj0mG0Ag-/0/1741803635584?e=1766620800&v=beta&t=_64oV1xVQle_GAxIlQpUP14ePsiXr2eo0asJLfhUo50', 3, 0),
                   (4, 'usuario@raicesvivas.com', 'admin', 'Juan', 'Participante', 'DNI', '34567890', 'USUARIO', null, 5, 50),
                   (5, 'usuario2@raicesvivas.com', 'admin', 'Ignacio', 'Participante', 'DNI', '34567890', 'USUARIO', null, 2, 0),

                   -- 10 Nuevos usuarios
                   (6, 'laura.martinez@raicesvivas.com', 'admin', 'Laura', 'Martínez', 'DNI', '45678901', 'USUARIO', null, 5, 0),
                   (7, 'roberto.gonzalez@raicesvivas.com', 'admin', 'Roberto', 'González', 'DNI', '45678902', 'USUARIO', null, 5, 0),
                   (8, 'ana.rodriguez@raicesvivas.com', 'admin', 'Ana', 'Rodríguez', 'DNI', '45678903', 'USUARIO', null, 5, 0),
                   (9, 'miguel.fernandez@raicesvivas.com', 'admin', 'Miguel', 'Fernández', 'DNI', '45678904', 'USUARIO', null, 5, 0),
                   (10, 'sofia.lopez@raicesvivas.com', 'admin', 'Sofía', 'López', 'DNI', '45678905', 'USUARIO', null, 5, 0),
                   (11, 'diego.perez@raicesvivas.com', 'admin', 'Diego', 'Pérez', 'DNI', '45678906', 'USUARIO', null, 5, 0),
                   (12, 'valeria.garcia@raicesvivas.com', 'admin', 'Valeria', 'García', 'DNI', '45678907', 'USUARIO', null, 5, 0),
                   (13, 'carlos.sanchez@raicesvivas.com', 'admin', 'Carlos', 'Sánchez', 'DNI', '45678908', 'USUARIO', null, 5, 0),
                   (14, 'marina.ruiz@raicesvivas.com', 'admin', 'Marina', 'Ruiz', 'DNI', '45678909', 'USUARIO', null, 5, 0),
                   (15, 'fernando.diaz@raicesvivas.com', 'admin', 'Fernando', 'Díaz', 'DNI', '45678910', 'USUARIO', null, 5, 0)
              ) AS v(id, email, password, nombre, apellido, tipo_documento, nro_doc, rol, ruta_img, provincia_id, puntos)
WHERE NOT EXISTS (SELECT 1 FROM usuarios WHERE usuarios.email = v.email);

-- Insertar sponsors
INSERT INTO sponsors (nombre, link_dominio, ruta_img1, ruta_img2, activo)
SELECT * FROM (VALUES
                   ('Coca Cola', 'https://www.coca-cola.com/ar/es', 'https://www.cocacolaep.com/assets/legacy-assets/Uploads/resources/Coca-Cola-1210__FocusFillWyIwLjAwIiwiMC4wMCIsMTM3Niw1MzJd.jpg', 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTQe0QogSQXZmAqf45bXvsjKT4SyWlcuvJajA&s', true),
                   ('Grido', 'https://argentina.gridohelado.com/', 'https://media.licdn.com/dms/image/v2/D4D0BAQH0hm5N81H1zA/company-logo_200_200/B4DZcxhTzvGgAQ-/0/1748882505376/gridohelados_logo?e=2147483647&v=beta&t=GeEC_C1FzEi2Utpsdt-2UxgNkvbh2FlofsQrlliWx-4', 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTfFOMdRG-pX8Vb-T73Ipvdkvw_k2ILfAC3Bw&s', true),
                   ('Branca', 'https://www.branca.com.ar/', 'https://lacoloniawinestore.com.ar/wp-content/uploads/2021/08/LOGO-BRANCA.png', '', true)
              ) AS v(nombre, link_dominio, ruta_img1, ruta_img2, activo)
WHERE NOT EXISTS (
    SELECT 1 FROM sponsors s WHERE s.nombre = v.nombre
);

-- Insertar eventos CON IDs EXPLÍCITOS
INSERT INTO eventos (id, tipo, estado, organizador_id, cuenta_bancaria_id, provincia_id, nombre, descripcion, ruta_img, direccion, hora_inicio, hora_fin, puntos_asistencia, costo_interno, costo_inscripcion, sponsor_id)
SELECT
    id,
    tipo,
    estado,
    organizador_id,
    cuenta_bancaria_id::integer,
    provincia_id,
    nombre,
    descripcion,
    ruta_img,
    direccion,
    hora_inicio::timestamp,
    hora_fin::timestamp,
    puntos_asistencia,
    costo_interno,
    costo_inscripcion,
    sponsor_id
FROM (VALUES
          (1, 'REFORESTACION', 'EN_CURSO', 2, NULL, 11, 'Reforestación Sierras Grandes', 'La reforestación en las sierras de Córdoba se enfoca en la restauración de los bosques nativos, especialmente con el árbol tabaquillo (\(PolylepisAustralis\)), que es vital para la recuperación de suelos, el ciclo hídrico y la biodiversidad.', 'https://www.unc.edu.ar/sites/default/files/RGB.jpg', 'Centro-noroeste de la provincia de Córdoba', '2025-11-20 07:00:00', '2025-12-20 17:30:00', 20, 500, 100, 1),
          (2, 'RECOLECCION_BASURA', 'PROXIMO', 2, NULL, 11, 'Recolección de basura Villa Urquiza', 'Se hará una recolección de basura volutaria para ayudar a mejorar la condición de los residentes de Villa Urquiza', 'https://cordoba.gob.ar/wp-content/uploads/2021/03/WhatsApp-Image-2021-03-19-at-15.56.13-800x400.jpeg', 'Villa Urquiza, Córdoba Capital', '2025-12-20 10:30:00', '2025-12-20 15:30:00', 10, 300, 0, 2),
          (3, 'REFORESTACION', 'PROXIMO', 3, NULL, 9, 'Reforestacion de Almirante Brown ORG 2', 'Se busca reforestar 20 km2 en el Departamento Almirante Brown de Chaco. /nLas acciones de reforestación están principalmente enfocadas en contrarrestar la deforestación ilegal y proteger los bosques nativos.', 'https://econoticias.com.ar/wp-content/uploads/2024/09/bol_visita_fao_gran_chaco_americano_onu_bolivia_credito_morelia_erostegui-44_0-scaled.jpg', 'Almirante Brown, Chaco', '2025-12-20 09:00:00', '2025-12-21 16:30:00', 15, 200, 100, 2),
          (4, 'JUNTA_ALIMENTOS', 'PROXIMO', 3, NULL, 10, 'Junta de Alimentos Colegio Malvinas', 'Unimos fuerzas para llevar alimentos a quienes más lo necesitan. Sumate con tu donación y ayudanos a llenar de esperanza las mesas de muchas familias.', 'https://www.bbva.com/wp-content/uploads/2024/04/BBVA-donacion-alimentos-sostenibilidad.jpg', 'Dr. Pedro Minuzzi 428, M5501 Godoy Cruz, Mendoza', '2025-12-28 07:00:00', '2025-12-31 17:30:00', 10, 0, 0, 1),
          (5, 'DONACIONES', 'FINALIZADO', 2, NULL, 18, 'Apoyo a los niños de Cochagual', 'Tras el terremoto de 2021, la Escuela Paulo VI quedó en condiciones que obligaron a sus alumnos a estudiar en módulos provisorios. Fue un desafío enorme para las familias, los docentes y toda la comunidad. /n/n🏫 Hoy la realidad es distinta: ya cuentan con un edificio nuevo, seguro y moderno, con aulas cómodas y servicios que garantizan la tranquilidad de enseñar y aprender con las condiciones adecuadas.', 'https://sisanjuan.b-cdn.net/media/k2/items/cache/ae44d2dd91a73b393523b3a0d4ac8bc4_L.jpg', 'CARMONA S/N COCHAGUAL CENTRO', '2025-10-03 07:00:00', '2025-10-03 21:00:00', 15, 100, NULL, 3)
     ) AS v(id, tipo, estado, organizador_id, cuenta_bancaria_id, provincia_id, nombre, descripcion, ruta_img, direccion, hora_inicio, hora_fin, puntos_asistencia, costo_interno, costo_inscripcion, sponsor_id)
WHERE NOT EXISTS (
    SELECT 1 FROM eventos e WHERE e.id = v.id
);

-- Evitar problemas de insert cuando se corre data.sql
SELECT setval('usuarios_id_seq', COALESCE((SELECT MAX(id) FROM usuarios), 1));
SELECT setval('eventos_id_seq', COALESCE((SELECT MAX(id) FROM eventos), 1));

-- Insertar inscripciones
-- Los 10 nuevos usuarios inscritos al evento "Reforestación Sierras Grandes" (evento_id = 1) con estado PENDIENTE
-- 8 de esos 10 usuarios también inscritos a "Apoyo a los niños de Cochagual" (evento_id = 5): 4 PRESENTE y 4 AUSENTE
INSERT INTO inscripciones (usuario_id, evento_id, estado, fecha_creacion)
SELECT
    usuario_id,
    evento_id,
    estado,
    fecha_creacion
FROM (VALUES
          (6, 1, 'PENDIENTE', CURRENT_TIMESTAMP),
          (6, 5, 'PRESENTE', CURRENT_TIMESTAMP),

          (7, 1, 'PENDIENTE', CURRENT_TIMESTAMP),
          (7, 5, 'PRESENTE', CURRENT_TIMESTAMP),

          (8, 1, 'PENDIENTE', CURRENT_TIMESTAMP),
          (8, 5, 'PRESENTE', CURRENT_TIMESTAMP),

          (9, 1, 'PENDIENTE', CURRENT_TIMESTAMP),
          (9, 5, 'PRESENTE', CURRENT_TIMESTAMP),

          (10, 1, 'PENDIENTE', CURRENT_TIMESTAMP),
          (10, 5, 'AUSENTE', CURRENT_TIMESTAMP),

          (11, 1, 'PENDIENTE', CURRENT_TIMESTAMP),
          (11, 5, 'AUSENTE', CURRENT_TIMESTAMP),

          (12, 1, 'PENDIENTE', CURRENT_TIMESTAMP),
          (12, 5, 'AUSENTE', CURRENT_TIMESTAMP),

          (13, 1, 'PENDIENTE', CURRENT_TIMESTAMP),
          (13, 5, 'AUSENTE', CURRENT_TIMESTAMP),

          (14, 1, 'PENDIENTE', CURRENT_TIMESTAMP),

          (15, 1, 'PENDIENTE', CURRENT_TIMESTAMP)
     ) AS v(usuario_id, evento_id, estado, fecha_creacion)
WHERE NOT EXISTS (
    SELECT 1 FROM inscripciones i
    WHERE i.usuario_id = v.usuario_id
      AND i.evento_id = v.evento_id
);

-- SECCION DE DONACIONES
INSERT INTO pagos (usuario_id, evento_id, tipo_pago, estado_pago, monto, fecha_creacion, fecha_actualizacion, mensaje)
SELECT
    usuario_id,
    evento_id,
    tipo_pago,
    estado_pago,
    monto,
    fecha_creacion::timestamp,
    fecha_actualizacion::timestamp,
    mensaje
FROM (VALUES
          -- Donación 1
          (4, 1, 'DONACION', 'APROBADO', 500.00, '2025-11-10 14:30:00', '2025-11-10 14:35:00',
           'Cada árbol que plantamos es una esperanza para las futuras generaciones. Juntos construimos un mundo más verde.'),

          -- Donación 2
          (5, 1, 'DONACION', 'APROBADO', 1000.00, '2025-11-09 10:15:00', '2025-11-09 10:20:00',
           'El planeta nos necesita ahora más que nunca. Mi granito de arena para reforestar nuestros bosques nativos.'),

          -- Donación 3
          (4, 2, 'DONACION', 'APROBADO', 250.00, '2025-11-08 16:45:00', '2025-11-08 16:50:00',
           'Si todos aportamos un poco, podemos lograr grandes cosas. Por un futuro más limpio y sustentable.'),

          -- Donación 4
          (2, 1, 'DONACION', 'APROBADO', 750.00, '2025-11-07 09:20:00', '2025-11-07 09:25:00',
           'Los árboles son los pulmones de la Tierra. Protejamos nuestros bosques, protejamos la vida.'),

          -- Donación 5
          (5, 3, 'DONACION', 'APROBADO', 300.00, '2025-11-06 18:30:00', '2025-11-06 18:35:00',
           'Cuidar el medio ambiente es cuidar nuestro hogar. Cada acción cuenta, cada donación suma.'),

          -- Donación 6
          (4, 2, 'DONACION', 'APROBADO', 1500.00, '2025-11-05 11:00:00', '2025-11-05 11:05:00',
           'La naturaleza no nos necesita, nosotros la necesitamos a ella. Devolvamos un poco de lo mucho que nos da.'),

          -- Donación 7
          (2, 1, 'DONACION', 'APROBADO', 200.00, '2025-11-04 15:40:00', '2025-11-04 15:45:00',
           'Plantar un árbol hoy es regalar oxígeno al mañana. Por mis hijos y los hijos de mis hijos.'),

          -- Donación 8
          (5, 3, 'DONACION', 'APROBADO', 650.00, '2025-11-03 08:25:00', '2025-11-03 08:30:00',
           'El cambio comienza por uno mismo. Orgulloso de contribuir a la reforestación de nuestras sierras.'),

          -- Donación 9
          (4, 1, 'DONACION', 'APROBADO', 400.00, '2025-11-02 13:15:00', '2025-11-02 13:20:00',
           'No heredamos la Tierra de nuestros padres, la tomamos prestada de nuestros hijos. Cuidémosla.'),

          -- Donación 10
          (2, 2, 'DONACION', 'APROBADO', 850.00, '2025-11-01 17:50:00', '2025-11-01 17:55:00',
           'Cada semilla plantada es un acto de fe en el futuro. Juntos podemos reverdecer nuestro planeta.'),

          -- Donación 11
          (5, 1, 'DONACION', 'APROBADO', 1200.00, '2025-10-31 12:30:00', '2025-10-31 12:35:00',
           'La Tierra no es una herencia de nuestros padres, sino un préstamo de nuestros hijos. Agradezcamos cuidándola.'),

          -- Donación 12
          (4, 3, 'DONACION', 'APROBADO', 550.00, '2025-10-30 14:20:00', '2025-10-30 14:25:00',
           'Somos parte de la naturaleza, no sus dueños. Mi aporte para un futuro más verde y sostenible.')

     ) AS v(usuario_id, evento_id, tipo_pago, estado_pago, monto, fecha_creacion, fecha_actualizacion, mensaje)
WHERE NOT EXISTS (
    SELECT 1 FROM pagos p
    WHERE p.usuario_id = v.usuario_id
      AND p.evento_id = v.evento_id
      AND p.tipo_pago = v.tipo_pago::text
        AND p.mensaje = v.mensaje
);

-- Actualizar la secuencia de pagos
SELECT setval('pagos_id_seq', COALESCE((SELECT MAX(id) FROM pagos), 1));

-- Actualizar la secuencia de inscripciones
SELECT setval('inscripciones_id_seq', COALESCE((SELECT MAX(id) FROM inscripciones), 1));

-- ====================================================================
-- DATOS ADICIONALES PARA REPORTES DEL ORGANIZADOR
-- Agregar al final del archivo data.sql
-- ====================================================================

-- Crear más eventos para el organizador 2 (Juan Pablo Bauza) con diferentes estados
INSERT INTO eventos (id, tipo, estado, organizador_id, cuenta_bancaria_id, provincia_id, nombre, descripcion, ruta_img, direccion, hora_inicio, hora_fin, puntos_asistencia, costo_interno, costo_inscripcion, sponsor_id)
SELECT
    id,
    tipo,
    estado,
    organizador_id,
    cuenta_bancaria_id::integer,
    provincia_id,
    nombre,
    descripcion,
    ruta_img,
    direccion,
    hora_inicio::timestamp,
    hora_fin::timestamp,
    puntos_asistencia,
    costo_interno,
    costo_inscripcion,
    sponsor_id
FROM (VALUES
          -- Evento 6: FINALIZADO para organizador 2 (para tasa de asistencia)
          (6, 'JUNTA_ALIMENTOS', 'FINALIZADO', 2, NULL, 5, 'Junta de Alimentos Solidaria Córdoba', 'Gran jornada de recolección de alimentos no perecederos para comedores comunitarios de la zona sur de Córdoba Capital.', 'https://www.lavoz.com.ar/resizer/v2/VRUPHUPYNJCTJKK2GE2RDKV3YQ.jpg?smart=true&auth=8b2e0a8b1c2d3e4f5g6h7i8j9k0l1m2n3o4p5q6r&width=980&height=640', 'Centro Vecinal Barrio Alberdi, Córdoba', '2025-09-15 09:00:00', '2025-09-15 18:00:00', 15, 200, 0, 1),

          -- Evento 7: FINALIZADO para organizador 2
          (7, 'DONACIONES', 'FINALIZADO', 2, NULL, 5, 'Donación de Ropa de Invierno', 'Campaña de donación de ropa de invierno para familias en situación de vulnerabilidad.', 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQVxMQFTk5HJKMo1k8SrJfEj_I&usqp=CAU', 'Parroquia San Cayetano, Córdoba', '2025-08-20 10:00:00', '2025-08-20 16:00:00', 10, 150, 0, 2),

          -- Evento 8: FINALIZADO para organizador 2
          (8, 'RECOLECCION_BASURA', 'FINALIZADO', 2, NULL, 12, 'Limpieza del Río Mendoza', 'Jornada de limpieza de las márgenes del Río Mendoza, sector Luján de Cuyo.', 'https://www.mdzol.com/u/fotografias/m/2023/3/15/f768x1-1439234_1439361_5050.jpg', 'Costanera Río Mendoza, Luján de Cuyo', '2025-07-10 08:00:00', '2025-07-10 14:00:00', 20, 350, 50, 3),

          -- Evento 9: PROXIMO para organizador 2
          (9, 'REFORESTACION', 'PROXIMO', 2, NULL, 20, 'Reforestación Parque del Sur Santa Fe', 'Plantación de 500 árboles nativos en el Parque del Sur de la ciudad de Santa Fe.', 'https://www.santafe.gob.ar/noticias/resources/uploads/notas/img_64f32e5a1b2c3.jpg', 'Parque del Sur, Santa Fe Capital', '2026-03-15 08:00:00', '2026-03-15 16:00:00', 25, 600, 0, 1)
     ) AS v(id, tipo, estado, organizador_id, cuenta_bancaria_id, provincia_id, nombre, descripcion, ruta_img, direccion, hora_inicio, hora_fin, puntos_asistencia, costo_interno, costo_inscripcion, sponsor_id)
WHERE NOT EXISTS (
    SELECT 1 FROM eventos e WHERE e.id = v.id
);

-- Actualizar secuencia de eventos
SELECT setval('eventos_id_seq', COALESCE((SELECT MAX(id) FROM eventos), 1));

-- Inscripciones para los nuevos eventos finalizados del organizador 2
-- Evento 6: 12 inscriptos (8 PRESENTE, 4 AUSENTE)
-- Evento 7: 10 inscriptos (7 PRESENTE, 3 AUSENTE)
-- Evento 8: 8 inscriptos (5 PRESENTE, 3 AUSENTE)
INSERT INTO inscripciones (usuario_id, evento_id, estado, fecha_creacion)
SELECT
    usuario_id,
    evento_id,
    estado,
    fecha_creacion
FROM (VALUES
          -- Inscripciones Evento 6 (Junta de Alimentos)
          (4, 6, 'PRESENTE', '2025-09-10 10:00:00'::timestamp),
          (5, 6, 'PRESENTE', '2025-09-11 11:00:00'::timestamp),
          (6, 6, 'PRESENTE', '2025-09-11 14:00:00'::timestamp),
          (7, 6, 'PRESENTE', '2025-09-12 09:00:00'::timestamp),
          (8, 6, 'PRESENTE', '2025-09-12 15:00:00'::timestamp),
          (9, 6, 'PRESENTE', '2025-09-13 10:00:00'::timestamp),
          (10, 6, 'PRESENTE', '2025-09-13 16:00:00'::timestamp),
          (11, 6, 'PRESENTE', '2025-09-14 08:00:00'::timestamp),
          (12, 6, 'AUSENTE', '2025-09-14 12:00:00'::timestamp),
          (13, 6, 'AUSENTE', '2025-09-14 14:00:00'::timestamp),
          (14, 6, 'AUSENTE', '2025-09-15 07:00:00'::timestamp),
          (15, 6, 'AUSENTE', '2025-09-15 08:00:00'::timestamp),

          -- Inscripciones Evento 7 (Donación Ropa)
          (4, 7, 'PRESENTE', '2025-08-15 09:00:00'::timestamp),
          (5, 7, 'PRESENTE', '2025-08-15 10:00:00'::timestamp),
          (6, 7, 'PRESENTE', '2025-08-16 11:00:00'::timestamp),
          (7, 7, 'PRESENTE', '2025-08-16 14:00:00'::timestamp),
          (8, 7, 'PRESENTE', '2025-08-17 09:00:00'::timestamp),
          (9, 7, 'PRESENTE', '2025-08-17 15:00:00'::timestamp),
          (10, 7, 'PRESENTE', '2025-08-18 10:00:00'::timestamp),
          (11, 7, 'AUSENTE', '2025-08-18 12:00:00'::timestamp),
          (12, 7, 'AUSENTE', '2025-08-19 09:00:00'::timestamp),
          (13, 7, 'AUSENTE', '2025-08-19 14:00:00'::timestamp),

          -- Inscripciones Evento 8 (Limpieza Río)
          (4, 8, 'PRESENTE', '2025-07-05 08:00:00'::timestamp),
          (5, 8, 'PRESENTE', '2025-07-05 10:00:00'::timestamp),
          (6, 8, 'PRESENTE', '2025-07-06 09:00:00'::timestamp),
          (7, 8, 'PRESENTE', '2025-07-06 14:00:00'::timestamp),
          (8, 8, 'PRESENTE', '2025-07-07 11:00:00'::timestamp),
          (9, 8, 'AUSENTE', '2025-07-08 10:00:00'::timestamp),
          (10, 8, 'AUSENTE', '2025-07-09 08:00:00'::timestamp),
          (11, 8, 'AUSENTE', '2025-07-09 12:00:00'::timestamp)
     ) AS v(usuario_id, evento_id, estado, fecha_creacion)
WHERE NOT EXISTS (
    SELECT 1 FROM inscripciones i
    WHERE i.usuario_id = v.usuario_id
      AND i.evento_id = v.evento_id
);

-- Actualizar secuencia de inscripciones
SELECT setval('inscripciones_id_seq', COALESCE((SELECT MAX(id) FROM inscripciones), 1));

-- Donaciones adicionales para eventos del organizador 2
INSERT INTO pagos (usuario_id, evento_id, tipo_pago, estado_pago, monto, fecha_creacion, fecha_actualizacion, mensaje)
SELECT
    usuario_id,
    evento_id,
    tipo_pago,
    estado_pago,
    monto,
    fecha_creacion::timestamp,
    fecha_actualizacion::timestamp,
    mensaje
FROM (VALUES
          -- Donaciones Evento 6 (Junta de Alimentos)
          (4, 6, 'DONACION', 'APROBADO', 300.00, '2025-09-12 10:30:00', '2025-09-12 10:35:00',
           'Un pequeño gesto puede hacer una gran diferencia. Unidos por los que más necesitan.'),
          (5, 6, 'DONACION', 'APROBADO', 450.00, '2025-09-13 14:20:00', '2025-09-13 14:25:00',
           'Compartir es la mejor forma de multiplicar. Gracias por esta iniciativa solidaria.'),
          (7, 6, 'DONACION', 'APROBADO', 200.00, '2025-09-14 09:15:00', '2025-09-14 09:20:00',
           'Nadie se salva solo. Juntos construimos una comunidad más fuerte.'),

          -- Donaciones Evento 7 (Donación Ropa)
          (4, 7, 'DONACION', 'APROBADO', 350.00, '2025-08-17 11:00:00', '2025-08-17 11:05:00',
           'El frío no espera. Gracias por ayudar a quienes más lo necesitan este invierno.'),
          (6, 7, 'DONACION', 'APROBADO', 500.00, '2025-08-18 15:30:00', '2025-08-18 15:35:00',
           'Abrigar un cuerpo es también calentar un corazón. Feliz de poder aportar.'),

          -- Donaciones Evento 8 (Limpieza Río)
          (5, 8, 'DONACION', 'APROBADO', 600.00, '2025-07-08 10:00:00', '2025-07-08 10:05:00',
           'Nuestros ríos son vida. Cuidarlos es nuestra responsabilidad con las futuras generaciones.'),
          (8, 8, 'DONACION', 'APROBADO', 400.00, '2025-07-09 16:45:00', '2025-07-09 16:50:00',
           'El agua limpia es un derecho de todos. Orgulloso de contribuir a esta causa.'),
          (10, 8, 'DONACION', 'APROBADO', 250.00, '2025-07-10 08:30:00', '2025-07-10 08:35:00',
           'Por un medio ambiente más sano para nuestros hijos.'),

          -- Más donaciones para eventos 1 y 2 (también del organizador 2)
          (6, 1, 'DONACION', 'APROBADO', 800.00, '2025-12-01 09:00:00', '2025-12-01 09:05:00',
           'Los bosques son el hogar de miles de especies. Protegerlos es proteger la vida.'),
          (7, 1, 'DONACION', 'APROBADO', 350.00, '2025-12-02 14:30:00', '2025-12-02 14:35:00',
           'Cada árbol plantado es un paso hacia un futuro más verde.'),
          (8, 2, 'DONACION', 'APROBADO', 275.00, '2025-12-03 11:15:00', '2025-12-03 11:20:00',
           'Mantener limpio nuestro barrio es tarea de todos. Gran iniciativa!')
     ) AS v(usuario_id, evento_id, tipo_pago, estado_pago, monto, fecha_creacion, fecha_actualizacion, mensaje)
WHERE NOT EXISTS (
    SELECT 1 FROM pagos p
    WHERE p.usuario_id = v.usuario_id
      AND p.evento_id = v.evento_id
      AND p.tipo_pago = v.tipo_pago::text
        AND p.mensaje = v.mensaje
);

-- Actualizar la secuencia de pagos
SELECT setval('pagos_id_seq', COALESCE((SELECT MAX(id) FROM pagos), 1));