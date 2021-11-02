package com.mattcollier.weather.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequiredArgsConstructor
@Slf4j
public class WeatherController {

    @GetMapping(value = {"/warmest-day"}, produces = {"application/json"})
    public ResponseEntity<String> warmestDay(@RequestParam String longitude, @RequestParam String latitude) {
        return ResponseEntity.ok("{}");
    }
}
