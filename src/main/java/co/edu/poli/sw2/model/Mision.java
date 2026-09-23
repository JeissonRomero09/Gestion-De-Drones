package co.edu.poli.sw2.model;

import java.util.Date;

/**
 * Representa una misión dentro del sistema de gestión de drones.
 *
 * <p>La clase contiene la información básica de una misión,
 * incluyendo su identificador, nombre, ubicación, fecha y
 * el dron asociado.</p>
 *
 * @author Jeisson Romero
 * @version 1.0
 */
public class Mision {

    /**
     * Identificador único de la misión.
     */
    private int id;

    /**
     * Nombre de la misión.
     */
    private String Nombre;

    /**
     * Ubicación donde se realizará la misión.
     */
    private String Ubicacion;

    /**
     * Fecha programada para la misión.
     */
    private Date Fecha;

    /**
     * Dron asociado a la misión.
     */
    private Dron dron;

    /**
     * Obtiene el identificador de la misión.
     *
     * @return identificador único de la misión.
     */
    public int getId() {
        return id;
    }

    /**
     * Establece el identificador de la misión.
     *
     * @param id identificador único de la misión.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Obtiene el nombre de la misión.
     *
     * @return nombre de la misión.
     */
    public String getNombre() {
        return Nombre;
    }

    /**
     * Establece el nombre de la misión.
     *
     * @param Nombre nombre que identificará la misión.
     */
    public void setNombre(String Nombre) {
        this.Nombre = Nombre;
    }

    /**
     * Obtiene la ubicación de la misión.
     *
     * @return ubicación donde se ejecutará la misión.
     */
    public String getUbicacion() {
        return Ubicacion;
    }

    /**
     * Establece la ubicación de la misión.
     *
     * @param Ubicacion ubicación donde se realizará la misión.
     */
    public void setUbicacion(String Ubicacion) {
        this.Ubicacion = Ubicacion;
    }

    /**
     * Obtiene la fecha programada para la misión.
     *
     * @return fecha de ejecución o programación de la misión.
     */
    public Date getFecha() {
        return Fecha;
    }

    /**
     * Establece la fecha de la misión.
     *
     * @param Fecha fecha programada para la misión.
     */
    public void setFecha(Date Fecha) {
        this.Fecha = Fecha;
    }

    /**
     * Obtiene el dron asociado a la misión.
     *
     * @return dron relacionado con la misión o null si no existe asociación.
     */
    public Dron getDron() {
        return dron;
    }

    /**
     * Establece el dron asociado a la misión.
     *
     * @param dron objeto {@link Dron} que se asignará a la misión.
     */
    public void setDron(Dron dron) {
        this.dron = dron;
    }
}