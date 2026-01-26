package com.bruno.hotel.hotel_api.service;


import com.bruno.hotel.hotel_api.dto.CheckoutResponseDTO;
import com.bruno.hotel.hotel_api.exception.BusinessRuleException;
import com.bruno.hotel.hotel_api.model.Reserva;
import com.bruno.hotel.hotel_api.model.StatusReserva;
import com.bruno.hotel.hotel_api.repository.ReservaRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
public class CheckoutService {

    private final ReservaRepository reservaRepository;
    private final PricingService pricingService;

    public CheckoutService(ReservaRepository reservaRepository, PricingService pricingService) {
        this.reservaRepository = reservaRepository;
        this.pricingService = pricingService;
    }

    public CheckoutResponseDTO checkout(Long reservaId, LocalDateTime quando) {
        Reserva r = reservaRepository.findById(reservaId).orElseThrow(() -> new BusinessRuleException("Reserva não encontrada"));
        if (r.getStatus() != null && r.getStatus().equals(StatusReserva.CHECKED_OUT)) {
            throw new BusinessRuleException("Reserva já finalizada.");
        }

        // calcular diárias entre dataCheckin (ou dataEntradaPrevista) e dataCheckout (quando)
        LocalDateTime inicio = r.getDataCheckin() != null ? r.getDataCheckin() : r.getDataEntradaPrevista();
        if (inicio == null) throw new BusinessRuleException("Check-in ainda não realizado.");
        long nights = pricingService.nightsBetween(inicio, quando);
        if (nights <= 0) nights = 1; // garante pelo menos uma diária

        BigDecimal valorDiarias = BigDecimal.ZERO;
        BigDecimal valorEstacionamento = BigDecimal.ZERO;

        LocalDate d = inicio.toLocalDate();
        for (int i = 0; i < nights; i++) {
            LocalDate dia = d.plusDays(i);
            valorDiarias = valorDiarias.add(pricingService.dailyRate(dia));
            if (Boolean.TRUE.equals(r.getUsaVaga()) && Boolean.TRUE.equals(r.getHospede().getTemCarro())) {
                valorEstacionamento = valorEstacionamento.add(pricingService.parkingFee(dia));
            }
        }

        // verifica atraso de checkout (após 12:00) — cobrar 50% do valor da diária referente ao dia do checkout
        BigDecimal valorAtraso = BigDecimal.ZERO;
        LocalDateTime limiteCheckout = quando.withHour(12).withMinute(0).withSecond(0).withNano(0);
        if (quando.isAfter(limiteCheckout)) {
            // considerar taxa de 50% da diária do dia da saída
            BigDecimal diariaDoDia = pricingService.dailyRate(quando.toLocalDate());
            valorAtraso = diariaDoDia.multiply(BigDecimal.valueOf(0.5));
        }

        BigDecimal total = valorDiarias.add(valorEstacionamento).add(valorAtraso);

        r.setDataCheckout(quando);
        r.setValorTotal(total);
        r.setStatus(StatusReserva.CHECKED_OUT);

        // Persiste alterações
        reservaRepository.save(r);

        // Retorna resposta com detalhamento dos valores
        return CheckoutResponseDTO.builder()
                .mensagem("Checkout realizado com sucesso")
                .valorDiarias(valorDiarias)
                .valorEstacionamento(valorEstacionamento)
                .valorAtraso(valorAtraso)
                .valorTotal(total)
                .build();
    }
}