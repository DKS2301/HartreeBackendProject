package com.example.trial.controller;

import com.example.trial.Exceptions.BadRequestException;
import com.example.trial.Exceptions.ResourceNotFoundException;
import com.example.trial.model.Events;
import com.example.trial.services.DataGenerationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/events/AllEvents")
public class EventController {
    @Autowired
    private DataGenerationService dataGenerationService;

    @GetMapping
    public List<Events> getAllEvents() {
        if(dataGenerationService.getAllEvents().isEmpty()) {
            throw new ResourceNotFoundException("No events found. Add atleast one event");
        }
        return dataGenerationService.getAllEvents();
    }

    @GetMapping("/{name}")
    public Events getEventByName(@PathVariable String name) {
        if(dataGenerationService.getEventByName(name)==null)
            throw new ResourceNotFoundException("No event "+name+" found");
        return dataGenerationService.getEventByName(name);
    }

    @PostMapping("/addNew")
    public void createEvent(@RequestBody String name) {
        Events event=dataGenerationService.getEventByName(name);
        if(event!=null)
            throw new BadRequestException("Event "+event.getName()+" already exists");
        event=new Events();
        event.setName(name);
        dataGenerationService.saveEvent(event);
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


