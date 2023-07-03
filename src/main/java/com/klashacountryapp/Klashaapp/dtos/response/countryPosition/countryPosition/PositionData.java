package com.klashacountryapp.Klashaapp.dtos.response.countryPosition.countryPosition;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PositionData {
    public String name;
    public String iso2;
    @JsonProperty("long")
    public int longitude;
    @JsonProperty("lat")
    public int latitude;
}
