package com.bufeotec.sipcsi.Models;

public class Usuario {

    private String usuario_nickname;
    private String usuario_contrasenha;
    private String usuario_id;
    private String distrito_id;
    private String email;
    private String distrito_nombre;
    private String usuario_nombre;
    private String usuario_apellido;
    private String usuario_dni;
    private String rol_id;
    private String vehiculo;


    public Usuario(String usuario_id, String rol_id, String token, String usuario_contrasenha, String usuario_nickname, String distrito_id, String distrito_nombre) {
        this.usuario_id=usuario_id;
        this.rol_id=rol_id;
        this.token=token;
        this.usuario_contrasenha=usuario_contrasenha;
        this.usuario_nickname=usuario_nickname;
        this.distrito_id=distrito_id;
        this.distrito_nombre=distrito_nombre;
    }

    public Usuario(String usuario_id, String rol_id, String token, String usuario_contrasenha, String usuario_nickname, String distrito_id, String distrito_nombre, String usuario_nombre , String vehiculo) {
        this.usuario_id=usuario_id;
        this.rol_id=rol_id;
        this.token=token;
        this.usuario_contrasenha=usuario_contrasenha;
        this.usuario_nickname=usuario_nickname;
        this.distrito_id=distrito_id;
        this.distrito_nombre=distrito_nombre;
        this.usuario_nombre=usuario_nombre;
        this.vehiculo=vehiculo;
    }

    public String getDistrito_nombre() {
        return distrito_nombre;
    }

    public void setDistrito_nombre(String distrito_nombre) {
        this.distrito_nombre = distrito_nombre;
    }

    public String getVehiculo() {
        return vehiculo;
    }

    public void setVehiculo(String vehiculo) {
        this.vehiculo = vehiculo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    private String token;

    public Usuario(String usuario_id){
        this.usuario_id=usuario_id;
    }

    public Usuario(String usuario_id, String rol_id) {
        this.usuario_id=usuario_id;
        this.rol_id=rol_id;
    }

    public String getRol_id() {
        return rol_id;
    }

    public void setRol_id(String rol_id) {
        this.rol_id = rol_id;
    }

    public String getUsuario_nickname() {
        return usuario_nickname;
    }

    public void setUsuario_nickname(String usuario_nickname) {
        this.usuario_nickname = usuario_nickname;
    }

    public String getUsuario_contrasenha() {
        return usuario_contrasenha;
    }

    public void setUsuario_contrasenha(String usuario_contrasenha) {
        this.usuario_contrasenha = usuario_contrasenha;
    }

    public String getUsuario_id() {
        return usuario_id;
    }

    public void setUsuario_id(String usuario_id) {
        this.usuario_id = usuario_id;
    }

    public String getDistrito_id() {
        return distrito_id;
    }

    public void setDistrito_id(String distrito_id) {
        this.distrito_id = distrito_id;
    }

    public String getUsuario_nombre() {
        return usuario_nombre;
    }

    public void setUsuario_nombre(String usuario_nombre) {
        this.usuario_nombre = usuario_nombre;
    }

    public String getUsuario_apellido() {
        return usuario_apellido;
    }

    public void setUsuario_apellido(String usuario_apellido) {
        this.usuario_apellido = usuario_apellido;
    }

    public String getUsuario_dni() {
        return usuario_dni;
    }

    public void setUsuario_dni(String usuario_dni) {
        this.usuario_dni = usuario_dni;
    }

    public Usuario(){

    }


}
