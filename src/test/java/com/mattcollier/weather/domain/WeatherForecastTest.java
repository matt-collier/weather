package com.mattcollier.weather.domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

class WeatherForecastTest {

    @Test
    void shouldReturnWarmestDay() {
        final var weatherDay = weatherForecast()
                                              .dailyForecast(dailyForecast()
                                                                          .date(LocalDate.of(2021, 11, 1))
                                                                          .temperature(253.11)
                                                                          .humidity(77)
                                                                          .build())
                                              .dailyForecast(dailyForecast()
                                                                          .date(LocalDate.of(2021, 11, 2))
                                                                          .temperature(255.33)
                                                                          .humidity(56)
                                                                          .build())
                                              .build()
                                              .warmestDayOfWeek();
        assertThat(weatherDay).isEqualTo(DailyForecast.builder()
                                                      .date(LocalDate.of(2021, 11, 2))
                                                      .temperature(255.33)
                                                      .humidity(56)
                                                      .build());
    }

    private DailyForecast.DailyForecastBuilder dailyForecast() {
        return DailyForecast.builder();
    }

    private WeatherForecast.WeatherForecastBuilder weatherForecast() {
        return WeatherForecast.builder();
    }
    
}