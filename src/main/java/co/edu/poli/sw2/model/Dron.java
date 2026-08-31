package co.edu.poli.sw2.model;

/**
 * Representa un dron dentro del sistema de gestión.
 *
 * <p>
 * Esta clase contiene la información básica de un dron, incluyendo su
 * identificador, serial, modelo, fabricante y peso.
 * </p>
 *
 * @author Jeison Romero
 * @version 2.0
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
	private int peso;

	/**
	 * Constructor vacío de la clase Dron.
	 */
	public Dron() {
	}

	/**
	 * Constructor que permite crear un dron con sus atributos básicos.
	 *
	 * @param id identificador único del dron.
	 * @param serial número de serie del dron.
	 * @param modelo modelo del dron.
	 * @param fabricante fabricante del dron.
	 * @param peso peso del dron.
	 */
	public Dron(int id, String serial, String modelo, String fabricante, int peso) {
		this.id = id;
		this.serial = serial;
		this.modelo = modelo;
		this.fabricante = fabricante;
		this.peso = peso;
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

	public int getPeso() {
		return peso;
	}

	public void setPeso(int peso) {
		this.peso = peso;
	}

	/**
	 * Devuelve una representación textual del objeto Dron.
	 *
	 * @return cadena de texto con los datos del dron.
	 */
	@Override
	public String toString() {
		return "Dron{" +
				"id=" + id +
				", serial='" + serial + '\'' +
				", modelo='" + modelo + '\'' +
				", fabricante='" + fabricante + '\'' +
				", peso=" + peso +
				'}';
	}
}