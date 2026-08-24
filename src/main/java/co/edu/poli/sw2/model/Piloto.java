package co.edu.poli.sw2.model;

/**
 * Representa un piloto dentro del sistema de gestión de drones.
 *
 * @author Jeison Romero
 * @version 1.0
 */
public class Piloto {

    /**
     * Identificador único del piloto.
     */
    private int id;

    /**
     * Nombre del piloto.
     */
    private String nombre;

    /**
     * Experiencia del piloto.
     */
    private String experiencia;

    /**
     * Número telefónico del piloto.
     */
    private int telefono;

    /**
     * Constructor vacío.
     */
    public Piloto() {
    }

    /**
     * Constructor con todos los atributos.
     *
     * @param id identificador del piloto.
     * @param nombre nombre del piloto.
     * @param experiencia experiencia del piloto.
     * @param telefono teléfono del piloto.
     */
    public Piloto(int id, String nombre, String experiencia, int telefono) {
        this.id = id;
        this.nombre = nombre;
        this.experiencia = experiencia;
        this.telefono = telefono;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getExperiencia() {
        return experiencia;
    }

    public void setExperiencia(String experiencia) {
        this.experiencia = experiencia;
    }

    public int getTelefono() {
        return telefono;
    }

    public void setTelefono(int telefono) {
        this.telefono = telefono;
    }

    @Override
    public String toString() {
        return "Piloto{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", experiencia='" + experiencia + '\'' +
                ", telefono=" + telefono +
                '}';
    }
}