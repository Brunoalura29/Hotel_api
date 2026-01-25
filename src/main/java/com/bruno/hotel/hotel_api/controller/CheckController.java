package com.bruno.hotel.hotel_api.controller;

import com.bruno.hotel.hotel_api.dto.CheckoutResponseDTO;
import com.bruno.hotel.hotel_api.model.Reserva;
import com.bruno.hotel.hotel_api.service.CheckinService;
import com.bruno.hotel.hotel_api.service.CheckoutService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/ops")
public class CheckController {

    private final CheckinService checkinService;
    private final CheckoutService checkoutService;

    public CheckController(CheckinService checkinService, CheckoutService checkoutService) {
        this.checkinService = checkinService;
        this.checkoutService = checkoutService;
    }

    @PostMapping("/checkin/{reservaId}")
    public ResponseEntity<Reserva> checkin(@PathVariable Long reservaId, @RequestBody(required = false) LocalDateTime when) {
        LocalDateTime now = when != null ? when : LocalDateTime.now();
        return ResponseEntity.ok(checkinService.checkin(reservaId, now));
    }

    @PostMapping("/checkout/{reservaId}")
    public ResponseEntity<CheckoutResponseDTO> checkout(@PathVariable Long reservaId, @RequestBody(required = false) LocalDateTime when) {
        LocalDateTime now = when != null ? when : LocalDateTime.now();
        return ResponseEntity.ok(checkoutService.checkout(reservaId, now));
    }
}