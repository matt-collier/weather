package com.mattcollier.weather.infrastructure.out.feign.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public class OpenWeatherResponse {

    @JsonProperty("daily")
    private List<Daily> daily = null;
    
}
