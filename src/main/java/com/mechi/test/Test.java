/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mechi.test;

import com.mechi.restaurant.DAO.DAO;
import com.mechi.restaurant.modelos.*;
import java.time.Instant;
import org.hibernate.Session;

/**
 *
 * @author mechi
 */
public class Test {
    public static void main(String []args){
//        //insert de Usuario
        /*
        Usuarios us = new Usuarios();
        us.setApellido("Giana");
        us.setNombre("Francisco");
        us.setPassword("123456");
        us.setUsuario("fgiana");
        DAO.getInstance().add(us);
        
        Clientes cl = new Clientes();
        cl.setApellido("Giana");
        cl.setNombre("Francisco");
        cl.setPassword("123456");
        cl.setUsuario("fgiana");
        DAO.getInstance().add(cl);

        Mozos ml = new Mozos();
        ml.setApellido("Giana");
        ml.setNombre("Francisco");
        ml.setPassword("123456");
        ml.setUsuario("fgiana");
        DAO.getInstance().add(ml);
                */
        
        Platos pla = DAO.getInstance().getPlato(11);
        Usuarios u = DAO.getInstance().getUsuario(5);
        Tickets t = DAO.getInstance().getTicket(1);
        Reservas r = DAO.getInstance().getReserva(1);
        Bebidas b = DAO.getInstance().getBebida(3);
        
        Pedidos p = new Pedidos();
        //p.setFecha(Instant.now());
        p.setEstado("EN COCINA");
        p.setPrecio(10.5);
        //p.setIdUsuario(5);
        //p.setIdReserva(1);
        //p.setIdTicket(1);
        p.getBebidas().add(b);
        p.setReserva(r);
        p.setUsuario(u);
        p.getPlatos().add(pla);
        DAO.getInstance().add(p);

//        
//        DAO.getInstance().add(us);
//        //insert de Ubicaciones
//        Ubicaciones ub = new Ubicaciones();
//        ub.setDescripcion("Terraza");
//        
//        DAO.getInstance().add(ub);
//        //insert de Mesas
//        Mesas me = new Mesas();
//        me.setNumero(1);
//        me.setDescripcion("mesa para dos");
//        me.setIdUbicacion(4);
//        
//        DAO.getInstance().add(me);
//        //insert de Pedidos
//        Pedidos pe = new Pedidos();
//        pe.setIdMesa(3);
//        pe.setIdPlatoPedido(1);
//        pe.setFecha("23/03/15");
//        pe.setIdUsuario(4);
//        pe.setPrecio(87.50);
//        pe.setEnviado(true);
//        pe.setIdBebidaPedido(1);
//        
//        DAO.getInstance().add(pe);
//        //insert de Platos
//        Platos pl = new Platos();
//        pl.setDescripcion("paella");
//        pl.setIdTipoPlato(1);
//        pl.setIdProducto(1);
//        pl.setPrecio(60.99);
        
//        Productos prod = DAO.getInstance().getProducto(2);
        //prod.setDescripcion("Bichos de mar");
        //DAO.getInstance().add(prod);
//        pl.setIdProducto(prod.getIdProducto());
//        TiposPlatos tp = DAO.getInstance().getTiposPlato(2);
//        tp.setDescripcion("Entrada");
//        DAO.getInstance().add(tp);
        
//        pl.setIdTipoPlato(tp.getIdTipoPlato());
        
        
//        DAO.getInstance().add(pl);
//        
//        DAO.getInstance().add(pl);
//     
//        //insert de Tipos de Platos
//        TiposPlatos tp = new TiposPlatos();
//        tp.setDescripcion("Plato Principal");
//        
//        DAO.getInstance().add(tp);
//        //insert de Bebidas
//        Bebidas be = new Bebidas();
//        be.setNombre("Coca Cola");
//        be.setPrecio(20.50);
//        be.setDescripcion("350 cm3");
//        
//        DAO.getInstance().add(be);
//        //insert de Facturas
//        Facturas fa = new Facturas();
//        fa.setTipoIva("Consumidor Final");
//        fa.setIdPedidoPorFactura(1);
//        
//        DAO.getInstance().add(fa);
        
        //insert de Facturas
        
        
        //Session s=DAO.getInstance().getSession();
        //s.beginTransaction();
        

//        Bebidas beb = new Bebidas();
//        beb.setDescripcion("Coca Cola");
//        beb.setNombre("Coca Cola");
//        beb.setPrecio(10.50);
//        DAO.getInstance().add(beb);
        
        //Creamos el pedido
        
//        Pedidos pe = new Pedidos();
//        
//        Usuarios u = DAO.getInstance().getUsuario(7);
//        Mesas m = DAO.getInstance().getMesa(3);
//        //Asignamos todo lo necesiario en el pedido
//        //pe.setIdMesa(3);//En un futuro este valor se va asignar de la misma forma que Usuario,Plato y Bebida
//        //pe.setIdPlatoPedido(1);
//        pe.setFecha("23/03/15");
//        pe.setPrecio(87.50);
//        pe.setEnviado(true);
//        //pe.setIdBebidaPedido(1);
//        
//        //muchos a muchos
//        //Buscamos el Plato
//        Platos pl = DAO.getInstance().getPlato(6);
//        pe.getPlatos().add(pl);
//        //Buscamos la bebida
//        Bebidas beb = DAO.getInstance().getBebida(1);
//        pe.getBebidas().add(beb);
//        //Buscamos la factura
//        Facturas fa = DAO.getInstance().getFactura(1);
//        pe.getFacturas().add(fa);
//        
//        //muchos a uno
//        //Seteamos la mesa
//        pe.setMesa(m);
//
//        pe.setUsuario(u);
//        
//        DAO.getInstance().add(pe);
        
//        Pedidos pe1 = DAO.getInstance().getPedido(7);
//        Usuarios u=DAO.getInstance().getUsuario(6);
//        DAO.getInstance().addPedidoToUsuarios(u, pe1);
        
//        Mesas me = new Mesas();
//        
//        Ubicaciones u = DAO.getInstance().getUbicacion(5);
//        //Asignamos todo lo necesiario en el pedido
//        //pe.setIdMesa(3);//En un futuro este valor se va asignar de la misma forma que Usuario,Plato y Bebida
//        //pe.setIdPlatoPedido(1);
//        me.setNumero(3);
//        me.setDescripcion("mesa para 4");
//        
//        //muchos a uno
//        //Seteamos la mesa
//        me.setUbicacion(u);
//        
//        DAO.getInstance().add(me);
        
//        Platos pl = new Platos();
//        TiposPlatos tp = DAO.getInstance().getTiposPlato(2);
//        Productos p = DAO.getInstance().getProducto(2);
//        pl.setDescripcion("Paella");
//        pl.setPrecio(69.90);
//        
//        //muchos a uno
//        //Seteamos la mesa
//        pl.setTiposPlatos(tp);
//        pl.setProductos(p);
//        
//        DAO.getInstance().add(pl);
          
//          Pedidos p = DAO.getInstance().getPedido(7);
//          System.out.println(p.getUsuario());
    
    
    }
}
