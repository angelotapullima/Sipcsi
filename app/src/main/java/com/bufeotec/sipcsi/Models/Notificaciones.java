package com.bufeotec.sipcsi.Models;

public class Notificaciones {
    String title;
    String description;
    String id;
    String descount;
    String nombre;

    public String getNombre(String s) {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Notificaciones(String title, String description, String id, String descount) {
        this.title = title;
        this.description = description;
        this.id = id;
        this.descount = descount;
    }

    public Notificaciones() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescount(String s) {
        return descount;
    }

    public void setDescount(String descount) {
        this.descount = descount;
    }
}
