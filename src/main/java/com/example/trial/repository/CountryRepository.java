package com.example.trial.repository;

import com.example.trial.model.Country;
import com.example.trial.model.Events;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CountryRepository extends JpaRepository<Country,String> {
    @Query("SELECT c from Country c JOIN c.event e where e=:event")
    List<Country> findCountryByEvent(@Param("event") Events event);
//
//    @Query("select c from Country c")
//    List<Country> findAll();
}