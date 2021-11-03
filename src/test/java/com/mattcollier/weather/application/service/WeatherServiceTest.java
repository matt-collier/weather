package com.mattcollier.weather.application.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.mattcollier.weather.application.port.in.WarmestDayQuery;
import com.mattcollier.weather.domain.DailyForecast;
import com.mattcollier.weather.domain.Location;
import com.mattcollier.weather.infrastructure.out.OpenWeatherAdapter;
import com.mattcollier.weather.infrastructure.out.feign.OpenWeatherClient;
import com.mattcollier.weather.infrastructure.out.feign.dto.Daily;
import com.mattcollier.weather.infrastructure.out.feign.dto.OpenWeatherResponse;
import com.mattcollier.weather.infrastructure.out.feign.dto.Temp;

@ExtendWith(MockitoExtension.class)
class WeatherServiceTest {

    private WarmestDayQuery weatherService;

    @Mock
    private OpenWeatherClient openWeatherClient;

    @BeforeEach
    void setUp() {
        weatherService = new WarmestDayService(new OpenWeatherAdapter(openWeatherClient, "dummyApiKey"));
    }

    @Test
    void shouldCallOpenWeatherAndReturnWarmestDay() {
        givenOpenWeatherReturns(new OpenWeatherResponse().setDaily(List.of(new Daily().setDt(1635850800L)
                                                                                      .setTemp(new Temp().setMax(283.85))
                                                                                      .setHumidity(25),
                                                                           new Daily().setDt(1635937200L)
                                                                           .setTemp(new Temp().setMax(278.44))
                                                                           .setHumidity(22))));
        final var warmestDay = weatherService.warmestDay(Location.of(22.3, 48.4));
        assertThat(warmestDay).isEqualTo(DailyForecast.builder()
                                                      .date(LocalDate.of(2021, 11, 2))
                                                      .temperature(283.85)
                                                      .humidity(25)
                                                      .build());
        verify(openWeatherClient).weatherForecast(22.3, 48.4, "dummyApiKey");
    }

    private void givenOpenWeatherReturns(final OpenWeatherResponse openWeatherResponse) {
        given(openWeatherClient.weatherForecast(any(Double.class), any(Double.class), any(String.class))).willReturn(
                openWeatherResponse);
    }
}