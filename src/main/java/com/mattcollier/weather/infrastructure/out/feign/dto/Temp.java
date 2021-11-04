package com.mattcollier.weather.infrastructure.out.feign.dto;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public class Temp {

    @JsonProperty("max")
    private BigDecimal max;


}
