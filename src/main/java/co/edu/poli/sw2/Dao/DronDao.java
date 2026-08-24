package co.edu.poli.sw2.Dao;

import co.edu.poli.sw2.Service.Singleton;
import co.edu.poli.sw2.model.Dron;
import co.edu.poli.sw2.model.Piloto;
import co.edu.poli.sw2.model.Sensores;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * 
 * Clase DAO encargada de realizar las operaciones de acceso a datos de la
 * entidad Dron.
 * 
 * Clase DAO (Data Access Object) encargada de realizar las operaciones de
 * acceso a datos de la entidad {@link Dron}.
 *
 * 
 * @author Jeison Romero
 * @version 1.0
 */
public class DronDao {
	/**
	 * Registra un nuevo dron en la base de datos y lo relaciona
	 * con un piloto y un sensor.
	 *
	 * @param drone objeto Dron con la información del dron.
	 * @param pilotoId identificador del piloto asociado.
	 * @param sensorId identificador del sensor asociado.
	 * @return identificador generado para el nuevo dron.
	 * @throws SQLException si ocurre un error de acceso a la base de datos.
	 */
	public int crear(Dron drone, int pilotoId, int sensorId) throws SQLException {

	    String sqlDron = "INSERT INTO dron "
	            + "(serial, modelo, fabricante, peso, piloto_id) "
	            + "VALUES (?, ?, ?, ?, ?)";

	    String sqlSensor = "INSERT INTO dron_sensor "
	            + "(dron_id, sensor_id) VALUES (?, ?)";

	    Connection conexion = Singleton.getInstance().getConexion();

	    try {

	        conexion.setAutoCommit(false);

	        try (PreparedStatement psDron = conexion.prepareStatement(
	                sqlDron,
	                java.sql.Statement.RETURN_GENERATED_KEYS);

	             PreparedStatement psSensor = conexion.prepareStatement(sqlSensor)) {

	            // Datos del dron
	            psDron.setString(1, drone.getSerial());
	            psDron.setString(2, drone.getModelo());
	            psDron.setString(3, drone.getFabricante());
	            psDron.setInt(4, drone.getPeso());

	            // Piloto asociado
	            psDron.setInt(5, pilotoId);

	            // Insertar dron
	            psDron.executeUpdate();

	            // Obtener ID generado
	            try (ResultSet rs = psDron.getGeneratedKeys()) {

	                if (!rs.next()) {
	                    conexion.rollback();
	                    return -1;
	                }

	                int idGenerado = rs.getInt(1);
	                drone.setId(idGenerado);
	            }

	            // Relacionar dron con sensor
	            psSensor.setInt(1, drone.getId());
	            psSensor.setInt(2, sensorId);
	            psSensor.executeUpdate();

	            conexion.commit();

	            return drone.getId();
	        }

	    } catch (SQLException e) {

	        conexion.rollback();
	        throw e;

	    } finally {

	        conexion.setAutoCommit(true);
	    }
	}

	/**
	 * Busca un dron utilizando su identificador.
	 *
	 * @param id identificador único del dron.
	 * @return objeto {@link Dron} encontrado o {@code null} si no existe.
	 * @throws SQLException si ocurre un error de acceso a la base de datos.
	 */
	public Dron buscar(int id) throws SQLException {

	    String sql = "SELECT id, serial, modelo, fabricante, peso, piloto_id "
	               + "FROM dron WHERE id = ?";

	    Connection conexion = Singleton.getInstance().getConexion();

	    try (PreparedStatement ps = conexion.prepareStatement(sql)) {

	        ps.setInt(1, id);

	        try (ResultSet rs = ps.executeQuery()) {

	            if (rs.next()) {

	                Dron drone = new Dron();

	                drone.setId(rs.getInt("id"));
	                drone.setSerial(rs.getString("serial"));
	                drone.setModelo(rs.getString("modelo"));
	                drone.setFabricante(rs.getString("fabricante"));
	                drone.setPeso(rs.getInt("peso"));

	           

	                int pilotoId = rs.getInt("piloto_id");

	                if (!rs.wasNull()) {

	                    Piloto piloto = new Piloto();
	                    piloto.setId(pilotoId);

	                    drone.setPiloto(piloto);
	                }

	               

	                Integer sensorId = buscarSensor(id);

	                if (sensorId != null) {

	                    Sensores sensor = new Sensores();
	                    sensor.setId(sensorId);

	                    drone.setSensores(sensor);
	                }

	                return drone;
	            }
	        }
	    }

	    return null;
	}
	/**
	 * Busca el identificador del sensor asociado a un dron.
	 *
	 * @param dronId identificador del dron.
	 * @return identificador del sensor o null si no existe.
	 */
	public Integer buscarSensor(int dronId) {

	    String sql = "SELECT sensor_id FROM dron_sensor WHERE dron_id = ?";

	    try {
	        Connection conexion = Singleton.getInstance().getConexion();

	        try (PreparedStatement ps = conexion.prepareStatement(sql)) {

	            ps.setInt(1, dronId);

	            try (ResultSet rs = ps.executeQuery()) {

	                if (rs.next()) {
	                    return rs.getInt("sensor_id");
	                }
	            }
	        }

	    } catch (SQLException e) {
	        return null;
	    }

	    return null;
	}

	/**
	 * Obtiene todos los drones registrados.
	 *
	 * @return lista de objetos {@link Dron}.
	 * @throws SQLException si ocurre un error de acceso a la base de datos.
	 */
	public List<Dron> listar() throws SQLException {

		List<Dron> drones = new ArrayList<>();

		String sql = "SELECT * FROM dron";

		Connection conexion = Singleton.getInstance().getConexion();

		try (PreparedStatement ps = conexion.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

			while (rs.next()) {

				Dron drone = new Dron();

				drone.setId(rs.getInt("id"));
				drone.setSerial(rs.getString("serial"));
				drone.setModelo(rs.getString("modelo"));
				drone.setFabricante(rs.getString("fabricante"));
				drone.setPeso(rs.getInt("peso"));
			

				drones.add(drone);
			}
		}

		return drones;
	}

	/**
	 * Actualiza la información de un dron.
	 *
	 * @param drone objeto {@link Dron} con los datos actualizados.
	 * @throws SQLException si ocurre un error de acceso a la base de datos.
	 */
	public void actualizar(Dron drone) throws SQLException {

		String sql = "UPDATE dron SET " + "serial = ?, " + "modelo = ?, " + "fabricante = ?, " + "peso = ?, "
				+ "piloto_id = ? " + "WHERE id = ?";

		Connection conexion = Singleton.getInstance().getConexion();

		try (PreparedStatement ps = conexion.prepareStatement(sql)) {

			ps.setString(1, drone.getSerial());
			ps.setString(2, drone.getModelo());
			ps.setString(3, drone.getFabricante());
			ps.setDouble(4, drone.getPeso());
		
			ps.setInt(6, drone.getId());

			ps.executeUpdate();
		}
	}

	/**
	 * Elimina un dron y sus asociaciones con sensores.
	 *
	 * @param id identificador del dron.
	 * @throws SQLException si ocurre un error de acceso a la base de datos.
	 */
	public void eliminar(int id) throws SQLException {

	    String sqlSensor = "DELETE FROM dron_sensor WHERE dron_id = ?";
	    String sqlDron = "DELETE FROM dron WHERE id = ?";

	    Connection conexion = Singleton.getInstance().getConexion();

	    try {

	        conexion.setAutoCommit(false);

	        try (PreparedStatement psSensor = conexion.prepareStatement(sqlSensor);
	             PreparedStatement psDron = conexion.prepareStatement(sqlDron)) {

	            // Eliminar asociaciones con sensores
	            psSensor.setInt(1, id);
	            psSensor.executeUpdate();

	            // Eliminar el dron
	            psDron.setInt(1, id);
	            psDron.executeUpdate();

	            conexion.commit();
	        }

	    } catch (SQLException e) {

	        conexion.rollback();
	        throw e;

	    } finally {

	        conexion.setAutoCommit(true);
	    }
	}
}
