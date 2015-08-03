package com.mechi.restaurant.modelos;

public class Asignaciones {

    private int idAsignacion;
    private String turno;
    private Mozos usuario;
    private Mesas mesa;

    public Asignaciones() {

    }

    public int getIdAsignacion() {
        return idAsignacion;
    }

    public void setIdAsignacion(int idAsignacion) {
        this.idAsignacion = idAsignacion;
    }

    public String getTurno() {
        return turno;
    }

    public void setTurno(String turno) {
        this.turno = turno;
    }

    public Mozos getUsuario() {
        return usuario;
    }

    public void setUsuario(Mozos usuario) {
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
        return "Asignaciones{" + "idAsignacion=" + idAsignacion + ", turno=" + turno + '}';
    }

    @Override
    public boolean equals(Object obj) {
        if (obj != null) {
            return mesa.equals(obj);
        }
        return false;
    }
}
