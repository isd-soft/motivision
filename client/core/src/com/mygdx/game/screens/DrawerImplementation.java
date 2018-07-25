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
import com.mygdx.game.requests.PlayerAccount;

import org.json.JSONException;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;

public class DrawerImplementation extends Drawer<Sprite> {

    SpriteBatch batch;
    ShapeRenderer renderer;
    ArrayList<String> items = PlayerAccount.getEquippedItems();
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

    private Sprite getSprite(ArrayList<String> itemsList, Timeline.Key.Object object) {
        Sprite sprite = null;
        // make a map

        //  has items
        if (itemsList != null) {
            //String key = BodyParts.getById(object.ref.file).name().toLowerCase()+"_"+
            switch (BodyParts.getById(object.ref.file)) {
                case BODY:
                    // default sprite init here
                    for (String item : itemsList) {
                        if (item.contains("armor")) {
                            String spritePath;
                            String[] word = item.split("_");
                            if (word[0].equals("iron")) {
                                spritePath = "body_iron";
                                sprite = loaderImplementation.get(spritePath);
                                return sprite;
                            }
                            if (word[0].equals("steel")) {
                                spritePath = "body_steel";
                                sprite = loaderImplementation.get(spritePath);
                                return sprite;
                            }
                            if (word[0].equals("gold")) {
                                spritePath = "body_gold";
                                sprite = loaderImplementation.get(spritePath);
                                return sprite;
                            }
                        }
                    }
                    break;
                case HEAD:
                    int headNumber = PlayerAccount.getHeadNumber();
                    if (headNumber != 0)
                        sprite = loaderImplementation.getHead(headNumber);
                    else
                        sprite = loaderImplementation.getHead(4);
                    break;
                case LEFT_ARM:
                    for (String item : itemsList) {
                        if (item.contains("armor")) {
                            String spritePath;
                            String[] word = item.split("_");
                            if (word[0].equals("iron")) {
                                spritePath = "left_arm_iron";
                                sprite = loaderImplementation.get(spritePath);
                                return sprite;
                            }
                            if (word[0].equals("steel")) {
                                spritePath = "left_arm_steel";
                                sprite = loaderImplementation.get(spritePath);
                                return sprite;
                            }
                            if (word[0].equals("gold")) {
                                spritePath = "left_arm_gold";
                                sprite = loaderImplementation.get(spritePath);
                                return sprite;
                            }
                        }
                    }
                    break;
                case RIGHT_ARM:
                    for (String item : itemsList) {
                        if (item.contains("armor")) {
                            String spritePath;
                            String[] word = item.split("_");
                            if (word[0].equals("iron")) {
                                spritePath = "right_arm_iron";
                                sprite = loaderImplementation.get(spritePath);
                                return sprite;
                            }
                            if (word[0].equals("steel")) {
                                spritePath = "right_arm_steel";
                                sprite = loaderImplementation.get(spritePath);
                                return sprite;
                            }
                            if (word[0].equals("gold")) {
                                spritePath = "right_arm_gold";
                                sprite = loaderImplementation.get(spritePath);
                                return sprite;
                            }
                        }
                    }
                    break;
                case WEAPON:
                    for (String item : itemsList) {
                        if (item.contains("sword") || item.contains("axe")) {
                            String spritePath;
                            String[] word = item.split("_");
                            if (word[0].equals("iron")) {
                                spritePath = "weapon_iron";
                                sprite = loaderImplementation.get(spritePath);
                                return sprite;
                            }
                            if (word[0].equals("steel")) {
                                spritePath = "weapon_steel";
                                sprite = loaderImplementation.get(spritePath);
                                return sprite;
                            }
                            if (word[0].equals("gold")) {
                                spritePath = "weapon_gold";
                                sprite = loaderImplementation.get(spritePath);
                                return sprite;
                            }
                        }
                    }
                    break;
                case SHIELD:
                    for (String item : itemsList) {
                        if (item.contains("shield")) {
                            String spritePath;
                            String[] word = item.split("_");
                            if (word[0].equals("iron")) {
                                spritePath = "shield_iron";
                                sprite = loaderImplementation.get(spritePath);
                                return sprite;
                            }
                            if (word[0].equals("steel")) {
                                spritePath = "shield_steel";
                                sprite = loaderImplementation.get(spritePath);
                                return sprite;
                            }
                            if (word[0].equals("gold")) {
                                spritePath = "shield_gold";
                                sprite = loaderImplementation.get(spritePath);
                                return sprite;
                            }
                        }
                    }
                    break;
                case RIGHT_LEG:
                    for (String item : itemsList) {
                        if (item.contains("leggins")) {
                            String spritePath;
                            String[] word = item.split("_");
                            if (word[0].equals("iron")) {
                                spritePath = "right_leg_iron";
                                sprite = loaderImplementation.get(spritePath);
                                return sprite;
                            }
                            if (word[0].equals("steel")) {
                                spritePath = "right_leg_steel";
                                sprite = loaderImplementation.get(spritePath);
                                return sprite;
                            }
                            if (word[0].equals("gold")) {
                                spritePath = "right_leg_gold";
                                sprite = loaderImplementation.get(spritePath);
                                return sprite;
                            }
                        }
                    }
                    break;
                case LEFT_LEG:
                    for (String item : itemsList) {
                        if (item.contains("leggins")) {
                            String spritePath;
                            String[] word = item.split("_");
                            if (word[0].equals("iron")) {
                                spritePath = "left_leg_iron";
                                sprite = loaderImplementation.get(spritePath);
                                return sprite;
                            }
                            if (word[0].equals("steel")) {
                                spritePath = "left_leg_steel";
                                sprite = loaderImplementation.get(spritePath);
                                return sprite;
                            }
                            if (word[0].equals("gold")) {
                                spritePath = "left_leg_gold";
                                sprite = loaderImplementation.get(spritePath);
                                return sprite;
                            }
                        }
                    }
                    break;
            }
        }
        return sprite;
    }

    private Sprite loadDefault(Timeline.Key.Object object) {
        Sprite sprite;
        // default
        switch (BodyParts.getById(object.ref.file)) {
            case BODY:
                sprite = loaderImplementation.get("body_default");
                return sprite;
            case HEAD:
                sprite = loaderImplementation.getHead(CreateCharacterScreen.getHeadNumber());
                return sprite;
            case LEFT_ARM:
                sprite = loaderImplementation.get("left_arm_default");
                return sprite;
            case RIGHT_ARM:
                sprite = loaderImplementation.get("right_arm_default");
                return sprite;
            case WEAPON:
                sprite = loaderImplementation.get("weapon_default");
                return sprite;
            case SHIELD:
                sprite = loaderImplementation.get("shield_default");
                return sprite;
            case RIGHT_LEG:
                sprite = loaderImplementation.get("right_leg_default");
                return sprite;
            case LEFT_LEG:
                sprite = loaderImplementation.get("left_leg_default");
                return sprite;
            default:
                sprite = loader.get(object.ref);
                return sprite;
        }
    }


    @Override
    public void draw(Timeline.Key.Object object) {

        Sprite sprite = null;
        if (GGame.SCREEN_NUMBER != 4 && GGame.SCREEN_NUMBER != 8) {
            ArrayList<String> itemsName = PlayerAccount.getEquippedItems();
            if (itemsName != null) {
                sprite = getSprite(itemsName, object);
                if (sprite == null)
                    sprite = loadDefault(object);
            }
            //Create character screen
            if (itemsName == null)
                sprite = getEmptySprite();
        } else if (GGame.SCREEN_NUMBER == 8) {
            try {
                ArrayList<String> itemsName = PlayerAccount.getTeamMemberEquippedItems(characterName);
                if (itemsName != null) {
                    sprite = getSprite(itemsName, object);
                    if (sprite == null)
                        sprite = loadDefault(object);
                }
                //Create character screen
                if (itemsName == null)
                    sprite = getEmptySprite();
            } catch (IOException e) {
                sprite = loadDefault(object);
            } catch (JSONException e) {
                sprite = loadDefault(object);
            }
        } else {
            sprite = loadDefault(object);
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