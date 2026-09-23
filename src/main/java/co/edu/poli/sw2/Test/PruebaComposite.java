package co.edu.poli.sw2.Test;

import co.edu.poli.sw2.Service.Composite.SensoresComposite;
import co.edu.poli.sw2.Service.Composite.SensoresWrapper;
import co.edu.poli.sw2.model.Sensores;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PruebaComposite {

    private SensoresComposite grupoPrincipal;
    private SensoresComposite subGrupo;
    private SensoresWrapper sensorWrapper1;
    private SensoresWrapper sensorWrapper2;

    @BeforeEach
    void setUp() {
        // Inicialización del grupo principal
        grupoPrincipal = new SensoresComposite("Zona Norte");

        // Creación de modelos Sensores y sus wrappers
        Sensores sensor1 = new Sensores();
        sensor1.setTipo("Temperatura");
        sensor1.setFabricante("Bosch");
        sensorWrapper1 = new SensoresWrapper(sensor1);

        Sensores sensor2 = new Sensores();
        sensor2.setTipo("Humedad");
        sensor2.setFabricante("Siemens");
        sensorWrapper2 = new SensoresWrapper(sensor2);

        // Subgrupo para probar anidación
        subGrupo = new SensoresComposite("Invernadero 1");
    }

    @Test
    @DisplayName("Debe agregar y eliminar componentes correctamente")
    void testAgregarYEliminarComponentes() {
        grupoPrincipal.add(sensorWrapper1);
        assertEquals(1, grupoPrincipal.getChildren().size());

        grupoPrincipal.remove(sensorWrapper1);
        assertEquals(0, grupoPrincipal.getChildren().size());
    }

    @Test
    @DisplayName("Debe formatear correctamente la lectura de un grupo con un sensor")
    void testExecuteGrupoConSensor() {
        grupoPrincipal.add(sensorWrapper1);
        String resultado = grupoPrincipal.execute();

        assertTrue(resultado.contains("Zona Norte"));
        assertTrue(resultado.contains("Lectura OK -> Sensor: Temperatura | Fabricante: Bosch"));
    }

    @Test
    @DisplayName("Debe manejar correctamente componentes anidados (Composite recursivo)")
    void testExecuteCompositeAnidado() {
        subGrupo.add(sensorWrapper2);
        grupoPrincipal.add(sensorWrapper1);
        grupoPrincipal.add(subGrupo);

        String resultado = grupoPrincipal.execute();

        assertTrue(resultado.contains("Zona Norte"));
        assertTrue(resultado.contains("Invernadero 1"));
        assertTrue(resultado.contains("Humedad"));
        assertEquals(2, grupoPrincipal.getChildren().size());
    }

    @Test
    @DisplayName("Debe manejar el caso de un SensoresWrapper sin datos")
    void testExecuteSensorNulo() {
        SensoresWrapper wrapperVacio = new SensoresWrapper();
        String resultado = wrapperVacio.execute();

        assertEquals("Lectura Fallida -> Sensor sin datos asignados.", resultado);
        assertEquals("Sensor Indefinido", wrapperVacio.getNombre());
    }
}