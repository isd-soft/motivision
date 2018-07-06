package com.mygdx.game.requests;

public class Item {
    public static final String  ITEM_ID = "itemId";
    public static final String  ITEM_IMAGE = "itemImageUrl";
    public static final String  ITEM_PRICE = "itemPrice";
    public static final String  ITEM_TYPE = "itemType";

    private int     id;
    private int     image;
    private int     price;
    private String  type;

    public Item(int id, int image, int price, String type) {
        this.id = id;
        this.image = image;
        this.price = price;
        this.type = type;
    }

    public void print() {
        System.out.println("item: id(" + id + "), image(" + image + "), price(" + price + "), type(" + type + ")");
    }
}
