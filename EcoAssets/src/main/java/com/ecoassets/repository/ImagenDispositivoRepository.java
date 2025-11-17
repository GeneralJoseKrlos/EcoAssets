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
public interface ImagenDispositivoRepository extends JpaRepository<ImagenDispositivo, Integer> {
    List<ImagenDispositivo> findByDispositivoOrderByOrden(Dispositivo dispositivo);

    Optional<ImagenDispositivo> findByDispositivoAndEsPrincipal(Dispositivo dispositivo, Boolean esPrincipal);
}