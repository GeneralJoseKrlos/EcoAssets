package com.ecoassets.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "categoria")
public class Categoria {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_categoria")
    private Integer idCategoria;
    
    @Column(nullable = false, length = 50)
    private String nombre;
    
    @ManyToOne
    @JoinColumn(name = "categoria_padre_id")
    private Categoria categoriaPadre;
    
    @OneToMany(mappedBy = "categoriaPadre")
    private List<Categoria> subcategorias = new ArrayList<>();
    
    private Integer nivel = 0;
    
    @OneToMany(mappedBy = "categoria")
    private List<Dispositivo> dispositivos = new ArrayList<>();
    
    public Categoria() {}
    
    public Categoria(String nombre, Integer nivel) {
        this.nombre = nombre;
        this.nivel = nivel;
    }
    
    // Getters y Setters
    public Integer getIdCategoria() { return idCategoria; }
    public void setIdCategoria(Integer idCategoria) { this.idCategoria = idCategoria; }
    
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    
    public Categoria getCategoriaPadre() { return categoriaPadre; }
    public void setCategoriaPadre(Categoria categoriaPadre) { this.categoriaPadre = categoriaPadre; }
    
    public List<Categoria> getSubcategorias() { return subcategorias; }
    public void setSubcategorias(List<Categoria> subcategorias) { this.subcategorias = subcategorias; }
    
    public Integer getNivel() { return nivel; }
    public void setNivel(Integer nivel) { this.nivel = nivel; }
    
    public List<Dispositivo> getDispositivos() { return dispositivos; }
    public void setDispositivos(List<Dispositivo> dispositivos) { this.dispositivos = dispositivos; }
}