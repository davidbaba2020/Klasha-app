package com.klashacountryapp.Klashaapp.service.serviceImpl;


import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.klashacountryapp.Klashaapp.dtos.CountryDataDtoV1;
import com.klashacountryapp.Klashaapp.dtos.LOCATION;
import com.klashacountryapp.Klashaapp.dtos.request.CountryApiRequest;
import com.klashacountryapp.Klashaapp.dtos.request.CountryCitiesPopulationDataRequest;
import com.klashacountryapp.Klashaapp.dtos.response.CurrentPopulation;
import com.klashacountryapp.Klashaapp.dtos.response.countryCapitalDto.CountryCapital;
import com.klashacountryapp.Klashaapp.dtos.response.countryCitiesByPopulation.*;
import com.klashacountryapp.Klashaapp.dtos.response.countryCurrency.CountryCurrency;
import com.klashacountryapp.Klashaapp.dtos.response.countryPopulation.CountryPopulation;
import com.klashacountryapp.Klashaapp.dtos.response.countryPopulation.PopulationCount;
import com.klashacountryapp.Klashaapp.dtos.response.countryPosition.CountryPosition;
import com.klashacountryapp.Klashaapp.exceptions.BadRequestException;
import com.klashacountryapp.Klashaapp.helperMethods.AppUtilsMethods;
import com.klashacountryapp.Klashaapp.service.CountryService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CountryServiceImpl implements CountryService {

    private final AppUtilsMethods appUtilsMethods;
    private final ObjectMapper objectMapper;


    @Value("${oder.for.country.population.fetch}")
    String order;

    @Value("${oderBy.for.country.population.fetch}")
    String orderBy;
    @Override
    public Object getCountryInformations(String c) throws IOException {
        CountryApiRequest country = CountryApiRequest.builder()
                .country(c)
                .build();
        CountryPopulation populationDetails = appUtilsMethods.getCountryPopulation(country);
        CountryCapital capitalCity = appUtilsMethods.getCountryCapitalCity(country);
        CountryPosition positionLocation = appUtilsMethods.getCountryPosition(country);
        CountryCurrency currency = appUtilsMethods.getCountryCurrency(country);

        PopulationCount populationCount = populationDetails.getData().getPopulationCounts().get(populationDetails.getData().getPopulationCounts().size() - 1);
        CurrentPopulation population = CurrentPopulation.builder()
                .year(populationCount.getYear())
                .value(populationCount.value)
                .build();

        new LOCATION();
        LOCATION location = LOCATION.builder()
                .longitude(positionLocation.getData().getLongitude())
                .latitude(positionLocation.getData().getLatitude())
                .build();

        System.out.println(populationCount);

        new CountryDataDtoV1();
        return CountryDataDtoV1.builder()
                .countryName(country.getCountry())
                .dateInfoRetrieved(LocalDate.now())
                .lastPopulationValue(population)
                .capitalCity(capitalCity.getData().getCapital())
                .location(location)
                .currency(currency.getData().currency)
                .ISO2(capitalCity.getData().getIso2())
                .ISO3(capitalCity.getData().getIso2())
                .build();
    }


    @Override
    public Object getCountryStates(CountryApiRequest country) throws IOException {
        return appUtilsMethods.getCountryStates(country);
    }


    @Override
    public Object getCountryCities(CountryApiRequest country) throws IOException {
        return appUtilsMethods.getCountryCities(country);
    }


    @Override
    public Object getCountryStatesCities(CountryApiRequest country) throws IOException {
        return appUtilsMethods.getStateDetails(country);
    }


    @Override
    public Object getCountryCitiesByPopulation(CountryApiRequest query) throws IOException {
        if(query.getLimit()==0) {
            throw new BadRequestException("Must provide a limit");
        }
        query.setOrder("dsc");
        query.setOrderBy("value");
        return appUtilsMethods.getCountryCitiesPopulationByQuery(query);
    }


    @Override
    public Object getCountryCitiesByPopulationAsRequested(CountryCitiesPopulationDataRequest q) throws IOException {
        List<DatumToReturn> citiesPopulation = new ArrayList<>();
        ArrayList<String> countries = new ArrayList<>(Arrays.asList("Italy", "New Zealand", "Ghana"));
        List<DatumToReturn> response = null;
        List<ResponseForPopulationCities> cities = new ArrayList<>();
        ResponseForPopulationCities r = new ResponseForPopulationCities();
        CountryCitiesPopulationData d = null;
        List<CountryCitiesPopulationData> dList = new ArrayList<>();
        for (String country : countries) {
            CountryApiRequest query = CountryApiRequest.builder()
                    .country(country)
                    .limit(q.getLimit())
                    .order(order)
                    .orderBy(orderBy)
                    .build();
            d = appUtilsMethods.getCountryCitiesPopulationByQuery(query);
            dList.add(d);

        }
        return dList.stream()
                .flatMap(ccP -> ccP.getData().stream())
                .map(du -> {
                    PopulationCountCity p = du.getPopulationCounts().get(du.getPopulationCounts().size() - 1);
                    return DatumToReturn.builder()
                            .city(du.getCity())
                            .country(du.getCountry())
                            .population(p)
                            .build();
                })
                .collect(Collectors.toList());
    }

}
