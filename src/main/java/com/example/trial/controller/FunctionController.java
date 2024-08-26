package com.example.trial.controller;

import com.example.trial.Exceptions.BadRequestException;
import com.example.trial.Exceptions.ResourceNotFoundException;
import com.example.trial.model.*;
import com.example.trial.services.DataGenerationService;
import com.example.trial.services.EventSimulation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/events")
public class FunctionController {

    @Autowired
    private DataGenerationService dataGenerationService;

    @Autowired
    private EventSimulation eventSimulation;

    @GetMapping
    public String[] getAllEvents() {
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

    @GetMapping("/{name}/Sim/{id}")
    public String simulateEventById(@PathVariable Long id, @PathVariable String name) {
        Events event = dataGenerationService.getEventByName(name);
        if (event == null) {
            throw new ResourceNotFoundException("Event " + name + " was not found");
        }

        Event_Item item = dataGenerationService.getEventItemById(id, event);
        if (item == null) {
            throw new ResourceNotFoundException("The Event id " + id + " does not exist. Check another event item");
        }

        if (dataGenerationService.getAthleteesByEventItem(event, item).size() <= 3) {
            throw new ResourceNotFoundException("The event item " + id + " doesn't have enough athletes");
        }

        return eventSimulation.createEventSimulation(item, event);
    }

    @GetMapping("/{name}/Sim")
    public String simulateEventById(@PathVariable String name) {
        Events event = dataGenerationService.getEventByName(name);
        if (event == null) {
            throw new ResourceNotFoundException("Event " + name + " was not found");
        }

        if (dataGenerationService.getAllEventItemsByEvent(event).isEmpty()) {
            throw new ResourceNotFoundException("The Event " + name + " does not have any event items. Generate data to add athletes and event items.");
        }

        return eventSimulation.createEventSimulation(null, event);
    }

    @GetMapping("/{name}/Gen")
    public String generateRecords(@PathVariable String name) {
        Events event = dataGenerationService.getEventByName(name);
        if (event == null) {
            throw new BadRequestException("Enter a valid event name");
        }
        dataGenerationService.generateData(event);
        return "Data generated successfully";
    }

    @GetMapping("/TopN/{category}")
    public Country topNation(@PathVariable int category) {
        if (dataGenerationService.getAllCountries().isEmpty()) {
            throw new BadRequestException("No countries found");
        }
        return switch (category) {
            case 1 -> dataGenerationService.highestGold();
            case 2 -> dataGenerationService.highestSilver();
            case 3 -> dataGenerationService.highestBronze();
            case 4 -> dataGenerationService.highestPointsByCountry();
            default -> throw new BadRequestException("Invalid category");
        };
    }

    @GetMapping("/{name}/TopN/{category}")
    public Country topNation(@PathVariable int category, @PathVariable String name) {
        Events event = dataGenerationService.getEventByName(name);
        if (event == null) {
            throw new BadRequestException("Enter a valid event name");
        }

        if (dataGenerationService.findCountriesByEvent(event).isEmpty()) {
            throw new BadRequestException("No countries found in event " + name);
        }

        return switch (category) {
            case 1 -> dataGenerationService.highestGold(event);
            case 2 -> dataGenerationService.highestSilver(event);
            case 3 -> dataGenerationService.highestBronze(event);
            case 4 -> dataGenerationService.highestPointsByCountry(event);
            default -> throw new BadRequestException("Invalid category");
        };
    }

    @GetMapping("/LowN/{category}")
    public Country lowestNation(@PathVariable int category) {
        if (dataGenerationService.getAllCountries().isEmpty()) {
            throw new BadRequestException("No countries found");
        }
        return switch (category) {
            case 1 -> dataGenerationService.lowestGold();
            case 2 -> dataGenerationService.lowestSilver();
            case 3 -> dataGenerationService.lowestBronze();
            case 4 -> dataGenerationService.lowestPointsByCountry();
            default -> throw new BadRequestException("Invalid category");
        };
    }

    @GetMapping("/{name}/LowN/{category}")
    public Country lowestNation(@PathVariable int category, @PathVariable String name) {
        Events event = dataGenerationService.getEventByName(name);
        if (event == null) {
            throw new BadRequestException("Enter a valid event name");
        }

        if (dataGenerationService.findCountriesByEvent(event).isEmpty()) {
            throw new BadRequestException("No countries found in event " + name);
        }

        return switch (category) {
            case 1 -> dataGenerationService.lowestGold(event);
            case 2 -> dataGenerationService.lowestSilver(event);
            case 3 -> dataGenerationService.lowestBronze(event);
            case 4 -> dataGenerationService.lowestPointsByCountry(event);
            default -> throw new BadRequestException("Invalid category");
        };
    }

    @GetMapping("/HMA")
    public Athlete highestMedalAthlete() {
        if (dataGenerationService.getAllAthletes().isEmpty()) {
            throw new ResourceNotFoundException("No athletes found");
        }
        return dataGenerationService.highestMedalAthlete();
    }

    @GetMapping("/{name}/HMA")
    public Athlete highestMedalAthlete(@PathVariable String name) {
        Events event = dataGenerationService.getEventByName(name);
        if (event == null) {
            throw new ResourceNotFoundException("No event with name " + name + " found");
        }

        if (dataGenerationService.findAthletesByEvent(event).isEmpty()) {
            throw new ResourceNotFoundException("No athletes found for event " + name);
        }

        return dataGenerationService.highestMedalAthlete(event);
    }

    @GetMapping("/HPA")
    public Athlete maxPointsAthlete() {
        if (dataGenerationService.getAllAthletes().isEmpty()) {
            throw new ResourceNotFoundException("No athletes found");
        }
        return dataGenerationService.maxPointAthlete();
    }

    @GetMapping("/HPA/{gender}")
    public Athlete maxPointsAthlete(@PathVariable Integer gender) {
        if (gender == 1 || gender == 2) {
            if (gender == 1 ? dataGenerationService.femaleAthletes().isEmpty() : dataGenerationService.maleAthletes().isEmpty()) {
                throw new ResourceNotFoundException("No " + (gender == 1 ? "Female" : "Male") + " athletes found");
            }
            return dataGenerationService.maxPointAthlete(gender);
        }
        throw new BadRequestException("Invalid gender");
    }

    @GetMapping("/{name}/HPA")
    public Athlete maxPointsAthlete(@PathVariable String name) {
        Events event = dataGenerationService.getEventByName(name);
        if (event == null) {
            throw new ResourceNotFoundException("No event " + name + " found");
        }

        if (dataGenerationService.findAthletesByEvent(event).isEmpty()) {
            throw new ResourceNotFoundException("No athletes found");
        }

        return dataGenerationService.maxPointAthlete(event);
    }

    @GetMapping("/{name}/HPA/{gender}")
    public Athlete maxPointsAthlete(@PathVariable Integer gender, @PathVariable String name) {
        Events event = dataGenerationService.getEventByName(name);
        if (event == null) {
            throw new ResourceNotFoundException("No event " + name + " found");
        }

        if (gender == 1 || gender == 2) {
            if (gender == 1 ? dataGenerationService.femaleAthletesByEvent(event).isEmpty() : dataGenerationService.maleAthletesByEvent(event).isEmpty()) {
                throw new ResourceNotFoundException("No " + (gender == 1 ? "Female" : "Male") + " athletes found");
            }
            return dataGenerationService.maxPointAthlete(gender,event);
        }
        throw new BadRequestException("Invalid gender");
    }

    @GetMapping("/MT{n}")
    public List<MedalTally> medalTally(@PathVariable int n) {
        if (dataGenerationService.getAllCountries().isEmpty()) {
            throw new BadRequestException("No countries found");
        }
        return dataGenerationService.firstNTally(n);
    }

    @GetMapping("/{name}/MT{n}")
    public List<MedalTally> medalTally(@PathVariable int n, @PathVariable String name) {
        Events event = dataGenerationService.getEventByName(name);
        if (event == null) {
            throw new BadRequestException("Enter a valid event name");
        }

        if (dataGenerationService.findCountriesByEvent(event).isEmpty()) {
            throw new BadRequestException("No countries found for event " + name);
        }

        return dataGenerationService.firstNTally(n, event);
    }
}
