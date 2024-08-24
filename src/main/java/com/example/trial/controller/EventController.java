package com.example.trial.controller;

import com.example.trial.model.Athlete;
import com.example.trial.model.Event_Item;
import com.example.trial.model.Events;
import com.example.trial.services.DataGenerationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/events")
public class EventController {
    @Autowired
    private DataGenerationService dataGenerationService;

    @GetMapping
    public List<Events> getAllEvents() {

        return dataGenerationService.getAllEvents();
    }

    @GetMapping("/{id}")
    public Events getEventById(@PathVariable Long id) {

        return dataGenerationService.getEventById(id);
    }

    @PostMapping
    public Events createEvent(@RequestBody Events event) {
        return dataGenerationService.saveEvent(event);
    }


    @DeleteMapping("/{id}")
    public void deleteEvent(@PathVariable long id) {
        dataGenerationService.deleteEvent(id);
    }
}


