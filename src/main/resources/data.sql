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
                   (3, 'organizador2@raicesvivas.com', 'admin', 'Ignacio', 'Solis', 'DNI', '23456789', 'ORGANIZADOR', 'https://media.licdn.com/dms/image/v2/D4D03AQFmYvVHXxSG8A/profile-displayphoto-shrink_800_800/profile-displayphoto-shrink_800_800/0/1725391616439?e=1766620800&v=beta&t=Hb3jHscXo_-FMrBCZQYpfWFO3uAtpK4h4z0OhLTOZfw', 9, 150),
                   (4, 'user@raicesvivas.com', 'admin', 'Santiago', 'Usuario', 'DNI', '34567890', 'USUARIO', null, 5, 200),
                   (5, 'user2@raicesvivas.com', 'admin', 'Luis', 'Vargas', 'DNI', '45678901', 'USUARIO', null, 11, 300),

                   -- Nuevos usuarios (usuarios 6-15)
                   (6, 'user3@raicesvivas.com', 'admin', 'María', 'González', 'DNI', '34567891', 'USUARIO', null, 5, 150),
                   (7, 'user4@raicesvivas.com', 'admin', 'Pedro', 'Martínez', 'DNI', '34567892', 'USUARIO', null, 1, 250),
                   (8, 'user5@raicesvivas.com', 'admin', 'Ana', 'López', 'DNI', '34567893', 'USUARIO', null, 20, 180),
                   (9, 'user6@raicesvivas.com', 'admin', 'Jorge', 'Fernández', 'DNI', '34567894', 'USUARIO', null, 12, 220),
                   (10, 'user7@raicesvivas.com', 'admin', 'Laura', 'Ramírez', 'DNI', '34567895', 'USUARIO', null, 5, 190),
                   (11, 'user8@raicesvivas.com', 'admin', 'Diego', 'Torres', 'DNI', '34567896', 'USUARIO', null, 11, 210),
                   (12, 'user9@raicesvivas.com', 'admin', 'Sofía', 'Ruiz', 'DNI', '34567897', 'USUARIO', null, 9, 160),
                   (13, 'user10@raicesvivas.com', 'admin', 'Martín', 'Acosta', 'DNI', '34567898', 'USUARIO', null, 5, 240),
                   (14, 'user11@raicesvivas.com', 'admin', 'Valeria', 'Castro', 'DNI', '34567899', 'USUARIO', null, 1, 170),
                   (15, 'user12@raicesvivas.com', 'admin', 'Facundo', 'Morales', 'DNI', '34567900', 'USUARIO', null, 20, 200)
              ) AS v(id, email, password, nombre, apellido, tipo_documento, nro_doc, rol, ruta_img, provincia_id, puntos)
WHERE NOT EXISTS (SELECT 1 FROM usuarios WHERE usuarios.email = v.email);

-- Insertar cuentas bancarias (solo si no existen)
INSERT INTO cuentas_bancarias (cbu, id_usuario)
SELECT * FROM (VALUES
                   (12345678901234567890, 2),
                   (23456789012345678901, 3)
              ) AS v(cbu, id_usuario)
WHERE NOT EXISTS (SELECT 1 FROM cuentas_bancarias WHERE cuentas_bancarias.cbu = v.cbu);

-- Insertar sponsors
INSERT INTO sponsors (nombre, link_dominio, ruta_img1, ruta_img2, activo)
SELECT * FROM (VALUES
                   ('Coca Cola', 'https://www.coca-cola.com/ar/es', 'https://www.cocacolaep.com/assets/legacy-assets/Uploads/resources/Coca-Cola-1210__FocusFillWyIwLjAwIiwiMC4wMCIsMTM3Niw1MzJd.jpg', 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTQe0QogSQXZmAqf45bXvsjKT4SyWlcuvJajA&s', true),
                   ('Grido', 'https://argentina.gridohelado.com/', 'https://media.licdn.com/dms/image/v2/D4D0BAQH0hm5N81H1zA/company-logo_200_200/B4DZcxhTzvGgAQ-/0/1748882505376/gridohelados_logo?e=2147483647&v=beta&t=GeEC_C1FzEi2Utpsdt-2UxgNkvbh2FlofsQrlliWx-4', 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTfFOMdRG-pX8Vb-T73Ipvdkvw_k2ILfAC3Bw&s', true),
                   ('Branca', 'https://www.branca.com.ar/', 'https://lacoloniawinestore.com.ar/wp-content/uploads/2021/08/LOGO-BRANCA.png', '', true),
                   ('Starbucks', 'https://www.starbucks.com.ar/', 'https://upload.wikimedia.org/wikipedia/en/thumb/d/d3/Starbucks_Corporation_Logo_2011.svg/1200px-Starbucks_Corporation_Logo_2011.svg.png', '', true),
                   ('Nike', 'https://www.nike.com/ar/', 'https://upload.wikimedia.org/wikipedia/commons/thumb/a/a6/Logo_NIKE.svg/1200px-Logo_NIKE.svg.png', '', true),
                   ('Burger King', 'https://www.burgerking.com.ar/', 'https://cdn.worldvectorlogo.com/logos/burger-king-1-logo.svg', '', true)
              ) AS v(nombre, link_dominio, ruta_img1, ruta_img2, activo)
WHERE NOT EXISTS (
    SELECT 1 FROM sponsors s WHERE s.nombre = v.nombre
);

-- Insertar eventos CON IDs EXPLÍCITOS y coordenadas geográficas
INSERT INTO eventos (id, tipo, estado, organizador_id, cuenta_bancaria_id, provincia_id, nombre, descripcion, ruta_img, direccion, latitud, longitud, hora_inicio, hora_fin, puntos_asistencia, costo_interno, costo_inscripcion, sponsor_id)
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
    latitud,
    longitud,
    hora_inicio::timestamp,
    hora_fin::timestamp,
    puntos_asistencia,
    costo_interno,
    costo_inscripcion,
    sponsor_id
FROM (VALUES
          -- EVENTOS EXISTENTES CON COORDENADAS
          (1, 'REFORESTACION', 'EN_CURSO', 2, NULL, 11, 'Reforestación Sierras Grandes', 'La reforestación en las sierras de Córdoba se enfoca en la restauración de los bosques nativos, especialmente con el árbol tabaquillo (Polylepis Australis), que es vital para la recuperación de suelos, el ciclo hídrico y la biodiversidad.', 'https://www.unc.edu.ar/sites/default/files/RGB.jpg', 'Centro-noroeste de la provincia de Córdoba', -31.2568, -64.8833, '2025-11-20 07:00:00', '2025-12-20 17:30:00', 20, 500, 100, 1),

          (2, 'RECOLECCION_BASURA', 'PROXIMO', 2, NULL, 11, 'Recolección de Basura Villa Urquiza', 'Jornada de recolección voluntaria de residuos para mejorar la condición ambiental del barrio Villa Urquiza y promover la conciencia ecológica en la comunidad.', 'https://cordoba.gob.ar/wp-content/uploads/2021/03/WhatsApp-Image-2021-03-19-at-15.56.13-800x400.jpeg', 'Villa Urquiza, Córdoba Capital', -31.3890, -64.2314, '2025-12-20 10:30:00', '2025-12-20 15:30:00', 10, 300, 0, 2),

          (3, 'REFORESTACION', 'PROXIMO', 3, NULL, 9, 'Reforestación Almirante Brown', 'Proyecto de reforestación de 20 km² en el Departamento Almirante Brown, Chaco. Las acciones están enfocadas en contrarrestar la deforestación ilegal y proteger los bosques nativos de la región.', 'https://www.sib.gob.ar/archivos/Reforestaci%C3%B3n%20para%20Chaco.jpg', 'Departamento Almirante Brown, Chaco', -26.4914, -61.1667, '2025-12-25 08:00:00', '2025-12-25 18:00:00', 25, 800, 200, 1),

          (4, 'JUNTA_ALIMENTOS', 'CANCELADO', 3, NULL, 9, 'Junta de Alimentos Resistencia', 'Campaña de recolección de alimentos no perecederos para familias en situación de vulnerabilidad en la ciudad de Resistencia.', 'https://www.ellitoral.com/images/2023/05/26/HFxKIEn6c_1256x620__1.jpg', 'Plaza 25 de Mayo, Resistencia', -27.4514, -58.9867, '2025-11-01 09:00:00', '2025-11-01 18:00:00', 15, 400, 0, 2),

          (5, 'DONACIONES', 'FINALIZADO', 3, NULL, 9, 'Apoyo a los Niños de Cochagual', 'Evento de apoyo integral a la Escuela 399 de Cochagual, que sufrió un incendio en 2021. Fue un desafío enorme para las familias, los docentes y toda la comunidad. Hoy la realidad es distinta: ya cuentan con un edificio nuevo, seguro y moderno, con aulas cómodas y servicios que garantizan la tranquilidad de enseñar y aprender con las condiciones adecuadas.', 'https://sisanjuan.b-cdn.net/media/k2/items/cache/ae44d2dd91a73b393523b3a0d4ac8bc4_L.jpg', 'CARMONA S/N COCHAGUAL CENTRO', -26.7667, -60.9167, '2025-10-03 07:00:00', '2025-10-03 21:00:00', 15, 100, NULL, 3),

          -- EVENTOS ADICIONALES CON COORDENADAS
          (6, 'JUNTA_ALIMENTOS', 'FINALIZADO', 2, NULL, 5, 'Junta de Alimentos Solidaria Córdoba', 'Gran jornada de recolección de alimentos no perecederos para comedores comunitarios de la zona sur de Córdoba Capital. Se lograron reunir más de 2 toneladas de alimentos.', 'https://www.lavoz.com.ar/resizer/v2/VRUPHUPYNJCTJKK2GE2RDKV3YQ.jpg?smart=true&auth=8b2e0a8b1c2d3e4f5g6h7i8j9k0l1m2n3o4p5q6r&width=980&height=640', 'Centro Vecinal Barrio Alberdi, Córdoba', -31.4135, -64.1888, '2025-09-15 09:00:00', '2025-09-15 18:00:00', 15, 200, 0, 1),

          (7, 'DONACIONES', 'FINALIZADO', 2, NULL, 5, 'Donación de Ropa de Invierno', 'Campaña de donación de ropa de invierno para familias en situación de vulnerabilidad. Se recibieron más de 500 prendas que fueron distribuidas a quienes más lo necesitan.', 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQVxMQFTk5HJKMo1k8SrJfEj_I&usqp=CAU', 'Parroquia San Cayetano, Córdoba', -31.4201, -64.1888, '2025-08-20 10:00:00', '2025-08-20 16:00:00', 10, 150, 0, 2),

          (8, 'RECOLECCION_BASURA', 'FINALIZADO', 2, NULL, 12, 'Limpieza del Río Mendoza', 'Jornada de limpieza de las márgenes del Río Mendoza, sector Luján de Cuyo. Participaron más de 80 voluntarios que retiraron residuos plásticos y escombros.', 'https://www.mdzol.com/u/fotografias/m/2023/3/15/f768x1-1439234_1439361_5050.jpg', 'Costanera Río Mendoza, Luján de Cuyo', -33.0368, -68.8779, '2025-07-10 08:00:00', '2025-07-10 14:00:00', 20, 350, 50, 3),

          (9, 'REFORESTACION', 'PROXIMO', 2, NULL, 20, 'Reforestación Parque del Sur Santa Fe', 'Plantación de 500 árboles nativos en el Parque del Sur de la ciudad de Santa Fe. Una iniciativa para aumentar las áreas verdes urbanas y mejorar la calidad del aire.', 'https://www.santafe.gob.ar/noticias/resources/uploads/notas/img_64f32e5a1b2c3.jpg', 'Parque del Sur, Santa Fe Capital', -31.6333, -60.7000, '2026-03-15 08:00:00', '2026-03-15 16:00:00', 25, 600, 0, 1),

          -- NUEVOS EVENTOS CON COORDENADAS
          (10, 'RECOLECCION_BASURA', 'PROXIMO', 2, NULL, 1, 'Limpieza Costera Mar del Plata', 'Limpieza de playas en Mar del Plata enfocada en la recolección de plásticos y microplásticos que afectan el ecosistema marino. Actividad familiar con refrigerio incluido.', 'https://www.0223.com.ar/media/img/2024/01/29/whatsapp-image-2024-01-29-at-12-25-43-1_crop1706545577829.jpg', 'Playa Bristol, Mar del Plata', -38.0055, -57.5426, '2026-01-15 09:00:00', '2026-01-15 14:00:00', 15, 250, 0, 2),

          (11, 'JUNTA_ALIMENTOS', 'PROXIMO', 3, NULL, 23, 'Colecta Alimentaria San Miguel de Tucumán', 'Campaña de recolección de alimentos para merenderos barriales. Se busca reunir productos no perecederos, leche en polvo y alimentos para niños.', 'https://www.eltucumano.com/fotos/202212/1671468506_colecta.jpg', 'Plaza Independencia, San Miguel de Tucumán', -26.8241, -65.2226, '2026-02-10 10:00:00', '2026-02-10 18:00:00', 12, 180, 0, 1),

          (12, 'REFORESTACION', 'PROXIMO', 2, NULL, 17, 'Reforestación Valle Fértil', 'Jornada de plantación de especies nativas en Valle Fértil, San Juan. Se plantarán algarrobos y chañares para recuperar la vegetación autóctona del valle.', 'https://www.sisanjuan.gob.ar/prensa/wp-content/uploads/sites/5/2023/09/valle-fertil-parque-ischigualasto.jpg', 'Valle Fértil, San Juan', -30.6333, -67.4667, '2026-04-22 08:00:00', '2026-04-22 16:00:00', 30, 700, 150, 3),

          (13, 'DONACIONES', 'EN_CURSO', 2, NULL, 5, 'Donación de Útiles Escolares Córdoba', 'Campaña de recolección de útiles escolares para escuelas de bajos recursos en Córdoba Capital. Mochilas, cuadernos, lápices y materiales educativos.', 'https://www.cba24n.com.ar/u/fotografias/m/2024/2/26/f1280x720-1383099_1517174_5050.jpg', 'Municipalidad de Córdoba', -31.4201, -64.1888, '2025-12-01 09:00:00', '2026-03-01 18:00:00', 10, 300, 0, 2),

          (14, 'RECOLECCION_BASURA', 'PROXIMO', 3, NULL, 16, 'Limpieza Quebrada de Humahuaca', 'Jornada de limpieza en la Quebrada de Humahuaca, Patrimonio de la Humanidad. Actividad coordinada con comunidades locales para preservar este sitio natural e histórico.', 'https://www.todojujuy.com/fotos-nuevas/2023/06/quebrada-de-humahuaca-6493e93b1e5a7.jpg', 'Quebrada de Humahuaca, Jujuy', -23.2033, -65.3483, '2026-05-20 08:00:00', '2026-05-20 15:00:00', 20, 400, 100, 1),

          (15, 'JUNTA_ALIMENTOS', 'PROXIMO', 2, NULL, 14, 'Colecta Solidaria Neuquén Capital', 'Recolección de alimentos y artículos de primera necesidad para familias afectadas por las nevadas en la región andina de Neuquén.', 'https://www.rionegro.com.ar/wp-content/uploads/2023/07/colecta.jpg', 'Plaza de las Banderas, Neuquén Capital', -38.9516, -68.0591, '2026-06-15 10:00:00', '2026-06-15 17:00:00', 15, 220, 0, 2)

     ) AS v(id, tipo, estado, organizador_id, cuenta_bancaria_id, provincia_id, nombre, descripcion, ruta_img, direccion, latitud, longitud, hora_inicio, hora_fin, puntos_asistencia, costo_interno, costo_inscripcion, sponsor_id)
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
           'Cada árbol que plantamos es una esperanza para las futuras generaciones. Orgulloso de aportar mi grano de arena.'),

          -- Donación 2
          (4, 2, 'DONACION', 'APROBADO', 200.00, '2025-11-11 09:00:00', '2025-11-11 09:05:00',
           'Nuestro barrio merece estar limpio. Este es mi aporte para lograrlo juntos.'),

          -- Donación 3
          (5, 1, 'DONACION', 'APROBADO', 750.00, '2025-11-12 16:45:00', '2025-11-12 16:50:00',
           'La naturaleza nos da todo, es hora de devolverle algo. Plantar un árbol es plantar vida.'),

          -- Donación 4
          (4, 5, 'DONACION', 'APROBADO', 300.00, '2025-10-25 11:20:00', '2025-10-25 11:25:00',
           'Los niños son nuestro futuro. Ayudarlos con su educación es invertir en una sociedad mejor.'),

          -- Donación 5
          (5, 3, 'DONACION', 'APROBADO', 600.00, '2025-10-28 14:00:00', '2025-10-28 14:05:00',
           'Proteger nuestros bosques nativos es proteger nuestro hogar. Feliz de contribuir.'),

          -- Donación 6
          (5, 5, 'DONACION', 'APROBADO', 400.00, '2025-10-29 10:30:00', '2025-10-29 10:35:00',
           'La educación es un derecho fundamental. Solidaridad con los chicos de Cochagual.'),

          -- Donación 7
          (4, 1, 'DONACION', 'APROBADO', 350.00, '2025-11-05 08:15:00', '2025-11-05 08:20:00',
           'Cada aporte suma. Si todos ponemos un poco, podemos lograr grandes cambios ambientales.'),

          -- Donación 8
          (5, 2, 'DONACION', 'APROBADO', 250.00, '2025-11-08 17:00:00', '2025-11-08 17:05:00',
           'Un barrio limpio es un barrio saludable. Apoyemos esta iniciativa.'),

          -- Donación 9
          (4, 3, 'DONACION', 'APROBADO', 450.00, '2025-10-27 13:45:00', '2025-10-27 13:50:00',
           'El Chaco necesita sus bosques. Detengamos la deforestación con acciones concretas.'),

          -- Donación 10
          (5, 1, 'DONACION', 'APROBADO', 900.00, '2025-11-15 15:30:00', '2025-11-15 15:35:00',
           'Plantar árboles es sembrar futuro. Juntos podemos reverdecer nuestro planeta.'),

          -- Donación 11
          (5, 1, 'DONACION', 'APROBADO', 1200.00, '2025-10-31 12:30:00', '2025-10-31 12:35:00',
           'La Tierra no es una herencia de nuestros padres, sino un préstamo de nuestros hijos. Agradezcamos cuidándola.'),

          -- Donación 12
          (4, 3, 'DONACION', 'APROBADO', 550.00, '2025-10-30 14:20:00', '2025-10-30 14:25:00',
           'Somos parte de la naturaleza, no sus dueños. Mi aporte para un futuro más verde y sostenible.'),

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

-- Actualizar la secuencia de inscripciones
SELECT setval('inscripciones_id_seq', COALESCE((SELECT MAX(id) FROM inscripciones), 1));

-- ====================================================================
-- SECCIÓN DE CANJEABLES - AL MENOS 10 CANJEABLES VARIADOS
-- ====================================================================

INSERT INTO canjeables (nombre, sponsor_id, url, costo_puntos, nombre_sponsor, valido_hasta, activo)
SELECT * FROM (VALUES
                   -- Canjeables de Coca Cola (sponsor_id = 1)
                   ('Descuento 20% en Coca-Cola 2.25L', 1, 'https://www.coca-cola.com/ar/es/promociones', 150, 'Coca Cola', '2026-06-30 23:59:59'::timestamp, true),
                   ('2x1 en Coca-Cola Zero 500ml', 1, 'https://www.coca-cola.com/ar/es/promociones', 200, 'Coca Cola', '2026-03-31 23:59:59'::timestamp, true),
                   ('Combo Coca-Cola: 4 latas + Vaso coleccionable', 1, 'https://www.coca-cola.com/ar/es/promociones', 350, 'Coca Cola', '2026-12-31 23:59:59'::timestamp, true),

                   -- Canjeables de Grido (sponsor_id = 2)
                   ('1 Kg de Helado Grido a precio especial', 2, 'https://argentina.gridohelado.com/promociones', 180, 'Grido', '2026-04-30 23:59:59'::timestamp, true),
                   ('2x1 en Cucuruchos Grido', 2, 'https://argentina.gridohelado.com/promociones', 120, 'Grido', '2026-02-28 23:59:59'::timestamp, true),
                   ('Descuento 25% en Tortas Heladas', 2, 'https://argentina.gridohelado.com/promociones', 300, 'Grido', '2026-08-31 23:59:59'::timestamp, true),

                   -- Canjeables de Branca (sponsor_id = 3)
                   ('Descuento 15% en Fernet Branca 750ml', 3, 'https://www.branca.com.ar/promociones', 220, 'Branca', '2026-05-31 23:59:59'::timestamp, true),
                   ('Pack Fernet + Cola promocional', 3, 'https://www.branca.com.ar/promociones', 280, 'Branca', '2026-07-31 23:59:59'::timestamp, true),

                   -- Canjeables de Starbucks (sponsor_id = 4)
                   ('Café Grande gratis en Starbucks', 4, 'https://www.starbucks.com.ar/promociones', 250, 'Starbucks', '2026-09-30 23:59:59'::timestamp, true),
                   ('2x1 en Frappuccino tamaño Grande', 4, 'https://www.starbucks.com.ar/promociones', 400, 'Starbucks', '2026-11-30 23:59:59'::timestamp, true),
                   ('Descuento 30% en Pastelería', 4, 'https://www.starbucks.com.ar/promociones', 180, 'Starbucks', '2026-03-31 23:59:59'::timestamp, true),

                   -- Canjeables de Nike (sponsor_id = 5)
                   ('Descuento 10% en Zapatillas Nike', 5, 'https://www.nike.com/ar/promociones', 500, 'Nike', '2026-12-31 23:59:59'::timestamp, true),
                   ('15% OFF en Ropa Deportiva Nike', 5, 'https://www.nike.com/ar/promociones', 350, 'Nike', '2026-10-31 23:59:59'::timestamp, true),

                   -- Canjeables de Burger King (sponsor_id = 6)
                   ('Combo Whopper completo', 6, 'https://www.burgerking.com.ar/promociones', 320, 'Burger King', '2026-06-30 23:59:59'::timestamp, true),
                   ('2x1 en Hamburguesas Medianas', 6, 'https://www.burgerking.com.ar/promociones', 250, 'Burger King', '2026-04-30 23:59:59'::timestamp, true)

              ) AS v(nombre, sponsor_id, url, costo_puntos, nombre_sponsor, valido_hasta, activo)
WHERE NOT EXISTS (
    SELECT 1 FROM canjeables c
    WHERE c.nombre = v.nombre
      AND c.sponsor_id = v.sponsor_id
);

-- Actualizar secuencia de canjeables
SELECT setval('canjeables_id_seq', COALESCE((SELECT MAX(id) FROM canjeables), 1));