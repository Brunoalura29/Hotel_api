package com.bruno.hotel.hotel_api.service;


import com.bruno.hotel.hotel_api.dto.ReservaRequestDTO;
import com.bruno.hotel.hotel_api.model.Hospede;
import com.bruno.hotel.hotel_api.model.Reserva;
import com.bruno.hotel.hotel_api.model.StatusReserva;
import com.bruno.hotel.hotel_api.repository.HospedeRepository;
import com.bruno.hotel.hotel_api.repository.ReservaRepository;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.time.LocalDateTime;

import java.util.List;

@Service
public class ReservaService {

    private final ReservaRepository reservaRepository;
    private final HospedeRepository hospedeRepository;

    public ReservaService(ReservaRepository reservaRepository,
                          HospedeRepository hospedeRepository) {
        this.reservaRepository = reservaRepository;
        this.hospedeRepository = hospedeRepository;
    }

    public Reserva criar(ReservaRequestDTO dto) {

        Hospede hospede = hospedeRepository.findById(dto.getHospedeId())
                .orElseThrow(() -> new RuntimeException("Hóspede não encontrado"));

        Reserva reserva = Reserva.builder()
                .hospede(hospede) // ⭐ ESSENCIAL
                .dataEntradaPrevista(dto.getDataEntradaPrevista())
                .dataSaidaPrevista(dto.getDataSaidaPrevista())
                .usaVaga(dto.getUsaVaga())
                .status(StatusReserva.RESERVADO)
                .build();

        return reservaRepository.save(reserva);
    }
    public List<Reserva> findByStatus(StatusReserva status) {
        return reservaRepository.findByStatus(status);
    }
    public List<Reserva> findByHospedeId(Long hospedeId) {
        return reservaRepository.findByHospedeId(hospedeId);
    }

}

/*
    public Reserva save(Reserva r) {
        if (r.getStatus() == null) r.setStatus(StatusReserva.RESERVADO);
        return reservaRepository.save(r);
    }

} */