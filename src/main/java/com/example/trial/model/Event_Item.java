package com.example.trial.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.util.Set;

@Setter
@Getter
@Entity
public class Event_Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private boolean completed;
    private String event_name;
    @ManyToMany(mappedBy = "eventItems")
    @JsonIgnore
    private Set<Athlete> athletes;

    @ManyToOne
    @JoinColumn(name = "event_id")
    private Events event;

    @ManyToOne
    @JoinColumn(name="athlete_event_item ")
    private Athlete gold;
    @ManyToOne
    @JoinColumn(name="athlete_silver_id")
    private Athlete silver;
    @ManyToOne
    @JoinColumn(name="athlete_bronze_id")
    private Athlete bronze;

}
