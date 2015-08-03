package com.mechi.restaurant.modelos;

import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class Reservas {
    private int nroReserva;
    private int idMesa;
    private String estado;
    private String fecha;
    private String hora;
    private int cantidadPersonas;
    private Persona usuario;
    private Mesas mesa;
    private Calendar fechaReserva;

    public Calendar getFechaReserva() {
        return fechaReserva;
    }

    public void setFechaReserva(Calendar fechaReserva) {
        this.fechaReserva = fechaReserva;
    }

    public Reservas(){
    
    }

    public int getIdMesa() {
        return idMesa;
    }

    public void setIdMesa(int idMesa) {
        this.idMesa = idMesa;
    }

    public int getNroReserva() {
        return nroReserva;
    }

    public void setNroReserva(int nroReserva) {
        this.nroReserva = nroReserva;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }
    
    public int getCantidadPersonas() {
        return cantidadPersonas;
    }

    public void setCantidadPersonas(int cantidadPersonas) {
        this.cantidadPersonas = cantidadPersonas;
    }

    public Persona getUsuario() {
        return usuario;
    }

    public void setUsuario(Persona usuario) {
        this.usuario = usuario;
    }

    public Mesas getMesa() {
        return mesa;
    }

    public void setMesa(Mesas mesa) {
        this.mesa = mesa;
    }

    @Override
    public String toString() {
        return "Reservas{" + "nroReserva=" + nroReserva + ", estado=" + estado + ", fecha=" + fecha + ", hora=" + hora + ", cantidadPersonas=" + cantidadPersonas + ", usuario=" + usuario + ", mesa=" + mesa + '}';
    }
}
