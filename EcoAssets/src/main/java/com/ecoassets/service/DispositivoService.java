package com.ecoassets.service;

import com.ecoassets.model.*;
import com.ecoassets.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class DispositivoService {
    @Autowired
    private DispositivoRepository dispositivoRepository;

    @Autowired
    private ImagenDispositivoRepository imagenRepository;

    @Transactional
    public Dispositivo publicarDispositivo(Dispositivo dispositivo, List<String> urlsImagenes) {
        // Guardar el dispositivo
        Dispositivo dispositivoGuardado = dispositivoRepository.save(dispositivo);

        // Guardar imágenes
        for (int i = 0; i < urlsImagenes.size(); i++) {
            ImagenDispositivo imagen = new ImagenDispositivo();
            imagen.setDispositivo(dispositivoGuardado);
            imagen.setUrlImagen(urlsImagenes.get(i));
            imagen.setEsPrincipal(i == 0); // Primera imagen es principal
            imagen.setOrden(i);
            imagenRepository.save(imagen);
        }

        return dispositivoGuardado;
    }

    public List<Dispositivo> obtenerDisponibles() {
        return dispositivoRepository.findDispositivosDisponibles();
    }

    public List<Dispositivo> obtenerEspeciales() {
        return dispositivoRepository.findDispositivosEspeciales();
    }

    public List<Dispositivo> obtenerSubastas() {
        return dispositivoRepository.findDispositivosEnSubasta();
    }

    public List<Dispositivo> buscar(String texto) {
        return dispositivoRepository.buscarPorTexto(texto);
    }

    public Optional<Dispositivo> obtenerPorId(Integer id) {
        return dispositivoRepository.findById(id);
    }

    public long contarDisponibles() {
        return dispositivoRepository.contarDispositivosDisponibles();
    }

    public List<Object[]> obtenerEstadisticas() {
        return dispositivoRepository.estadisticasPorCategoria();
    }
}
