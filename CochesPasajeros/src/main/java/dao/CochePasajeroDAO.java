package dao;

import database.DBConnection;
import database.SchemaDB;
import model.Pasajero;

import java.sql.*;
import java.util.ArrayList;

public class CochePasajeroDAO {

    private Connection connection;
    private PreparedStatement preparedStatement;
    private ResultSet resultSet;

    public CochePasajeroDAO() {
        this.connection = new DBConnection().getConnection();
        if (this.connection == null){
            System.err.println("Error: no se ha establecido la conexión a la base de datos");
        }
    }

    public void addPasajeroCoche(int idCoche, int idPasajero) throws SQLException {
        preparedStatement = connection.prepareStatement(
                String.format("INSERT INTO %s (%s, %s) VALUES (?, ?)",
                        SchemaDB.TAB_COCHEPASAJERO, SchemaDB.COL_COCHEPASAJERO_ID_COCHE, SchemaDB.COL_COCHEPASAJERO_ID_PASAJERO));
        preparedStatement.setInt(1, idCoche);
        preparedStatement.setInt(2, idPasajero);
        preparedStatement.execute();
        System.out.println("Pasajero con ID " + idPasajero + " añadido al coche con ID " + idCoche);
    }

    public void removePasajeroCoche(int idCoche, int idPasajero) throws SQLException {
        preparedStatement = connection.prepareStatement(
                String.format("DELETE FROM %s WHERE %s = ? AND %s = ?",
                        SchemaDB.TAB_COCHEPASAJERO, SchemaDB.COL_COCHEPASAJERO_ID_COCHE, SchemaDB.COL_COCHEPASAJERO_ID_PASAJERO));
        preparedStatement.setInt(1, idCoche);
        preparedStatement.setInt(2, idPasajero);
        int rowsDeleted = preparedStatement.executeUpdate();
        if (rowsDeleted > 0) {
            System.out.println("El pasajero con ID " + idPasajero + " se ha eliminado del coche con ID " + idCoche);
        } else {
            System.out.println("No se encontró relación entre el coche con ID " + idCoche + " y el pasajero con ID " + idPasajero);
        }
    }

    public ArrayList<Pasajero> getPasajerosByCoche(int idCoche) throws SQLException {
        preparedStatement = connection.prepareStatement(
                String.format("SELECT p.* FROM %s cp JOIN %s p ON cp.%s = p.%s WHERE cp.%s = ?",
                        SchemaDB.TAB_COCHEPASAJERO,
                        SchemaDB.TAB_PASAJERO,
                        SchemaDB.COL_COCHEPASAJERO_ID_PASAJERO,
                        SchemaDB.COL_PASAJERO_ID,
                        SchemaDB.COL_COCHEPASAJERO_ID_COCHE));
        preparedStatement.setInt(1, idCoche);
        resultSet = preparedStatement.executeQuery();
        return getResultados(resultSet);
    }

    private ArrayList<Pasajero> getResultados(ResultSet datosResultados) throws SQLException {
        ArrayList<Pasajero> listaResultado = new ArrayList<>();
        while (datosResultados.next()) {
            int id = resultSet.getInt(SchemaDB.COL_PASAJERO_ID);
            String nombre = resultSet.getString(SchemaDB.COL_PASAJERO_NOMBRE);
            int edad = resultSet.getInt(SchemaDB.COL_PASAJERO_EDAD);
            double peso = resultSet.getDouble(SchemaDB.COL_PASAJERO_PESO);
            listaResultado.add(mapearPasajero(id, nombre, edad, peso));
        }
        return listaResultado;
    }

    private Pasajero mapearPasajero(int id, String nombre, int edad, double peso) {
        return new Pasajero(id, nombre, edad, peso);
    }

}
