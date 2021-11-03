package com.mattcollier.weather.domain;

import lombok.Value;

@Value(staticConstructor = "of")
public class Location {
    double longitude;
    double latitude;
}
