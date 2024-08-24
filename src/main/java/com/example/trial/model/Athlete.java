package com.example.trial.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Set;

@Getter
@Setter
@Entity
public class Athlete {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String fName;
    private String lName;
    private String gender;
    private int age;
    @ManyToOne
    @JoinColumn(name = "Country_Code")//foreign key for country
    private Country country;

    @ManyToMany(mappedBy = "athletes")
    @JsonIgnore
    private Set<Events> event;

    @ManyToMany
    @JoinTable(
            name = "athlete_event_item",
            joinColumns = @JoinColumn(name = "athlete_id"),
            inverseJoinColumns = @JoinColumn(name = "event_item_id")
    )
    @JsonIgnore
    private Set<Event_Item> eventItems;

    @OneToMany(mappedBy = "gold")
    @JsonIgnore
    private Set<Event_Item> gold;

    @OneToMany(mappedBy = "silver")
    @JsonIgnore
    private Set<Event_Item> silver;

    @OneToMany(mappedBy = "bronze")
    @JsonIgnore
    private Set<Event_Item> bronze;
}
