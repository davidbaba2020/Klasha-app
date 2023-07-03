package com.klashacountryapp.Klashaapp.dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.klashacountryapp.Klashaapp.dtos.response.CurrentPopulation;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CountryDataDtoV1 {
    private String countryName;
    private LocalDate dateInfoRetrieved;
    private CurrentPopulation lastPopulationValue;
    private String capitalCity;
    private LOCATION location;
    private String currency;
    private String ISO2;
    private String ISO3;

}
