package com.valva.proyectointegrador.persistence.entities.auth;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.Collections;

@Getter
@Entity
public class Usuario implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "usuario_seq")
    @SequenceGenerator(name = "usuario_seq", sequenceName = "usuario_seq", allocationSize = 1)
    @Column
    private Integer id;

    @Setter
    @Column
    private Integer dni;

    @Setter
    @Column
    private String username;

    @Setter
    @Column
    private String email;

    @Setter
    @Column
    private String password;

    @Setter
    @Column
    @Enumerated(EnumType.STRING)
    private UsuarioRoles usuarioRoles;

    public Usuario() {
    }

    public Usuario(Integer dni, String username, String email, String password, UsuarioRoles usuarioRoles) {
        this.dni = dni;
        this.username = username;
        this.email = email;
        this.password = password;
        this.usuarioRoles = usuarioRoles;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(new SimpleGrantedAuthority(usuarioRoles.name()));
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
