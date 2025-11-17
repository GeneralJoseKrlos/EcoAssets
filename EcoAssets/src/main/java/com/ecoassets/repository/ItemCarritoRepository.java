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
public interface ItemCarritoRepository extends JpaRepository<ItemCarrito, Integer> {
    List<ItemCarrito> findByCarrito(Carrito carrito);

    Optional<ItemCarrito> findByCarritoAndDispositivo(Carrito carrito, Dispositivo dispositivo);

    @Query("DELETE FROM ItemCarrito ic WHERE ic.carrito = :carrito")
    void deleteByCarrito(@Param("carrito") Carrito carrito);
}