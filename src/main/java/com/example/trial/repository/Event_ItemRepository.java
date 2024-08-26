package com.example.trial.repository;

import com.example.trial.model.Athlete;
import com.example.trial.model.Event_Item;
import com.example.trial.model.Events;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface Event_ItemRepository extends JpaRepository<Event_Item,Long> {
    @Query("SELECT count(*) from Event_Item ei where ei.gold =:athlete")
    long countgoldMedals(@Param("athlete") Athlete athlete);

    @Query("SELECT count(*) from Event_Item ei where ei.silver =:athlete")
    long countsilverMedals(@Param("athlete") Athlete athlete);

    @Query("SELECT count(*) from Event_Item ei where ei.bronze =:athlete")
    long countbronzeMedals(@Param("athlete") Athlete athlete);


    @Query("SELECT e from Event_Item e where e.event=:event and e.id=:id")
    Event_Item findByEvent(@Param("id") long id,@Param("event") Events event);

    @Query("SELECT e from Event_Item e join e.event where e.event=:event ")
    List<Event_Item> findByEvent(@Param("event") Events event);

    @Query("SELECT a from Athlete a join a.eventItems ei where ei=:item and ei.event=:event")
    List<Athlete> findAthletesByEventItem(@Param("event") Events event, @Param("item") Event_Item item);
}
