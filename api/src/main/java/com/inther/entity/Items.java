package com.inther.entity;

import javax.persistence.Entity;

@Entity
public class Items {

    private Long ID;

    private Long type_id;

    private String image_url;

    private int price;

    public Items() {}

    public Long getID() {
        return ID;
    }

    public Long getType_id() {
        return type_id;
    }

    public void setType_id(Long type_id) {
        this.type_id = type_id;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
