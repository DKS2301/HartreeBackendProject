package com.example.trial.repository;

import com.example.trial.model.Athlete;
import com.example.trial.model.Country;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Set;

public interface AthleteRepository extends JpaRepository<Athlete,Long> {
    @Query("select distinct a from Athlete a where a.country=:country")
    Set<Athlete> countryAthletes(@Param("country") Country country);

}
