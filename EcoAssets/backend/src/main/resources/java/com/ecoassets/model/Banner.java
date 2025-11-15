package com.ecoassets.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "banner")
public class Banner {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_banner")
    private Integer idBanner;
    
    @Column(length = 100)
    private String titulo;
    
    @Column(name = "url_imagen", nullable = false)
    private String urlImagen;
    
    @Column(name = "url_destino")
    private String urlDestino;
    
    private Integer orden = 0;
    
    private Boolean activo = true;
    
    @Column(name = "fecha_inicio")
    private LocalDate fechaInicio;
    
    @Column(name = "fecha_fin")
    private LocalDate fechaFin;
    
    // Getters y Setters
    public Integer getIdBanner() { return idBanner; }
    public void setIdBanner(Integer idBanner) { this.idBanner = idBanner; }
    
    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }
    
    public String getUrlImagen() { return urlImagen; }
    public void setUrlImagen(String urlImagen) { this.urlImagen = urlImagen; }
    
    public String getUrlDestino() { return urlDestino; }
    public void setUrlDestino(String urlDestino) { this.urlDestino = urlDestino; }
    
    public Integer getOrden() { return orden; }
    public void setOrden(Integer orden) { this.orden = orden; }
    
    public Boolean getActivo() { return activo; }
    public void setActivo(Boolean activo) { this.activo = activo; }
    
    public LocalDate getFechaInicio() { return fechaInicio; }
    public void setFechaInicio(LocalDate fechaInicio) { this.fechaInicio = fechaInicio; }
    
    public LocalDate getFechaFin() { return fechaFin; }
    public void setFechaFin(LocalDate fechaFin) { this.fechaFin = fechaFin; }
}