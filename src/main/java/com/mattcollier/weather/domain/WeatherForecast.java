package com.mattcollier.weather.domain;

import java.util.Comparator;
import java.util.List;

import lombok.Builder;
import lombok.Singular;
import lombok.Value;

@Value
@Builder
public class WeatherForecast {

    @Singular
    List<DailyForecast> dailyForecasts;
    
    public DailyForecast warmestDayOfWeek() {
        return dailyForecasts.stream()
                             .max(Comparator.comparing(DailyForecast::getTemperature))
                             .orElseThrow(() -> new RuntimeException("TODO: implement error handling"));
    }


}
