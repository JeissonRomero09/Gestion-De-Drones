package co.edu.poli.sw2.controller;

import co.edu.poli.sw2.Dao.DronDao;
import co.edu.poli.sw2.Service.AgriculturaFactory;
import co.edu.poli.sw2.Service.DronFactory;
import co.edu.poli.sw2.Service.DronPrototype;
import co.edu.poli.sw2.Service.DronPrototypeImpl;
import co.edu.poli.sw2.Service.VigilanciaFactory;
import co.edu.poli.sw2.model.Agricultura;
import co.edu.poli.sw2.model.Dron;
import co.edu.poli.sw2.model.Vigilancia;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;

import java.sql.SQLException;

/**
 * Controlador encargado de gestionar la interfaz gráfica de los drones.
 *
 * @author Camilo Vera
 * @author Jeisson Romero
 * @version 2.0
 */
public class DroneController {

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
	private TextArea txtMemoria;

	@FXML
	private Button btnClonar;


	private DronFactory dronFactory;

	private Button botonTipoSeleccionado;
	
	


	/**
	 * Servicio encargado de gestionar la lógica de negocio y clonación de prototipos.
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
	 * Busca el prototipo original, extrae su dirección de memoria en RAM,
	 * genera un clon con una dirección de memoria distinta y despliega ambos resultados.
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

		// Si no existe en el mapa, intentamos crearlo con los datos actuales de la vista
				if (dronOriginal == null) {
					if (txtModelo != null && !txtModelo.getText().isEmpty() && dronFactory != null) {
						dronOriginal = dronFactory.crearDron();
						dronOriginal.setSerial(idBusqueda);
						dronOriginal.setModelo(txtModelo.getText());
						
						if (txtFabricante != null) dronOriginal.setFabricante(txtFabricante.getText());
						if (txtPeso != null && !txtPeso.getText().isEmpty()) {
							try {
								dronOriginal.setPeso(Integer.parseInt(txtPeso.getText()));
							} catch (NumberFormatException ignored) {}
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
		String memClonado  = "0x" + Integer.toHexString(System.identityHashCode(dronClonado)).toUpperCase();

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

		txtMemoria.setText(sb.toString());

		mostrarAlerta(Alert.AlertType.INFORMATION, "Clonación Exitosa", 
				"Clon generado en una posición de memoria diferente.");
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
	 * Procesa la creación e inserción de un nuevo dron en la base de datos.
	 * <p>
	 * El método valida que se haya seleccionado una fábrica concreta
	 * ({@link DronFactory}), verifica que los campos obligatorios del formulario no
	 * estén vacíos, construye la instancia correspondiente usando el patrón
	 * <i>Abstract Factory</i> y asigna los atributos capturados desde la interfaz
	 * gráfica.
	 * </p>
	 * <p>
	 * Si la inserción en la base de datos es exitosa, se actualiza el campo de
	 * texto del ID con la clave primaria generada y se reinicia el formulario.
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

			if (txtSerial.getText().isEmpty() || txtModelo.getText().isEmpty() || txtFabricante.getText().isEmpty()
					|| txtPeso.getText().isEmpty()) {

				mostrarAlerta(Alert.AlertType.WARNING, "Campos incompletos",
						"Por favor, complete todos los campos requeridos.");
				return;
			}

			Dron drone = dronFactory.crearDron();

			drone.setSerial(txtSerial.getText());
			drone.setModelo(txtModelo.getText());
			drone.setFabricante(txtFabricante.getText());
			drone.setPeso(Integer.parseInt(txtPeso.getText()));

			int idGenerado = dronDao.crear(drone);

			txtId.setText(String.valueOf(idGenerado));

			mostrarAlerta(Alert.AlertType.INFORMATION, "Dron guardado",
					"El dron se guardó correctamente con ID: " + idGenerado);

			limpiarCampos();

		} catch (NumberFormatException e) {
			mostrarAlerta(Alert.AlertType.ERROR, "Datos inválidos", "El peso debe ser un valor numérico entero.");
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

			// Usar la fábrica para conservar/cambiar el tipo de dron
			Dron drone = dronFactory.crearDron();
			drone.setId(id);
			drone.setSerial(txtSerial.getText());
			drone.setModelo(txtModelo.getText());
			drone.setFabricante(txtFabricante.getText());
			drone.setPeso(peso);

			dronDao.actualizar(drone);

			mostrarAlerta(Alert.AlertType.INFORMATION, "Dron actualizado",
					"Los datos del dron se actualizaron correctamente.");

			limpiarCampos();

		} catch (NumberFormatException e) {
			mostrarAlerta(Alert.AlertType.ERROR, "Datos inválidos",
					"El ID y el Peso deben contener valores numéricos enteros.");
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

		 * PUNTO 2: Implementación y demostración formal del patrón Builder (Estructura GoF).
	 */
	@FXML
	public void ejecutarBuilder() {
		try {
			// 1. Validar selección de tipo de fábrica abajo
			if (dronFactory == null) {
				javafx.scene.control.Alert alertWarning = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.WARNING);
				alertWarning.setTitle("Tipo no seleccionado");
				alertWarning.setHeaderText(null);
				alertWarning.setContentText("Por favor, seleccione primero si el dron es Agrícola o Vigilante con los botones de abajo.");
				alertWarning.showAndWait();
				return;
			}

			// 2. Validar campos de texto requeridos vacíos
			if (txtSerial.getText().isEmpty() || txtModelo.getText().isEmpty() || 
				txtFabricante.getText().isEmpty() || txtPeso.getText().isEmpty()) {

				javafx.scene.control.Alert alertCampos = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.WARNING);
				alertCampos.setTitle("Campos incompletos");
				alertCampos.setHeaderText(null);
				alertCampos.setContentText("Por favor, complete los campos (Serial, Modelo, Fabricante y Peso) a la izquierda antes de construir.");
				alertCampos.showAndWait();
				return;
			}

			// 3. Obtener variables desde la GUI
			String serial = txtSerial.getText().trim();
			String modelo = txtModelo.getText().trim();
			String fabricante = txtFabricante.getText().trim();
			int peso = Integer.parseInt(txtPeso.getText().trim());
			
			int id = 0;
			if (txtId.getText() != null && !txtId.getText().trim().isEmpty()) {
				try {
					id = Integer.parseInt(txtId.getText().trim());
				} catch (NumberFormatException ignored) {}
			}

			// 4. INVOCACIÓN SIGUIENDO EL DIAGRAMA DE CLASES
			Dron dronConstruido = null;
			String detallesEspecializados = "";

			if (dronFactory instanceof co.edu.poli.sw2.Service.VigilanciaFactory) {
				// Instanciar el ConcreteBuilder1 de tu diagrama
				co.edu.poli.sw2.Service.VigilanciaBuilder vegBuilder = new co.edu.poli.sw2.Service.VigilanciaBuilder();
				vegBuilder.buildDatosBasicos(id, serial, modelo, fabricante, peso); // buildStepA
				vegBuilder.buildAtributoEspecializado();                           // buildStepB
				dronConstruido = vegBuilder.getResult();                           // getResult()
				detallesEspecializados = "• Sistema Térmico: INSTALADO (true)\n";
				
			} else if (dronFactory instanceof co.edu.poli.sw2.Service.AgriculturaFactory) {
				// Instanciar el ConcreteBuilder2 de tu diagrama
				co.edu.poli.sw2.Service.AgriculturaBuilder agroBuilder = new co.edu.poli.sw2.Service.AgriculturaBuilder();
				agroBuilder.buildDatosBasicos(id, serial, modelo, fabricante, peso); // buildStepA
				agroBuilder.buildAtributoEspecializado();                            // buildStepB
				dronConstruido = agroBuilder.getResult();                            // getResult()
				detallesEspecializados = "• Volumen del Tanque: 25.0 L\n";
			}

			// 5. Registrar el objeto en el mapa de prototipos para que el botón clonar funcione de inmediato
			prototypeService.registrarPrototipo(serial, dronConstruido);

			// 6. Construir mensaje de demostración para el Pop-up flotante
			StringBuilder sb = new StringBuilder();
			sb.append("Construcción GoF Certificada:\n");
			sb.append("• Clase Creada: ").append(dronConstruido.getClass().getSimpleName()).append("\n");
			sb.append("• ID del Dron: ").append(dronConstruido.getId() == 0 ? "Asignado por DB" : dronConstruido.getId()).append("\n");
			sb.append("• Serial / Llave: ").append(dronConstruido.getSerial()).append("\n");
			sb.append("• Modelo: ").append(dronConstruido.getModelo()).append("\n");
			sb.append("• Fabricante: ").append(dronConstruido.getFabricante()).append("\n");
			sb.append("• Peso total: ").append(dronConstruido.getPeso()).append(" gramos\n");
			sb.append(detallesEspecializados);
			
			// Lanzar alerta de confirmación nativa de JavaFX
			javafx.scene.control.Alert alertSuccess = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.INFORMATION);
			alertSuccess.setTitle("Patrón Builder Clásico Ejecutado");
			alertSuccess.setHeaderText("¡Estructura de construcción GoF verificada!");
			alertSuccess.setContentText(sb.toString());
			alertSuccess.showAndWait();

			// Pintar reporte en la caja de texto
			if (txtMemoria != null) {
				txtMemoria.setText("=== ESTRUCTURA FORMAL BUILDER (GoF) ===\n" + sb.toString());
			}

		} catch (NumberFormatException e) {
			javafx.scene.control.Alert alertError = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.ERROR);
			alertError.setTitle("Error de Formato");
			alertError.setHeaderText(null);
			alertError.setContentText("El campo Peso debe ser un número entero válido.");
			alertError.showAndWait();
		}
	}
}
