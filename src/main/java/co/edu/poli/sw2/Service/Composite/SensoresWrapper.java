package co.edu.poli.sw2.Service.Composite;

import co.edu.poli.sw2.model.Sensores;

/**
 * Representa un sensor individual dentro de la estructura Composite (nodo hoja).
 * 
 * @author Cristian Vera
 * @version 1.0
 */
public class SensoresWrapper implements SensoresComponent {

    /** Objeto del modelo que contiene los datos del sensor. */
    private Sensores sensor;

    /**
     * Constructor por defecto.
     */
    public SensoresWrapper() {
    }

    /**
     * Constructor que inicializa el wrapper con una instancia de {@link Sensores}.
     * 
     * @param sensor Objeto del modelo que representa el sensor.
     */
    public SensoresWrapper(Sensores sensor) {
        this.sensor = sensor;
    }

    /**
     * Retorna el resultado de la lectura del sensor individual como texto
     * para su consumo en la interfaz de JavaFX.
     * 
     * @return Texto con los detalles del sensor.
     */
    @Override
    public String execute() {
        if (sensor != null) {
            return "Lectura OK -> Sensor: " + sensor.getTipo() + " | Fabricante: " + sensor.getFabricante();
        }
        return "Lectura Fallida -> Sensor sin datos asignados.";
    }

    /**
     * Obtiene el nombre descriptivo del sensor para la representación del árbol.
     *
     * @return nombre del sensor o texto predeterminado si no hay datos.
     */
    @Override
    public String getNombre() {
        if (sensor != null) {
            return sensor.getTipo() + " (" + sensor.getFabricante() + ")";
        }
        return "Sensor Indefinido";
    }

    /**
     * Obtiene el sensor asociado al wrapper.
     *
     * @return instancia de {@link Sensores} contenida en el wrapper.
     */
    public Sensores getSensor() {
        return sensor;
    }

    /**
     * Asigna el sensor asociado al wrapper.
     *
     * @param sensor instancia de {@link Sensores} a asociar.
     */
    public void setSensor(Sensores sensor) {
        this.sensor = sensor;
    }
}