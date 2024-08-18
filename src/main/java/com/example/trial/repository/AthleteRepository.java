package com.example.trial.repository;

import com.example.trial.model.Athlete;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AthleteRepository extends JpaRepository<Athlete,Long> {
}
