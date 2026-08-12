package co.edu.poli.sw2.main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * Clase principal de la aplicación del sistema de gestión de drones.
 *
 * <p>Esta clase inicia la aplicación JavaFX, carga la interfaz
 * definida en el archivo {@code drone.fxml} y configura la
 * ventana principal del sistema.</p>
 *
 * @author Jeison Romero
 * @version 1.0
 */
public class app extends Application {

    /**
     * Inicia la aplicación JavaFX y configura la ventana principal.
     *
     * <p>Este método carga el archivo FXML correspondiente a la
     * interfaz del sistema de drones, crea la escena y muestra
     * la ventana principal.</p>
     *
     * @param stage ventana principal proporcionada por JavaFX.
     * @throws Exception si ocurre un error al cargar el archivo FXML
     *         o al inicializar la interfaz.
     */
    @Override
    public void start(Stage stage) throws Exception {

        FXMLLoader loader = new FXMLLoader(
                getClass().getResource(
                        "/co/edu/poli/sw2/view/drone.fxml"
                )
        );

        AnchorPane root = loader.load();

        Scene scene = new Scene(root);

        stage.setTitle("Sistema de Drones");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Punto de entrada principal de la aplicación.
     *
     * <p>Este método inicia el ciclo de vida de JavaFX mediante
     * el método {@link #launch(String...)}.</p>
     *
     * @param args argumentos proporcionados al ejecutar la aplicación.
     */
    public static void main(String[] args) {
        launch(args);
    }
}