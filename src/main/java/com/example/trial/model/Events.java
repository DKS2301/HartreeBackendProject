package com.example.trial.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Set;

@Setter
@Getter
@Entity
public class Events {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;

    @OneToMany(mappedBy = "event", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Event_Item> eventItem;

    @ManyToMany
    @JoinTable(
            name = "events_athlete", // Name of the join table
            joinColumns = @JoinColumn(name = "events_id"), // Foreign key column for events
            inverseJoinColumns = @JoinColumn(name = "athlete_id") // Foreign key column for athlete
    )
    private Set<Athlete> athletes;

    @ManyToMany
    @JoinTable(
            name = "events_country", // Name of the join table
            joinColumns = @JoinColumn(name = "events_id"), // Foreign key column for events
            inverseJoinColumns = @JoinColumn(name = "country_id") // Foreign key column for country
    )
    private Set<Country> countries;
}
