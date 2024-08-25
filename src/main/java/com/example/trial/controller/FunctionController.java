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
import java.util.Optional;

@RestController
@RequestMapping("/functions")
public class FunctionController {
    @Autowired
    private DataGenerationService dataGenerationService;

    @Autowired
    private EventSimulation eventSimulation;

    @GetMapping
    public String[] getAllEvents() {

        // List of all available API endpoints.
        return new String[]{
                "/Sim/{EventId} : Simulate the event by EventId",
                "/Gen/{n} : Generate 'n' new events and assign random data for their attributes",
                "/TopN/{category} : Retrieve the top country based on category (1:gold, 2:silver, 3:bronze, 4:total points)",
                "/LowN/{category} : Retrieve the lowest performing country based on category (1:gold, 2:silver, 3:bronze, 4:total points)",
                "/HMA : Retrieve the athlete with the highest number of medals across all events",
                "/HMA/{eventId} : Retrieve the athlete with the highest number of medals in a specific event by eventId",
                "/HPA : Retrieve the athlete with the maximum points across all events",
                "/HPA/{gender} : Retrieve the athlete with the maximum points across all events filtered by gender (1:female, 2:male)",
                "/MT{n} : Retrieve the top 'n' countries by medal tally",
                "/MT{n}/{eventId} : Retrieve the top 'n' countries by medal tally for a specific event by eventId"
        };
    }

    //Simulate an event identified by event id
    @GetMapping("/Sim/{id}")
    public Events simulateEventById(@PathVariable Long id) {

        eventSimulation.createEventSimulation(id);
        return dataGenerationService.getEventById(id);
    }

    //Generate random data for n events
   @GetMapping("/Gen/{n}")
    public String generateRecords(@PathVariable int records) {
        dataGenerationService.generateData(records);
        return "Data generated successfully";
   }

   //Retrieve the top nation based on category
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

   //Retrieve the lowest nation based on category
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

   //Retrieve the athlete with most medals
   @GetMapping("/HMA")
    public Athlete highestMedalAthlete() {
        return dataGenerationService.highestMedalAthlete(null);
   }

   //Retrieve the athlete with most medals in a particular event
   @GetMapping("/HMA/{eventId}")
    public Athlete highestMedalAthlete(@PathVariable Long eventId) {
        return dataGenerationService.highestMedalAthlete(dataGenerationService.getEventById(eventId));
   }

   //Retrieve the athlete with most point
   @GetMapping("/HPA")
    public Athlete maxPointsAthlete() {
            return dataGenerationService.maxPointAthlete();
   }

   //Retrieve the athlete with most point based on gender
    @GetMapping("/HPA/{gender}")
    public Athlete maxPointsAthlete(@PathVariable Integer gender) {
        return dataGenerationService.maxPointAthlete(gender);
    }

    //Display medal tally of top n nations across all events
    @GetMapping("/MT{n}")
    public List<MedalTally> medalTally(@PathVariable int n) {
        return dataGenerationService.firstNTally(n,null);
    }

    //Display medal tally of top n nations across a particular event
    @GetMapping("/MT{n}/{eventId}")
    public List<MedalTally> medalTally(@PathVariable int n,@PathVariable long eventId) {
        return dataGenerationService.firstNTally(n,dataGenerationService.getEventById(eventId));
    }
}


