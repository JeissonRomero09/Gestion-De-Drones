package co.edu.poli.sw2.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.sql.Connection;
import java.sql.SQLException;

import org.junit.jupiter.api.Test;

import co.edu.poli.sw2.Dao.ConexionBD;

/**
 * Prueba de integración para verificar la conexión entre la aplicación y la
 * base de datos.
 *
 * <p>
 * Esta clase comprueba que el método {@link ConexionBD#getInstance()} pueda
 * establecer correctamente una conexión con la base de datos.
 * </p>
 *
 * @author Jeison Romero
 * @version 1.0
 */
class DronDAOIntegrationTest {

	/**
	 * Verifica que la conexión con la base de datos se establezca correctamente.
	 *
	 * <p>
	 * La prueba falla si el método de conexión retorna {@code null}.
	 * </p>
	 * @throws SQLException 
	 */
	@Test
	void probarConexionBD() throws SQLException {

		Connection conexion = ConexionBD.getInstance().getConexion();

		assertNotNull(conexion);
	}
}
