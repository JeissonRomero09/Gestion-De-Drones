package co.edu.poli.sw2.Dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import io.github.cdimascio.dotenv.Dotenv;

/**
 * Clase Singleton encargada de gestionar la conexión con la base de datos.
 *
 * <p>
 * La información de conexión se obtiene desde las variables de entorno
 * definidas en el archivo {@code .env}.
 * </p>
 *
 * @author Jeison Romero, Cristian vera
 * @version 1.0
 */
public class ConexionBD {

    /**
     * Instancia única de la clase.
     */
    private static ConexionBD instancia;

    /**
     * Conexión única a la base de datos.
     */
    private Connection conexion;

    /**
     * Objeto utilizado para cargar las variables de entorno.
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
     * Constructor privado para evitar que otras clases
     * creen instancias directamente.
     */
    private ConexionBD() throws SQLException {

        conectar();

    }

    /**
     * Obtiene la única instancia de la clase ConexionBD.
     *
     * @return instancia única de ConexionBD.
     * @throws SQLException si ocurre un error al establecer la conexión.
     */
    public static ConexionBD getInstance() throws SQLException {

        if (instancia == null) {

            instancia = new ConexionBD();

        }

        return instancia;
    }

    /**
     * Establece la conexión con la base de datos.
     *
     * @throws SQLException si ocurre un error al establecer la conexión.
     */
    private void conectar() throws SQLException {

        conexion = DriverManager.getConnection(
                URL,
                USUARIO,
                PASSWORD
        );

    }

    /**
     * Obtiene la conexión actual.
     *
     * @return objeto Connection.
     */
    public Connection getConexion() {

        return conexion;

    }

}
