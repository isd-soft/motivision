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

    @OneToOne(mappedBy = "items")
    private CharacterItem characterItem;

    private String imageUrl;

    private int price;

    public Items() {}

    public Long getID() {
        return ID;
    }

    public CharacterItem getCharacterItem() {
        return characterItem;
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
}
