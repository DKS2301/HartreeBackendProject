package com.example.trial.controller;

import com.example.trial.model.Athlete;
import com.example.trial.model.Event_Item;
import com.example.trial.services.DataGenerationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/eventItems")
public class Event_ItemController {
    @Autowired
    private DataGenerationService dataGenerationService;

    @GetMapping
    public List<Event_Item> getAllEvent_Items() {

        return dataGenerationService.getAllEventItems();
    }

    @GetMapping("/{id}")
    public Event_Item getEvent_ItemById(@PathVariable Long id) {

        return dataGenerationService.getEventItemById(id);
    }

    @PostMapping
    public Event_Item createEvent_Item(@RequestBody Event_Item eventItem) {
        return dataGenerationService.saveEventItem(eventItem);
    }


    @DeleteMapping("/{id}")
    public void deleteEvent_item(@PathVariable long id) {

        dataGenerationService.deleteEventItem(id);
    }
}


