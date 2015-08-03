package com.mechi.restaurant.modelos;

import java.util.HashSet;
import java.util.Set;

public class Mesas {
    private int idMesa;
    private int numero;
    private String descripcion;
    private Ubicaciones ubicacion;
    private boolean activo;
    private boolean reservarOnline; 

    public boolean isReservarOnline() {
        return reservarOnline;
    }

    public void setReservarOnline(boolean reservarOnline) {
        this.reservarOnline = reservarOnline;
    }
    
    private Set reservas = new HashSet();
    private Set asignaciones = new HashSet();
    
    public Mesas(){
        activo = true;
    }
    
    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    public int getIdMesa() {
        return idMesa;
    }

    public void setIdMesa(int idMesa) {
        this.idMesa = idMesa;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Ubicaciones getUbicacion(){
        return ubicacion;
    }
    
    public void setUbicacion(Ubicaciones ubicacion) {
        this.ubicacion = ubicacion;
    }

    public Set getReservas() {
        return reservas;
    }

    public void setReservas(Set reservas) {
        this.reservas = reservas;
    }

    public Set getAsignaciones() {
        return asignaciones;
    }

    public void setAsignaciones(Set asignaciones) {
        this.asignaciones = asignaciones;
    }

    public String toString() {
        return descripcion;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 43 * hash + this.idMesa;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Mesas other = (Mesas) obj;
        if (this.idMesa != other.idMesa) {
            return false;
        }
        return true;
    }
}
