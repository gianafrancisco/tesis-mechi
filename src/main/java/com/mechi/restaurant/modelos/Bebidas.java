package com.mechi.restaurant.modelos;

public class Bebidas {
    private int idBebida;
    private String nombre;
    private double precio;
    private String descripcion;
    private TiposPlatos tiposPlatos;
    private boolean activo;

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }
    
    public Bebidas(){
        activo = true;
    }

    public int getIdBebida() {
        return idBebida;
    }

    public void setIdBebida(int idBebida) {
        this.idBebida = idBebida;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public TiposPlatos getTiposPlatos() {
        return tiposPlatos;
    }

    public void setTiposPlatos(TiposPlatos tiposPlatos) {
        this.tiposPlatos = tiposPlatos;
    }
    
    @Override
    public String toString() {
        return "Bebidas{" + "idBebida=" + idBebida + ", nombre=" + nombre + ", precio=" + precio + ", descripcion=" + descripcion + '}';
    }
}
