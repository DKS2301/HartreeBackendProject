package com.example.trial.controller;

import com.example.trial.Exceptions.ResourceNotFoundException;
import com.example.trial.model.Athlete;
import com.example.trial.model.Event_Item;
import com.example.trial.model.Events;
import com.example.trial.services.DataGenerationService;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("events/{name}/eventItems")
public class Event_ItemController {
    @Autowired
    private DataGenerationService dataGenerationService;

    @GetMapping
    public List<Event_Item> getAllEvent_Items(@PathVariable String name) {
        Events event = dataGenerationService.getEventByName(name);
        if (event == null)
            throw new ResourceNotFoundException("Event not found");
        else if (dataGenerationService.getAllEventItemsByEvent(event).isEmpty())
            throw new ResourceNotFoundException("No event items found.Add an event item to simulate");
        return dataGenerationService.getAllEventItemsByEvent(event);
    }

    @GetMapping("/{id}")
    public Event_Item getEventItemById(@PathVariable long id,@PathVariable String name){
        Events event = dataGenerationService.getEventByName(name);
        if (event == null)
            throw new ResourceNotFoundException("Event " + name + " not found");
        if (event.getEventItem().isEmpty())
            throw new ResourceNotFoundException("No Event item " + id + " found for " + name);
        return dataGenerationService.getEventItemById(id, event);
    }

    @GetMapping("/{id}/Winners")
    public Dictionary<String, Athlete> getWinners(@PathVariable long id,@PathVariable String name){
        Events event = dataGenerationService.getEventByName(name);
        if (event == null)
            throw new ResourceNotFoundException("Event " + name + " not found");
        if (dataGenerationService.getEventItemById(id,event)==null)
            throw new ResourceNotFoundException("No Event item " + id + " found for " + name);
        Event_Item item = dataGenerationService.getEventItemById(id, event);
        Dictionary<String, Athlete> winners = new Hashtable<>();
        winners.put("gold", item.getGold());
        winners.put("silver", item.getSilver());
        winners.put("bronze", item.getBronze());
        return winners;
    }

    @PostMapping
    public Event_Item createEvent_Item(@RequestBody Event_Item eventItem, @PathVariable String name){
        Events event = dataGenerationService.getEventByName(name);
        if (event == null)
            throw new ResourceNotFoundException("Event " + name + " not found");
        eventItem.setEvent(dataGenerationService.getEventByName(name));
        return dataGenerationService.saveEventItem(eventItem);
    }


    @DeleteMapping("/{id}")
    public void deleteEvent_item ( @PathVariable long id){
        if (dataGenerationService.getEventItemById(id) == null)
            throw new ResourceNotFoundException("No Event item " + id + " found");
        dataGenerationService.deleteEventItem(id);
    }
}


