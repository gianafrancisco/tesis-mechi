package com.mechi.restaurant.modelos;

import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;

public class Promociones {
   private int idPromocion;
   private String descripcion;
   private Calendar fechaDesde;
   private Calendar fechaHasta;
   private double precio;
   private boolean activo;
   
   private Set<Platos> platos = new HashSet<Platos>();
   private Set<Bebidas> bebidas = new HashSet<Bebidas>();
   
   public Promociones(){
       activo = true;
   }
   
   public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    public int getIdPromocion() {
        return idPromocion;
    }

    public void setIdPromocion(int idPromocion) {
        this.idPromocion = idPromocion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Calendar getFechaDesde() {
        return fechaDesde;
    }

    public void setFechaDesde(Calendar fechaDesde) {
        this.fechaDesde = fechaDesde;
    }

    public Calendar getFechaHasta() {
        return fechaHasta;
    }

    public void setFechaHasta(Calendar fechaHasta) {
        this.fechaHasta = fechaHasta;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public Set<Platos> getPlatos() {
        return platos;
    }

    public void setPlatos(Set<Platos> platos) {
        this.platos = platos;
    }

    public Set<Bebidas> getBebidas() {
        return bebidas;
    }

    public void setBebidas(Set<Bebidas> bebidas) {
        this.bebidas = bebidas;
    }

    @Override
    public String toString() {
        return "Promociones{" + "idPromocion=" + idPromocion + ", descripcion=" + descripcion + ", fechaDesde=" + fechaDesde + ", fechaHasta=" + fechaHasta + ", precio=" + precio + '}';
    }
}
