package co.edu.poli.sw2.Test;

import co.edu.poli.sw2.Service.Adapter.MisionAdapter;
import co.edu.poli.sw2.Service.Adapter.MisionService;
import co.edu.poli.sw2.model.Mision;

import java.util.Date;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Prueba unitaria para verificar el funcionamiento
 * del patrón Adapter aplicado a la clase Mision.
 *
 * <p>La prueba crea una misión con información precargada,
 * utiliza {@link MisionAdapter} para convertirla a formato
 * JSON y verifica que el resultado contenga la información
 * esperada.</p>
 *
 * @author
 * @version 1.0
 */
public class PruebaAdapter {

    /**
     * Verifica que el Adapter convierta correctamente
     * una instancia de {@link Mision} a un String
     * con formato JSON.
     *
     * <p>También comprueba que el JSON generado no sea
     * nulo, no esté vacío y contenga los datos principales
     * de la misión.</p>
     */
    @Test
    public void probarAdapter() {

        // Crear una misión con información precargada
        Mision mision = new Mision();

        mision.setId(1);
        mision.setNombre("Mision de reconocimiento");
        mision.setUbicacion("Bogota");
        mision.setFecha(new Date());

        // Crear el servicio
        MisionService misionService = new MisionService();

        // Crear el Adapter
        MisionAdapter adapter = new MisionAdapter(misionService);

        // Adaptar la misión a formato JSON
        String json = adapter.convertir(mision);

        // Verificar que el JSON no sea nulo
        assertNotNull(json);

        // Verificar que el JSON no esté vacío
        assertFalse(json.isEmpty());

        // Verificar los datos de la misión
        assertTrue(json.contains("\"id\":1"));
        assertTrue(json.contains("\"Nombre\":\"Mision de reconocimiento\""));
        assertTrue(json.contains("\"Ubicacion\":\"Bogota\""));
        assertTrue(json.contains("\"Fecha\":\""));

        // Verificar que el atributo dron exista en el JSON
        assertTrue(json.contains("\"dron\":\""));

        // Mostrar el resultado en la consola de JUnit
        System.out.println("===== PRUEBA ADAPTER =====");
        System.out.println("Mision adaptada correctamente.");
        System.out.println("JSON generado:");
        System.out.println(json);
        System.out.println("==========================");
    }
}