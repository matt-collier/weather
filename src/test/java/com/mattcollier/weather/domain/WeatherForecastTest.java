package com.mattcollier.weather.domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;

class WeatherForecastTest {

    private WeatherForecast weatherForecast;

    @Test
    void shouldReturnDayWithHighestMaxTemperatureAsWarmestDay() {
        givenForecast(dailyForecastFor(2021, 11, 1)
                              .temperature(new BigDecimal("253.11"))
                              .humidity(77),
                      dailyForecastFor(2021, 11, 2)
                              .temperature(new BigDecimal("255.33"))
                              .humidity(56));

        assertWarmestDayIs(new DailyForecast(LocalDate.of(2021, 11, 2),
                                             new BigDecimal("255.33"),
                                             56));
    }

    @Test
    void shouldReturnDayWithLowestHumidityWhenHighestMaxTemperaturesAreEqual() {
        givenForecast(dailyForecastFor(2021, 11, 1)
                              .temperature(new BigDecimal("253.11"))
                              .humidity(77),
                      dailyForecastFor(2021, 11, 2)
                              .temperature(new BigDecimal("244.22"))
                              .humidity(56),
                      dailyForecastFor(2021, 11, 3)
                              .temperature(new BigDecimal("231.77"))
                              .humidity(77),
                      dailyForecastFor(2021, 11, 4)
                              .temperature(new BigDecimal("255.33"))
                              .humidity(56),
                      dailyForecastFor(2021, 11, 5)
                              .temperature(new BigDecimal("238.22"))
                              .humidity(77),
                      dailyForecastFor(2021, 11, 6)
                              .temperature(new BigDecimal("255.33"))
                              .humidity(37),
                      dailyForecastFor(2021, 11, 7)
                              .temperature(new BigDecimal("248.24"))
                              .humidity(77));

        assertWarmestDayIs(new DailyForecast(LocalDate.of(2021, 11, 6),
                                             new BigDecimal("255.33"),
                                             37));
    }

    @Test
    void shouldReturnFirstDayWhenMultipleDaysHaveSameMaxTemperatureAndHumidity() {
        givenForecast(dailyForecastFor(2021, 11, 1)
                              .temperature(new BigDecimal("253.11"))
                              .humidity(77),
                      dailyForecastFor(2021, 11, 2)
                              .temperature(new BigDecimal("244.22"))
                              .humidity(56),
                      dailyForecastFor(2021, 11, 3)
                              .temperature(new BigDecimal("255.33"))
                              .humidity(56),
                      dailyForecastFor(2021, 11, 4)
                              .temperature(new BigDecimal("255.33"))
                              .humidity(56),
                      dailyForecastFor(2021, 11, 5)
                              .temperature(new BigDecimal("238.22"))
                              .humidity(77),
                      dailyForecastFor(2021, 11, 6)
                              .temperature(new BigDecimal("255.33"))
                              .humidity(56),
                      dailyForecastFor(2021, 11, 7)
                              .temperature(new BigDecimal("248.24"))
                              .humidity(77));

        assertWarmestDayIs(new DailyForecast(LocalDate.of(2021, 11, 3),
                                             new BigDecimal("255.33"),
                                             56));
    }

    private void assertWarmestDayIs(final DailyForecast expected) {
        assertThat(weatherForecast.warmestDayOfWeek()).isEqualTo(expected);
    }

    private void givenForecast(final DailyForecast.DailyForecastBuilder... dailyForecasts) {
        weatherForecast = WeatherForecast.builder()
                                         .dailyForecasts(Stream.of(dailyForecasts)
                                                               .map(DailyForecast.DailyForecastBuilder::build)
                                                               .collect(Collectors.toList())).build();
    }

    private DailyForecast.DailyForecastBuilder dailyForecastFor(final int year, final int month, final int day) {
        return DailyForecast.builder().date(LocalDate.of(year, month, day));
    }

}