/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mechi.test;

import com.mechi.restaurant.DAO.DAO;
import com.mechi.restaurant.modelos.Mesas;
import com.mechi.restaurant.modelos.Ubicaciones;

/**
 *
 * @author mechi
 */
public class MesaTest {

    public static void main(String[] args) {
        Mesas m = new Mesas();
        m.setNumero(2);
        m.setDescripcion("Prueba");
        Ubicaciones u = DAO.getInstance().getUbicacion(8);

        m.setUbicacion(u);

        DAO.getInstance().add(m);
    }
}
