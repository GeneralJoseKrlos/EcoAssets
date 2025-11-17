package com.ecoassets.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "puja")
public class Puja {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_puja")
    private Integer idPuja;

    @ManyToOne
    @JoinColumn(name = "dispositivo_codigo", nullable = false)
    private Dispositivo dispositivo;

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal monto;

    @Column(name = "fecha_puja")
    private LocalDateTime fechaPuja = LocalDateTime.now();

    @Enumerated(EnumType.STRING)
    private EstadoPuja estado = EstadoPuja.PENDIENTE;

    // Getters y Setters
    public Integer getIdPuja() {
        return idPuja;
    }

    public void setIdPuja(Integer idPuja) {
        this.idPuja = idPuja;
    }

    public Dispositivo getDispositivo() {
        return dispositivo;
    }

    public void setDispositivo(Dispositivo dispositivo) {
        this.dispositivo = dispositivo;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public BigDecimal getMonto() {
        return monto;
    }

    public void setMonto(BigDecimal monto) {
        this.monto = monto;
    }

    public LocalDateTime getFechaPuja() {
        return fechaPuja;
    }

    public void setFechaPuja(LocalDateTime fechaPuja) {
        this.fechaPuja = fechaPuja;
    }

    public EstadoPuja getEstado() {
        return estado;
    }

    public void setEstado(EstadoPuja estado) {
        this.estado = estado;
    }
}
