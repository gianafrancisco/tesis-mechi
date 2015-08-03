package com.mechi.rest.controllers;

import com.mechi.restaurant.DAO.DAO;
import com.mechi.restaurant.modelos.*;
import java.security.Principal;
import java.text.ParseException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@RestController
@RequestMapping("/pedido")
public class PedidoController {

    //PEDIDOS
    @RequestMapping("/pedidos")
    public List<Pedidos> getItemsPedidos(@RequestBody HashMap<String, String> filtro) throws ParseException {
        //return 
        List<Pedidos> pedidos = DAO.getInstance().getPedidos(filtro);
        return pedidos;
    }

    @RequestMapping("/listos")
    public List<Pedidos> getItemsPedidosListo() {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Persona p = DAO.getInstance().getUsuarioPorNombre(auth.getName());
        if (p != null) {
            int id = p.getIdUsuario();
            return DAO.getInstance().getPedidosListo(id);
        }
        return Collections.emptyList();
    }

    @RequestMapping("/entregados")
    public List<Pedidos> getItemsPedidosEntregados() {
        return DAO.getInstance().getPedidosEntregado();
    }

    @RequestMapping("/cocina")
    public List<Pedidos> getItemsPedidosCocina() {
        return DAO.getInstance().getPedidosEnCocina();
    }

    @RequestMapping("/pedido")
    public Pedidos greetingP(@RequestParam(value = "id", defaultValue = "1") int id) {
        return DAO.getInstance().getPedido(id);
    }

    @RequestMapping("/agregar")
    public Pedidos agregarPedido(@RequestBody Pedidos pedido, Principal user) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Persona p = DAO.getInstance().getUsuarioPorNombre(auth.getName());
        pedido.setUsuario(p);
        if (pedido.getReserva().getNroReserva() == 0) {
            DAO.getInstance().add(pedido.getReserva());
        }

        pedido.getReserva().setEstado("EJECUCION");
        DAO.getInstance().update(pedido.getReserva());
        DAO.getInstance().add(pedido);
        return pedido;
    }

    @RequestMapping("/modificar")
    public Pedidos modificarPedido(@RequestBody Pedidos pedido) {
        DAO.getInstance().update(pedido);
        return pedido;
    }

    @RequestMapping("/borrar/{id}")
    public void BorrarPedido(@PathVariable("id") int id) {
        Pedidos pe = DAO.getInstance().getPedido(id);
        DAO.getInstance().delete(pe);
    }

    @RequestMapping("/listo/{id}")
    public void pedidoListo(@PathVariable("id") int id) {
        Pedidos pe = DAO.getInstance().getPedido(id);
        pe.setEstado("LISTO");
        DAO.getInstance().update(pe);
    }

    @RequestMapping("/entregado/{id}")
    public void pedidoEntregado(@PathVariable("id") int id) {
        Pedidos pe = DAO.getInstance().getPedido(id);
        pe.setEstado("ENTREGADO");
        DAO.getInstance().update(pe);
    }
}
