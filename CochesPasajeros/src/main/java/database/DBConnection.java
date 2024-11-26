package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    private static Connection connection;

    public Connection getConnection() {
        if (connection == null) {
            newConnection();
        }
        return connection;
    }

    private void newConnection() {
        String url = "jdbc:mysql://127.0.0.1:3306/" + SchemaDB.DB_NAME;
        try {
            connection = DriverManager.getConnection(url, "root", "");
            System.out.println("Conexión creada correctamente.");
        } catch (SQLException e) {
            System.err.println("Error en la conexión a la base de datos: " + e.getMessage());
        }
    }

    public void closeConnection() {
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            System.err.println("Error al cerrar la conexión: " + e.getMessage());
        } finally {
            connection = null;
        }
    }
}
