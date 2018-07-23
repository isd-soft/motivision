package com.inther.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
public class LastBattle {

    @Id
    @GenericGenerator(name = "kaugen", strategy = "increment")
    @GeneratedValue(generator = "kaugen")
    private Long id;

    @OneToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "team_id")
    private Team team;

    private Integer enemyPower;

    private Integer teamPower;

    public LastBattle() {
    }

    public Long getId() {
        return id;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public Integer getEnemyPower() {
        return enemyPower;
    }

    public void setEnemyPower(Integer enemyPower) {
        this.enemyPower = enemyPower;
    }

    public Integer getTeamPower() {
        return teamPower;
    }

    public void setTeamPower(Integer teamPower) {
        this.teamPower = teamPower;
    }
}
