package co.edu.poli.sw2.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.sql.Connection;
import java.sql.SQLException;

import org.junit.jupiter.api.Test;

import co.edu.poli.sw2.Service.Singleton;

/**
 * Prueba de integración para verificar la conexión entre la aplicación y la
 * base de datos.
 *
 * @author Jeison Romero
 * @version 1.0
 */
class DronDAOIntegrationTest {

	/**
	 * Verifica que la conexión con la base de datos se establezca correctamente.
	 *
	 * @throws SQLException si ocurre un error durante la conexión.
	 */
	@Test
	void probarConexionBD() throws SQLException {

		Connection conexion = Singleton.getInstance().getConexion();

		assertNotNull(conexion);
	}
}