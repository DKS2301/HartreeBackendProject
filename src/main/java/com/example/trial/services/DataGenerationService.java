package com.example.trial.services;
import com.example.trial.model.Event_Item;
import com.example.trial.model.Events;
import com.example.trial.repository.AthleteRepository;
import com.example.trial.repository.CountryRepository;
import com.example.trial.model.Country;
import com.example.trial.model.Athlete;
import com.example.trial.repository.EventRepository;
import com.example.trial.repository.Event_ItemRepository;
import com.github.javafaker.Faker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;


@Service
public class DataGenerationService {

    @Autowired
    protected CountryRepository countryRepository;
    @Autowired
    private AthleteRepository athleteRepository;
    @Autowired
    private EventRepository eventRepository;
    @Autowired
    private Event_ItemRepository event_itemRepository;
    @Transactional
    public void generateData(int numberOfRecords) {
        Faker faker = new Faker();
        for (int i = 0; i < numberOfRecords; i++) {
            Events event = new Events();
            event.setName(faker.lorem().word());

            Set<Country> countries = new HashSet<>();
            for (int j = 0; j < 100; j++) {
                Country country = new Country();
                country.setName(faker.address().country());
                country.setIso_code(faker.address().countryCode());
                Country existingCountry = countryRepository.findById(country.getIso_code()).orElse(null);
                if (existingCountry != null) {
                    countries.add(existingCountry);
                } else {
                    countryRepository.save(country);
                    countries.add(country);
                }
            }
            event.setCountries(countries);
            eventRepository.save(event);
            Set<Event_Item> items = new HashSet<>();

            String gender;
            String[] eventitems = {"100m Sprint", "200m Sprint", "400m Sprint", "800m Run", "1500m Run", "5000m Run", "10000m Run", "Marathon", "110m Hurdles", "400m Hurdles", "4x100m Relay", "4x400m Relay", "High Jump", "Pole Vault", "Long Jump", "Triple Jump", "Shot Put", "Discus Throw", "Javelin Throw", "Hammer Throw", "Decathlon"};
            int number_of_items = faker.number().numberBetween(1, eventitems.length);
            Set<String> presentEvents=new HashSet<>();
            while (presentEvents.size() < number_of_items) {
                presentEvents.add(eventitems[faker.number().numberBetween(0,eventitems.length)]);
            }
            for (int j = 0; j < number_of_items; j++) {
                Event_Item item = new Event_Item();
                item.setEvent_name((String) presentEvents.toArray()[j]);
                item.setEvent(event);
                items.add(item);
                event_itemRepository.save(item);
            }
            for (int j = 0; j < faker.number().numberBetween(10, 100); j++) {
                Set<Event_Item> registered_events = new HashSet<>();
                int random = faker.number().numberBetween(0, 1);
                if (random == 1) {
                    gender = "M";
                } else {
                    gender = "F";
                }
                Athlete athlete = new Athlete();
                athlete.setFName(faker.name().firstName());
                athlete.setLName(faker.name().lastName());
                athlete.setAge(faker.number().numberBetween(18, 40));
                athlete.setGender(gender);
                List<Event_Item> itemsList=new ArrayList<>(items);
                for (int k = 0; k < faker.number().numberBetween(1, 5); k++) {
                    registered_events.add(itemsList.get(faker.number().numberBetween(0, number_of_items-1)));
                }
                athlete.setEventItems(registered_events);
                System.out.println("athletes");
                List<Country> countriesList=new ArrayList<>(countries);
                athlete.setCountry(countriesList.get(faker.number().numberBetween(0, countries.size()-1))); // Random country
                athleteRepository.save(athlete);

            }


        }
    }

    public List<Country> getAllCountries() {
        return countryRepository.findAll();
    }
//    public List<Athlete> getAllAthletes() {
//        return athleteRepository.findAll();
//    }
    public List<Events> getAllEvents() {
        return eventRepository.findAll();
    }
    public Events getEventById(long id) {
        return eventRepository.findById(id).orElse(null);
    }
    public List<Event_Item> getAllEventItems() {
        return event_itemRepository.findAll();
    }

    public Country getCountryById(String id) {
        return countryRepository.findById(id).orElse(null);
    }
//    public Athlete getAthleteById(Long id) {
//        return athleteRepository.findById(id).orElse(null);
//    }
//    public Event_Item getEvent_itemRepository(Long id){
//        return event_itemRepository.findById(id).orElse(null);
//    }
    public Country saveCountry(Country country) {
        return countryRepository.save(country);
    }
    public Event_Item saveEvent_Item(Event_Item eventItem){
        return event_itemRepository.save(eventItem);
    }
    public void deleteCountry(String id) {
        countryRepository.deleteById(id);
    }

    public Country updateCountry(String id,Country country) {
        if(countryRepository.existsById(id)) {
            country.setIso_code(id);
            return countryRepository.save(country);
        }
        return null;
    }
}
