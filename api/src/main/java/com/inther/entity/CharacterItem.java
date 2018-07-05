package com.inther.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.List;

@Entity
public class CharacterItem {

    @Id
    @GenericGenerator(name = "kaugen", strategy = "increment")
    @GeneratedValue(generator = "kaugen")
    private Long ID;

    @OneToOne
    @JoinColumn(name = "character_id")
    private Character character;

    @OneToMany
    @JoinColumn(name = "item_id")
    private List<Items> items;

    public CharacterItem() {
    }

    public Character getCharacter() {
        return character;
    }

    public List<Items> getItems() {
        return items;
    }
}
