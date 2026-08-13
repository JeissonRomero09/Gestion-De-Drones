package co.edu.poli.sw2.controller;

import co.edu.poli.sw2.Dao.DronDao;
import co.edu.poli.sw2.model.Dron;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

/**
 * Controlador encargado de gestionar la interfaz gráfica de los drones.
 *
 * <p>
 * Esta clase permite realizar las operaciones CRUD sobre los drones: crear,
 * buscar, actualizar y eliminar.
 * </p>
 *
 * @author Camilo Vera
 * @version 1.0
 */
public class DronController {

	@FXML
	private Button btnCrear;

	@FXML
	private Button btnBuscar;

	@FXML
	private Button btnEliminar;

	@FXML
	private Button btnActualizar;

	/**
	 * Campo para ingresar el identificador del dron.
	 */
	@FXML
	private TextField txtId;

	/**
	 * Campo para ingresar el número de serial del dron.
	 */
	@FXML
	private TextField txtSerial;

	/**
	 * Campo para ingresar el modelo del dron.
	 */
	@FXML
	private TextField txtModelo;

	/**
	 * Campo para ingresar el fabricante del dron.
	 */
	@FXML
	private TextField txtFabricante;

	/**
	 * Campo para ingresar el peso del dron.
	 */
	@FXML
	private TextField txtPeso;

	/**
	 * Campo para ingresar el identificador del piloto asociado.
	 */
	@FXML
	private TextField txtPiloto;
	/**
	 * Campo para ingresar el sensor.
	 */
	@FXML
	private TextField txtSensor;
	/**
	 * Objeto DAO encargado de gestionar las operaciones de los drones.
	 */
	private DronDao dronDao = new DronDao();

	/**
	 * Inicializa el controlador y configura los eventos de los botones.
	 */
	@FXML
	public void initialize() {

		efectoBoton(btnCrear);
		efectoBoton(btnBuscar);
		efectoBoton(btnEliminar);
		efectoBoton(btnActualizar);

		btnCrear.setOnAction(e -> crear());
		btnBuscar.setOnAction(e -> buscar());
		btnEliminar.setOnAction(e -> eliminar());
		btnActualizar.setOnAction(e -> actualizar());
	}

	/**
	 * Aplica efectos visuales a los botones cuando el usuario interactúa con ellos
	 * mediante el mouse.
	 *
	 * @param boton botón al que se aplicarán los efectos.
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

	private void crear() {

		try {

			if (txtId.getText().isEmpty() || txtSerial.getText().isEmpty() || txtModelo.getText().isEmpty()
					|| txtFabricante.getText().isEmpty() || txtPeso.getText().isEmpty() || txtPiloto.getText().isEmpty()
					|| txtSensor.getText().isEmpty()) {

				mostrarAlerta(Alert.AlertType.WARNING, "Campos incompletos",
						"Por favor, complete todos los campos, incluido el sensor.");

				return;
			}

			Dron drone = new Dron();

			drone.setId(Integer.parseInt(txtId.getText()));
			drone.setSerial(txtSerial.getText());
			drone.setModelo(txtModelo.getText());
			drone.setFabricante(txtFabricante.getText());
			drone.setPeso(Double.parseDouble(txtPeso.getText()));
			drone.setPilotoId(Integer.parseInt(txtPiloto.getText()));

			int sensorId = Integer.parseInt(txtSensor.getText());

			boolean creado = dronDao.crear(drone, sensorId);

			if (creado) {

				mostrarAlerta(Alert.AlertType.INFORMATION, "Dron guardado",
						"El dron se guardó correctamente con su sensor.");

				limpiarCampos();

			} else {

				mostrarAlerta(Alert.AlertType.ERROR, "Error al guardar",
						"No se pudo guardar el dron. Revise los datos ingresados.");
			}

		} catch (NumberFormatException e) {

			mostrarAlerta(Alert.AlertType.ERROR, "Datos inválidos",
					"ID, peso, piloto y sensor deben ser valores numéricos.");
		}
	}

	/**
	 * Busca un dron en la base de datos utilizando su identificador.
	 *
	 * <p>
	 * Si el dron existe, sus datos se cargan en los campos correspondientes del
	 * formulario, incluyendo el sensor asociado. Si no existe, se muestra una
	 * alerta al usuario.
	 * </p>
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

				txtSerial.setText(drone.getSerial());
				txtModelo.setText(drone.getModelo());
				txtFabricante.setText(drone.getFabricante());
				txtPeso.setText(String.valueOf(drone.getPeso()));
				txtPiloto.setText(String.valueOf(drone.getPilotoId()));

				Integer sensorId = dronDao.buscarSensor(id);

				if (sensorId != null) {
					txtSensor.setText(String.valueOf(sensorId));
				} else {
					txtSensor.clear();
				}

				mostrarAlerta(Alert.AlertType.INFORMATION, "Dron encontrado", "El dron se encontró correctamente.");

			} else {

				mostrarAlerta(Alert.AlertType.WARNING, "Dron no encontrado", "No existe un dron con el ID ingresado.");
			}

		} catch (NumberFormatException e) {

			mostrarAlerta(Alert.AlertType.ERROR, "ID inválido", "El ID debe ser un número entero.");
		}
	}

	/**
	 * Elimina un dron de la base de datos utilizando su identificador.
	 *
	 * <p>
	 * Verifica que se haya ingresado un ID válido antes de realizar la eliminación.
	 * Después de eliminar el dron, limpia los campos del formulario.
	 * </p>
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
		}
	}

	/**
	 * Actualiza la información de un dron existente.
	 *
	 * <p>
	 * Verifica que el ID y los demás campos requeridos estén completos, crea un
	 * objeto {@link Dron} con la información ingresada y utiliza el DAO para
	 * actualizar los datos en la base de datos.
	 * </p>
	 */
	private void actualizar() {

		try {

			if (txtId.getText().isEmpty()) {

				mostrarAlerta(Alert.AlertType.WARNING, "ID requerido", "Ingrese el ID del dron que desea actualizar.");

				return;
			}

			if (txtSerial.getText().isEmpty() || txtModelo.getText().isEmpty() || txtFabricante.getText().isEmpty()
					|| txtPeso.getText().isEmpty() || txtPiloto.getText().isEmpty() || txtSensor.getText().isEmpty()) {

				mostrarAlerta(Alert.AlertType.WARNING, "Campos incompletos",
						"Complete todos los campos antes de actualizar.");

				return;
			}

			Dron drone = new Dron();

			drone.setId(Integer.parseInt(txtId.getText()));
			drone.setSerial(txtSerial.getText());
			drone.setModelo(txtModelo.getText());
			drone.setFabricante(txtFabricante.getText());
			drone.setPeso(Double.parseDouble(txtPeso.getText()));
			drone.setPilotoId(Integer.parseInt(txtPiloto.getText()));

			dronDao.actualizar(drone);

			mostrarAlerta(Alert.AlertType.INFORMATION, "Dron actualizado",
					"Los datos del dron se actualizaron correctamente.");

		} catch (NumberFormatException e) {

			mostrarAlerta(Alert.AlertType.ERROR, "Datos inválidos",
					"ID, peso y piloto deben contener valores numéricos.");
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
	 * Muestra una ventana de alerta con el tipo, título y mensaje especificados.
	 *
	 * @param tipo    tipo de alerta que se mostrará.
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