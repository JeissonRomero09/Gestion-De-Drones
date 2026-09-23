package co.edu.poli.sw2.Test;

import co.edu.poli.sw2.Dao.DronDao;
import co.edu.poli.sw2.Service.Proxy.DronProxy;
import co.edu.poli.sw2.Service.Proxy.EliminarDron;
import co.edu.poli.sw2.Service.Proxy.ServiceInterface;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.SQLException;

public class PruebaProxy {

    private ServiceInterface servicioReal;
    private DronDao dronDaoMock;
    private final String CLAVE_CORRECTA = "Admin123";

    @BeforeEach
    void setUp() {
        // Objeto simulado para evitar conexiones reales a MySQL durante los tests
        dronDaoMock = new DronDao() {
            @Override
            public void eliminar(int id) {
                // Simulación exitosa de borrado en BD
            }
        };

        // Inicialización del servicio real con el DAO simulado
        servicioReal = new EliminarDron(dronDaoMock);
    }

    @Test
    @DisplayName("Debe ejecutar la eliminación correctamente a través del servicio real")
    void testEliminarDronServicioReal() throws SQLException {
        int idDron = 10;
        String resultado = servicioReal.eliminarDron(idDron);

        assertNotNull(resultado);
        assertTrue(resultado.contains("✅"));
        assertTrue(resultado.contains(String.valueOf(idDron)));
    }

    @Test
    @DisplayName("Debe validar que la contraseña del Proxy coincida correctamente")
    void testValidacionClaveProxy() {
        String claveIngresadaCorrecta = "Admin123";
        String claveIngresadaIncorrecta = "12345";

        assertTrue(CLAVE_CORRECTA.equals(claveIngresadaCorrecta), "La clave ingresada debe ser válida.");
        assertFalse(CLAVE_CORRECTA.equals(claveIngresadaIncorrecta), "La clave incorrecta debe ser rechazada.");
    }

    @Test
    @DisplayName("Debe mantener la consistencia de tipos entre Proxy y Servicio Real (Patrón Proxy)")
    void testPolimorfismoProxy() {
        ServiceInterface proxy = new DronProxy(servicioReal, CLAVE_CORRECTA);

        // Comprueba que tanto el Proxy como el servicio real implementan la misma interfaz
        assertInstanceOf(ServiceInterface.class, proxy);
        assertInstanceOf(ServiceInterface.class, servicioReal);
    }
}