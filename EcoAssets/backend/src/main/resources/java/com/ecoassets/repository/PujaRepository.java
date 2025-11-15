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
public interface PujaRepository extends JpaRepository<Puja, Integer> {
    List<Puja> findByDispositivo(Dispositivo dispositivo);

    List<Puja> findByUsuario(Usuario usuario);

    List<Puja> findByEstado(EstadoPuja estado);

    @Query("SELECT p FROM Puja p WHERE p.dispositivo = :dispositivo ORDER BY p.monto DESC")
    List<Puja> findByDispositivoOrderByMontoDesc(@Param("dispositivo") Dispositivo dispositivo);

    @Query("SELECT p FROM Puja p WHERE p.dispositivo = :dispositivo AND p.estado = 'PENDIENTE' ORDER BY p.fechaPuja DESC")
    List<Puja> findPujasPendientes(@Param("dispositivo") Dispositivo dispositivo);

    @Query("SELECT MAX(p.monto) FROM Puja p WHERE p.dispositivo = :dispositivo")
    Optional<BigDecimal> findMaxMonto(@Param("dispositivo") Dispositivo dispositivo);
}
