/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mechi.security;

import com.mechi.restaurant.modelos.Persona;
import java.security.Principal;

/**
 *
 * @author mechi
 */
public class MyPrincipal implements Principal {
    private Persona p;

    public Persona getP() {
        return p;
    }

    public void setP(Persona p) {
        this.p = p;
    }

    @Override
    public String getName() {
        return "MyPrincipal";
    }
    
}
