package dao;

import database.DBConnection;
import database.SchemaDB;
import model.Coche;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CocheDAO {
    private Connection connection;
    private PreparedStatement preparedStatement;
    private ResultSet resultSet;

    public CocheDAO() {
        this.connection = new DBConnection().getConnection();
        if (this.connection == null){
            System.err.println("Error: no se ha establecido la conexión a la base de datos");
        }
    }

    public void addCoche(Coche coche) throws SQLException{
        preparedStatement = connection.prepareStatement(
                String.format("INSERT INTO %s (%s,%s,%s) VALUES (?,?,?)",
                        SchemaDB.TAB_COCHE,
                        SchemaDB.COL_COCHE_MODELO, SchemaDB.COL_COCHE_MARCA, SchemaDB.COL_COCHE_PRECIO));
        preparedStatement.setString(1,coche.getModelo());
        preparedStatement.setString(2,coche.getMarca());
        preparedStatement.setDouble(3,coche.getPrecio());
        preparedStatement.execute();
    }

    public void deleteCocheById(int id) throws SQLException {
        preparedStatement = connection.prepareStatement(
                String.format("DELETE FROM %s WHERE %s = (?)",
                        SchemaDB.TAB_COCHE, SchemaDB.COL_COCHE_ID));
        preparedStatement.setInt(1, id);
        preparedStatement.execute();
    }

    public Coche getCocheById(int id) throws SQLException {
        preparedStatement = connection.prepareStatement(
                String.format("SELECT * FROM %s WHERE %s = ?",
                        SchemaDB.TAB_COCHE, SchemaDB.COL_COCHE_ID));
        preparedStatement.setInt(1, id);
        resultSet = preparedStatement.executeQuery();

        if (resultSet.next()) {
            return new Coche(
                    resultSet.getInt(SchemaDB.COL_COCHE_ID),
                    resultSet.getString(SchemaDB.COL_COCHE_MODELO),
                    resultSet.getString(SchemaDB.COL_COCHE_MARCA),
                    resultSet.getInt(SchemaDB.COL_COCHE_PRECIO)
            );
        }
        return null;
    }

    public ArrayList<Coche> getAllCoches() throws SQLException {
        preparedStatement = connection.prepareStatement(
                String.format("SELECT * FROM %s", SchemaDB.TAB_COCHE));
        resultSet = preparedStatement.executeQuery();
        return getResultados(resultSet);
    }

    private ArrayList<Coche> getResultados(ResultSet datosResultados) throws SQLException {
        ArrayList<Coche> listaCoches = new ArrayList<>();
        while (datosResultados.next()) {
            int id = datosResultados.getInt(SchemaDB.COL_COCHE_ID);
            String modelo = datosResultados.getString(SchemaDB.COL_COCHE_MODELO);
            String marca = datosResultados.getString(SchemaDB.COL_COCHE_MARCA);
            double precio = datosResultados.getDouble(SchemaDB.COL_COCHE_PRECIO);
            listaCoches.add(mapearCoche(id, modelo, marca, precio));
        }
        return listaCoches;
    }

    private Coche mapearCoche(int id, String modelo, String marca, double precio) {
        return new Coche (id,modelo,marca,precio);
    }

    public void updateCocheById(Coche coche) throws SQLException {
        preparedStatement = connection.prepareStatement(
                String.format("UPDATE %s SET %s = ?, %s = ?, %s = ? WHERE %s = ?",
                        SchemaDB.TAB_COCHE,
                        SchemaDB.COL_COCHE_MODELO, SchemaDB.COL_COCHE_MARCA, SchemaDB.COL_COCHE_PRECIO, SchemaDB.COL_COCHE_ID));
        preparedStatement.setString(1, coche.getModelo());
        preparedStatement.setString(2, coche.getMarca());
        preparedStatement.setDouble(3, coche.getPrecio());
        preparedStatement.setInt(4, coche.getId());
        preparedStatement.execute();
    }

}
