package com.ecoassets.service;

import com.ecoassets.model.*;
import com.ecoassets.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Transactional
    public Usuario registrarUsuario(Usuario usuario) {
        // Verificar si el correo ya existe
        if (usuarioRepository.existsByCorreo(usuario.getCorreo())) {
            throw new RuntimeException("El correo ya está registrado");
        }

        // Verificar si el nombre ya existe
        if (usuarioRepository.existsByNombre(usuario.getNombre())) {
            throw new RuntimeException("El nombre de usuario ya está en uso");
        }

        // Encriptar contraseña
        usuario.setContrasena(passwordEncoder.encode(usuario.getContrasena()));

        return usuarioRepository.save(usuario);
    }

    public Optional<Usuario> autenticar(String correo, String contrasena) {
        Optional<Usuario> usuario = usuarioRepository.findByCorreo(correo);

        if (usuario.isPresent() && passwordEncoder.matches(contrasena, usuario.get().getContrasena())) {
            return usuario;
        }

        return Optional.empty();
    }

    public List<Usuario> obtenerTodos() {
        return usuarioRepository.findAll();
    }

    public long contarUsuariosActivos() {
        return usuarioRepository.contarUsuariosActivos();
    }
}