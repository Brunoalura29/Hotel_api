package com.bruno.hotel.hotel_api.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "reserva")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Reserva {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    private Hospede hospede;

    private LocalDateTime dataEntradaPrevista;
    private LocalDateTime dataSaidaPrevista;

    private LocalDateTime dataCheckin;
    private LocalDateTime dataCheckout;

    @Enumerated(EnumType.STRING)
    private StatusReseva status;

    private BigDecimal valorTotal;
    private Boolean usaVaga;
}