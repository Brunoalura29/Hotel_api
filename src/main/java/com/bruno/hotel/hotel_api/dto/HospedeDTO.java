package com.bruno.hotel.hotel_api.dto;

import lombok.Data;

@Data
public class HospedeDTO {
    private Long id;
    private String nome;
    private String documento;
    private String telefone;
    private Boolean temCarro;
}
