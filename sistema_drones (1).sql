-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 24-08-2026 a las 22:58:54
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
(4, '1', '1', '1', 1, 1);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `dron_sensor`
--

CREATE TABLE `dron_sensor` (
  `dron_id` int(11) NOT NULL,
  `sensor_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `dron_sensor`
--

INSERT INTO `dron_sensor` (`dron_id`, `sensor_id`) VALUES
(4, 1);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `piloto`
--

CREATE TABLE `piloto` (
  `id` int(11) NOT NULL,
  `nombre` varchar(100) NOT NULL,
  `experiencia` varchar(100) NOT NULL,
  `telefono` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `piloto`
--

INSERT INTO `piloto` (`id`, `nombre`, `experiencia`, `telefono`) VALUES
(1, 'Carlos Rodriguez', 'Avanzado', 2147483647);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `sensor`
--

CREATE TABLE `sensor` (
  `id` int(11) NOT NULL,
  `tipo` varchar(100) NOT NULL,
  `fabricante` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `sensor`
--

INSERT INTO `sensor` (`id`, `tipo`, `fabricante`) VALUES
(1, 'Camara', 'DJI');

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `dron`
--
ALTER TABLE `dron`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `serial` (`serial`),
  ADD UNIQUE KEY `uq_dron_piloto` (`piloto_id`);

--
-- Indices de la tabla `dron_sensor`
--
ALTER TABLE `dron_sensor`
  ADD PRIMARY KEY (`dron_id`,`sensor_id`),
  ADD KEY `sensor_id` (`sensor_id`);

--
-- Indices de la tabla `piloto`
--
ALTER TABLE `piloto`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `sensor`
--
ALTER TABLE `sensor`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `dron`
--
ALTER TABLE `dron`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT de la tabla `piloto`
--
ALTER TABLE `piloto`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT de la tabla `sensor`
--
ALTER TABLE `sensor`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `dron`
--
ALTER TABLE `dron`
  ADD CONSTRAINT `fk_dron_piloto` FOREIGN KEY (`piloto_id`) REFERENCES `piloto` (`id`);

--
-- Filtros para la tabla `dron_sensor`
--
ALTER TABLE `dron_sensor`
  ADD CONSTRAINT `dron_sensor_ibfk_1` FOREIGN KEY (`dron_id`) REFERENCES `dron` (`id`),
  ADD CONSTRAINT `dron_sensor_ibfk_2` FOREIGN KEY (`sensor_id`) REFERENCES `sensor` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
