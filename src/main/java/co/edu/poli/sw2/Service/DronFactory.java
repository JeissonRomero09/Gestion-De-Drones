package co.edu.poli.sw2.Service;

import co.edu.poli.sw2.model.Dron;

/**
 * Interfaz que define la fábrica abstracta para la creación de objetos de tipo {@link Dron}.
 * Forma parte de la implementación del patrón de diseño Factory Method.
 * 
 * @author Jeisson
 * @author Camilo
 * @version 1.0
 */
public interface DronFactory {

    /**
     * Crea y retorna una nueva instancia concreta de la clase {@link Dron}.
     * 
     * @return Objeto instanciado de tipo {@link Dron}.
     */
    Dron crearDron();

}
