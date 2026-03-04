package com.bruno.hotel.hotel_api;

import com.bruno.hotel.hotel_api.controller.ReservaController;
import com.bruno.hotel.hotel_api.dto.ReservaRequestDTO;
import com.bruno.hotel.hotel_api.model.Reserva;
import com.bruno.hotel.hotel_api.model.StatusReserva;
import com.bruno.hotel.hotel_api.service.ReservaService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ReservaController.class)
class ReservaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @MockitoBean
    private ReservaService reservaService;

    @Test
    void deveCriarReservaERetornar200() throws Exception {

        Reserva reserva = new Reserva();
        reserva.setId(1L);

        when(reservaService.criar(any(ReservaRequestDTO.class)))
                .thenReturn(reserva);

        ReservaRequestDTO dto = new ReservaRequestDTO();

        mockMvc.perform(post("/api/reservas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L));

        verify(reservaService).criar(any(ReservaRequestDTO.class));
    }

    @Test
    void deveBuscarReservaPorIdERetornar200() throws Exception {

        Reserva reserva = new Reserva();
        reserva.setId(1L);

        when(reservaService.buscarPorId(anyLong()))
                .thenReturn(reserva);

        mockMvc.perform(get("/api/reservas/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L));

        verify(reservaService).buscarPorId(1L);
    }

    @Test
    void deveBuscarReservaPorStatusERetornar200() throws Exception {

        Reserva reserva = new Reserva();
        reserva.setId(1L);

        when(reservaService.findByStatus(StatusReserva.CHECKED_IN))
                .thenReturn(List.of(reserva));

        mockMvc.perform(get("/api/reservas/status/CHECKED_IN"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L));

        verify(reservaService).findByStatus(StatusReserva.CHECKED_IN);
    }

    @Test
    void deveRetornarErro400QuandoStatusInvalido() throws Exception {

        mockMvc.perform(get("/api/reservas/status/INVALIDO"))
                .andExpect(status().isBadRequest());
    }
}