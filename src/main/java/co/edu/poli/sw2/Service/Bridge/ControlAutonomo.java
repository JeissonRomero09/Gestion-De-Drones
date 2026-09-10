package co.edu.poli.sw2.Service.Bridge;

/**
 * Implementa el control autónomo de un dron.
 *
 * <p>
 * Esta clase representa una implementación concreta del patrón
 * estructural Bridge y proporciona el comportamiento correspondiente
 * al control autónomo.
 * </p>
 *
 * @author Jeison Romero
 * @version 1.0
 */
public class ControlAutonomo implements ControlDron {

    /**
     * Ejecuta el control autónomo del dron.
     *
     * @return mensaje que indica que se creó un dron con control autónomo.
     */
    @Override
    public String controlar(int id) {
        return "El dron " + id + " es automático";

    }
}