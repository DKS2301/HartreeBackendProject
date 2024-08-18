package com.example.trial.model;

import com.github.javafaker.Faker;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Objects;
import java.util.Set;

@Setter
@Getter
@Entity
public class Country {
    @Id
    private String iso_code;
    @Column(nullable = false)
    private String name;

    @OneToMany(mappedBy = "country", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Athlete> athletes;

    @ManyToMany(mappedBy = "countries")
    private Set<Events> event;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Country country = (Country) o;
        return Objects.equals(iso_code, country.iso_code);
    }

    @Override
    public int hashCode() {
        return Objects.hash(iso_code);
    }
}
