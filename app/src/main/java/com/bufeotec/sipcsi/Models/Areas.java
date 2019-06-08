package com.bufeotec.sipcsi.Models;

public class Areas {

   private String area_id;
   private String area_nombre;

   public Areas() {
   }

   public Areas(String area_id, String area_nombre) {
      this.area_id = area_id;
      this.area_nombre = area_nombre;
   }

   public String getArea_id() {
      return area_id;
   }

   public void setArea_id(String area_id) {
      this.area_id = area_id;
   }

   public String getArea_nombre() {
      return area_nombre;
   }

   public void setArea_nombre(String area_nombre) {
      this.area_nombre = area_nombre;
   }
}
