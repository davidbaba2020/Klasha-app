package com.klashacountryapp.Klashaapp.dtos.response.countryCitiesByPopulation;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
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
public class ResponseForPopulationCities {
    private String country;
    private LocalDate dateDataRetrieved;
    private String cityName;
    private String populationYear;
    private String populationSize;

}

////Italy, New Zealand and Ghana\
//CountryName
//        DateDataRetrieved
//CityName
//        pupulationValue
//
//        Map<String, List<Response>
//Nigeria ,[
//        city {CityName
//            pupulationValue
//            DateRetrieved
//        }
//
//        ]