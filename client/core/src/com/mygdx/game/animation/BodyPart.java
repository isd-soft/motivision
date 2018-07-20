package com.mygdx.game.animation;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.FileReader;
import java.io.IOException;

public class BodyPart {
    private int posX;
    private int posY;
    private float scale;
    private float rotation;

    public int getPosX() {
        return posX;
    }

    public int getPosY() {
        return posY;
    }

    public BodyPart(int posX, int posY, float scale, float rotation) {
        this.posX = posX;
        this.posY = posY;
        this.scale = scale;
        this.rotation = rotation;
    }

    //"properties/head_" +armorType+".txt"
    private void readingLines(String propertiesPath) throws IOException {
        BufferedReader file = null;
        try{
            file = new BufferedReader (new FileReader(propertiesPath));

            String line = null;
            //while(( line = file.readLine()) != null) {
            line = file.readLine();
            parseLine(line);
            //}

        }catch (IOException e) {
            //
        }
        finally {
            closeFile(file);
        }
    }

    private void closeFile(Closeable closeable){
        if (closeable != null){
            try{
                closeable.close();
            }catch (IOException ex){
                //
            }
        }
    }

    public static BodyPart parseLine(String lineToParse){
        int         x;
        int         y;
        float         scale;
        float         rotation;

        String[] result = lineToParse.split("\\|");
        x = Integer.parseInt(result[2]);
        y = Integer.parseInt(result[3]);
        scale = Float.parseFloat(result[4]);
        rotation = Float.parseFloat(result[5]);
        return new BodyPart(x, y, scale, rotation);
    }

    public void draw(SpriteBatch batch, Texture bodyPartsTexture, int pivotX, int pivotY, float scale) {
        int originX;
        int originY;
        int x;
        int y;
        int width;
        int height;

        width = (int) (bodyPartsTexture.getWidth());
        height = (int) (bodyPartsTexture.getHeight());
        originX = -posX;
        originY = -posY;
        x = (int) (posX + pivotX);
        y = (int) (posY + pivotY);

        batch.draw(new TextureRegion(bodyPartsTexture), x, y, originX, originY, width, height, scale, scale, rotation);
    }
}

