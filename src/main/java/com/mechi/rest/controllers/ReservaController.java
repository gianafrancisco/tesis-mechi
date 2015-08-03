package com.mechi.rest.controllers;

import com.mechi.restaurant.DAO.DAO;
import com.mechi.restaurant.modelos.Persona;
import com.mechi.restaurant.modelos.Reservas;
import java.text.ParseException;
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
@RequestMapping("/reserva")
public class ReservaController {

    @RequestMapping("/reserva")
    public Reservas greetingRe(@RequestParam(value = "id", defaultValue = "1") int id) {
        return DAO.getInstance().getReserva(id);
    }

    @RequestMapping("/reservas")
    public Map<String, Object> getItems(@RequestBody HashMap<String, String> filtro, @RequestParam(value = "p", defaultValue = "1") int currentPage, @RequestParam(value = "ipp", defaultValue = "1000") int itemsPerPage) throws ParseException {
        Map<String, Object> json = new HashMap<String, Object>();
        List<Reservas> items = DAO.getInstance().getReservas(filtro, currentPage, itemsPerPage);
        json.put("items", items);
        json.put("totalItems", DAO.getInstance().getSizeReservasEntreFechas(filtro));
        return json;
    }

    /*@RequestMapping("/activas")
     public List<Reservas> getItemsReservasActivas() {
     return DAO.getInstance().getReservasActivas();
     }*/
    @RequestMapping("/activas")
    public List<Reservas> getReservasActivasPorMozo() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Persona p = DAO.getInstance().getUsuarioPorNombre(auth.getName());
        if (p==null) {
            return DAO.getInstance().getReservasActivas();
        } else {
            return DAO.getInstance().getReservasActivasPorMozo(p.getIdUsuario());
        }
    }

    @RequestMapping("/reservas/{idUsuario}")
    public Map<String, Object> getItems2(@PathVariable("idUsuario") int id, @RequestParam(value = "p", defaultValue = "1") int currentPage, @RequestParam(value = "ipp", defaultValue = "1000") int itemsPerPage) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Persona p = DAO.getInstance().getUsuarioPorNombre(auth.getName());
        id = p.getIdUsuario();
        //reserva.setUsuario(p);

        Map<String, Object> json = new HashMap<String, Object>();
        List<Reservas> items = DAO.getInstance().getReservasPorUsuario(id, currentPage, itemsPerPage);
        json.put("reservas", items);
        json.put("totalItems", DAO.getInstance().getSizeReservasPorUsuario(id));
        return json;
    }

    @RequestMapping("/agregar")
    public Reservas agregarReserva(@RequestBody Reservas reserva, @RequestParam(value = "u", defaultValue = "-1") int idCliente) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Persona p = DAO.getInstance().getUsuarioPorNombre(auth.getName());
        //reserva.setUsuario(p);
        //Clientes u = DAO.getInstance().getCliente(idCliente);
        if (p != null) {
            reserva.setUsuario(p);
            reserva.setEstado("ACTIVA");
            DAO.getInstance().add(reserva);
        }
        return reserva;
    }

    @RequestMapping("/modificar/{id}")
    public Reservas modificarReserva(@RequestBody Reservas reserva, @PathVariable("id") int id) {
        DAO.getInstance().update(reserva);
        return reserva;
    }

    @RequestMapping("/cancelar/{id}")
    public boolean CancelarReserva(@PathVariable("id") int id) {
        Reservas re = DAO.getInstance().getReserva(id);
        re.setEstado("CANCELADA");
        DAO.getInstance().update(re);
        return true;
    }
}
