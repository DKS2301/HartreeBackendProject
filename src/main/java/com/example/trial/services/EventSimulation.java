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
    private final DataGenerationService dataGenerationService=new DataGenerationService();

    @Transactional
    public void createEventSimulation(long eventId) {
        Events event = dataGenerationService.getEventById(eventId);
        if(event!=null){
            Faker faker = new Faker();
            List<Event_Item> eventItem=new ArrayList<>(event.getEventItem());
            for(Event_Item eventItem1:eventItem){
                Set<Athlete> athletes=eventItem1.getAthletes();
                List<Athlete> athletesList=new ArrayList<>(athletes);
                Set<Athlete> winners=new HashSet<>();
                if(athletesList.size()>3){
                    while(winners.size()<3){
                        winners.add(athletesList.get(faker.number().numberBetween(0, athletes.size()-1)));
                    }
                }
                else{
                    System.out.println("Not enough athletes");
                    continue;
                }
                List<Athlete> winnersList=new ArrayList<>(winners);
                eventItem1.setGold(winnersList.get(0));
                eventItem1.setSilver(winnersList.get(1));
                eventItem1.setBronze(winnersList.get(2));
                System.out.println(dataGenerationService.saveEventItem(eventItem1));
            }
        }
    }
}
