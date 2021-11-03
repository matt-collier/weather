package com.mattcollier.weather.infrastructure.out.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.mattcollier.weather.infrastructure.out.feign.dto.OpenWeatherResponse;

import feign.Headers;

@FeignClient(
        name = "open-weather-api",
        url = "${open-weather.host}",
        path = "${open-weather.path}"
)
public interface OpenWeatherClient {
    @GetMapping
    @Headers("Accept-Encoding: application/json")
    OpenWeatherResponse weatherForecast(@RequestParam("lon") double longitude,
                                        @RequestParam("lat") double latitude,
                                        @RequestParam("appid") String apiKey);
}
