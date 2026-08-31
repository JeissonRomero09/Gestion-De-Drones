package co.edu.poli.sw2.model;

/**
 * Representa un dron especializado para actividades de vigilancia.
 *
 * <p>
 * Esta clase extiende la clase {@link Dron} e implementa el patrón Prototype
 * como SubclassPrototype, permitiendo clonar sus atributos heredados y propios.
 * </p>
 *
 * @author Jeisson Romero
 * @version 3.0
 */
public class Vigilancia extends Dron {

    /**
     * Indica si el dron cuenta con capacidad de detección térmica.
     */
    private boolean deteccionTermica;

    /**
     * Constructor por defecto de la clase Vigilancia.
     */
    public Vigilancia() {
        super();
    }

    /**
     * Constructor de la clase Vigilancia.
     *
     * @param id identificador único del dron
     * @param serial número serial del dron
     * @param modelo modelo del dron
     * @param fabricante fabricante del dron
     * @param peso peso del dron
     * @param deteccionTermica indica si el dron cuenta con detección térmica
     */
    public Vigilancia(int id, String serial, String modelo, String fabricante,
                      int peso, boolean deteccionTermica) {
        super(id, serial, modelo, fabricante, peso);
        this.deteccionTermica = deteccionTermica;
    }

    /**
     * Constructor de copia de la subclase (Alineado con SubclassPrototype).
     *
     * @param prototype Instancia previa a clonar (super(prototype) y copia de field2).
     */
    public Vigilancia(Vigilancia prototype) {
        super(prototype); // super(prototype)
        if (prototype != null) {
            this.deteccionTermica = prototype.deteccionTermica; // this.field2 = prototype.field2
        }
    }

    /**
     * Clona el objeto actual retornando una nueva instancia especializada.
     *
     * @return Una copia de tipo {@link Prototype}.
     */
    @Override
    public Prototype clone() {
        return new Vigilancia(this); // return new SubclassPrototype(this)
    }

    /**
     * Verifica si el dron cuenta con detección térmica.
     *
     * @return {@code true} si cuenta con detección térmica;
     *         {@code false} en caso contrario
     */
    public boolean isDeteccionTermica() {
        return deteccionTermica;
    }

    /**
     * Establece si el dron cuenta con detección térmica.
     *
     * @param deteccionTermica nuevo estado de la detección térmica
     */
    public void setDeteccionTermica(boolean deteccionTermica) {
        this.deteccionTermica = deteccionTermica;
    }
}