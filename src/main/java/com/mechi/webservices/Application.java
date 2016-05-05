package com.mechi.webservices;


import com.mechi.restaurant.DAO.DAO;
import com.mechi.restaurant.modelos.*;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableScheduling
@ComponentScan(basePackages = "com.mechi")
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);

        if(DAO.getInstance().getUsuarioPorNombre("admin") == null){
            Persona u = new Usuarios();
            u.setEmail("admin@admin.com");
            u.setNombre("admin");
            u.setUsuario("admin@admin.com");
            u.setActivo(true);
            u.setPassword("admin");
            u.setRol("ADMIN");
            DAO.getInstance().add(u);

            u = new Mozos();
            u.setEmail("jose@demo.com");
            u.setNombre("Jose");
            u.setUsuario("jose@demo.com");
            u.setActivo(true);
            u.setPassword("jose");
            u.setRol("MOZO");
            DAO.getInstance().add(u);
            Mozos mozo = (Mozos) u;

            TiposPlatos entrada = new TiposPlatos();
            DAO.getInstance().add(entrada);
            TiposPlatos platoPrincipal = new TiposPlatos();
            DAO.getInstance().add(platoPrincipal);
            TiposPlatos bebida = new TiposPlatos();
            DAO.getInstance().add(bebida);
            TiposPlatos postre = new TiposPlatos();
            DAO.getInstance().add(postre);

            Bebidas b = new Bebidas();
            b.setActivo(true);
            b.setDescripcion("Coca Cola");
            b.setNombre("Coca Cola");
            b.setPrecio(14);
            b.setTiposPlatos(bebida);

            DAO.getInstance().add(b);

            Platos p = new Platos();
            p.setTiposPlatos(entrada);
            p.setPrecio(5);
            p.setDescripcion("Empanadas");
            p.setActivo(true);
            DAO.getInstance().add(p);
            p = new Platos();
            p.setTiposPlatos(platoPrincipal);
            p.setPrecio(78);
            p.setDescripcion("Milaneza Napolitana");
            p.setActivo(true);
            DAO.getInstance().add(p);
            p = new Platos();
            p.setTiposPlatos(postre);
            p.setPrecio(25);
            p.setDescripcion("Copa de helado");
            p.setActivo(true);
            DAO.getInstance().add(p);

            Ubicaciones ub = new Ubicaciones();
            ub.setActivo(true);
            ub.setDescripcion("Patio");
            DAO.getInstance().add(ub);

            Mesas m = new Mesas();
            m.setActivo(true);
            m.setDescripcion("Mesa 1");
            m.setNumero(1);
            m.setReservarOnline(true);
            m.setUbicacion(ub);
            DAO.getInstance().add(m);

            Asignaciones as = new Asignaciones();
            as.setMesa(m);
            as.setTurno("noche");
            as.setUsuario(mozo);
            DAO.getInstance().add(as);


        }
    }
}
