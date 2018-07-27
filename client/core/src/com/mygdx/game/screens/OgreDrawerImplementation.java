package com.mygdx.game.screens;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.brashmonkey.spriter.Drawer;
import com.brashmonkey.spriter.Loader;
import com.brashmonkey.spriter.Timeline;
import com.mygdx.game.gameSets.GGame;
import com.mygdx.game.requests.PlayerAccount;

import java.util.ArrayList;

public class OgreDrawerImplementation extends Drawer<Sprite> {

    SpriteBatch batch;
    ShapeRenderer renderer;
    LoaderImplementation loaderImplementation;

    /**
     * Creates a new drawer based on the given loader.
     *
     * @param loader the loader containing resources
     */
    public OgreDrawerImplementation(LoaderImplementation loader, SpriteBatch batch, ShapeRenderer renderer) {
        super(loader);
        this.renderer = renderer;
        this.batch = batch;
        this.loaderImplementation = loader;
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

    private Sprite getSprite(MonsterGenerator monster, Timeline.Key.Object object) {
        Sprite sprite = null;

        // make a map

        //  has items
        if (monster != null) {
            //String key = BodyParts.getById(object.ref.file).name().toLowerCase()+"_"+
            switch (BodyParts.getById(object.ref.file)) {
                case BODY:
                    sprite = loaderImplementation.get("armor_" + monster.getBodyType());
                    return sprite;
                case HEAD:
                    sprite = loaderImplementation.getHead(monster.getHead());
                    return sprite;
                case LEFT_ARM:
                    sprite = loaderImplementation.get("left_arm_" + monster.getBodyType());
                    return sprite;
                case RIGHT_ARM:
                    sprite = loaderImplementation.get("right_arm_" + monster.getBodyType());
                    return sprite;
                case WEAPON:
                    sprite = loaderImplementation.get("weapon_" + monster.getWeaponType());
                    return sprite;
                case SHIELD:
                    sprite = loaderImplementation.get("shield_" + monster.getShieldType());
                    return sprite;
                case RIGHT_LEG:
                    sprite = loaderImplementation.get("right_leg_" + monster.getLegsType());
                    return sprite;
                case LEFT_LEG:
                    sprite = loaderImplementation.get("left_leg_" + monster.getLegsType());
                    return sprite;
            }
        }
        return sprite;
    }

    @Override
    public void draw(Timeline.Key.Object object) {

        Sprite sprite = null;

        //
        sprite = getSprite(MonsterGenerator.getInstance(), object);



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
