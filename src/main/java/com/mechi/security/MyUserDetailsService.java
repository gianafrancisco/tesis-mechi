package com.mechi.security;

import com.mechi.restaurant.DAO.DAO;
import com.mechi.restaurant.modelos.Clientes;
import com.mechi.restaurant.modelos.Mozos;
import com.mechi.restaurant.modelos.Persona;
import com.mechi.restaurant.modelos.Usuarios;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service("userDetailsService")
public class MyUserDetailsService implements UserDetailsService {

    @Override
    public UserDetails loadUserByUsername(final String username)
            throws UsernameNotFoundException {
        List<GrantedAuthority> authorities = null;
        Persona user = DAO.getInstance().getUsuarioPorNombre(username);
        if (user instanceof Clientes) {
            authorities
                    = buildUserAuthority("CLIENTE");
        } else if (user instanceof Mozos) {
            authorities
                    = buildUserAuthority("MOZO");
        } else if (user instanceof Usuarios) {
            authorities
                    = buildUserAuthority("ADMIN");
        }else{
            authorities
                    = buildUserAuthority("GUEST");
        }
        return buildUserForAuthentication(user, authorities);

    }

    private User buildUserForAuthentication(Persona user,
            List<GrantedAuthority> authorities) {
        return new User(user.getUsuario(), user.getPassword(),
                true, true, true, true, authorities);
    }

    private List<GrantedAuthority> buildUserAuthority(String userRole) {

        Set<GrantedAuthority> setAuths = new HashSet<GrantedAuthority>();

        // Build user's authorities
        setAuths.add(new SimpleGrantedAuthority(userRole));

        List<GrantedAuthority> Result = new ArrayList<GrantedAuthority>(setAuths);

        return Result;
    }

}
