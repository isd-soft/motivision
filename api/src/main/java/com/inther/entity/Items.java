package com.inther.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.List;

@Entity
public class Items {

    @Id
    @GenericGenerator(name = "kaugen", strategy = "increment")
    @GeneratedValue(generator = "kaugen")
    private Long ID;

    private String type;

    private String imageUrl;

    private int price;

    @ManyToOne
    @JoinTable(name = "character_item",
            joinColumns = {
                    @JoinColumn(name = "item_id", referencedColumnName = "id")
            },
            inverseJoinColumns = {
                    @JoinColumn(name = "character_id", referencedColumnName = "id", unique = true)
            }
    )
    private Character character;

    public Items() {}

    public Long getID() {
        return ID;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public Character getCharacter() {
        return character;
    }

    public void setCharacter(Character character) {
        this.character = character;
    }
}
