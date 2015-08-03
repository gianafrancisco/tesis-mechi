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
public class ValidarEmailTest {
    public static void main(String []args){
        
        System.out.println(DAO.getInstance().validarEmail("mmercedeshernando@gmail.com"));

    }
}
