package com.mattcollier.weather.application.service;

import org.springframework.stereotype.Service;

import com.mattcollier.weather.application.port.in.WarmestDayQuery;
import com.mattcollier.weather.application.port.out.WeatherLookup;
import com.mattcollier.weather.domain.DailyForecast;
import com.mattcollier.weather.domain.Location;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class WarmestDayService implements WarmestDayQuery {

    private final WeatherLookup weatherLookup;
    
    @Override
    public DailyForecast warmestDay(final Location location) {
        final var weatherForecast = weatherLookup.weatherForecast(location);
        return weatherForecast.warmestDayOfWeek();
    }
}
