package co.edu.poli.sw2.Service.Bridge;

/**
 * Define el contrato de control que debe implementar cualquier estrategia del dron.
 *
 * <p>Forma parte del patrón estructural Bridge, donde la abstracción del dron
 * delega el comportamiento operativo a una implementación concreta de control.</p>
 *
 * @author Jeisson Romero
 * @version 1.0
 */
public interface ControlDron {
    
    /**
     * Ejecuta la acción de control del dron según la implementación concreta.
     *
     * @param id identificador del dron que será controlado.
     * @return texto descriptivo del comportamiento de control.
     */
    String controlar(int id);

}