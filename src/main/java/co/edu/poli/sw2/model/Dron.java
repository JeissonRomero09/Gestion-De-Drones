package co.edu.poli.sw2.model;

/**
 * Representa un dron dentro del sistema de gestión.
 *
 * <p>
 * Esta clase contiene la información básica de un dron, incluyendo su
 * identificador, serial, modelo, fabricante, peso, piloto y sensores asociados.
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
	private int peso;


	/**
	 * Pilotos asociados al dron.
	 */
	private Piloto piloto;

	/**
	 * Sensores asociados al dron.
	 */
	private Sensores sensores;

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
	 * @param sensores sensores asociados al dron.
	 */
	public Dron(int id, String serial, String modelo, String fabricante,
			int peso, Piloto piloto, Sensores sensores) {

		this.id = id;
		this.serial = serial;
		this.modelo = modelo;
		this.fabricante = fabricante;
		this.peso = peso;
		this.piloto = piloto;
		this.sensores = sensores;
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

	public Piloto getPiloto() {
		return piloto;
	}

	public void setPiloto(Piloto piloto) {
		this.piloto = piloto;
	}
	public Sensores getSensores() {
		return sensores;
	}

	public void setSensores(Sensores sensores) {
		this.sensores = sensores;
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
				", piloto=" + piloto +
				", sensores=" + sensores +
				'}';
	}
}