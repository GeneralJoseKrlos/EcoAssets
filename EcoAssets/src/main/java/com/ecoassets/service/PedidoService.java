package com.ecoassets.service;

import com.ecoassets.model.*;
import com.ecoassets.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class PedidoService {
    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private CarritoRepository carritoRepository;

    @Autowired
    private ItemCarritoRepository itemCarritoRepository;

    @Transactional
    public Pedido crearPedido(Usuario usuario) {
        // Obtener carrito activo del usuario
        Optional<Carrito> carritoOpt = carritoRepository.findByUsuarioAndActivo(usuario, true);

        if (!carritoOpt.isPresent()) {
            throw new RuntimeException("No hay carrito activo");
        }

        Carrito carrito = carritoOpt.get();
        List<ItemCarrito> items = itemCarritoRepository.findByCarrito(carrito);

        if (items.isEmpty()) {
            throw new RuntimeException("El carrito está vacío");
        }

        // Calcular totales
        BigDecimal subtotal = BigDecimal.ZERO;
        for (ItemCarrito item : items) {
            BigDecimal itemTotal = item.getPrecioUnitario()
                    .multiply(new BigDecimal(item.getCantidad()));
            subtotal = subtotal.add(itemTotal);
        }

        BigDecimal iva = subtotal.multiply(new BigDecimal("0.19")); // 19% IVA
        BigDecimal total = subtotal.add(iva);

        // Crear pedido
        Pedido pedido = new Pedido();
        pedido.setUsuario(usuario);
        pedido.setSubtotal(subtotal);
        pedido.setIva(iva);
        pedido.setTotal(total);
        pedido = pedidoRepository.save(pedido);

        // Crear detalles del pedido
        for (ItemCarrito item : items) {
            DetallePedido detalle = new DetallePedido();
            detalle.setPedido(pedido);
            detalle.setDispositivo(item.getDispositivo());
            detalle.setCantidad(item.getCantidad());
            detalle.setPrecioUnitario(item.getPrecioUnitario());
            detalle.setSubtotal(item.getPrecioUnitario()
                    .multiply(new BigDecimal(item.getCantidad())));
            pedido.getDetalles().add(detalle);
        }

        // Limpiar carrito
        carrito.setActivo(false);
        carritoRepository.save(carrito);

        return pedidoRepository.save(pedido);
    }

    public List<Pedido> obtenerPedidosUsuario(Usuario usuario) {
        return pedidoRepository.findByUsuarioOrderByFechaDesc(usuario);
    }
}