package com.mechi.rest.controllers;

import com.mechi.restaurant.DAO.DAO;
import com.mechi.restaurant.modelos.Mesas;
import com.mechi.restaurant.modelos.Mozos;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/mozo")
public class MozoController {
    @RequestMapping("/mozo")
    public Mozos greetingMo(@RequestParam(value="id", defaultValue="1") int id) {
        return DAO.getInstance().getMozo(id);
    }    
    @RequestMapping("/mozos")
    public Map<String, Object> getItems(@RequestParam(value = "p", defaultValue = "1") int currentPage, @RequestParam(value = "ipp", defaultValue = "1000") int itemsPerPage, @RequestParam(value = "filter", defaultValue = "") String filter) {
        Map<String, Object> json = new HashMap<String, Object>();
        List<Mozos> items = DAO.getInstance().getMozos(currentPage,itemsPerPage,filter);
        json.put("usuarios", items);
        json.put("totalItems", DAO.getInstance().getSize(Mozos.class,filter));
        return json;
    }
    @RequestMapping("/mesas/{idMozo}")
    public List<Mesas> getMesasPorMozo(@PathVariable("idMozo") int idMozo) {
        return DAO.getInstance().getMesas(1,1000);
    }
    @RequestMapping("/agregar")
    public Mozos agregarMozo(@RequestBody Mozos mozo) {
        DAO.getInstance().add(mozo);
        return mozo;
    }
    @RequestMapping("/modificar/{id}")
    public Mozos modificarMozo(@PathVariable("id") int id,@RequestBody Mozos mozo){
        DAO.getInstance().update(mozo);
        return mozo;
    }
    @RequestMapping("/borrar/{id}")
    public void BorrarMozo(@PathVariable("id") int id) {
        Mozos mo = DAO.getInstance().getMozo(id);
        mo.setActivo(false);
        DAO.getInstance().update(mo);
    }
}
