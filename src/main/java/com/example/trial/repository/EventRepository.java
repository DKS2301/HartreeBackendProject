package com.example.trial.repository;

import com.example.trial.model.Athlete;
import com.example.trial.model.Country;
import com.example.trial.model.Events;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Set;

public interface EventRepository extends JpaRepository<Events, Long> {

    @Query("SELECT a from Athlete a JOIN a.event e where e=:event")
    List<Athlete> findAthletesByEvent(@Param("event") Events event);

    @Query("SELECT a from Athlete a JOIN a.event e where e=:event and a.country=:country")
    List<Athlete> findAthletesByEventCountry(@Param("event") Events event,@Param("country") Country country);

}
