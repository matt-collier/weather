package com.mattcollier.weather.domain;

import java.math.BigDecimal;
import java.time.LocalDate;

import lombok.Builder;
import lombok.Value;

@Value
public class DailyForecast {

    private static final BigDecimal CELCIUS_CONVERSION = new BigDecimal("-273.15");
    
    LocalDate date;
    BigDecimal temperature;
    int humidity;

    @Builder
    public DailyForecast(final LocalDate date, final BigDecimal temperature, final int humidity) {
        this.date = date;
        this.temperature = temperature.add(CELCIUS_CONVERSION);
        this.humidity = humidity;
    }
}
