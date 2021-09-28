package com.valva.proyectointegrador.config;

import com.valva.proyectointegrador.persistence.entities.auth.UsuarioRoles;
import com.valva.proyectointegrador.service.impl.auth.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final UsuarioService usuarioService;

    @Autowired
    public WebSecurityConfig(BCryptPasswordEncoder bCryptPasswordEncoder, UsuarioService usuarioService) {
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.usuarioService = usuarioService;
    }

    public WebSecurityConfig(boolean disableDefaults, BCryptPasswordEncoder bCryptPasswordEncoder, UsuarioService usuarioService) {
        super(disableDefaults);
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.usuarioService = usuarioService;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/turnos/**").hasAnyAuthority(UsuarioRoles.USER.name(), UsuarioRoles.ADMIN.name())
                .antMatchers("/odontologos/**", "/pacientes/**").hasAuthority(UsuarioRoles.ADMIN.name())
                .anyRequest().authenticated()
                .and().formLogin().permitAll()
                .and().logout().permitAll()
                .and().exceptionHandling().accessDeniedPage("/403");
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(daoAuthenticationProvider());
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(bCryptPasswordEncoder);
        provider.setUserDetailsService(usuarioService);
        return provider;
    }
}
