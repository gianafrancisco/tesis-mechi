package com.mechi.rest.controllers;

import com.mechi.restaurant.DAO.DAO;
import com.mechi.restaurant.modelos.Clientes;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cliente")
public class ClienteController {

    @RequestMapping("/cliente")
    public Clientes greetingCl(@RequestParam(value = "id", defaultValue = "1") int id) {
        return DAO.getInstance().getCliente(id);
    }

    @RequestMapping("/clientes")
    public Map<String, Object> getItems(@RequestParam(value = "p", defaultValue = "1") int currentPage, @RequestParam(value = "ipp", defaultValue = "1000") int itemsPerPage, @RequestParam(value = "filter", defaultValue = "") String filter) {
        Map<String, Object> json = new HashMap<String, Object>();
        List<Clientes> items = DAO.getInstance().getClientes(currentPage, itemsPerPage, filter);
        json.put("usuarios", items);
        json.put("totalItems", DAO.getInstance().getSize(Clientes.class,filter));
        return json;
    }

    @RequestMapping("/agregar")
    public Clientes agregarCliente(@RequestBody Clientes cliente) {
        DAO.getInstance().add(cliente);
        return cliente;
    }

    @RequestMapping("/modificar/{id}")
    public Clientes modificarCliente(@PathVariable("id") int id, @RequestBody Clientes cliente) {
        DAO.getInstance().update(cliente);
        return cliente;
    }

    @RequestMapping("/borrar/{id}")
    public void BorrarCliente(@PathVariable("id") int id) {
        Clientes cl = DAO.getInstance().getCliente(id);
        cl.setActivo(false);
        DAO.getInstance().update(cl);
    }
}
