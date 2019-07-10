package com.bufeotec.sipcsi.Models;

public class Vehiculos {

    private String id_vehiculo;
    private String marca;
    private String modelo;
    private String placa;
    private String color;
    private String latitud;
    private String longitud;
    private String latitud_antiguo;
    private String longitud_antiguo;
    private String tipo;
    private String Hacetiempo;
    private String foto;
    private String valorfoto;

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public String getValorfoto() {
        return valorfoto;
    }

    public void setValorfoto(String valorfoto) {
        this.valorfoto = valorfoto;
    }

    public String getHacetiempo() {
        return Hacetiempo;
    }

    public void setHacetiempo(String hacetiempo) {
        Hacetiempo = hacetiempo;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getFechora() {
        return fechora;
    }

    public void setFechora(String fechora) {
        this.fechora = fechora;
    }

    private String fechora;

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    private String usuario;

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    private String fecha;

    public String getTok() {
        return tok;
    }

    public void setTok(String tok) {
        this.tok = tok;
    }

    private String tok;

    public Vehiculos(){

    }

    public String getId_vehiculo() {
        return id_vehiculo;
    }

    public void setId_vehiculo(String id_vehiculo) {
        this.id_vehiculo = id_vehiculo;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getLatitud() {
        return latitud;
    }

    public void setLatitud(String latitud) {
        this.latitud = latitud;
    }

    public String getLongitud() {
        return longitud;
    }

    public void setLongitud(String longitud) {
        this.longitud = longitud;
    }

    public String getLatitud_antiguo() {
        return latitud_antiguo;
    }

    public void setLatitud_antiguo(String latitud_antiguo) {
        this.latitud_antiguo = latitud_antiguo;
    }

    public String getLongitud_antiguo() {
        return longitud_antiguo;
    }

    public void setLongitud_antiguo(String longitud_antiguo) {
        this.longitud_antiguo = longitud_antiguo;
    }
}
