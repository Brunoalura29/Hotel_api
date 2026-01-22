package com.bruno.hotel.hotel_api.service;


import com.bruno.hotel.hotel_api.model.Reserva;
import com.bruno.hotel.hotel_api.model.StatusReserva;
import com.bruno.hotel.hotel_api.repository.ReservaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReservaService {

    private final ReservaRepository reservaRepository;

    public ReservaService(ReservaRepository reservaRepository) {
        this.reservaRepository = reservaRepository;
    }

    public Reserva save(Reserva r) {
        if (r.getStatus() == null) r.setStatus(StatusReserva.RESERVADO);
        return reservaRepository.save(r);
    }

    public List<Reserva> findByStatus(StatusReserva status) {
        return reservaRepository.findByStatus(status);
    }

    public List<Reserva> findByHospedeId(Long hospedeId) {
        return reservaRepository.findByHospedeId(hospedeId);
    }

    public Reserva findById(Long id) {
        return reservaRepository.findById(id).orElseThrow(() -> new RuntimeException("Reserva não encontrada"));
    }
}