package com.mechi.rest.controllers;

import com.mechi.restaurant.DAO.DAO;
import com.mechi.restaurant.modelos.Platos;
import com.mechi.restaurant.modelos.Promociones;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/promocion")
public class PromocionController {
    @RequestMapping("/promocion")
    public Promociones greeting(@RequestParam(value="id", defaultValue="1") int id) {
        return DAO.getInstance().getPromocion(id);
    }    
    @RequestMapping("/promociones")
    public Map<String, Object> getItems(@RequestParam(value = "p", defaultValue = "1") int currentPage, @RequestParam(value = "ipp", defaultValue = "1000") int itemsPerPage) {
        Map<String, Object> json = new HashMap<String, Object>();
        List<Promociones> items = DAO.getInstance().getPromociones(currentPage, itemsPerPage);
        json.put("items", items);
        json.put("totalItems", DAO.getInstance().getSize(Promociones.class,""));
        return json;
    }
    
    @RequestMapping("/disponibles")
    public Map<String, Object> getItemsDisponibles(@RequestParam(value = "p", defaultValue = "1") int currentPage, @RequestParam(value = "ipp", defaultValue = "1000") int itemsPerPage) {
        Map<String, Object> json = new HashMap<String, Object>();
        List<Promociones> items = DAO.getInstance().getPromocionesDisponibles(currentPage, itemsPerPage);
        json.put("items", items);
        json.put("totalItems", items.size());
        return json;
    }
    
    @RequestMapping("/agregar")
    public Promociones agregarPromocion(@RequestBody Promociones promocion) {
        DAO.getInstance().add(promocion);
        return promocion;
    }
    @RequestMapping("/modificar/{id}")
    public Promociones modificarPromocion(@RequestBody Promociones promocion, @PathVariable("id") int id){
        DAO.getInstance().update(promocion);
        return promocion;
    }
    @RequestMapping("/borrar/{id}")
    public boolean BorrarPromocion(@PathVariable("id") int id) {
        Promociones pr = DAO.getInstance().getPromocion(id);
        pr.setActivo(false);
        DAO.getInstance().update(pr);
        return true;
    }
}
