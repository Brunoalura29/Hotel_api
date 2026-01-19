package com.bruno.hotel.hotel_api.service;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Responsável por calcular diárias e taxas.
 */
@Service
public class PricingService {

    private static final BigDecimal WEEKDAY_RATE = BigDecimal.valueOf(120.00);
    private static final BigDecimal WEEKEND_RATE = BigDecimal.valueOf(180.00);

    private static final BigDecimal WEEKDAY_PARK = BigDecimal.valueOf(15.00);
    private static final BigDecimal WEEKEND_PARK = BigDecimal.valueOf(20.00);

    /**
     * Retorna o valor da diária de acordo com a data informada.
     * Se for sábado ou domingo, aplica tarifa de final de semana.
     */
    public BigDecimal dailyRate(LocalDate date) {
        DayOfWeek d = date.getDayOfWeek();
        if (d == DayOfWeek.SATURDAY || d == DayOfWeek.SUNDAY) {
            return WEEKEND_RATE;
        } else {
            return WEEKDAY_RATE;
        }
    }

    /**
     * Retorna o valor da taxa de estacionamento de acordo com a data.
     * Final de semana possui valor diferenciado.
     */
    public BigDecimal parkingFee(LocalDate date) {
        DayOfWeek d = date.getDayOfWeek();
        if (d == DayOfWeek.SATURDAY || d == DayOfWeek.SUNDAY) {
            return WEEKEND_PARK;
        } else {
            return WEEKDAY_PARK;
        }
    }

    /**
     * calcula número de noites entre entrada e saida previstas.
     */
    public long nightsBetween(LocalDateTime checkin, LocalDateTime checkout) {
        if (Objects.isNull(checkin) || Objects.isNull(checkout)) return 0;
        return java.time.Duration.between(checkin.toLocalDate().atStartOfDay(), checkout.toLocalDate().atStartOfDay())
                .toDays();
    }
}