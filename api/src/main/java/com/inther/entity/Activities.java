package com.inther.entity;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Activities {

    private Long ID;

    private String name;

    private int reward;

    @ManyToOne
    @JoinColumn(name = "team_id")
    private Team team_activities;

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
        return team_activities;
    }

    public void setTeam_activities(Team team_activities) {
        this.team_activities = team_activities;
    }
}
