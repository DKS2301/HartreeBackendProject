package com.example.trial.controller;

import com.example.trial.errorhandling.ResourceNotFoundException;
import com.example.trial.model.Country;
import com.example.trial.services.DataGenerationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/countries")
public class CountryController {
    @Autowired
    private DataGenerationService dataGenerationService;

    @GetMapping
    public List<Country> getAllCountries() {
        if (dataGenerationService.getAllCountries().isEmpty())
            throw new ResourceNotFoundException("No countries found");
        return dataGenerationService.getAllCountries();
    }


    @GetMapping("/{id}")
    public Country getCountryById(@PathVariable String id) {
        if (dataGenerationService.getCountryById(id)==null)
            throw new ResourceNotFoundException("Country with iso code "+id+" was not found");
        return dataGenerationService.getCountryById(id);
    }

    @PostMapping
    public Country createCountry(@RequestBody Country country) {
        return dataGenerationService.saveCountry(country);
    }

    @PutMapping("/{id}")
    public Country updateCountry(@RequestBody String name,@PathVariable String id) {
        Country c=dataGenerationService.getCountryById(id);
        if (c==null)
            throw new ResourceNotFoundException("No country with iso code "+id);
        c.setName(name);
        return dataGenerationService.saveCountry(c);
    }
    @DeleteMapping("/{id}")
    public void deleteCountry(@PathVariable String id) {
        Country c=dataGenerationService.getCountryById(id);
        if (c==null)
            throw new ResourceNotFoundException("No country with iso code "+id);
        dataGenerationService.deleteCountry(id);
    }
}
