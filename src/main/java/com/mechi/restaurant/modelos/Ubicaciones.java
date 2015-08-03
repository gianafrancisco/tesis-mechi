package com.mechi.restaurant.modelos;

import java.util.HashSet;
import java.util.Set;

public class Ubicaciones {
    private int idUbicacion;
    private String descripcion;
    private boolean activo;
    
    private Set<Mesas> mesas = new HashSet<Mesas>();

    public Set<Mesas> getMesas() {
        return mesas;
    }

    public void setMesas(Set<Mesas> mesas) {
        this.mesas = mesas;
    }

    public Ubicaciones(){
        activo = true;
    }
    
    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }
    
    public int getIdUbicacion() {
        return idUbicacion;
    }

    private void setIdUbicacion(int idUbicacion) {
        this.idUbicacion = idUbicacion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    
}
