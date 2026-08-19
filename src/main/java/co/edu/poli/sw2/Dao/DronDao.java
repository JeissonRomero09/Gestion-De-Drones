package co.edu.poli.sw2.Dao;

import co.edu.poli.sw2.model.Dron;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase DAO (Data Access Object) encargada de realizar las operaciones de
 * acceso a datos de la entidad {@link Dron}.
 *
 * @author Jeison Romero
 * @version 1.0
 */
public class DronDao {

	/**
	 * Inserta un nuevo dron en la base de datos.
	 *
	 * @param drone objeto {@link Dron} con la información a registrar.
	 * @throws SQLException si ocurre un error de acceso a la base de datos.
	 */
	public void crear(Dron drone) throws SQLException {

		String sql = "INSERT INTO dron (serial, modelo, fabricante, peso, piloto_id, sensor_id) "
				+ "VALUES (?, ?, ?, ?, ?, ?)";

		Connection conexion = ConexionBD.getInstance().getConexion();

		try (PreparedStatement ps = conexion.prepareStatement(sql)) {

			ps.setString(1, drone.getSerial());
			ps.setString(2, drone.getModelo());
			ps.setString(3, drone.getFabricante());
			ps.setDouble(4, drone.getPeso());
			ps.setInt(5, drone.getPilotoId());
			ps.setInt(6, drone.getsensorid());

			ps.executeUpdate();
		}
	}

	/**
	 * Busca un dron en la base de datos utilizando su identificador.
	 *
	 * @param id identificador único del dron.
	 * @return objeto {@link Dron} encontrado o {@code null} si no existe.
	 * @throws SQLException si ocurre un error de acceso a la base de datos.
	 */
	public Dron buscar(int id) throws SQLException {

		String sql = "SELECT * FROM dron WHERE id = ?";

		Connection conexion = ConexionBD.getInstance().getConexion();

		try (PreparedStatement ps = conexion.prepareStatement(sql)) {

			ps.setInt(1, id);

			try (ResultSet rs = ps.executeQuery()) {

				if (rs.next()) {

					Dron drone = new Dron();

					drone.setId(rs.getInt("id"));
					drone.setSerial(rs.getString("serial"));
					drone.setModelo(rs.getString("modelo"));
					drone.setFabricante(rs.getString("fabricante"));
					drone.setPeso(rs.getDouble("peso"));
					drone.setPilotoId(rs.getInt("piloto_id"));
					drone.setsensorid(rs.getInt("sensor_id"));

					return drone;
				}
			}
		}

		return null;
	}

	/**
	 * Obtiene todos los drones registrados en la base de datos.
	 *
	 * @return lista de objetos {@link Dron}.
	 * @throws SQLException si ocurre un error de acceso a la base de datos.
	 */
	public List<Dron> listar() throws SQLException {

		List<Dron> drones = new ArrayList<>();

		String sql = "SELECT * FROM dron";

		Connection conexion = ConexionBD.getInstance().getConexion();

		try (PreparedStatement ps = conexion.prepareStatement(sql);
			 ResultSet rs = ps.executeQuery()) {

			while (rs.next()) {

				Dron drone = new Dron();

				drone.setId(rs.getInt("id"));
				drone.setSerial(rs.getString("serial"));
				drone.setModelo(rs.getString("modelo"));
				drone.setFabricante(rs.getString("fabricante"));
				drone.setPeso(rs.getDouble("peso"));
				drone.setPilotoId(rs.getInt("piloto_id"));
				drone.setsensorid(rs.getInt("sensor_id"));

				drones.add(drone);
			}
		}

		return drones;
	}

	/**
	 * Actualiza la información de un dron existente.
	 *
	 * @param drone objeto {@link Dron} con los datos actualizados.
	 * @throws SQLException si ocurre un error de acceso a la base de datos.
	 */
	public void actualizar(Dron drone) throws SQLException {

		String sql = "UPDATE dron SET serial = ?, modelo = ?, fabricante = ?, peso = ?, piloto_id = ?, sensor_id = ? "
				+ "WHERE id = ?";

		Connection conexion = ConexionBD.getInstance().getConexion();

		try (PreparedStatement ps = conexion.prepareStatement(sql)) {

			ps.setString(1, drone.getSerial());
			ps.setString(2, drone.getModelo());
			ps.setString(3, drone.getFabricante());
			ps.setDouble(4, drone.getPeso());
			ps.setInt(5, drone.getPilotoId());
			ps.setInt(6, drone.getsensorid());
			ps.setInt(7, drone.getId());

			ps.executeUpdate();
		}
	}

	/**
	 * Elimina un dron de la base de datos utilizando su identificador.
	 *
	 * @param id identificador único del dron.
	 * @throws SQLException si ocurre un error de acceso a la base de datos.
	 */
	public void eliminar(int id) throws SQLException {

		String sql = "DELETE FROM dron WHERE id = ?";

		Connection conexion = ConexionBD.getInstance().getConexion();

		try (PreparedStatement ps = conexion.prepareStatement(sql)) {

			ps.setInt(1, id);

			ps.executeUpdate();
		}
	}
}
