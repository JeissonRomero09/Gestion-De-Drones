
package co.edu.poli.sw2.model;

import co.edu.poli.sw2.Service.Bridge.ControlDron;

/**
 * Representa un dron dentro del sistema de gestión.
 *
 * <p>
 * Esta clase implementa el patrón creacional Prototype mediante un constructor
 * de copia y un método {@code clone()}, permitiendo crear una nueva instancia
 * de un dron a partir de otra existente.
 * </p>
 *
 * <p>
 * Además, participa como Abstracción en el patrón estructural Bridge, delegando
 * el control del dron a una implementación de {@code ControlDron}.
 * </p>
 *
 * @author Jeison Romero
 * @author Camilo Vera
 * @version 3.0
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
	 * Implementación del control utilizada por el dron.
	 *
	 * <p>
	 * Permite desacoplar el dron de las diferentes formas de control, como el
	 * control básico y el control autónomo.
	 * </p>
	 */
	private ControlDron controlDron;

	/**
	 * Constructor vacío de la clase Dron.
	 */
	public Dron() {
	}

	/**
	 * Constructor que permite crear un dron con sus atributos básicos.
	 *
	 * @param id         identificador único del dron.
	 * @param serial     número de serie del dron.
	 * @param modelo     modelo del dron.
	 * @param fabricante fabricante del dron.
	 * @param peso       peso del dron.
	 */
	public Dron(int id, String serial, String modelo, String fabricante, int peso) {
		this.id = id;
		this.serial = serial;
		this.modelo = modelo;
		this.fabricante = fabricante;
		this.peso = peso;
	}

	/**
	 * Constructor de copia utilizado para implementar el patrón Prototype.
	 *
	 * <p>
	 * Permite crear un nuevo objeto Dron copiando los atributos de una instancia
	 * existente.
	 * </p>
	 *
	 * @param prototype instancia de Dron que se utilizará como prototipo.
	 */
	public Dron(Dron prototype) {
		if (prototype != null) {
			this.id = prototype.id;
			this.serial = prototype.serial;
			this.modelo = prototype.modelo;
			this.fabricante = prototype.fabricante;
			this.peso = prototype.peso;
		}
	}

	/**
	 * Crea una copia de la instancia actual utilizando el constructor de copia.
	 *
	 * @return un nuevo objeto Dron con los mismos atributos de la instancia actual.
	 */
	public Dron clone() {
		return new Dron(this);
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
	 * Modifica el identificador del dron.
	 *
	 * @param id nuevo identificador del dron.
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * Obtiene el número de serie del dron.
	 *
	 * @return número de serie del dron.
	 */
	public String getSerial() {
		return serial;
	}

	/**
	 * Modifica el número de serie del dron.
	 *
	 * @param serial nuevo número de serie del dron.
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
	 * Modifica el modelo del dron.
	 *
	 * @param modelo nuevo modelo del dron.
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
	 * Modifica el fabricante del dron.
	 *
	 * @param fabricante nuevo fabricante del dron.
	 */
	public void setFabricante(String fabricante) {
		this.fabricante = fabricante;
	}

	/**
	 * Obtiene el peso del dron.
	 *
	 * @return peso del dron.
	 */
	public int getPeso() {
		return peso;
	}

	/**
	 * Modifica el peso del dron.
	 *
	 * @param peso nuevo peso del dron.
	 */
	public void setPeso(int peso) {
		this.peso = peso;
	}

	/**
	 * Obtiene la implementación de control asociada al dron.
	 *
	 * @return implementación de control del dron.
	 */
	public ControlDron getControlDron() {
		return controlDron;
	}

	/**
	 * Asigna la implementación de control que utilizará el dron.
	 *
	 * @param controlDron implementación de control del dron.
	 */
	public void setControlDron(ControlDron controlDron) {
		this.controlDron = controlDron;
	}

	/**
	 * Ejecuta el tipo de control configurado para el dron.
	 *
	 * <p>
	 * El comportamiento es delegado a la implementación de {@code ControlDron}
	 * asociada al dron, permitiendo utilizar diferentes formas de control sin
	 * modificar la clase Dron.
	 * </p>
	 *
	 * @return mensaje correspondiente al tipo de control seleccionado.
	 */
	public String controlar() {
		if (controlDron == null) {
			return "Control de dron no seleccionado";
		}

		return controlDron.controlar(id);
	}

	/**
	 * Devuelve una representación textual del objeto Dron.
	 *
	 * @return cadena de texto con los datos del dron.
	 */
	@Override
	public String toString() {
		return "Dron{" + "id=" + id + ", serial='" + serial + '\'' + ", modelo='" + modelo + '\'' + ", fabricante='"
				+ fabricante + '\'' + ", peso=" + peso + '}';
	}
}