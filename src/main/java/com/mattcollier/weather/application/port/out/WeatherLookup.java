package com.mattcollier.weather.application.port.out;

import com.mattcollier.weather.domain.Location;
import com.mattcollier.weather.domain.WeatherForecast;

public interface WeatherLookup {
    WeatherForecast weatherForecast(Location location);
}
