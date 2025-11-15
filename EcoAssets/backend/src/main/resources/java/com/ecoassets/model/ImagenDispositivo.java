package com.ecoassets.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "imagen_dispositivo")
public class ImagenDispositivo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_imagen")
    private Integer idImagen;
    
    @ManyToOne
    @JoinColumn(name = "dispositivo_codigo", nullable = false)
    private Dispositivo dispositivo;
    
    @Column(name = "url_imagen", nullable = false)
    private String urlImagen;
    
    @Column(name = "es_principal")
    private Boolean esPrincipal = false;
    
    private Integer orden = 0;
    
    // Getters y Setters
    public Integer getIdImagen() { return idImagen; }
    public void setIdImagen(Integer idImagen) { this.idImagen = idImagen; }
    
    public Dispositivo getDispositivo() { return dispositivo; }
    public void setDispositivo(Dispositivo dispositivo) { this.dispositivo = dispositivo; }
    
    public String getUrlImagen() { return urlImagen; }
    public void setUrlImagen(String urlImagen) { this.urlImagen = urlImagen; }
    
    public Boolean getEsPrincipal() { return esPrincipal; }
    public void setEsPrincipal(Boolean esPrincipal) { this.esPrincipal = esPrincipal; }
    
    public Integer getOrden() { return orden; }
    public void setOrden(Integer orden) { this.orden = orden; }
}