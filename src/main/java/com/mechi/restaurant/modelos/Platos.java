package com.mechi.restaurant.modelos;

public class Platos {
    private int idPlato;
    private String descripcion;
    private double precio;
    private TiposPlatos tiposPlatos;
    private boolean activo;
    
    public Platos(){   
        activo = true;
    }

    public int getIdPlato() {
        return idPlato;
    }
    
    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    public void setIdPlato(int idPlato) {
        this.idPlato = idPlato;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public TiposPlatos getTiposPlatos() {
        return tiposPlatos;
    }

    public void setTiposPlatos(TiposPlatos tiposPlatos) {
        this.tiposPlatos = tiposPlatos;
    }

    @Override
    public String toString() {
        return "Platos{" + "idPlato=" + idPlato + ", descripcion=" + descripcion + ", precio=" + precio + '}';
    }
}
