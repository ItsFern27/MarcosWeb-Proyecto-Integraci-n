-- Crear base de datos
CREATE DATABASE integracion_proyectos;

-- Tabla de usuarios
CREATE TABLE usuarios (
    id SERIAL PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    email VARCHAR(150) UNIQUE NOT NULL,
    password VARCHAR(200) NOT NULL,
    rol VARCHAR(50) DEFAULT 'usuario', -- usuario o admin
    fecha_registro TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Tabla de proyectos
CREATE TABLE proyectos (
    id SERIAL PRIMARY KEY,
    titulo VARCHAR(200) NOT NULL,
    descripcion TEXT,
    autor_id INT REFERENCES usuarios(id) ON DELETE CASCADE,
    estado VARCHAR(50) DEFAULT 'En desarrollo',
    fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    duracion VARCHAR(50)
);

-- Tabla de miembros de proyectos
CREATE TABLE miembros_proyecto (
    id SERIAL PRIMARY KEY,
    proyecto_id INT REFERENCES proyectos(id) ON DELETE CASCADE,
    usuario_id INT REFERENCES usuarios(id) ON DELETE CASCADE,
    rol VARCHAR(100),
    fecha_union TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Tabla de portafolio
CREATE TABLE portafolio (
    id SERIAL PRIMARY KEY,
    usuario_id INT REFERENCES usuarios(id) ON DELETE CASCADE,
    titulo VARCHAR(200),
    descripcion TEXT,
    url TEXT,
    fecha TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Tabla de contactos (red de usuarios)
CREATE TABLE contactos (
    id SERIAL PRIMARY KEY,
    usuario_id INT REFERENCES usuarios(id) ON DELETE CASCADE,
    contacto_id INT REFERENCES usuarios(id) ON DELETE CASCADE,
    estado VARCHAR(50) DEFAULT 'pendiente', -- pendiente, aceptado, rechazado
    fecha TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Tabla de soporte/ayuda
CREATE TABLE tickets_soporte (
    id SERIAL PRIMARY KEY,
    usuario_id INT REFERENCES usuarios(id) ON DELETE CASCADE,
    asunto VARCHAR(200),
    mensaje TEXT,
    estado VARCHAR(50) DEFAULT 'abierto',
    fecha TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
