package co.edu.poli.sw2.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class BocetoController {

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

        btnCrear.setOnAction(e -> crear());
        btnBuscar.setOnAction(e -> buscar());
        btnEliminar.setOnAction(e -> eliminar());
        btnActualizar.setOnAction(e -> actualizar());

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