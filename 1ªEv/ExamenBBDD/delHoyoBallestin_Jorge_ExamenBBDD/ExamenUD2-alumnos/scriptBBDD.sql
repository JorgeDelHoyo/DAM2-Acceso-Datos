-- ============================================
-- CREAR BASE DE DATOS
-- ============================================
DROP DATABASE IF EXISTS `examen-ud2-tienda`;
CREATE DATABASE `examen-ud2-tienda`;
USE `examen-ud2-tienda`;

-- ============================================
-- TABLA CLIENTE
-- ============================================
CREATE TABLE cliente (
    id INT PRIMARY KEY AUTO_INCREMENT,
    nombre VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL
);

-- ============================================
-- TABLA PEDIDO
-- ============================================
CREATE TABLE pedido (
    id INT PRIMARY KEY AUTO_INCREMENT,
    importe DOUBLE NOT NULL,
    id_cliente INT NOT NULL,
    CONSTRAINT fk_pedido_cliente
        FOREIGN KEY (id_cliente)
        REFERENCES cliente(id)
        ON DELETE CASCADE
);

-- ============================================
-- INSERTS INICIALES (2 clientes sin pedidos)
-- Mismos datos que en el ejemplo Java
-- ============================================
INSERT INTO cliente (nombre, email)
VALUES
('Juan Pérez', 'juan@example.com'),
('Ana López', 'ana@example.com');

-- ============================================
-- FIN DEL SCRIPT
-- ============================================
