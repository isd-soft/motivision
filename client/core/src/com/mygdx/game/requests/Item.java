package com.mygdx.game.requests;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import javax.rmi.CORBA.StubDelegate;

public class Item {
    public static final String ITEM_ID = "itemId";
    public static final String ITEM_IMAGE = "itemImagePath";
    public static final String ITEM_NAME = "itemName";
    public static final String ITEM_PRICE = "itemPrice";
    public static final String ITEM_EQUIPPED = "equipped";
    public static final String ITEM_TYPE = "itemType";

    public static final int STORE_ITEM = 0;
    public static final int EQUIPPED_ITEM = 1;
    public static final int UNEQUIPPED_ITEM = 2;

    private static int  powerLevel1;
    private static int  powerLevel2;
    private static int  powerLevel3;

    private int id;
    private String  name;
    private String  imagePath;
    private int price;
    private String type;
    private boolean equipped;
//    private static LinkedHashMap<Integer, Integer> priceList = null;
    private static List<Item> priceList = null;

    public Item(int id, String name, String imagePath, int price, String type, boolean equipped) {
        this.id = id;
        this.name = name;
        this.imagePath = imagePath;
        this.price = price;
        this.type = type;
        this.equipped = equipped;
    }

//    public void print() {
//        System.out.println("item: id(" + id + "), image(" + image + "), price(" + price + "), type(" + type + ")");
//    }

    public String getType() {
        return type;
    }

    public boolean isEquipped() {
        return equipped;
    }

    public int getId() {
        return id;
    }

    private static void loadPriceList() throws IOException, JSONException {
        String url;
        String urlParameters;
        JSONObject jsonObject;
        JSONArray jsonArray;


        url = JsonHandler.domain + "/get_store_items";
        urlParameters = "";
        jsonObject = JsonHandler.readJsonFromUrl(url, urlParameters, "GET");
        if (jsonObject == null)
            return;
        priceList = readStoreItemsFromJson(jsonObject);
    }

    private static ArrayList<Item> readStoreItemsFromJson(JSONObject jsonObject) throws JSONException {
        Item   item;
        String itemName;
        String itemType;
        String itemImage;
        int itemId;
        int itemPrice;
        boolean equipped;
        ArrayList<Item>  itemsList;

        itemsList = new ArrayList<Item>();
        if (jsonObject.has("items") == false)
            return itemsList;
        if (jsonObject.isNull("items"))
            return itemsList;

        if (jsonObject.get("items") == null)
            return itemsList;
        JSONArray arr;
        try {
            arr = jsonObject.getJSONArray("items");
        } catch (JSONException e) {
            return itemsList;
        }
        if (arr == null)
            return itemsList;

        for (int i = 0; i < arr.length(); i++) {
            itemId = arr.getJSONObject(i).getInt(Item.ITEM_ID);
            itemName = arr.getJSONObject(i).getString(Item.ITEM_NAME);
            itemImage = arr.getJSONObject(i).getString(Item.ITEM_IMAGE);
            itemPrice = arr.getJSONObject(i).getInt(Item.ITEM_PRICE);
            itemType = arr.getJSONObject(i).getString(Item.ITEM_TYPE);
            item = new Item(itemId, itemName, itemImage, itemPrice, itemType, false);
            itemsList.add(item);
        }
        return itemsList;
    }

    public static Item getItem(int itemId) throws IOException, JSONException {
        if (itemId == -1)
            return null;
        if (priceList == null)
            loadPriceList();
        return priceList.get(itemId);
    }

    public static int  getItemPrice(int id) {
        for (Item item: priceList) {
            if (item.getId() == id)
                return item.getPrice();
        }
        return -1;
    }

    public void equip() {
        this.equipped = true;
    }

    public void unequip() {
        this.equipped = false;
    }

    public String getName() {
        return name;
    }

    public static int getPowerLevel1() {
        return powerLevel1;
    }

    public static int getPowerLevel2() {
        return powerLevel2;
    }

    public static int getPowerLevel3() {
        return powerLevel3;
    }

    public int getPrice() {
        return price;
    }

    public String getImagePath() {
        return imagePath;
    }
}
