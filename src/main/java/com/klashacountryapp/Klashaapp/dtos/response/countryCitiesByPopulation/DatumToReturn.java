package com.klashacountryapp.Klashaapp.dtos.response.countryCitiesByPopulation;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class DatumToReturn {
    public String city;
    public String country;
    public PopulationCountCity population;
}