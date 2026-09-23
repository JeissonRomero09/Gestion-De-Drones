package co.edu.poli.sw2.Service.Proxy;

import java.sql.SQLException;

/**
 * Interfaz común para el patrón de diseño Proxy[cite: 7].
 * Define el contrato para las operaciones del servicio de gestión de drones 
 * que deben ser implementadas tanto por el objeto real como por su intermediario[cite: 7].
 *
 * @author Cristian Vera
 * @version 1.0
 */
public interface ServiceInterface {

    /**
     * Procesa la solicitud de eliminación de un dron según su ID.
     *
     * @param idDron Identificador único del dron a eliminar.
     * @return Mensaje con el resultado de la operación.
     * @throws SQLException 
     */
    String eliminarDron(int idDron) throws SQLException;
}