package co.edu.poli.sw2.Dao;

import co.edu.poli.sw2.Service.Singleton;
import co.edu.poli.sw2.Service.DronFactory;
import co.edu.poli.sw2.Service.AgriculturaFactory;
import co.edu.poli.sw2.Service.VigilanciaFactory;
import co.edu.poli.sw2.model.Dron;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase DAO (Data Access Object) encargada de realizar las operaciones de
 * acceso a datos de la entidad {@link Dron}.
 *
 * @author Jeisson Romero
 * @version 2.0
 */
public class DronDao {

    /**
     * Registra un nuevo dron en la base de datos guardando su tipo concreto.
     *
     * @param drone objeto Dron con la información del dron.
     * @return identificador generado para el nuevo dron.
     * @throws SQLException si ocurre un error de acceso a la base de datos.
     */
    public int crear(Dron drone) throws SQLException {

        String sqlDron = "INSERT INTO dron "
                + "(serial, modelo, fabricante, peso, tipo_dron) "
                + "VALUES (?, ?, ?, ?, ?)";

        Connection conexion = Singleton.getInstance().getConexion();

        try (PreparedStatement psDron = conexion.prepareStatement(
                sqlDron,
                java.sql.Statement.RETURN_GENERATED_KEYS)) {

            // Datos del dron
            psDron.setString(1, drone.getSerial());
            psDron.setString(2, drone.getModelo());
            psDron.setString(3, drone.getFabricante());
            psDron.setInt(4, drone.getPeso());
            psDron.setString(5, drone.getClass().getSimpleName());

            // Insertar dron
            psDron.executeUpdate();

            // Obtener ID generado
            try (ResultSet rs = psDron.getGeneratedKeys()) {
                if (rs.next()) {
                    int idGenerado = rs.getInt(1);
                    drone.setId(idGenerado);
                    return idGenerado;
                }
            }
        }

        return -1;
    }

    /**
     * Busca un dron utilizando su identificador y lo instancia mediante el patrón Factory.
     *
     * @param id identificador único del dron.
     * @return objeto de subclase {@link Dron} (Agricultura o Vigilancia) o {@code null} si no existe.
     * @throws SQLException si ocurre un error de acceso a la base de datos.
     */
    public Dron buscar(int id) throws SQLException {

        String sql = "SELECT id, serial, modelo, fabricante, peso, tipo_dron "
                + "FROM dron WHERE id = ?";

        Connection conexion = Singleton.getInstance().getConexion();

        try (PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {

                    String tipoDron = rs.getString("tipo_dron");
                    DronFactory factory;

                    // Seleccionar la fábrica concreta según el registro de la BD
                    if ("AGRICOLA".equalsIgnoreCase(tipoDron) || "Agricultura".equalsIgnoreCase(tipoDron)) {
                        factory = new AgriculturaFactory();
                    } else if ("VIGILANTE".equalsIgnoreCase(tipoDron) || "Vigilancia".equalsIgnoreCase(tipoDron)) {
                        factory = new VigilanciaFactory();
                    } else {
                        return null;
                    }

                    // La fábrica genera la instancia concreta (Agricultura o Vigilancia)
                    Dron drone = factory.crearDron();

                    drone.setId(rs.getInt("id"));
                    drone.setSerial(rs.getString("serial"));
                    drone.setModelo(rs.getString("modelo"));
                    drone.setFabricante(rs.getString("fabricante"));
                    drone.setPeso(rs.getInt("peso"));

                    return drone;
                }
            }
        }

        return null;
    }

    /**
     * Obtiene todos los drones registrados.
     *
     * @return lista de objetos {@link Dron}.
     * @throws SQLException si ocurre un error de acceso a la base de datos.
     */
    public List<Dron> listar() throws SQLException {

        List<Dron> drones = new ArrayList<>();
        String sql = "SELECT id, serial, modelo, fabricante, peso FROM dron";

        Connection conexion = Singleton.getInstance().getConexion();

        try (PreparedStatement ps = conexion.prepareStatement(sql); 
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Dron drone = new Dron();
                drone.setId(rs.getInt("id"));
                drone.setSerial(rs.getString("serial"));
                drone.setModelo(rs.getString("modelo"));
                drone.setFabricante(rs.getString("fabricante"));
                drone.setPeso(rs.getInt("peso"));

                drones.add(drone);
            }
        }

        return drones;
    }

    /**
     * Actualiza la información de un dron.
     *
     * @param drone objeto {@link Dron} con los datos actualizados.
     * @throws SQLException si ocurre un error de acceso a la base de datos.
     */
    public void actualizar(Dron drone) throws SQLException {

        String sql = "UPDATE dron SET " 
                + "serial = ?, " 
                + "modelo = ?, " 
                + "fabricante = ?, " 
                + "peso = ?, " 
                + "tipo_dron = ? " 
                + "WHERE id = ?";

        Connection conexion = Singleton.getInstance().getConexion();

        try (PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setString(1, drone.getSerial());
            ps.setString(2, drone.getModelo());
            ps.setString(3, drone.getFabricante());
            ps.setInt(4, drone.getPeso());
            ps.setString(5, drone.getClass().getSimpleName()); // Guarda "Agricultura" o "Vigilancia"
            ps.setInt(6, drone.getId());

            ps.executeUpdate();
        }
    }

    /**
     * Elimina un dron de la base de datos.
     *
     * @param id identificador del dron.
     * @throws SQLException si ocurre un error de acceso a la base de datos.
     */
    public void eliminar(int id) throws SQLException {

        String sqlDron = "DELETE FROM dron WHERE id = ?";

        Connection conexion = Singleton.getInstance().getConexion();

        try (PreparedStatement psDron = conexion.prepareStatement(sqlDron)) {
            psDron.setInt(1, id);
            psDron.executeUpdate();
        }
    }
}