package com.bruno.hotel.hotel_api.service;

import com.bruno.hotel.hotel_api.exception.BusinessRuleException;
import com.bruno.hotel.hotel_api.model.Reserva;
import com.bruno.hotel.hotel_api.model.StatusReserva;
import com.bruno.hotel.hotel_api.repository.ReservaRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class CheckinService {

    private final ReservaRepository reservaRepository;

    public CheckinService(ReservaRepository reservaRepository) {
        this.reservaRepository = reservaRepository;
    }

    public Reserva checkin(Long reservaId, LocalDateTime quando) {
        Reserva r = reservaRepository.findById(reservaId).orElseThrow(() -> new BusinessRuleException("Reserva não encontrada"));

        // regra: check-in a partir das 14:00
        LocalDateTime inicioCheckin = quando.withHour(14).withMinute(0).withSecond(0).withNano(0);
        if (quando.isBefore(inicioCheckin)) {
            throw new BusinessRuleException("Check-in só permitido a partir das 14:00. (Tentativa em: " + quando.toLocalTime() + ")");
        }

        // Atualiza a data/hora real do check-in
        r.setDataCheckin(quando);

        // Atualiza o status da reserva para CHECKED_IN
        r.setStatus(StatusReserva.CHECKED_IN);

        // Persiste as alterações no banco e retorna a reserva atualizada
        return reservaRepository.save(r);
}