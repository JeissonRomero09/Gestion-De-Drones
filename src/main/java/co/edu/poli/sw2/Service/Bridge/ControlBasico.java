package co.edu.poli.sw2.Service.Bridge;

/**
 * Implementa el control básico de un dron.
 *
 * <p>
 * Esta clase representa una implementación concreta del patrón
 * estructural Bridge y proporciona el comportamiento correspondiente
 * al control básico.
 * </p>
 *
 * @author Jeison Romero
 * @version 1.0
 */
public class ControlBasico implements ControlDron {

    /**
     * Ejecuta el control básico del dron.
     *
     * @return mensaje que indica que se creó un dron con control básico.
     */
    @Override
    public String controlar(int id) {
    	 return "El dron " + id + " es básico";
    }
}