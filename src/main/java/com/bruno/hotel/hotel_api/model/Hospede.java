package com.bruno.hotel.hotel_api.model;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "hospede")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Hospede {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private String documento;
    private String telefone;
    private Boolean temCarro;
}
