/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mechi.test;

import com.mechi.restaurant.DAO.DAO;
import com.mechi.restaurant.modelos.Clientes;
import com.mechi.restaurant.modelos.Mesas;
import com.mechi.restaurant.modelos.Platos;
import com.mechi.restaurant.modelos.Reservas;
import com.mechi.restaurant.modelos.TiposPlatos;
import java.util.ArrayList;

/**
 *
 * @author mechi
 */
public class ReservaTest {
    public static void main(String []args){
        Mesas m = DAO.getInstance().getMesa(7);
        Clientes c = DAO.getInstance().getCliente(1);
        
        //Reservas r = new Reservas();
        Reservas r = DAO.getInstance().getReserva(2);
        r.setCantidadPersonas(4);
        r.setUsuario(c);
        r.setMesa(m);
        r.setFecha("2015-05-16");
        r.setHora("19:15:00");
        r.setEstado("ACTIVA");
        
        DAO.getInstance().update(r);
        /*
        ArrayList<Mesas> list = DAO.getInstance().getMesas();
        
        for(Mesas me: list){
            System.out.println(me.getDescripcion());
        }
        
        ArrayList<Reservas> listReservas = DAO.getInstance().getReservas();
        
        for(Reservas re: listReservas){
            System.out.println(re.getMesa().getDescripcion());
        }
        
        ArrayList<Mesas> listMesa1 = DAO.getInstance().getMesasSinReservas("2015-0516","");
        
        for(Mesas me: listMesa1){
            System.out.println(me.getDescripcion());
        }
        */
    }
}
