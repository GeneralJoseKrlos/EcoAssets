package com.ecoassets.controller;

import com.ecoassets.model.*;
import com.ecoassets.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/api/banners")
@CrossOrigin(origins = "*")
public class BannerController {

    @Autowired
    private BannerRepository bannerRepository;

    @GetMapping
    public ResponseEntity<List<Banner>> obtenerActivos() {
        return ResponseEntity.ok(bannerRepository.findBannersActivos());
    }

    @PostMapping
    public ResponseEntity<Banner> crear(@RequestBody Banner banner) {
        return ResponseEntity.ok(bannerRepository.save(banner));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Banner> actualizar(@PathVariable Integer id, @RequestBody Banner banner) {
        banner.setIdBanner(id);
        return ResponseEntity.ok(bannerRepository.save(banner));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Integer id) {
        bannerRepository.deleteById(id);
        return ResponseEntity.ok("Banner eliminado");
    }
}