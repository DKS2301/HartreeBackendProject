package com.example.trial.repository;
import com.example.trial.model.Events;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventRepository extends JpaRepository<Events, Long> {

    Events findByName(String name);
}
