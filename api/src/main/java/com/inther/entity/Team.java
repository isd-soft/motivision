package com.inther.entity;


import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.List;

@Entity
public class Team {

    @Id
    @GenericGenerator(name = "kaugen", strategy = "increment")
    @GeneratedValue(generator = "kaugen")
    private Long ID;

    private String name;

    private String teamLogo;

    private int battleFrequency;

    @OneToMany(mappedBy = "team", cascade = CascadeType.ALL)
    private List<Character> characters;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "lider_id")
    private Character admin;

    @OneToMany
    @JoinTable(name = "team_activities",
            joinColumns = {
                    @JoinColumn(name = "team_id", referencedColumnName = "id")
            },
            inverseJoinColumns = {
                    @JoinColumn(name = "activity_id", referencedColumnName = "id", unique = true)
            }
    )
    private List<Activities> activities;

    public Team() {
    }

    public Long getID() {
        return ID;
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
}
