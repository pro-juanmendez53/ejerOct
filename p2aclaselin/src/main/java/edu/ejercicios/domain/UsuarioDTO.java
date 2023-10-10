package edu.ejercicios.domain;

public class UsuarioDTO
{
    private int id_usuario;
    private String username;
    private String password;
//creamos nuestro constructor vacio
    public UsuarioDTO()
    {

    }
//nuestro constructor con parametros
    public UsuarioDTO(int id_usuario, String username, String password)
    {
        this.id_usuario=id_usuario;
        this.username=username;
        this.password=password;
    }

    //GETTERS AND SETTERS
    public int getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(int id_usuario) {
        this.id_usuario = id_usuario;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "Usuario{" + "id_usuario=" + id_usuario + ", username=" + username + ", password=" + password + '}';
    }


}
