package com.example.trial.controller;

import com.example.trial.errorhandling.ResourceNotFoundException;
import com.example.trial.model.Events;
import com.example.trial.services.DataGenerationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/events")
public class EventController {
    @Autowired
    private DataGenerationService dataGenerationService;

    @GetMapping
    public List<Events> getAllEvents() {
        if(dataGenerationService.getAllEvents().isEmpty()) {
           throw new ResourceNotFoundException("No events found");
        }
        return dataGenerationService.getAllEvents();
    }

    @GetMapping("/{id}")
    public Events getEventById(@PathVariable Long id) {
        if(dataGenerationService.getEventById(id)==null)
            throw new ResourceNotFoundException("No events with id "+id+" found");
        return dataGenerationService.getEventById(id);
    }

    @PostMapping
    public Events createEvent(@RequestBody Events event) {
        return dataGenerationService.saveEvent(event);
    }

    @PutMapping("/{id}")
    public Events updateEvent(@RequestBody String name,@PathVariable Long id) {
        if (dataGenerationService.getEventById(id)==null)
            throw new ResourceNotFoundException("No events with id "+id+" found");
        Events event = dataGenerationService.getEventById(id);
        event.setName(name);
        return dataGenerationService.saveEvent(event);
    }

    @DeleteMapping("/{id}")
    public void deleteEvent(@PathVariable long id) {
        if (dataGenerationService.getEventById(id)==null)
            throw new ResourceNotFoundException("No events with id "+id+" found");
        dataGenerationService.deleteEvent(id);
    }
}


