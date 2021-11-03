package com.mattcollier.weather.infrastructure.out.feign.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public class Daily {

    @JsonProperty("dt")
    private long dt;
    @JsonProperty("temp")
    private Temp temp;
    @JsonProperty("humidity")
    private int humidity;
}
