package com.mechi.rest.controllers;

import com.mechi.restaurant.DAO.DAO;
import com.mechi.restaurant.modelos.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/usuario")
public class UsuarioController {

    @RequestMapping("/usuarios")
    public Map<String, Object> getItems(@RequestParam(value = "p", defaultValue = "1") int currentPage, @RequestParam(value = "ipp", defaultValue = "1000") int itemsPerPage, @RequestParam(value = "filter", defaultValue = "") String filter) {
        Map<String, Object> json = new HashMap<String, Object>();
        List<Usuarios> items = DAO.getInstance().getUsuarios(currentPage, itemsPerPage, filter);
        json.put("usuarios", items);
        json.put("totalItems", DAO.getInstance().getSize(Usuarios.class,filter));
        return json;
    }

    @RequestMapping("/{id}")
    public Usuarios greetingUsuario(@PathVariable("id") int id) {
        return DAO.getInstance().getUsuario(id);
    }

    @RequestMapping("/agregar")
    public Usuarios agregarUsuario(@RequestBody Usuarios usuario) {
        DAO.getInstance().add(usuario);
        return usuario;
    }

    @RequestMapping("/modificar/{id}")
    public Usuarios modificarUsuario(@PathVariable int id, @RequestBody Usuarios usuario) {
        DAO.getInstance().update(usuario);
        return usuario;
    }

    @RequestMapping("/borrar/{id}")
    public void BorrarUsuario(@PathVariable("id") int id) {
        Usuarios u = DAO.getInstance().getUsuario(id);
        u.setActivo(false);
        DAO.getInstance().update(u);
    }
}
