package co.edu.poli.sw2.Service.Adapter;

/**
 * Servicio encargado de procesar la información JSON
 * generada por el patrón Adapter.
 *
 * <p>Actualmente, el servicio recibe el contenido JSON
 * y lo retorna como un {@link String}. Esto permite
 * demostrar la funcionalidad del Adapter sin necesidad
 * de crear todavía un archivo físico.</p>
 *
 * @author
 * @version 1.0
 */
public class MisionService {

    /**
     * Recibe y retorna la información de una misión
     * en formato JSON.
     *
     * @param json información de la misión en formato JSON
     * @return el mismo String recibido
     */
    public String guardar(String json) {

        return json;

    }
}