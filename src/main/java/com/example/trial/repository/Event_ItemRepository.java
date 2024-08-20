package com.example.trial.repository;

import com.example.trial.model.Athlete;
import com.example.trial.model.Event_Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface Event_ItemRepository extends JpaRepository<Event_Item,Long> {
    @Query("SELECT count(*) from Event_Item ei where ei.gold =:athlete")
    long countgoldMedals(@Param("athlete") Athlete athlete);

    @Query("SELECT count(*) from Event_Item ei where ei.silver =:athlete")
    long countsilverMedals(@Param("athlete") Athlete athlete);

    @Query("SELECT count(*) from Event_Item ei where ei.bronze =:athlete")
    long countbronzeMedals(@Param("athlete") Athlete athlete);

}
