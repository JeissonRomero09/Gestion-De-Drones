package co.edu.poli.sw2.Service.Builder;

import co.edu.poli.sw2.model.Dron;

/**
 * Define el contrato base para los builders que crean instancias de {@link Dron}.
 *
 * <p>Este patrón permite construir objetos complejos con pasos secuenciales,
 * dejando que cada implementación concreta defina la lógica específica de cada atributo.</p>
 *
 * @author Jeisson Romero
 * @version 1.0
 */
public interface DronBuilder {
    /**
     * Reinicia el estado del constructor para preparar una nueva construcción.
     */
    void reset();

    /**
     * Configura los datos básicos del dron.
     *
     * @param id identificador del dron.
     * @param serial número de serie del dron.
     * @param modelo modelo del dron.
     * @param fabricante fabricante del dron.
     * @param peso peso del dron.
     */
    void buildDatosBasicos(int id, String serial, String modelo, String fabricante, int peso);

    /**
     * Asigna el atributo específico de la variante del dron.
     */
    void buildAtributoEspecializado();
}