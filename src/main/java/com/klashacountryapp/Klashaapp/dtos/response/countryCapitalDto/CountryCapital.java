package com.klashacountryapp.Klashaapp.dtos.response.countryCapitalDto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.klashacountryapp.Klashaapp.dtos.response.countryCapitalDto.CapitalData;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CountryCapital {
    public boolean error;
    public String msg;
    public CapitalData data;
}