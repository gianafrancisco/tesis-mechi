package com.mechi.rest.controllers;

import com.mechi.restaurant.DAO.DAO;
import com.mechi.restaurant.modelos.Asignaciones;
import com.mechi.restaurant.modelos.Persona;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/asignacion")
public class AsignacionController {

    @RequestMapping("/asignacion")
    public Asignaciones greetingAs(@RequestParam(value = "id", defaultValue = "1") int id) {
        return DAO.getInstance().getAsignacion(id);
    }

    @RequestMapping("/asignaciones")
    public Map<String, Object> getItems(@RequestParam(value = "p", defaultValue = "1") int currentPage, @RequestParam(value = "ipp", defaultValue = "1000") int itemsPerPage) {
        //return DAO.getInstance().getAsignaciones();

        Map<String, Object> json = new HashMap<String, Object>();
        List<Asignaciones> items = DAO.getInstance().getAsignaciones(currentPage, itemsPerPage);
        json.put("items", items);
        json.put("totalItems", DAO.getInstance().getSize(Asignaciones.class, ""));
        return json;
    }

    @RequestMapping("/agregar")
    public Asignaciones agregarAsignacion(@RequestBody Asignaciones asignacion) {

        DAO.getInstance().add(asignacion);
        return asignacion;
    }

    @RequestMapping("/modificar")
    public Asignaciones modificarAsignacion(@RequestBody Asignaciones asignacion) {
        DAO.getInstance().update(asignacion);
        return asignacion;
    }

    @RequestMapping("/borrar/{id}")
    public void BorrarAsignacion(@PathVariable("id") int id) {
        Asignaciones as = DAO.getInstance().getAsignacion(id);
        DAO.getInstance().delete(as);
    }

    @RequestMapping("/mesas")
    public List<Asignaciones> getItemsMesas() {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Persona p = DAO.getInstance().getUsuarioPorNombre(auth.getName());
        int id = p.getIdUsuario();

        return DAO.getInstance().getAsignacionesPorMozo(id);
    }

}
