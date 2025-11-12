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
@RequestMapping("/api/dispositivos")
@CrossOrigin(origins = "*")
public class DispositivoController {
    
    @Autowired
    private DispositivoService dispositivoService;
    
    @GetMapping
    public ResponseEntity<List<Dispositivo>> obtenerTodos() {
        return ResponseEntity.ok(dispositivoService.obtenerDisponibles());
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerPorId(@PathVariable Integer id) {
        Optional<Dispositivo> dispositivo = dispositivoService.obtenerPorId(id);
        
        if (dispositivo.isPresent()) {
            return ResponseEntity.ok(dispositivo.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    
    @GetMapping("/especiales")
    public ResponseEntity<List<Dispositivo>> obtenerEspeciales() {
        return ResponseEntity.ok(dispositivoService.obtenerEspeciales());
    }
    
    @GetMapping("/subastas")
    public ResponseEntity<List<Dispositivo>> obtenerSubastas() {
        return ResponseEntity.ok(dispositivoService.obtenerSubastas());
    }
    
    @GetMapping("/buscar")
    public ResponseEntity<List<Dispositivo>> buscar(@RequestParam String q) {
        return ResponseEntity.ok(dispositivoService.buscar(q));
    }
    
    @PostMapping
    public ResponseEntity<?> publicar(@RequestBody Map<String, Object> datos) {
        try {
            // Extraer datos del dispositivo
            Dispositivo dispositivo = new Dispositivo();
            dispositivo.setNombre((String) datos.get("nombre"));
            dispositivo.setDescripcion((String) datos.get("descripcion"));
            // ... mapear todos los campos
            
            @SuppressWarnings("unchecked")
            List<String> imagenes = (List<String>) datos.get("imagenes");
            
            Dispositivo guardado = dispositivoService.publicarDispositivo(dispositivo, imagenes);
            return ResponseEntity.ok(guardado);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    
    @GetMapping("/estadisticas")
    public ResponseEntity<Map<String, Object>> obtenerEstadisticas() {
        Map<String, Object> stats = new HashMap<>();
        stats.put("total", dispositivoService.contarDisponibles());
        stats.put("porCategoria", dispositivoService.obtenerEstadisticas());
        return ResponseEntity.ok(stats);
    }
}