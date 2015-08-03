/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mechi.test;

import com.mechi.restaurant.DAO.DAO;
import com.mechi.restaurant.modelos.Mesas;
import com.mechi.restaurant.modelos.Ubicaciones;
import java.text.ParseException;
import java.util.List;

/**
 *
 * @author mechi
 */
public class MesaDisponiblesTest {

    public static void main(String[] args) throws ParseException {
        String fecha="2015-07-19";
        String hora="22:30:00";
        List<Mesas> mesasDisponibles = DAO.getInstance().getMesasSinReservas(fecha, hora);
        for(Mesas m: mesasDisponibles){
            System.out.println(m.getDescripcion());
        }
    }
}
