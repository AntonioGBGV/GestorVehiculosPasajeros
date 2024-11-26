package database;

public interface SchemaDB {
    String DB_NAME = "coches";
    String TAB_COCHE = "coche";
    String COL_COCHE_MODELO = "modelo";
    String COL_COCHE_MARCA = "marca";
    String COL_COCHE_PRECIO = "precio";
    String COL_COCHE_ID =  "id";
    String TAB_PASAJERO = "pasajero";
    String COL_PASAJERO_ID = "id";
    String COL_PASAJERO_NOMBRE = "nombre";
    String COL_PASAJERO_EDAD = "edad";
    String COL_PASAJERO_PESO = "peso";
    String TAB_COCHEPASAJERO = "cochepasajero";
    String COL_COCHEPASAJERO_ID_COCHE = "id_coche";
    String COL_COCHEPASAJERO_ID_PASAJERO = "id_pasajero";

}
