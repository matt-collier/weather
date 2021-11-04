package com.mattcollier.weather.domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.junit.jupiter.api.Test;

class DailyForecastTest {


    private DailyForecast dailyForecast;

    @Test
    void shouldConvertTemperatureToCelcius() {
        givenDailyForecast(dailyForecast().temperature(new BigDecimal("283.85")));
        assertThat(dailyForecast.getTemperature())
                .isEqualByComparingTo(new BigDecimal("10.7"));
    }

    private void givenDailyForecast(final DailyForecast.DailyForecastBuilder dailyForecastBuilder) {
        dailyForecast = dailyForecastBuilder.build();
    }

    private DailyForecast.DailyForecastBuilder dailyForecast() {
        return DailyForecast.builder()
                            .date(LocalDate.of(2021, 11, 5))
                            .humidity(66);
    }
}