package dao;

import database.DBConnection;
import database.SchemaDB;
import model.Pasajero;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class PasajeroDAO {
    private Connection connection;
    private PreparedStatement preparedStatement;
    private ResultSet resultSet;

    public PasajeroDAO() {connection = new DBConnection().getConnection();}

    public void addPasajero(Pasajero pasajero) throws SQLException {
        preparedStatement = connection.prepareStatement(
                String.format("INSERT INTO %s (%s, %s, %s) VALUES (?, ?, ?)",
                        SchemaDB.TAB_PASAJERO,
                        SchemaDB.COL_PASAJERO_NOMBRE, SchemaDB.COL_PASAJERO_EDAD, SchemaDB.COL_PASAJERO_PESO));
        preparedStatement.setString(1, pasajero.getNombre());
        preparedStatement.setInt(2, pasajero.getEdad());
        preparedStatement.setDouble(3, pasajero.getPeso());
        preparedStatement.execute();
    }

    public void deletePasajeroById(int id) throws SQLException {
        preparedStatement = connection.prepareStatement(
                String.format("DELETE FROM %s WHERE %s = ?",
                        SchemaDB.TAB_PASAJERO, SchemaDB.COL_PASAJERO_ID));
        preparedStatement.setInt(1, id);
        preparedStatement.execute();
    }

    public Pasajero getPasajeroById(int id) throws SQLException {
        preparedStatement = connection.prepareStatement(
                String.format("SELECT * FROM %s WHERE %s = ?",
                        SchemaDB.TAB_PASAJERO, SchemaDB.COL_PASAJERO_ID));
        preparedStatement.setInt(1, id);
        resultSet = preparedStatement.executeQuery();

        if (resultSet.next()) {
            return new Pasajero(
                    resultSet.getInt(SchemaDB.COL_PASAJERO_ID),
                    resultSet.getString(SchemaDB.COL_PASAJERO_NOMBRE),
                    resultSet.getInt(SchemaDB.COL_PASAJERO_EDAD),
                    resultSet.getDouble(SchemaDB.COL_PASAJERO_PESO)
            );
        }
        return null;
    }

    public ArrayList<Pasajero> getAllPasajeros() throws SQLException {
        preparedStatement = connection.prepareStatement(
                String.format("SELECT * FROM %s", SchemaDB.TAB_PASAJERO));
        resultSet = preparedStatement.executeQuery();
        return getResultados(resultSet);

    }
    private ArrayList<Pasajero> getResultados(ResultSet datosResultados) throws SQLException {
        ArrayList<Pasajero> listaPasajeros = new ArrayList<>();
        while (datosResultados.next()) {
            int id = datosResultados.getInt(SchemaDB.COL_PASAJERO_ID);
            String nombre = datosResultados.getString(SchemaDB.COL_PASAJERO_NOMBRE);
            int edad = datosResultados.getInt(SchemaDB.COL_PASAJERO_EDAD);
            double peso = datosResultados.getDouble(SchemaDB.COL_PASAJERO_PESO);
            listaPasajeros.add(mapearPasajero(id, nombre, edad, peso));
        }
        return listaPasajeros;
    }

    private Pasajero mapearPasajero(int id, String nombre, int edad, double peso) {
        return new Pasajero (id,nombre,edad,peso);
    }
}
