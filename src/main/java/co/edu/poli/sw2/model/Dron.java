package co.edu.poli.sw2.model;

/**
 * Representa un dron dentro del sistema de gestión.
 *
 * <p>
 * Esta clase contiene la información básica de un dron, incluyendo su
 * identificador, serial, modelo, fabricante, peso y el piloto asociado.
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
	 * Obtiene el identificador del dron.
	 *
	 * @return identificador del dron.
	 */
	public int getId() {
		return id;
	}

	/**
	 * Establece el identificador del dron.
	 *
	 * @param id identificador del dron.
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * Obtiene el número de serie del dron.
	 *
	 * @return número de serie.
	 */
	public String getSerial() {
		return serial;
	}

	/**
	 * Establece el número de serie del dron.
	 *
	 * @param serial número de serie del dron.
	 */
	public void setSerial(String serial) {
		this.serial = serial;
	}

	/**
	 * Obtiene el modelo del dron.
	 *
	 * @return modelo del dron.
	 */
	public String getModelo() {
		return modelo;
	}

	/**
	 * Establece el modelo del dron.
	 *
	 * @param modelo modelo del dron.
	 */
	public void setModelo(String modelo) {
		this.modelo = modelo;
	}

	/**
	 * Obtiene el fabricante del dron.
	 *
	 * @return fabricante del dron.
	 */
	public String getFabricante() {
		return fabricante;
	}

	/**
	 * Establece el fabricante del dron.
	 *
	 * @param fabricante fabricante del dron.
	 */
	public void setFabricante(String fabricante) {
		this.fabricante = fabricante;
	}

	/**
	 * Obtiene el peso del dron.
	 *
	 * @return peso del dron.
	 */
	public double getPeso() {
		return peso;
	}

	/**
	 * Establece el peso del dron.
	 *
	 * @param peso peso del dron.
	 */
	public void setPeso(double peso) {
		this.peso = peso;
	}

	/**
	 * Obtiene el identificador del piloto asociado.
	 *
	 * @return identificador del piloto.
	 */
	public int getPilotoId() {
		return pilotoId;
	}

	/**
	 * Establece el identificador del piloto asociado.
	 *
	 * @param pilotoId identificador del piloto.
	 */
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