package edu.ejercicios.datos;

import java.sql.SQLException;
import java.sql.*;

public class Conexion
{
    //jbdc:mysql//localhost:3306
//    private static final String JDBC_URL = "jdbc:mysql://localhost/p2a?useSSL=false&serverTimezone=UTC";
//    private static final String JDBC_USER = "root";
//    private static final String JDBC_PASS = "";

    //cra la conexion a la base de datos
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASS);
    }

    //cierra la conexion al resultset
    public static void close(ResultSet rs){
        try {
            rs.close();
        } catch (SQLException ex) {
            ex.printStackTrace(System.out);
        }
    }

    //cierra la conexion al statement
    public static void close(PreparedStatement stmt){
        try {
            stmt.close();
        } catch (SQLException ex) {
            ex.printStackTrace(System.out);
        }
    }

    //cierra la conexion a la base de datos
    public static void close(Connection conn){
        try {
            conn.close();
        } catch (SQLException ex) {
            ex.printStackTrace(System.out);
        }
    }



}
