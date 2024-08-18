package com.example.trial;

import com.example.trial.model.Country;
import com.example.trial.repository.CountryRepository;
import com.example.trial.services.DataGenerationService;
import com.example.trial.services.EventSimulation;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;
import java.util.ListIterator;

@SpringBootApplication
public class TrialApplication implements ApplicationRunner {
	private final DataGenerationService dataGenerationService;
	private final EventSimulation eventSimulation;
	public TrialApplication(DataGenerationService dataGenerationService, EventSimulation eventSimulation) {
		this.dataGenerationService = dataGenerationService;
        this.eventSimulation = eventSimulation;
    }

	@Override
	public void run(ApplicationArguments args) throws Exception {
		dataGenerationService.generateData(1); // Generate 100 records
		List<Country> countries= dataGenerationService.getAllCountries();
		ListIterator<Country> i=countries.listIterator();
		while (i.hasNext()) {
			Country country=i.next();
			System.out.println(country.getName());
		}
		eventSimulation.createEventSimulation(1);
	}

	public static void main(String[] args) {
		System.out.println("Hello World");
		SpringApplication.run(TrialApplication.class, args);
	}
}
