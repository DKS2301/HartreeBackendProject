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

    // Random Data Generation
    @Transactional
    public void generateData(Events event) {
        Faker faker = new Faker();
        // Generate countries
        Set<Country> countries = generateCountries(faker);
        event.setCountries(countries);
        eventRepository.save(event);

        // Generate event items
        Set<Event_Item> items = generateEventItems(faker, event);
        event_itemRepository.saveAll(items);

        // Generate athletes
        Set<Athlete> athletes = generateAthletes(faker, items, countries);
        athleteRepository.saveAll(athletes);

        event.setAthletes(athletes);
        eventRepository.save(event);
    }
    //Generate random countries
    private Set<Country> generateCountries(Faker faker) {
        Set<Country> countries = new HashSet<>();
        for (int j = 0; j < faker.number().numberBetween(10,100); j++) {
            Country country = new Country();
            country.setName(faker.address().country());
            country.setIso_code(faker.address().countryCode());
            countries.add(countryRepository.findById(country.getIso_code()).orElseGet(() -> countryRepository.save(country)));
        }
        return countries;
    }

    //Generate Event Items

    private Set<Event_Item> generateEventItems(Faker faker, Events event) {
        String[] eventItems = {"100m Sprint", "200m Sprint", "400m Sprint", "800m Run", "1500m Run", "5000m Run", "10000m Run", "Marathon", "110m Hurdles", "400m Hurdles", "4x100m Relay", "4x400m Relay", "High Jump", "Pole Vault", "Long Jump", "Triple Jump", "Shot Put", "Discus Throw", "Javelin Throw", "Hammer Throw", "Decathlon"};
        Set<String> selectedEvents = new HashSet<>();
        int numberOfItems = faker.number().numberBetween(1, eventItems.length);
        while (selectedEvents.size() < numberOfItems) {
            selectedEvents.add(eventItems[faker.number().numberBetween(0, eventItems.length)]);
        }
        Set<Event_Item> items = new HashSet<>();
        for (String eventName : selectedEvents) {
            Event_Item item = new Event_Item();
            item.setEvent_name(eventName);
            item.setEvent(event);
            item.setCompleted(false);
            items.add(item);
        }
        return items;
    }

    //Generate athletes
    private Set<Athlete> generateAthletes(Faker faker, Set<Event_Item> items, Set<Country> countries) {
        Set<Athlete> athletes = new HashSet<>();
        for(Country country : countries) {
            for (int j = 0; j < faker.number().numberBetween(5, 15); j++) {
                Athlete athlete = new Athlete();
                athlete.setFName(faker.name().firstName());
                athlete.setLName(faker.name().lastName());
                athlete.setAge(faker.number().numberBetween(18, 40));
                athlete.setGender(faker.bool().bool() ? "M" : "F");

                Set<Event_Item> registeredEvents = new HashSet<>();
                List<Event_Item> itemsList = new ArrayList<>(items);
                for (int k = 0; k < faker.number().numberBetween(1, 5); k++) {
                    registeredEvents.add(itemsList.get(faker.number().numberBetween(0, itemsList.size() - 1)));
                }
                athlete.setEventItems(registeredEvents);
                athlete.setCountry(country);
                athletes.add(athlete);
            }
        }
        return athletes;
    }

    /** Athlete Medal Counting **/

    //specific Athlete
    @Transactional
    public long totalMedalCount(Athlete athlete) {
        return event_itemRepository.countgoldMedals(athlete) +
                event_itemRepository.countsilverMedals(athlete) +
                event_itemRepository.countbronzeMedals(athlete);
    }

    //Total points of an Athlete
    @Transactional
    public long totalPoints(Athlete athlete) {
        return event_itemRepository.countgoldMedals(athlete)*3 +
                event_itemRepository.countsilverMedals(athlete)*2 +
                event_itemRepository.countbronzeMedals(athlete);// Gold=3, Silver=2, Bronze=1
    }

    //Athlete with most points
    @Transactional
    public Athlete maxPointAthlete() {
        return athleteRepository.findAll().stream()
                .max(Comparator.comparing(this::totalPoints)).orElse(null);
    }

    @Transactional
    public Athlete maxPointAthlete(Events event) {
        return athleteRepository.findAthletesByEvent(event).stream()
                .max(Comparator.comparing(this::totalPoints)).orElse(null);
    }

    //Athlete with most points based on gender
    @Transactional
    public Athlete maxPointAthlete(int gender,Events event) {
        List<Athlete> athletes = gender == 1 ? femaleAthletesByEvent(event) : maleAthletesByEvent(event);
        return athletes.stream().max(Comparator.comparing(this::totalPoints)).orElse(null);
    }

    @Transactional
    public Athlete maxPointAthlete(int gender) {
        List<Athlete> athletes = gender == 1 ? femaleAthletes() : maleAthletes();
        return athletes.stream().max(Comparator.comparing(this::totalPoints)).orElse(null);
    }

    @Transactional
    public List<Athlete> femaleAthletes(){
        return athleteRepository.femaleAthletes();
    }

    @Transactional
    public List<Athlete> femaleAthletesByEvent(Events event){
        return athleteRepository.femaleAthletesByEvent(event);
    }

    @Transactional
    public List<Athlete> maleAthletes(){
        return athleteRepository.maleAthletes();
    }

    @Transactional
    public List<Athlete> maleAthletesByEvent(Events event){
        return athleteRepository.maleAthletesByEvent(event);
    }

    // Ranking
    @Transactional
    public Athlete highestMedalAthlete(Events event) {
        return findAthletesByEvent(event).stream()
                .max(Comparator.comparing(this::totalMedalCount)).orElse(null);
    }

    @Transactional
    public Athlete highestMedalAthlete() {
        return athleteRepository.findAll().stream()
                .max(Comparator.comparing(this::totalMedalCount)).orElse(null);
    }

    @Transactional
    public List<Athlete> findAthletesByEvent(Events event){
        return athleteRepository.findAthletesByEvent(event);
    }

    /**Country Medal Counting**/

    //Points of a Country
    @Transactional
    public long totalPointsByCountry(Country country) {
        return 3 * countryGold(country) + 2 * countrySilver(country) + countryBronze(country);
    }

    //Points of a Country in a specific event
    @Transactional
    public long totalPointsByCountry(Country country, Events event) {
        return 3 * countryGold(country, event) + 2 * countrySilver(country, event) + countryBronze(country, event);
    }

    //total gold medals
    @Transactional
    public long countryGold(Country country) {
        return athleteRepository.countryAthletes(country).stream()
                .mapToLong(event_itemRepository::countgoldMedals).sum();
    }

    //total silver medals
    @Transactional
    public long countrySilver(Country country) {
        return athleteRepository.countryAthletes(country).stream()
                .mapToLong(event_itemRepository::countsilverMedals).sum();
    }

    //total bronze medals
    @Transactional
    public long countryBronze(Country country) {
        return athleteRepository.countryAthletes(country).stream()
                .mapToLong(event_itemRepository::countbronzeMedals).sum();
    }

    //total gold medals in a specific event
    @Transactional
    public long countryGold(Country country, Events event) {
        return athleteRepository.findAthletesByEventCountry(event, country).stream()
                .mapToLong(event_itemRepository::countgoldMedals).sum();
    }

    //find countries in a particular event
    public List<Country> findCountriesByEvent(Events event){
        return countryRepository.findCountryByEvent(event);
    }

    //total silver medals in a specific event
    @Transactional
    public long countrySilver(Country country, Events event) {
        return athleteRepository.findAthletesByEventCountry(event, country).stream()
                .mapToLong(event_itemRepository::countsilverMedals).sum();
    }

    //total bronze medals in a specific event
    @Transactional
    public long countryBronze(Country country, Events event) {
        return athleteRepository.findAthletesByEventCountry(event, country).stream()
                .mapToLong(event_itemRepository::countbronzeMedals).sum();
    }


    //Ranking

    //Country with most points
    @Transactional
    public Country highestPointsByCountry() {
        return countryRepository.findAll().stream()
                .max(Comparator.comparing(this::totalPointsByCountry)).orElse(null);
    }

    @Transactional
    public Country highestPointsByCountry(Events event) {
        List<Country> countries=findCountriesByEvent(event);
        Country highest=null;
        for(Country c:countries){
            if(highest==null||totalPointsByCountry(c,event)>totalPointsByCountry(highest,event)){
                highest=c;
            }
        }
        return highest;
    }

    //Country with least points
    @Transactional
    public Country lowestPointsByCountry() {
        return countryRepository.findAll().stream()
                .min(Comparator.comparing(this::totalPointsByCountry)).orElse(null);
    }

    @Transactional
    public Country lowestPointsByCountry(Events event) {
        List<Country> countries=findCountriesByEvent(event);
        Country lowest =null;
        for(Country c:countries){
            if(lowest ==null||totalPointsByCountry(c,event)<totalPointsByCountry(lowest,event)){
                lowest =c;
            }
        }
        return lowest;
    }

    //Most gold
    @Transactional
    public Country highestGold() {
        return countryRepository.findAll().stream()
                .max(Comparator.comparing(this::countryGold)).orElse(null);
    }

    @Transactional
    public Country highestGold(Events event) {
        List<Country> countries=findCountriesByEvent(event);
        Country highestgold =null;
        for(Country c:countries){
            if(highestgold ==null||totalPointsByCountry(c,event)>totalPointsByCountry(highestgold,event)){
                highestgold =c;
            }
        }
        return highestgold;
    }

    //Least gold
    @Transactional
    public Country lowestGold() {
        return countryRepository.findAll().stream()
                .min(Comparator.comparing(this::countryGold)).orElse(null);
    }

    @Transactional
    public Country lowestGold(Events event) {
        List<Country> countries=findCountriesByEvent(event);
        Country lowestGold =null;
        for(Country c:countries){
            if(lowestGold ==null||totalPointsByCountry(c,event)<totalPointsByCountry(lowestGold,event)){
                lowestGold =c;
            }
        }
        return lowestGold;
    }

    //Most silver
    @Transactional
    public Country highestSilver() {
        return countryRepository.findAll().stream()
                .max(Comparator.comparing(this::countrySilver)).orElse(null);
    }

    @Transactional
    public Country highestSilver(Events event) {
        List<Country> countries=findCountriesByEvent(event);
        Country highestSilver =null;
        for(Country c:countries){
            if(highestSilver ==null||totalPointsByCountry(c,event)<totalPointsByCountry(highestSilver,event)){
                highestSilver =c;
            }
        }
        return highestSilver;
    }

    //Least silver
    @Transactional
    public Country lowestSilver() {
        return countryRepository.findAll().stream()
                .min(Comparator.comparing(this::countrySilver)).orElse(null);
    }

    @Transactional
    public Country lowestSilver(Events event) {
        List<Country> countries=findCountriesByEvent(event);
        Country lowestSilver =null;
        for(Country c:countries){
            if(lowestSilver ==null||totalPointsByCountry(c,event)<totalPointsByCountry(lowestSilver,event)){
                lowestSilver =c;
            }
        }
        return lowestSilver;
    }

    //Most bronze
    @Transactional
    public Country highestBronze() {
        return countryRepository.findAll().stream()
                .max(Comparator.comparing(this::countryBronze)).orElse(null);
    }

    @Transactional
    public Country highestBronze(Events event) {
        List<Country> countries=findCountriesByEvent(event);
        Country bronze =null;
        for(Country c:countries){
            if(bronze ==null||totalPointsByCountry(c,event)<totalPointsByCountry(bronze,event)){
                bronze =c;
            }
        }
        return bronze;
    }

    //Least bronze
    @Transactional
    public Country lowestBronze() {
        return countryRepository.findAll().stream()
                .min(Comparator.comparing(this::countryBronze)).orElse(null);
    }

    @Transactional
    public Country lowestBronze(Events event) {
        List<Country> countries=findCountriesByEvent(event);
        Country lowestBronze =null;
        for(Country c:countries){
            if(lowestBronze ==null||totalPointsByCountry(c,event)<totalPointsByCountry(lowestBronze,event)){
                lowestBronze =c;
            }
        }
        return lowestBronze;
    }

    //Medal tally of first n nations across all events
    @Transactional
    public List<MedalTally> firstNTally(int n) {
        return countryRepository.findAll().stream()
                .map(c -> new MedalTally(c, countryGold(c), countrySilver(c), countryBronze(c), totalPointsByCountry(c)))
                .sorted(Comparator.comparing(MedalTally::getPoints).reversed())
                .limit(n)
                .toList();
    }

    //Medal Tally of first n nations across a particular event
    @Transactional
    public List<MedalTally> firstNTally(int n, Events event) {
        return countryRepository.findAll().stream()
                .map(c -> new MedalTally(c, countryGold(c, event), countrySilver(c, event), countryBronze(c, event), totalPointsByCountry(c, event)))
                .sorted(Comparator.comparing(MedalTally::getPoints).reversed())
                .limit(n)
                .toList();
    }


    // CRUD for Country

    @Transactional
    public List<Country> getAllCountries() {
        return countryRepository.findAll();
    }

    @Transactional
    public Country getCountryById(String isoCode) {
        return countryRepository.findById(isoCode).orElse(null);
    }

    @Transactional
    public Country getCountryByEventId(String id,Events event){
        return countryRepository.findCountriesByIdEvent(id,event);
    }

    @Transactional
    public Country saveCountry(Country country) {
        return countryRepository.save(country);
    }

    @Transactional
    public void deleteCountry(String isoCode) {
        countryRepository.deleteById(isoCode);
    }


    // CRUD for Athlete


    @Transactional
    public List<Athlete> getAllAthletes() {
        return athleteRepository.findAll();
    }

    @Transactional
    public Athlete getAthleteById(Long id) {
        return athleteRepository.findById(id).orElse(null);
    }

    @Transactional
    public Athlete saveAthlete(Athlete athlete) {
        return athleteRepository.save(athlete);
    }

    @Transactional
    public void deleteAthlete(Long id) {
        athleteRepository.deleteById(id);
    }

    @Transactional
    public Athlete getAthleteByEventId(Events event,long id){
        return athleteRepository.findAthletesByEvent(event,id);
    }

    // CRUD Operations for Events


    @Transactional
    public List<Events> getAllEvents() {
        return eventRepository.findAll();
    }

    @Transactional
    public Events getEventByName(String name) {
        return eventRepository.findByName(name);
    }

    @Transactional
    public Events getEventById(Long id) {
        return eventRepository.findById(id).orElse(null);
    }




    @Transactional
    public Events saveEvent(Events updatedEvent) {
        return eventRepository.save(updatedEvent);
    }

    @Transactional
    public void deleteEvent(Long id) {
        eventRepository.deleteById(id);
    }


    // CRUD Operations for Event_Item


    @Transactional
    public List<Event_Item> getAllEventItems() {
        return event_itemRepository.findAll();
    }

    @Transactional
    public List<Event_Item> getAllEventItemsByEvent(Events event) {
        return event_itemRepository.findByEvent(event);
    }

    @Transactional
    public Event_Item getEventItemById(Long id,Events event) {

        return event_itemRepository.findByEvent(id,event);
    }

    @Transactional
    public List<Athlete> getAthleteesByEventItem(Events event,Event_Item item){
        return event_itemRepository.findAthletesByEventItem(event,item);
    }
    @Transactional
    public Event_Item getEventItemById(Long id) {
        return event_itemRepository.findById(id).orElse(null);
    }

    @Transactional
    public Event_Item saveEventItem(Event_Item ei) {
        return event_itemRepository.save(ei);
    }

    @Transactional
    public void deleteEventItem(Long id) {
        event_itemRepository.deleteById(id);
    }
}
