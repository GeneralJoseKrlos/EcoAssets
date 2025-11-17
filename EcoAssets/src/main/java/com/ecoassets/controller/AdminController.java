package com.ecoassets.controller;

import com.ecoassets.model.*;
import com.ecoassets.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.math.BigDecimal;
import java.util.*;

@RestController
@RequestMapping("/api/admin")
@CrossOrigin(origins = "*")
public class AdminController {

    @Autowired
    private DispositivoService dispositivoService;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private PedidoRepository pedidoRepository;

    @GetMapping("/dashboard")
    public ResponseEntity<Map<String, Object>> obtenerEstadisticas() {
        Map<String, Object> dashboard = new HashMap<>();

        // Estadísticas generales
        dashboard.put("totalUsuarios", usuarioService.contarUsuariosActivos());
        dashboard.put("totalDispositivos", dispositivoService.contarDisponibles());
        dashboard.put("pedidosActivos", pedidoRepository.contarPedidosActivos());
        dashboard.put("ventasTotales", pedidoRepository.calcularVentasTotales());

        // Estadísticas por categoría
        List<Object[]> porCategoria = dispositivoService.obtenerEstadisticas();
        Map<String, Long> categorias = new HashMap<>();
        for (Object[] cat : porCategoria) {
            categorias.put((String) cat[0], (Long) cat[1]);
        }
        dashboard.put("dispositivosPorCategoria", categorias);

        return ResponseEntity.ok(dashboard);
    }

    @GetMapping("/dispositivos")
    public ResponseEntity<List<Dispositivo>> obtenerTodosDispositivos() {
        return ResponseEntity.ok(dispositivoService.obtenerDisponibles());
    }

    @GetMapping("/usuarios")
    public ResponseEntity<List<Usuario>> obtenerTodosUsuarios() {
        return ResponseEntity.ok(usuarioService.obtenerTodos());
    }
}