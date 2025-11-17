package com.ecoassets.repository;

import com.ecoassets.model.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface CategoriaRepository extends JpaRepository<Categoria, Integer> {
    List<Categoria> findByCategoriaPadreIsNull(); // Categorías raíz

    List<Categoria> findByCategoriaPadre(Categoria padre);

    List<Categoria> findByNivel(Integer nivel);

    Optional<Categoria> findByNombre(String nombre);

    @Query("SELECT c FROM Categoria c LEFT JOIN FETCH c.subcategorias WHERE c.categoriaPadre IS NULL")
    List<Categoria> findCategoriasRaizConSubcategorias();
}
