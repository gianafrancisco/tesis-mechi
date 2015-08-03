/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mechi.webservices;

import com.mechi.restaurant.DAO.DAO;
import com.mechi.restaurant.modelos.Reservas;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 *
 * @author mechi
 */
@Component
public class ScheduledVerificarTiempoReservas {

    private static final SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    @Scheduled(fixedRate = 60000)
    public void verificarTiempoReservas() {
        Calendar c = Calendar.getInstance();
        Date dia = c.getTime();
        System.out.println("Buscando Reservas para cancelar "+dateFormat.format(dia)+" "+timeFormat.format(dia));
        List<Reservas> list = DAO.getInstance().getReservasPorCancelar(dia);
        if (list != null) {
            for (Reservas r : list) {
                r.setEstado("AUTO-CANCELADA");
                DAO.getInstance().update(r);
            }
        }
    }
}
