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

    @ManyToOne
    @JoinTable(name = "team_activities",
            joinColumns = {
                    @JoinColumn(name = "activity_id", referencedColumnName = "id")
            },
            inverseJoinColumns = {
                    @JoinColumn(name = "team_id", referencedColumnName = "id", unique = true)
            }
    )
    private Team team;

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
    
}
