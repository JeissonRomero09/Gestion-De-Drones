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
 * Esta clase permite realizar las operaciones CRUD sobre los drones:
 * crear, buscar, actualizar y eliminar. También se encarga de validar
 * los datos ingresados por el usuario y mostrar mensajes mediante
 * ventanas de alerta.
 * </p>
 *
 * <p>
 * El acceso a la base de datos se realiza mediante la clase
 * {@link DronDao}.
 * </p>
 *
 * @author Jeison Romero
 * @version 1.0
 */
public class DronController {

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


    // ==============================
    // CAMPOS DEL FORMULARIO
    // ==============================

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
     * Campo de texto utilizado para ingresar el identificador de el sensor
     */
    @FXML
    private TextField txtSensor;


    // ==============================
    // DAO
    // ==============================

    /**
     * Objeto DAO encargado de realizar las operaciones de acceso
     * a la base de datos de los drones.
     */
    private DronDao dronDao = new DronDao();


    /**
     * Inicializa los componentes y eventos de la interfaz.
     *
     * <p>
     * Este método se ejecuta automáticamente cuando se carga el archivo FXML.
     * Se configuran los efectos visuales de los botones y las acciones
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


    // ==============================
    // EFECTO DE LOS BOTONES
    // ==============================

    /**
     * Configura los efectos visuales de interacción de un botón.
     *
     * <p>
     * El botón aumenta ligeramente su tamaño cuando el cursor entra,
     * vuelve a su tamaño original cuando sale, se reduce al presionarlo
     * y recupera su tamaño al soltarlo.
     * </p>
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


    // ==============================
    // CREAR
    // ==============================

    /**
     * Crea un nuevo dron en la base de datos.
     *
     * <p>
     * Antes de realizar el registro, verifica que los campos obligatorios
     * estén completos. También convierte los valores numéricos de peso,
     * piloto y misión a sus respectivos tipos.
     * </p>
     *
     * <p>
     * Si los datos son válidos, se crea un objeto {@link Dron} y se envía
     * al {@link DronDao} para realizar la inserción en la base de datos.
     * </p>
     *
     * <p>
     * Después de guardar el dron correctamente, se muestra un mensaje
     * de confirmación y se limpian los campos del formulario.
     * </p>
     */
    private void crear() {

        try {

            if (txtSerial.getText().isEmpty()
                    || txtModelo.getText().isEmpty()
                    || txtFabricante.getText().isEmpty()
                    || txtPeso.getText().isEmpty()
                    || txtPiloto.getText().isEmpty()
                    || txtMision.getText().isEmpty()) {

                mostrarAlerta(
                        Alert.AlertType.WARNING,
                        "Campos incompletos",
                        "Por favor, complete todos los campos."
                );

                return;
            }

            Dron drone = new Dron();

            drone.setSerial(txtSerial.getText());
            drone.setModelo(txtModelo.getText());
            drone.setFabricante(txtFabricante.getText());
            drone.setPeso(Double.parseDouble(txtPeso.getText()));
            drone.setPilotoId(Integer.parseInt(txtPiloto.getText()));
            drone.setSensor(Integer.parseInt(txtMision.getText()));

            dronDao.crear(drone);

            mostrarAlerta(
                    Alert.AlertType.INFORMATION,
                    "Dron guardado",
                    "El dron se guardó correctamente."
            );

            limpiarCampos();

        } catch (NumberFormatException e) {

            mostrarAlerta(
                    Alert.AlertType.ERROR,
                    "Datos inválidos",
                    "Peso, piloto y misión deben ser valores numéricos."
            );
        }
    }


    // ==============================
    // BUSCAR
    // ==============================

    /**
     * Busca un dron utilizando su identificador.
     *
     * <p>
     * El método valida que el campo ID no esté vacío y que contenga
     * un número entero. Luego consulta el {@link DronDao}.
     * </p>
     *
     * <p>
     * Si el dron existe, sus datos son cargados en los campos del formulario.
     * Si no existe, se muestra una alerta informando al usuario.
     * </p>
     */
    private void buscar() {

        try {

            if (txtId.getText().isEmpty()) {

                mostrarAlerta(
                        Alert.AlertType.WARNING,
                        "ID requerido",
                        "Ingrese el ID del dron que desea buscar."
                );

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
                txtMision.setText(String.valueOf(drone.getMisionId()));

                mostrarAlerta(
                        Alert.AlertType.INFORMATION,
                        "Dron encontrado",
                        "El dron se encontró correctamente."
                );

            } else {

                mostrarAlerta(
                        Alert.AlertType.WARNING,
                        "Dron no encontrado",
                        "No existe un dron con el ID ingresado."
                );
            }

        } catch (NumberFormatException e) {

            mostrarAlerta(
                    Alert.AlertType.ERROR,
                    "ID inválido",
                    "El ID debe ser un número entero."
            );
        }
    }


    // ==============================
    // ELIMINAR
    // ==============================

    /**
     * Elimina un dron de la base de datos utilizando su identificador.
     *
     * <p>
     * Primero verifica que se haya ingresado un ID válido. Posteriormente
     * solicita al {@link DronDao} realizar la eliminación correspondiente.
     * </p>
     *
     * <p>
     * Una vez realizada la operación, se muestra un mensaje de confirmación
     * y se limpian los campos del formulario.
     * </p>
     */
    private void eliminar() {

        try {

            if (txtId.getText().isEmpty()) {

                mostrarAlerta(
                        Alert.AlertType.WARNING,
                        "ID requerido",
                        "Ingrese el ID del dron que desea eliminar."
                );

                return;
            }

            int id = Integer.parseInt(txtId.getText());

            dronDao.eliminar(id);

            mostrarAlerta(
                    Alert.AlertType.INFORMATION,
                    "Dron eliminado",
                    "El dron se eliminó correctamente."
            );

            limpiarCampos();

        } catch (NumberFormatException e) {

            mostrarAlerta(
                    Alert.AlertType.ERROR,
                    "ID inválido",
                    "El ID debe ser un número entero."
            );
        }
    }


    // ==============================
    // ACTUALIZAR
    // ==============================

    /**
     * Actualiza la información de un dron existente.
     *
     * <p>
     * Valida que se haya ingresado un ID y que todos los campos necesarios
     * estén completos. Posteriormente crea un objeto {@link Dron} con los
     * nuevos datos y lo envía al {@link DronDao} para actualizarlo.
     * </p>
     *
     * <p>
     * Los valores de ID, peso, piloto y misión deben ser numéricos.
     * </p>
     */
    private void actualizar() {

        try {

            if (txtId.getText().isEmpty()) {

                mostrarAlerta(
                        Alert.AlertType.WARNING,
                        "ID requerido",
                        "Ingrese el ID del dron que desea actualizar."
                );

                return;
            }

            if (txtSerial.getText().isEmpty()
                    || txtModelo.getText().isEmpty()
                    || txtFabricante.getText().isEmpty()
                    || txtPeso.getText().isEmpty()
                    || txtPiloto.getText().isEmpty()
                    || txtSensor.getText().isEmpty()) {

                mostrarAlerta(
                        Alert.AlertType.WARNING,
                        "Campos incompletos",
                        "Complete todos los campos antes de actualizar."
                );

                return;
            }

            Dron drone = new Dron();

            drone.setId(Integer.parseInt(txtId.getText()));
            drone.setSerial(txtSerial.getText());
            drone.setModelo(txtModelo.getText());
            drone.setFabricante(txtFabricante.getText());
            drone.setPeso(Double.parseDouble(txtPeso.getText()));
            drone.setPilotoId(Integer.parseInt(txtPiloto.getText()));
            drone.setSensor(Integer.parseInt(txtMision.getText()));

            dronDao.actualizar(drone);

            mostrarAlerta(
                    Alert.AlertType.INFORMATION,
                    "Dron actualizado",
                    "Los datos del dron se actualizaron correctamente."
            );

        } catch (NumberFormatException e) {

            mostrarAlerta(
                    Alert.AlertType.ERROR,
                    "Datos inválidos",
                    "ID, peso, piloto y misión deben contener valores numéricos."
            );
        }
    }


    // ==============================
    // LIMPIAR CAMPOS
    // ==============================

    /**
     * Limpia todos los campos del formulario.
     *
     * <p>
     * Este método se utiliza después de completar operaciones como
     * crear o eliminar un dron.
     * </p>
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


    // ==============================
    // ALERTAS
    // ==============================

    /**
     * Muestra una ventana de alerta al usuario.
     *
     * @param tipo tipo de alerta que se desea mostrar.
     * @param titulo título de la ventana de alerta.
     * @param mensaje mensaje que se mostrará al usuario.
     */
    private void mostrarAlerta(
            Alert.AlertType tipo,
            String titulo,
            String mensaje) {

        Alert alerta = new Alert(tipo);

        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);

        alerta.showAndWait();
    }
}
