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
public interface BannerRepository extends JpaRepository<Banner, Integer> {
    List<Banner> findByActivoOrderByOrden(Boolean activo);

    @Query("SELECT b FROM Banner b WHERE b.activo = true AND (b.fechaInicio IS NULL OR b.fechaInicio <= CURRENT_DATE) AND (b.fechaFin IS NULL OR b.fechaFin >= CURRENT_DATE) ORDER BY b.orden")
    List<Banner> findBannersActivos();
}