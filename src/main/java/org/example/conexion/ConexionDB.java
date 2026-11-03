package org.example.conexion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionDB {

    private static final String URL = "jdbc:mariadb://localhost:3306/tiendadb";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    // Método para utilizar la conexión desde los DAO
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }


    //Método para comprobar que se puede conectar
    public static void main(String[] args){
        try (Connection conexion = DriverManager.getConnection(URL,USER,PASSWORD)){
            System.out.println("Conexión exitosa a la base de datos");
        } catch (SQLException e){
            System.out.println("Error de conexión: "+ e.getMessage());
        }
    }
}
