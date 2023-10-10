package edu.ejercicios.datos;

import edu.ejercicios.domain.UsuarioDTO;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;



public class UsuarioJDBC
{

    private Connection conexionTransaccional;  //TENEMOS NUESTRA VARIABLE PARA ALMACENAR NUESTRA CONEXION A LA BASE DE DATOS

    //LAS SIGUIENTES LINEAS DE CODIGO SON VARIABLES PARA ALMACENAR NUESTRAS DIFERENTES CONSULTAS DE SQL

    private static final String SQL_SELECT = "SELECT id_usuario, username, password FROM usuario";
    private static final String SQL_INSERT = "INSERT INTO usuario(username, password) VALUES(?, ?)";
    private static final String SQL_UPDATE = "UPDATE usuario SET username=?, password=? WHERE id_usuario = ?";
    private static final String SQL_DELETE = "DELETE FROM usuario WHERE id_usuario=?";

    //ACA CREAMOS UN CONSTRUCTORS VACIO
    public UsuarioJDBC() {

    }

//EL SIGUIENTE ES UN CONSTRUCTOR YA CON PARAMETROS
    public UsuarioJDBC(Connection conexionTransaccional) {
        this.conexionTransaccional = conexionTransaccional;
    }
//a continuacion tenemos nuestra funcion select
    public List<UsuarioDTO> select() throws SQLException {
        Connection conn = null;        //es nuestra variable que almacena la conexion a la base de datos, y se inicializa en null
        PreparedStatement stmt = null;   //es nuestra variable que prepara la consulta
        ResultSet rs = null;   //y esta variable almacenara los datos de la consulta
        UsuarioDTO usuario = null;    //es nuestra variable del tipo UsuarioDTO que almacenara los datos de usuario que seleccionaremos
        List<UsuarioDTO> usuarios = new ArrayList<UsuarioDTO>();    //es nuestra variable tipo list que almacenara la lista de objetos seleccionados
        try {
            conn = this.conexionTransaccional != null ? this.conexionTransaccional : Conexion.getConnection();
            stmt = conn.prepareStatement(SQL_SELECT);    //esta variable prepara la consulta y conecta con la base de datos
            rs = stmt.executeQuery();    //ejecuta la consulta y el executequery obtiene los resultados
            while (rs.next()) {
                int id_usuario = rs.getInt("id_usuario");
                String username = rs.getString("username");
                String password = rs.getString("password");

                usuario = new UsuarioDTO();        //creamos un objeto UsuarioDTO y lo llamamos usuario para almacenar los datos del registro actual
                //las siguientes 3 lineas de codigo son para establecer los datos de los parametros en el objeto usuario
                usuario.setId_usuario(id_usuario);
                usuario.setUsername(username);
                usuario.setPassword(password);

                usuarios.add(usuario);    //agregamos el objeto usuario a la lista
            }
        } finally { //las siguientes lineas ciereran sus respectivas consultas de la base de datos (resultados y preparacion)
            Conexion.close(rs);
            Conexion.close(stmt);
            if (this.conexionTransaccional == null) {
                Conexion.close(conn);  //cerramos la conexion a la base de datos
            }
        }

        return usuarios;
    }

    public int insert(UsuarioDTO usuario) throws SQLException {
        Connection conn = null;
        PreparedStatement stmt = null;
        int rows = 0;
        try {
            conn = this.conexionTransaccional != null ? this.conexionTransaccional : Conexion.getConnection();
    //las siguientes lineas de codigo las utilizamos para establecer el valor del parametro de nuestra consulta
    //
            stmt = conn.prepareStatement(SQL_INSERT);
            stmt.setString(1, usuario.getUsername());
            stmt.setString(2, usuario.getPassword());

            System.out.println("ejecutando query:" + SQL_INSERT);
            rows = stmt.executeUpdate();
            System.out.println("Registros afectados:" + rows);
        } finally {
            Conexion.close(stmt);
            if (this.conexionTransaccional == null) {
                Conexion.close(conn);
            }
        }

        return rows;
    }

    public int update(UsuarioDTO usuario) throws SQLException {     //es nuestra funcion para actualizar y toma de parametro un objeto del tipo UsuarioDTO
        Connection conn = null;
        PreparedStatement stmt = null;
        int rows = 0;
        try {
            conn = this.conexionTransaccional != null ? this.conexionTransaccional : Conexion.getConnection();
            System.out.println("ejecutando query: " + SQL_UPDATE);
            stmt = conn.prepareStatement(SQL_UPDATE);
            //establecen el valor del parametro de nuestra consulta:
            stmt.setString(1, usuario.getUsername());
            stmt.setString(2, usuario.getPassword());
            stmt.setInt(3, usuario.getId_usuario());

            rows = stmt.executeUpdate();
            System.out.println("Registros actualizado:" + rows);
        } finally {
            Conexion.close(stmt);
            if (this.conexionTransaccional == null) {
                Conexion.close(conn);
            }
        }

        return rows;
    }

    public int delete(UsuarioDTO usuario) throws SQLException {   //nuestra funcoin borrar, que toma como parametro un objeto del tipo UsuarioDTO
        Connection conn = null;
        PreparedStatement stmt = null;
        int rows = 0;
        try {
            conn = this.conexionTransaccional != null ? this.conexionTransaccional : Conexion.getConnection();
            System.out.println("Ejecutando query:" + SQL_DELETE);
            stmt = conn.prepareStatement(SQL_DELETE);
            stmt.setInt(1, usuario.getId_usuario());
            rows = stmt.executeUpdate();
            System.out.println("Registros eliminados:" + rows);
        } finally {
            Conexion.close(stmt);
            if (this.conexionTransaccional == null) {
                Conexion.close(conn);
            }
        }

        return rows;
    }


}

//PRUEBA SOBRE LA ENCRIPTACION CONTRASEÑA
//    private String encriptarContraseña(String contraseña) {
//        try {
//            MessageDigest md = MessageDigest.getInstance("SHA-512");
//            md.update(contraseña.getBytes());
//            byte[] mb = md.digest();
//            String encriptado = "";
//            for (int i = 0; i < mb.length; i++) {
//                encriptado += Integer.toHexString(mb[i]);
//            }
//            return encriptado;
//        } catch (NoSuchAlgorithmException ex) {
//            ex.printStackTrace(System.out);
//            return null;
//        }
//    }


//la funcion tomara como parametro una contraseña como entrada, calcula su hash utilizando el algoritmo SHA-512
// y devuelve el hash en forma de cadena hexadecimal. Nos sirve para almacenar de
// manera segura en una base de datos una contra, ya que el hash es irreversible y difícil de descifrar.


