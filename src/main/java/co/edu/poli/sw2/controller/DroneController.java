package co.edu.poli.sw2.controller;

import co.edu.poli.sw2.Dao.DronDao;
import co.edu.poli.sw2.model.Dron;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.sql.SQLException;

/**
 * Controlador encargado de gestionar la interfaz gráfica de los drones.
 *
 * <p>
 * Esta clase permite realizar las operaciones CRUD sobre los drones: crear,
 * buscar, actualizar y eliminar. También se encarga de validar los datos
 * ingresados por el usuario y mostrar mensajes mediante ventanas de alerta.
 * </p>
 *
 * <p>
 * El acceso a la base de datos se realiza mediante la clase {@link DronDao}.
 * </p>
 *
 * @author Camilo Vera
 * @version 1.0
 */
public class DroneController {

	/**
	 * Botón utilizado para crear un nuevo dron.
	 */
	@FXML
	private Button btnCrear;

	/**
	 * Botón utilizado para buscar un dron.
	 */
	@FXML
	private Button btnBuscar;

	/**
	 * Botón utilizado para eliminar un dron.
	 */
	@FXML
	private Button btnEliminar;

	/**
	 * Botón utilizado para actualizar la información de un dron.
	 */
	@FXML
	private Button btnActualizar;

	/**
	 * Campo de texto utilizado para ingresar el identificador del dron.
	 */
	@FXML
	private TextField txtId;

	/**
	 * Campo de texto utilizado para ingresar el número de serial del dron.
	 */
	@FXML
	private TextField txtSerial;

	/**
	 * Campo de texto utilizado para ingresar el modelo del dron.
	 */
	@FXML
	private TextField txtModelo;

	/**
	 * Campo de texto utilizado para ingresar el fabricante del dron.
	 */
	@FXML
	private TextField txtFabricante;

	/**
	 * Campo de texto utilizado para ingresar el peso del dron.
	 */
	@FXML
	private TextField txtPeso;

	/**
	 * Campo de texto utilizado para ingresar el identificador del piloto.
	 */
	@FXML
	private TextField txtPiloto;

	/**
	 * Campo de texto utilizado para ingresar el identificador del sensor.
	 */
	@FXML
	private TextField txtSensor;

	/**
	 * Objeto DAO encargado de realizar las operaciones de acceso a la base de datos
	 * de los drones.
	 */
	private DronDao dronDao = new DronDao();

	/**
	 * Inicializa los componentes y eventos de la interfaz.
	 *
	 * <p>
	 * Este método se ejecuta automáticamente cuando se carga el archivo FXML. Se
	 * configuran los efectos visuales de los botones y las acciones
	 * correspondientes a cada operación CRUD.
	 * </p>
	 */
	@FXML
	public void initialize() {

		efectoBoton(btnCrear);
		efectoBoton(btnBuscar);
		efectoBoton(btnEliminar);
		efectoBoton(btnActualizar);

		// Acciones

		btnCrear.setOnAction(e -> crear());
		btnBuscar.setOnAction(e -> buscar());
		btnEliminar.setOnAction(e -> eliminar());
		btnActualizar.setOnAction(e -> actualizar());
	}

	/**
	 * Configura los efectos visuales de interacción de un botón.
	 *
	 * @param boton botón al que se le aplicarán los efectos visuales.
	 */
	private void efectoBoton(Button boton) {

		boton.setOnMouseEntered(e -> {
			boton.setScaleX(1.10);
			boton.setScaleY(1.10);
		});

		boton.setOnMouseExited(e -> {
			boton.setScaleX(1.0);
			boton.setScaleY(1.0);
		});

		boton.setOnMousePressed(e -> {
			boton.setScaleX(0.85);
			boton.setScaleY(0.85);
		});

		boton.setOnMouseReleased(e -> {
			boton.setScaleX(1.10);
			boton.setScaleY(1.10);
		});
	}

	/**
	 * Crea un nuevo dron en la base de datos.
	 *
	 * <p>
	 * Valida que todos los campos requeridos estén diligenciados, obtiene los
	 * identificadores del piloto y del sensor y registra el dron junto con sus
	 * asociaciones correspondientes.
	 * </p>
	 *
	 * <p>
	 * El identificador del dron es generado automáticamente por la base de datos y
	 * posteriormente se muestra en el campo ID.
	 * </p>
	 *
	 * @throws NumberFormatException si el peso, piloto o sensor no contienen
	 *                               valores numéricos válidos.
	 * @throws SQLException          si ocurre un error al guardar el dron en la
	 *                               base de datos.
	 */
	private void crear() {

		try {

			// Validar campos obligatorios
			if (txtSerial.getText().isEmpty() || txtModelo.getText().isEmpty() || txtFabricante.getText().isEmpty()
					|| txtPeso.getText().isEmpty() || txtPiloto.getText().isEmpty() || txtSensor.getText().isEmpty()) {

				mostrarAlerta(Alert.AlertType.WARNING, "Campos incompletos", "Por favor, complete todos los campos.");

				return;
			}

			// Obtener los identificadores
			int pilotoId = Integer.parseInt(txtPiloto.getText());
			int sensorId = Integer.parseInt(txtSensor.getText());

			// Crear objeto Dron
			Dron drone = new Dron();

			drone.setSerial(txtSerial.getText());
			drone.setModelo(txtModelo.getText());
			drone.setFabricante(txtFabricante.getText());
			drone.setPeso(Integer.parseInt(txtPeso.getText()));

			// Guardar el dron y obtener el ID generado
			int idGenerado = dronDao.crear(drone, pilotoId, sensorId);

			// Mostrar el ID generado
			txtId.setText(String.valueOf(idGenerado));

			mostrarAlerta(Alert.AlertType.INFORMATION, "Dron guardado",
					"El dron se guardó correctamente con ID: " + idGenerado);

			// Limpiar los demás campos
			txtSerial.clear();
			txtModelo.clear();
			txtFabricante.clear();
			txtPeso.clear();
			txtPiloto.clear();
			txtSensor.clear();

		} catch (NumberFormatException e) {

			mostrarAlerta(Alert.AlertType.ERROR, "Datos inválidos",
					"Peso, piloto y sensor deben ser valores numéricos.");

		} catch (SQLException e) {

			mostrarAlerta(Alert.AlertType.ERROR, "Error de Base de Datos",
					"No se pudo guardar el dron: " + e.getMessage());
		}
	}

	/**
	 * Busca un dron en la base de datos utilizando su identificador.
	 *
	 * <p>
	 * Si el dron existe, se muestran sus datos en los campos correspondientes,
	 * incluyendo el identificador del piloto y del sensor asociados.
	 * </p>
	 *
	 * @throws NumberFormatException si el ID ingresado no es un número entero.
	 * @throws SQLException          si ocurre un error al consultar la base de
	 *                               datos.
	 */
	private void buscar() {

		try {

			if (txtId.getText().isEmpty()) {

				mostrarAlerta(Alert.AlertType.WARNING, "ID requerido", "Ingrese el ID del dron que desea buscar.");

				return;
			}

			int id = Integer.parseInt(txtId.getText());

			Dron drone = dronDao.buscar(id);

			if (drone != null) {

				txtId.setText(String.valueOf(drone.getId()));
				txtSerial.setText(drone.getSerial());
				txtModelo.setText(drone.getModelo());
				txtFabricante.setText(drone.getFabricante());
				txtPeso.setText(String.valueOf(drone.getPeso()));

				// Mostrar piloto asociado
				if (drone.getPiloto() != null) {

					txtPiloto.setText(String.valueOf(drone.getPiloto().getId()));

				} else {

					txtPiloto.clear();
				}

				// Mostrar sensor asociado
				if (drone.getSensores() != null) {

					txtSensor.setText(String.valueOf(drone.getSensores().getId()));

				} else {

					txtSensor.clear();
				}

				mostrarAlerta(Alert.AlertType.INFORMATION, "Dron encontrado", "El dron se encontró correctamente.");

			} else {

				mostrarAlerta(Alert.AlertType.WARNING, "Dron no encontrado", "No existe un dron con el ID ingresado.");
			}

		} catch (NumberFormatException e) {

			mostrarAlerta(Alert.AlertType.ERROR, "ID inválido", "El ID debe ser un número entero.");

		} catch (SQLException e) {

			mostrarAlerta(Alert.AlertType.ERROR, "Error de Base de Datos",
					"No se pudo consultar el dron: " + e.getMessage());
		}
	}

	/**
	 * Elimina un dron de la base de datos utilizando su identificador.
	 *
	 * <p>
	 * Primero valida que el usuario haya ingresado un identificador y
	 * posteriormente solicita al DAO la eliminación del dron.
	 * </p>
	 *
	 * @throws NumberFormatException si el ID ingresado no es un número entero.
	 * @throws SQLException          si ocurre un error al eliminar el dron de la
	 *                               base de datos.
	 */
	private void eliminar() {

		try {

			if (txtId.getText().isEmpty()) {

				mostrarAlerta(Alert.AlertType.WARNING, "ID requerido", "Ingrese el ID del dron que desea eliminar.");

				return;
			}

			int id = Integer.parseInt(txtId.getText());

			dronDao.eliminar(id);

			mostrarAlerta(Alert.AlertType.INFORMATION, "Dron eliminado", "El dron se eliminó correctamente.");

			limpiarCampos();

		} catch (NumberFormatException e) {

			mostrarAlerta(Alert.AlertType.ERROR, "ID inválido", "El ID debe ser un número entero.");

		} catch (SQLException e) {

			mostrarAlerta(Alert.AlertType.ERROR, "Error de Base de Datos",
					"No se pudo eliminar el dron: " + e.getMessage());
		}
	}

	/**
	 * Actualiza la información de un dron existente.
	 *
	 * <p>
	 * Valida los datos ingresados, actualiza la información básica del dron y
	 * actualiza las asociaciones correspondientes con el piloto y el sensor.
	 * </p>
	 *
	 * @throws NumberFormatException si el ID, peso, piloto o sensor no contienen
	 *                               valores numéricos válidos.
	 * @throws SQLException          si ocurre un error al actualizar el dron en la
	 *                               base de datos.
	 */
	private void actualizar() {

		try {

			// Validar ID
			if (txtId.getText().isEmpty()) {

				mostrarAlerta(Alert.AlertType.WARNING, "ID requerido", "Ingrese el ID del dron que desea actualizar.");

				return;
			}

			// Validar campos
			if (txtSerial.getText().isEmpty() || txtModelo.getText().isEmpty() || txtFabricante.getText().isEmpty()
					|| txtPeso.getText().isEmpty() || txtPiloto.getText().isEmpty() || txtSensor.getText().isEmpty()) {

				mostrarAlerta(Alert.AlertType.WARNING, "Campos incompletos",
						"Complete todos los campos antes de actualizar.");

				return;
			}

			// Obtener valores numéricos
			int id = Integer.parseInt(txtId.getText());
			int peso = Integer.parseInt(txtPeso.getText());
			int pilotoId = Integer.parseInt(txtPiloto.getText());
			int sensorId = Integer.parseInt(txtSensor.getText());

			// Crear objeto Dron
			Dron drone = new Dron();

			drone.setId(id);
			drone.setSerial(txtSerial.getText());
			drone.setModelo(txtModelo.getText());
			drone.setFabricante(txtFabricante.getText());
			drone.setPeso(peso);

			// Actualizar dron, piloto y sensor
			dronDao.actualizar(drone);

			mostrarAlerta(Alert.AlertType.INFORMATION, "Dron actualizado",
					"Los datos del dron se actualizaron correctamente.");

		} catch (NumberFormatException e) {

			mostrarAlerta(Alert.AlertType.ERROR, "Datos inválidos",
					"ID, peso, piloto y sensor deben contener valores numéricos.");

		} catch (SQLException e) {

			mostrarAlerta(Alert.AlertType.ERROR, "Error de Base de Datos",
					"No se pudo actualizar el dron: " + e.getMessage());
		}
	}

	/**
	 * Limpia todos los campos del formulario.
	 */
	private void limpiarCampos() {

		txtId.clear();
		txtSerial.clear();
		txtModelo.clear();
		txtFabricante.clear();
		txtPeso.clear();
		txtPiloto.clear();
		txtSensor.clear();
	}

	/**
	 * Muestra una ventana de alerta al usuario.
	 *
	 * @param tipo    tipo de alerta que se desea mostrar.
	 * @param titulo  título de la ventana de alerta.
	 * @param mensaje mensaje que se mostrará al usuario.
	 */
	private void mostrarAlerta(Alert.AlertType tipo, String titulo, String mensaje) {

		Alert alerta = new Alert(tipo);

		alerta.setTitle(titulo);
		alerta.setHeaderText(null);
		alerta.setContentText(mensaje);

		alerta.showAndWait();
	}
}
