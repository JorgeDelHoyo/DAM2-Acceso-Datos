-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 10-11-2025 a las 12:31:25
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
-- Base de datos: `accesodatos`
--
CREATE DATABASE IF NOT EXISTS `accesodatos` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
USE `accesodatos`;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `alimentos`
--

DROP TABLE IF EXISTS `alimentos`;
CREATE TABLE `alimentos` (
  `id` int(11) NOT NULL,
  `nombre` varchar(100) NOT NULL,
  `calorias` int(11) NOT NULL,
  `valor_energetico` decimal(6,2) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `alimentos`
--

INSERT INTO `alimentos` (`id`, `nombre`, `calorias`, `valor_energetico`) VALUES
(1, 'Pollo (pechuga sin piel)', 165, 690.00),
(2, 'Arroz blanco cocido', 130, 545.00),
(3, 'Aguacate', 160, 670.00),
(4, 'Manzana', 52, 218.00),
(5, 'Plátano', 89, 372.00),
(6, 'Huevo', 155, 650.00),
(7, 'Salmón', 208, 870.00),
(8, 'Pan integral', 250, 1045.00),
(9, 'Leche entera', 61, 255.00),
(10, 'Yogur natural', 59, 247.00),
(11, 'Queso fresco', 98, 410.00),
(12, 'Aceite de oliva', 884, 3696.00),
(13, 'Avena', 389, 1630.00),
(14, 'Pasta cocida', 131, 550.00),
(15, 'Patata cocida', 86, 360.00),
(16, 'Tomate', 18, 75.00),
(17, 'Zanahoria', 41, 172.00),
(18, 'Lentejas cocidas', 116, 485.00),
(19, 'Atún en lata (al natural)', 116, 485.00),
(20, 'Almendras', 579, 2423.00);

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `alimentos`
--
ALTER TABLE `alimentos`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `alimentos`
--
ALTER TABLE `alimentos`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=21;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
