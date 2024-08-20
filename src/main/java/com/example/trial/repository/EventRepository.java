package com.example.trial.repository;

import com.example.trial.model.Athlete;
import com.example.trial.model.Country;
import com.example.trial.model.Events;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface EventRepository extends JpaRepository<Events, Long> {
    @Query("SELECT c from Country c where c.event=:event")
    List<Country> findCountryByEvent(@Param("event") Events event);

    @Query("SELECT a from Athlete a where a.event=:event")
    List<Athlete> findAthletesByEvent(@Param("event") Events event);
}
