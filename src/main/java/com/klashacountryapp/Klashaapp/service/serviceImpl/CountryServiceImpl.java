package com.klashacountryapp.Klashaapp.service.serviceImpl;


import com.klashacountryapp.Klashaapp.dtos.CountryDataDtoV1;
import com.klashacountryapp.Klashaapp.dtos.LOCATION;
import com.klashacountryapp.Klashaapp.dtos.request.CountryApiRequest;
import com.klashacountryapp.Klashaapp.dtos.request.CountryCitiesPopulationDataRequest;
import com.klashacountryapp.Klashaapp.dtos.response.CurrentPopulation;
import com.klashacountryapp.Klashaapp.dtos.response.countryCapitalDto.CountryCapital;
import com.klashacountryapp.Klashaapp.dtos.response.countryCitiesByPopulation.CountryCitiesPopulationData;
import com.klashacountryapp.Klashaapp.dtos.response.countryCitiesByPopulation.Datum;
import com.klashacountryapp.Klashaapp.dtos.response.countryCitiesByPopulation.PopulationCountCities;
import com.klashacountryapp.Klashaapp.dtos.response.countryCitiesByPopulation.ResponseForPopulationCities;
import com.klashacountryapp.Klashaapp.dtos.response.countryCurrency.CountryCurrency;
import com.klashacountryapp.Klashaapp.dtos.response.countryPopulation.CountryPopulation;
import com.klashacountryapp.Klashaapp.dtos.response.countryPopulation.PopulationCount;
import com.klashacountryapp.Klashaapp.dtos.response.countryPosition.CountryPosition;
import com.klashacountryapp.Klashaapp.helperMethods.AppUtilsMethods;
import com.klashacountryapp.Klashaapp.service.CountryService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDate;
import java.util.*;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CountryServiceImpl implements CountryService {

    private final AppUtilsMethods appUtilsMethods;

    @Value("${oder.for.country.population.fetch}")
    String order;

    @Value("${oderBy.for.country.population.fetch}")
    String orderBy;
    @Override
    public Object getCountryInformations(CountryApiRequest country) throws IOException {
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
        query.setOrder("dsc");
        query.setOrderBy("value");
        return appUtilsMethods.getCountryCitiesPopulationByQuery(query);
    }


    @Override
    public Object getCountryCitiesByPopulationAsRequested(CountryCitiesPopulationDataRequest q) throws IOException {
        Map<String, Object> countryCities = new HashMap<>();
        List<ResponseForPopulationCities> citiesPopulation = new ArrayList<>();
        List<Map<String, Object>> responseForCountriesCitiesPopulation = new ArrayList<>();
        ArrayList<String> countries = new ArrayList<>(Arrays.asList("Italy", "New Zealand", "Ghana"));
        CountryCitiesPopulationData response = new CountryCitiesPopulationData();
        ResponseForPopulationCities r = new ResponseForPopulationCities();
        for (String country : countries) {
            CountryApiRequest query = CountryApiRequest.builder()
                    .country(country)
                    .limit(q.getLimit())
                    .order(order)
                    .orderBy(orderBy)
                    .build();
            response = appUtilsMethods.getCountryCitiesPopulationByQuery(query);
            for (Datum d : response.getData()){
                PopulationCountCities p = d.getPopulationCounts().get(d.getPopulationCounts().size()-1);
                r = ResponseForPopulationCities.builder()
                        .country(response.getData().get(0).getCountry())
                        .dateDataRetrieved(LocalDate.now())
                        .populationYear(p.getYear())
                        .cityName(response.getData().get(0).getCity())
                        .populationSize(p.getValue())
                        .build();
            citiesPopulation.add(r);
            }
//                countryCities.put(country,citiesPopulation);
        }

        return citiesPopulation;
    }

}
