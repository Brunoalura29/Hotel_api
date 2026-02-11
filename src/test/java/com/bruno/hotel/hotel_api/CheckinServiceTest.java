package com.bruno.hotel.hotel_api;

import com.bruno.hotel.hotel_api.exception.BusinessRuleException;
import com.bruno.hotel.hotel_api.model.Reserva;
import com.bruno.hotel.hotel_api.model.StatusReserva;
import com.bruno.hotel.hotel_api.repository.ReservaRepository;
import com.bruno.hotel.hotel_api.service.CheckinService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CheckinServiceTest {

    @Mock
    private ReservaRepository reservaRepository;

    @InjectMocks
    private CheckinService checkinService;

    private Reserva reserva;

    @BeforeEach
    void setup() {
        reserva = Reserva.builder()
                .id(1L)
                .dataEntradaPrevista(LocalDateTime.of(2026, 1, 26, 14, 0))
                .status(StatusReserva.RESERVADO)
                .build();
    }

    //  não permitir antes da data de entrada
    @Test
    void naoDevePermitirCheckinAntesDaDataPrevista() {
        LocalDateTime tentativa = LocalDateTime.of(2026, 1, 25, 15, 0);

        when(reservaRepository.findById(1L)).thenReturn(Optional.of(reserva));

        BusinessRuleException exception = assertThrows(
                BusinessRuleException.class,
                () -> checkinService.checkin(1L, tentativa)
        );

        assertEquals("Check-in não permitido antes da data de entrada", exception.getMessage());
        verify(reservaRepository, never()).save(any());
    }

    //  não permitir ante das 14h
    @Test
    void naoDevePermitirCheckinAntesDas14h() {
        LocalDateTime tentativa = LocalDateTime.of(2026, 1, 26, 13, 0);

        when(reservaRepository.findById(1L)).thenReturn(Optional.of(reserva));

        assertThrows(
                BusinessRuleException.class,
                () -> checkinService.checkin(1L, tentativa)
        );

        verify(reservaRepository, never()).save(any());
    }

    //  não permitir se já estiver CHECKED_IN
    @Test
    void naoDevePermitirCheckinSeJaEstiverCheckedIn() {
        reserva.setStatus(StatusReserva.CHECKED_IN);

        LocalDateTime tentativa = LocalDateTime.of(2026, 1, 26, 15, 0);

        when(reservaRepository.findById(1L)).thenReturn(Optional.of(reserva));

        assertThrows(
                BusinessRuleException.class,
                () -> checkinService.checkin(1L, tentativa)
        );
    }

    //  permitir check-in válido
    @Test
    void devePermitirCheckinValido() {
        LocalDateTime tentativa = LocalDateTime.of(2026, 1, 26, 15, 0);

        when(reservaRepository.findById(1L)).thenReturn(Optional.of(reserva));
        when(reservaRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

        Reserva resultado = checkinService.checkin(1L, tentativa);

        assertNotNull(resultado);
        assertEquals(StatusReserva.CHECKED_IN, resultado.getStatus());
        assertEquals(tentativa, resultado.getDataCheckin());

        verify(reservaRepository, times(1)).save(reserva);
    }

    //  alterar staus corretamente
    @Test
    void deveAlterarStatusParaCheckedIn() {
        LocalDateTime tentativa = LocalDateTime.of(2026, 1, 26, 16, 0);

        when(reservaRepository.findById(1L)).thenReturn(Optional.of(reserva));
        when(reservaRepository.save(any())).thenReturn(reserva);

        Reserva resultado = checkinService.checkin(1L, tentativa);

        assertEquals(StatusReserva.CHECKED_IN, resultado.getStatus());
    }
}