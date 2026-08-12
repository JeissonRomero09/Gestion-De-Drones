package co.edu.poli.sw2.Dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import io.github.cdimascio.dotenv.Dotenv;

/**
 * Clase encargada de gestionar la conexión con la base de datos.
 *
 * <p>
 * La información de conexión se obtiene desde las variables de entorno
 * definidas en el archivo {@code .env}.
 * </p>
 *
 * @author Jeison Romero
 * @version 1.0
 */
public class ConexionBD {

	/**
	 * Objeto utilizado para cargar las variables de entorno desde el archivo
	 * {@code .env}.
	 */
	private static final Dotenv dotenv = Dotenv.load();

	/**
	 * URL de conexión a la base de datos.
	 */
	private static final String URL = dotenv.get("DB_URL");

	/**
	 * Usuario utilizado para conectarse a la base de datos.
	 */
	private static final String USUARIO = dotenv.get("DB_USER");

	/**
	 * Contraseña utilizada para conectarse a la base de datos.
	 */
	private static final String PASSWORD = dotenv.get("DB_PASSWORD");

	/**
	 * Establece una conexión con la base de datos utilizando las credenciales
	 * configuradas en el archivo {@code .env}.
	 *
	 * <p>
	 * Si la conexión se realiza correctamente, retorna un objeto
	 * {@link Connection}. En caso de producirse un error de SQL, se muestra el
	 * mensaje correspondiente y se retorna {@code null}.
	 * </p>
	 *
	 * @return objeto {@link Connection} si la conexión es exitosa; {@code null} si
	 *         ocurre un error durante la conexión.
	 */
	public static Connection conectar() {

		try {

			Connection conexion = DriverManager.getConnection(URL, USUARIO, PASSWORD);

			System.out.println("Conexion exitosa a la base de datos");

			return conexion;

		} catch (SQLException e) {

			System.out.println("Error de conexion: " + e.getMessage());

			return null;
		}
	}
}