package co.edu.poli.sw2.Service.Proxy;

import co.edu.poli.sw2.Dao.DronDao;
import java.sql.SQLException;

/**
 * Implementación concreta del servicio real para la eliminación de drones[cite: 7].
 * Atrapa los errores SQL internamente para no romper el contrato de la interfaz[cite: 7].
 * 
 * @author Cristian Vera
 * @version 1.0
 */
public class EliminarDron implements ServiceInterface {

    private DronDao dronDao;

    public EliminarDron() {
        this.dronDao = new DronDao();
    }

    public EliminarDron(DronDao dronDao) {
        this.dronDao = dronDao;
    }

    @Override
    public String eliminarDron(int idDron) {
        try {
            // Invocación al DAO que lanza SQLException
            this.dronDao.eliminar(idDron);
            return "✅ Dron con ID " + idDron + " eliminado correctamente de la base de datos.";
        } catch (SQLException e) {
            // Captura el error de base de datos y devuelve un mensaje informativo
            return "❌ Error en la base de datos al eliminar el dron: " + e.getMessage();
        } catch (Exception e) {
            return "❌ Error inesperado: " + e.getMessage();
        }
    }
}