package co.edu.poli.sw2.Dao;

import co.edu.poli.sw2.Service.Factory.AgriculturaFactory;
import co.edu.poli.sw2.Service.Factory.DronFactory;
import co.edu.poli.sw2.Service.Factory.VigilanciaFactory;
import co.edu.poli.sw2.Service.Singleton.Singleton;
import co.edu.poli.sw2.model.Agricultura;
import co.edu.poli.sw2.model.Dron;
import co.edu.poli.sw2.model.Sensores;
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
 * <p>Esta clase maneja el flujo completo de CRUD para la entidad Dron, incluyendo
 * la creación, búsqueda, actualización y eliminación de drones, así como la gestión
 * de la relación con los sensores asociados.</p>
 *
 * @author Jeisson Romero
 * @version 3.0
 */
public class DronDao {
    
    /**
     * Verifica si un sensor existe en la base de datos.
     *
     * @param conexion conexión activa a la base de datos.
     * @param sensorId identificador del sensor a validar.
     * @return true si el sensor existe; false en caso contrario.
     * @throws SQLException si ocurre un error de acceso a la base de datos.
     */
    private boolean existeSensor(Connection conexion, int sensorId) throws SQLException {
        String sql = "SELECT 1 FROM sensores WHERE id = ? LIMIT 1";

        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setInt(1, sensorId);

            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        }
    }

    /**
     * Asocia uno o varios sensores a un dron, desvinculando primero los sensores
     * que ya estaban asociados y validando la existencia de cada ID indicado.
     *
     * @param dronId identificador del dron al que se asociarán los sensores.
     * @param sensorIds lista de IDs de sensores a asociar.
     * @throws SQLException si ocurre un error de acceso a la base de datos.
     * @throws IllegalArgumentException si alguno de los IDs no existe.
     */
    private void asociarSensores(Connection conexion, int dronId, List<Integer> sensorIds) throws SQLException {
        List<Integer> ids = new ArrayList<>();

        if (sensorIds != null) {
            for (Integer sensorId : sensorIds) {
                if (sensorId != null) {
                    ids.add(sensorId);
                }
            }
        }

        String sqlDesvincular = "UPDATE sensores SET dron_id = NULL WHERE dron_id = ?";
        try (PreparedStatement psDesvincular = conexion.prepareStatement(sqlDesvincular)) {
            psDesvincular.setInt(1, dronId);
            psDesvincular.executeUpdate();
        }

        if (ids.isEmpty()) {
            return;
        }

        List<Integer> inexistentes = new ArrayList<>();
        for (Integer sensorId : ids) {
            if (!existeSensor(conexion, sensorId)) {
                inexistentes.add(sensorId);
            }
        }

        if (!inexistentes.isEmpty()) {
            throw new IllegalArgumentException(
                    "Los siguientes IDs de sensores no existen: " + inexistentes);
        }

        String sqlAsociar = "UPDATE sensores SET dron_id = ? WHERE id = ?";
        try (PreparedStatement psAsociar = conexion.prepareStatement(sqlAsociar)) {
            for (Integer sensorId : ids) {
                psAsociar.setInt(1, dronId);
                psAsociar.setInt(2, sensorId);
                psAsociar.executeUpdate();
            }
        }
    }

    /**
     * Obtiene los sensores asociados a un dron desde la tabla sensores.
     *
     * @param dronId identificador del dron.
     * @return lista de sensores del dron; nunca null.
     * @throws SQLException si ocurre un error de acceso a la base de datos.
     */
    private List<Sensores> obtenerSensoresPorDron(int dronId) throws SQLException {
        List<Sensores> sensores = new ArrayList<>();

        String sql = "SELECT id, tipo, fabricante, dron_id FROM sensores WHERE dron_id = ?";
        Connection conexion = Singleton.getInstance().getConexion();

        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setInt(1, dronId);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Sensores sensor = new Sensores();
                    sensor.setId(rs.getInt("id"));
                    sensor.setTipo(rs.getString("tipo"));
                    sensor.setFabricante(rs.getString("fabricante"));
                    sensor.setDronId(rs.getObject("dron_id") != null ? rs.getInt("dron_id") : null);
                    sensores.add(sensor);
                }
            }
        }

        return sensores;
    }

    /**
     * Registra un nuevo dron en la base de datos y, si se indican sensores,
     * asocia los IDs de sensores existentes a la relación {@code dron.id -> sensores.dron_id}.
     *
     * <p>Este método realiza la inserción del dron y luego actualiza la tabla
     * {@code sensores} para vincular cada sensor existente al nuevo dron.</p>
     *
     * @param drone objeto {@link Dron} con la información del dron a crear.
     * @return identificador generado para el nuevo dron.
     * @throws SQLException si ocurre un error de acceso a la base de datos.
     * @throws IllegalArgumentException si alguno de los sensores indicados no existe.
     */
    public int crear(Dron drone) throws SQLException {
        return crear(drone, null);
    }

    /**
     * Registra un nuevo dron y asocia los sensores indicados mediante sus IDs.
     *
     * <p>La asociación se almacena mediante la clave foránea {@code sensores.dron_id}
     * para mantener la relación uno a muchos entre un dron y varios sensores.</p>
     *
     * @param drone objeto {@link Dron} con la información del dron.
     * @param sensorIds lista de IDs de sensores existentes que se asociarán al dron.
     * @return identificador generado para el nuevo dron.
     * @throws SQLException si ocurre un error de acceso a la base de datos.
     * @throws IllegalArgumentException si alguno de los IDs de sensores no existe.
     */
    public int crear(Dron drone, List<Integer> sensorIds) throws SQLException {
        Connection conexion = Singleton.getInstance().getConexion();
        boolean autoCommitOriginal = conexion.getAutoCommit();

        try {
            conexion.setAutoCommit(false);

            String sqlDron = "INSERT INTO dron "
                    + "(serial, modelo, fabricante, peso) "
                    + "VALUES (?, ?, ?, ?)";

            try (PreparedStatement psDron = conexion.prepareStatement(
                    sqlDron,
                    java.sql.Statement.RETURN_GENERATED_KEYS)) {

                psDron.setString(1, drone.getSerial());
                psDron.setString(2, drone.getModelo());
                psDron.setString(3, drone.getFabricante());
                psDron.setInt(4, drone.getPeso());
                psDron.executeUpdate();

                try (ResultSet rs = psDron.getGeneratedKeys()) {
                    if (rs.next()) {
                        int idGenerado = rs.getInt(1);
                        drone.setId(idGenerado);

                        if (drone instanceof Agricultura) {
                            Agricultura agricultura = (Agricultura) drone;
                            String sqlAgricultura =
                                    "INSERT INTO agricultura "
                                    + "(id, capacidad_tanque) "
                                    + "VALUES (?, ?)";

                            try (PreparedStatement psAgricultura =
                                         conexion.prepareStatement(sqlAgricultura)) {
                                psAgricultura.setInt(1, idGenerado);
                                psAgricultura.setDouble(2, agricultura.getCapacidadTanque());
                                psAgricultura.executeUpdate();
                            }
                        } else if (drone instanceof Vigilancia) {
                            Vigilancia vigilancia = (Vigilancia) drone;
                            String sqlVigilancia =
                                    "INSERT INTO vigilancia "
                                    + "(id, deteccion_termica) "
                                    + "VALUES (?, ?)";

                            try (PreparedStatement psVigilancia =
                                         conexion.prepareStatement(sqlVigilancia)) {
                                psVigilancia.setInt(1, idGenerado);
                                psVigilancia.setBoolean(2, vigilancia.isDeteccionTermica());
                                psVigilancia.executeUpdate();
                            }
                        }

                        asociarSensores(conexion, idGenerado, sensorIds);
                        conexion.commit();
                        return idGenerado;
                    }
                }
            }

            conexion.rollback();
            return -1;
        } catch (IllegalArgumentException e) {
            conexion.rollback();
            throw e;
        } catch (SQLException e) {
            conexion.rollback();
            throw e;
        } finally {
            conexion.setAutoCommit(autoCommitOriginal);
        }
    }

    /**
     * Busca un dron por su identificador y carga además los sensores asociados.
     *
     * <p>La búsqueda devuelve el dron junto con su lista de sensores cargada desde
     * la tabla {@code sensores} por la relación {@code dron_id = ?}.</p>
     *
     * @param id identificador del dron a consultar.
     * @return instancia del dron encontrado, o null si no existe.
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

                                agricultura.setSensores(obtenerSensoresPorDron(agricultura.getId()));

                                return agricultura;
                            }
                        }
                    }

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

                                vigilancia.setSensores(obtenerSensoresPorDron(vigilancia.getId()));

                                return vigilancia;
                            }
                        }
                    }

                    Dron drone = new Dron();

                    drone.setId(rs.getInt("id"));
                    drone.setSerial(rs.getString("serial"));
                    drone.setModelo(rs.getString("modelo"));
                    drone.setFabricante(rs.getString("fabricante"));
                    drone.setPeso(rs.getInt("peso"));
                    drone.setSensores(obtenerSensoresPorDron(drone.getId()));

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
     * Actualiza la información base del dron y sincroniza la relación con sensores.
     *
     * <p>Al actualizar, se conservan los datos del dron, se desasocian los sensores
     * previos y se vuelven a enlazar los nuevos sensores indicados por su identificador.
     * Los sensores que ya no estén en la nueva lista quedan con {@code dron_id = NULL}.</p>
     *
     * @param drone dron con la información actualizada.
     * @param sensorIds lista de IDs de sensores que quedarán asociados al dron.
     * @throws SQLException si ocurre un error de acceso a la base de datos.
     * @throws IllegalArgumentException si alguno de los IDs de sensores no existe.
     */
    public void actualizar(Dron drone, List<Integer> sensorIds) throws SQLException {
        Connection conexion = Singleton.getInstance().getConexion();
        boolean autoCommitOriginal = conexion.getAutoCommit();

        try {
            conexion.setAutoCommit(false);

            String sql = "UPDATE dron SET "
                    + "serial = ?, "
                    + "modelo = ?, "
                    + "fabricante = ?, "
                    + "peso = ? "
                    + "WHERE id = ?";

            try (PreparedStatement ps = conexion.prepareStatement(sql)) {
                ps.setString(1, drone.getSerial());
                ps.setString(2, drone.getModelo());
                ps.setString(3, drone.getFabricante());
                ps.setInt(4, drone.getPeso());
                ps.setInt(5, drone.getId());
                ps.executeUpdate();
            }

            if (drone instanceof Agricultura) {
                Agricultura agricultura = (Agricultura) drone;
                String sqlAgricultura =
                        "UPDATE agricultura SET "
                        + "capacidad_tanque = ? "
                        + "WHERE id = ?";

                try (PreparedStatement ps = conexion.prepareStatement(sqlAgricultura)) {
                    ps.setDouble(1, agricultura.getCapacidadTanque());
                    ps.setInt(2, agricultura.getId());
                    ps.executeUpdate();
                }

            } else if (drone instanceof Vigilancia) {
                Vigilancia vigilancia = (Vigilancia) drone;
                String sqlVigilancia =
                        "UPDATE vigilancia SET "
                        + "deteccion_termica = ? "
                        + "WHERE id = ?";

                try (PreparedStatement ps = conexion.prepareStatement(sqlVigilancia)) {
                    ps.setBoolean(1, vigilancia.isDeteccionTermica());
                    ps.setInt(2, vigilancia.getId());
                    ps.executeUpdate();
                }
            }

            asociarSensores(conexion, drone.getId(), sensorIds);
            conexion.commit();
        } catch (IllegalArgumentException e) {
            conexion.rollback();
            throw e;
        } catch (SQLException e) {
            conexion.rollback();
            throw e;
        } finally {
            conexion.setAutoCommit(autoCommitOriginal);
        }
    }

    /**
     * Elimina un dron y desvincula todos sus sensores, sin borrar los registros de la tabla sensores.
     *
     * <p>La eliminación mantiene la integridad de la relación uno a muchos al dejar
     * {@code sensores.dron_id = NULL} para los sensores asociados y luego borrar el dron.</p>
     *
     * @param id identificador del dron a eliminar.
     * @throws SQLException si ocurre un error de acceso a la base de datos.
     */
    public void eliminar(int id) throws SQLException {
        Connection conexion = Singleton.getInstance().getConexion();
        boolean autoCommitOriginal = conexion.getAutoCommit();

        try {
            conexion.setAutoCommit(false);

            String sqlDesvincular = "UPDATE sensores SET dron_id = NULL WHERE dron_id = ?";
            try (PreparedStatement psDesvincular = conexion.prepareStatement(sqlDesvincular)) {
                psDesvincular.setInt(1, id);
                psDesvincular.executeUpdate();
            }

            String sqlAgricultura = "DELETE FROM agricultura WHERE id = ?";
            try (PreparedStatement psAgricultura = conexion.prepareStatement(sqlAgricultura)) {
                psAgricultura.setInt(1, id);
                psAgricultura.executeUpdate();
            }

            String sqlVigilancia = "DELETE FROM vigilancia WHERE id = ?";
            try (PreparedStatement psVigilancia = conexion.prepareStatement(sqlVigilancia)) {
                psVigilancia.setInt(1, id);
                psVigilancia.executeUpdate();
            }

            String sqlDron = "DELETE FROM dron WHERE id = ?";
            try (PreparedStatement psDron = conexion.prepareStatement(sqlDron)) {
                psDron.setInt(1, id);
                psDron.executeUpdate();
            }

            conexion.commit();
        } catch (SQLException e) {
            conexion.rollback();
            throw e;
        } finally {
            conexion.setAutoCommit(autoCommitOriginal);
        }
    }
}