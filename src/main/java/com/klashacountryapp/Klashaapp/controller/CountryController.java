package com.klashacountryapp.Klashaapp.controller;

import com.klashacountryapp.Klashaapp.dtos.request.CountryApiRequest;
import com.klashacountryapp.Klashaapp.dtos.request.CountryCitiesPopulationDataRequest;
import com.klashacountryapp.Klashaapp.service.CountryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/api/v1/klash/country")
@RequiredArgsConstructor
public class CountryController {

    private final CountryService countryService;

    @GetMapping("/population")
    public ResponseEntity<Object> getCountry(@RequestParam String country) throws IOException {
        return ResponseEntity.ok(countryService.getCountryInformations(country));
    }

    @GetMapping("/states")
    public ResponseEntity<Object> getCountryStates(@RequestBody CountryApiRequest country) throws IOException {
        return ResponseEntity.ok(countryService.getCountryStates(country));
    }

    @GetMapping("/cities")
    public ResponseEntity<Object> getCountryCities(@RequestBody CountryApiRequest country) throws IOException {
        return ResponseEntity.ok(countryService.getCountryCities(country));
    }

    @GetMapping("/states/cities")
    public ResponseEntity<Object> getCountryStatesCities(@RequestBody CountryApiRequest country) throws IOException {
        return ResponseEntity.ok(countryService.getCountryStatesCities(country));
    }

    @GetMapping("/cities/population-by-size")
    public ResponseEntity<Object> getCountryCitiesBySize(@RequestBody CountryCitiesPopulationDataRequest queryParams) throws IOException {
        return ResponseEntity.ok(countryService.getCountryCitiesByPopulationAsRequested(queryParams));
    }

//    @GetMapping("/population-by-size")
//    public ResponseEntity<Object> getCountryPopulationByCitySize(@RequestBody CountryCitiesPopulationDataRequest queryParams) throws IOException {
//        return ResponseEntity.ok(countryService.getCountryCitiesByPopulationAsRequested(queryParams));
//    }
}
