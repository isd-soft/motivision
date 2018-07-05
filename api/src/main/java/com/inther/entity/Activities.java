package com.inther.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
public class Activities {

    @Id
    @GenericGenerator(name = "kaugen", strategy = "increment")
    @GeneratedValue(generator = "kaugen")
    private Long ID;

    private String name;

    private int reward;

    @ManyToOne
    @JoinColumn(name = "team_id")
    private Team teamActivities;

    public Activities() {}

    public Long getID() {
        return ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getReward() {
        return reward;
    }

    public void setReward(int reward) {
        this.reward = reward;
    }

    public Team getTeam_activities() {
        return teamActivities;
    }

    public void setTeam_activities(Team teamActivities) {
        this.teamActivities = teamActivities;
    }
}
