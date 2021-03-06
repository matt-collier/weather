package com.mattcollier.weather.infrastructure.in;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mattcollier.weather.application.port.in.WarmestDayQuery;
import com.mattcollier.weather.domain.DailyForecast;
import com.mattcollier.weather.domain.Location;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequiredArgsConstructor
@Slf4j
public class WeatherController {

    private final WarmestDayQuery warmestDayQuery;
    
    @GetMapping(value = {"/warmest-day"}, produces = {"application/json"})
    public ResponseEntity<DailyForecast> warmestDay(@RequestParam double longitude, @RequestParam double latitude) {
        return ResponseEntity.ok(warmestDayQuery.warmestDay(Location.of(longitude, latitude)));
    }
}
