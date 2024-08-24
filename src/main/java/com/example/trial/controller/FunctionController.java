package com.example.trial.controller;

import com.example.trial.model.Athlete;
import com.example.trial.model.Country;
import com.example.trial.model.Events;
import com.example.trial.services.DataGenerationService;
import com.example.trial.services.EventSimulation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/functions")
public class FunctionController {
    @Autowired
    private DataGenerationService dataGenerationService;

    @Autowired
    private EventSimulation eventSimulation;

    @GetMapping
    public String[] getAllEvents() {

        return new String[]{"Simulate/EventId: Simulation of the event having id EventId",
        "/Generate/no: Create a 'n 'new event and assign random data for its attributes",
        "/(Top or Lowest)Nation/{category} :Country having highest and lowest points based on category(1:gold ,2:silver ,3:bronze ,4:total points)",
        "/HighestMedalAthlete/{eventid}(optional) :Athlete who won highest number of events across all events or a particular event identified by eventId",
        "/MaxPointAthlete/{gender}(optional) :Athlete who secured maximum points across all event may be filtered by gender(1:female ,2:male)",

        };
    }

    @GetMapping("/Simulate/{id}")
    public Events SimulateEventById(@PathVariable Long id) {

        eventSimulation.createEventSimulation(id);
        return dataGenerationService.getEventById(id);
    }

   @GetMapping("/Generate/{records}")
    public String GenerateRecords(@PathVariable int records) {
        dataGenerationService.generateData(records);
        return "Data generated successfully";
   }

   @GetMapping("/TopNation/{category}")
    public Country TopNation(@PathVariable int category) {
       return switch (category) {
           case 1 -> dataGenerationService.highestGold();
           case 2 -> dataGenerationService.highestSilver();
           case 3 -> dataGenerationService.highestBronze();
           case 4 -> dataGenerationService.highestPointsByCountry();
           default -> null;
       };
   }

   @GetMapping("/LowestNation/{category}")
    public Country LowestNation(@PathVariable int category) {
       return switch (category) {
           case 1 -> dataGenerationService.lowestGold();
           case 2 -> dataGenerationService.lowestSilver();
           case 3 -> dataGenerationService.lowestBronze();
           case 4 -> dataGenerationService.lowestPointsByCountry();
           default -> null;
       };
   }

   @GetMapping("/HighestMedalsAthlete")
    public Athlete HighestMedalsAthlete() {
        return dataGenerationService.highestMedalAthlete();
   }

   @GetMapping("/HighestMedalAthlete/{eventId}")
    public Athlete HighestMedalAthlete(@PathVariable Long eventId) {
        return dataGenerationService.highestMedalAthlete(dataGenerationService.getEventById(eventId));
   }

   @GetMapping("/MaxPointsAthlete/{gender}")
    public Athlete MaxPointsAthlete(@PathVariable Integer gender) {
            return dataGenerationService.maxPointAthlete(gender);
   }
}


