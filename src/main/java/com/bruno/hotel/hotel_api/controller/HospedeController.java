package com.bruno.hotel.hotel_api.controller;

import com.bruno.hotel.hotel_api.model.Hospede;
import com.bruno.hotel.hotel_api.service.HospedeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/hospedes")
public class HospedeController {

    private final HospedeService hospedeService;

    public HospedeController(HospedeService hospedeService) {
        this.hospedeService = hospedeService;
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

        if (nome != null)
            return ResponseEntity.ok(hospedeService.findByNome(nome));
        if (documento != null)
            return ResponseEntity.of(hospedeService.findByDocumento(documento));
        if (telefone != null)
            return ResponseEntity.ok(hospedeService.findByTelefone(telefone));
        return ResponseEntity.badRequest().body("Informe nome, documento ou telefone");
    }

    /*@GetMapping("/search")
    public ResponseEntity<List<Hospede>> buscar(@RequestParam(required = false) String nome,
                                                @RequestParam(required = false) String documento,
                                                @RequestParam(required = false) String telefone) {

        return ResponseEntity.ok().build();
    }*/
}