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
public interface PedidoRepository extends JpaRepository<Pedido, Integer> {
    List<Pedido> findByUsuario(Usuario usuario);

    List<Pedido> findByEstado(EstadoPedido estado);

    @Query("SELECT p FROM Pedido p WHERE p.usuario = :usuario ORDER BY p.fechaPedido DESC")
    List<Pedido> findByUsuarioOrderByFechaDesc(@Param("usuario") Usuario usuario);

    @Query("SELECT COUNT(p) FROM Pedido p WHERE p.estado = 'CONFIRMADO' OR p.estado = 'ENVIADO'")
    long contarPedidosActivos();

    @Query("SELECT SUM(p.total) FROM Pedido p WHERE p.estado = 'CONFIRMADO' OR p.estado = 'ENTREGADO'")
    BigDecimal calcularVentasTotales();
}
