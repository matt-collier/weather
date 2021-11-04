package com.mattcollier.weather.infrastructure.in;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.mattcollier.weather.application.port.in.WarmestDayQuery;
import com.mattcollier.weather.domain.DailyForecast;
import com.mattcollier.weather.domain.Location;

@ExtendWith(MockitoExtension.class)
class WeatherControllerTest {

    @Mock
    private WarmestDayQuery warmestDayQuery;
    
    private WeatherController controller;

    @BeforeEach
    void setUp() {
        controller = new WeatherController(warmestDayQuery);
    }

    @Test
    void shouldCallService() {
        final var dailyForecast = DailyForecast.builder()
                                            .date(LocalDate.of(2021, 11, 2))
                                            .temperature(new BigDecimal("288.26"))
                                            .humidity(52)
                                            .build();
        given(warmestDayQuery.warmestDay(any(Location.class))).willReturn(dailyForecast);

        final double longitude = 1.4;
        final double latitude = 2.1;
        final var response = controller.warmestDay(longitude, latitude);

        verify(warmestDayQuery).warmestDay(Location.of(longitude, latitude));
        assertThat(response).extracting(ResponseEntity::getStatusCode,
                                        ResponseEntity::getBody).containsExactly(HttpStatus.OK, dailyForecast);
    }
}