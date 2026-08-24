package co.edu.poli.sw2.model;

/**
 * Representa un dron dentro del sistema de gestión.
 *
 * <p>
 * Esta clase contiene la información básica de un dron, incluyendo su

 * identificador, serial, modelo, fabricante, peso y el piloto asociado.

 * identificador, serial, modelo, fabricante, peso y las referencias al piloto y
 * sensor asociados.
>>>>>>> de0283c25a8d037d860fc14254cc1ff358ddba88
 * </p>
 *
 * @author Jeison Romero
 * @version 1.0
 */
public class Dron {

    /**
     * Identificador único del dron.
     */
    private int id;

    /**
     * Número de serie del dron.
     */
    private String serial;

    /**
     * Modelo del dron.
     */
    private String modelo;

    /**
     * Fabricante del dron.
     */
    private String fabricante;

    /**
     * Peso del dron.
     */
    private double peso;

    /**
     * Identificador del piloto asociado al dron.
     */
    private int pilotoId;


	/**
	 * Constructor vacío de la clase Dron.
	 */
	public Dron() {
	}

	/**
	 * Constructor que permite crear un dron con todos sus atributos.
	 *
	 * @param id         identificador único del dron.
	 * @param serial     número de serie del dron.
	 * @param modelo     modelo del dron.
	 * @param fabricante fabricante del dron.
	 * @param peso       peso del dron.
	 * @param pilotoId   identificador del piloto asociado.
	 */
	public Dron(int id, String serial, String modelo, String fabricante, double peso, int pilotoId) {

		this.id = id;
		this.serial = serial;
		this.modelo = modelo;
		this.fabricante = fabricante;
		this.peso = peso;
		this.pilotoId = pilotoId;
	}

    /**
     * Identificador del sensor asociado al dron.
     */
    private int sensorid;

    /**
     * Constructor vacío de la clase Dron.
     */
    public Dron() {
    }

    /**
     * Constructor que permite crear un dron con todos sus atributos.
     *
     * @param id identificador único del dron.
     * @param serial número de serie del dron.
     * @param modelo modelo del dron.
     * @param fabricante fabricante del dron.
     * @param peso peso del dron.
     * @param pilotoId identificador del piloto asociado.
     * @param sensorid identificador del sensor asociado.
     */
    public Dron(int id, String serial, String modelo, String fabricante,
                double peso, int pilotoId, int sensorid) {

        this.id = id;
        this.serial = serial;
        this.modelo = modelo;
        this.fabricante = fabricante;
        this.peso = peso;
        this.pilotoId = pilotoId;
        this.sensorid = sensorid;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSerial() {
        return serial;
    }

    public void setSerial(String serial) {
        this.serial = serial;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getFabricante() {
        return fabricante;
    }

    public void setFabricante(String fabricante) {
        this.fabricante = fabricante;
    }

    public double getPeso() {
        return peso;
    }

    public void setPeso(double peso) {
        this.peso = peso;
    }

    public int getPilotoId() {
        return pilotoId;
    }

    public void setPilotoId(int pilotoId) {
        this.pilotoId = pilotoId;
    }


	/**
	 * Devuelve una representación textual del objeto Dron.
	 *
	 * @return cadena de texto con los datos del dron.
	 */
	@Override
	public String toString() {
		return "Dron{" + "id=" + id + ", serial='" + serial + '\'' + ", modelo='" + modelo + '\'' + ", fabricante='"
				+ fabricante + '\'' + ", peso=" + peso + ", pilotoId=" + pilotoId + '}';
	}
}

    public int getsensorid() {
        return sensorid;
    }

    public void setsensorid(int sensorid) {
        this.sensorid = sensorid;
    }

    @Override
    public String toString() {
        return "Dron{" +
                "id=" + id +
                ", serial='" + serial + '\'' +
                ", modelo='" + modelo + '\'' +
                ", fabricante='" + fabricante + '\'' +
                ", peso=" + peso +
                ", pilotoId=" + pilotoId +
                ", sensorid=" + sensorid +
                '}';
    }
}

