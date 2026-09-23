package co.edu.poli.sw2.Service.Proxy;

import javafx.scene.control.TextInputDialog;

import java.sql.SQLException;
import java.util.Optional;

/**
 * Proxy de protección que controla el acceso a la operación sensible de eliminación de drones[cite: 7].
 * Despliega un diálogo de autenticación nativo de JavaFX antes de delegar la acción al servicio real[cite: 3, 7].
 *
 * @author Cristian Vera
 * @version 1.0
 */
public class DronProxy implements ServiceInterface {

    /**
     * Instancia encapsulada del servicio real ({@link EliminarDron})[cite: 7].
     */
    private final ServiceInterface servicioReal;

    /**
     * Clave requerida para autorizar la acción de borrado[cite: 3].
     */
    private final String claveCorrecta;

    /**
     * Construye un Proxy envolviendo el servicio real con su respectiva clave de acceso[cite: 7].
     *
     * @param servicioReal Objeto real que implementa {@link ServiceInterface}[cite: 7].
     * @param claveCorrecta Contraseña requerida para conceder permisos.
     */
    public DronProxy(ServiceInterface servicioReal, String claveCorrecta) {
        this.servicioReal = servicioReal;
        this.claveCorrecta = claveCorrecta;
    }

    /**
     * Intercepta la petición, solicita la contraseña en un modal de JavaFX y, si es correcta,
     * delega la llamada al servicio real[cite: 3, 7].
     *
     * @param idDron Identificador del dron que se desea borrar.
     * @return Mensaje con el estado de la operación (éxito, denegado o cancelado).
     * @throws SQLException 
     */
    @Override
    public String eliminarDron(int idDron) throws SQLException {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Autenticación Requerida");
        dialog.setHeaderText("Eliminación del Dron ID: " + idDron);
        dialog.setContentText("Ingrese la clave de administrador:");

        Optional<String> result = dialog.showAndWait();

        if (result.isPresent()) {
            String claveIngresada = result.get().trim();
            if (autenticar(claveIngresada)) {
                return servicioReal.eliminarDron(idDron);
            } else {
                return "❌ Acceso denegado: Contraseña incorrecta. Operación cancelada.";
            }
        } else {
            return "⚠️ Operación cancelada por el usuario.";
        }
    }

    /**
     * Valida si la clave ingresada coincide con la clave registrada[cite: 3, 7].
     *
     * @param clave Clave introducida por el usuario en el cuadro de texto.
     * @return {@code true} si la clave es correcta; {@code false} en caso contrario.
     */
    private boolean autenticar(String clave) {
        return this.claveCorrecta.equals(clave);
    }
}
