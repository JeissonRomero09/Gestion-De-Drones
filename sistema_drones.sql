-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 19-09-2026 a las 20:16:20
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
-- Base de datos: `sistema_drones`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `agricultura`
--

CREATE TABLE `agricultura` (
  `id` int(11) NOT NULL,
  `capacidad_tanque` double DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `agricultura`
--

INSERT INTO `agricultura` (`id`, `capacidad_tanque`) VALUES
(43, 0);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `dron`
--

CREATE TABLE `dron` (
  `id` int(11) NOT NULL,
  `serial` varchar(100) NOT NULL,
  `modelo` varchar(100) NOT NULL,
  `fabricante` varchar(100) NOT NULL,
  `peso` int(11) NOT NULL,
  `piloto_id` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `dron`
--

INSERT INTO `dron` (`id`, `serial`, `modelo`, `fabricante`, `peso`, `piloto_id`) VALUES
(30, '111', '1111', '1', 1, NULL),
(31, '3', '1', '1', 1, NULL),
(32, '113', '14', '13', 13, NULL),
(33, '123', '123', '123', 123, NULL),
(36, '12323', '12323', '12323', 12323, NULL),
(38, '333432', '334324', '33', 33, NULL),
(39, 'A-110', 'DRK', 'Kasd', 134, NULL),
(40, 'M-100', '100', 'kas', 100, NULL),
(41, 'M-200', '300', 'kas', 324, NULL),
(42, '545', '2334', '45', 4545, NULL),
(43, '32', '12', '4334', 534, NULL);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `mision`
--

CREATE TABLE `mision` (
  `id` int(11) NOT NULL,
  `nombre` varchar(100) NOT NULL,
  `ubicacion` varchar(200) NOT NULL,
  `fecha` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `mision`
--

INSERT INTO `mision` (`id`, `nombre`, `ubicacion`, `fecha`) VALUES
(1, 'Mision de reconocimiento', 'Bogota', '2026-09-19');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `mision_dron`
--

CREATE TABLE `mision_dron` (
  `mision_id` int(11) NOT NULL,
  `dron_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `mision_dron`
--

INSERT INTO `mision_dron` (`mision_id`, `dron_id`) VALUES
(1, 43);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `piloto`
--

CREATE TABLE `piloto` (
  `id` int(11) NOT NULL,
  `nombre` varchar(100) NOT NULL,
  `experiencia` varchar(100) DEFAULT NULL,
  `telefono` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `sensores`
--

CREATE TABLE `sensores` (
  `id` int(11) NOT NULL,
  `tipo` varchar(100) NOT NULL,
  `fabricante` varchar(100) NOT NULL,
  `dron_id` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `vigilancia`
--

CREATE TABLE `vigilancia` (
  `id` int(11) NOT NULL,
  `deteccion_termica` tinyint(1) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `agricultura`
--
ALTER TABLE `agricultura`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `dron`
--
ALTER TABLE `dron`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `serial` (`serial`),
  ADD UNIQUE KEY `piloto_id` (`piloto_id`);

--
-- Indices de la tabla `mision`
--
ALTER TABLE `mision`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `mision_dron`
--
ALTER TABLE `mision_dron`
  ADD PRIMARY KEY (`mision_id`,`dron_id`),
  ADD KEY `fk_mision_dron_dron` (`dron_id`);

--
-- Indices de la tabla `piloto`
--
ALTER TABLE `piloto`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `sensores`
--
ALTER TABLE `sensores`
  ADD PRIMARY KEY (`id`),
  ADD KEY `fk_sensor_dron` (`dron_id`);

--
-- Indices de la tabla `vigilancia`
--
ALTER TABLE `vigilancia`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `dron`
--
ALTER TABLE `dron`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=44;

--
-- AUTO_INCREMENT de la tabla `mision`
--
ALTER TABLE `mision`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT de la tabla `piloto`
--
ALTER TABLE `piloto`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `sensores`
--
ALTER TABLE `sensores`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `agricultura`
--
ALTER TABLE `agricultura`
  ADD CONSTRAINT `agricultura_ibfk_1` FOREIGN KEY (`id`) REFERENCES `dron` (`id`);

--
-- Filtros para la tabla `dron`
--
ALTER TABLE `dron`
  ADD CONSTRAINT `fk_dron_piloto` FOREIGN KEY (`piloto_id`) REFERENCES `piloto` (`id`);

--
-- Filtros para la tabla `mision_dron`
--
ALTER TABLE `mision_dron`
  ADD CONSTRAINT `fk_mision_dron_dron` FOREIGN KEY (`dron_id`) REFERENCES `dron` (`id`),
  ADD CONSTRAINT `fk_mision_dron_mision` FOREIGN KEY (`mision_id`) REFERENCES `mision` (`id`);

--
-- Filtros para la tabla `sensores`
--
ALTER TABLE `sensores`
  ADD CONSTRAINT `fk_sensor_dron` FOREIGN KEY (`dron_id`) REFERENCES `dron` (`id`);

--
-- Filtros para la tabla `vigilancia`
--
ALTER TABLE `vigilancia`
  ADD CONSTRAINT `vigilancia_ibfk_1` FOREIGN KEY (`id`) REFERENCES `dron` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
