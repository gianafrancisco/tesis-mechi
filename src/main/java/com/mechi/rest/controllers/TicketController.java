package com.mechi.rest.controllers;

import com.mechi.restaurant.DAO.DAO;
import com.mechi.restaurant.modelos.Pedidos;
import com.mechi.restaurant.modelos.Reservas;
import com.mechi.restaurant.modelos.Tickets;
import java.util.List;
import java.util.Set;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ticket")
public class TicketController {
    @RequestMapping("/ticket")
    public Tickets greetingTi(@RequestParam(value="id", defaultValue="1") int id) {
        return DAO.getInstance().getTicket(id);
    }    
    @RequestMapping("/tickets")
    public List<Tickets> getItemsTickets() {
        return DAO.getInstance().getTickets();
    }
    @RequestMapping("/agregar")
    public Tickets agregarTicket(@RequestBody Tickets ticket) {
        Set<Pedidos> pedidos = ticket.getPedidos();
        for(Pedidos p: pedidos){
            p.setEstado("PROCESADO");
            DAO.getInstance().update(p);
            Reservas r = p.getReserva();
            r.setEstado("EJECUTADO");
            DAO.getInstance().update(r);
        }
        DAO.getInstance().add(ticket);
        return ticket;
    }
    @RequestMapping("/modificar")
    public Tickets modificarTicket(@RequestBody Tickets ticket){
        DAO.getInstance().update(ticket);
        return ticket;
    }
    @RequestMapping("/borrar/{id}")
    public void BorrarTicket(@PathVariable("id") int id) {
        Tickets ti = DAO.getInstance().getTicket(id);
        DAO.getInstance().delete(ti);
    }
}
