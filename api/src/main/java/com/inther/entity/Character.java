package com.inther.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Character {

    @Id
    @GenericGenerator(name = "kaugen", strategy = "increment")
    @GeneratedValue(generator = "kaugen")
    private Long ID;

    @ManyToOne
    private Long playerId;

    private Long headType;

    private Long bodyType;

    private char gender;

    private int power;

    private String name;

    private int team_id;

    public Character() {
    }

    public Character(int team_id) {
        this.team_id = team_id;
    }

    public Long getID() {
        return ID;
    }

    public Long getPlayerId() {
        return playerId;
    }

    public int getTeam_id() {
        return team_id;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
