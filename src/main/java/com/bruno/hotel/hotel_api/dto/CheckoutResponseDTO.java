package com.bruno.hotel.hotel_api.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class CheckoutResponseDTO {
    private BigDecimal valorDiarias;
    private BigDecimal valorEstacionamento;
    private BigDecimal valorAtraso;
    private BigDecimal valorTotal;
}