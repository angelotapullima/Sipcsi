package com.bufeotec.sipcsi.Models;

public class Queja {

   private String queja_id;
   private String usuario_id;
   private String usuario_nombre;
   private String distrito_id;
   private String queja_destino;
   private String queja_descripcion;
   private String queja_foto;
   private String queja_hora;
   private String queja_fecha;
   private String cantidad;
   private String estado_like;

   public Queja() {
   }

   public String getCantidad() {
      return cantidad;
   }

   public void setCantidad(String cantidad) {
      this.cantidad = cantidad;
   }

   public String getEstado_like() {
      return estado_like;
   }

   public void setEstado_like(String estado_like) {
      this.estado_like = estado_like;
   }

   public Queja(String usuario_id) {
      this.usuario_id = usuario_id;
   }

   public Queja(String queja_id, String usuario_id, String usuario_nombre, String distrito_id, String queja_destino, String queja_descripcion, String queja_foto, String queja_hora, String queja_fecha) {
      this.queja_id = queja_id;
      this.usuario_id = usuario_id;
      this.usuario_nombre = usuario_nombre;
      this.distrito_id = distrito_id;
      this.queja_destino = queja_destino;
      this.queja_descripcion = queja_descripcion;
      this.queja_foto = queja_foto;
      this.queja_hora = queja_hora;
      this.queja_fecha = queja_fecha;
   }

   public String getQueja_id() {
      return queja_id;
   }

   public void setQueja_id(String queja_id) {
      this.queja_id = queja_id;
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

   public String getQueja_destino() {
      return queja_destino;
   }

   public void setQueja_destino(String queja_destino) {
      this.queja_destino = queja_destino;
   }

   public String getQueja_descripcion() {
      return queja_descripcion;
   }

   public void setQueja_descripcion(String queja_descripcion) {
      this.queja_descripcion = queja_descripcion;
   }

   public String getQueja_foto() {
      return queja_foto;
   }

   public void setQueja_foto(String queja_foto) {
      this.queja_foto = queja_foto;
   }

   public String getQueja_hora() {
      return queja_hora;
   }

   public void setQueja_hora(String queja_hora) {
      this.queja_hora = queja_hora;
   }

   public String getQueja_fecha() {
      return queja_fecha;
   }

   public void setQueja_fecha(String queja_fecha) {
      this.queja_fecha = queja_fecha;
   }

   public String getUsuario_nombre() {
      return usuario_nombre;
   }

   public void setUsuario_nombre(String usuario_nombre) {
      this.usuario_nombre = usuario_nombre;
   }
}
