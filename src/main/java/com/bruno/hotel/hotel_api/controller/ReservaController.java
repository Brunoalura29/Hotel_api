package com.bruno.hotel.hotel_api.controller;

import com.bruno.hotel.hotel_api.dto.ReservaRequestDTO;
import com.bruno.hotel.hotel_api.model.Reserva;
import com.bruno.hotel.hotel_api.model.StatusReserva;
import com.bruno.hotel.hotel_api.service.ReservaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reservas")
public class ReservaController {

    private final ReservaService reservaService;

    public ReservaController(ReservaService reservaService) {
        this.reservaService = reservaService;
    }

    @PostMapping
    public ResponseEntity<Reserva> criar(@RequestBody ReservaRequestDTO dto) {
        return ResponseEntity.ok(reservaService.criar(dto));
    }
    @GetMapping("/status/{status}")
    public ResponseEntity<List<Reserva>> porStatus(@PathVariable String status) {
        return ResponseEntity.ok(
                reservaService.findByStatus(
                        Enum.valueOf(com.bruno.hotel.hotel_api.model.StatusReserva.class, status))); }

}