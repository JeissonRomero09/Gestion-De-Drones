
package co.edu.poli.sw2.model;

/**
 * Representa un dron especializado para actividades de vigilancia.
 *
 * <p>
 * Esta clase extiende la clase {@link Dron} e implementa el patrón Prototype
 * mediante un constructor de copia y el método {@code clone()}.
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
     * Constructor de copia de la clase Vigilancia.
     *
     * @param prototype instancia de Vigilancia que se utilizará como prototipo
     */
    public Vigilancia(Vigilancia prototype) {

        super(prototype);

        if (prototype != null) {
            this.deteccionTermica = prototype.deteccionTermica;
        }
    }

    /**
     * Crea una copia de la instancia actual.
     *
     * @return una nueva instancia de Vigilancia con los mismos atributos
     */
    @Override
    public Vigilancia clone() {

        return new Vigilancia(this);
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
     * Modifica el estado de la detección térmica.
     *
     * @param deteccionTermica nuevo estado de la detección térmica
     */
    public void setDeteccionTermica(boolean deteccionTermica) {

        this.deteccionTermica = deteccionTermica;
    }

    /**
     * Devuelve una representación textual del objeto Vigilancia.
     *
     * @return cadena de texto con los datos del dron de vigilancia
     */
    @Override
    public String toString() {

        return "Vigilancia{" +
                "id=" + getId() +
                ", serial='" + getSerial() + '\'' +
                ", modelo='" + getModelo() + '\'' +
                ", fabricante='" + getFabricante() + '\'' +
                ", peso=" + getPeso() +
                ", deteccionTermica=" + deteccionTermica +
                '}';
    }
}

