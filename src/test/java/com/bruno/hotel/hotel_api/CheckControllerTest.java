package com.bruno.hotel.hotel_api;

import com.bruno.hotel.hotel_api.controller.CheckController;
import com.bruno.hotel.hotel_api.dto.CheckoutResponseDTO;
import com.bruno.hotel.hotel_api.model.Reserva;
import com.bruno.hotel.hotel_api.service.CheckinService;
import com.bruno.hotel.hotel_api.service.CheckoutService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;


import java.math.BigDecimal;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CheckController.class)
class CheckControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private CheckinService checkinService;

    @MockitoBean
    private CheckoutService checkoutService;

    @Test
    void deveRealizarCheckoutERetornar200() throws Exception {

        CheckoutResponseDTO response = CheckoutResponseDTO.builder()
                .mensagem("Checkout realizado com sucesso")
                .valorDiarias(BigDecimal.valueOf(120.00))
                .valorEstacionamento(BigDecimal.ZERO)
                .valorAtraso(BigDecimal.ZERO)
                .valorTotal(BigDecimal.valueOf(120.00))
                .build();

        when(checkoutService.checkout(anyLong(), any()))
                .thenReturn(response);

        mockMvc.perform(put("/api/ops/checkout/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.mensagem").value("Checkout realizado com sucesso"));

        verify(checkoutService).checkout(anyLong(), any());
    }

    @Test
    void deveRealizarCheckinERetornar200() throws Exception {

        Reserva reserva = new Reserva();
        reserva.setId(1L);

        when(checkinService.checkin(anyLong(), any()))
                .thenReturn(reserva);

        mockMvc.perform(put("/api/ops/checkin/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(checkinService).checkin(anyLong(), any());
    }
}