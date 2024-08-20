package com.example.trial.controller;

import com.example.trial.model.Country;
import com.example.trial.services.DataGenerationService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/countries")
public class CountryController {
    @Autowired
    private DataGenerationService dataGenerationService;

    @GetMapping
    public List<Country> getAllCountries() {
        return dataGenerationService.getAllCountries();
    }


    @GetMapping("/{id}")
    public Country getCountryById(@PathVariable String id) {
        return dataGenerationService.getCountryById(id);
    }

    @PostMapping
    public Country createCountry(@RequestBody Country country) {
        return dataGenerationService.saveCountry(country);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Country> updateCountry(@RequestBody Country country,@PathVariable String id) {
        Country updatedCountry = dataGenerationService.updateCountry(id, country);
        if (updatedCountry != null) {
            return ResponseEntity.ok(updatedCountry);
        }
        return ResponseEntity.notFound().build();
    }
    @DeleteMapping("/{id}")
    public void deleteCountry(@PathVariable String id) {
        dataGenerationService.deleteCountry(id);
    }
}
