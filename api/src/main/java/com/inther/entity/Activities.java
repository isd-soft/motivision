package com.inther.entity;

import org.hibernate.annotations.GenericGenerator;
import javax.persistence.*;
import java.util.List;

@Entity
public class Activities {

    @Id
    @GenericGenerator(name = "kaugen", strategy = "increment")
    @GeneratedValue(generator = "kaugen")
    private Long ID;

    private String name;

    private int reward;

    @OneToMany(mappedBy = "activities", cascade = CascadeType.ALL)
    private List<TeamActivities> teamActivities;

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

    public List<TeamActivities> getTeamActivities() {
        return teamActivities;
    }

    public void setTeamActivities(List<TeamActivities> teamActivities) {
        this.teamActivities = teamActivities;
    }
}
