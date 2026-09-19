package co.edu.poli.sw2.model;

/**
 * Representa un dron especializado en actividades de agricultura.
 *
 * <p>
 * Esta clase hereda los atributos generales de {@link Dron} y agrega
 * las características específicas de un dron agrícola.
 * </p>
 *
 * @author Jeisson Romero
 * @version 4.0
 */
public class Agricultura extends Dron {

    /**
     * Capacidad del tanque del dron para almacenar sustancias
     * utilizadas en actividades agrícolas.
     */
    private double capacidadTanque;

    /**
     * Constructor por defecto.
     */
    public Agricultura() {
        super();
    }

    /**
     * Constructor de Agricultura.
     *
     * @param id identificador único del dron
     * @param serial número serial del dron
     * @param modelo modelo del dron
     * @param fabricante fabricante del dron
     * @param peso peso del dron
     * @param capacidadTanque capacidad del tanque del dron
     */
    public Agricultura(int id, String serial, String modelo,
                       String fabricante, int peso,
                       double capacidadTanque) {

        super(id, serial, modelo, fabricante, peso);
        this.capacidadTanque = capacidadTanque;
    }

    /**
     * Constructor de copia.
     *
     * @param prototype instancia de Agricultura que se utilizará
     *                  como prototipo
     */
    public Agricultura(Agricultura prototype) {
        super(prototype);

        if (prototype != null) {
            this.capacidadTanque = prototype.capacidadTanque;
        }
    }

    /**
     * Crea una copia de la instancia actual.
     *
     * @return una nueva instancia de Agricultura
     */
    @Override
    public Agricultura clone() {
        return new Agricultura(this);
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
     * Modifica la capacidad del tanque del dron.
     *
     * @param capacidadTanque nueva capacidad del tanque
     */
    public void setCapacidadTanque(double capacidadTanque) {
        this.capacidadTanque = capacidadTanque;
    }

    /**
     * Devuelve una representación textual del objeto Agricultura.
     *
     * @return cadena con los datos del dron agrícola
     */
    @Override
    public String toString() {
        return "Agricultura{" +
                "id=" + getId() +
                ", serial='" + getSerial() + '\'' +
                ", modelo='" + getModelo() + '\'' +
                ", fabricante='" + getFabricante() + '\'' +
                ", peso=" + getPeso() +
                ", capacidadTanque=" + capacidadTanque +
                '}';
    }
}