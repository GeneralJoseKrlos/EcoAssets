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
@RequestMapping("/api/pujas")
@CrossOrigin(origins = "*")
public class PujaController {
    
    @Autowired
    private PujaService pujaService;
    
    @Autowired
    private DispositivoService dispositivoService;
    
    @PostMapping
    public ResponseEntity<?> realizarPuja(@RequestBody Map<String, Object> datos) {
        try {
            Integer dispositivoId = (Integer) datos.get("dispositivoId");
            Integer usuarioId = (Integer) datos.get("usuarioId");
            BigDecimal monto = new BigDecimal(datos.get("monto").toString());
            
            // Obtener dispositivo y usuario (simplificado)
            Optional<Dispositivo> dispositivo = dispositivoService.obtenerPorId(dispositivoId);
            
            if (!dispositivo.isPresent()) {
                return ResponseEntity.notFound().build();
            }
            
            // En implementación real, obtener usuario del contexto de seguridad
            // Puja puja = pujaService.realizarPuja(dispositivo.get(), usuario, monto);
            
            return ResponseEntity.ok("Puja realizada exitosamente");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    
    @PostMapping("/{id}/aceptar")
    public ResponseEntity<?> aceptarPuja(@PathVariable Integer id) {
        try {
            pujaService.aceptarPuja(id);
            return ResponseEntity.ok("Puja aceptada");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    
    @GetMapping("/dispositivo/{id}")
    public ResponseEntity<List<Puja>> obtenerPujas(@PathVariable Integer id) {
        Optional<Dispositivo> dispositivo = dispositivoService.obtenerPorId(id);
        
        if (dispositivo.isPresent()) {
            List<Puja> pujas = pujaService.obtenerPujasPorDispositivo(dispositivo.get());
            return ResponseEntity.ok(pujas);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}