package com.mygdx.game.screens;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.brashmonkey.spriter.Drawer;
import com.brashmonkey.spriter.FileReference;
import com.brashmonkey.spriter.Loader;
import com.brashmonkey.spriter.Player;
import com.brashmonkey.spriter.Timeline;
import com.mygdx.game.requests.PlayerAccount;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;

public class DrawerImplementation extends Drawer<Sprite> {

    SpriteBatch batch;
    ShapeRenderer renderer;
    ArrayList<String> items = PlayerAccount.getEquippedItems();
    LoaderImplementation loaderImplementation;

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

    private Sprite getSprite(ArrayList<String> itemsList, Timeline.Key.Object object) {
        Sprite sprite = null;
        // make a map

        //  has items
        if (itemsList != null) {
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
                    for (String item : itemsList) {
                        if (item.contains("armor")) {
                            String spritePath;
                            String[] word = item.split("_");
                            if (word[0].equals("iron")) {
                                spritePath = "head_iron";
                                sprite = loaderImplementation.get(spritePath);
                                return sprite;
                            }
                            if (word[0].equals("steel")) {
                                spritePath = "head_steel";
                                sprite = loaderImplementation.get(spritePath);
                                return sprite;
                            }
                            if (word[0].equals("gold")) {
                                spritePath = "head_gold";
                                sprite = loaderImplementation.get(spritePath);
                                return sprite;
                            }
                        }
                    }
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
                sprite = loaderImplementation.get("body_gold");
                return sprite;
            case HEAD:
                sprite = loaderImplementation.get("head_gold");
                return sprite;
            case LEFT_ARM:
                sprite = loaderImplementation.get("left_arm_gold");
                return sprite;
            case RIGHT_ARM:
                sprite = loaderImplementation.get("right_arm_gold");
                return sprite;
            case WEAPON:
                sprite = loaderImplementation.get("weapon_gold");
                return sprite;
            case SHIELD:
                sprite = loaderImplementation.get("shield_gold");
                return sprite;
            case RIGHT_LEG:
                sprite = loaderImplementation.get("right_leg_gold");
                return sprite;
            case LEFT_LEG:
                sprite = loaderImplementation.get("left_leg_gold");
                return sprite;
            default:
                sprite = loader.get(object.ref);
                return sprite;
        }
    }

    @Override
    public void draw(Timeline.Key.Object object) {

        ArrayList<String> itemsName = PlayerAccount.getEquippedItems();
        Sprite sprite = null;
        if(itemsName != null)
            sprite = getSprite(itemsName, object);
        if(sprite == null)
            sprite = loadDefault(object);

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