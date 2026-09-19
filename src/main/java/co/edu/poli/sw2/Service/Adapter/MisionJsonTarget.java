package co.edu.poli.sw2.Service.Adapter;

import co.edu.poli.sw2.model.Mision;

/**
 * Interfaz objetivo del patrón Adapter.
 *
 * <p>Define la operación que permite convertir una instancia
 * de {@link Mision} a una representación en formato JSON.</p>
 *
 * @author
 * @version 1.0
 */
public interface MisionJsonTarget {

    /**
     * Convierte una misión a una representación en formato JSON.
     *
     * @param mision misión que será convertida
     * @return String que contiene la información de la misión
     *         en formato JSON
     */
    String convertir(Mision mision);
}