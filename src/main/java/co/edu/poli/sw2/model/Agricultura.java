package co.edu.poli.sw2.model;

/**
 * Representa un dron especializado para actividades de agricultura.
 *
 * <p>
 * Esta clase extiende la clase {@link Dron} y agrega información específica
 * relacionada con la capacidad del tanque utilizado en labores agrícolas.
 * </p>
 *
 * @author Jeisson Romero
 * @version 1.0
 */
public class Agricultura extends Dron {

    /**
     * Capacidad del tanque del dron para almacenar sustancias utilizadas
     * en actividades agrícolas.
     */
    private double capacidadTanque;

    /**
     * Constructor por defecto de la clase Agricultura.
     */
    public Agricultura() {
        super();
    }

    /**
     * Constructor de la clase Agricultura.
     *
     * @param id identificador único del dron
     * @param serial número serial del dron
     * @param modelo modelo del dron
     * @param fabricante fabricante del dron
     * @param peso peso del dron
     * @param capacidadTanque capacidad del tanque del dron
     */
    public Agricultura(int id, String serial, String modelo, String fabricante,
                       int peso, double capacidadTanque) {
        super(id, serial, modelo, fabricante, peso);
        this.capacidadTanque = capacidadTanque;
    }

    /**
     * Obtiene la capacidad del tanque del dron.
     *
     * @return capacidad del tanque
     */
    public double getCapacidadTanque() {
        return capacidadTanque;
    }

    /**
     * Establece la capacidad del tanque del dron.
     *
     * @param capacidadTanque nueva capacidad del tanque
     */
    public void setCapacidadTanque(double capacidadTanque) {
        this.capacidadTanque = capacidadTanque;
    }
}