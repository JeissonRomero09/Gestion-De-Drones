package co.edu.poli.sw2.Service.Composite;

/**
 * Interfaz base del patrón de diseño Composite para la gestión de sensores.
 * <p>
 * Define las operaciones comunes para la representación en la interfaz JavaFX
 * sin realizar impresiones directas en la consola.
 * </p>
 * 
 * @author Cristian Vera
 * @version 1.0
 */
public interface SensoresComponent {

    /**
     * Devuelve una cadena formateada con el resultado de la lectura o ejecución
     * del sensor o grupo de sensores para ser mostrada en la interfaz JavaFX.
     * 
     * @return Texto estructurado con el estado o lectura del componente.
     */
    String execute();

    /**
     * Obtiene el nombre o descripción representativa del componente.
     * 
     * @return Cadena con el nombre o descripción del componente.
     */
    String getNombre();
}