package com.mattcollier.weather.application.port.in;

import com.mattcollier.weather.domain.DailyForecast;
import com.mattcollier.weather.domain.Location;

public interface WarmestDayQuery {

    DailyForecast warmestDay(Location location);
}
