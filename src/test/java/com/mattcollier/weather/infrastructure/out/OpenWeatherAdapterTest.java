package com.mattcollier.weather.infrastructure.out;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.mattcollier.weather.domain.DailyForecast;
import com.mattcollier.weather.domain.Location;
import com.mattcollier.weather.domain.WeatherForecast;
import com.mattcollier.weather.infrastructure.out.feign.OpenWeatherClient;
import com.mattcollier.weather.infrastructure.out.feign.dto.Daily;
import com.mattcollier.weather.infrastructure.out.feign.dto.OpenWeatherResponse;
import com.mattcollier.weather.infrastructure.out.feign.dto.Temp;

@ExtendWith(MockitoExtension.class)
class OpenWeatherAdapterTest {

    @Mock
    private OpenWeatherClient openWeatherClient;
    private OpenWeatherAdapter openWeatherAdapter;

    @BeforeEach
    void setUp() {
        openWeatherAdapter = new OpenWeatherAdapter(openWeatherClient, "dummyApiKey");
    }

    @Test
    void shouldMapDtosIntoDomainModel() {
        given(openWeatherClient.weatherForecast(any(Double.class), any(Double.class), any(String.class)))
                .willReturn(new OpenWeatherResponse().setDaily(List.of(new Daily().setDt(1635850800L)
                                                                                  .setTemp(new Temp().setMax(new BigDecimal("283.85")))
                                                                                  .setHumidity(25),
                                                                       new Daily().setDt(1635937200L)
                                                                                  .setTemp(new Temp().setMax(new BigDecimal("278.44")))
                                                                                  .setHumidity(22))));

        final var weatherForecast = openWeatherAdapter.weatherForecast(Location.of(55.8, 78.3));
        assertThat(weatherForecast).isEqualTo(WeatherForecast.builder()
                                                             .dailyForecast(new DailyForecast(LocalDate.of(2021, 11, 2),
                                                                                              new BigDecimal("283.85"),
                                                                                              25))
                                                             .dailyForecast(new DailyForecast(LocalDate.of(2021, 11, 3),
                                                                                              new BigDecimal("278.44"),
                                                                                              22))
                                                      .build());
    }
}