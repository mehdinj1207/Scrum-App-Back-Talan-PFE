package com.example.ScrumWise.service;

import com.example.ScrumWise.model.entity.Country;

import java.util.List;

public interface CountryService {
    Country getCountryById(Long id);
    Country getCountryByIso(String iso);
    List<Country> getCountryList();
    Country addCountry(Country country);
    public List<Country> addCountries(List<Country> countryList);
    Country updateCountry(Long id, Country country);
    void deleteCountry(Long id);
}
