package com.example.trial.model;

public class MedalTally {
    Country country;
    long gold;
    long silver;
    long bronze;
    long points;
    public MedalTally(Country country,long gold,long silver,long bronze,long points) {
        this.country = country;
        this.gold = gold;
        this.silver = silver;
        this.bronze = bronze;
        this.points = points;
    }
}
