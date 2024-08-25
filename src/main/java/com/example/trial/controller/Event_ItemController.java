package com.example.trial.controller;

import com.example.trial.errorhandling.ResourceNotFoundException;
import com.example.trial.model.Event_Item;
import com.example.trial.services.DataGenerationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/eventItems")
public class Event_ItemController {
    @Autowired
    private DataGenerationService dataGenerationService;

    @GetMapping
    public List<Event_Item> getAllEvent_Items() {
        if (dataGenerationService.getAllEventItems().isEmpty())
            throw new ResourceNotFoundException("No event items found");
        return dataGenerationService.getAllEventItems();
    }

    @GetMapping("/{id}")
    public Event_Item getEvent_ItemById(@PathVariable Long id) {
        if (dataGenerationService.getEventItemById(id)==null)
            throw new ResourceNotFoundException("No Event item "+id+" found");
        return dataGenerationService.getEventItemById(id);
    }

    @PostMapping
    public Event_Item createEvent_Item(@RequestBody Event_Item eventItem) {
        return dataGenerationService.saveEventItem(eventItem);
    }


    @DeleteMapping("/{id}")
    public void deleteEvent_item(@PathVariable long id) {
        if (dataGenerationService.getEventItemById(id)==null)
            throw new ResourceNotFoundException("No Event item "+id+" found");
        dataGenerationService.deleteEventItem(id);
    }
}


