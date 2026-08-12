package co.edu.poli.sw2.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class BroneController {

	@FXML
	private Button btnCrear;

	@FXML
	private Button btnBuscar;

	@FXML
	private Button btnEliminar;

	@FXML
	private Button btnActualizar;

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
		System.out.println("Botón Crear presionado");
	}

	private void buscar() {
		System.out.println("Botón Buscar presionado");
	}

	private void eliminar() {
		System.out.println("Botón Eliminar presionado");
	}

	private void actualizar() {
		System.out.println("Botón Actualizar presionado");
	}

}
