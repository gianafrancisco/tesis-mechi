package com.mechi.restaurant.modelos;


import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class Pedidos {
    private int idPedido;
    private Date fecha;
    private double precio;
    private String estado;
    private Persona usuario;
    private Reservas reserva;
    private Set<Platos> platos = new HashSet<Platos>();
    private Set<Bebidas> bebidas = new HashSet<Bebidas>();
    private Set<Promociones> promociones = new HashSet<Promociones>();

    public Set<Promociones> getPromociones() {
        return promociones;
    }

    public void setPromociones(Set<Promociones> promociones) {
        this.promociones = promociones;
    }

    public Set<Platos> getPlatos() {
        return platos;
    }

    public void setPlatos(Set<Platos> platos) {
        this.platos = platos;
    }

    public Pedidos(){
        
    }

    public Set<Bebidas> getBebidas() {
        return bebidas;
    }

    public void setBebidas(Set<Bebidas> bebidas) {
        this.bebidas = bebidas;
    }

    public int getIdPedido() {
        return idPedido;
    }

    public void setIdPedido(int idPedido) {
        this.idPedido = idPedido;
    }
    
    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }
    
    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }


    public Persona getUsuario() {
        return usuario;
    }

    public void setUsuario(Persona usuario) {
        this.usuario = usuario;
    }

    public Reservas getReserva() {
        return reserva;
    }

    public void setReserva(Reservas reserva) {
        this.reserva = reserva;
    }
    @Override
    public String toString() {
        return "Pedidos{" + "idPedido=" + idPedido + ", fecha=" + fecha + ", precio=" + precio + ", estado=" + estado + '}';
    }
}
