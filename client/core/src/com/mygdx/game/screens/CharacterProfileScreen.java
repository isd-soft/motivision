package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.gameSets.GGame;
import com.mygdx.game.requests.JsonHandler;
import com.mygdx.game.requests.PlayerAccount;
import com.mygdx.game.requests.Profile;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;

import de.tomgrill.gdxdialogs.core.GDXDialogs;
import de.tomgrill.gdxdialogs.core.GDXDialogsSystem;
import de.tomgrill.gdxdialogs.core.dialogs.GDXButtonDialog;

import de.tomgrill.gdxdialogs.core.listener.ButtonClickListener;

public class CharacterProfileScreen implements Screen {
    private static final int    YES = 0;
    private static final int    CANCEL = 1;
    private GGame parent;
    private Stage stage;
    private Skin skin;

    private Table itemTable;
    private Texture textureImage;
    private TextureRegion textureRegion;
    private TextureRegionDrawable textureRegionDrawable;

    private GDXDialogs manageTeamDialog;
    private GDXDialogs buyItemDialog;

    private Texture knightTex;

    private Viewport viewport;
    private Camera camera;
    private Music loginMusic;

    public CharacterProfileScreen(GGame g) {
        parent = g;
        manageTeamDialog = GDXDialogsSystem.install();
        buyItemDialog = GDXDialogsSystem.install();

        skin = new Skin(Gdx.files.internal("skin1/neon-ui.json"));
//        skin = new Skin(Gdx.files.internal("skin/glassy-ui.json"));

        stage = new Stage();
        viewport = new StretchViewport(800, 480, stage.getCamera());
        stage.setViewport(viewport);


// tells our asset manger that we want to load the images set in loadImages method
        parent.assetsManager.loadImages();
// tells the asset manager to load the images and wait until finished loading.
        parent.assetsManager.aManager.finishLoading();
// gets the images as a texture
        knightTex = parent.assetsManager.aManager.get("knight.png");



    }

    @Override
    public void show() {
        //take number of points from DB
        //here is just random number
        int pointsNumber = PlayerAccount.getProfilePoints();
        if (pointsNumber == -1) {
            SelectDialog("Please First Select Character From List");
        }
        stage.clear();
//        stage.setDebugAll(true);
        float pad = 5;

        // Character Sprite
        Texture texture = null;
        try {
            texture = PlayerAccount.getProfileTexture();
        } catch (IOException e) {
            texture = new Texture("default.png");
            e.printStackTrace();
        } catch (JSONException e) {
            texture = new Texture("default.png");
            e.printStackTrace();
        }
        Image image = new Image(texture);
        //create text buttons and give them listeners
        TextButton earnPointsButton = new TextButton("Earn Points", skin);
        TextButton teamMembersButton = new TextButton("Team Members", skin);
        TextButton lastBattleButton = new TextButton("Last battle", skin);
        TextButton manageTeamButton = new  TextButton("Manage Team", skin);
        TextButton settingsButton = new TextButton("Settings", skin);
        TextButton backButton = new TextButton("Back", skin);

        ImageButton imageButton = null;

        Table imageTable = new Table();
        Table screenTable = new Table();

        earnPointsButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor){
                parent.changeScreen(parent.getEarnPoints());
            }
        });

        teamMembersButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor){
                parent.changeScreen(parent.getTeamMembers());
            }
        });

        lastBattleButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor){
        //        parent.changeScreen(parent.getLastBattle());
            }
        });

        manageTeamButton.addListener(new ManageTeamButton());

        settingsButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor){
                parent.changeScreen(parent.getSettings());
            }
        });

        backButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor){
                parent.changeScreen(parent.getCharacterSelect());
            }
        });

        //Create label witch represents points
        Label pointsLabel = new Label("Points: " + pointsNumber, skin);

        // add item list
        ArrayList<Integer> numberOfItems = new ArrayList<Integer>();

        //here should go i< activities and activities should be taken from Data Base
        for (int i = 1; i <13; i++) {
            numberOfItems.add(i);
        }

        /*
        for (int i = 0; i < 10; i++) {
                numberOfActivities.add("Activity: " + activity);
            }
        */


        //create and fill table with buttons and labels
        itemTable = new Table();
        itemTable.add(earnPointsButton).fill().expand();
        itemTable.add(teamMembersButton).fill().expand();
        itemTable.add(lastBattleButton).fill().expand();
        itemTable.row();
        itemTable.add(pointsLabel).fill().expand().padLeft(pad*2);
        itemTable.row();

        for (int i = 1; i<5; i++) {
            int x = 0;
            if(i == 1){
                for(int e = 0; e < 3; e++){
                    imageButton =  new ImageButton((addImage("store_items/sword_" + (i+e) + ".png")));
                    imageButton.addListener(new ClickButton((i+e) + "_sword", 1 + e));
                    imageTable.add(imageButton).fill().expand().size(80, 80);//.pad(pad, pad, pad, pad);
                }
                imageTable.row();
            }else if(i ==2) {
                for(int e = 0; e < 3; e++){
                    imageButton =  new ImageButton((addImage("store_items/shield_" + ((i-1)+e) + ".png")));
                    imageButton.addListener(new ClickButton(((i-1)+e) + "_shield", 4 + e));
                    imageTable.add(imageButton).fill().expand().size(80, 80);//.pad(pad, pad, pad, pad);
                }
                imageTable.row();
            }else if(i == 3){
                for(int e = 0; e < 3; e++) {
                    imageButton = new ImageButton((addImage("store_items/armor_" + ((i-2)+e) + ".png")));
                    imageButton.addListener(new ClickButton((i-2)+e + "_armor", 7 + e));
                    imageTable.add(imageButton).fill().expand().size(80, 80);//.pad(pad, pad, pad, pad);
                }
                imageTable.row();
            }else{
                for(int e = 0; e < 3; e++){
                    imageButton =  new ImageButton((addImage("store_items/leggins_" + ((i-3)+e) + ".png")));
                    imageButton.addListener(new ClickButton( (i-3)+e + "_leggins", 10 + e));
                    imageTable.add(imageButton).fill().expand().size(80, 80);//.pad(pad, pad, pad, pad);
                    x = e;
                }
                imageTable.row();
            }
            if(imageButton == null){
                imageButton = new ImageButton((addImage("store_items/sword_default.png")));
            }
            //Set the button up
            //add listener to image button, so item will replace already equipped item
            imageButton.addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    //here should go Yes No pop up screen
                }
            });


        }
        //here should go if statement, if user is admin of team
        //if{ }
        itemTable.add(imageTable).fill().expand().colspan(3);
        itemTable.row();
        itemTable.add(settingsButton).fill().expand();
        itemTable.add(backButton).fill().expand();
        itemTable.add(manageTeamButton).fill().expand();

        screenTable.setFillParent(true);
        screenTable.add(image).fill().expand().uniform().pad(pad, pad, pad, pad / 2);
        screenTable.add(itemTable).fill().expand().uniform().pad(pad, pad / 2, pad, pad);
        stage.addActor(screenTable);

        Gdx.input.setInputProcessor(stage);
    }

    public void     SelectDialog(String message) {
        final GDXButtonDialog bDialog = dialogs.newDialog(GDXButtonDialog.class);
        bDialog.setTitle("Error");
        bDialog.setMessage(message);
        bDialog.addButton("Ok");

        bDialog.build().show();
    }

    public TextureRegionDrawable addImage(String imagePath){



        textureImage = new Texture(Gdx.files.internal(imagePath));
        textureRegion = new TextureRegion(textureImage);
        textureRegionDrawable = new TextureRegionDrawable(textureRegion);
        return textureRegionDrawable;
    }


    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0f, 0f, 0f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        // tell our stage to do actions and draw itself
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width,height,true);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        stage.dispose();
    }

    class ManageTeamButton extends ChangeListener{

        @Override
        public void changed(ChangeEvent changeEvent, Actor actor) {
            if(PlayerAccount.isAdmin()){
                parent.changeScreen(parent.getAdmin());
            }else{
                manageRefuse();
            }
        }

        private void manageRefuse(){
            final GDXButtonDialog bDialog = manageTeamDialog.newDialog(GDXButtonDialog.class);
            bDialog.setTitle("Nah bro");
            bDialog.setMessage("You are not team admin!");
            bDialog.addButton("Back");
            bDialog.build().show();
        }
    }
    class ClickButton extends ChangeListener{
        String  itemType;
        int     itemId;


        public void     confirmDialog() {
            final GDXButtonDialog bDialog = buyItemDialog.newDialog(GDXButtonDialog.class);
            bDialog.setTitle("Confirmation");
            bDialog.setMessage("Are you sure you want to buy \"" + itemType.replace('_', ' ') + "\"");

            bDialog.setClickListener(new ButtonClickListener() {

                @Override
                public void click(int button) {
                    if (button == YES) {
                        try {
                            if (PlayerAccount.buyItem(itemId) == false)
                                DialogBox.showInfoDialog("Error", JsonHandler.errorMessage);
                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        show();
                    }
                }
            });

            bDialog.addButton("Ok");
            bDialog.addButton("Cancel");

            bDialog.build().show();
        }

        public ClickButton(String name, int id) {
            name = name.replaceFirst("1", "iron");
            name = name.replaceFirst("2", "steel");
            name = name.replaceFirst("3", "diamond");
            this.itemType = name;
            this.itemId = id;
        }

        @Override
        public void changed(ChangeEvent changeEvent, Actor actor) {
            int status;

            status = PlayerAccount.getItemStatus(itemId);
            if (status == Profile.STORE_ITEM) {
                confirmDialog();
            }

        }
    }
}

