package co.edu.poli.sw2.Service.Decorator;
/**
 * Componente abstracto base para la estructura del patrón Decorator.
 * Define la interfaz común para los componentes de dron y sus decoradores.
 * 
 * @author Cristian Vera
 * @version 1.0
 */
public abstract class DronComponent {

    /**
     * Obtiene la descripción textual del componente o la combinación de componentes del dron.
     * 
     * @return {@link String} con la descripción detallada.
     */
    public abstract String descripcion();
}