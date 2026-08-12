package co.edu.poli.sw2.Dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionBD {

    private static final String URL = "jdbc:mysql://localhost:3306/sistema_drones";
    private static final String USUARIO = "root";
    private static final String PASSWORD = "";

    public static Connection conectar() {

        Connection conexion = null;

        try {
            conexion = DriverManager.getConnection(
                URL,
                USUARIO,
                PASSWORD
            );

            System.out.println("Conexion exitosa a la base de datos");

        } catch (SQLException e) {
            System.out.println("Error de conexion: " + e.getMessage());
        }

        return conexion;
    }
}