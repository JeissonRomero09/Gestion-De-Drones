package co.edu.poli.sw2.Service;

import java.util.HashMap;
import java.util.Map;
import co.edu.poli.sw2.model.Dron;
import co.edu.poli.sw2.model.Prototype;

/**
 * Implementación concreta del servicio {@link DronPrototype}.
 *
 * @author Jeisson Romero
 * @version 1.0
 */
public class DronPrototypeImpl implements DronPrototype {

    private final Map<String, Dron> mapaPrototipos = new HashMap<>();

    @Override
    public Dron clonarDron(Dron dronOriginal) {
        if (dronOriginal == null) {
            return null;
        }
        return (Dron) dronOriginal.clone();
    }

    @Override
    public void registrarPrototipo(String clave, Dron dron) {
        if (clave != null && dron != null) {
            mapaPrototipos.put(clave.toUpperCase(), dron);
        }
    }
    
    @Override
    public Dron obtenerPrototipoBase(String clave) {
        if (clave == null) {
            return null;
        }
        return mapaPrototipos.get(clave.toUpperCase());
    }

    @Override
    public Dron obtenerClon(String clave) {
        if (clave == null) {
            return null;
        }

        Dron prototipoBase = mapaPrototipos.get(clave.toUpperCase());
        if (prototipoBase != null) {
            return clonarDron(prototipoBase);
        }
        return null;
    }
}