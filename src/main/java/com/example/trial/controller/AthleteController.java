package com.example.trial.controller;

import com.example.trial.model.Athlete;
import com.example.trial.services.DataGenerationService;
import jakarta.transaction.Transactional;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/athletes")
public class AthleteController {
    @Autowired
    private DataGenerationService dataGenerationService;
    @Transactional

    @GetMapping
    public List<Athlete> getAllAthletes() {
        Hibernate.initialize(dataGenerationService.getAllAthletes()); // Ensure Hibernate session is active
        return dataGenerationService.getAllAthletes();
    }

    @Transactional
    @GetMapping("/{id}")
    public Athlete getAthleteById(@PathVariable Long id) {
        Hibernate.initialize(dataGenerationService.getAthleteById(id)); // Ensure Hibernate session is active
        return dataGenerationService.getAthleteById(id);
    }

    @Transactional
    @PostMapping
    public Athlete createAthlete(@RequestBody Athlete athlete) {
        Hibernate.initialize(dataGenerationService.saveAthlete(athlete)); // Ensure Hibernate session is active
        return dataGenerationService.saveAthlete(athlete);
    }
    @Transactional
    @PutMapping("/{id}")
    public ResponseEntity<Athlete> updateCountry(@RequestBody Athlete athlete, @PathVariable Long id) {
        Hibernate.initialize(dataGenerationService.saveAthlete(athlete)); // Ensure Hibernate session is active
        Athlete updatedAthlete = dataGenerationService.saveAthlete(athlete);
        if (updatedAthlete != null) {
            return ResponseEntity.ok(updatedAthlete);
        }
        return ResponseEntity.notFound().build();
    }
    @Transactional
    @DeleteMapping("/{id}")
    public void deleteAthlete(@PathVariable long id) {

        dataGenerationService.deleteAthlete(id);
    }
}


