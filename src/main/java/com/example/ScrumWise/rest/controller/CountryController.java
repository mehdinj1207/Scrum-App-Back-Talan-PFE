package com.example.ScrumWise.rest.controller;

import com.example.ScrumWise.model.entity.Country;
import com.example.ScrumWise.rest.dto.CountryDto;
import com.example.ScrumWise.service.CountryService;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import java.lang.reflect.Type;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin
public class CountryController {
    @Autowired
    private CountryService countryService ;
    @Autowired
    private ModelMapper modelMapper ;
    @GetMapping("/countries")
    @RolesAllowed({"Human Ressources","Manager","Consultant"})
    public Object CountryList() {
        List<Country> countries = countryService.getCountryList();
        Type listType = new TypeToken<List<CountryDto>>() {}.getType() ;
        List<CountryDto> countryDtos = modelMapper.map(countries,listType);
        return ResponseEntity.status(HttpStatus.OK).body(countryDtos);
    }
    @GetMapping("/countries/{idCountry}")
    @RolesAllowed({"Human Ressources","Manager","Consultant"})
    public Object Country(@PathVariable Long idCountry ) {
        Country country = countryService.getCountryById(idCountry) ;
        if (country.getIdCountry()==null)
            return ResponseEntity.status(HttpStatus.OK).body(null);
        else {
            CountryDto countryDto = modelMapper.map(country, CountryDto.class);
            return ResponseEntity.status(HttpStatus.OK).body(countryDto);
        }
    }

    @PostMapping("/countries")
    @RolesAllowed("Human Ressources")
    public Object AddCountry(@Validated @RequestBody CountryDto countryDto)
    {
        Country country = modelMapper.map(countryDto, Country.class);
        country = countryService.addCountry(country);
        countryDto = modelMapper.map(country, CountryDto.class);
        return ResponseEntity.status(HttpStatus.CREATED).body(countryDto);
    }
    @PostMapping("/countries/all")
    @RolesAllowed("Human Ressources")
    public Object AddCountries(@Validated @RequestBody List<CountryDto> countryDtoList) {
        List<Country> countryList = countryDtoList.stream()
                .map(countryDto -> modelMapper.map(countryDto, Country.class))
                .collect(Collectors.toList());
        countryList = countryService.addCountries(countryList);
        List<CountryDto> savedCountryDtoList = countryList.stream()
                .map(country -> modelMapper.map(country, CountryDto.class))
                .collect(Collectors.toList());
        return ResponseEntity.status(HttpStatus.CREATED).body(savedCountryDtoList);
    }

    @PutMapping("/countries/{idCountry}")
    @RolesAllowed("Human Ressources")
    public Object UpdateCountry (@Validated @RequestBody CountryDto countryDto, @PathVariable Long idCountry) {
        Country country = modelMapper.map(countryDto, Country.class);
        country = countryService.updateCountry(idCountry, country);
        countryDto = modelMapper.map(country, CountryDto.class);
        return ResponseEntity.status(HttpStatus.CREATED).body(countryDto);

    }

    @DeleteMapping("/countries/{idCountry}")
    @RolesAllowed("Human Ressources")
    public Object DeleteCountry(@PathVariable Long idCountry) {
        countryService.deleteCountry(idCountry);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);

    }
}

