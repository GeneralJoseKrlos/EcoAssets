package com.ecoassets.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "dispositivo")
public class Dispositivo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer codigo;

    @Column(nullable = false, length = 100)
    private String nombre;

    @Column(columnDefinition = "TEXT")
    private String descripcion;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EstadoDispositivo estado;

    private Boolean funciona = true;

    @Column(name = "es_especial")
    private Boolean esEspecial = false;

    @Column(name = "anio_fabricacion")
    private Integer anioFabricacion;

    @Column(name = "anio_primera_compra")
    private Integer anioPrimeraCompra;

    @Column(nullable = false)
    private Integer cantidad = 1;

    @Column(name = "precio_base", nullable = false, precision = 12, scale = 2)
    private BigDecimal precioBase;

    @Column(name = "acepta_pujas")
    private Boolean aceptaPujas = false;

    @Column(name = "precio_actual", precision = 12, scale = 2)
    private BigDecimal precioActual;

    private Boolean vendido = false;

    @Column(name = "fecha_publicacion")
    private LocalDateTime fechaPublicacion = LocalDateTime.now();

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "marca_id", nullable = false)
    private Marca marca;

    @ManyToOne
    @JoinColumn(name = "categoria_id", nullable = false)
    private Categoria categoria;

    @OneToMany(mappedBy = "dispositivo", cascade = CascadeType.ALL)
    private List<ImagenDispositivo> imagenes = new ArrayList<>();

    @OneToMany(mappedBy = "dispositivo", cascade = CascadeType.ALL)
    private List<Puja> pujas = new ArrayList<>();

    // Constructores, getters y setters
    public Dispositivo() {
    }

    // Getters y Setters completos
    public Integer getCodigo() {
        return codigo;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public EstadoDispositivo getEstado() {
        return estado;
    }

    public void setEstado(EstadoDispositivo estado) {
        this.estado = estado;
    }

    public Boolean getFunciona() {
        return funciona;
    }

    public void setFunciona(Boolean funciona) {
        this.funciona = funciona;
    }

    public Boolean getEsEspecial() {
        return esEspecial;
    }

    public void setEsEspecial(Boolean esEspecial) {
        this.esEspecial = esEspecial;
    }

    public Integer getAnioFabricacion() {
        return anioFabricacion;
    }

    public void setAnioFabricacion(Integer anioFabricacion) {
        this.anioFabricacion = anioFabricacion;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public BigDecimal getPrecioBase() {
        return precioBase;
    }

    public void setPrecioBase(BigDecimal precioBase) {
        this.precioBase = precioBase;
        if (this.precioActual == null) {
            this.precioActual = precioBase;
        }
    }

    public Boolean getAceptaPujas() {
        return aceptaPujas;
    }

    public void setAceptaPujas(Boolean aceptaPujas) {
        this.aceptaPujas = aceptaPujas;
    }

    public BigDecimal getPrecioActual() {
        return precioActual;
    }

    public void setPrecioActual(BigDecimal precioActual) {
        this.precioActual = precioActual;
    }

    public Boolean getVendido() {
        return vendido;
    }

    public void setVendido(Boolean vendido) {
        this.vendido = vendido;
    }

    public LocalDateTime getFechaPublicacion() {
        return fechaPublicacion;
    }

    public void setFechaPublicacion(LocalDateTime fechaPublicacion) {
        this.fechaPublicacion = fechaPublicacion;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Marca getMarca() {
        return marca;
    }

    public void setMarca(Marca marca) {
        this.marca = marca;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public List<ImagenDispositivo> getImagenes() {
        return imagenes;
    }

    public void setImagenes(List<ImagenDispositivo> imagenes) {
        this.imagenes = imagenes;
    }

    public List<Puja> getPujas() {
        return pujas;
    }

    public void setPujas(List<Puja> pujas) {
        this.pujas = pujas;
    }
}