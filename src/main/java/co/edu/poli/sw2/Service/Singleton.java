package co.edu.poli.sw2.Service;

import java.sql.Connection;
import java.sql.SQLException;
import co.edu.poli.sw2.Dao.ConexionBD;

/**
 * Clase Singleton para la gestión centralizada de servicios e interacción 
 * con la base de datos.
 */
public class Singleton {

    // Única instancia de la clase Singleton (Patrón Singleton)
    private static Singleton singleton;

    // Conexión activa a la base de datos
    private Connection conexion;

    /**
     * Constructor privado para evitar instanciación externa.
     * Inicializa la conexión con la base de datos.
     */
    private Singleton() {
        try {
            this.conexion = ConexionBD.conectar();
            System.out.println("Conexión a la base de datos establecida exitosamente.");
        } catch (SQLException e) {
            System.err.println("Error al conectar con la base de datos: " + e.getMessage());
        }
    }

    /**
     * Método estático para obtener la instancia única del Singleton.
     *
     * @return Instancia única de Singleton.
     */
    public static Singleton getInstance() {
        if (singleton == null) {
            singleton = new Singleton();
        }
        return singleton;
    }

    /**
     * Obtiene la conexión actual a la base de datos.
     * Si la conexión está cerrada o es nula, intenta reconectarla.
     *
     * @return Objeto {@link Connection} a la base de datos.
     * @throws SQLException Si ocurre un error al reconectar.
     */
    public Connection getConexion() throws SQLException {
        if (this.conexion == null || this.conexion.isClosed()) {
            this.conexion = ConexionBD.conectar();
        }
        return this.conexion;
    }

    /**
     * Cierra la conexión a la base de datos de manera segura.
     */
    public void cerrarConexion() {
        if (this.conexion != null) {
            try {
                this.conexion.close();
                System.out.println("Conexión a la base de datos cerrada.");
            } catch (SQLException e) {
                System.err.println("Error al cerrar la conexión: " + e.getMessage());
            }
        }
    }
}