package co.edu.poli.sw2.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.sql.Connection;

import org.junit.jupiter.api.Test;

import co.edu.poli.sw2.Dao.ConexionBD;

class DronDAOIntegrationTest {

    @Test
    void probarConexionBD() {

        Connection conexion = ConexionBD.conectar();

        assertNotNull(conexion);
    }
}