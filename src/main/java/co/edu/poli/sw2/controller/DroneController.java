package co.edu.poli.sw2.controller;

import co.edu.poli.sw2.Dao.DronDao;
import co.edu.poli.sw2.Service.Adapter.MisionAdapter;
import co.edu.poli.sw2.Service.Adapter.MisionService;
import co.edu.poli.sw2.Service.Bridge.ControlAutonomo;
import co.edu.poli.sw2.Service.Bridge.ControlBasico;
import co.edu.poli.sw2.Service.Composite.SensoresComponent;
import co.edu.poli.sw2.Service.Composite.SensoresComposite;
import co.edu.poli.sw2.Service.Composite.SensoresWrapper;
import co.edu.poli.sw2.Service.Decorator.Bateria;
import co.edu.poli.sw2.Service.Decorator.DronComponent;
import co.edu.poli.sw2.Service.Decorator.DroneWrapper;
import co.edu.poli.sw2.Service.Factory.AgriculturaFactory;
import co.edu.poli.sw2.Service.Factory.DronFactory;
import co.edu.poli.sw2.Service.Factory.VigilanciaFactory;
import co.edu.poli.sw2.Service.Protorype.DronPrototype;
import co.edu.poli.sw2.Service.Protorype.DronPrototypeImpl;
import co.edu.poli.sw2.Service.Proxy.DronProxy;
import co.edu.poli.sw2.Service.Proxy.EliminarDron;
import co.edu.poli.sw2.Service.Proxy.ServiceInterface;
import co.edu.poli.sw2.model.Agricultura;
import co.edu.poli.sw2.model.Dron;
import co.edu.poli.sw2.model.Mision;
import co.edu.poli.sw2.model.Sensores;
import co.edu.poli.sw2.model.Vigilancia;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * Controlador encargado de gestionar la interfaz gráfica de los drones.
 *
 * @author Camilo Vera
 * @author Jeisson Romero
 * @version 2.0
 */
public class DroneController {

	@FXML
	private ComboBox<String> cmbTiposSensores;

	@FXML
	private Button btnComposite;

	@FXML
	private Button btnAyudaComposite;

	@FXML
	private CheckBox chkBateria;
	@FXML
	private Button btnAgricola;

	@FXML
	private Button btnVigilante;

	@FXML
	private Button btnCrear;

	@FXML
	private Button btnBuscar;

	@FXML
	private Button btnEliminar;

	@FXML
	private Button btnActualizar;

	@FXML
	private TextField txtId;

	@FXML
	private TextField txtSensor;

	@FXML
	private TextField txtPiloto;

	@FXML
	private TextField txtSerial;

	@FXML
	private TextField txtModelo;

	@FXML
	private TextField txtFabricante;

	@FXML
	private TextField txtPeso;

	@FXML
	private ImageView imgAgricola;

	@FXML
	private ImageView imgVigilante;

	@FXML
	private TextArea txtConsola;

	@FXML
	private Button btnClonar;

	@FXML
	private RadioButton rbBasico;

	@FXML
	private RadioButton rbAutomatico;

	private DronFactory dronFactory;

	private Button botonTipoSeleccionado;

	/**
	 * Servicio encargado de gestionar la lógica de negocio y clonación de
	 * prototipos.
	 */
	private DronPrototype prototypeService = new DronPrototypeImpl();

	/**
	 * Objeto DAO encargado de gestionar la persistencia y las operaciones de acceso
	 * a datos para las entidades de tipo {@link Dron}.
	 */
	private DronDao dronDao = new DronDao();

	/**
	 * Inicializa los componentes de la interfaz gráfica de usuario (GUI), registra
	 * los controladores de eventos para los botones de control y asigna las
	 * fábricas correspondientes para cada tipo de dron.
	 * <p>
	 * Este método es invocado automáticamente por el cargador de JavaFX al
	 * finalizar la carga del archivo FXML asociativo.
	 * </p>
	 */
	@FXML
	public void initialize() {

		ToggleGroup grupoControlDron = new ToggleGroup();

		rbBasico.setToggleGroup(grupoControlDron);
		rbAutomatico.setToggleGroup(grupoControlDron);

		// Aplicar efectos a TODOS los botones
		efectoBoton(btnCrear);
		efectoBoton(btnBuscar);
		efectoBoton(btnEliminar);
		efectoBoton(btnActualizar);
		efectoBoton(btnAgricola);
		efectoBoton(btnVigilante);

		// Acciones de creación/búsqueda
		btnCrear.setOnAction(e -> crear());
		btnBuscar.setOnAction(e -> buscar());
		btnEliminar.setOnAction(e -> eliminar());
		btnActualizar.setOnAction(e -> actualizar());

		// Acciones de selección de tipo y fábrica
		btnAgricola.setOnAction(e -> {
			this.dronFactory = new AgriculturaFactory();
			seleccionarTipo(btnAgricola);
		});

		btnVigilante.setOnAction(e -> {
			this.dronFactory = new VigilanciaFactory();
			seleccionarTipo(btnVigilante);
		});

		limpiarSeleccionTipo();
	}

	/**
	 * Busca el prototipo original, extrae su dirección de memoria en RAM, genera un
	 * clon con una dirección de memoria distinta y despliega ambos resultados.
	 */
	@FXML
	private void clonar() {
		if (txtId == null || txtId.getText() == null || txtId.getText().trim().isEmpty()) {
			mostrarAlerta(Alert.AlertType.WARNING, "ID Requerido",
					"Por favor, ingrese el ID del dron que desea clonar.");
			return;
		}

		String idBusqueda = txtId.getText().trim();

		// 1. Obtener el objeto ORIGINAL (prototipo base) guardado en el mapa
		Dron dronOriginal = prototypeService.obtenerPrototipoBase(idBusqueda);

		// Si no existe en el mapa, intentamos crearlo con los datos actuales de la
		// vista
		if (dronOriginal == null) {
			if (txtModelo != null && !txtModelo.getText().isEmpty() && dronFactory != null) {
				dronOriginal = dronFactory.crearDron();
				dronOriginal.setSerial(idBusqueda);
				dronOriginal.setModelo(txtModelo.getText());

				if (txtFabricante != null)
					dronOriginal.setFabricante(txtFabricante.getText());
				if (txtPeso != null && !txtPeso.getText().isEmpty()) {
					try {
						dronOriginal.setPeso(Integer.parseInt(txtPeso.getText()));
					} catch (NumberFormatException ignored) {
					}
				}

				prototypeService.registrarPrototipo(idBusqueda, dronOriginal);
			}
		}

		if (dronOriginal == null) {
			mostrarAlerta(Alert.AlertType.ERROR, "No se puede clonar",
					"No hay ningún dron registrado o cargado con el ID: " + idBusqueda);
			return;
		}

		// 2. Obtener el CLON independiente a través del servicio
		Dron dronClonado = prototypeService.obtenerClon(idBusqueda);

		// 3. Extraer las direcciones de memoria ÚNICAS de cada objeto en la RAM
		String memOriginal = "0x" + Integer.toHexString(System.identityHashCode(dronOriginal)).toUpperCase();
		String memClonado = "0x" + Integer.toHexString(System.identityHashCode(dronClonado)).toUpperCase();

		// 4. Mostrar ambos resultados detallados en el TextArea
		StringBuilder sb = new StringBuilder();
		sb.append("--- DRON ORIGINAL (Prototipo) ---\n");
		sb.append("ID/Serial: ").append(idBusqueda).append("\n");
		sb.append("Tipo: ").append(dronOriginal.getClass().getSimpleName()).append("\n");
		sb.append("Modelo: ").append(dronOriginal.getModelo() != null ? dronOriginal.getModelo() : "N/A").append("\n");
		sb.append("Memoria RAM: ").append(memOriginal).append("\n\n");

		sb.append("--- DRON CLONADO ---\n");
		sb.append("ID/Serial: ").append(idBusqueda).append("\n");
		sb.append("Tipo: ").append(dronClonado.getClass().getSimpleName()).append("\n");
		sb.append("Modelo: ").append(dronClonado.getModelo() != null ? dronClonado.getModelo() : "N/A").append("\n");
		sb.append("Memoria RAM: ").append(memClonado);

		txtConsola.setText(sb.toString());

		mostrarAlerta(Alert.AlertType.INFORMATION, "Clonación Exitosa", "Clon generado");
	}

	/**
	 * Maneja el evento de selección para configurar el contexto de creación hacia
	 * un dron de tipo Agrícola.
	 *
	 * @param event El evento de acción disparado por la interfaz (clic en botón).
	 */
	@FXML
	private void crearDronAgricola(ActionEvent event) {
		dronFactory = new AgriculturaFactory();
		seleccionarTipo(btnAgricola);
	}

	/**
	 * Maneja el evento de selección para configurar el contexto de creación hacia
	 * un dron de tipo Vigilante.
	 *
	 * @param event El evento de acción disparado por la interfaz (clic en botón).
	 */
	@FXML
	private void crearDronVigilante(ActionEvent event) {
		dronFactory = new VigilanciaFactory();
		seleccionarTipo(btnVigilante);
	}

	/**
	 * PUNTO 2: Implementación y demostración formal del patrón Builder (Estructura
	 * GoF).
	 * 
	 */
	@FXML
	public void ejecutarBuilder() {
		try {
			// 1. Validar que el ID no esté vacío (al igual que en el Decorator)
			String idText = txtId.getText() != null ? txtId.getText().trim() : "";
			if (idText.isEmpty()) {
				javafx.scene.control.Alert alertId = new javafx.scene.control.Alert(
						javafx.scene.control.Alert.AlertType.WARNING);
				alertId.setTitle("ID Requerido");
				alertId.setHeaderText(null);
				alertId.setContentText("Por favor, ingrese el ID del dron.");
				alertId.showAndWait();
				return;
			}

			// 2. Validar selección de tipo de fábrica abajo
			if (dronFactory == null) {
				javafx.scene.control.Alert alertWarning = new javafx.scene.control.Alert(
						javafx.scene.control.Alert.AlertType.WARNING);
				alertWarning.setTitle("Tipo no seleccionado");
				alertWarning.setHeaderText(null);
				alertWarning.setContentText(
						"Por favor, seleccione primero si el dron es Agrícola o Vigilante con los botones de abajo.");
				alertWarning.showAndWait();
				return;
			}

			// 3. Validar campos de texto requeridos vacíos
			if (txtSerial.getText().isEmpty() || txtModelo.getText().isEmpty() || txtFabricante.getText().isEmpty()
					|| txtPeso.getText().isEmpty()) {

				javafx.scene.control.Alert alertCampos = new javafx.scene.control.Alert(
						javafx.scene.control.Alert.AlertType.WARNING);
				alertCampos.setTitle("Campos incompletos");
				alertCampos.setHeaderText(null);
				alertCampos.setContentText(
						"Por favor, complete los campos (Serial, Modelo, Fabricante y Peso) a la izquierda antes de construir.");
				alertCampos.showAndWait();
				return;
			}

			// 4. Obtener variables desde la GUI
			String serial = txtSerial.getText().trim();
			String modelo = txtModelo.getText().trim();
			String fabricante = txtFabricante.getText().trim();
			int peso = Integer.parseInt(txtPeso.getText().trim());
			int id = Integer.parseInt(idText);

			// 5. INVOCACIÓN SIGUIENDO EL DIAGRAMA DE CLASES
			Dron dronConstruido = null;
			String detallesEspecializados = "";

			if (dronFactory instanceof co.edu.poli.sw2.Service.Factory.VigilanciaFactory) {
				// Instanciar el ConcreteBuilder1 de tu diagrama
				co.edu.poli.sw2.Service.Builder.VigilanciaBuilder vegBuilder = new co.edu.poli.sw2.Service.Builder.VigilanciaBuilder();
				vegBuilder.buildDatosBasicos(id, serial, modelo, fabricante, peso); // buildStepA
				vegBuilder.buildAtributoEspecializado(); // buildStepB
				dronConstruido = vegBuilder.getResult(); // getResult()
				detallesEspecializados = "• Sistema Térmico: INSTALADO (true)\n";

			} else if (dronFactory instanceof co.edu.poli.sw2.Service.Factory.AgriculturaFactory) {
				// Instanciar el ConcreteBuilder2 de tu diagrama
				co.edu.poli.sw2.Service.Builder.AgriculturaBuilder agroBuilder = new co.edu.poli.sw2.Service.Builder.AgriculturaBuilder();
				agroBuilder.buildDatosBasicos(id, serial, modelo, fabricante, peso); // buildStepA
				agroBuilder.buildAtributoEspecializado(); // buildStepB
				dronConstruido = agroBuilder.getResult(); // getResult()
				detallesEspecializados = "• Volumen del Tanque: 25.0 L\n";
			}

			// 6. Registrar el objeto en el mapa de prototipos para que el botón clonar
			// funcione de inmediato
			prototypeService.registrarPrototipo(serial, dronConstruido);

			// 7. Construir mensaje de demostración para el Pop-up flotante
			StringBuilder sb = new StringBuilder();
			sb.append("Construcción GoF Certificada:\n");
			sb.append("• Clase Creada: ").append(dronConstruido.getClass().getSimpleName()).append("\n");
			sb.append("• ID del Dron: ")
					.append(dronConstruido.getId() == 0 ? "Asignado por DB" : dronConstruido.getId()).append("\n");
			sb.append("• Serial / Llave: ").append(dronConstruido.getSerial()).append("\n");
			sb.append("• Modelo: ").append(dronConstruido.getModelo()).append("\n");
			sb.append("• Fabricante: ").append(dronConstruido.getFabricante()).append("\n");
			sb.append("• Peso total: ").append(dronConstruido.getPeso()).append(" gramos\n");
			sb.append(detallesEspecializados);

			// Lanzar alerta de confirmación nativa de JavaFX
			javafx.scene.control.Alert alertSuccess = new javafx.scene.control.Alert(
					javafx.scene.control.Alert.AlertType.INFORMATION);
			alertSuccess.setTitle("Patrón Builder Clásico Ejecutado");
			alertSuccess.setHeaderText("¡Estructura de construcción GoF verificada!");
			alertSuccess.setContentText(sb.toString());
			alertSuccess.showAndWait();

			// Pintar reporte en la caja de texto
			if (txtConsola != null) {
				txtConsola.setText("=== ESTRUCTURA FORMAL BUILDER (GoF) ===\n" + sb.toString());
			}

		} catch (NumberFormatException e) {
			javafx.scene.control.Alert alertError = new javafx.scene.control.Alert(
					javafx.scene.control.Alert.AlertType.ERROR);
			alertError.setTitle("Error de Formato");
			alertError.setHeaderText(null);
			alertError.setContentText("Los campos numéricos (ID y Peso) deben contener números enteros válidos.");
			alertError.showAndWait();
		}
	}

	/**
	 * Ejecuta el patrón Decorator y muestra los datos formateados en la alerta
	 * confirmando que el decorador fue creado correctamente.
	 * 
	 * @param event Evento generado por el clic en el botón Decorator.
	 */
	@FXML
	void ejecutarDecorator(ActionEvent event) {

		String idDron = txtId.getText() != null ? txtId.getText().trim() : "";

		if (idDron.isEmpty()) {

			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("ID Requerido");
			alert.setHeaderText(null);
			alert.setContentText("Por favor, ingrese el ID del dron.");
			alert.showAndWait();

			return;
		}

		String modelo = (txtModelo != null && !txtModelo.getText().trim().isEmpty()) ? txtModelo.getText().trim()
				: "N/A";

		String serial = (txtSerial != null && !txtSerial.getText().trim().isEmpty()) ? txtSerial.getText().trim()
				: "N/A";

		String fabricante = (txtFabricante != null && !txtFabricante.getText().trim().isEmpty())
				? txtFabricante.getText().trim()
				: "N/A";

		String peso = (txtPeso != null && !txtPeso.getText().trim().isEmpty()) ? txtPeso.getText().trim() : "N/A";

		/*
		 * Componente concreto del patrón Decorator.
		 */
		DronComponent dron = new Bateria(5000);

		/*
		 * Se aplica el decorador al componente.
		 */

		if (chkBateria != null && chkBateria.isSelected()) {
			dron = new DroneWrapper(dron);
		}

		Alert alertInfo = new Alert(AlertType.INFORMATION);

		alertInfo.setTitle("Decorator Creado Correctamente");
		alertInfo.setHeaderText("¡Patrón Decorator Aplicado Exitosamente!");

		StringBuilder sb = new StringBuilder();

		sb.append("Decoración de Dron Certificada:\n");
		sb.append("• ID del Dron: ").append(idDron).append("\n");
		sb.append("• Serial / Llave: ").append(serial).append("\n");
		sb.append("• Modelo: ").append(modelo).append("\n");
		sb.append("• Fabricante: ").append(fabricante).append("\n");
		sb.append("• Peso total: ").append(peso).append(" gramos\n");
		sb.append("• Componente Decorado: ").append(dron.descripcion()).append("\n");

		alertInfo.setContentText(sb.toString());
		alertInfo.showAndWait();

		if (txtConsola != null) {

			txtConsola.setText("=== ESTRUCTURA FORMAL DECORATOR (GoF) ===\n" + sb.toString());
		}
	}

	/**
	 * Ejecuta la demostración del patrón Adapter.
	 *
	 * <p>
	 * Crea una instancia de {@link Mision} con información precargada, utiliza
	 * {@link MisionAdapter} para adaptarla al formato JSON y muestra en la consola
	 * de la aplicación el resultado de la operación.
	 * </p>
	 *
	 * @throws Exception si ocurre un error durante la adaptación de la misión.
	 */
	@FXML
	private void Adapter() {

		try {

			// Crear una instancia de Mision con información precargada
			Mision mision = new Mision();

			mision.setId(1);
			mision.setNombre("Mision de reconocimiento");
			mision.setUbicacion("Bogota");
			mision.setFecha(new Date());

			// Crear el servicio de misiones
			MisionService misionService = new MisionService();

			txtConsola.appendText("===== ADAPTER =====\n");

			// Crear el Adapter
			MisionAdapter adapter = new MisionAdapter(misionService);

			// Mostrar únicamente la evidencia de la ejecución

			txtConsola.appendText("Misión adaptada correctamente.\n");

			// Adaptar la misión y generar el archivo JSON
			String json = adapter.convertir(mision);

			txtConsola.appendText("Archivo JSON generado correctamente.\n");
			txtConsola.appendText("Resultado:\n");
			txtConsola.appendText(json + "\n");
			txtConsola.appendText("===================\n\n");

		} catch (Exception e) {

			Alert alerta = new Alert(Alert.AlertType.ERROR);
			alerta.setTitle("Error en Adapter");
			alerta.setHeaderText(null);
			alerta.setContentText("No fue posible generar el archivo JSON:\n" + e.getMessage());
			alerta.showAndWait();
		}
	}

	/**
	 * Procesa la creación e inserción de un nuevo dron en la base de datos.
	 *
	 * <p>
	 * El método valida que se haya seleccionado una fábrica concreta
	 * ({@link DronFactory}), verifica que los campos obligatorios del formulario no
	 * estén vacíos, comprueba que se haya seleccionado una modalidad de control
	 * mediante el patrón Bridge y construye la instancia correspondiente usando el
	 * patrón <i>Abstract Factory</i>.
	 * </p>
	 *
	 * <p>
	 * La implementación de control básico o autónomo se asigna al dron mediante el
	 * patrón estructural Bridge.
	 * </p>
	 *
	 * <p>
	 * Si la inserción en la base de datos es exitosa, se actualiza el campo de
	 * texto del ID con la clave primaria generada y se muestra en la consola el
	 * mensaje correspondiente al tipo de control seleccionado.
	 * </p>
	 *
	 * @see DronFactory#crearDron()
	 * @see DronDao#crear(Dron)
	 */
	@FXML
	private void crear() {
		try {
			if (dronFactory == null) {
				mostrarAlerta(Alert.AlertType.WARNING, "Tipo de dron no seleccionado",
						"Por favor, seleccione si el dron es Agrícola o Vigilante antes de crear.");
				return;
			}

			if (!rbBasico.isSelected() && !rbAutomatico.isSelected()) {
				mostrarAlerta(Alert.AlertType.WARNING, "Control no seleccionado",
						"Por favor, seleccione el tipo de control del dron.");
				return;
			}

			if (txtSerial.getText().isEmpty() || txtModelo.getText().isEmpty() || txtFabricante.getText().isEmpty()
					|| txtPeso.getText().isEmpty()) {
				mostrarAlerta(Alert.AlertType.WARNING, "Campos incompletos",
						"Por favor, complete todos los campos requeridos.");
				return;
			}

			Dron drone = dronFactory.crearDron();

			if (rbBasico.isSelected()) {
				drone.setControlDron(new ControlBasico());
			} else if (rbAutomatico.isSelected()) {
				drone.setControlDron(new ControlAutonomo());
			}

			drone.setSerial(txtSerial.getText());
			drone.setModelo(txtModelo.getText());
			drone.setFabricante(txtFabricante.getText());
			drone.setPeso(Integer.parseInt(txtPeso.getText()));

			List<Integer> idsSensores = obtenerSensoresDesdeCampo();
			int idGenerado = dronDao.crear(drone, idsSensores);

			txtId.setText(String.valueOf(idGenerado));

			String mensajeControl = drone.controlar();

			mostrarAlerta(Alert.AlertType.INFORMATION, "Dron guardado",
					"El dron se guardó correctamente with ID: " + idGenerado);

			limpiarCampos();

			txtConsola.setText(mensajeControl);

		} catch (NumberFormatException e) {
			mostrarAlerta(Alert.AlertType.ERROR, "Datos inválidos", "El peso debe ser un valor numérico entero.");

		} catch (IllegalArgumentException e) {
			mostrarAlerta(Alert.AlertType.ERROR, "IDs de sensores inválidos", e.getMessage());

		} catch (SQLException e) {
			mostrarAlerta(Alert.AlertType.ERROR, "Error de Base de Datos",
					"No se pudo guardar el dron: " + e.getMessage());
		}
	}

	/**
	 * Consulta un dron en la base de datos utilizando el identificador numérico
	 * (ID) ingresado.
	 * <p>
	 * Si el registro existe, el método mapea los atributos del objeto recuperado
	 * hacia los campos de texto de la interfaz gráfica. Además, utiliza la
	 * evaluación de tipos en tiempo de ejecución ({@code instanceof}) para
	 * identificar la subclase exacta ({@link Agricultura} o {@link Vigilancia}),
	 * reasignando la fábrica adecuada y resaltando el botón correspondiente.
	 * </p>
	 * <p>
	 * En caso de no encontrar coincidencias o presentarse un error en la entrada de
	 * datos, limpia las selecciones y presenta un mensaje de advertencia o error
	 * según corresponda.
	 * </p>
	 * 
	 * @see DronDao#buscar(int)
	 * @see #seleccionarTipo(Button)
	 */
	@FXML
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
				if (txtSensor != null) {
					txtSensor.setText(formatearIdsSensores(drone.getSensores()));
				}

				// Resetear selecciones previas
				limpiarSeleccionTipo();

				// Evaluamos el tipo de objeto retornado por la Factory en la BD
				if (drone instanceof Agricultura) {
					this.dronFactory = new AgriculturaFactory();
					seleccionarTipo(btnAgricola);
				} else if (drone instanceof Vigilancia) {
					this.dronFactory = new VigilanciaFactory();
					seleccionarTipo(btnVigilante);
				} else {
					limpiarSeleccionTipo();
				}

				mostrarAlerta(Alert.AlertType.INFORMATION, "Dron encontrado", "El dron se encontró correctamente.");

			} else {
				limpiarSeleccionTipo();
				if (txtSensor != null) {
					txtSensor.setText("Ninguno");
				}
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
	 * Elimina un registro de dron existente en la base de datos a partir de su
	 * identificador numérico (ID).
	 * <p>
	 * Valida que el campo correspondiente al ID no esté vacío y que contenga una
	 * cadena numérica válida. Una vez completada la eliminación en el sistema de
	 * datos, notifica al usuario mediante un diálogo informativo y procede a
	 * restablecer los campos de la interfaz gráfica.
	 * </p>
	 * 
	 * @see DronDao#eliminar(int)
	 * @see #limpiarCampos()
	 */
	@FXML
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
	 * Actualiza la información de un dron preexistente en la base de datos.
	 * <p>
	 * Verifica la presencia del identificador del dron, la selección activa de una
	 * fábrica ({@link DronFactory}) para determinar el tipo concreto (Agrícola o
	 * Vigilante), y la integridad de los datos obligatorios. A través de la fábrica
	 * instanciada, recrea el objeto especializado, asigna los nuevos atributos y
	 * solicita la persistencia del cambio mediante la capa DAO.
	 * </p>
	 * <p>
	 * Al finalizar con éxito, resetea los campos del formulario para prevenir
	 * modificaciones accidentales.
	 * </p>
	 * 
	 * @see DronFactory#crearDron()
	 * @see DronDao#actualizar(Dron)
	 * @see #limpiarCampos()
	 */
	@FXML
	private void actualizar() {

		try {
			if (txtId.getText().isEmpty()) {
				mostrarAlerta(Alert.AlertType.WARNING, "ID requerido", "Ingrese el ID del dron que desea actualizar.");
				return;
			}

			if (dronFactory == null) {
				mostrarAlerta(Alert.AlertType.WARNING, "Tipo no seleccionado",
						"Por favor seleccione si el dron es Agrícola o Vigilante antes de actualizar.");
				return;
			}

			if (txtSerial.getText().isEmpty() || txtModelo.getText().isEmpty() || txtFabricante.getText().isEmpty()
					|| txtPeso.getText().isEmpty()) {

				mostrarAlerta(Alert.AlertType.WARNING, "Campos incompletos",
						"Complete todos los campos del dron antes de actualizar.");
				return;
			}

			int id = Integer.parseInt(txtId.getText());
			int peso = Integer.parseInt(txtPeso.getText());

			Dron drone = dronFactory.crearDron();
			drone.setId(id);
			drone.setSerial(txtSerial.getText());
			drone.setModelo(txtModelo.getText());
			drone.setFabricante(txtFabricante.getText());
			drone.setPeso(peso);

			List<Integer> idsSensores = obtenerSensoresDesdeCampo();
			dronDao.actualizar(drone, idsSensores);

			mostrarAlerta(Alert.AlertType.INFORMATION, "Dron actualizado",
					"Los datos del dron se actualizaron correctamente.");

			limpiarCampos();

		} catch (NumberFormatException e) {
			mostrarAlerta(Alert.AlertType.ERROR, "Datos inválidos",
					"El ID y el Peso deben contener valores numéricos enteros.");
		} catch (IllegalArgumentException e) {
			mostrarAlerta(Alert.AlertType.ERROR, "IDs de sensores inválidos", e.getMessage());
		} catch (SQLException e) {
			mostrarAlerta(Alert.AlertType.ERROR, "Error de Base de Datos",
					"No se pudo actualizar el dron: " + e.getMessage());
		}
	}

	/**
	 * Limpia y restablece el estado original de todos los campos de texto del
	 * formulario en la interfaz gráfica.
	 * <p>
	 * Incluye comprobaciones de nulidad para los campos opcionales
	 * {@code txtPiloto} y {@code txtSensor}, garantizando la seguridad en tiempo de
	 * ejecución ante cambios estructurales en el archivo FXML. Además, invoca la
	 * deselección del tipo de dron activo.
	 * </p>
	 * 
	 * @see #limpiarSeleccionTipo()
	 */
	private void limpiarCampos() {

		txtId.clear();
		txtSerial.clear();
		txtModelo.clear();
		txtFabricante.clear();
		txtPeso.clear();
		if (txtPiloto != null) {
			txtPiloto.clear();
		}
		if (txtSensor != null) {
			txtSensor.clear();
		}

		limpiarSeleccionTipo();
	}

	/**
	 * Restablece la escala gráfica predeterminada (1.0) para los botones e imágenes
	 * de selección de tipo de dron, y anula las referencias activas a la fábrica
	 * ({@link DronFactory}) y al botón seleccionado.
	 */
	private void limpiarSeleccionTipo() {

		btnAgricola.setScaleX(1.0);
		btnAgricola.setScaleY(1.0);
		imgAgricola.setScaleX(1.0);
		imgAgricola.setScaleY(1.0);

		btnVigilante.setScaleX(1.0);
		btnVigilante.setScaleY(1.0);
		imgVigilante.setScaleX(1.0);
		imgVigilante.setScaleY(1.0);

		botonTipoSeleccionado = null;
		dronFactory = null;
	}

	/**
	 * Aplica un realce visual mediante un incremento de escala (1.20) al botón y a
	 * la imagen correspondiente al tipo de dron seleccionado.
	 * <p>
	 * Primero restablece las transformaciones visuales previas en todos los
	 * controles de tipo antes de aplicar la nueva escala de realce.
	 * </p>
	 *
	 * @param boton El {@link Button} asociado al tipo de dron que se desea
	 *              destacar.
	 */
	private void seleccionarTipo(Button boton) {

		btnAgricola.setScaleX(1.0);
		btnAgricola.setScaleY(1.0);
		imgAgricola.setScaleX(1.0);
		imgAgricola.setScaleY(1.0);

		btnVigilante.setScaleX(1.0);
		btnVigilante.setScaleY(1.0);
		imgVigilante.setScaleX(1.0);
		imgVigilante.setScaleY(1.0);

		botonTipoSeleccionado = boton;

		if (boton == btnAgricola) {
			btnAgricola.setScaleX(1.20);
			btnAgricola.setScaleY(1.20);
			imgAgricola.setScaleX(1.20);
			imgAgricola.setScaleY(1.20);
		} else if (boton == btnVigilante) {
			btnVigilante.setScaleX(1.20);
			btnVigilante.setScaleY(1.20);
			imgVigilante.setScaleX(1.20);
			imgVigilante.setScaleY(1.20);
		}
	}

	/**
	 * Configura los eventos de entrada, salida, presión y liberación del ratón
	 * sobre un botón para aplicar un efecto dinámico de escalado (*hover* y
	 * *click*).
	 *
	 * @param boton El {@link Button} al cual se le registrarán las animaciones
	 *              interactivas.
	 */
	private void efectoBoton(Button boton) {

		if (boton == null)
			return;

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
			if (boton.isHover()) {
				boton.setScaleX(1.10);
				boton.setScaleY(1.10);
			} else {
				boton.setScaleX(1.0);
				boton.setScaleY(1.0);
			}
		});
	}

	private List<Integer> obtenerSensoresDesdeCampo() {
		List<Integer> ids = new ArrayList<>();
		if (txtSensor == null || txtSensor.getText() == null || txtSensor.getText().trim().isEmpty()) {
			return ids;
		}

		String[] partes = txtSensor.getText().split("[\\s,;]+");
		for (String parte : partes) {
			if (parte == null || parte.trim().isEmpty()) {
				continue;
			}
			ids.add(Integer.parseInt(parte.trim()));
		}
		return ids;
	}

	private String formatearIdsSensores(List<Sensores> sensores) {
		if (sensores == null || sensores.isEmpty()) {
			return "Ninguno";
		}

		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < sensores.size(); i++) {
			if (i > 0) {
				sb.append(", ");
			}
			sb.append(sensores.get(i).getId());
		}
		return sb.toString();
	}

	/**
	 * Despliega un cuadro de diálogo modal de tipo {@link Alert} para notificar
	 * informaciones, advertencias o errores al usuario.
	 *
	 * @param tipo    El nivel de gravedad o categoría de la alerta
	 *                ({@link Alert.AlertType}).
	 * @param titulo  El texto que se mostrará en la barra de título de la ventana.
	 * @param mensaje El cuerpo del texto explicativo dentro de la alerta.
	 */
	private void mostrarAlerta(Alert.AlertType tipo, String titulo, String mensaje) {

		Alert alerta = new Alert(tipo);
		alerta.setTitle(titulo);
		alerta.setHeaderText(null);
		alerta.setContentText(mensaje);
		alerta.showAndWait();
	}

	/**
	 * Controlador de JavaFX para construir y visualizar la jerarquía del patrón
	 * Composite de sensores dentro de un componente {@link TreeView}.
	 * 
	 * @author Cristian Vera
	 * @version 1.0
	 */
	/**
	 * Carga las opciones en el ComboBox y aplica una fábrica de celdas
	 * (CellFactory) para deshabilitar la selección de los nodos que son grupos o
	 * contenedores.
	 * 
	 * @param event Evento de acción de JavaFX.
	 */
	@FXML
	void mostrarTiposSensores(ActionEvent event) {
		if (cmbTiposSensores.getItems().isEmpty()) {
			cmbTiposSensores.getItems().addAll("--- Sensor Temperatura ---", "  Sensor Infrarrojo", "  RTD",
					"--- Sensor Cámara ---", "  Sensor CMOS", "  Sensor CCD", "--- Sensor Sonido ---",
					"  Sensor Analógico", "--- Sensor Digital ---", "    SPI", "    UART", "  Sensor Inteligente");

			// Personalizar las celdas para que los grupos no sean seleccionables
			cmbTiposSensores.setCellFactory(lv -> new ListCell<String>() {
				@Override
				protected void updateItem(String item, boolean empty) {
					super.updateItem(item, empty);
					if (empty || item == null) {
						setText(null);
						setDisable(false);
					} else {
						setText(item);
						// Si empieza con "---", se considera un grupo/contenedor y se deshabilita
						if (item.startsWith("---")) {
							setDisable(true);
							setStyle("-fx-font-weight: bold; -fx-opacity: 0.5; -fx-text-fill: #888888;");
						} else {
							setDisable(false);
							setStyle("-fx-font-weight: normal; -fx-opacity: 1.0;");
						}
					}
				}
			});

			// Personalizar la celda principal visible para evitar incoherencias visuales
			cmbTiposSensores.setButtonCell(cmbTiposSensores.getCellFactory().call(null));
		}

		cmbTiposSensores.setVisible(true);
	}

	@FXML
	void seleccionarSensor(ActionEvent event) {
		String seleccion = cmbTiposSensores.getValue();

		if (seleccion != null && !seleccion.startsWith("---")) {

			// 1. Validar el ID desde el campo txtId
			if (txtId == null) {
				Alert alert = new Alert(Alert.AlertType.ERROR);
				alert.setTitle("Error de Configuración");
				alert.setHeaderText("Campo No Inyectado");
				alert.setContentText("El campo txtId no está vinculado en el archivo FXML.");
				alert.showAndWait();
				return;
			}

			String idIngresado = txtId.getText() != null ? txtId.getText().trim() : "";

			if (idIngresado.isEmpty()) {
				Alert alertError = new Alert(Alert.AlertType.WARNING);
				alertError.setTitle("ID Requerido");
				alertError.setHeaderText(null);
				alertError.setContentText("Por favor, ingrese el ID del dron.");
				alertError.showAndWait();

				// Se difiere la limpieza de la selección para no interrumpir el evento de
				// JavaFX
				Platform.runLater(() -> cmbTiposSensores.getSelectionModel().clearSelection());
				return;
			}

			// 2. Detectar el grupo contenedor (Composite) buscando hacia arriba en el
			// ComboBox
			String sensorFinal = seleccion.trim();
			String nombreGrupo = "Grupo General de Sensores";
			int indexSeleccionado = cmbTiposSensores.getSelectionModel().getSelectedIndex();

			for (int i = indexSeleccionado - 1; i >= 0; i--) {
				String itemAnterior = cmbTiposSensores.getItems().get(i);
				if (itemAnterior.startsWith("---")) {
					nombreGrupo = itemAnterior.replace("---", "").trim();
					break;
				}
			}

			// 3. Crear el modelo del sensor asignándole el ID y fabricante
			Sensores sensorModel = new Sensores();
			sensorModel.setTipo(sensorFinal);
			sensorModel.setFabricante("Predeterminado");

			// 4. Construir la estructura del Patrón Composite
			SensoresComponent hoja = new SensoresWrapper(sensorModel);
			SensoresComposite grupo = new SensoresComposite(nombreGrupo);
			grupo.add(hoja);

			// 5. Formatear y enviar la salida hacia la Consola de la interfaz
			StringBuilder salidaConsola = new StringBuilder();
			salidaConsola.append("> ===== EJECUCIÓN PATRÓN COMPOSITE =====\n");
			salidaConsola.append("> ID Dron Asociado: ").append(idIngresado).append("\n");
			salidaConsola.append("> ").append(grupo.execute());
			salidaConsola.append("> ======================================\n\n");

			if (txtConsola != null) {
				txtConsola.appendText(salidaConsola.toString());
			} else {
				Alert alert = new Alert(Alert.AlertType.INFORMATION);
				alert.setTitle("Patrón Composite - Sensores");
				alert.setHeaderText("Resultado de Ejecución");
				alert.setContentText(salidaConsola.toString());
				alert.showAndWait();
			}

		} else if (seleccion != null && seleccion.startsWith("---")) {
			// Se difiere la limpieza de la selección para no interrumpir el evento de
			// JavaFX
			Platform.runLater(() -> cmbTiposSensores.getSelectionModel().clearSelection());
		}
	}

	@FXML
	void mostrarDiagrama(ActionEvent event) {
		try {
			Stage stage = new Stage();
			stage.setTitle("Diagrama Jerárquico - Patrón Composite");

			Image image = new Image(getClass().getResourceAsStream("/co/edu/poli/sw2/images/diagrama_composite.png"));
			ImageView imageView = new ImageView(image);
			imageView.setFitWidth(500);
			imageView.setPreserveRatio(true);

			StackPane pane = new StackPane(imageView);
			Scene scene = new Scene(pane, 520, 350);

			stage.setScene(scene);
			stage.show();
		} catch (Exception e) {
			// Se captura la excepción en silencio (sin e.printStackTrace()) y se notifica
			// vía alerta
			Alert alert = new Alert(Alert.AlertType.INFORMATION);
			alert.setTitle("Estructura Composite");
			alert.setHeaderText("Jerarquía de Sensores");
			alert.setContentText("Sensor General\n" + " ├── Sensor Temperatura (Infrarrojo, RTD)\n"
					+ " ├── Sensor Cámara (CMOS, CCD)\n" + " ├── Sensor Sonido (Analógico, Digital -> SPI, UART)\n"
					+ " └── Sensor Inteligente");
			alert.showAndWait();
		}

	}
/**
	 * Elimina un registro de dron existente utilizando el patrón Proxy de protección.
	 * <p>
	 * Muestra un diálogo emergente para solicitar la contraseña de administrador
	 * al usuario. La clave ingresada es validada a través de {@link DronProxy}.
	 * Si la autenticación es exitosa, se delega la ejecución al servicio real
	 * {@link EliminarDron} para remover el dron del sistema de datos.
	 * </p>
	 * 
	 * @author Cristian Vera
	 * @version 2.0
	 */
	@FXML
	private void eliminar() {
	    try {
	        if (txtId.getText().isEmpty()) {
	            mostrarAlerta(Alert.AlertType.WARNING, "ID requerido", "Ingrese el ID del dron que desea eliminar.");
	            return;
	        }

	        int id = Integer.parseInt(txtId.getText().trim());

	        // 1. Solicitar la contraseña de administrador al usuario
	        TextInputDialog dialog = new TextInputDialog();
	        dialog.setTitle("Verificación de Seguridad (Proxy)");
	        dialog.setHeaderText("Acceso Restringido - Eliminación de Dron");
	        dialog.setContentText("Ingrese la contraseña de administrador:");

	        Optional<String> result = dialog.showAndWait();

	        if (!result.isPresent() || result.get().trim().isEmpty()) {
	            mostrarAlerta(Alert.AlertType.WARNING, "Operación Cancelada", "Debe ingresar una contraseña para continuar.");
	            return;
	        }

	        String passwordIngresada = result.get().trim();

	        // 2. Instanciar el servicio real y el Proxy con la clave que ingresó el usuario
	        ServiceInterface servicioReal = new EliminarDron(this.dronDao);
	        ServiceInterface proxy = new DronProxy(servicioReal, passwordIngresada);

	        // 3. Ejecutar la acción mediante el Proxy
	        String resultado = proxy.eliminarDron(id);

	        // 4. Evaluar la respuesta devuelta por el Proxy
	        if (resultado.startsWith("✅")) {
	            mostrarAlerta(Alert.AlertType.INFORMATION, "Éxito", resultado);
	            limpiarCampos();
	            if (txtConsola != null) {
	                txtConsola.appendText("=== PATRÓN PROXY ===\n" + resultado + "\n\n");
	            }
	        } else if (resultado.startsWith("❌")) {
	            mostrarAlerta(Alert.AlertType.ERROR, "Acceso Denegado", resultado);
	            if (txtConsola != null) {
	                txtConsola.appendText("=== PATRÓN PROXY (Bloqueado) ===\n" + resultado + "\n\n");
	            }
	        } else {
	            mostrarAlerta(Alert.AlertType.WARNING, "Respuesta Proxy", resultado);
	        }

	    } catch (NumberFormatException e) {
	        mostrarAlerta(Alert.AlertType.ERROR, "ID inválido", "El ID debe ser un número entero.");
	    } catch (Exception e) {
	        mostrarAlerta(Alert.AlertType.ERROR, "Error", "Ocurrió un error inesperado: " + e.getMessage());
	    }
	}
}
