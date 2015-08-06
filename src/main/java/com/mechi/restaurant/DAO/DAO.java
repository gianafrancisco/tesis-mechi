package com.mechi.restaurant.DAO;

import com.mechi.restaurant.modelos.*;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.Query;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.Projections;

public class DAO {

    private static DAO instance = null;
    private static SessionFactory sessionFactory;

    private DAO() {
        try {
            //Configuration cfg = new Configuration().configure();
            //cfg.setProperty("connection.username", System.getProperty("connection.username","sa"));
            //cfg.setProperty("connection.password", System.getProperty("connection.username","roadrunner"));
            sessionFactory = new Configuration().configure().buildSessionFactory();
            //sessionFactory = cfg.buildSessionFactory();
        } catch (HibernateException he) {
            System.err.println("Ocurrió un error en la inicialización de la SessionFactory: " + he);
            throw new ExceptionInInitializerError(he);
        }
    }

    public static DAO getInstance() {
        if (instance == null) {
            instance = new DAO();
        }
        return instance;
    }

    //UBICACIONES
    public List<Ubicaciones> getUbicaciones(final int currentPage, final int itemsPerPage) {
        Session session = sessionFactory.openSession();
        Transaction tx = null;
        List ubicaciones = null;
        try {
            tx = session.beginTransaction();
            Query q = session.createQuery("FROM Ubicaciones WHERE activo = 1");
            q.setMaxResults(itemsPerPage);
            q.setFirstResult(itemsPerPage * (currentPage - 1));
            ubicaciones = q.list();
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
        return ubicaciones;
    }

    public Ubicaciones getUbicacion(int id) {
        Session session = sessionFactory.openSession();
        Transaction tx = null;
        Ubicaciones ubicaciones = null;
        try {
            tx = session.beginTransaction();
            ubicaciones = (Ubicaciones) session.get(Ubicaciones.class, id);
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
        return ubicaciones;
    }

    //MESAS
    public List<Mesas> getMesas(final int currentPage, final int itemsPerPage) {
        Session session = sessionFactory.openSession();
        Transaction tx = null;
        List mesas = null;
        try {
            tx = session.beginTransaction();
            Query q = session.createQuery("FROM Mesas WHERE activo = 1");
            q.setMaxResults(itemsPerPage);
            q.setFirstResult(itemsPerPage * (currentPage - 1));
            mesas = q.list();
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
        return mesas;
    }

    public List<Mesas> getMesasSinReservas(String fecha, String hora) throws ParseException {
        Session session = sessionFactory.openSession();
        Transaction tx = null;
        List mesas = null;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date reserva = format.parse(fecha + " " + hora);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(reserva);
        calendar.add(Calendar.HOUR, 2);
        Date hasta = calendar.getTime();
        calendar.add(Calendar.HOUR, -4);
        Date desde = calendar.getTime();
        //String horaDesde = 
        try {
            tx = session.beginTransaction();
            Query q = session.createQuery("FROM Mesas as m WHERE m.activo = 1 AND m.reservarOnline = 1 AND m.idMesa NOT IN ( SELECT r.idMesa FROM Reservas as r WHERE r.fechaReserva > :horaDesde AND r.fechaReserva < :horaHasta AND estado = 'ACTIVA')");
            //Query q = session.createQuery("FROM Mesas as m WHERE m.activo = 1 AND m.reservarOnline = 1 AND m.idMesa NOT IN ( SELECT r.idMesa FROM Reservas as r WHERE r.fecha=:fecha AND r.hora > :horaDesde AND r.hora < :horaHasta AND estado = 'ACTIVA')");
            //Query q= session.createQuery("FROM Mesas as m WHERE m.activo = 1 AND m.idMesa NOT IN ( SELECT r.idMesa FROM Reservas as r WHERE r.fecha=:fecha AND DATE(r.hora) > DATE('19:00:00') AND DATE(r.hora) < DATE('22:00:00') AND estado = 'ACTIVA')");
            //q.setParameter("fecha", fecha).setTime("horaDesde", desde).setTime("horaHasta", hasta);
            q.setDate("horaDesde", desde);
            q.setDate("horaHasta", hasta);
            //q.setParameter("fecha", fecha);
            mesas = q.list();
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
        return mesas;
    }

    public Mesas getMesa(int id) {
        Session session = sessionFactory.openSession();
        Transaction tx = null;
        Mesas mesas = null;
        try {
            tx = session.beginTransaction();
            mesas = (Mesas) session.get(Mesas.class, id);
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
        return mesas;
    }

    //PEDIDOS
    public List<Pedidos> getPedidos() {
        Session session = sessionFactory.openSession();
        Transaction tx = null;
        List pedidos = null;
        try {
            tx = session.beginTransaction();
            pedidos = session.createQuery("FROM Pedidos").list();
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
        return pedidos;
    }

    public List<Pedidos> getPedidos(HashMap<String, String> filtro) throws ParseException {
        Session session = sessionFactory.openSession();
        Transaction tx = null;
        List pedidos = null;
        String fechaDesde = filtro.get("fechaDesde") + " 00:00:00";
        String fechaHasta = filtro.get("fechaHasta") + " 23:59:59";
        String idUsuario = filtro.get("idMozo");
        try {
            tx = session.beginTransaction();
            //Query q = session.createQuery("FROM Pedidos as p WHERE estado = 'PROCESADO' ORDER BY p.fecha DESC");
            Query q = session.createQuery("FROM Pedidos as p WHERE p.fecha >=:fechaDesde AND p.fecha <=:fechaHasta AND estado = 'PROCESADO' ORDER BY p.fecha DESC");
            if (!idUsuario.equals("")) {
                q = session.createQuery("FROM Pedidos as p WHERE p.fecha >=:fechaDesde AND p.fecha <=:fechaHasta AND estado = 'PROCESADO' AND idUsuario=:idUsuario ORDER BY p.fecha DESC");
                q.setParameter("idUsuario", idUsuario);
            }
            DateFormat formatter = new SimpleDateFormat("yy-MM-dd hh:mm:ss");
            q.setTimestamp("fechaDesde", formatter.parse(fechaDesde));
            q.setTimestamp("fechaHasta", formatter.parse(fechaHasta));
            pedidos = q.list();
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
        return pedidos;
    }

    public List<Pedidos> getPedidosEnCocina() {
        Session session = sessionFactory.openSession();
        Transaction tx = null;
        List pedidos = null;
        try {
            tx = session.beginTransaction();
            pedidos = session.createQuery("FROM Pedidos WHERE estado = 'NUEVO' order by idPedido desc").list();
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
        return pedidos;
    }

    public List<Pedidos> getPedidosListo(int idUsuario) {
        Session session = sessionFactory.openSession();
        Transaction tx = null;
        List pedidos = null;
        try {
            tx = session.beginTransaction();
            Query q = session.createQuery("FROM Pedidos WHERE estado = 'LISTO' AND idUsuario =:idUsuario order by idPedido desc");
            q.setParameter("idUsuario", idUsuario);
            pedidos = q.list();
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
        return pedidos;
    }

    public List<Pedidos> getPedidosEntregado() {
        Session session = sessionFactory.openSession();
        Transaction tx = null;
        List pedidos = null;
        try {
            tx = session.beginTransaction();
            pedidos = session.createQuery("FROM Pedidos WHERE estado = 'ENTREGADO' order by idPedido desc").list();
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
        return pedidos;
    }

    public Pedidos getPedido(int id) {
        Session session = sessionFactory.openSession();
        Transaction tx = null;
        Pedidos pedidos = null;
        try {
            tx = session.beginTransaction();
            pedidos = (Pedidos) session.get(Pedidos.class, id);
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
        return pedidos;
    }

    //PLATOS
    public List<Platos> getPlatos(final int currentPage, final int itemsPerPage) {
        Session session = sessionFactory.openSession();
        Transaction tx = null;
        List platos = null;
        try {
            tx = session.beginTransaction();
            Query q = session.createQuery("FROM Platos WHERE activo = 1");
            q.setMaxResults(itemsPerPage);
            q.setFirstResult(itemsPerPage * (currentPage - 1));
            platos = q.list();
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
        return platos;
    }

    public Platos getPlato(int id) {
        Session session = sessionFactory.openSession();
        Transaction tx = null;
        Platos platos = null;
        try {
            tx = session.beginTransaction();
            platos = (Platos) session.get(Platos.class, id);
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
        return platos;
    }

    //TIPOSPLATOS
    public List<TiposPlatos> getTiposPlatos(final int currentPage, final int itemsPerPage) {
        Session session = sessionFactory.openSession();
        Transaction tx = null;
        List tiposPlatos = null;
        try {
            tx = session.beginTransaction();
            Query q = session.createQuery("FROM TiposPlatos WHERE activo = 1");
            q.setMaxResults(itemsPerPage);
            q.setFirstResult(itemsPerPage * (currentPage - 1));
            tiposPlatos = q.list();
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
        return tiposPlatos;
    }

    public TiposPlatos getTiposPlato(int id) {
        Session session = sessionFactory.openSession();
        Transaction tx = null;
        TiposPlatos tiposPlatos = null;
        try {
            tx = session.beginTransaction();
            tiposPlatos = (TiposPlatos) session.get(TiposPlatos.class, id);
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
        return tiposPlatos;
    }

    //USUARIOS
    public List<Usuarios> getUsuarios(final int currentPage, final int itemsPerPage, final String filter) {
        Session session = sessionFactory.openSession();
        Transaction tx = null;
        List usuarios = null;
        try {
            tx = session.beginTransaction();
            Criteria c = session.createCriteria(Usuarios.class);
            c.add(Restrictions.eq("activo", Boolean.TRUE));
            if (!filter.equals("")) {
                c.add(Restrictions.or(
                        Restrictions.like("usuario", "%" + filter + "%"),
                        Restrictions.like("nombre", "%" + filter + "%"),
                        Restrictions.like("apellido", "%" + filter + "%"),
                        Restrictions.like("telefono", "%" + filter + "%"),
                        Restrictions.like("calle", "%" + filter + "%"),
                        Restrictions.like("fechaNacimiento", "%" + filter + "%")
                ));
            }

            c.setMaxResults(itemsPerPage);
            c.setFirstResult(itemsPerPage * (currentPage - 1));
            usuarios = c.list();
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
        return usuarios;
    }

    public Usuarios getUsuario(int id) {
        Session session = sessionFactory.openSession();
        Transaction tx = null;
        Usuarios usuarios = null;
        try {
            tx = session.beginTransaction();
            usuarios = (Usuarios) session.get(Usuarios.class, id);
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
        return usuarios;
    }

    //BEBIDAS
    public List<Bebidas> getBebidas(final int currentPage, final int itemsPerPage) {
        Session session = sessionFactory.openSession();
        Transaction tx = null;
        List bebidas = null;
        try {
            tx = session.beginTransaction();
            Query q = session.createQuery("FROM Bebidas WHERE activo = 1");
            q.setMaxResults(itemsPerPage);
            q.setFirstResult(itemsPerPage * (currentPage - 1));
            bebidas = q.list();
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
        return  bebidas;
    }

    public Bebidas getBebida(int id) {
        Session session = sessionFactory.openSession();
        Transaction tx = null;
        Bebidas bebidas = null;
        try {
            tx = session.beginTransaction();
            bebidas = (Bebidas) session.get(Bebidas.class, id);
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
        return bebidas;
    }

    //TICKETS
    public List<Tickets> getTickets() {
        Session session = sessionFactory.openSession();
        Transaction tx = null;
        List tickets = null;
        try {
            tx = session.beginTransaction();
            tickets = session.createQuery("FROM Tickets").list();
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
        return tickets;
    }

    public Tickets getTicket(int id) {
        Session session = sessionFactory.openSession();
        Transaction tx = null;
        Tickets tickets = null;
        try {
            tx = session.beginTransaction();
            tickets = (Tickets) session.get(Tickets.class, id);
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
        return tickets;
    }

    //CLIENTES
    public List<Clientes> getClientes(final int currentPage, final int itemsPerPage, final String filter) {
        Session session = sessionFactory.openSession();
        Transaction tx = null;
        List clientes = null;
        try {
            tx = session.beginTransaction();
            Criteria c = session.createCriteria(Clientes.class);
            c.add(Restrictions.eq("activo", Boolean.TRUE));
            if (!filter.equals("")) {
                c.add(Restrictions.or(
                        Restrictions.like("usuario", "%" + filter + "%"),
                        Restrictions.like("nombre", "%" + filter + "%"),
                        Restrictions.like("apellido", "%" + filter + "%"),
                        Restrictions.like("telefono", "%" + filter + "%"),
                        Restrictions.like("calle", "%" + filter + "%"),
                        Restrictions.like("fechaNacimiento", "%" + filter + "%")
                ));
            }

            c.setMaxResults(itemsPerPage);
            c.setFirstResult(itemsPerPage * (currentPage - 1));
            clientes = c.list();
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
        return clientes;
    }

    public Clientes getCliente(int id) {
        Session session = sessionFactory.openSession();
        Transaction tx = null;
        Clientes clientes = null;
        try {
            tx = session.beginTransaction();
            clientes = (Clientes) session.get(Clientes.class, id);
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
        return clientes;
    }

    public Persona getUsuarioPorNombre(String username) {
        Session session = sessionFactory.openSession();
        Transaction tx = null;
        Persona clientes = null;
        try {
            tx = session.beginTransaction();
            Query q = session.createQuery("FROM Persona WHERE usuario = :usuario").setParameter("usuario", username);
            clientes = (Persona) q.list().get(0);
            tx.commit();

        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
        return clientes;
    }

    //RESERVAS
    public List<Reservas> getReservas(HashMap<String, String> filtro, final int currentPage, final int itemsPerPage) throws ParseException {
        Session session = sessionFactory.openSession();
        Transaction tx = null;
        List reservas = null;
        String fechaDesde = filtro.get("fechaDesde");
        String fechaHasta = filtro.get("fechaHasta");
        String horaDesde = filtro.get("horaDesde");
        String horaHasta = filtro.get("horaHasta");
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        Date desde = format.parse(fechaDesde + " " + horaDesde);
        Date hasta = format.parse(fechaHasta + " " + horaHasta);
        try {
            tx = session.beginTransaction();

            Query q = session.createQuery("FROM Reservas as r WHERE "
                    + "day(r.fechaReserva) >= day(:fechaDesde) AND "
                    + "day(r.fechaReserva) <= day(:fechaHasta) AND "
                    + "month(r.fechaReserva) >= month(:fechaDesde) AND "
                    + "month(r.fechaReserva) <= month(:fechaHasta) AND "
                    + "year(r.fechaReserva) >= year(:fechaDesde) AND "
                    + "year(r.fechaReserva) <= year(:fechaHasta) AND "
                    + "hour(r.fechaReserva) >= hour(:fechaDesde) AND "
                    + "hour(r.fechaReserva) <= hour(:fechaHasta) AND "
                    + "minute(r.fechaReserva) >= minute(:fechaDesde) AND "
                    + "minute(r.fechaReserva) <= minute(:fechaHasta) "
                    + "order by r.fechaReserva DESC");
            q.setTimestamp("fechaDesde", desde);
            q.setTimestamp("fechaHasta", hasta);
            q.setMaxResults(itemsPerPage);
            q.setFirstResult(itemsPerPage * (currentPage - 1));
            reservas = q.list();
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
        return reservas;
    }

    public int getSizeReservasEntreFechas(HashMap<String, String> filtro) throws ParseException {
        Session session = sessionFactory.openSession();
        Transaction tx = null;
        List reservas = null;
        String fechaDesde = filtro.get("fechaDesde");
        String fechaHasta = filtro.get("fechaHasta");
        String horaDesde = filtro.get("horaDesde");
        String horaHasta = filtro.get("horaHasta");
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        Date desde = format.parse(fechaDesde + " " + horaDesde);
        Date hasta = format.parse(fechaHasta + " " + horaHasta);
        int count = 0;
        try {
            tx = session.beginTransaction();
            Query q = session.createQuery("FROM Reservas as r WHERE "
                    + "day(r.fechaReserva) >= day(:fechaDesde) AND "
                    + "day(r.fechaReserva) <= day(:fechaHasta) AND "
                    + "month(r.fechaReserva) >= month(:fechaDesde) AND "
                    + "month(r.fechaReserva) <= month(:fechaHasta) AND "
                    + "year(r.fechaReserva) >= year(:fechaDesde) AND "
                    + "year(r.fechaReserva) <= year(:fechaHasta) AND "
                    + "hour(r.fechaReserva) >= hour(:fechaDesde) AND "
                    + "hour(r.fechaReserva) <= hour(:fechaHasta) AND "
                    + "minute(r.fechaReserva) >= minute(:fechaDesde) AND "
                    + "minute(r.fechaReserva) <= minute(:fechaHasta) "
                    + "order by r.fecha DESC");
            q.setTimestamp("fechaDesde", desde);
            q.setTimestamp("fechaHasta", hasta);
            count = q.list().size();
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
        return count;
    }

    public int gteSizeReservasEntreFechas(HashMap<String, String> filtro) {
        Session session = sessionFactory.openSession();
        Transaction tx = null;
        List reservas = null;
        String fechaDesde = filtro.get("fechaDesde");
        String fechaHasta = filtro.get("fechaHasta");
        int count = 0;
        try {
            tx = session.beginTransaction();
            Query q = session.createQuery("FROM Reservas as r WHERE r.fecha>=:fechaDesde AND r.fecha <= :fechaHasta order by r.fecha DESC");
            q.setParameter("fechaDesde", fechaDesde);
            q.setParameter("fechaHasta", fechaHasta);
            count = q.list().size();
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
        return count;
    }

    public List<Reservas> getReservasActivas() {
        Session session = sessionFactory.openSession();
        Transaction tx = null;
        Calendar c = Calendar.getInstance();
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        Date hoy = c.getTime();
        c.add(Calendar.HOUR, 24);
        Date manana = c.getTime();
        List reservas = null;
        try {
            tx = session.beginTransaction();
            Query q = session.createQuery("FROM Reservas WHERE estado = 'ACTIVA' AND fechaReserva >= :hoy AND fechaReserva <= :manana");
            q.setDate("hoy", hoy);
            q.setDate("manana", manana);
            reservas = q.list();

            tx.commit();

        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
        return reservas;
    }

    public ArrayList<Reservas> getReservasActivasPorMozo(int idMozo) {

        List<Reservas> reservas = getReservasActivas();
        List<Reservas> reservasFiltradas = new ArrayList<Reservas>();
        List<Asignaciones> asignaciones = getAsignacionesPorMozo(idMozo);
        Set<Mesas> mesasAsignadas = new HashSet<Mesas>();
        for (Asignaciones a : asignaciones) {
            mesasAsignadas.add(a.getMesa());
        }
        for (Reservas r : reservas) {
            if (mesasAsignadas.contains(r.getMesa())) {
                reservasFiltradas.add(r);
            }
        }
        return (ArrayList<Reservas>) reservasFiltradas;
        /*
         Session session = sessionFactory.openSession();
         Transaction tx = null;
         List reservas = null;
         try {
         tx = session.beginTransaction();
         reservas = session.createQuery("FROM Reservas WHERE estado = 'ACTIVA'").list();
         tx.commit();

         } catch (HibernateException e) {
         if (tx != null) {
         tx.rollback();
         }
         e.printStackTrace();
         } finally {
         session.close();
         }
         return (ArrayList<Reservas>) reservas;
         */
    }

    public List<Reservas> getReservasPorUsuario(final int id, final int currentPage, final int itemsPerPage) {
        Session session = sessionFactory.openSession();
        Transaction tx = null;
        List reservas = null;
        Reservas r = null;
        try {
            tx = session.beginTransaction();
            //Query q = session.createQuery("FROM Reservas WHERE idUsuario = :idUsuario ORDER BY fecha DESC,hora DESC").setParameter("idUsuario", id);
            Query q = session.createQuery("FROM Reservas WHERE idUsuario = :idUsuario ORDER BY nroReserva DESC").setParameter("idUsuario", id);
            //List allElements = q.list();
            //Query q1 = session.createFilter(allElements, "");
            q.setMaxResults(itemsPerPage);
            q.setFirstResult(itemsPerPage * (currentPage - 1));
            /*
             int desde = (currentPage-1)*itemsPerPage;
             int hasta = (currentPage)*itemsPerPage;
             reservas = new ArrayList<Reservas>();
            
             for(int i = 0; i < hasta && i < allElements.size(); i++){
             if(i>= desde && i< hasta){
             reservas.add(allElements.get(i));
             }
             }*/
            reservas = q.list();
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
        return reservas;
    }

    public long getSizeReservasPorUsuario(final int id) {
        Session session = sessionFactory.openSession();
        Transaction tx = null;
        List reservas = null;
        long count = 0;
        try {
            tx = session.beginTransaction();
            Query q = session.createQuery("SELECT COUNT(*) FROM Reservas WHERE idUsuario = :idUsuario").setParameter("idUsuario", id);
            count = ((Long) q.iterate().next());
            //reservas = .list();
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
        return count;
    }
    /*
     public List<Reservas> getReservasPorCancelar(String dia, String hora) {
     Session session = sessionFactory.openSession();
     Transaction tx = null;
     List reservas = null;
     try {
     tx = session.beginTransaction();
     Query q = session.createQuery("FROM Reservas as r WHERE (r.fecha < :fecha OR (r.fecha=:fecha AND r.hora < :hora)) AND r.estado = 'ACTIVA'");
     q.setParameter("hora", hora);
     q.setParameter("fecha", dia);
     reservas = q.list();
     tx.commit();
     } catch (HibernateException e) {
     if (tx != null) {
     tx.rollback();
     }
     e.printStackTrace();
     } finally {
     session.close();
     }
     return (ArrayList<Reservas>) reservas;
     }
     */

    public List<Reservas> getReservasPorCancelar(Date fechaHora) {
        Session session = sessionFactory.openSession();
        Transaction tx = null;
        List reservas = null;
        //SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            tx = session.beginTransaction();
            Query q = session.createQuery("FROM Reservas as r WHERE r.fechaReserva <= :fecha AND r.estado = 'ACTIVA'");
            q.setTimestamp("fecha", fechaHora);
            reservas = q.list();
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
        return reservas;
    }

    public Reservas getReserva(int id) {
        Session session = sessionFactory.openSession();
        Transaction tx = null;
        Reservas reservas = null;
        try {
            tx = session.beginTransaction();
            reservas = (Reservas) session.get(Reservas.class, id);
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
        return reservas;
    }

    //PROMOCIONES
    public List getPromociones(final int currentPage, final int itemsPerPage) {
        Session session = sessionFactory.openSession();
        Transaction tx = null;
        List promociones = null;
        try {
            tx = session.beginTransaction();
            Query q = session.createQuery("FROM Promociones WHERE activo = 1");
            q.setMaxResults(itemsPerPage);
            q.setFirstResult(itemsPerPage * (currentPage - 1));
            promociones = q.list();
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
        return promociones;
    }

    public List getPromocionesDisponibles(final int currentPage, final int itemsPerPage) {
        Session session = sessionFactory.openSession();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        Date dia = c.getTime();
        Transaction tx = null;
        List promociones = null;
        try {
            tx = session.beginTransaction();
            Query q = session.createQuery("FROM Promociones WHERE activo = 1 AND fechaDesde <= :fecha AND fechaHasta >= :fecha");
            q.setDate("fecha", dia);
            q.setMaxResults(itemsPerPage);
            q.setFirstResult(itemsPerPage * (currentPage - 1));
            promociones = q.list();
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
        return promociones;
    }

    public Promociones getPromocion(int id) {
        Session session = sessionFactory.openSession();
        Transaction tx = null;
        Promociones promociones = null;
        try {
            tx = session.beginTransaction();
            promociones = (Promociones) session.get(Promociones.class, id);
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
        return promociones;
    }

    //MOZOS
    public List getMozos(final int currentPage, final int itemsPerPage, final String filter) {
        Session session = sessionFactory.openSession();
        Transaction tx = null;
        List mozos = null;
        Query q = null;
        Criteria c = session.createCriteria(Mozos.class);
        c.add(Restrictions.eq("activo", Boolean.TRUE));
        try {
            tx = session.beginTransaction();
            if (!filter.equals("")) {
                c.add(Restrictions.or(
                        Restrictions.like("usuario", "%" + filter + "%"),
                        Restrictions.like("nombre", "%" + filter + "%"),
                        Restrictions.like("apellido", "%" + filter + "%"),
                        Restrictions.like("telefono", "%" + filter + "%"),
                        Restrictions.like("calle", "%" + filter + "%"),
                        Restrictions.like("fechaNacimiento", "%" + filter + "%")
                ));
            }

            c.setMaxResults(itemsPerPage);
            c.setFirstResult(itemsPerPage * (currentPage - 1));
            mozos = c.list();
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
        return mozos;
    }

    public Mozos getMozo(int id) {
        Session session = sessionFactory.openSession();
        Transaction tx = null;
        Mozos mozos = null;
        try {
            tx = session.beginTransaction();
            mozos = (Mozos) session.get(Mozos.class, id);
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
        return mozos;
    }

    //ASIGNACIONES
    public List getAsignaciones(final int currentPage, final int itemsPerPage) {
        Session session = sessionFactory.openSession();
        Transaction tx = null;
        List asignaciones = null;
        try {
            tx = session.beginTransaction();
            Query q = session.createQuery("FROM Asignaciones");
            q.setMaxResults(itemsPerPage);
            q.setFirstResult(itemsPerPage * (currentPage - 1));
            asignaciones = q.list();
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
        return asignaciones;
    }

    public List getAsignacionesPorMozo(final int idMozo) {
        Session session = sessionFactory.openSession();
        Transaction tx = null;
        List asignaciones = null;
        try {
            tx = session.beginTransaction();
            Query q = session.createQuery("FROM Asignaciones WHERE idUsuario =:idUsuario");
            q.setParameter("idUsuario", idMozo);
            asignaciones = q.list();
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
        return asignaciones;
    }

    public Asignaciones getAsignacion(int id) {
        Session session = sessionFactory.openSession();
        Transaction tx = null;
        Asignaciones asignaciones = null;
        try {
            tx = session.beginTransaction();
            asignaciones = (Asignaciones) session.get(Asignaciones.class, id);
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
        return asignaciones;
    }

    //INSERTS
    public void add(Object u) {
        Session s = sessionFactory.openSession();
        s.beginTransaction();
        s.save(u);
        s.getTransaction().commit();
        s.close();
    }

    public void addPedidoToUsuarios(Usuarios u, Pedidos p) {
        Session s = sessionFactory.openSession();
        s.beginTransaction();

        //u.getPedidos().add(p);
        s.getTransaction().commit();
        s.close();
    }

    //VALIDAR MAIL
    public boolean validarEmail(String email) {
        Session session = sessionFactory.openSession();
        Transaction tx = null;
        List personas = null;
        try {
            tx = session.beginTransaction();
            personas = session.createQuery("FROM Persona AS U WHERE U.usuario = :email AND activo = 1").setParameter("email", email).list();
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
        return personas != null && personas.size() > 0;
    }

    //VALIDAR UBICACIONES
    public boolean validarUbicacion(String descripcion) {
        Session session = sessionFactory.openSession();
        Transaction tx = null;
        List descripciones = null;
        try {
            tx = session.beginTransaction();
            descripciones = session.createQuery("FROM Ubicaciones u where u.descripcion = :descripcion").setParameter("descripcion", descripcion).list();
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
        return descripciones != null && descripciones.size() > 0;
    }

    //VALIDAR TIPOS DE PLATOS
    public boolean validarTipoPlato(String descripcion) {
        Session session = sessionFactory.openSession();
        Transaction tx = null;
        List descripciones = null;
        try {
            tx = session.beginTransaction();
            descripciones = session.createQuery("FROM TiposPlatos tp where tp.descripcion = :descripcion").setParameter("descripcion", descripcion).list();
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
        return descripciones != null && descripciones.size() > 0;
    }

    //VALIDAR PROMOCIÓN
    public boolean validarPromocion(String descripcion) {
        Session session = sessionFactory.openSession();
        Transaction tx = null;
        List promociones = null;
        try {
            tx = session.beginTransaction();
            promociones = session.createQuery("FROM Promociones p where p.descripcion = :descripcion").setParameter("descripcion", descripcion).list();
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
        return promociones != null && promociones.size() > 0;
    }

    //OPEN SESSION
    public Session getSession() {
        return sessionFactory.openSession();
    }

    public void delete(Object u) {
        Session s = sessionFactory.openSession();
        s.beginTransaction();

        s.delete(u);
        s.getTransaction().commit();
        s.close();
    }

    public void update(Object u) {
        Session s = sessionFactory.openSession();
        s.beginTransaction();

        s.update(u);
        s.getTransaction().commit();
        s.close();
    }

    public long getSize(Class T, final String filter) {
        Session session = sessionFactory.openSession();
        Transaction tx = null;
        long count = 0;
        List rowCount = null;
        try {
            tx = session.beginTransaction();
            Criteria c = session.createCriteria(T);
            c.setProjection(Projections.rowCount());

            if (!filter.equals("")) {
                c.add(Restrictions.eq("activo", Boolean.TRUE));
                c.add(Restrictions.or(
                        Restrictions.like("usuario", "%" + filter + "%"),
                        Restrictions.like("nombre", "%" + filter + "%"),
                        Restrictions.like("apellido", "%" + filter + "%"),
                        Restrictions.like("telefono", "%" + filter + "%"),
                        Restrictions.like("calle", "%" + filter + "%"),
                        Restrictions.like("fechaNacimiento", "%" + filter + "%")
                ));
            }

            rowCount = c.list();
            //Query q = session.createQuery("SELECT COUNT(*) FROM " + table);
            //count = ((Long) q.iterate().next());
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
        return (Long) (rowCount != null ? rowCount.get(0) : 0);
    }
}
