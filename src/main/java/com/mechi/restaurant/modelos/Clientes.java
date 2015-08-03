package com.mechi.restaurant.modelos;

import java.util.HashSet;
import java.util.Set;


public class Clientes extends Persona {

    public Set getReservas() {
        return reservas;
    }

    public void setReservas(Set reservas) {
        this.reservas = reservas;
    }
    private Set reservas = new HashSet();
}
