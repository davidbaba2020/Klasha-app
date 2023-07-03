package com.klashacountryapp.Klashaapp.helperMethods;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.klashacountryapp.Klashaapp.dtos.request.CountryApiRequest;
import com.klashacountryapp.Klashaapp.dtos.response.countryAndStates.CountryStateData;
import com.klashacountryapp.Klashaapp.dtos.response.countryAndStates.State;
import com.klashacountryapp.Klashaapp.dtos.response.countryCapitalDto.CountryCapital;
import com.klashacountryapp.Klashaapp.dtos.response.countryCities.CountryCities;
import com.klashacountryapp.Klashaapp.dtos.response.countryCitiesByPopulation.CountryCitiesPopulationData;
import com.klashacountryapp.Klashaapp.dtos.response.countryCurrency.CountryCurrency;
import com.klashacountryapp.Klashaapp.dtos.response.countryPopulation.CountryPopulation;
import com.klashacountryapp.Klashaapp.dtos.response.countryPosition.CountryPosition;
import lombok.RequiredArgsConstructor;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.LaxRedirectStrategy;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AppUtilsMethods {

    private final ObjectMapper objectMapper;

    @Value("${baseurl}")
    String baseUrl;

    private final Logger log = LoggerFactory.getLogger(AppUtilsMethods.class);

    public CountryPopulation getCountryPopulation(CountryApiRequest country) throws IOException {
        String apiUrl = baseUrl+"/population";
        var countryPopulation = callCountryAndCityApi(country,apiUrl);
        return (CountryPopulation) convertToEntity(countryPopulation, CountryPopulation.class);
    }


    public CountryCapital getCountryCapitalCity(CountryApiRequest country) throws IOException {
        String apiUrl = baseUrl+"/capital";
        var countryCapital = callCountryAndCityApi(country,apiUrl);
        return (CountryCapital) convertToEntity(countryCapital, CountryCapital.class);
    }

    public CountryPosition getCountryPosition(CountryApiRequest country) throws IOException {
        String apiUrl = baseUrl+"/positions";
        var countryPosition = callCountryAndCityApi(country,apiUrl);
        return (CountryPosition) convertToEntity(countryPosition, CountryPosition.class);
    }


    public CountryCurrency getCountryCurrency(CountryApiRequest country) throws IOException {
        String apiUrl = baseUrl+"/currency";
        var countryCurrency = callCountryAndCityApi(country,apiUrl);
        return (CountryCurrency) convertToEntity(countryCurrency, CountryCurrency.class);
    }

    public CountryStateData getCountryStates(CountryApiRequest country) throws IOException {
        String apiUrl = baseUrl+"/states";
        var countryStates = callCountryAndCityApi(country,apiUrl);
        return (CountryStateData) convertToEntity(countryStates, CountryStateData.class);
    }

    public CountryCities getCountryCities(CountryApiRequest country) throws IOException {
        String apiUrl = baseUrl+"/state/cities";
        var countryStates = callCountryAndCityApi(country,apiUrl);
        return (CountryCities) convertToEntity(countryStates, CountryCities.class);
    }


    public List<State> getStateDetails(CountryApiRequest country) throws IOException {
        CountryStateData countryStateData = getCountryStates(country);
        return countryStateData.getData().getStates().stream()
                .peek(stateResponse -> {
                    CountryApiRequest countryRequest = CountryApiRequest.builder()
                            .country(country.getCountry())
                            .state(stateResponse.getName())
                            .build();
                    List<String> cities = null;
                    try {
                        cities = getCountryCities(countryRequest).getData();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    stateResponse.setCites(cities);
                    stateResponse.setCountryName(country.getCountry());
                    stateResponse.setDateRetrieved(LocalDate.now());
                })
                .collect(Collectors.toList());
    }



    public CountryCitiesPopulationData getCountryCitiesPopulationByQuery(CountryApiRequest query) throws IOException {
        String apiUrl = baseUrl+"/population/cities/filter";
        var countryCitiesPopulationData = callCountryAndCityApi(query,apiUrl);
        return (CountryCitiesPopulationData) convertToEntity(countryCitiesPopulationData, CountryCitiesPopulationData.class);
    }



    protected String callCountryAndCityApi(CountryApiRequest country, String apiUrl) throws IOException {
        HttpPost httpPost = new HttpPost(apiUrl);

        String requestBody = objectMapper.writeValueAsString(country);
        StringEntity requestEntity = new StringEntity(requestBody, ContentType.APPLICATION_JSON);
        httpPost.setEntity(requestEntity);

        HttpClient httpClient = HttpClientBuilder.create().setRedirectStrategy(new LaxRedirectStrategy()).build();
        HttpResponse response = httpClient.execute(httpPost);

        HttpEntity entity = response.getEntity();
        String responseBody = EntityUtils.toString(entity);
//        System.out.println("Response Status Code: " + response.getStatusLine().getStatusCode());
        System.out.println("Response Body: " + responseBody);

        return responseBody;
    }


    protected <T> Object  convertToEntity(String countryPopulation, Class<T> classToConvert) {
        try {
            log.info("Country Population Response, {}", countryPopulation);
            return objectMapper.readValue(countryPopulation, classToConvert);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
