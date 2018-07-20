package com.mygdx.game.gameSets;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

public class GameModel {
    public World world;
    //private Body bodyd;
    private Body bodys;
    private Body bodyk;


    public Body knight;

    public GameModel(){

        world = new World(new Vector2(0,-10f), true);
        createFloor();
        createObject();
        createMovingObject();

        BodyFactory bodyFactory = BodyFactory.getInstance(world);

        bodyFactory.makeSquareBody(1,3,2,  BodyFactory.HUMAN, BodyDef.BodyType.DynamicBody, false);
        bodyFactory.makeSquareBody(-10,3,2,  BodyFactory.SKELETON, BodyDef.BodyType.DynamicBody, false);
        bodyFactory.makeSquareBody(10,4,2,  BodyFactory.WOLF, BodyDef.BodyType.DynamicBody, false);
        bodyFactory.makeSquareBody(3,2,2,  BodyFactory.TROLL, BodyDef.BodyType.DynamicBody, false);


    }


    private void createObject() {

        //create a new body definition
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(0, 0);


        // add it to the world
        knight = world.createBody(bodyDef);

        // set the shape (here we use a box 1 meters wide, 1 meter tall )
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(2, 1);

        // set the properties of the object ( shape, weight, restitution(bouncyness)
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 1f;

        // create the physical object in our body)
        // without this our body would just be data in the world
        knight.createFixture(shape, 1.0f);

        // we no longer use the shape object here so dispose of it.
        shape.dispose();
    }


    private void createFloor() {
        // create a new body definition (type and location)
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.position.set(0, -10f);

        // add it to the world
        bodys = world.createBody(bodyDef);

        // set the shape (here we use a box 50 meters wide, 1 meter tall )
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(50, 1);

        // create the physical object in our body)
        // without this our body would just be data in the world
        bodys.createFixture(shape, 0.0f);

        // we no longer use the shape object here so dispose of it.
        shape.dispose();
    }

    private void createMovingObject(){

        //create a new body definition (type and location)
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.KinematicBody;
        bodyDef.position.set(-14.5f,-7f);


        // add it to the world
        bodyk = world.createBody(bodyDef);

        // set the shape (here we use a box 50 meters wide, 1 meter tall )
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(1,2f);

        // set the properties of the object ( shape, weight, restitution(bouncyness)
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 1f;

        // create the physical object in our body)
        // without this our body would just be data in the world
        bodyk.createFixture(shape, 0.0f);

        // we no longer use the shape object here so dispose of it.
        shape.dispose();

        bodyk.setLinearVelocity(1, 0f);
    }

    //game logic
    public void logicStep(float delta){
        world.step(delta , 3, 3);
    }

}
