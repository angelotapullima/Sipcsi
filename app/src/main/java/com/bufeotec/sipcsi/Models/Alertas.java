package com.bufeotec.sipcsi.Models;

public class Alertas {
    private String usuario_id;
    private String distrito_id;
    private String alerta_id;
    private String alerta_tipo;
    private String alerta_tipo_delito;
    private String alerta_descripcion;
    private String alerta_fecha;
    private String calle_nombre;
    private String calle_x;
    private String calle_y;
    private String cantidad;
    private String alerta_estado;
    private String alerta_foto;

    public Alertas(){

    }

    public String getAlerta_foto() {
        return alerta_foto;
    }

    public void setAlerta_foto(String alerta_foto) {
        this.alerta_foto = alerta_foto;
    }

    public String getCantidad() {
        return cantidad;
    }

    public void setCantidad(String cantidad) {
        this.cantidad = cantidad;
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

    public String getAlerta_id() {
        return alerta_id;
    }

    public void setAlerta_id(String alerta_id) {
        this.alerta_id = alerta_id;
    }

    public String getAlerta_tipo() {
        return alerta_tipo;
    }

    public void setAlerta_tipo(String alerta_tipo) {
        this.alerta_tipo = alerta_tipo;
    }

    public String getAlerta_tipo_delito() {
        return alerta_tipo_delito;
    }

    public void setAlerta_tipo_delito(String alerta_tipo_delito) {
        this.alerta_tipo_delito = alerta_tipo_delito;
    }

    public String getAlerta_descripcion() {
        return alerta_descripcion;
    }

    public void setAlerta_descripcion(String alerta_descripcion) {
        this.alerta_descripcion = alerta_descripcion;
    }

    public String getAlerta_fecha() {
        return alerta_fecha;
    }

    public void setAlerta_fecha(String alerta_fecha) {
        this.alerta_fecha = alerta_fecha;
    }

    public String getCalle_nombre() {
        return calle_nombre;
    }

    public void setCalle_nombre(String calle_nombre) {
        this.calle_nombre = calle_nombre;
    }

    public String getCalle_x() {
        return calle_x;
    }

    public void setCalle_x(String calle_x) {
        this.calle_x = calle_x;
    }

    public String getCalle_y() {
        return calle_y;
    }

    public void setCalle_y(String calle_y) {
        this.calle_y = calle_y;
    }

    public String getAlerta_estado() {
        return alerta_estado;
    }

    public void setAlerta_estado(String alerta_estado) {
        this.alerta_estado = alerta_estado;
    }
}
