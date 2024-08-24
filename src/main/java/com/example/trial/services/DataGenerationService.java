package com.example.trial.services;
import com.example.trial.model.*;
import com.example.trial.repository.AthleteRepository;
import com.example.trial.repository.CountryRepository;
import com.example.trial.repository.EventRepository;
import com.example.trial.repository.Event_ItemRepository;
import com.github.javafaker.Faker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

import static java.lang.Math.min;


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
    @Transactional
    public List<Country> getAllCountries() {

        return countryRepository.findAll();
    }
    @Transactional
    public long totalMedalCount(Athlete athlete){
        return event_itemRepository.countgoldMedals(athlete)+event_itemRepository.countsilverMedals(athlete)+event_itemRepository.countbronzeMedals(athlete);
    }
    @Transactional
    public long totalPoints(Athlete athlete){
        return event_itemRepository.countgoldMedals(athlete)*3+event_itemRepository.countsilverMedals(athlete)*2+event_itemRepository.countbronzeMedals(athlete)*1;
    }
    @Transactional
    public List<Athlete> getAllAthletes() {
        return athleteRepository.findAll();
    }
    @Transactional
    public Athlete highestMedalAthlete(Events event){
        Athlete maxMedals =null;
        List<Athlete> athletes=eventRepository.findAthletesByEvent(event);
        for(Athlete athlete:athletes){
            if(maxMedals ==null){
                maxMedals =athlete;
            }
            else{
                if(totalMedalCount(maxMedals)<totalMedalCount(athlete)){
                    maxMedals =athlete;
                }
            }
        }
        return maxMedals;
    }
    @Transactional
    public Athlete maxPointAthlete(){
        Athlete maxPoints =null;
        List<Athlete> athletes=athleteRepository.findAll();
        for(Athlete athlete:athletes){
            if(maxPoints ==null){
                maxPoints =athlete;
            }
            else{
                if(totalPoints(maxPoints)<totalPoints(athlete)){
                    maxPoints =athlete;
                }
            }
        }
        return maxPoints;
    }
    @Transactional
    public List<MedalTally> firstNTally(int n){
        List<Country> countries=countryRepository.findAll();
        List<MedalTally> medalTallies=new ArrayList<>();
        for(Country country:countries){
            MedalTally c=new MedalTally(country,countryGold(country),countrySilver(country),countryBronze(country),totalPointsByCountry(country));
            medalTallies.add(c);
        }
        medalTallies.sort(Collections.reverseOrder());
        return medalTallies.subList(0,min(n,medalTallies.size()-1));
    }
    @Transactional
    public List<MedalTally> firstNTally(int n,Events event){
        List<Country> countries=countryRepository.findCountryByEvent(event);
        List<MedalTally> medalTallies=new ArrayList<>();
        for(Country country:countries){
            MedalTally c=new MedalTally(country,countryGold(country,event),countrySilver(country,event),countryBronze(country,event),totalPointsByCountry(country,event));
            medalTallies.add(c);
        }
        medalTallies.sort(Collections.reverseOrder());
        return medalTallies.subList(0,min(n,medalTallies.size()-1));
    }
    @Transactional
    public long totalPointsByCountry(Country country){
        return 3*countryGold(country)+2*countrySilver(country)+countryBronze(country);
    }
    @Transactional
    public long totalPointsByCountry(Country country,Events event){
        return 3*countryGold(country,event)+2*countrySilver(country,event)+countryBronze(country,event);
    }
    @Transactional
    public long countryMedals(Country country){
        long point=0;
        Set<Athlete> athletes=athleteRepository.countryAthletes(country);
        for(Athlete athlete:athletes){
            point+=totalMedalCount(athlete);
        }
        return point;
    }
    @Transactional
    public long countryGold(Country country){
        long golds=0;
        Set<Athlete> athletes=athleteRepository.countryAthletes(country);
        for(Athlete athlete:athletes){
            golds+=event_itemRepository.countgoldMedals(athlete);
        }
        return golds;
    }
    @Transactional
    public long countrySilver(Country country){
        long silvers=0;
        Set<Athlete> athletes=athleteRepository.countryAthletes(country);
        for(Athlete athlete:athletes){
            silvers+=event_itemRepository.countsilverMedals(athlete);
        }
        return silvers;
    }
    @Transactional
    public long countryBronze(Country country){
        long bronzes =0;
        Set<Athlete> athletes=athleteRepository.countryAthletes(country);
        for(Athlete athlete:athletes){
            bronzes +=event_itemRepository.countbronzeMedals(athlete);
        }
        return bronzes;
    }
    @Transactional
    public long countryGold(Country country,Events event){
        long golds=0;
        List<Athlete> athletes=eventRepository.findAthletesByEventCountry(event,country);
        for(Athlete athlete:athletes){
            golds+=event_itemRepository.countgoldMedals(athlete);
        }
        return golds;
    }
    @Transactional
    public long countrySilver(Country country,Events event){
        long silvers=0;
        List<Athlete> athletes=eventRepository.findAthletesByEventCountry(event,country);
        for(Athlete athlete:athletes){
            silvers+=event_itemRepository.countsilverMedals(athlete);
        }
        return silvers;
    }
    @Transactional
    public long countryBronze(Country country,Events event){
        long bronzes =0;
        List<Athlete> athletes=eventRepository.findAthletesByEventCountry(event,country);
        for(Athlete athlete:athletes){
            bronzes +=event_itemRepository.countbronzeMedals(athlete);
        }
        return bronzes;
    }
    @Transactional
    public Country highestGold(Events event){
        List<Country> countries=countryRepository.findCountryByEvent(event);
        Country maxGold=null;
        for(Country country:countries){
            if(maxGold==null){
                maxGold=country;
            }
            else{
                if(countryGold(maxGold)<countryGold(country)){
                    maxGold=country;
                }
            }
        }
        return maxGold;
    }
    @Transactional
    public Country highestSilver(Events event){
        List<Country> countries=countryRepository.findCountryByEvent(event);
        Country maxSilver =null;
        for(Country country:countries){
            if(maxSilver ==null){
                maxSilver =country;
            }
            else{
                if(countrySilver(maxSilver)<countryGold(country)){
                    maxSilver =country;
                }
            }
        }
        return maxSilver;
    }
    @Transactional
    public Country highestBronze(Events event){
        List<Country> countries=countryRepository.findCountryByEvent(event);
        Country maxBronze =null;
        for(Country country:countries){
            if(maxBronze ==null){
                maxBronze =country;
            }
            else{
                if(countryBronze(maxBronze)<countryGold(country)){
                    maxBronze =country;
                }
            }
        }
        return maxBronze;
    }
    @Transactional
    public List<Events> getAllEvents() {
        return eventRepository.findAll();
    }
    @Transactional
    public Events getEventById(long id) {
        return eventRepository.findById(id).orElse(null);
    }
    @Transactional
    public List<Event_Item> getAllEventItems() {

        return event_itemRepository.findAll();
    }
    @Transactional
    public Country getCountryById(String id) {
        return countryRepository.findById(id).orElse(null);
    }
    @Transactional
    public Athlete getAthleteById(Long id) {
        return athleteRepository.findById(id).orElse(null);
    }
    @Transactional
    public Event_Item getEvent_itemById(Long id){
        return event_itemRepository.findById(id).orElse(null);
    }
    @Transactional
    public Country saveCountry(Country country) {

        return countryRepository.save(country);
    }
    @Transactional
    public Athlete saveAthlete(Athlete athlete) {

        return athleteRepository.save(athlete);
    }
    @Transactional
    public void deleteCountry(String id) {

        countryRepository.deleteById(id);
    }
    @Transactional
    public void deleteAthlete(long id) {

        athleteRepository.deleteById(id);
    }
    @Transactional
    public Country updateCountry(String id,Country country) {
        if(countryRepository.existsById(id)) {
            country.setIso_code(id);
            return countryRepository.save(country);
        }
        return null;
    }
    @Transactional
    public Event_Item saveEvent_item(Event_Item eventItem) {
        return event_itemRepository.save(eventItem);
    }
    @Transactional
    public void deleteEvent_item(long id) {
        event_itemRepository.deleteById(id);
    }
}
