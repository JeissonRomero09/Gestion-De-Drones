
package co.edu.poli.sw2.Service;

import java.util.HashMap;
import java.util.Map;

import co.edu.poli.sw2.model.Dron;

/**
 * Implementación concreta del servicio {@link DronPrototype}.
 *
 * <p>
 * Esta clase administra un conjunto de prototipos de drones y permite
 * obtener clones de los prototipos registrados.
 * </p>
 *
 * @author Jeisson Romero
 * @version 3.0
 */
public class DronPrototypeImpl implements DronPrototype {

    /**
     * Mapa que almacena los prototipos de drones registrados.
     */
    private final Map<String, Dron> mapaPrototipos = new HashMap<>();

    /**
     * Clona un dron utilizando el método clone() definido en la clase Dron.
     *
     * @param dronOriginal dron que se desea clonar
     * @return una copia del dron original o null si el dron es null
     */
    @Override
    public Dron clonarDron(Dron dronOriginal) {

        if (dronOriginal == null) {
            return null;
        }

        return dronOriginal.clone();
    }

    /**
     * Registra un dron como prototipo utilizando una clave.
     *
     * @param clave clave con la que se almacenará el prototipo
     * @param dron dron que se registrará como prototipo
     */
    @Override
    public void registrarPrototipo(String clave, Dron dron) {

        if (clave != null && dron != null) {
            mapaPrototipos.put(clave.toUpperCase(), dron);
        }
    }

    /**
     * Obtiene el prototipo base registrado con una determinada clave.
     *
     * @param clave clave del prototipo
     * @return el dron prototipo registrado o null si no existe
     */
    @Override
    public Dron obtenerPrototipoBase(String clave) {

        if (clave == null) {
            return null;
        }

        return mapaPrototipos.get(clave.toUpperCase());
    }

    /**
     * Obtiene un clon del prototipo registrado con una determinada clave.
     *
     * @param clave clave del prototipo
     * @return una copia del prototipo o null si no existe
     */
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

