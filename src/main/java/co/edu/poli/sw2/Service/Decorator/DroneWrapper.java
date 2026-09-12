package co.edu.poli.sw2.Service.Decorator;

/**
 * Decorador abstracto base (Wrapper) que mantiene una referencia a un {@link DronComponent}.
 * Delega las llamadas del método {@link #descripcion()} al objeto decorado.
 * 
 * @author Cristian Vera 
 * @version 1.0
 */
public class DroneWrapper extends DronComponent {

    /** Componente de dron que está siendo envuelto/decorado. */
    protected DronComponent dron;

    /**
     * Constructor que recibe el componente base o decorador a envolver.
     * 
     * @param dron la instancia de {@link DronComponent} a decorar.
     */
    public DroneWrapper(DronComponent dron) {
        this.dron = dron;
    }

    /**
     * Redirige la llamada de descripción al objeto envuelto.
     * 
     * @return {@link String} con la descripción delegada del componente interno.
     */
    @Override
    public String descripcion() {
        return dron.descripcion();
    }
}