package com.example.trial.repository;

import com.example.trial.model.Athlete;
import com.example.trial.model.Country;
import com.example.trial.model.Events;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface AthleteRepository extends JpaRepository<Athlete,Long> {
    @Query("select distinct a from Athlete a where a.country=:country")
    List<Athlete> countryAthletes(@Param("country") Country country);

    @Query("SELECT distinct a from Athlete a where a.gender='F'")
    List<Athlete> femaleAthletes();

    @Query("SELECT distinct a from Athlete a where a.gender='M'")
    List<Athlete> maleAthletes();

    @Query("SELECT distinct a from Athlete a join a.event e where a.gender='F' and e=:event")
    List<Athlete> femaleAthletesByEvent(@Param("event") Events event);

    @Query("SELECT distinct a from Athlete a join a.event e where a.gender='M' and e=:event")
    List<Athlete> maleAthletesByEvent(@Param("event") Events event);


    @Query("SELECT a from Athlete a JOIN a.event e where e=:event")
    List<Athlete> findAthletesByEvent(@Param("event") Events event);

    @Query("SELECT a from Athlete a JOIN a.event e where e=:event and a.id=:id")
    Athlete findAthletesByEvent(@Param("event") Events event,@Param("id") long id);


    @Query("SELECT a from Athlete a JOIN a.event e where e=:event and a.country=:country")
    List<Athlete> findAthletesByEventCountry(@Param("event") Events event,@Param("country") Country country);

}

