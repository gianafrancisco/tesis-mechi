/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mechi.test;

import com.mechi.restaurant.DAO.DAO;
import com.mechi.restaurant.modelos.Bebidas;
import com.mechi.restaurant.modelos.TiposPlatos;

/**
 *
 * @author mechi
 */
public class BebidasTest {
    public static void main(String []args){
        
        TiposPlatos tp = DAO.getInstance().getTiposPlato(3);
        
        Bebidas b = new Bebidas();
        b.setNombre("coca cola");
        b.setDescripcion("350 cm3");
        b.setPrecio(78);
        b.setTiposPlatos(tp);
        DAO.getInstance().add(b);

    }
}
