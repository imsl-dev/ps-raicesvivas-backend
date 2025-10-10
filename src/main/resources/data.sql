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

INSERT INTO sponsors (nombre, link_dominio, ruta_img1, ruta_img2)
SELECT * FROM (VALUES
                   ('Coca Cola', 'https://www.coca-cola.com/ar/es', 'https://www.cocacolaep.com/assets/legacy-assets/Uploads/resources/Coca-Cola-1210__FocusFillWyIwLjAwIiwiMC4wMCIsMTM3Niw1MzJd.jpg', 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTQe0QogSQXZmAqf45bXvsjKT4SyWlcuvJajA&s'),
                   ('Grido', 'https://argentina.gridohelado.com/', 'https://media.licdn.com/dms/image/v2/D4D0BAQH0hm5N81H1zA/company-logo_200_200/B4DZcxhTzvGgAQ-/0/1748882505376/gridohelados_logo?e=2147483647&v=beta&t=GeEC_C1FzEi2Utpsdt-2UxgNkvbh2FlofsQrlliWx-4', 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTfFOMdRG-pX8Vb-T73Ipvdkvw_k2ILfAC3Bw&s'),
                   ('Branca', 'https://www.branca.com.ar/', 'https://lacoloniawinestore.com.ar/wp-content/uploads/2021/08/LOGO-BRANCA.png', '')
              ) AS v(nombre, link_dominio, ruta_img1, ruta_img2)
WHERE NOT EXISTS (
    SELECT 1 FROM sponsors s WHERE s.nombre = v.nombre
);

-- Inserts para eventos
INSERT INTO eventos (tipo, estado, organizador_id, cuenta_bancaria_id, provincia_id, nombre, descripcion, ruta_img, direccion, hora_inicio, hora_fin, puntos_asistencia, costo_interno, costo_inscripcion, sponsor_id)
SELECT
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
                   ('REFORESTACION', 'PENDIENTE', 1, NULL, 11, 'Reforestación Sierras Grandes', 'La reforestación en las sierras de Córdoba se enfoca en la restauración de los bosques nativos, especialmente con el árbol tabaquillo (\(PolylepisAustralis\)), que es vital para la recuperación de suelos, el ciclo hídrico y la biodiversidad.', 'https://www.unc.edu.ar/sites/default/files/RGB.jpg', 'Centro-noroeste de la provincia de Córdoba', '2025-11-07 07:00:00', '2025-11-07 17:30:00', 20, 500, 100, 1),
                   ('RECOLECCION_BASURA', 'PENDIENTE', 1, NULL, 11, 'Recolección de basura Villa Urquiza', 'Se hará una recolección de basura volutaria para ayudar a mejorar la condición de los residentes de Villa Urquiza', 'https://cordoba.gob.ar/wp-content/uploads/2021/03/WhatsApp-Image-2021-03-19-at-15.56.13-800x400.jpeg', 'Villa Urquiza, Córdoba Capital', '2025-10-24 10:30:00', '2025-10-24 15:30:00', 10, 300, 0, 2),
                   ('REFORESTACION', 'CANCELADO', 1, NULL, 9, 'Reforestacion de Almirante Brown', 'Se busca reforestar 20 km2 en el Departamento Almirante Brown de Chaco. /nLas acciones de reforestación están principalmente enfocadas en contrarrestar la deforestación ilegal y proteger los bosques nativos.', 'https://econoticias.com.ar/wp-content/uploads/2024/09/bol_visita_fao_gran_chaco_americano_onu_bolivia_credito_morelia_erostegui-44_0-scaled.jpg', 'Almirante Brown, Chaco', '2025-10-11 09:00:00', '2025-10-11 16:30:00', 15, 200, 100, 2),
                   ('JUNTA_ALIMENTOS', 'EN_CURSO', 1, NULL, 10, 'Dormir con la panza llena', 'Unimos fuerzas para llevar alimentos a quienes más lo necesitan. Sumate con tu donación y ayudanos a llenar de esperanza las mesas de muchas familias.', 'https://www.bbva.com/wp-content/uploads/2024/04/BBVA-donacion-alimentos-sostenibilidad.jpg', 'Dr. Pedro Minuzzi 428, M5501 Godoy Cruz, Mendoza', '2025-10-07 07:00:00', '2025-10-31 17:30:00', 10, 0, 0, 1),
                   ('DONACIONES', 'FINALIZADO', 1, NULL, 18, 'Apoyo a los niños de Cochagual', 'Tras el terremoto de 2021, la Escuela Paulo VI quedó en condiciones que obligaron a sus alumnos a estudiar en módulos provisorios. Fue un desafío enorme para las familias, los docentes y toda la comunidad. /n/n🏫 Hoy la realidad es distinta: ya cuentan con un edificio nuevo, seguro y moderno, con aulas cómodas y servicios que garantizan la tranquilidad de enseñar y aprender con las condiciones adecuadas.', 'https://sisanjuan.b-cdn.net/media/k2/items/cache/ae44d2dd91a73b393523b3a0d4ac8bc4_L.jpg', 'CARMONA S/N COCHAGUAL CENTRO', '2025-10-03 07:00:00', '2025-10-03 21:00:00', 15, 100, NULL, 3)
              ) AS v(tipo, estado, organizador_id, cuenta_bancaria_id, provincia_id, nombre, descripcion, ruta_img, direccion, hora_inicio, hora_fin, puntos_asistencia, costo_interno, costo_inscripcion, sponsor_id)
WHERE NOT EXISTS (
    SELECT 1 FROM eventos e WHERE e.nombre = v.nombre
);
