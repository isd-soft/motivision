package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.gameSets.GGame;
import com.mygdx.game.music.GameSounds;
import com.mygdx.game.requests.Item;
import com.mygdx.game.requests.JsonHandler;
import com.mygdx.game.requests.PlayerAccount;

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
    private ShopAnimation shopAnimation;

    private GDXDialogs selectDialog;
    private GDXDialogs manageTeamDialog;
    private GDXDialogs buyItemDialog;

    private Texture knightTex;
    private GameSounds gameSounds;
    private Viewport viewport;
    private Camera camera;
    private Music loginMusic;

    private Label volumeMusicLabel;
    private Label volumeSoundLabel;
    private Label musicOnOffLabel;
    private Label soundOnOffLabel;

    public CharacterProfileScreen(GGame g) {
        parent = g;
        selectDialog = GDXDialogsSystem.install();
        manageTeamDialog = GDXDialogsSystem.install();
        buyItemDialog = GDXDialogsSystem.install();
        gameSounds = new GameSounds(g);
        skin = new Skin(Gdx.files.internal("skin2/clean-crispy-ui.json"));
        shopAnimation = new ShopAnimation(parent);
        stage = new Stage();
        viewport = new StretchViewport(800, 480, stage.getCamera());
        stage.setViewport(viewport);

    }

    @Override
    public void show() {
        //take number of points from DB
        //here is just random number
        int pointsNumber = PlayerAccount.getProfilePoints();
        String teamName = PlayerAccount.getTeamName();
        String characterName = PlayerAccount.getProfileName();

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
                gameSounds.clickSound();
                parent.changeScreen(parent.getEarnPoints());
            }
        });

        teamMembersButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor){
                gameSounds.clickSound();
                parent.changeScreen(parent.getTeamMembers());
            }
        });

        lastBattleButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor){
                gameSounds.clickSound();
                //        parent.changeScreen(parent.getLastBattle());
            }
        });

        manageTeamButton.addListener(new ManageTeamButton());

        //music volume
        final Slider volumeMusicSlider = new Slider( 0f, 1f, 0.1f,false, skin );
        volumeMusicSlider.setValue(parent.getPreferences().getMusicVolume());
        volumeMusicSlider.addListener( new EventListener() {
            @Override
            public boolean handle(Event event) {
                parent.getPreferences().setMusicVolume( volumeMusicSlider.getValue() );
                return false;
            }
        });
        //sound volume
        final Slider volumeSoundSlider = new Slider( 0f, 1f, 0.1f,false, skin );
        volumeSoundSlider.setValue( parent.getPreferences().getSoundVolume());
        volumeSoundSlider.addListener( new EventListener() {
            @Override
            public boolean handle(Event event) {
                parent.getPreferences().setSoundVolume(volumeSoundSlider.getValue());
                return false;
            }
        });



        //music
        final CheckBox musicCheckbox = new CheckBox(null, skin);
        musicCheckbox.setChecked( parent.getPreferences().isMusicEnabled() );
        musicCheckbox.addListener( new EventListener() {
            @Override
            public boolean handle(Event event) {
                boolean enabled = musicCheckbox.isChecked();
                parent.getPreferences().setMusicEnabled( enabled );
                return false;
            }
        });
        //sound
        final CheckBox soundCheckbox = new CheckBox(null, skin );
        soundCheckbox.setChecked( parent.getPreferences().isSoundEnabled() );
        soundCheckbox.addListener( new EventListener() {
            @Override
            public boolean handle(Event event) {
                boolean enabled = soundCheckbox.isChecked();
                parent.getPreferences().setSoundEnabled( enabled );
                return false;
            }
        });

        //return to main screen
        final TextButton back = new TextButton("Back", skin);
        back.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                parent.changeScreen(parent.getBackFromSettings());
            }
        });

        //making labels
        volumeMusicLabel = new Label( "Music Volume", skin );
        volumeSoundLabel = new Label( "Sound Volume", skin  );
        musicOnOffLabel = new Label( "Music Effect", skin  );
        soundOnOffLabel = new Label( "Sound Effect", skin  );

        settingsButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor){
                //parent.setBackFromSettings(6);

                Dialog dialog = new Dialog("Settings", skin) {
                    public void result(Object obj) {
                        System.out.println("result "+obj);
                    }
                };
                dialog.getContentTable().row();
                dialog.getContentTable().add(volumeMusicLabel);
                dialog.getContentTable().add(volumeMusicSlider);
                dialog.getContentTable().row();
                dialog.getContentTable().add(musicOnOffLabel);
                dialog.getContentTable().add(musicCheckbox);
                dialog.getContentTable().row();
                dialog.getContentTable().add(volumeSoundLabel);
                dialog.getContentTable().add(volumeSoundSlider);
                dialog.getContentTable().row();
                dialog.getContentTable().add(soundOnOffLabel);
                dialog.getContentTable().add(soundCheckbox);
                dialog.getContentTable().row();
                dialog.button("back", "back");
                dialog.show(stage);
                //parent.changeScreen(parent.getSettings());
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
        Label teamLabel = new Label(" Team: " + teamName, skin);
        Label characterLabel = new Label(" Character: " + characterName, skin);

        // add item list
        ArrayList<Integer> numberOfItems = new ArrayList<Integer>();

        //here should go i< activities and activities should be taken from Data Base
        for (int i = 1; i <13; i++) {
            numberOfItems.add(i);
        }

        //create and fill table with buttons and labels
        itemTable = new Table();
        Table upButtonsTable = new Table();
        upButtonsTable.add(earnPointsButton).fill().expandX();
        upButtonsTable.add(teamMembersButton).fill().expandX();
        upButtonsTable.add(lastBattleButton).fill().expandX();

        itemTable.add(upButtonsTable).fill().expandX().colspan(2);
        itemTable.row();
        itemTable.add(characterLabel).colspan(2);
        itemTable.row();
        itemTable.add(pointsLabel);
        itemTable.add(teamLabel);
        itemTable.row();

        for (int i = 1; i<5; i++) {
            int x = 0;
            if(i == 1){
                for(int e = 0; e < 3; e++){
                    imageButton =  new ImageButton((addImage("store_items/sword_" + (i+e) + ".png", 1 + e)));
                    imageButton.addListener(new ClickButton((i+e) + "_sword", 1 + e));
                    imageTable.add(imageButton).fill().expand().size(80, 80);//.pad(pad, pad, pad, pad);
                }
                imageTable.row();
            }else if(i ==2) {
                for(int e = 0; e < 3; e++){
                    imageButton =  new ImageButton((addImage("store_items/shield_" + ((i-1)+e) + ".png", 4 + e)));
                    imageButton.addListener(new ClickButton(((i-1)+e) + "_shield", 4 + e));
                    imageTable.add(imageButton).fill().expand().size(80, 80);//.pad(pad, pad, pad, pad);
                }
                imageTable.row();
            }else if(i == 3){
                for(int e = 0; e < 3; e++) {
                    imageButton = new ImageButton((addImage("store_items/armor_" + ((i-2)+e) + ".png", 7 + e)));
                    imageButton.addListener(new ClickButton((i-2)+e + "_armor", 7 + e));
                    imageTable.add(imageButton).fill().expand().size(80, 80);//.pad(pad, pad, pad, pad);
                }
                imageTable.row();
            }else{
                for(int e = 0; e < 3; e++){
                    imageButton =  new ImageButton((addImage("store_items/leggins_" + ((i-3)+e) + ".png", 10 + e)));
                    imageButton.addListener(new ClickButton( (i-3)+e + "_leggins", 10 + e));
                    imageTable.add(imageButton).fill().expand().size(80, 80);//.pad(pad, pad, pad, pad);
                    x = e;
                }
                imageTable.row();
            }
            if(imageButton == null){
                imageButton = new ImageButton((addImage("store_items/sword_default.png", -1)));
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
        itemTable.add(imageTable).fill().expand().colspan(2);
        itemTable.row();

        Table botButtonTable = new Table();
        botButtonTable.add(backButton).fill().expandX();
        botButtonTable.add(settingsButton).fill().expandX();
        if(PlayerAccount.isAdmin())
            botButtonTable.add(manageTeamButton).fill().expandX();

        itemTable.add(botButtonTable).fill().expandX().colspan(2);

        shopAnimation.setFillParent(true);
        shopAnimation.setZIndex(0);
        screenTable.addActor(shopAnimation);
        screenTable.setFillParent(true);
        screenTable.add(image).fill().expand().uniform().pad(pad, pad, pad, pad / 2);
        screenTable.add(itemTable).fill().expand().uniform().pad(pad, pad / 2, pad, pad);
        stage.addActor(screenTable);

//        stage.setDebugAll(true);

        Gdx.input.setInputProcessor(stage);
    }

    public void     SelectDialog(String message) {
        final GDXButtonDialog bDialog = selectDialog.newDialog(GDXButtonDialog.class);
        bDialog.setTitle("Error");
        bDialog.setMessage(message);
        bDialog.addButton("Ok");

        bDialog.build().show();
    }

    public TextureRegionDrawable addImage(String imagePath, int itemId) {
        Pixmap  pixmap;

        pixmap = new Pixmap(Gdx.files.internal(imagePath));
        if (itemId != -1) {
            try {
                pixmap = PlayerAccount.addProfileStatusOnImage(pixmap, itemId);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        textureImage = new Texture(pixmap);
        textureRegion = new TextureRegion(textureImage);
        textureRegionDrawable = new TextureRegionDrawable(textureRegion);
        pixmap.dispose();
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
            gameSounds.clickSound();
            if(PlayerAccount.isAdmin()){
                parent.changeScreen(parent.getAdmin());
            }else{
                manageRefuse();
            }
        }

        private void manageRefuse(){
            final GDXButtonDialog bDialog = manageTeamDialog.newDialog(GDXButtonDialog.class);
            bDialog.setTitle("Nah bro");
            bDialog.setClickListener(new ButtonClickListener() {
                @Override
                public void click(int button) {
                    gameSounds.clickSound();
                }
            });
            bDialog.setMessage("You are not team admin!");
            bDialog.addButton("Back");
            bDialog.build().show();
        }
    }
    class ClickButton extends ChangeListener{
        String  itemType;
        int     itemId;


        public void     confirmDialog() {
            gameSounds.clickSound();
            final GDXButtonDialog bDialog = buyItemDialog.newDialog(GDXButtonDialog.class);
            bDialog.setTitle("Confirmation");
            bDialog.setMessage("Are you sure you want to buy \"" + itemType.replace('_', ' ') + "\"");

            bDialog.setClickListener(new ButtonClickListener() {

                @Override
                public void click(int button) {
                    if (button == YES) {
                        try {
                            if (!PlayerAccount.buyItem(itemId)) {
                                gameSounds.deniedSound();
                                DialogBox.showInfoDialog("Error", JsonHandler.errorMessage);
                            }
                            else
                               gameSounds.buySound();
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
            if (status == Item.STORE_ITEM) {
                confirmDialog();
            }
            else if (status == Item.EQUIPPED_ITEM) {
                PlayerAccount.unequipItem(itemId);
                gameSounds.itemSound();
            }
            else if (status == Item.UNEQUIPPED_ITEM) {
                System.out.println("Start equipping " + itemType + "(" + itemId + ")");
                PlayerAccount.equipItem(itemId);
                gameSounds.itemSound();
            }
            show();
        }
    }
}

