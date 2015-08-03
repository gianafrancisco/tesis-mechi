package com.mechi.restaurant.modelos;

import java.util.HashSet;
import java.util.Set;

public class Mozos extends Persona {
    private Set asignaciones = new HashSet();
    private Set pedidos = new HashSet();

    public Set getAsignaciones() {
        return asignaciones;
    }

    public void setAsignaciones(Set asignaciones) {
        this.asignaciones = asignaciones;
    }

    public Set getPedidos() {
        return pedidos;
    }

    public void setPedidos(Set pedidos) {
        this.pedidos = pedidos;
    }

}
