package com.example.trial;

import com.example.trial.model.Country;
import com.example.trial.model.Events;
import com.example.trial.model.MedalTally;
import com.example.trial.repository.CountryRepository;
import com.example.trial.repository.EventRepository;
import com.example.trial.services.DataGenerationService;
import com.example.trial.services.EventSimulation;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

@SpringBootApplication
public class TrialApplication implements ApplicationRunner {
	private final DataGenerationService dataGenerationService;
	private final EventSimulation eventSimulation;
	private final EventRepository eventRepository;

	public TrialApplication(DataGenerationService dataGenerationService, EventSimulation eventSimulation, EventRepository eventRepository) {
		this.dataGenerationService = dataGenerationService;
        this.eventSimulation = eventSimulation;
		this.eventRepository = eventRepository;
	}

	@Override
	public void run(ApplicationArguments args) throws Exception {
		dataGenerationService.generateData(1); // Generate 100 records
		eventSimulation.createEventSimulation(1);
		eventSimulation.createEventSimulation(2);
		List<Events> events=dataGenerationService.getAllEvents();
		List<MedalTally> mt=new ArrayList<>();
		System.out.println("\n\n[OUTPUT]Events are\n"+events);
		for (Events event:events) {
			if(event.getId()==1) {
				mt = dataGenerationService.firstNTally(4, event);
			}
		}
		System.out.println(mt);


	}

	public static void main(String[] args) {
		System.out.println("Hello World");
		SpringApplication.run(TrialApplication.class, args);
	}
}
