package com.mygdx.game.requests;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import javax.imageio.ImageIO;

public class Profile implements Comparable<Profile>{
    public static final String  PROFILE_ID = "characterId";
    public static final String  TEAM_ID = "teamId";
    public static final String  IS_ADMIN = "isAdmin";
    public static final String  NAME = "characterName";
    public static final String  HEAD_TYPE = "headType";
    public static final String  BODY_TYPE = "bodyType";
    public static final String  GENDER = "gender";
    public static final String  POINTS = "points";
    public static final String  PLAYER_ID = "playerId";

    private String  name;
    private int     id;
    private int     headType;
    private int     bodyType;
    private char    gender;
    private int     points;
    private int     teamId;
    private boolean isAdmin;
    private ArrayList<Item>     itemList;


    private Profile(int id, int teamId, boolean isAdmin) {
        this.id = id;
        this.name = null;
        this.headType = 0;
        this.bodyType = 0;
        this.gender = 'N';
        this.points = 0;
        this.teamId = teamId;
        this.isAdmin = isAdmin;
        this.itemList = null;
    }

    public static boolean	nameExist(String name) throws IOException, JSONException {
        String      urlParameters;
        String		url;
        JSONObject  jsonObject;
        String		result;

        url = JsonHandler.domain + "/character_exist";
        urlParameters = NAME + "=" + name;
        jsonObject = JsonHandler.readJsonFromUrl(url, urlParameters, "POST");
        if (jsonObject == null)
            return true;
        result = jsonObject.getString("message");
        if (result.equals("false"))
            return false;
        return true;
    }

    public ArrayList<String>    getEquippedItems() {
        ArrayList<String>   equippedItems = null;
        if(itemList == null){
            return null;
        }
        for (Item item: itemList) {
            if (item.isEquipped()) {
                if (equippedItems == null)
                    equippedItems = new ArrayList<String>();
                equippedItems.add(item.getName());
            }
        }
        return equippedItems;
    }

    private static void	setErrorMessage(String message) {
        JsonHandler.errorMessage = message;
    }

    public boolean  isAdmin() {
        return isAdmin;
    }

    public static Profile      getProfileFromJson(JSONObject jsonObject) throws JSONException {
        Profile     profile;
        String		field;
        int         characterId;
        int         teamId;
        boolean     isAdmin;
        int         number;
        char        gender = 'N';

        if (jsonObject == null)
            return null;
        try {
            characterId = jsonObject.getInt(PROFILE_ID);
            if (jsonObject.has(TEAM_ID))
                teamId = jsonObject.getInt(TEAM_ID);
            else
                teamId = -1;

            if (jsonObject.has(IS_ADMIN)) {
                field = jsonObject.getString(IS_ADMIN);
                isAdmin = field.equals("true");
            }
            else
                isAdmin = false;

            profile = new Profile(characterId, teamId, isAdmin);

            field = jsonObject.getString(NAME);
            profile.setName(field);

            field = jsonObject.getString(HEAD_TYPE);
            number = Integer.parseInt(field);
            profile.setHeadType(number);

            field = jsonObject.getString(BODY_TYPE);
            number = Integer.parseInt(field);
            profile.setBodyType(number);

            field = jsonObject.getString(GENDER);
            if (field.length() == 1)
                gender = field.toCharArray()[0];
            profile.setGender(gender);

            //field = jsonObject.getString(POINTS);
            //System.out.println("Points = " + field);
            number = jsonObject.getInt(POINTS);
            profile.setPoints(number);

            profile.loadItems(jsonObject);

            return profile;
        } catch (NumberFormatException e) {
            setErrorMessage("Invalid number format");
            return null;
        }
    }

    static Profile      getProfileFromUrl(String url, String urlParameters, String requestMethod)
            throws JSONException, IOException {
        JSONObject	jsonObject;

        jsonObject = JsonHandler.readJsonFromUrl(url, urlParameters, requestMethod);
        System.out.println(url + "?" + urlParameters);
        return getProfileFromJson(jsonObject);
    }

    public void    updateItems() throws IOException, JSONException {
        String      urlParameters;
        String		url;
        JSONObject  jsonObject;

        itemList = null;
        url = JsonHandler.domain + "/get_items";
        urlParameters = PROFILE_ID + "=" + id;
        jsonObject = JsonHandler.readJsonFromUrl(url, urlParameters, "GET");
        if (jsonObject == null)
            return;
        loadItems(jsonObject);
    }

    private void loadItems(JSONObject jsonObject) throws JSONException {
        Item    item;
        String  field;
        String  itemType;
        int     itemId;
        int     itemImage;
        int     itemPrice;
        boolean equipped;

        if (jsonObject.has("items") == false)
            return;
        if (jsonObject.isNull("items"))
            return;

        if (jsonObject.get("items") == null)
            return;
        JSONArray arr;
        try {
            arr = jsonObject.getJSONArray("items");
        } catch (JSONException e) {
            return;
        }
        if (arr == null)
            return;
        itemList = new ArrayList<Item>();

        for (int i = 0; i < arr.length(); i++)
        {
            itemId = arr.getJSONObject(i).getInt(Item.ITEM_ID);
            itemImage = arr.getJSONObject(i).getInt(Item.ITEM_IMAGE);
            itemPrice = arr.getJSONObject(i).getInt(Item.ITEM_PRICE);
            itemType = arr.getJSONObject(i).getString(Item.ITEM_TYPE);
            if (arr.getJSONObject(i).has(Item.ITEM_EQUIPPED))
                equipped = arr.getJSONObject(i).getBoolean(Item.ITEM_EQUIPPED);
            else
                equipped = true;
            item = new Item(itemId, itemImage, itemPrice, itemType, equipped);
            itemList.add(item);
        }
    }

    private static Profile  createProfileWithId(int characterId, LinkedHashMap<String, String> profileParams) {
        try {
            Profile         profile;
            String          field;
            int             teamId;
            int             number;
            boolean         isAdmin;
            char            gender = 'N';

            field = profileParams.get(TEAM_ID);
            teamId = Integer.parseInt(field);

            field = profileParams.get(IS_ADMIN);
            isAdmin = field.equals("true");
            profile = new Profile(characterId, teamId, isAdmin);

            field = profileParams.get(NAME);
            profile.setName(field);

            field = profileParams.get(HEAD_TYPE);
            number = Integer.parseInt(field);
            profile.setHeadType(number);

            field = profileParams.get(BODY_TYPE);
            number = Integer.parseInt(field);
            profile.setBodyType(number);

            field = profileParams.get(GENDER);
            if (field.length() == 1)
                gender = field.toCharArray()[0];
            profile.setGender(gender);

            return profile;
        } catch (NumberFormatException e) {
            setErrorMessage("Invalid number format");
            return null;
        }
    }

    public static Profile	createNewProfile(LinkedHashMap<String, String> profileParams) throws IOException, JSONException {
        // List<BasicNameValuePair>    urlParameters;
        Profile         profile;
        String          url;
        String          urlParameters = null;
        String          name;
        Set<String>     keySet;
        JSONObject      jsonObject;
        int             characterId;

        name = profileParams.get(NAME);
        if (nameExist(name)) {
            setErrorMessage("Name already exist");
            return null;
        }
        url = JsonHandler.domain + "/create_character";
        keySet = profileParams.keySet();
        for (String key: keySet) {
            if (urlParameters == null)
                urlParameters = "";
            else
                urlParameters += "&";
            urlParameters += key + "=" + profileParams.get(key);
        }
//        urlParameters = new ArrayList<BasicNameValuePair>();
//        urlParameters.add(new BasicNameValuePair("login", login + ""));
//        urlParameters.add(new BasicNameValuePair("password", password + ""));

        //String urlParameters = "login=" + login + "&password=" + password;
        System.out.println(url + "?" + urlParameters);
        jsonObject = JsonHandler.readJsonFromUrl(url, urlParameters, "POST");
        if (jsonObject == null)
            return null;
        System.out.println(jsonObject);

        characterId = jsonObject.getInt(PROFILE_ID);
        profile = createProfileWithId(characterId, profileParams);
        if (profile == null)
            System.out.println("Profile is null");
        else
            System.out.println("Profile is not null");
        //PlayerAccount.setProfile(profile);
        return profile;
        //return getProfileFromUrl(url, urlParameters, true);
    }

    public static Profile	getProfile(int profileId) throws IOException, JSONException {
        String  url;
        Profile profile;
        String urlParameters;

        if (profileId == -1)
            return null;

        url = JsonHandler.domain + "/get_character";
        urlParameters = PROFILE_ID + "=" + profileId;

        //String urlParameters = "name=Jack&occupation=programmer";
        System.out.println("Start get profile from URL");
        profile = getProfileFromUrl(url, urlParameters, "GET");
        //PlayerAccount.setProfile(profile);
        return profile;
    }

    private Texture     splitImages(Map<String, String> itemImages) throws IOException {
        BufferedImage result = new BufferedImage(466, 510, BufferedImage.TYPE_INT_RGB);
        Graphics g = result.getGraphics();
        BufferedImage bi;

        bi = ImageIO.read(new File("knight_3.png"));
        g.drawImage(bi, 0, 0, null);

        bi = ImageIO.read(new File("items/" + itemImages.get("leggins") + ".png"));
        g.drawImage(bi, 30, 353, null);

        bi = ImageIO.read(new File("items/" + itemImages.get("armor") + ".png"));
        g.drawImage(bi, 0, 188, null);

        bi = ImageIO.read(new File("items/" + itemImages.get("sword") + ".png"));
        g.drawImage(bi, 203, 165, null);


        bi = ImageIO.read(new File("items/" + itemImages.get("fingers") + ".png"));
        g.drawImage(bi, 219, 304, null);

        if (itemImages.get("shield").equals(null) == false) {
            bi = ImageIO.read(new File("items/" + itemImages.get("shield") + ".png"));
            g.drawImage(bi, 17, 320, null);
        }

        ImageIO.write(result,"png",new File("result.png"));

        return new Texture("result.png");
    }

    private static Pixmap addItemOnPixmap(Pixmap pixmap, String item, int x, int y) {
        Pixmap  itemPixmap;

        itemPixmap = new Pixmap(Gdx.files.internal("items/" + item + ".png"));
        pixmap.drawPixmap(itemPixmap, x, y);
        itemPixmap.dispose();
        return pixmap;
    }

    private Texture addItems(Map<String, String> itemImages) {
        Texture mergedImage;
        Pixmap pixmap;

        System.out.println("Start creating profile texture");
        pixmap = new Pixmap(Gdx.files.internal("default.png"));
        pixmap = addItemOnPixmap(pixmap, itemImages.get("leggins"), 30, 353);
        pixmap = addItemOnPixmap(pixmap, itemImages.get("armor"), 0, 188);
        pixmap = addItemOnPixmap(pixmap, itemImages.get("sword"), 203, 165);
        pixmap = addItemOnPixmap(pixmap, itemImages.get("fingers"), 219, 304);
        if (itemImages.get("shield").equals("null") == false) {
            pixmap = addItemOnPixmap(pixmap, itemImages.get("shield"), 17, 320);
        }
        mergedImage = changeFaceType(pixmap, headType, bodyType);
//        pixmap.dispose();
        return mergedImage;
    }

    public Texture getProfileTexture() {
        Map<String, String> itemImages;
        Set<String>         itemSet;

        //System.out.println("procesing...");
        itemImages = new LinkedHashMap<String, String>();
        itemImages.put("sword", "default_sword");
        itemImages.put("armor", "default_armor");
        itemImages.put("fingers", "default_fingers");
        itemImages.put("leggins", "default_leggins");
        itemImages.put("shield", "null");
        itemSet = itemImages.keySet();
        if (itemList != null) {
            for (Item item : itemList) {
                if (item.isEquipped() == false)
                    continue;
                for (String itemType : itemSet) {
                    if (item.getType().contains(itemType)) {
                        itemImages.put(itemType, item.getType());
                        if (itemType.equals("armor")) {
                            itemImages.put("fingers", item.getType().replaceAll("armor", "fingers"));
                        }
                    }
                }
            }
        }
        System.out.println("procesing...");
        return addItems(itemImages);
    }

    public boolean      deleteProfile() throws IOException, JSONException {
        String  url;
        String  status;
        String urlParameters;
        JSONObject jsonObject;

        if (id == -1)
            return false;
        url = JsonHandler.domain + "/delete_character";
        urlParameters = PROFILE_ID + "=" + id;
        jsonObject = JsonHandler.readJsonFromUrl(url, urlParameters, "DELETE");
        //System.out.println(url + "?" + urlParameters);
        if (jsonObject == null)
            return false;
        status = jsonObject.getString("status");
        if (status.equals("success"))
            return true;
        return false;
    }

    public void     printProfile() {
        System.out.println("Id: " + this.id);
        System.out.println("Name: " + this.name);
        System.out.println("headType: " + this.headType);
        System.out.println("bodyType: " + this.bodyType);
        System.out.println("gender: " + this.gender);
        System.out.println("points: " + this.points);
        System.out.println("teamId: " + this.teamId);
        System.out.println("isAdmin: " + this.isAdmin);
        if (itemList != null) {
            for (Item item: itemList) {
                item.print();
            }
        }
    }

    public void     buyItem(String itemType) {
        int     itemId;

        //itemId = Item.getId(itemType);
    }

    public int getId() {
        return id;
    }

    public String getProfileName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setHeadType(int headType) {
        this.headType = headType;
    }

    public void setBodyType(int bodyType) {
        this.bodyType = bodyType;
    }

    public char getGender() {
        return gender;
    }

    public void setGender(char gender) {
        this.gender = gender;
    }

    public int getBodyType() {
        return bodyType;
    }

    public int getHeadType() {
        return headType;
    }

    public String getName() {
        return name;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public int getTeamId() {
        return teamId;
    }


    public static Texture changeFaceType(Pixmap background, int headType, int bodyType) {
        Texture mergedImage;
        Pixmap  itemPixmap;

        if (headType <= 0 || headType > 3)
            headType = 1;
        if (bodyType <= 0 || bodyType > 3)
            bodyType = 1;

        itemPixmap = new Pixmap(Gdx.files.internal("head/body" + bodyType + ".png"));
        background.drawPixmap(itemPixmap, 0, 0);
        itemPixmap.dispose();
        itemPixmap = new Pixmap(Gdx.files.internal("head/head" + headType + ".png"));
        background.drawPixmap(itemPixmap, 0, 0);
        itemPixmap.dispose();
        mergedImage = new Texture(background);
        System.out.println("Texture created");
        // background.dispose();
        return mergedImage;
    }

    public static Texture changeFaceType(int headType, int bodyType) {
        Pixmap  pixmap;

        pixmap = new Pixmap(Gdx.files.internal("default.png"));
        return changeFaceType(pixmap, headType, bodyType);
    }

    public boolean     buyItem(int id) throws IOException, JSONException {
        String      urlParameters;
        String		url;
        JSONObject  jsonObject;
        String		result;

        url = JsonHandler.domain + "/buy_item";
        urlParameters = PROFILE_ID + "=" + this.id;
        urlParameters += "&" + Item.ITEM_ID + "=" + id;
        jsonObject = JsonHandler.readJsonFromUrl(url, urlParameters, "GET");
        if (jsonObject == null)
            return false;
        result = jsonObject.getString("status");
        return result.equals("success");
    }

    public int getItemStatus(int id) {
        if (itemList == null)
            return Item.STORE_ITEM;
        for (Item item: itemList) {
            if (item.getId() == id)
                return item.isEquipped() ? Item.EQUIPPED_ITEM : Item.UNEQUIPPED_ITEM;
        }
        return Item.STORE_ITEM;
    }

    public void unequipItem(int itemId) throws IOException, JSONException {
        String      url;
        String      urlParameters;

        for (Item item: itemList) {
            if (item.getId() == itemId) {
                item.unequip();
            }
        }
        url = JsonHandler.domain + "/unequip_item";
        urlParameters = PROFILE_ID + "=" + this.id;
        urlParameters += "&" + Item.ITEM_ID + "=" + itemId;
        JsonHandler.readJsonFromUrl(url, urlParameters, "GET");
    }
    public void equipItem(int itemId) throws IOException, JSONException {
        String      url;
        String      urlParameters;
        String      type = null;

        for (Item item: itemList) {
            if (item.getId() == itemId) {
                type = item.getType().split("_")[1];
                item.equip();
                System.out.println("Item found!");
                break;
            }

        }
        url = JsonHandler.domain + "/equip_item";
        urlParameters = PROFILE_ID + "=" + this.id;
        urlParameters += "&" + Item.ITEM_ID + "=" + itemId;
        JsonHandler.readJsonFromUrl(url, urlParameters, "GET");
        for (Item item: itemList) {
            if (item.getId() != itemId && item.getType().contains(type)) {
                item.unequip();
            }
        }
    }



    public boolean      doActivity(int activityId) throws IOException, JSONException {
        String      urlParameters;
        String		url;
        JSONObject  jsonObject;
        String		result;

        url = JsonHandler.domain + "/do_activity";
        urlParameters = Profile.PROFILE_ID + "=" + id;
        urlParameters += "&" + Activity.ACTIVITY_ID + "=" + activityId;
        jsonObject = JsonHandler.readJsonFromUrl(url, urlParameters, "POST");
        if(jsonObject == null)
            return false;
        this.points = jsonObject.getInt("points");
        return jsonObject.getString("status").equals("success");

    }

    public boolean updateActivity(int activityId, String activityName, int activityReward)
            throws IOException, JSONException {
        String      urlParameters;
        String		url;
        JSONObject  jsonObject;
        String		result;

        url = JsonHandler.domain + "/update_activity";
        urlParameters = Team.TEAM_ID + "=" + teamId;
        urlParameters += "&" + Activity.ACTIVITY_ID + "=" + activityId;
        urlParameters += "&" + Activity.ACTIVITY_NAME + "=" + activityName;
        urlParameters += "&" + Activity.ACTIVITY_REWARD + "=" + activityReward;
        jsonObject = JsonHandler.readJsonFromUrl(url, urlParameters, "POST");
        if(jsonObject == null)
            return false;
        return jsonObject.getString("status").equals("success");
    }

    public boolean deleteActivity( int activityId)
            throws IOException, JSONException {
        String      urlParameters;
        String		url;
        JSONObject  jsonObject;
        String		result;

        url = JsonHandler.domain + "/delete_activity";
        urlParameters = Team.TEAM_ID + "=" + teamId;
        urlParameters += "&" + Activity.ACTIVITY_ID + "=" + activityId;
        jsonObject = JsonHandler.readJsonFromUrl(url, urlParameters, "DELETE");
        if(jsonObject == null)
            return false;
        return jsonObject.getString("status").equals("success");
    }

    public boolean createActivity(String name) throws IOException, JSONException {
        String      urlParameters;
        String		url;
        JSONObject  jsonObject;
        String		result;

        url = JsonHandler.domain + "/create_activity";
        urlParameters = Team.TEAM_ID + "=" + teamId;
        urlParameters += "&" + Activity.ACTIVITY_NAME + "=" + name;
        urlParameters += "&" + Activity.ACTIVITY_REWARD + "=" + 0;
        jsonObject = JsonHandler.readJsonFromUrl(url, urlParameters, "POST");
        if(jsonObject == null)
            return false;
        return jsonObject.getString("status").equals("success");
    }

    @Override
    public int compareTo(Profile profile) {
        return name.compareTo(profile.getName());
    }
}
