package com.bruno.hotel.hotel_api;

import com.bruno.hotel.hotel_api.service.PricingService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class PricingServiceTest {

    private PricingService pricingService;

    @BeforeEach
    void configurar() {
        pricingService = new PricingService();
    }

    @Test
    void deveRetornarValorDeDiariaEmDiaUtil() {
        LocalDate segunda = LocalDate.of(2026, 3, 2);

        BigDecimal resultado = pricingService.dailyRate(segunda);

        assertEquals(BigDecimal.valueOf(120.00), resultado);
    }

    @Test
    void deveRetornarValorDeDiariaEmFinalDeSemana() {
        LocalDate sabado = LocalDate.of(2026, 3, 7);

        BigDecimal resultado = pricingService.dailyRate(sabado);

        assertEquals(BigDecimal.valueOf(180.00), resultado);
    }

    @Test
    void deveRetornarTaxaEstacionamentoEmDiaUtil() {
        LocalDate terca = LocalDate.of(2026, 3, 3);

        BigDecimal resultado = pricingService.parkingFee(terca);

        assertEquals(BigDecimal.valueOf(15.00), resultado);
    }

    @Test
    void deveRetornarTaxaEstacionamentoEmFinalDeSemana() {
        LocalDate domingo = LocalDate.of(2026, 3, 8);

        BigDecimal resultado = pricingService.parkingFee(domingo);

        assertEquals(BigDecimal.valueOf(20.00), resultado);
    }

    @Test
    void deveCalcularCorretamenteQuantidadeDeNoites() {
        LocalDateTime checkin = LocalDateTime.of(2026, 3, 1, 14, 0);
        LocalDateTime checkout = LocalDateTime.of(2026, 3, 4, 10, 0);

        long resultado = pricingService.nightsBetween(checkin, checkout);

        assertEquals(3, resultado);
    }

    @Test
    void deveRetornarZeroSeCheckinForNulo() {
        LocalDateTime checkout = LocalDateTime.of(2026, 3, 4, 10, 0);

        long resultado = pricingService.nightsBetween(null, checkout);

        assertEquals(0, resultado);
    }

    @Test
    void deveRetornarZeroSeCheckoutForNulo() {
        LocalDateTime checkin = LocalDateTime.of(2026, 3, 1, 14, 0);

        long resultado = pricingService.nightsBetween(checkin, null);

        assertEquals(0, resultado);
    }
}