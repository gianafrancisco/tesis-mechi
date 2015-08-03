package com.mechi.rest.controllers;

import com.mechi.restaurant.DAO.DAO;
import com.mechi.restaurant.modelos.*;
import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/mesa")
public class MesaController {
//MESAS

    @RequestMapping("/mesas")
    public Map<String, Object> getItemsMesas(@RequestParam(value = "p", defaultValue = "1") int currentPage, @RequestParam(value = "ipp", defaultValue = "1000") int itemsPerPage) {
        Map<String, Object> json = new HashMap<String, Object>();
        List<Mesas> items = DAO.getInstance().getMesas(currentPage, itemsPerPage);
        json.put("items", items);
        json.put("totalItems", DAO.getInstance().getSize(Mesas.class,""));
        return json;
    }

    @RequestMapping("/mesa")
    public Mesas greetingM(@RequestParam(value = "id", defaultValue = "1") int id) {
        return DAO.getInstance().getMesa(id);
    }

    @RequestMapping("/agregar")
    public Mesas agregarMesa(@RequestBody Mesas mesa) {
        DAO.getInstance().add(mesa);
        return mesa;
    }

    @RequestMapping("/modificar/{id}")
    public Mesas modificarMesa(@RequestBody Mesas mesa, @PathVariable("id") int id) {
        DAO.getInstance().update(mesa);
        return mesa;
    }

    @RequestMapping("/borrar/{id}")
    public boolean BorrarMesa(@PathVariable("id") int id) {
        Mesas me = DAO.getInstance().getMesa(id);
        me.setActivo(false);
        DAO.getInstance().update(me);
        return true;
    }

    @RequestMapping("/disponibles/{fecha}/{hora}")
    public List<Mesas> mesasDisponibles(@PathVariable("fecha") String fecha, @PathVariable("hora") String hora) throws ParseException {
        List<Mesas> mesasDisponibles = DAO.getInstance().getMesasSinReservas(fecha, hora);
        return mesasDisponibles;
    }
}
