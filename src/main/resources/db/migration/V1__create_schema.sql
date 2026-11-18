CREATE TABLE usuario (
    id_usuario SERIAL PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    correo VARCHAR(150) NOT NULL UNIQUE,
    contrasena VARCHAR(255) NOT NULL,
    foto TEXT,
    wsp_num VARCHAR(20) NOT NULL,
    rol VARCHAR(30) NOT NULL DEFAULT 'VENDEDOR',
    creado_en TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE publicacion (
    id_publicacion SERIAL PRIMARY KEY,
    titulo VARCHAR(150) NOT NULL,
    descripcion TEXT,
    reglas TEXT,
    autor INTEGER NOT NULL,
    precio NUMERIC(10, 2) NOT NULL,
    wsp_num VARCHAR(20) NOT NULL,
    calle VARCHAR(150),
    numero VARCHAR(20),
    colonia VARCHAR(150),
    codigo_postal VARCHAR(10),
    latitud DOUBLE PRECISION,
    longitud DOUBLE PRECISION,
    modalidad VARCHAR(20),
    tipo VARCHAR(30),
    estado VARCHAR(20) NOT NULL DEFAULT 'PUBLICADA',
    activa BOOLEAN NOT NULL DEFAULT TRUE,
    superficie INTEGER,
    uso_suelo VARCHAR(30),
    dormitorios INTEGER,
    banos INTEGER,
    cochera INTEGER,
    piso INTEGER,
    niveles INTEGER,
    amueblado BOOLEAN NOT NULL DEFAULT FALSE,
    mascotas BOOLEAN NOT NULL DEFAULT FALSE,
    elevador BOOLEAN NOT NULL DEFAULT FALSE,
    patio BOOLEAN NOT NULL DEFAULT FALSE,
    creado_en TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_publicacion_usuario
        FOREIGN KEY (autor) REFERENCES usuario(id_usuario) ON DELETE CASCADE
);

CREATE TABLE publicacion_foto (
    id_foto SERIAL PRIMARY KEY,
    id_publicacion INTEGER NOT NULL,
    foto_url TEXT NOT NULL,
    orden INTEGER NOT NULL DEFAULT 1,
    creado_en TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_foto_publicacion
        FOREIGN KEY (id_publicacion) REFERENCES publicacion(id_publicacion) ON DELETE CASCADE
);

CREATE INDEX idx_publicacion_autor ON publicacion(autor);
