package com.mygdx.game.requests;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;

import sun.awt.image.ImageWatched;

public class Item implements Comparable<Item> {
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
    private static int maxPower;

    private int id;
    private String  name;
    private String  imagePath;
    private int price;
    private String type;
    private boolean equipped;
//    private static LinkedHashMap<Integer, Integer> storeItems = null;
    private static ArrayList<Item> storeItems = null;

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

    public static int getMaxPower() {
        return maxPower;
    }

    private static void  calculateMaxPower() {
        LinkedHashMap<String, Integer>  maxPowerPerType;
        Set<String> types;
        int power;

        if (storeItems == null) {
            return;
        };
        maxPowerPerType = new LinkedHashMap<String, Integer>();
        for (Item item: storeItems) {
            if (maxPowerPerType.containsKey(item.getType())) {
                power = maxPowerPerType.get(item.getType());
                if (power < item.getPrice())
                    maxPowerPerType.put(item.getType(), item.getPrice());
            }
            else
                maxPowerPerType.put(item.getType(), item.getPrice());
        }
        types = maxPowerPerType.keySet();
        power = 0;
        for (String type: types) {
            power += maxPowerPerType.get(type);
        }
        maxPower = power;
    }

    private static void loadPriceList() {
        String url;
        String urlParameters;
        JSONObject jsonObject;
        JSONArray jsonArray;

        url = JsonHandler.domain + "/get_store_items";
        urlParameters = "";
        try {
            jsonObject = JsonHandler.readJsonFromUrl(url, urlParameters, "GET");
            if (jsonObject == null)
                return;
            storeItems = readStoreItemsFromJson(jsonObject);
            calculateMaxPower();
            Collections.sort(storeItems);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
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
        if (storeItems == null)
            loadPriceList();
        return storeItems.get(itemId);
    }

    public static ArrayList<Item> getStoreItems() {
        if (storeItems == null) {
            loadPriceList();
        }
        return storeItems;
    }

    public static int  getItemPrice(int id) {
        if (storeItems == null) {
            loadPriceList();
        }
        for (Item item: storeItems) {
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

    @Override
    public int compareTo(Item item) {
        if (type.equals(item.getType()))
            return Integer.compare(price, item.getPrice());
        if (type.equals("weapon"))
            return -1;
        if (item.getType().equals("weapon"))
            return 1;

        if (type.equals("armor"))
            return -1;
        if (item.getType().equals("armor"))
            return 1;

        if (type.equals("shield"))
            return -1;
        if (item.getType().equals("shield"))
            return 1;

        if (type.equals("leggins"))
            return -1;
        if (item.getType().equals("leggins"))
            return 1;
        return 0;
    }

    public static Item getMostPowerfulItemByType(String type) {
        Item mostPowerfulItem = null;

        if (storeItems == null)
            loadPriceList();
        for (Item item: storeItems) {
            if (item.getType().equals(type)) {
                if (mostPowerfulItem == null)
                    mostPowerfulItem = item;
                else if (item.getPrice() > mostPowerfulItem.getPrice())
                    mostPowerfulItem = item;
            }
        }
        return mostPowerfulItem;
    }

    public static Item getNextLessPowerfulItemByType(Item currentItem) {
        Item lessPowerfulItem = null;
        String currentItemType;
        int currentItemPrice;

        if (currentItem == null)
            return null;
        if (storeItems == null)
            loadPriceList();
        currentItemType = currentItem.getType();
        currentItemPrice = currentItem.getPrice();
        for (Item item: storeItems) {
            if (item.getType().equals(currentItemType)) {
                if (item.getPrice() >= currentItemPrice)
                    continue;
                if (lessPowerfulItem == null)
                    lessPowerfulItem = item;
                else if (item.getPrice() > lessPowerfulItem.getPrice())
                    lessPowerfulItem = item;
            }
        }
        return lessPowerfulItem;
    }
}
