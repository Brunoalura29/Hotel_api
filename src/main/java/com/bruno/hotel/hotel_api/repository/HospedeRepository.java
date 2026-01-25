package com.bruno.hotel.hotel_api.repository;

import com.bruno.hotel.hotel_api.model.Hospede;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface HospedeRepository extends JpaRepository<Hospede, Long> {

    List<Hospede> findByNomeContainingIgnoreCase(String nome);

    Optional<Hospede> findByDocumento(String documento);

    List<Hospede> findByTelefone(String telefone);
}

