package com.mechi.restaurant.modelos;

import java.util.HashSet;
import java.util.Set;

public class TiposPlatos {
    private int idTipoPlato;
    private String descripcion;
    private String imagen;

    private boolean activo;
    
    private Set<Platos> platos = new HashSet<Platos>();
    private Set<Bebidas> bebidas = new HashSet<Bebidas>();

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
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
 
    public TiposPlatos(){
        activo = true;
    }
    
    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    public int getIdTipoPlato() {
        return idTipoPlato;
    }

    public void setIdTipoPlato(int idTipoPlato) {
        this.idTipoPlato = idTipoPlato;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @Override
    public String toString() {
        return "TiposPlatos{" + "idTipoPlato=" + idTipoPlato + ", descripcion=" + descripcion + '}';
    }
}
