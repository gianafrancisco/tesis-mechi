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
@RequestMapping("/ubicacion")
public class UbicacionController {

    @RequestMapping("/ubicacion")
    public Ubicaciones greetingU(@RequestParam(value = "id", defaultValue = "1") int id) {
        return DAO.getInstance().getUbicacion(id);
    }

    @RequestMapping("/ubicaciones")
    public Map<String, Object> getItems(@RequestParam(value = "p", defaultValue = "1") int currentPage, @RequestParam(value = "ipp", defaultValue = "1000") int itemsPerPage) {
        //return DAO.getInstance().getUbicaciones();
        Map<String, Object> json = new HashMap<String, Object>();
        List<Ubicaciones> items = DAO.getInstance().getUbicaciones(currentPage, itemsPerPage);
        json.put("items", items);
        json.put("totalItems", DAO.getInstance().getSize(Ubicaciones.class,""));
        return json;

    }

    @RequestMapping("/agregar")
    public Ubicaciones agregarUbicacion(@RequestBody Ubicaciones ubicacion) {
        DAO.getInstance().add(ubicacion);
        return ubicacion;
    }

    @RequestMapping("/modificar/{id}")
    public Ubicaciones modificarUbicacion(@RequestBody Ubicaciones ubicacion, @PathVariable("id") int id) {
        DAO.getInstance().update(ubicacion);
        return ubicacion;
    }

    @RequestMapping("/borrar/{id}")
    public boolean BorrarUbicacion(@PathVariable("id") int id) {
        Ubicaciones ub = DAO.getInstance().getUbicacion(id);
        ub.setActivo(false);
        DAO.getInstance().update(ub);
//        if (ub.getMesas().isEmpty()) {
//            DAO.getInstance().delete(ub);
//            return true;
//        }
        return true;
    }
}
