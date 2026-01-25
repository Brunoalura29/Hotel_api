package com.bruno.hotel.hotel_api.service;


import com.bruno.hotel.hotel_api.model.Hospede;
import com.bruno.hotel.hotel_api.repository.HospedeRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class HospedeService {

    private final HospedeRepository hospedeRepository;

    public HospedeService(HospedeRepository hospedeRepository) {
        this.hospedeRepository = hospedeRepository;
    }

    public Hospede save(Hospede hospede) {
        return hospedeRepository.save(hospede);
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
}

