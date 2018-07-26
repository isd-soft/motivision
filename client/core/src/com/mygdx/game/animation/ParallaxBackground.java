package com.mygdx.game.animation;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.utils.Array;
public class ParallaxBackground extends Actor {

    private int scroll;
    private Array<Texture> layers;
    private final int LAYER_SPEED_DIFFERENCE = 1;

    float x,y,width,heigth,scaleX,scaleY;
    int originX, originY,rotation,srcX,srcY;
    boolean flipX,flipY;
    Group group;

    private int speed;

    public ParallaxBackground(Array<Texture> textures){
        layers = textures;
        group = new Group();
        for(int i = 0; i <textures.size;i++){
            layers.get(i).setWrap(Texture.TextureWrap.MirroredRepeat, Texture.TextureWrap.MirroredRepeat);
        }
        scroll = 0;
        speed = 0;
        x = y = originX = originY = rotation = srcY = 0;
        width =  Gdx.graphics.getWidth();
        heigth = Gdx.graphics.getHeight();
        scaleX = scaleY = 1;
        flipX = flipY = false;

    }

    public Array<Texture> getLayers() {
        return layers;
    }

    public void setSpeed(int newSpeed){
        this.speed = newSpeed;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.setColor(getColor().r, getColor().g, getColor().b, getColor().a * parentAlpha);

        scroll+=speed;
        for(int i = 0;i<layers.size;i++) {
            // background speed adjusting here
            srcX = scroll + (i*this.LAYER_SPEED_DIFFERENCE *scroll)/3;
            batch.draw(layers.get(i), x, y, originX, originY, width, heigth,
                    scaleX,scaleY, rotation, srcX,srcY, 800,layers.get(i).getHeight(), flipX,flipY);
        }
    }
}
