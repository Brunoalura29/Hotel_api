package com.bruno.hotel.hotel_api.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ReservaRequestDTO {
    private Long hospedeId;
    private LocalDateTime dataEntradaPrevista;
    private LocalDateTime dataSaidaPrevista;
    private Boolean usaVaga;
}
