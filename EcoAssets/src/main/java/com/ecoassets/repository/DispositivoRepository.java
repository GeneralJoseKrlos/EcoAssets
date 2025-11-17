package com.ecoassets.repository;

import com.ecoassets.model.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
public interface DispositivoRepository extends JpaRepository<Dispositivo, Integer> {
    List<Dispositivo> findByCategoria(Categoria categoria);

    List<Dispositivo> findByMarca(Marca marca);

    List<Dispositivo> findByUsuario(Usuario usuario);

    List<Dispositivo> findByVendido(Boolean vendido);

    List<Dispositivo> findByEsEspecial(Boolean esEspecial);

    @Query("SELECT d FROM Dispositivo d WHERE d.vendido = false ORDER BY d.fechaPublicacion DESC")
    List<Dispositivo> findDispositivosDisponibles();

    @Query("SELECT d FROM Dispositivo d WHERE d.vendido = false AND d.esEspecial = true")
    List<Dispositivo> findDispositivosEspeciales();

    @Query("SELECT d FROM Dispositivo d WHERE d.vendido = false AND d.aceptaPujas = true")
    List<Dispositivo> findDispositivosEnSubasta();

    @Query("SELECT d FROM Dispositivo d WHERE d.nombre LIKE %:texto% OR d.descripcion LIKE %:texto%")
    List<Dispositivo> buscarPorTexto(@Param("texto") String texto);

    @Query("SELECT d FROM Dispositivo d WHERE d.precioActual BETWEEN :min AND :max AND d.vendido = false")
    List<Dispositivo> findByRangoPrecio(@Param("min") BigDecimal min, @Param("max") BigDecimal max);

    @Query("SELECT COUNT(d) FROM Dispositivo d WHERE d.vendido = false")
    long contarDispositivosDisponibles();

    @Query("SELECT d.categoria.nombre, COUNT(d) FROM Dispositivo d WHERE d.vendido = false GROUP BY d.categoria.nombre")
    List<Object[]> estadisticasPorCategoria();
}