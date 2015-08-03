package com.mechi.rest.controllers;

import com.mechi.restaurant.DAO.DAO;
import com.mechi.restaurant.modelos.Bebidas;
import com.mechi.restaurant.modelos.Platos;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/bebida")
public class BebidaController {
    @RequestMapping("/bebida")
    public Bebidas greetingBe(@RequestParam(value="id", defaultValue="1") int id) {
        return DAO.getInstance().getBebida(id);
    }    
    @RequestMapping("/bebidas")
    public Map<String, Object> getItems(@RequestParam(value = "p", defaultValue = "1") int currentPage, @RequestParam(value = "ipp", defaultValue = "1000") int itemsPerPage) {
        Map<String, Object> json = new HashMap<String, Object>();
        List<Bebidas> items = DAO.getInstance().getBebidas(currentPage, itemsPerPage);
        json.put("items", items);
        json.put("totalItems", DAO.getInstance().getSize(Bebidas.class,""));
        return json;
    }
    @RequestMapping("/agregar")
    public Bebidas agregarBebida(@RequestBody Bebidas bebida) {
        DAO.getInstance().add(bebida);
        return bebida;
    }
    @RequestMapping("/modificar/{id}")
    public Bebidas modificarBebida(@RequestBody Bebidas bebida, @PathVariable("id") int id){
        DAO.getInstance().update(bebida);
        return bebida;
    }
    @RequestMapping("/borrar/{id}")
    public boolean BorrarBebida(@PathVariable("id") int id) {
        Bebidas be = DAO.getInstance().getBebida(id);
        be.setActivo(false);
        DAO.getInstance().update(be);
        return true;
    }
}
