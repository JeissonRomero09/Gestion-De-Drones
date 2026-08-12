package co.edu.poli.sw2.controller;

import co.edu.poli.sw2.Dao.DronDao;
import co.edu.poli.sw2.model.Dron;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class DronController {

    @FXML
    private Button btnCrear;

    @FXML
    private Button btnBuscar;

    @FXML
    private Button btnEliminar;

    @FXML
    private Button btnActualizar;


    // Campos del formulario

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
    private TextField txtPiloto;

    @FXML
    private TextField txtMision;


    // DAO

    private DronDao dronDao = new DronDao();


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
            drone.setMisionId(Integer.parseInt(txtMision.getText()));

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
                    || txtMision.getText().isEmpty()) {

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
            drone.setMisionId(Integer.parseInt(txtMision.getText()));

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

    private void limpiarCampos() {

        txtId.clear();
        txtSerial.clear();
        txtModelo.clear();
        txtFabricante.clear();
        txtPeso.clear();
        txtPiloto.clear();
        txtMision.clear();
    }


    // ==============================
    // ALERTAS
    // ==============================

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
