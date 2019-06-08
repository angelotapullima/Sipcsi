package com.bufeotec.sipcsi.Models;

public class Delito {
    private String id;
    private String numero_parte;
    private String asunto;
    private String fecha;
    private String destinarario;
    private String descripcion;
    private String direccion;
    private String latitud;
    private String longitud;
    private String distrito;
    private String usuario_id;

    public Delito() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNumero_parte() {
        return numero_parte;
    }

    public void setNumero_parte(String numero_parte) {
        this.numero_parte = numero_parte;
    }

    public String getAsunto() {
        return asunto;
    }

    public void setAsunto(String asunto) {
        this.asunto = asunto;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getDestinarario() {
        return destinarario;
    }

    public void setDestinarario(String destinarario) {
        this.destinarario = destinarario;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
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

    public String getDistrito() {
        return distrito;
    }

    public void setDistrito(String distrito) {
        this.distrito = distrito;
    }

    public String getUsuario_id() {
        return usuario_id;
    }

    public void setUsuario_id(String usuario_id) {
        this.usuario_id = usuario_id;
    }
}
