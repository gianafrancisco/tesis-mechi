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
@RequestMapping("/plato")
public class PlatoController {
    //PLATOS
    @RequestMapping("/platos")
    public Map<String, Object> getItems(@RequestParam(value = "p", defaultValue = "1") int currentPage, @RequestParam(value = "ipp", defaultValue = "1000") int itemsPerPage) {
        Map<String, Object> json = new HashMap<String, Object>();
        List<Platos> items = DAO.getInstance().getPlatos(currentPage, itemsPerPage);
        json.put("items", items);
        json.put("totalItems", DAO.getInstance().getSize(Platos.class,""));
        return json;
    }
    @RequestMapping("/plato")
    public Platos greetingPl(@RequestParam(value="id", defaultValue="1") int id) {
        return DAO.getInstance().getPlato(id);
    }
    @RequestMapping("/agregar")
    public Platos agregarPlato(@RequestBody Platos plato) {
        DAO.getInstance().add(plato);
        return plato;
    }
    @RequestMapping("/modificar/{id}")
    public Platos modificarPlato(@RequestBody Platos plato, @PathVariable("id") int id){
        DAO.getInstance().update(plato);
        return plato;
    }
    @RequestMapping("/borrar/{id}")
    public boolean BorrarPlato(@PathVariable("id") int id) {
        Platos pl = DAO.getInstance().getPlato(id);
        pl.setActivo(false);
        DAO.getInstance().update(pl);
        return true;
    }
}
