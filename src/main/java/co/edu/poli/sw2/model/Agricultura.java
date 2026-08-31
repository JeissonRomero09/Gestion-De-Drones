package co.edu.poli.sw2.model;

/**
 * Representa un dron especializado para actividades de agricultura.
 *
 * <p>
 * Esta clase extiende la clase {@link Dron} e implementa el patrón Prototype
 * como SubclassPrototype, permitiendo clonar sus atributos heredados y propios.
 * </p>
 *
 * @author Jeisson Romero
 * @version 3.0
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
     * Constructor de copia de la subclase (Alineado con SubclassPrototype).
     *
     * @param prototype Instancia previa a clonar (super(prototype) y copia de field2).
     */
    public Agricultura(Agricultura prototype) {
        super(prototype); // super(prototype)
        if (prototype != null) {
            this.capacidadTanque = prototype.capacidadTanque; // this.field2 = prototype.field2
        }
    }

    /**
     * Clona el objeto actual retornando una nueva instancia especializada.
     *
     * @return Una copia de tipo {@link Prototype}.
     */
    @Override
    public Prototype clone() {
        return new Agricultura(this); // return new SubclassPrototype(this)
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