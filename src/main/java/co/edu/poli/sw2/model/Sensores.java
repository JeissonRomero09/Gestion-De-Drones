package co.edu.poli.sw2.model;

/**
 * Representa la entidad de dominio de un Sensor en el sistema.
 * <p>
 * Esta clase mapea la estructura de la tabla <code>sensores</code> de la base de datos
 * y proporciona los métodos de acceso para gestionar sus propiedades.
 * </p>
 * 
 * @author Cristian Vera
 * @version 1.0
 */
public class Sensores {
    
    /** Identificador único del sensor en la base de datos. */
    private int id;
    
    /** Tipo o categoría del sensor (ej. "Cámara Térmica", "LiDAR", "Multiespectral"). */
    private String tipo;
    
    /** Marca o fabricante del sensor. */
    private String fabricante;
    
    /** Identificador del dron al que está asignado el sensor. Puede ser null si no está asignado. */
    private Integer dronId;

    /**
     * Constructor por defecto sin parámetros.
     * Requerido para operaciones con marcos de trabajo, JavaFX y mapeo DAO.
     */
    public Sensores() {
    }

    /**
     * Constructor para la creación de nuevos sensores sin identificador asignado.
     * Útil antes de realizar la inserción en la base de datos.
     * 
     * @param tipo       El tipo o categoría del sensor.
     * @param fabricante El fabricante del sensor.
     * @param dronId     El ID del dron asociado (puede ser {@code null}).
     */
    public Sensores(String tipo, String fabricante, Integer dronId) {
        this.tipo = tipo;
        this.fabricante = fabricante;
        this.dronId = dronId;
    }

    /**
     * Constructor completo con identificador de base de datos.
     * Útil al recuperar registros previamente almacenados en la base de datos.
     * 
     * @param id         El identificador único del sensor.
     * @param tipo       El tipo o categoría del sensor.
     * @param fabricante El fabricante del sensor.
     * @param dronId     El ID del dron asociado (puede ser {@code null}).
     */
    public Sensores(int id, String tipo, String fabricante, Integer dronId) {
        this.id = id;
        this.tipo = tipo;
        this.fabricante = fabricante;
        this.dronId = dronId;
    }

    /**
     * Obtiene el ID del sensor.
     * 
     * @return El identificador único del sensor.
     */
    public int getId() {
        return id;
    }

    /**
     * Establece el ID del sensor.
     * 
     * @param id El nuevo identificador único para el sensor.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Obtiene el tipo del sensor.
     * 
     * @return El tipo de sensor.
     */
    public String getTipo() {
        return tipo;
    }

    /**
     * Establece el tipo del sensor.
     * 
     * @param tipo El nuevo tipo de sensor.
     */
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    /**
     * Obtiene el fabricante del sensor.
     * 
     * @return El fabricante del sensor.
     */
    public String getFabricante() {
        return fabricante;
    }

    /**
     * Establece el fabricante del sensor.
     * 
     * @param fabricante El nuevo fabricante del sensor.
     */
    public void setFabricante(String fabricante) {
        this.fabricante = fabricante;
    }

    /**
     * Obtiene el ID del dron asignado.
     * 
     * @return El identificador del dron, o {@code null} si no está asignado a ninguno.
     */
    public Integer getDronId() {
        return dronId;
    }

    /**
     * Asigna un dron al sensor por su identificador.
     * 
     * @param dronId El identificador del dron, o {@code null} para desasignarlo.
     */
    public void setDronId(Integer dronId) {
        this.dronId = dronId;
    }

    /**
     * Devuelve una representación en cadena de texto del objeto {@code Sensores}.
     * 
     * @return Cadena que contiene los atributos principales del sensor.
     */
    @Override
    public String toString() {
        return "Sensores [id=" + id + ", tipo=" + tipo + ", fabricante=" + fabricante + ", dronId=" + dronId + "]";
    }
}
