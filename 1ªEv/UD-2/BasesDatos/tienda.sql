-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 10-11-2025 a las 12:29:40
-- Versión del servidor: 10.4.32-MariaDB
-- Versión de PHP: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `tienda`
--
CREATE DATABASE IF NOT EXISTS `tienda` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
USE `tienda`;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `producto`
--

DROP TABLE IF EXISTS `producto`;
CREATE TABLE `producto` (
  `id` int(11) NOT NULL,
  `nombre` varchar(100) NOT NULL,
  `precio` decimal(10,2) NOT NULL,
  `descripcion` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `producto`
--

INSERT INTO `producto` (`id`, `nombre`, `precio`, `descripcion`) VALUES
(1, 'Camiseta básica blanca', 12.99, 'Camiseta de algodón 100% color blanco, talla única'),
(2, 'Pantalón vaquero azul', 34.50, 'Pantalón denim clásico con corte regular'),
(3, 'Sudadera con capucha', 29.90, 'Sudadera gris con capucha ajustable y bolsillo delantero'),
(4, 'Zapatillas deportivas', 59.99, 'Zapatillas ligeras con suela de goma antideslizante'),
(5, 'Chaqueta impermeable', 45.00, 'Chaqueta resistente al agua con cierre de cremallera'),
(6, 'Cinturón de cuero', 18.75, 'Cinturón de piel marrón con hebilla metálica'),
(7, 'Calcetines deportivos (pack 3)', 9.95, 'Calcetines transpirables en color blanco'),
(8, 'Gorra negra', 14.20, 'Gorra ajustable color negro con logotipo bordado'),
(9, 'Reloj digital', 49.99, 'Reloj resistente al agua con cronómetro y alarma'),
(10, 'Bolso de mano', 39.90, 'Bolso de cuero sintético color beige'),
(11, 'Bufanda de lana', 15.50, 'Bufanda suave color gris para invierno'),
(12, 'Guantes térmicos', 12.00, 'Guantes de tejido térmico para bajas temperaturas'),
(13, 'Camiseta estampada', 17.25, 'Camiseta con estampado gráfico, disponible en varias tallas'),
(14, 'Pantalón deportivo', 28.90, 'Pantalón de chándal con bolsillos laterales'),
(15, 'Mochila escolar', 31.80, 'Mochila con varios compartimentos y soporte lumbar'),
(16, 'Zapatos de vestir', 64.00, 'Zapatos de piel negra con cordones'),
(17, 'Gafas de sol', 22.95, 'Gafas con protección UV400 y montura ligera'),
(18, 'Perfume unisex 100ml', 49.50, 'Fragancia fresca con notas cítricas y amaderadas'),
(19, 'Camiseta sin mangas', 10.99, 'Camiseta de tirantes color azul marino'),
(20, 'Sombrero de verano', 19.99, 'Sombrero de paja natural con cinta decorativa');

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `producto`
--
ALTER TABLE `producto`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `producto`
--
ALTER TABLE `producto`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=21;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
