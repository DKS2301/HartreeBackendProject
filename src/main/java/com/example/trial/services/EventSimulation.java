package com.example.trial.services;

import com.example.trial.model.Athlete;
import com.example.trial.model.Event_Item;
import com.example.trial.model.Events;
import com.github.javafaker.Faker;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class EventSimulation {

    @Autowired
    private final DataGenerationService dataGenerationService = new DataGenerationService();

    @Transactional
    public String createEventSimulation(Event_Item item, Events event) {
        StringBuilder response = new StringBuilder();
        if (event != null) {
            Faker faker = new Faker();
            if (item == null) {
                List<Event_Item> eventItems = new ArrayList<>(dataGenerationService.getAllEventItemsByEvent(event));
                for (Event_Item eventItem : eventItems) {
                    if(!eventItem.isCompleted()) {
                        List<Athlete> athletesList = new ArrayList<>(dataGenerationService.getAthleteesByEventItem(event, eventItem));
                        Set<Athlete> winners = new HashSet<>();
                        if (athletesList.size() > 3) {
                            while (winners.size() < 3) {
                                winners.add(athletesList.get(faker.number().numberBetween(0, athletesList.size() - 1)));
                            }
                        } else {
                            response.append("Not enough athletes for event item ").append(eventItem.getId()).append("Add least 4 athletes to simulate event ");
                            continue;
                        }
                        List<Athlete> winnersList = new ArrayList<>(winners);
                        eventItem.setGold(winnersList.get(0));
                        eventItem.setSilver(winnersList.get(1));
                        eventItem.setBronze(winnersList.get(2));
                        eventItem.setCompleted(true);
                        response.append(dataGenerationService.saveEventItem(eventItem));
                    }
                }
            } else {
                if(!item.isCompleted()) {
                    List<Athlete> athletes = new ArrayList<>(dataGenerationService.getAthleteesByEventItem(event,item));
                    Set<Athlete> winners = new HashSet<>();
                    if (athletes.size() > 3) {
                        while (winners.size() < 3) {
                            winners.add(athletes.get(faker.number().numberBetween(0, athletes.size() - 1)));
                        }
                    }
                    else {
                        response.append("Not enough athletes for event item ").append(item.getId()).append("Add least 4 athletes to simulate event \n");
                    }
                    List<Athlete> winnersList = new ArrayList<>(winners);
                    item.setGold(winnersList.get(0));
                    item.setSilver(winnersList.get(1));
                    item.setBronze(winnersList.get(2));
                    item.setCompleted(true);
                    response.append(dataGenerationService.saveEventItem(item));
                }
                else {
                    return "The event item is already completed";
                }
            }

        }
        response.append("Successfully simulated");
        return response.toString();
    }
}
