package co.edu.poli.sw2.Service.Decorator;
/**
 * Componente concreto que representa la batería de un dron.
 * Actúa como el objeto base que puede ser envuelto por decoradores.
 * 
 * @author Cristian Vera 
 * @version 1.0
 */
public class Bateria extends DronComponent {

    /** Capacidad de la batería en miliamperios hora (mAh). */
    private int capacidad;

    /**
     * Crea una nueva instancia de la batería con la capacidad especificada.
     * 
     * @param capacidad la capacidad de la batería en mAh.
     */
    public Bateria(int capacidad) {
        this.capacidad = capacidad;
    }

    /**
     * Obtiene la capacidad actual de la batería.
     * 
     * @return la capacidad en mAh.
     */
    public int getCapacidad() {
        return capacidad;
    }

    /**
     * Establece la capacidad de la batería.
     * 
     * @param capacidad la nueva capacidad en mAh.
     */
    public void setCapacidad(int capacidad) {
        this.capacidad = capacidad;
    }

    /**
     * Retorna la descripción específica de la batería con su capacidad.
     * 
     * @return {@link String} representando la batería y su capacidad.
     */
    @Override
    public String descripcion() {
        return "Batería con capacidad de " + capacidad + " mAh";
    }
}