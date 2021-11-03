package com.mattcollier.weather.infrastructure.out;

import static java.time.ZoneOffset.UTC;

import java.time.Instant;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.mattcollier.weather.application.port.out.WeatherLookup;
import com.mattcollier.weather.domain.DailyForecast;
import com.mattcollier.weather.domain.Location;
import com.mattcollier.weather.domain.WeatherForecast;
import com.mattcollier.weather.infrastructure.out.feign.OpenWeatherClient;
import com.mattcollier.weather.infrastructure.out.feign.dto.Daily;
import com.mattcollier.weather.infrastructure.out.feign.dto.OpenWeatherResponse;

@Component
public class OpenWeatherAdapter implements WeatherLookup {
    private final OpenWeatherClient openWeatherClient;
    private final String apiKey;

    public OpenWeatherAdapter(final OpenWeatherClient openWeatherClient, @Value("${apiKey}") final String apiKey) {
        this.openWeatherClient = openWeatherClient;
        this.apiKey = apiKey;
    }

    @Override
    public WeatherForecast weatherForecast(final Location location) {
        final var openWeatherResponse = openWeatherClient.weatherForecast(location.getLongitude(),
                                                               location.getLatitude(),
                                                                          apiKey);
        return mapToDomainModel(openWeatherResponse);
    }

    private WeatherForecast mapToDomainModel(final OpenWeatherResponse openWeatherResponse) {
        return WeatherForecast.builder()
                              .dailyForecasts(openWeatherResponse.getDaily()
                                                                 .stream()
                                                                 .map(this::mapToDomainModel)
                                                                 .collect(Collectors.toList()))
                              .build();
    }

    private DailyForecast mapToDomainModel(final Daily daily) {
        return DailyForecast.builder()
                            .date(Instant.ofEpochMilli(daily.getDt() * 1000).atZone(UTC).toLocalDate())
                            .temperature(daily.getTemp().getMax())
                            .humidity(daily.getHumidity())
                            .build();
    }
}
