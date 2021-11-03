package com.mattcollier.weather.domain;

import java.time.LocalDate;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class DailyForecast {
    LocalDate date;
    double temperature;
    int humidity;
}
