package com.mechi.rest.controllers;

import com.mechi.restaurant.DAO.DAO;
import com.mechi.restaurant.modelos.*;
import java.security.Principal;
import java.util.HashMap;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/validar")
public class ValidarController {

    @RequestMapping("/email")
    public boolean getPersonaByEmail(@RequestBody HashMap<String, String> param) {
        return DAO.getInstance().validarEmail(param.get("email"));
    }

    @RequestMapping("/ubicacion")
    public boolean getUbicacionByDescripcion(@RequestBody HashMap<String, String> param) {
        return DAO.getInstance().validarUbicacion(param.get("descripcion"));
    }

    @RequestMapping("/tipoPlato")
    public boolean getTipoPlatoByDescripcion(@RequestBody HashMap<String, String> param) {
        return DAO.getInstance().validarTipoPlato(param.get("descripcion"));
    }

    @RequestMapping("/promocion")
    public boolean getPromocionByDescripcion(@RequestBody HashMap<String, String> param) {
        return DAO.getInstance().validarPromocion(param.get("descripcion"));
    }

    @RequestMapping("/session/usuario")
    public Persona getSession() {
        return (Persona) DAO.getInstance().getMozo(4);
    }

    @RequestMapping("/session/cliente")
    public Persona getSessionCliente(@RequestBody HashMap<String, String> param) {
        return (Persona) DAO.getInstance().getUsuarioPorNombre(param.get("username"));
    }

    @RequestMapping("/session/mozo")
    public Persona getSessionMozo(Principal user, @RequestBody HashMap<String, String> param) {
        return (Persona) DAO.getInstance().getUsuarioPorNombre(param.get("username"));
    }

    @RequestMapping("/session/admin")
    public Persona getSessionAdmin(Principal user, @RequestBody HashMap<String, String> param) {
        return (Persona) DAO.getInstance().getUsuarioPorNombre(param.get("username"));
    }

    @RequestMapping("/user")
    public Principal user(Principal user) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return user;
    }
}
