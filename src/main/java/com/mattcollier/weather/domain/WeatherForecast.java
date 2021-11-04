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
                             .max(sortByTemperature().thenComparing(sortByHumidityAsc())
                                                     .thenComparing(sortByDateAsc()))
                             .orElseThrow(() -> new RuntimeException("TODO: implement error handling"));
    }


    public Comparator<DailyForecast> sortByTemperature() {
        return Comparator.comparing(DailyForecast::getTemperature);
    }

    public Comparator<DailyForecast> sortByHumidityAsc() {
        return Comparator.comparing(DailyForecast::getHumidity).reversed();
    }

    public Comparator<DailyForecast> sortByDateAsc() {
        return Comparator.comparing(DailyForecast::getDate).reversed();
    }
}
