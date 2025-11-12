CREATE DATABASE eco_assets
CHARACTER SET utf8mb4
COLLATE utf8mb4_spanish_ci;

USE eco_assets;

-- Tabla de usuarios
CREATE TABLE usuario (
    id_usuario INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(50) NOT NULL UNIQUE,
    correo VARCHAR(100) NOT NULL UNIQUE,
    celular VARCHAR(20) NOT NULL,
    contrasena VARCHAR(255) NOT NULL,
    es_admin BOOLEAN DEFAULT FALSE,
    fecha_registro TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    activo BOOLEAN DEFAULT TRUE,
    INDEX idx_correo (correo),
    INDEX idx_nombre (nombre)
);

-- Tabla de marcas
CREATE TABLE marca (
    id_marca INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(50) NOT NULL UNIQUE
);

-- Tabla de categorías (para estructura de árbol)
CREATE TABLE categoria (
    id_categoria INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(50) NOT NULL,
    categoria_padre_id INT UNSIGNED,
    nivel INT DEFAULT 0,
    FOREIGN KEY (categoria_padre_id) REFERENCES categoria(id_categoria)
        ON DELETE SET NULL
        ON UPDATE CASCADE
);

-- Tabla de dispositivos
CREATE TABLE dispositivo (
    codigo INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    descripcion TEXT,
    estado ENUM('Nuevo', 'Usado - Como Nuevo', 'Usado - Buen Estado', 
                'Usado - Estado Aceptable', 'Para Repuestos') NOT NULL,
    funciona BOOLEAN NOT NULL DEFAULT TRUE,
    es_especial BOOLEAN DEFAULT FALSE,
    anio_fabricacion YEAR,
    anio_primera_compra YEAR,
    cantidad INT UNSIGNED NOT NULL DEFAULT 1,
    precio_base DECIMAL(12,2) NOT NULL,
    acepta_pujas BOOLEAN DEFAULT FALSE,
    precio_actual DECIMAL(12,2),
    vendido BOOLEAN DEFAULT FALSE,
    fecha_publicacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    usuario_id INT UNSIGNED NOT NULL,
    marca_id INT UNSIGNED NOT NULL,
    categoria_id INT UNSIGNED NOT NULL,
    FOREIGN KEY (usuario_id) REFERENCES usuario(id_usuario)
        ON DELETE CASCADE
        ON UPDATE CASCADE,
    FOREIGN KEY (marca_id) REFERENCES marca(id_marca)
        ON DELETE RESTRICT
        ON UPDATE CASCADE,
    FOREIGN KEY (categoria_id) REFERENCES categoria(id_categoria)
        ON DELETE RESTRICT
        ON UPDATE CASCADE,
    INDEX idx_categoria (categoria_id),
    INDEX idx_precio (precio_actual),
    INDEX idx_fecha (fecha_publicacion)
);

-- Tabla de imágenes de dispositivos
CREATE TABLE imagen_dispositivo (
    id_imagen INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    dispositivo_codigo INT UNSIGNED NOT NULL,
    url_imagen VARCHAR(255) NOT NULL,
    es_principal BOOLEAN DEFAULT FALSE,
    orden INT DEFAULT 0,
    FOREIGN KEY (dispositivo_codigo) REFERENCES dispositivo(codigo)
        ON DELETE CASCADE
        ON UPDATE CASCADE
);

-- Tabla de pujas
CREATE TABLE puja (
    id_puja INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    dispositivo_codigo INT UNSIGNED NOT NULL,
    usuario_id INT UNSIGNED NOT NULL,
    monto DECIMAL(12,2) NOT NULL,
    fecha_puja TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    estado ENUM('Pendiente', 'Aceptada', 'Rechazada') DEFAULT 'Pendiente',
    FOREIGN KEY (dispositivo_codigo) REFERENCES dispositivo(codigo)
        ON DELETE CASCADE
        ON UPDATE CASCADE,
    FOREIGN KEY (usuario_id) REFERENCES usuario(id_usuario)
        ON DELETE CASCADE
        ON UPDATE CASCADE,
    INDEX idx_dispositivo (dispositivo_codigo),
    INDEX idx_fecha (fecha_puja)
);

-- Tabla de carritos
CREATE TABLE carrito (
    id_carrito INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    usuario_id INT UNSIGNED NOT NULL,
    fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    activo BOOLEAN DEFAULT TRUE,
    FOREIGN KEY (usuario_id) REFERENCES usuario(id_usuario)
        ON DELETE CASCADE
        ON UPDATE CASCADE
);

-- Tabla de items del carrito
CREATE TABLE item_carrito (
    id_item INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    carrito_id INT UNSIGNED NOT NULL,
    dispositivo_codigo INT UNSIGNED NOT NULL,
    cantidad INT UNSIGNED NOT NULL DEFAULT 1,
    precio_unitario DECIMAL(12,2) NOT NULL,
    FOREIGN KEY (carrito_id) REFERENCES carrito(id_carrito)
        ON DELETE CASCADE
        ON UPDATE CASCADE,
    FOREIGN KEY (dispositivo_codigo) REFERENCES dispositivo(codigo)
        ON DELETE CASCADE
        ON UPDATE CASCADE
);

-- Tabla de pedidos/órdenes
CREATE TABLE pedido (
    id_pedido INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    usuario_id INT UNSIGNED NOT NULL,
    subtotal DECIMAL(12,2) NOT NULL,
    iva DECIMAL(12,2) NOT NULL,
    total DECIMAL(12,2) NOT NULL,
    estado ENUM('Pendiente', 'Confirmado', 'Enviado', 'Entregado', 'Cancelado') DEFAULT 'Pendiente',
    fecha_pedido TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    fecha_actualizacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (usuario_id) REFERENCES usuario(id_usuario)
        ON DELETE CASCADE
        ON UPDATE CASCADE,
    INDEX idx_fecha (fecha_pedido),
    INDEX idx_usuario (usuario_id)
);

-- Tabla de detalles del pedido
CREATE TABLE detalle_pedido (
    id_detalle INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    pedido_id INT UNSIGNED NOT NULL,
    dispositivo_codigo INT UNSIGNED NOT NULL,
    cantidad INT UNSIGNED NOT NULL,
    precio_unitario DECIMAL(12,2) NOT NULL,
    subtotal DECIMAL(12,2) NOT NULL,
    FOREIGN KEY (pedido_id) REFERENCES pedido(id_pedido)
        ON DELETE CASCADE
        ON UPDATE CASCADE,
    FOREIGN KEY (dispositivo_codigo) REFERENCES dispositivo(codigo)
        ON DELETE RESTRICT
        ON UPDATE CASCADE
);

-- Tabla de banners promocionales
CREATE TABLE banner (
    id_banner INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    titulo VARCHAR(100),
    url_imagen VARCHAR(255) NOT NULL,
    url_destino VARCHAR(255),
    orden INT DEFAULT 0,
    activo BOOLEAN DEFAULT TRUE,
    fecha_inicio DATE,
    fecha_fin DATE
);

-- Insertar categorías (estructura de árbol)
INSERT INTO categoria (nombre, categoria_padre_id, nivel) VALUES
-- Nivel 0 (raíz)
('Computación', NULL, 0),
('Telefonía', NULL, 0),
('Audio y Video', NULL, 0),
('Gaming', NULL, 0),
('Accesorios', NULL, 0),
('Dispositivos Especiales', NULL, 0),

-- Nivel 1 (Computación)
('Computadores de Escritorio', 1, 1),
('Portátiles', 1, 1),
('Tablets', 1, 1),
('Componentes de PC', 1, 1),

-- Nivel 1 (Telefonía)
('Teléfonos Móviles', 2, 1),
('Teléfonos Fijos', 2, 1),

-- Nivel 1 (Audio y Video)
('Audífonos', 3, 1),
('Parlantes', 3, 1),
('Cámaras', 3, 1),
('Televisores', 3, 1),

-- Nivel 1 (Gaming)
('Consolas', 4, 1),
('Videojuegos', 4, 1),
('Controles', 4, 1),

-- Nivel 2 (Componentes de PC)
('Procesadores', 10, 2),
('Tarjetas Gráficas', 10, 2),
('Memoria RAM', 10, 2),
('Almacenamiento', 10, 2);

-- Insertar marcas comunes
INSERT INTO marca (nombre) VALUES
('Apple'), ('Samsung'), ('Huawei'), ('Xiaomi'), ('LG'),
('Sony'), ('Microsoft'), ('Nintendo'), ('HP'), ('Dell'),
('Lenovo'), ('Asus'), ('Acer'), ('MSI'), ('Razer'),
('Bose'), ('JBL'), ('Logitech'), ('Canon'), ('Nikon'),
('Intel'), ('AMD'), ('NVIDIA'), ('Corsair'), ('Kingston');

-- Crear usuario administrador por defecto (contraseña: admin123)
INSERT INTO usuario (nombre, correo, celular, contrasena, es_admin) VALUES
('Administrador', 'admin@ecoassets.com', '3001234567', 
 '$2a$10$XYZ...', TRUE);

-- Insertar banners de ejemplo
INSERT INTO banner (titulo, url_imagen, url_destino, orden, activo) VALUES
('Promoción Gaming', 'img/banner1.jpg', '/categoria/gaming', 1, TRUE),
('Especiales Retro', 'img/banner2.jpg', '/especiales', 2, TRUE),
('Ofertas del Mes', 'img/banner3.jpg', '/ofertas', 3, TRUE),
('Nuevos Ingresos', 'img/banner4.jpg', '/nuevos', 4, TRUE);