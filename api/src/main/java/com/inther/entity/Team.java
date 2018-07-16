package com.inther.entity;


import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.List;

@Entity
public class Team {

    @Id
    @GenericGenerator(name = "kaugen", strategy = "increment")
    @GeneratedValue(generator = "kaugen")
    private Long id;

    private String name;

    private String teamLogo;

    private int battleFrequency;

    @OneToMany(mappedBy = "team", cascade = CascadeType.ALL)
    private List<Character> characters;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "lider_id")
    private Character admin;

    @OneToMany(mappedBy = "team", cascade = CascadeType.ALL)
    private List<Activities> teamActivities;

    @OneToOne(mappedBy = "team")
    private LastBattle teamLastBattles;

    private Integer teamWins;

    private Integer teamLoss;

    private Boolean locked;

    public Boolean getLock() {
        return locked;
    }

    public void setLock(Boolean lock) {
        this.locked = lock;
    }

    public Team() {
        teamWins = 0;
        teamLoss = 0;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTeamLogo() {
        return teamLogo;
    }

    public void setTeamLogo(String teamLogo) {
        this.teamLogo = teamLogo;
    }

    public int getBattleFrequency() {
        return battleFrequency;
    }

    public void setBattleFrequency(int battleFrequency) {
        this.battleFrequency = battleFrequency;
    }

    public List<Character> getCharacters() {
        return characters;
    }

    public void setCharacters(List<Character> characters) {
        this.characters = characters;
    }

    public Character getAdmin() {
        return admin;
    }

    public void setAdmin(Character admin) {
        this.admin = admin;
    }

    public List<Activities> getTeamActivities() {
        return teamActivities;
    }

    public void setTeamActivities(List<Activities> teamActivities) {
        this.teamActivities = teamActivities;
    }

    public LastBattle getTeamLastBattles() {
        return teamLastBattles;
    }

    public void setTeamLastBattles(LastBattle teamLastBattles) {
        this.teamLastBattles = teamLastBattles;
    }

    public Integer getTeamWins() {
        return teamWins;
    }

    public void setTeamWins(Integer teamWins) {
        this.teamWins = teamWins;
    }

    public Integer getTeamLoss() {
        return teamLoss;
    }

    public void setTeamLoss(Integer teamLoss) {
        this.teamLoss = teamLoss;
    }
}
