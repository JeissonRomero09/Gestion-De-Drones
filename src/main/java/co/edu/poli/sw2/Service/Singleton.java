package co.edu.poli.sw2.Service;

import java.sql.Connection;
import java.sql.SQLException;

import co.edu.poli.sw2.Dao.ConexionBD;

/**
 * Clase Singleton encargada de gestionar de manera centralizada
 * la conexión con la base de datos.
 *
 * <p>
 * Esta clase utiliza {@link ConexionBD} para establecer la conexión
 * con la base de datos y garantiza una única instancia de Singleton.
 * </p>
 *
 * @author Jeison Romero
 * @version 1.0
 */
public class Singleton {

	/**
	 * Única instancia de la clase Singleton.
	 */
	private static Singleton singleton;

	/**
	 * Conexión activa con la base de datos.
	 */
	private Connection conexion;

	/**
	 * Constructor privado para evitar la creación de múltiples
	 * instancias desde otras clases.
	 *
	 * @throws SQLException si ocurre un error al establecer la conexión.
	 */
	private Singleton() throws SQLException {
		this.conexion = ConexionBD.conectar();
	}

	/**
	 * Obtiene la única instancia de la clase Singleton.
	 *
	 * <p>
	 * Si la instancia aún no existe, se crea. Si ya existe,
	 * se devuelve la misma instancia.
	 * </p>
	 *
	 * @return instancia única de Singleton.
	 * @throws SQLException si ocurre un error al establecer la conexión.
	 */
	public static Singleton getInstance() throws SQLException {

		if (singleton == null) {
			singleton = new Singleton();
		}

		return singleton;
	}

	/**
	 * Obtiene la conexión actual con la base de datos.
	 *
	 * <p>
	 * Si la conexión es nula o se encuentra cerrada, se establece
	 * una nueva conexión mediante {@link ConexionBD}.
	 * </p>
	 *
	 * @return conexión activa con la base de datos.
	 * @throws SQLException si ocurre un error al establecer la conexión.
	 */
	public Connection getConexion() throws SQLException {

		if (conexion == null || conexion.isClosed()) {
			conexion = ConexionBD.conectar();
		}

		return conexion;
	}

	/**
	 * Cierra la conexión con la base de datos.
	 *
	 * @throws SQLException si ocurre un error al cerrar la conexión.
	 */
	public void cerrarConexion() throws SQLException {

		if (conexion != null && !conexion.isClosed()) {
			conexion.close();
		}
	}
} 