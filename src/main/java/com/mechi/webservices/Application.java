package com.mechi.webservices;


import com.mechi.restaurant.DAO.DAO;
import com.mechi.restaurant.modelos.Usuarios;
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

        /*if(DAO.getInstance().getUsuarioPorNombre("admin") == null){
            Usuarios u = new Usuarios();
            u.setEmail("admin@admin.com");
            u.setNombre("admin");
            u.setUsuario("admin@admin.com");
            u.setActivo(true);
            u.setPassword("admin");
            u.setRol("ADMIN");
            DAO.getInstance().add(u);
        }*/
    }
}