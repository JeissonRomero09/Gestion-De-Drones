package co.edu.poli.sw2.Dao;

import co.edu.poli.sw2.Service.Factory.AgriculturaFactory;
import co.edu.poli.sw2.Service.Factory.DronFactory;
import co.edu.poli.sw2.Service.Factory.VigilanciaFactory;
import co.edu.poli.sw2.Service.Singleton.Singleton;
import co.edu.poli.sw2.model.Agricultura;
import co.edu.poli.sw2.model.Dron;
import co.edu.poli.sw2.model.Vigilancia;

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
 * @version 3.0
 */
public class DronDao {

    /**
     * Registra un nuevo dron en la base de datos.
     *
     * @param drone objeto Dron con la información del dron.
     * @return identificador generado para el nuevo dron.
     * @throws SQLException si ocurre un error de acceso a la base de datos.
     */
    public int crear(Dron drone) throws SQLException {

        String sqlDron = "INSERT INTO dron "
                + "(serial, modelo, fabricante, peso) "
                + "VALUES (?, ?, ?, ?)";

        Connection conexion = Singleton.getInstance().getConexion();

        try (PreparedStatement psDron = conexion.prepareStatement(
                sqlDron,
                java.sql.Statement.RETURN_GENERATED_KEYS)) {

            // Datos comunes del dron
            psDron.setString(1, drone.getSerial());
            psDron.setString(2, drone.getModelo());
            psDron.setString(3, drone.getFabricante());
            psDron.setInt(4, drone.getPeso());

            // Insertar dron
            psDron.executeUpdate();

            // Obtener ID generado
            try (ResultSet rs = psDron.getGeneratedKeys()) {

                if (rs.next()) {

                    int idGenerado = rs.getInt(1);

                    drone.setId(idGenerado);

                    /*
                     * Si el objeto es Agricultura, se registra también
                     * en la tabla Agricultura.
                     */
                    if (drone instanceof Agricultura) {

                        Agricultura agricultura = (Agricultura) drone;

                        String sqlAgricultura =
                                "INSERT INTO agricultura "
                                + "(id, capacidad_tanque) "
                                + "VALUES (?, ?)";

                        try (PreparedStatement psAgricultura =
                                     conexion.prepareStatement(sqlAgricultura)) {

                            psAgricultura.setInt(1, idGenerado);
                            psAgricultura.setDouble(
                                    2,
                                    agricultura.getCapacidadTanque());

                            psAgricultura.executeUpdate();
                        }

                    /*
                     * Si el objeto es Vigilancia, se registra también
                     * en la tabla Vigilancia.
                     */
                    } else if (drone instanceof Vigilancia) {

                        Vigilancia vigilancia = (Vigilancia) drone;

                        String sqlVigilancia =
                                "INSERT INTO vigilancia "
                                + "(id, deteccion_termica) "
                                + "VALUES (?, ?)";

                        try (PreparedStatement psVigilancia =
                                     conexion.prepareStatement(sqlVigilancia)) {

                            psVigilancia.setInt(1, idGenerado);
                            psVigilancia.setBoolean(
                                    2,
                                    vigilancia.isDeteccionTermica());

                            psVigilancia.executeUpdate();
                        }
                    }

                    return idGenerado;
                }
            }
        }

        return -1;
    }

    /**
     * Busca un dron utilizando su identificador y lo instancia mediante
     * el patrón Factory.
     *
     * @param id identificador único del dron.
     * @return objeto de subclase {@link Dron} o {@code null} si no existe.
     * @throws SQLException si ocurre un error de acceso a la base de datos.
     */
    public Dron buscar(int id) throws SQLException {

        String sql = "SELECT id, serial, modelo, fabricante, peso "
                + "FROM dron WHERE id = ?";

        Connection conexion = Singleton.getInstance().getConexion();

        try (PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {

                    DronFactory factory = null;

                    /*
                     * Primero verificamos si el dron pertenece
                     * a Agricultura.
                     */
                    String sqlAgricultura =
                            "SELECT capacidad_tanque "
                            + "FROM agricultura WHERE id = ?";

                    try (PreparedStatement psAgricultura =
                                 conexion.prepareStatement(sqlAgricultura)) {

                        psAgricultura.setInt(1, id);

                        try (ResultSet rsAgricultura =
                                     psAgricultura.executeQuery()) {

                            if (rsAgricultura.next()) {

                                factory = new AgriculturaFactory();

                                Dron drone = factory.crearDron();

                                drone.setId(rs.getInt("id"));
                                drone.setSerial(rs.getString("serial"));
                                drone.setModelo(rs.getString("modelo"));
                                drone.setFabricante(rs.getString("fabricante"));
                                drone.setPeso(rs.getInt("peso"));

                                Agricultura agricultura =
                                        (Agricultura) drone;

                                agricultura.setCapacidadTanque(
                                        rsAgricultura.getDouble(
                                                "capacidad_tanque"));

                                return agricultura;
                            }
                        }
                    }

                    /*
                     * Si no es Agricultura, verificamos si pertenece
                     * a Vigilancia.
                     */
                    String sqlVigilancia =
                            "SELECT deteccion_termica "
                            + "FROM vigilancia WHERE id = ?";

                    try (PreparedStatement psVigilancia =
                                 conexion.prepareStatement(sqlVigilancia)) {

                        psVigilancia.setInt(1, id);

                        try (ResultSet rsVigilancia =
                                     psVigilancia.executeQuery()) {

                            if (rsVigilancia.next()) {

                                factory = new VigilanciaFactory();

                                Dron drone = factory.crearDron();

                                drone.setId(rs.getInt("id"));
                                drone.setSerial(rs.getString("serial"));
                                drone.setModelo(rs.getString("modelo"));
                                drone.setFabricante(rs.getString("fabricante"));
                                drone.setPeso(rs.getInt("peso"));

                                Vigilancia vigilancia =
                                        (Vigilancia) drone;

                                vigilancia.setDeteccionTermica(
                                        rsVigilancia.getBoolean(
                                                "deteccion_termica"));

                                return vigilancia;
                            }
                        }
                    }

                    /*
                     * Si existe en Dron pero no tiene una tabla hija,
                     * se devuelve como Dron.
                     */
                    Dron drone = new Dron();

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

                /*
                 * Se verifica el tipo concreto utilizando las tablas
                 * hijas mediante buscar().
                 */
                Dron drone = buscar(rs.getInt("id"));

                if (drone != null) {
                    drones.add(drone);
                }
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
                + "peso = ? "
                + "WHERE id = ?";

        Connection conexion = Singleton.getInstance().getConexion();

        try (PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setString(1, drone.getSerial());
            ps.setString(2, drone.getModelo());
            ps.setString(3, drone.getFabricante());
            ps.setInt(4, drone.getPeso());
            ps.setInt(5, drone.getId());

            ps.executeUpdate();
        }

        /*
         * Actualizar información específica de Agricultura.
         */
        if (drone instanceof Agricultura) {

            Agricultura agricultura = (Agricultura) drone;

            String sqlAgricultura =
                    "UPDATE agricultura SET "
                    + "capacidad_tanque = ? "
                    + "WHERE id = ?";

            try (PreparedStatement ps =
                         conexion.prepareStatement(sqlAgricultura)) {

                ps.setDouble(1, agricultura.getCapacidadTanque());
                ps.setInt(2, agricultura.getId());

                ps.executeUpdate();
            }

        /*
         * Actualizar información específica de Vigilancia.
         */
        } else if (drone instanceof Vigilancia) {

            Vigilancia vigilancia = (Vigilancia) drone;

            String sqlVigilancia =
                    "UPDATE vigilancia SET "
                    + "deteccion_termica = ? "
                    + "WHERE id = ?";

            try (PreparedStatement ps =
                         conexion.prepareStatement(sqlVigilancia)) {

                ps.setBoolean(1, vigilancia.isDeteccionTermica());
                ps.setInt(2, vigilancia.getId());

                ps.executeUpdate();
            }
        }
    }

    /**
     * Elimina un dron de la base de datos.
     *
     * @param id identificador del dron.
     * @throws SQLException si ocurre un error de acceso a la base de datos.
     */
    public void eliminar(int id) throws SQLException {

        Connection conexion = Singleton.getInstance().getConexion();

        /*
         * Primero se elimina de Agricultura.
         * Si no existe, simplemente no se elimina ningún registro.
         */
        String sqlAgricultura =
                "DELETE FROM agricultura WHERE id = ?";

        try (PreparedStatement psAgricultura =
                     conexion.prepareStatement(sqlAgricultura)) {

            psAgricultura.setInt(1, id);
            psAgricultura.executeUpdate();
        }

        /*
         * Después se elimina de Vigilancia.
         */
        String sqlVigilancia =
                "DELETE FROM vigilancia WHERE id = ?";

        try (PreparedStatement psVigilancia =
                     conexion.prepareStatement(sqlVigilancia)) {

            psVigilancia.setInt(1, id);
            psVigilancia.executeUpdate();
        }

        /*
         * Finalmente se elimina el registro de Dron.
         */
        String sqlDron =
                "DELETE FROM dron WHERE id = ?";

        try (PreparedStatement psDron =
                     conexion.prepareStatement(sqlDron)) {

            psDron.setInt(1, id);
            psDron.executeUpdate();
        }
    }
}