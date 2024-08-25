package com.example.trial.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MedalTally implements Comparable<MedalTally> {
    private Country country;
    private long gold;
    private long silver;
    private long bronze;
    private long points;
    public MedalTally(Country country,long gold,long silver,long bronze,long points) {
        this.country = country;
        this.gold = gold;
        this.silver = silver;
        this.bronze = bronze;
        this.points = points;
    }
    @Override
    public int compareTo(MedalTally b) {
        return Integer.compare((int) this.points, (int) b.points);
    }
    @Override
    public String toString() {
        return  country.getName()+"gold:"+gold+"silver"+silver+"bronze"+bronze+","+points;
    }
}
