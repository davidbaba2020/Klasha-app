package com.klashacountryapp.Klashaapp.service;

import com.klashacountryapp.Klashaapp.dtos.request.CountryApiRequest;
import com.klashacountryapp.Klashaapp.dtos.request.CountryCitiesPopulationDataRequest;

import java.io.IOException;

public interface CountryService {


    Object getCountryInformations(CountryApiRequest country) throws IOException;

    Object getCountryStates(CountryApiRequest country) throws IOException;

    Object getCountryCities(CountryApiRequest country) throws IOException;

    Object getCountryStatesCities(CountryApiRequest country) throws IOException;

    Object getCountryCitiesByPopulation(CountryApiRequest query) throws IOException;

    Object getCountryCitiesByPopulationAsRequested(CountryCitiesPopulationDataRequest q) throws IOException;
}
