package co.edu.poli.sw2.model;

/**
 * Representa un sensor dentro del sistema de gestión de drones.
 *
 * @author Jeison Romero
 * @version 1.0
 */
public class Sensores {

    /**
     * Identificador único del sensor.
     */
    private int id;

    /**
     * Tipo de sensor.
     */
    private String tipo;

    /**
     * Fabricante del sensor.
     */
    private String fabricante;

    /**
     * Constructor vacío.
     */
    public Sensores() {
    }

    /**
     * Constructor con todos los atributos.
     *
     * @param id identificador del sensor.
     * @param tipo tipo de sensor.
     * @param fabricante fabricante del sensor.
     */
    public Sensores(int id, String tipo, String fabricante) {
        this.id = id;
        this.tipo = tipo;
        this.fabricante = fabricante;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getFabricante() {
        return fabricante;
    }

    public void setFabricante(String fabricante) {
        this.fabricante = fabricante;
    }

    @Override
    public String toString() {
        return "Sensores{" +
                "id=" + id +
                ", tipo='" + tipo + '\'' +
                ", fabricante='" + fabricante + '\'' +
                '}';
    }
}