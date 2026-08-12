package co.edu.poli.sw2.Dao;

import co.edu.poli.sw2.Dao.ConexionBD;
import co.edu.poli.sw2.model.Dron;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DronDao {

    // CREAR
    public void crear(Dron drone) {

        String sql = "INSERT INTO dron "
                + "(serial, modelo, fabricante, peso, piloto_id, mision_id) "
                + "VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conexion = ConexionBD.conectar();
             PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setString(1, drone.getSerial());
            ps.setString(2, drone.getModelo());
            ps.setString(3, drone.getFabricante());
            ps.setDouble(4, drone.getPeso());
            ps.setInt(5, drone.getPilotoId());
            ps.setInt(6, drone.getMisionId());

            ps.executeUpdate();

            System.out.println("Dron creado correctamente.");

        } catch (SQLException e) {
            System.out.println("Error al crear el dron: " + e.getMessage());
        }
    }

    // BUSCAR
    public Dron buscar(int id) {

        String sql = "SELECT * FROM dron WHERE id = ?";

        try (Connection conexion = ConexionBD.conectar();
             PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                Dron drone = new Dron();

                drone.setId(rs.getInt("id"));
                drone.setSerial(rs.getString("serial"));
                drone.setModelo(rs.getString("modelo"));
                drone.setFabricante(rs.getString("fabricante"));
                drone.setPeso(rs.getDouble("peso"));
                drone.setPilotoId(rs.getInt("piloto_id"));
                drone.setMisionId(rs.getInt("mision_id"));

                return drone;
            }

        } catch (SQLException e) {
            System.out.println("Error al buscar el dron: " + e.getMessage());
        }

        return null;
    }

    // LISTAR
    public List<Dron> listar() {

        List<Dron> drones = new ArrayList<>();

        String sql = "SELECT * FROM dron";

        try (Connection conexion = ConexionBD.conectar();
             PreparedStatement ps = conexion.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {

                Dron drone = new Dron();

                drone.setId(rs.getInt("id"));
                drone.setSerial(rs.getString("serial"));
                drone.setModelo(rs.getString("modelo"));
                drone.setFabricante(rs.getString("fabricante"));
                drone.setPeso(rs.getDouble("peso"));
                drone.setPilotoId(rs.getInt("piloto_id"));
                drone.setMisionId(rs.getInt("mision_id"));

                drones.add(drone);
            }

        } catch (SQLException e) {
            System.out.println("Error al listar los drones: " + e.getMessage());
        }

        return drones;
    }

    // ACTUALIZAR
    public void actualizar(Dron drone) {

        String sql = "UPDATE dron SET "
                + "serial = ?, "
                + "modelo = ?, "
                + "fabricante = ?, "
                + "peso = ?, "
                + "piloto_id = ?, "
                + "mision_id = ? "
                + "WHERE id = ?";

        try (Connection conexion = ConexionBD.conectar();
             PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setString(1, drone.getSerial());
            ps.setString(2, drone.getModelo());
            ps.setString(3, drone.getFabricante());
            ps.setDouble(4, drone.getPeso());
            ps.setInt(5, drone.getPilotoId());
            ps.setInt(6, drone.getMisionId());
            ps.setInt(7, drone.getId());

            ps.executeUpdate();

            System.out.println("Dron actualizado correctamente.");

        } catch (SQLException e) {
            System.out.println("Error al actualizar el dron: " + e.getMessage());
        }
    }

    // ELIMINAR
    public void eliminar(int id) {

        String sql = "DELETE FROM dron WHERE id = ?";

        try (Connection conexion = ConexionBD.conectar();
             PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setInt(1, id);

            ps.executeUpdate();

            System.out.println("Dron eliminado correctamente.");

        } catch (SQLException e) {
            System.out.println("Error al eliminar el dron: " + e.getMessage());
        }
    }
}