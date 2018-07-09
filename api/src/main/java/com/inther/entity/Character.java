package com.inther.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.List;

@Entity
public class Character {

    @Id
    @GenericGenerator(name = "kaugen", strategy = "increment")
    @GeneratedValue(generator = "kaugen")
    private Long ID;

    private Long headType;

    private Long bodyType;

    private char gender;

    private int points;

    private String name;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "team_id")
    @OneToOne(mappedBy = "admin")
    private Team team;

    @ManyToOne
    @JoinColumn(name = "player_id")
    private Player player;

    @OneToMany(mappedBy = "character", cascade = CascadeType.ALL)
    private List<CharacterItem> characterItems;

    public Character() {
        points = 0;
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

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }
}
