/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mechi.test;

import com.mechi.restaurant.DAO.DAO;
import com.mechi.restaurant.modelos.Platos;
import com.mechi.restaurant.modelos.TiposPlatos;

/**
 *
 * @author mechi
 */
public class PlatosTest {
    public static void main(String []args){
        
        TiposPlatos tp = new TiposPlatos();
        tp.setDescripcion("POSTRES");
        DAO.getInstance().add(tp);
        
        Platos p = new Platos();
        p.setDescripcion("Milanesa");
        p.setPrecio(78);
        p.setTiposPlatos(tp);
        DAO.getInstance().add(p);

    }
}
