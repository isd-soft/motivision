package com.inther.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
public class TeamActivities {

    @Id
    @GenericGenerator(name = "kaugen", strategy = "increment")
    @GeneratedValue(generator = "kaugen")
    private Long ID;

    @ManyToOne
    @JoinColumn(name = "team_id")
    private Team team;

    @ManyToOne
    @JoinColumn(name = "activity_id")
    private Activities activities;

    public TeamActivities() {
    }

    public TeamActivities(Team team, Activities activities) {
        this.team = team;
        this.activities = activities;
    }

    public Long getID() {
        return ID;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public Activities getActivities() {
        return activities;
    }

    public void setActivities(Activities activities) {
        this.activities = activities;
    }
}
