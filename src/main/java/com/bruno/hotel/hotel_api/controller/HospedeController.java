package com.bruno.hotel.hotel_api.controller;

import com.bruno.hotel.hotel_api.model.Hospede;
import com.bruno.hotel.hotel_api.repository.HospedeRepository;
import com.bruno.hotel.hotel_api.service.HospedeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/hospedes")
@CrossOrigin(origins = "http://localhost:4200")
public class HospedeController {

    private final HospedeService hospedeService;

    public HospedeController(HospedeService hospedeService) {
        this.hospedeService = hospedeService;
    }

    @GetMapping
    public ResponseEntity<List<Hospede>> listar() {
        return ResponseEntity.ok(hospedeService.findAll());
    }

    @PostMapping
    public ResponseEntity<Hospede> criar(@RequestBody Hospede hospede) {
        return ResponseEntity.ok(hospedeService.save(hospede));
    }

    @GetMapping("/search")
    public ResponseEntity<?> buscar(
            @RequestParam(required = false) String nome,
            @RequestParam(required = false) String documento,
            @RequestParam(required = false) String telefone) {

        return hospedeService.buscar(nome, documento, telefone);
    }
}


