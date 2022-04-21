-- phpMyAdmin SQL Dump
-- version 5.0.2
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1:3306
-- Tiempo de generación: 07-12-2021 a las 13:32:19
-- Versión del servidor: 5.7.31
-- Versión de PHP: 7.3.21

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `restaurante`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `menus`
--

DROP TABLE IF EXISTS `menus`;
CREATE TABLE IF NOT EXISTS `menus` (
  `idmenu` int(11) NOT NULL,
  `nombre` text COLLATE utf8_spanish_ci NOT NULL,
  `precio` float NOT NULL,
  `cantidad` int(11) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci;

--
-- Volcado de datos para la tabla `menus`
--

INSERT INTO `menus` (`idmenu`, `nombre`, `precio`, `cantidad`) VALUES
(1, 'Lomo saltado', 14.5, 30),
(2, 'Ají de gallina', 16.5, 50),
(3, 'Arroz con pollo', 16.5, 30),
(4, 'Mondonguito a la italiana', 14.5, 40),
(5, 'Hígado encebollado', 13.5, 30),
(6, 'Pollo al horno', 13.5, 20),
(7, 'Milanesa de pescado', 14.5, 50),
(8, 'Seco de res', 15.5, 10);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `reservas`
--

DROP TABLE IF EXISTS `reservas`;
CREATE TABLE IF NOT EXISTS `reservas` (
  `nombre` text COLLATE utf8_spanish_ci NOT NULL,
  `apellidos` text COLLATE utf8_spanish_ci NOT NULL,
  `correo` text COLLATE utf8_spanish_ci NOT NULL,
  `telefono` text COLLATE utf8_spanish_ci NOT NULL,
  `idmenu` int(11) NOT NULL,
  `cantidad` int(11) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci;

--
-- Volcado de datos para la tabla `reservas`
--

INSERT INTO `reservas` (`nombre`, `apellidos`, `correo`, `telefono`, `idmenu`, `cantidad`) VALUES
('Juan', 'Valdez', 'jvaldez@gmail.com', '978654321', 1, 1),
('Fiorella', 'Salazar', 'fsalazar@gmail.com', '978654322', 3, 2),
('Roberto', 'Jimenez', 'rjimenez@gmail.com', '978654323', 4, 3),
('Silvana', 'Rodríguez', 'srodriguez@gmail.com', '978654324', 2, 1),
('Claudia', 'Márquez', 'cmarquez@gmail.com', '978654325', 1, 4),
('Rodrigo', 'Tantas', 'rtantas@gmail.com', '978654326', 1, 5);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
