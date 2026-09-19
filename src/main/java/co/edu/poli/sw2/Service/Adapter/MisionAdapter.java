package co.edu.poli.sw2.Service.Adapter;

import co.edu.poli.sw2.model.Mision;

/**
 * Implementación del patrón Adapter para convertir una instancia
 * de {@link Mision} a una representación en formato JSON.
 *
 * <p>Esta clase adapta la información de una misión para que pueda
 * ser utilizada como un String con estructura JSON. Además, utiliza
 * {@link MisionService} para procesar el resultado generado.</p>
 *
 * @author
 * @version 1.0
 */
public class MisionAdapter implements MisionJsonTarget {

    /**
     * Servicio encargado de procesar la información JSON generada
     * por el Adapter.
     */
    private MisionService misionService;

    /**
     * Construye una instancia de {@code MisionAdapter}.
     *
     * @param misionService servicio utilizado para procesar
     *                      el JSON generado
     */
    public MisionAdapter(MisionService misionService) {
        this.misionService = misionService;
    }

    /**
     * Convierte una instancia de {@link Mision} en un String
     * con formato JSON.
     *
     * <p>La información convertida incluye el identificador,
     * nombre, ubicación, fecha y dron asociado a la misión.</p>
     *
     * @param mision misión que será convertida a formato JSON
     * @return String que contiene la información de la misión
     *         en formato JSON
     */
    @Override
    public String convertir(Mision mision) {

        String json = "{"
                + "\"id\":" + mision.getId() + ","
                + "\"Nombre\":\"" + mision.getNombre() + "\","
                + "\"Ubicacion\":\"" + mision.getUbicacion() + "\","
                + "\"Fecha\":\"" + mision.getFecha() + "\","
                + "\"dron\":\"" + mision.getDron() + "\""
                + "}";

        misionService.guardar(json);

        return json;
    }
}