package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.brashmonkey.spriter.Data;
import com.brashmonkey.spriter.Drawer;
import com.brashmonkey.spriter.Entity;
import com.brashmonkey.spriter.Loader;
import com.brashmonkey.spriter.Player;
import com.brashmonkey.spriter.SCMLReader;
import com.mygdx.game.animation.ParallaxBackground;
import com.mygdx.game.loader.AssetsManager;

public class CharacterFightAnimation extends Image{

    private SpriteBatch batch;
    private ShapeRenderer renderer;
    private Player player;
    private Player enemy;

    private Loader loader;
    private Drawer drawer;
    private Data data;


    private int currentPosition = 1400;

    private int monsterPosition;
    private int playerPosition;

    //Health should be points, which are taken from DB
    private int monsterHealth = 200;
    private int playerHealth = 250;
    //attack should be some math stuff dunno
    private int playerAttack;
    private int monsterAttack;
    //label for HP
    Label playerHP;
    Label monsterHP;

    Skin skin;
    //

    OgreDrawerImplementation monsterDrawer;

    AssetsManager assetsManager = AssetsManager.getInstance();
    Texture texture;
    Image image;
    ParallaxBackground parallaxBackground;

    public CharacterFightAnimation() {
        this.setZIndex(5);
        parallaxBackground = new ParallaxBackground(assetsManager.getLayers());
        texture = assetsManager.aManager.get("universalbg.png");

        image = new Image(texture);

        image.setBounds(0, 0, getWidth(), getHeight());
        renderer = new ShapeRenderer();
        batch = new SpriteBatch();


        //labels

        skin = new Skin(Gdx.files.internal("skin2/clean-crispy-ui.json"));
//        playerHP = new Label("Players' HP " + playerHealth, skin);
//        monsterHP = new Label("Monsters' HP " + monsterHealth, skin);
        SCMLReader reader = new SCMLReader(Gdx.files.internal("KekNew.scml").read());
        data = reader.getData();
        Entity humanEntity = data.getEntity(0);
        player = new Player(humanEntity);
        enemy = new Player(humanEntity);
        enemy.flip(true, false);

        setPosition();
        player.setTime(700);
        enemy.setTime(700);


//        player.characterMaps = new CharacterMap[1];
//
//        player.characterMaps[0] = charMaps[0];

        //player.
        // player.setAnimation();

        //Animation troll

    }

    public void makeRandomAttack(){
        playerAttack = (int) (Math.random() * 50);
        monsterAttack = (int) (Math.random() * 50);
    }


    public void init(String animation) {
        player.setAnimation(animation);
        enemy.setAnimation(animation);

        loader = new LoaderImplementation(data);
        loader.load(Gdx.files.internal("").path());
        drawer = new DrawerImplementation((LoaderImplementation) loader, batch, renderer);

        monsterDrawer = new OgreDrawerImplementation((LoaderImplementation) loader, batch, renderer);
    }


    public void changeAnimation(String animation) {
        player.setAnimation(animation);
        enemy.setAnimation(animation);

    }


    public void setPosition() {
        playerPosition = -300;
        monsterPosition = 1500;
    }

    @Override
    public void act(float delta) {


        makeRandomAttack();
        player.setPosition(playerPosition, 150);
        enemy.setPosition(monsterPosition, 150);



        player.update();
        enemy.update();
        //first is y second is x

        playerPosition += +2;
        monsterPosition += -2;
        batch.begin();

        batch.draw(texture, 0, 0, 1300, 800);

        drawer.draw(player);
        monsterDrawer.draw(enemy);
        if (playerPosition > 485 && monsterPosition < 715) {
            monsterPosition = 715;
            playerPosition = 485;

            if (monsterHealth > 0 || playerHealth >0) {
                mortalKombat();
            }else{
                setPosition();
            }

        }
//            if (currentPosition < 520) {
//                player.setAnimation("ATTACK");
            //drawer.draw(player);

//            if (currentPosition < 450) {
//                currentPosition = 450;
//                player.setAnimation("ATTACK");
//                enemy.setAnimation("DIE");
//
//                //drawer.draw(enemy);
//                if (enemy.getTime() == 600) {
//                    --alo;
//                    player.setAnimation("WALK");
//                    enemy.setAnimation("WALK");
//                    setPosition();
//
//                    //drawer.draw(player);
//                }
//            }
            //    }

        batch.end();
    }

    public void playerMove(){
        player.setAnimation("ATTACK");
        monsterHealth -= playerAttack;
        if(player.getTime() >= 600){
            enemy.setAnimation("DIE");
        }
    }

    public void ogreMove(){
        enemy.setAnimation("ATTACK");
    }

    public void mortalKombat(){
        player.setAnimation("ATTACK");
        //makeRandomAttack();
        monsterHealth -= playerAttack;
        //monsterHP.setText("Monster HP:" + monsterHealth);
        if(player.getTime() >= 600){
            player.setAnimation("IDLE");
            enemy.setAnimation("DIE");
//            if(enemy.getTime() >= 600){
//                enemy.setAnimation("ATTACK");
//                playerHealth -= monsterAttack;
//
//                player.setAnimation("DIE");
//                //playerHP.setText("Player HP:" + playerHealth);
//                if(enemy.getTime() >= 600) {
//                    player.setAnimation("DIE");
//                    enemy.setAnimation("DIE");
//                    if(player.getTime() >= 600){
//                        player.setAnimation("DIE");
//                    }
//                }
//            }
        }

        if(enemy.getTime() == 600){
            enemy.setAnimation("ATTACK");
            playerHealth -= monsterAttack;
        }if(enemy.getTime() == 600){
            enemy.setAnimation("IDLE");
            player.setAnimation("DIE");
        }


    }
}
