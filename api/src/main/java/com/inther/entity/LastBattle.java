package com.inther.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
public class LastBattle {

    @Id
    @GenericGenerator(name = "kaugen", strategy = "increment")
    @GeneratedValue(generator = "kaugen")
    private Long ID;

    @OneToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "team1_id")
    private Team team1;

    @OneToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "team2_id")
    private Team team2;

    private Long winnerTeam;

    public LastBattle() {
        setWinnerTeam(null);
    }

    public Team getTeam1() {
        return team1;
    }

    public void setTeam1(Team team1) {
        this.team1 = team1;
    }

    public Team getTeam2() {
        return team2;
    }

    public void setTeam2(Team team2) {
        this.team2 = team2;
    }

    public Long getWinnerTeam() {
        return winnerTeam;
    }

    public void setWinnerTeam(Long winnerTeam) {
        this.winnerTeam = winnerTeam;
    }
}
