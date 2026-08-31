package co.edu.poli.sw2.Service;

import co.edu.poli.sw2.model.Dron;

/**
 * Servicio encargado de gestionar los prototipos de drones y coordinar
 * las operaciones de clonación en la capa de negocio.
 *
 * @author Jeisson Romero
 * @version 2.0
 */
public interface DronPrototype {
	
	/**
     * Obtiene el prototipo base registrado sin clonarlo (permite ver su dirección de memoria original).
     *
     * @param clave Identificador del prototipo.
     * @return El objeto dron original almacenado en el mapa.
     */
    Dron obtenerPrototipoBase(String clave);

    /**
     * Clona un dron existente utilizando su método de clonación.
     *
     * @param dronOriginal Instancia base a duplicar.
     * @return Una nueva instancia clonada en una posición de memoria distinta.
     */
    Dron clonarDron(Dron dronOriginal);

    /**
     * Registra un prototipo base asociado a una clave (ej: "AGRICOLA", "VIGILANCIA").
     *
     * @param clave Identificador del prototipo.
     * @param dron  Objeto dron base.
     */
    void registrarPrototipo(String clave, Dron dron);

    /**
     * Obtiene una copia clonada de un prototipo previamente registrado.
     *
     * @param clave Identificador del prototipo a clonar.
     * @return Copia del dron prototipo.
     */
    Dron obtenerClon(String clave);	
}