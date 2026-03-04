package com.bruno.hotel.hotel_api;

import com.bruno.hotel.hotel_api.dto.CheckoutResponseDTO;
import com.bruno.hotel.hotel_api.exception.BusinessRuleException;
import com.bruno.hotel.hotel_api.model.Hospede;
import com.bruno.hotel.hotel_api.model.Reserva;
import com.bruno.hotel.hotel_api.model.StatusReserva;
import com.bruno.hotel.hotel_api.repository.ReservaRepository;
import com.bruno.hotel.hotel_api.service.CheckoutService;
import com.bruno.hotel.hotel_api.service.PricingService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class   CheckoutServiceTest {

    @Mock
    private ReservaRepository reservaRepository;

    @Mock
    private PricingService pricingService;

    @InjectMocks
    private CheckoutService checkoutService;

    private Reserva reserva;
    private Hospede hospede;

    @BeforeEach
    void setup() {
        hospede = new Hospede();
        hospede.setTemCarro(true);

        reserva = new Reserva();
        reserva.setId(1L);
        reserva.setHospede(hospede);
        reserva.setUsaVaga(true);
        reserva.setDataCheckin(LocalDateTime.of(2026, 1, 10, 14, 0));
        reserva.setStatus(StatusReserva.CHECKED_IN);
    }

    // reserva não encontrada
    @Test
    void deveLancarExcecaoQuandoReservaNaoExiste() {
        when(reservaRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(BusinessRuleException.class,
                () -> checkoutService.checkout(1L, LocalDateTime.now()));
    }

    // checkout duplicado
    @Test
    void naoDevePermitirCheckoutDuplicado() {
        reserva.setStatus(StatusReserva.CHECKED_OUT);

        when(reservaRepository.findById(1L)).thenReturn(Optional.of(reserva));

        assertThrows(BusinessRuleException.class,
                () -> checkoutService.checkout(1L, LocalDateTime.now()));
    }

    // checkout sem chekin
    @Test
    void naoDevePermitirSemCheckin() {
        reserva.setDataCheckin(null);
        reserva.setDataEntradaPrevista(null);

        when(reservaRepository.findById(1L)).thenReturn(Optional.of(reserva));

        assertThrows(BusinessRuleException.class,
                () -> checkoutService.checkout(1L, LocalDateTime.now()));
    }

    @Test
    void deveCobrarMultaQuandoCheckoutForAposMeioDia() {

        // Cenário
        Long reservaId = 1L;

        LocalDateTime checkin = LocalDateTime.of(2026, 3, 1, 14, 0);
        LocalDateTime checkoutAtrasado = LocalDateTime.of(2026, 3, 2, 13, 0);

        Hospede hospede = new Hospede();
        hospede.setTemCarro(false);

        Reserva reserva = new Reserva();
        reserva.setId(reservaId);
        reserva.setDataCheckin(checkin);
        reserva.setStatus(StatusReserva.CHECKED_IN);
        reserva.setUsaVaga(false);
        reserva.setHospede(hospede);

        when(reservaRepository.findById(reservaId)).thenReturn(Optional.of(reserva));

        // 1 noite
        when(pricingService.nightsBetween(checkin, checkoutAtrasado)).thenReturn(1L);

        // diária do dia
        when(pricingService.dailyRate(any(LocalDate.class)))
                .thenReturn(BigDecimal.valueOf(120.00));

        CheckoutResponseDTO response = checkoutService.checkout(reservaId, checkoutAtrasado);

        // Verificações
        BigDecimal valorEsperadoMulta = BigDecimal.valueOf(60.00);

        assertEquals(0, valorEsperadoMulta.compareTo(response.getValorAtraso()));
        assertEquals(StatusReserva.CHECKED_OUT, reserva.getStatus());

        verify(reservaRepository).save(reserva);
    }
}