
package co.edu.poli.sw2.Test;
 
import static org.junit.jupiter.api.Assertions.*;

import java.util.Date;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import co.edu.poli.sw2.Service.Adapter.MisionAdapter;
import co.edu.poli.sw2.Service.Decorator.Bateria;
import co.edu.poli.sw2.Service.Decorator.DronComponent;
import co.edu.poli.sw2.Service.Decorator.DroneWrapper;
import co.edu.poli.sw2.Service.Facade.DronFacade;
import co.edu.poli.sw2.Service.Protorype.DronPrototype;
import co.edu.poli.sw2.Service.Protorype.DronPrototypeImpl;
import co.edu.poli.sw2.Service.Adapter.MisionService;
import co.edu.poli.sw2.model.Dron;
import co.edu.poli.sw2.model.Mision;

public class DronFacadeTest {

    private DronPrototype prototypeService;
    private DronFacade facade;

    @BeforeEach
    void setUp() {
        prototypeService = new DronPrototypeImpl();
        facade = new DronFacade(prototypeService);
    }

   


    // =========================================================
    // TEST ADAPTER - JSON
    // =========================================================

    @Test
    void probarAdapterJson() {

        Mision mision = new Mision();

        mision.setId(1);
        mision.setNombre("Mision de reconocimiento");
        mision.setUbicacion("Bogota");
        mision.setFecha(new Date());

        MisionService misionService =
                new MisionService();

        MisionAdapter adapter =
                new MisionAdapter(misionService);

        String json =
                adapter.convertir(mision);

        assertNotNull(json);

        assertTrue(
                json.contains("\"id\":1")
        );

        assertTrue(
                json.contains(
                        "\"Nombre\":\"Mision de reconocimiento\""
                )
        );

        assertTrue(
                json.contains(
                        "\"Ubicacion\":\"Bogota\""
                )
        );

        assertTrue(
                json.contains("\"Fecha\":\"")
        );

        assertTrue(
                json.contains("\"dron\":\"")
        );
    }

    // =========================================================
    // TEST PROTOTYPE
    // =========================================================

    @Test
    void probarPrototype() {

        Dron original = new Dron();

        original.setId(1);
        original.setSerial("DRON-001");
        original.setModelo("Mavic 3");
        original.setFabricante("DJI");
        original.setPeso(900);

        prototypeService.registrarPrototipo(
                "DRON-001",
                original
        );

        Dron prototipo =
                prototypeService.obtenerPrototipoBase(
                        "DRON-001"
                );

        assertNotNull(prototipo);

        Dron clon =
                prototypeService.obtenerClon(
                        "DRON-001"
                );

        assertNotNull(clon);

        assertNotSame(
                prototipo,
                clon
        );

        assertEquals(
                prototipo.getSerial(),
                clon.getSerial()
        );
    }

    // =========================================================
    // TEST DECORATOR
    // =========================================================

    @Test
    void probarDecorator() {

        DronComponent componente =
                new Bateria(5000);

        String resultado =
                componente.descripcion();

        assertNotNull(resultado);

        assertTrue(
                resultado.contains("5000")
        );
    }

    // =========================================================
    // TEST DECORATOR + WRAPPER
    // =========================================================

    @Test
    void probarDecoratorConWrapper() {

        DronComponent componente =
                new Bateria(5000);

        DronComponent decorado =
                new DroneWrapper(componente);

        String resultado =
                decorado.descripcion();

        assertNotNull(resultado);

        assertTrue(
                resultado.contains("5000")
        );
    }

    // =========================================================
    // TEST FACADE
    // =========================================================

    @Test
    void probarFacade() {

        // -------------------------
        // ADAPTER
        // -------------------------

        Mision mision = new Mision();

        mision.setId(1);
        mision.setNombre(
                "Mision de reconocimiento"
        );
        mision.setUbicacion("Bogota");
        mision.setFecha(new Date());

        MisionService misionService =
                new MisionService();

        MisionAdapter adapter =
                new MisionAdapter(misionService);

        // -------------------------
        // PROTOTYPE
        // -------------------------

        Dron original = new Dron();

        original.setId(1);
        original.setSerial("DRON-001");
        original.setModelo("Mavic 3");
        original.setFabricante("DJI");
        original.setPeso(900);

        // -------------------------
        // DECORATOR
        // -------------------------

        DronComponent componente =
                new Bateria(5000);

        // -------------------------
        // FACADE
        // -------------------------

        String resultado =
                facade.ejecutarPatrones(
                        adapter,
                        mision,
                        original,
                        componente
                );

        assertNotNull(resultado);

        // Adapter
        assertTrue(
                resultado.contains("[ADAPTER]")
        );

        assertTrue(
                resultado.contains(
                        "Mision de reconocimiento"
                )
        );

        assertTrue(
                resultado.contains("Bogota")
        );

        // Prototype
        assertTrue(
                resultado.contains("[PROTOTYPE]")
        );

        assertTrue(
                resultado.contains(
                        "¿Son el mismo objeto?: false"
                )
        );

        // Decorator
        assertTrue(
                resultado.contains("[DECORATOR]")
        );

        assertTrue(
                resultado.contains("5000")
        );
    }
}

