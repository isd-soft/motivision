package com.mygdx.game.requests;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;

import javax.rmi.CORBA.StubDelegate;

public class Item {
    public static final String  ITEM_ID = "itemId";
    public static final String  ITEM_IMAGE = "itemImageUrl";
    public static final String  ITEM_PRICE = "itemPrice";
    public static final String  ITEM_EQUIPPED = "equipped";
    public static final String  ITEM_TYPE = "itemType";

    public static final int     STORE_ITEM = 0;
    public static final int     EQUIPPED_ITEM = 1;
    public static final int     UNEQUIPPED_ITEM = 2;

    private int     id;
    private int     image;
    private int     price;
    private String  type;
    private boolean equipped;
    private static LinkedHashMap<Integer, Integer>   priceList = null;
    private static List<Item> itemList = null;

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

    private static void loadPriceList() throws IOException, JSONException {
        String      url;
        String      urlParameters;
        JSONObject  jsonObject;
        JSONArray   jsonArray;

        url = JsonHandler.domain + "/get_item_price";
        urlParameters = "";
        jsonObject = JsonHandler.readJsonFromUrl(url, urlParameters, "GET");
        if (jsonObject == null)
            return;
        jsonArray = jsonObject.getJSONArray("items");
        priceList = new LinkedHashMap<Integer, Integer>(jsonArray.length());
        for (int i = 0; i < jsonArray.length(); i++) {
            priceList.put(jsonArray.getJSONObject(i).getInt(ITEM_ID),
                    jsonArray.getJSONObject(i).getInt(ITEM_PRICE));
        }
    }

    public static int   getItemPrice(int itemId) throws IOException, JSONException {
        if (itemId == -1)
            return -1;
        if (priceList == null)
            loadPriceList();
        return priceList.get(itemId);
    }

    public void equip() {
        this.equipped = true;
    }

    public void unequip() {
        this.equipped = false;
    }
}
