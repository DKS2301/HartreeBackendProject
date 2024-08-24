package com.example.trial.controller;

import com.example.trial.model.Athlete;
import com.example.trial.model.Country;
import com.example.trial.model.Events;
import com.example.trial.model.MedalTally;
import com.example.trial.services.DataGenerationService;
import com.example.trial.services.EventSimulation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/functions")
public class FunctionController {
    @Autowired
    private DataGenerationService dataGenerationService;

    @Autowired
    private EventSimulation eventSimulation;

    @GetMapping
    public String[] getAllEvents() {

        return new String[]{"Sim/EventId: Simulation of the event having id EventId",
        "/Gen/no:                    Create a 'n 'new event and assign random data for its attributes",
        "/(Top or Low)N/{category} : Country having highest and lowest points based on category(1:gold ,2:silver ,3:bronze ,4:total points)",
        "/HMA/{eventid}(optional) :  Athlete who won highest number of events across all events or a particular event identified by eventId",
        "/HPA/{gender}(optional) :   Athlete who secured maximum points across all event may be filtered by gender(1:female ,2:male)",

        };
    }

    @GetMapping("/Sim/{id}")
    public Events simulateEventById(@PathVariable Long id) {

        eventSimulation.createEventSimulation(id);
        return dataGenerationService.getEventById(id);
    }

   @GetMapping("/Gen/{records}")
    public String generateRecords(@PathVariable int records) {
        dataGenerationService.generateData(records);
        return "Data generated successfully";
   }

   @GetMapping("/TopN/{category}")
    public Country topNation(@PathVariable int category) {
       return switch (category) {
           case 1 -> dataGenerationService.highestGold();
           case 2 -> dataGenerationService.highestSilver();
           case 3 -> dataGenerationService.highestBronze();
           case 4 -> dataGenerationService.highestPointsByCountry();
           default -> null;
       };
   }

   @GetMapping("/LowN/{category}")
    public Country lowestNation(@PathVariable int category) {
       return switch (category) {
           case 1 -> dataGenerationService.lowestGold();
           case 2 -> dataGenerationService.lowestSilver();
           case 3 -> dataGenerationService.lowestBronze();
           case 4 -> dataGenerationService.lowestPointsByCountry();
           default -> null;
       };
   }

   @GetMapping("/HMA")
    public Athlete highestMedalAthlete() {
        return dataGenerationService.highestMedalAthlete();
   }

   @GetMapping("/HMA/{eventId}")
    public Athlete highestMedalAthlete(@PathVariable Long eventId) {
        return dataGenerationService.highestMedalAthlete(dataGenerationService.getEventById(eventId));
   }

   @GetMapping("/HPA")
    public Athlete maxPointsAthlete() {
            return dataGenerationService.maxPointAthlete();
   }

    @GetMapping("/HPA/{gender}")
    public Athlete maxPointsAthlete(@PathVariable Integer gender) {
        return dataGenerationService.maxPointAthlete(gender);
    }

    @GetMapping("/MT{n}")
    public List<MedalTally> medalTally(@PathVariable int n) {
        return dataGenerationService.firstNTally(n);
    }

    @GetMapping("/MT{n}/{eventId}")
    public List<MedalTally> medalTally(@PathVariable int n,@PathVariable long eventId) {
        return dataGenerationService.firstNTally(n,dataGenerationService.getEventById(eventId));
    }
}


