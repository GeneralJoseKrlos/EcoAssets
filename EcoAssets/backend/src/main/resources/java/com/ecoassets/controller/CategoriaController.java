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
@RequestMapping("/api/categorias")
@CrossOrigin(origins = "*")
public class CategoriaController {
    
    @Autowired
    private CategoriaRepository categoriaRepository;
    
    @GetMapping
    public ResponseEntity<List<Categoria>> obtenerTodas() {
        return ResponseEntity.ok(categoriaRepository.findAll());
    }
    
    @GetMapping("/arbol")
    public ResponseEntity<List<Categoria>> obtenerArbol() {
        return ResponseEntity.ok(categoriaRepository.findCategoriasRaizConSubcategorias());
    }
    
    @GetMapping("/nivel/{nivel}")
    public ResponseEntity<List<Categoria>> obtenerPorNivel(@PathVariable Integer nivel) {
        return ResponseEntity.ok(categoriaRepository.findByNivel(nivel));
    }
}