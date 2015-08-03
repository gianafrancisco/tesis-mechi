package com.mechi.restaurant.modelos;

import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;

public class Tickets {
    private int idTicket;
    private Calendar fecha;
    
    private Set<Pedidos> pedidos = new HashSet<Pedidos>();
    
    public Set<Pedidos> getPedidos() {
        return pedidos;
    }

    public void setPedidos(Set<Pedidos> pedidos) {
        this.pedidos = pedidos;
    }
     
    public Tickets(){
        
    }

    public int getIdTicket() {
        return idTicket;
    }

    public void setIdTicket(int idTicket) {
        this.idTicket = idTicket;
    }

    public Calendar getFecha() {
        return fecha;
    }

    public void setFecha(Calendar fecha) {
        this.fecha = fecha;
    }

    @Override
    public String toString() {
        return "Tickets{" + "idTicket=" + idTicket + ", fecha=" + fecha + '}';
    }
}
