package com.mygdx.game.screens;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.brashmonkey.spriter.Drawer;
import com.brashmonkey.spriter.FileReference;
import com.brashmonkey.spriter.Loader;
import com.brashmonkey.spriter.Player;
import com.brashmonkey.spriter.Timeline;
import com.mygdx.game.gameSets.GGame;
import com.mygdx.game.requests.Item;
import com.mygdx.game.requests.PlayerAccount;

import org.json.JSONException;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;

public class DrawerImplementation extends Drawer<Sprite> {

    SpriteBatch batch;
    ShapeRenderer renderer;
    ArrayList<Item> items = PlayerAccount.getEquippedItems();
    LoaderImplementation loaderImplementation;
    public static String characterName;

    public DrawerImplementation(LoaderImplementation loader, SpriteBatch batch, ShapeRenderer renderer) {
        super(loader);
        this.loaderImplementation = loader;
        this.batch = batch;
        this.renderer = renderer;
    }

    @Override
    public void setColor(float r, float g, float b, float a) {
        renderer.setColor(r, g, b, a);
    }

    @Override
    public void rectangle(float x, float y, float width, float height) {
        renderer.rect(x, y, width, height);
    }

    @Override
    public void line(float x1, float y1, float x2, float y2) {
        renderer.line(x1, y1, x2, y2);
    }

    @Override
    public void circle(float x, float y, float radius) {
        renderer.circle(x, y, radius);
    }

    private void updateItems() {
        items = PlayerAccount.getEquippedItems();
    }

    private Sprite getEmptySprite() {
        return loaderImplementation.get("empty");
    }

    private Sprite getSprite(ArrayList<Item> itemsList, int headNumber, Timeline.Key.Object object) {
        Sprite sprite = null;
        // make a map

        //  has items
        if (itemsList != null) {
            //String key = BodyParts.getById(object.ref.file).name().toLowerCase()+"_"+
            switch (BodyParts.getById(object.ref.file)) {
                case BODY:
                    // default sprite init here
                    for (Item item : itemsList) {
                        if(item.getType().equals("armor")){
                            sprite = loaderImplementation.get(item.getImagePath());
                        }
                    }
                    break;
                case HEAD:
//                    int headNumber = PlayerAccount.getHeadNumber();
                    if (headNumber != 0)
                        sprite = loaderImplementation.getHead(headNumber);
                    else
                        sprite = loaderImplementation.getHead(4);
                    break;
                case LEFT_ARM:
                    for (Item item : itemsList) {
                        if(item.getType().equals("armor")){
                            sprite = loaderImplementation.get("left_arm_" + item.getImagePath().replaceAll("armor_", ""));
                        }
                    }
                    break;
                case RIGHT_ARM:
                    for (Item item : itemsList) {
                        if(item.getType().equals("armor")){
                            sprite = loaderImplementation.get("right_arm_" + item.getImagePath().replaceAll("armor_", ""));
                        }
                    }
                    break;
                case WEAPON:
                    for (Item item : itemsList) {
                        if(item.getType().equals("weapon")){
                            sprite = loaderImplementation.get(item.getImagePath());
                        }
                    }
                    break;
                case SHIELD:
                    for (Item item : itemsList) {
                        if(item.getType().equals("shield")){
                            sprite = loaderImplementation.get(item.getImagePath());
                        }
                    }
                    break;
                case RIGHT_LEG:
                    for (Item item : itemsList) {
                        if(item.getType().equals("leggins")){
                            sprite = loaderImplementation.get("right_leg_" + item.getImagePath().replaceAll("leggins_", ""));
                        }
                    }
                    break;
                case LEFT_LEG:
                    for (Item item : itemsList) {
                        if(item.getType().equals("leggins")){
                            sprite = loaderImplementation.get("left_leg_" + item.getImagePath().replaceAll("leggins_", ""));
                        }
                    }
                    break;
            }
        }
        return sprite;
    }

    private Sprite loadDefault(Timeline.Key.Object object, int headNumber) {
        Sprite sprite;
        // default
        switch (BodyParts.getById(object.ref.file)) {
            case BODY:
                sprite = loaderImplementation.get("armor_1");
                return sprite;
            case HEAD:
                if (GGame.SCREEN_NUMBER == 4) {
                    headNumber = CreateCharacterScreen.getHeadNumber();
                }
                sprite = loaderImplementation.getHead(headNumber);
                return sprite;
            case LEFT_ARM:
                sprite = loaderImplementation.get("left_arm_1");
                return sprite;
            case RIGHT_ARM:
                sprite = loaderImplementation.get("right_arm_1");
                return sprite;
            case WEAPON:
                sprite = loaderImplementation.get("weapon_1");
                return sprite;
            case SHIELD:
                sprite = loaderImplementation.get("shield_1");
                return sprite;
            case RIGHT_LEG:
                sprite = loaderImplementation.get("right_leg_1");
                return sprite;
            case LEFT_LEG:
                sprite = loaderImplementation.get("left_leg_1");
                return sprite;
            default:
                sprite = loader.get(object.ref);
                return sprite;
        }
    }


    @Override
    public void draw(Timeline.Key.Object object) {

        Sprite sprite = null;
        int    headNumber;


        if (GGame.SCREEN_NUMBER != GGame.CREATECHARACTER - 1 && GGame.SCREEN_NUMBER != GGame.TEAMMEMBER - 1) {
            ArrayList<Item> itemsName = PlayerAccount.getEquippedItems();

            if (itemsName != null) {
                headNumber = PlayerAccount.getHeadNumber();
                sprite = getSprite(itemsName, headNumber, object);
                if (sprite == null)
                    sprite = loadDefault(object, 1);
            }
            //Create character screen
            if (itemsName == null)
                sprite = getEmptySprite();
        } else if (GGame.SCREEN_NUMBER == GGame.TEAMMEMBER - 1) {
            try {
                ArrayList<Item> itemsName = PlayerAccount.getTeamMemberEquippedItems(characterName);
                if (itemsName != null) {
                    headNumber = PlayerAccount.getTeamMemberHeadNumber(characterName);
                    sprite = getSprite(itemsName, headNumber, object);
                    if (sprite == null)
                        sprite = loadDefault(object, 1);
                }
                //Create character screen
                if (itemsName == null)
                    sprite = getEmptySprite();
            } catch (IOException e) {
                sprite = loadDefault(object, 1);
            } catch (JSONException e) {
                sprite = loadDefault(object, 1);
            }
        } else if (GGame.SCREEN_NUMBER == GGame.CREATECHARACTER - 1) {
                sprite = loadDefault(object, CreateCharacterScreen.getHeadNumber());
        } else {
            sprite = loadDefault(object, 1);
        }


        float newPivotX = (sprite.getWidth() * object.pivot.x);
        float newX = object.position.x - newPivotX;
        float newPivotY = (sprite.getHeight() * object.pivot.y);
        float newY = object.position.y - newPivotY;

        sprite.setX(newX);
        sprite.setY(newY);

        sprite.setOrigin(newPivotX, newPivotY);
        sprite.setRotation(object.angle);

        sprite.setColor(1f, 1f, 1f, object.alpha);
        sprite.setScale(object.scale.x, object.scale.y);
        sprite.draw(batch);
    }

}