package com.bruno.hotel.hotel_api.service;


import com.bruno.hotel.hotel_api.model.Hospede;
import com.bruno.hotel.hotel_api.repository.HospedeRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class HospedeService {

    private final HospedeRepository hospedeRepository;

    public HospedeService(HospedeRepository hospedeRepository) {
        this.hospedeRepository = hospedeRepository;
    }

    public List<Hospede> findByNome(String nome) {
        return hospedeRepository.findByNomeContainingIgnoreCase(nome);
    }

    public Optional<Hospede> findByDocumento(String documento) {
        return hospedeRepository.findByDocumento(documento);

    }

    public List<Hospede> findByTelefone(String telefone) {
        return hospedeRepository.findByTelefone(telefone);
    }

    public List<Hospede> findAll() {
        return hospedeRepository.findAll();
    }

    public Hospede save(Hospede hospede) {
        return hospedeRepository.save(hospede);
    }

    public ResponseEntity<?> buscar(String nome, String documento, String telefone) {
        if (nome != null)
            return ResponseEntity.ok(hospedeRepository.findByNomeContainingIgnoreCase(nome));
        if (documento != null)
            return ResponseEntity.of(hospedeRepository.findByDocumento(documento));
        if (telefone != null)
            return ResponseEntity.ok(hospedeRepository.findByTelefone(telefone));
        return ResponseEntity.badRequest().body("Informe nome, documento ou telefone");
    }
}

