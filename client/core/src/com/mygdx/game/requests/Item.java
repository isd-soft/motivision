package com.mygdx.game.requests;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import javax.rmi.CORBA.StubDelegate;

public class Item {
    public static final String  ITEM_ID = "itemId";
    public static final String  ITEM_IMAGE = "itemImageUrl";
    public static final String  ITEM_PRICE = "itemPrice";
    public static final String  ITEM_EQUIPPED = "equipped";
    public static final String  ITEM_TYPE = "itemType";

    private int     id;
    private int     image;
    private int     price;
    private String  type;
    private boolean equipped;

    public Item(int id, int image, int price, String type, boolean equipped) {
        this.id = id;
        this.image = image;
        this.price = price;
        this.type = type;
        this.equipped = equipped;
    }

    public void print() {
        System.out.println("item: id(" + id + "), image(" + image + "), price(" + price + "), type(" + type + ")");
    }

    public String getType() {
        return type;
    }

    public boolean isEquipped() {
        return equipped;
    }

    public int  getId() {
        return id;
    }

    public static int   getItemPrice(int itemId) throws IOException, JSONException {
        String      url;
        String      urlParameters;
        JSONObject  jsonObject;

        if (itemId == -1)
            return -1;

        url = JsonHandler.domain + "/get_item";
        urlParameters = ITEM_ID + "=" + itemId;
        jsonObject = JsonHandler.readJsonFromUrl(url, urlParameters, "GET");
        if (jsonObject == null)
            return -1;
        if (jsonObject.has("itemPrice"))
            return jsonObject.getInt(ITEM_PRICE);
        return -1;
    }
}
