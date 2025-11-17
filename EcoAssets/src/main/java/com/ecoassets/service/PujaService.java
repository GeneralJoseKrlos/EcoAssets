package com.ecoassets.service;

import com.ecoassets.model.*;
import com.ecoassets.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class PujaService {
    @Autowired
    private PujaRepository pujaRepository;

    @Autowired
    private DispositivoRepository dispositivoRepository;

    @Transactional
    public Puja realizarPuja(Dispositivo dispositivo, Usuario usuario, BigDecimal monto) {
        // Verificar que el dispositivo acepta pujas
        if (!dispositivo.getAceptaPujas()) {
            throw new RuntimeException("Este dispositivo no acepta pujas");
        }

        // Verificar que la puja es mayor al precio actual
        if (monto.compareTo(dispositivo.getPrecioActual()) <= 0) {
            throw new RuntimeException("La puja debe ser mayor al precio actual");
        }

        // Crear la puja
        Puja puja = new Puja();
        puja.setDispositivo(dispositivo);
        puja.setUsuario(usuario);
        puja.setMonto(monto);

        return pujaRepository.save(puja);
    }

    @Transactional
    public void aceptarPuja(Integer idPuja) {
        Optional<Puja> pujaOpt = pujaRepository.findById(idPuja);
        if (pujaOpt.isPresent()) {
            Puja puja = pujaOpt.get();
            puja.setEstado(EstadoPuja.ACEPTADA);
            pujaRepository.save(puja);

            // Actualizar precio del dispositivo
            Dispositivo dispositivo = puja.getDispositivo();
            dispositivo.setPrecioActual(puja.getMonto());
            dispositivoRepository.save(dispositivo);

            // Rechazar otras pujas pendientes
            List<Puja> otrasPujas = pujaRepository.findPujasPendientes(dispositivo);
            for (Puja otra : otrasPujas) {
                if (!otra.getIdPuja().equals(idPuja)) {
                    otra.setEstado(EstadoPuja.RECHAZADA);
                    pujaRepository.save(otra);
                }
            }
        }
    }

    public List<Puja> obtenerPujasPorDispositivo(Dispositivo dispositivo) {
        return pujaRepository.findByDispositivoOrderByMontoDesc(dispositivo);
    }
}