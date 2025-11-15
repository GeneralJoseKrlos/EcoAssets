package com.ecoassets.repository;

import com.ecoassets.model.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
    Optional<Usuario> findByCorreo(String correo);

    Optional<Usuario> findByNombre(String nombre);

    boolean existsByCorreo(String correo);

    boolean existsByNombre(String nombre);

    List<Usuario> findByEsAdmin(Boolean esAdmin);

    @Query("SELECT COUNT(u) FROM Usuario u WHERE u.activo = true")
    long contarUsuariosActivos();
}
