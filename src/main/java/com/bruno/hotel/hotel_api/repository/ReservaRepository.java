package com.bruno.hotel.hotel_api.repository;

import com.bruno.hotel.hotel_api.model.Reserva;
import com.bruno.hotel.hotel_api.model.StatusReseva;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReservaRepository extends JpaRepository<Reserva, Long> {
    List<Reserva> findByStatus(StatusReseva status);
    List<Reserva> findByHospedeId(Long hospedeId);
}