package co.edu.poli.sw2.Dao;

import co.edu.poli.sw2.model.Dron;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase DAO encargada de realizar las operaciones de acceso a datos de la
 * entidad Dron.
 *
 * @author Jeison Romero
 * @version 1.0
 */
public class DronDao {

	/**
	 * Registra un nuevo dron en la base de datos y lo relaciona con un sensor.
	 *
	 * @param drone objeto Dron que contiene los datos del dron.
	 * @param sensorId identificador del sensor asociado al dron.
	 * @return true si el dron y su relación con el sensor fueron registrados
	 *         correctamente; false si ocurrió un error.
	 */
	public boolean crear(Dron drone, int sensorId) {

		String sqlDron = "INSERT INTO dron "
				+ "(id, serial, modelo, fabricante, peso, piloto_id) "
				+ "VALUES (?, ?, ?, ?, ?, ?)";

		String sqlSensor = "INSERT INTO dron_sensor "
				+ "(dron_id, sensor_id) VALUES (?, ?)";

		try (Connection conexion = ConexionBD.conectar()) {

			conexion.setAutoCommit(false);

			try (PreparedStatement psDron = conexion.prepareStatement(sqlDron);
					PreparedStatement psSensor = conexion.prepareStatement(sqlSensor)) {

				// Guardar dron
				psDron.setInt(1, drone.getId());
				psDron.setString(2, drone.getSerial());
				psDron.setString(3, drone.getModelo());
				psDron.setString(4, drone.getFabricante());
				psDron.setDouble(5, drone.getPeso());
				psDron.setInt(6, drone.getPilotoId());

				psDron.executeUpdate();

				// Guardar relación Dron-Sensor
				psSensor.setInt(1, drone.getId());
				psSensor.setInt(2, sensorId);

				psSensor.executeUpdate();

				conexion.commit();

				return true;

			} catch (SQLException e) {

				conexion.rollback();

				System.out.println("ERROR AL CREAR EL DRON Y ASOCIAR EL SENSOR:");
				e.printStackTrace();

				return false;
			}

		} catch (SQLException e) {

			System.out.println("ERROR DE CONEXIÓN:");
			e.printStackTrace();

			return false;
		}
	}

	/**
	 * Busca un dron utilizando su identificador.
	 *
	 * @param id identificador del dron.
	 * @return Dron encontrado o null si no existe.
	 */
	public Dron buscar(int id) {

		String sql = "SELECT * FROM dron WHERE id = ?";

		try (Connection conexion = ConexionBD.conectar();
				PreparedStatement ps = conexion.prepareStatement(sql)) {

			ps.setInt(1, id);

			ResultSet rs = ps.executeQuery();

			if (rs.next()) {

				Dron drone = new Dron();

				drone.setId(rs.getInt("id"));
				drone.setSerial(rs.getString("serial"));
				drone.setModelo(rs.getString("modelo"));
				drone.setFabricante(rs.getString("fabricante"));
				drone.setPeso(rs.getDouble("peso"));
				drone.setPilotoId(rs.getInt("piloto_id"));

				return drone;
			}

		} catch (SQLException e) {

			System.out.println("Error al buscar el dron: " + e.getMessage());
		}

		return null;
	}
	/**
	 * Busca el identificador del sensor asociado a un dron.
	 *
	 * @param dronId identificador del dron.
	 * @return identificador del sensor asociado al dron o null si no existe
	 *         una relación registrada.
	 */
	public Integer buscarSensor(int dronId) {

		String sql = "SELECT sensor_id FROM dron_sensor WHERE dron_id = ?";

		try (Connection conexion = ConexionBD.conectar();
				PreparedStatement ps = conexion.prepareStatement(sql)) {

			ps.setInt(1, dronId);

			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				return rs.getInt("sensor_id");
			}

		} catch (SQLException e) {

			System.out.println("Error al buscar el sensor del dron: " + e.getMessage());
		}

		return null;
	}
	/**
	 * Obtiene todos los drones registrados.
	 *
	 * @return lista de drones.
	 */
	public List<Dron> listar() {

		List<Dron> drones = new ArrayList<>();

		String sql = "SELECT * FROM dron";

		try (Connection conexion = ConexionBD.conectar();
				PreparedStatement ps = conexion.prepareStatement(sql);
				ResultSet rs = ps.executeQuery()) {

			while (rs.next()) {

				Dron drone = new Dron();

				drone.setId(rs.getInt("id"));
				drone.setSerial(rs.getString("serial"));
				drone.setModelo(rs.getString("modelo"));
				drone.setFabricante(rs.getString("fabricante"));
				drone.setPeso(rs.getDouble("peso"));
				drone.setPilotoId(rs.getInt("piloto_id"));

				drones.add(drone);
			}

		} catch (SQLException e) {

			System.out.println("Error al listar los drones: " + e.getMessage());
		}

		return drones;
	}

	/**
	 * Actualiza la información de un dron.
	 *
	 * @param drone objeto Dron con los datos actualizados.
	 */
	public void actualizar(Dron drone) {

		String sql = "UPDATE dron SET "
				+ "serial = ?, "
				+ "modelo = ?, "
				+ "fabricante = ?, "
				+ "peso = ?, "
				+ "piloto_id = ? "
				+ "WHERE id = ?";

		try (Connection conexion = ConexionBD.conectar();
				PreparedStatement ps = conexion.prepareStatement(sql)) {

			ps.setString(1, drone.getSerial());
			ps.setString(2, drone.getModelo());
			ps.setString(3, drone.getFabricante());
			ps.setDouble(4, drone.getPeso());
			ps.setInt(5, drone.getPilotoId());
			ps.setInt(6, drone.getId());

			ps.executeUpdate();

			System.out.println("Dron actualizado correctamente.");

		} catch (SQLException e) {

			System.out.println("Error al actualizar el dron: " + e.getMessage());
		}
	
	}

	/**
	 * Elimina un dron utilizando su identificador.
	 *
	 * @param id identificador del dron.
	 */
	public void eliminar(int id) {

		String sql = "DELETE FROM dron WHERE id = ?";

		try (Connection conexion = ConexionBD.conectar();
				PreparedStatement ps = conexion.prepareStatement(sql)) {

			ps.setInt(1, id);

			ps.executeUpdate();

			System.out.println("Dron eliminado correctamente.");

		} catch (SQLException e) {

			System.out.println("Error al eliminar el dron: " + e.getMessage());
		}
	}
}