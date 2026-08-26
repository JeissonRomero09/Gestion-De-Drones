package co.edu.poli.sw2.model;

/**
 * Representa un dron especializado para actividades de vigilancia.
 *
 * <p>
 * Esta clase extiende la clase {@link Dron} y agrega información específica
 * relacionada con la capacidad de detección térmica utilizada en labores
 * de vigilancia y monitoreo.
 * </p>
 *
 * @author jeisson romero
 * @version 1.0
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
                      int peso,Piloto piloto, Sensores sensores, boolean deteccionTermica) {
        super(id, serial, modelo, fabricante, peso, piloto, sensores);
        this.deteccionTermica = deteccionTermica;
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
