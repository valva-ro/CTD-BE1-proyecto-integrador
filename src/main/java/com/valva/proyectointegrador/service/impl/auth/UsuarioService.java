package com.valva.proyectointegrador.service.impl.auth;

import com.valva.proyectointegrador.exceptions.BadRequestException;
import com.valva.proyectointegrador.persistence.entities.auth.Rol;
import com.valva.proyectointegrador.persistence.entities.auth.Usuario;
import com.valva.proyectointegrador.persistence.repository.auth.IRolRepository;
import com.valva.proyectointegrador.persistence.repository.auth.IUsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class UsuarioService implements UserDetailsService {

    private final IUsuarioRepository usuarioRepository;
    private final IRolRepository rolRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public UsuarioService(IUsuarioRepository usuarioRepository, IRolRepository rolRepository, BCryptPasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.rolRepository = rolRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Usuario crear(Usuario usuario) throws BadRequestException {
        if (usuario == null)
            throw new BadRequestException("El usuario no puede ser null");
        if (usuario.getDni() == null)
            throw new BadRequestException("El DNI del usuario no puede ser null");
        rolRepository.saveAll(usuario.getRoles());
        usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
        return usuarioRepository.save(usuario);
    }

    public List<Usuario> consultarTodos() {
        return usuarioRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Usuario> u = usuarioRepository.findByUsername(username);
        if (u.isEmpty())
            throw new UsernameNotFoundException("No existe el usuario con username: " + username);

        Usuario usuario = u.get();
        Set<GrantedAuthority> autorizaciones = new HashSet<>();
        for (Rol rol: usuario.getRoles()) {
            autorizaciones.add(new SimpleGrantedAuthority(rol.getName()));
        }

        return new User(usuario.getEmail(), usuario.getPassword(), true, true, true, true, autorizaciones);
    }
}
