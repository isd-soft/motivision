package com.inther.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
public class Character {

    @Id
    @GenericGenerator(name = "kaugen", strategy = "increment")
    @GeneratedValue(generator = "kaugen")
    private Long ID;

    private Long headType;

    private Long bodyType;

    private char gender;

    private int power;

    private String name;

    @ManyToOne
    @JoinColumn(name = "team_id")
    @OneToOne(mappedBy = "admin")
    private Team team;

    @ManyToOne
    @JoinColumn(name = "player_id")
    private Player player;

    public Character() {
    }

    public Long getID() {
        return ID;
    }

    public Long getHeadType() {
        return headType;
    }

    public void setHeadType(Long headType) {
        this.headType = headType;
    }

    public Long getBodyType() {
        return bodyType;
    }

    public void setBodyType(Long bodyType) {
        this.bodyType = bodyType;
    }

    public char getGender() {
        return gender;
    }

    public void setGender(char gender) {
        this.gender = gender;
    }

    public int getPower() {
        return power;
    }

    public void setPower(int power) {
        this.power = power;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }
}
