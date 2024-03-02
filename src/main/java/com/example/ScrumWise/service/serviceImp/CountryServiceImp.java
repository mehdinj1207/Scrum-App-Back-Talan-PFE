package com.example.ScrumWise.service.serviceImp;

import com.example.ScrumWise.model.entity.Country;
import com.example.ScrumWise.repository.CountryRepository;
import com.example.ScrumWise.service.CountryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class CountryServiceImp implements CountryService {
    @Autowired
    private CountryRepository countryRepository ;
    @Override
    public Country getCountryById(Long id) {
        try{
            return countryRepository.findById(id).get();
        }catch (NoSuchElementException e) {
            System.out.println("Country not found!");
            return new Country();
        }
    }
    @Override
    public Country getCountryByIso(String iso){
        try{
            return countryRepository.findByIso(iso);
        }catch (NoSuchElementException e) {
            System.out.println("Country not found!");
            return new Country();
        }
    }

    @Override
    public List<Country> getCountryList() {
        return countryRepository.findAll();
    }

    @Override
    public Country addCountry(Country country) {
        return countryRepository.save(country);
    }
    @Override
    @Transactional
    public List<Country> addCountries(List<Country> countryList) {
        return countryRepository.saveAll(countryList);
    }

    @Override
    public Country updateCountry(Long id, Country country) {
        country.setIdCountry(id);
        return countryRepository.save(country);
    }

    @Override
    public void deleteCountry(Long id) {
        countryRepository.deleteById(id);
    }
}
