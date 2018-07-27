package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
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
    private static final int YES = 0;
    private static final int CANCEL = 1;
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

    private CharacterAnimation animation;
    private ScrollPane scrollPane;
    private Texture knightTex;
    private GameSounds gameSounds = GameSounds.getInstance();
    private Viewport viewport;
    private Camera camera;
    private Music loginMusic;
    private Drawable profileImage;

    private LastBattleScreen lastBattleScreen;

    private SettingsPopup settingsPopup;

    public CharacterProfileScreen(GGame g) {
        parent = g;
        selectDialog = GDXDialogsSystem.install();
        animation = new CharacterAnimation();
        animation.init("IDLE");
        animation.setZIndex(10);
        manageTeamDialog = GDXDialogsSystem.install();
        buyItemDialog = GDXDialogsSystem.install();
        skin = new Skin(Gdx.files.internal("skin2/clean-crispy-ui.json"));
        //shopAnimation = new ShopAnimation(parent);
        stage = new Stage();
        viewport = new StretchViewport(800, 480, stage.getCamera());
        stage.setViewport(viewport);
        settingsPopup = new SettingsPopup();

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

        Gdx.input.setCatchBackKey(true);
        stage.addListener(new InputListener() {
            @Override
            public boolean keyUp(InputEvent event, int keycode) {
//                System.out.println("new keycode = " + keycode);
                if (keycode == Input.Keys.BACK /*|| keycode == Input.Keys.TAB*/) {
                    parent.changeScreen(parent.getCharacterSelect());
                }
                return false;
            }
        });

        // Character Sprite
        /*Texture texture = null;
        try {
            texture = PlayerAccount.getProfileTexture();
        } catch (IOException e) {
            texture = new Texture("default.png");
            e.printStackTrace();
        } catch (JSONException e) {
            texture = new Texture("default.png");
            e.printStackTrace();
        }*/
        //profileImage = new TextureRegionDrawable(new TextureRegion(texture));
        //create text buttons and give them listeners
        TextButton earnPointsButton = new TextButton("Earn Points", skin);
        TextButton teamMembersButton = new TextButton("Team Members", skin);
        final TextButton lastBattleButton = new TextButton("Last battle", skin);
        TextButton manageTeamButton = new TextButton("Manage Team", skin);
        TextButton settingsButton = new TextButton("Settings", skin);
        TextButton backButton = new TextButton("Back", skin);

        Button imageButton = null;

        Table imageTable = new Table();
        Table screenTable = new Table();

        earnPointsButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                gameSounds.clickSound();

                parent.changeScreen(parent.getEarnPoints());
            }
        });

        teamMembersButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                gameSounds.clickSound();
                parent.changeScreen(parent.getTeamMembers());
            }
        });

        lastBattleButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                gameSounds.clickSound();
                //parent.changeScreen(parent.getLastBattle());

            }
        });

        manageTeamButton.addListener(new ManageTeamButton());


        settingsButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                settingsPopup.show(stage);
            }
        });


        backButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                gameSounds.clickSound();
                parent.changeScreen(parent.getCharacterSelect());
            }
        });

        //Create label witch represents points
        Label pointsLabel = new Label("Points: " + pointsNumber, skin);
        pointsLabel.setAlignment(Align.center);
        Label teamLabel = new Label(" Team: " + teamName, skin);
        teamLabel.setAlignment(Align.center);
        Label characterLabel = new Label(" Character: " + characterName, skin);
        characterLabel.setAlignment(Align.center);

        // add item list
        ArrayList<Integer> numberOfItems = new ArrayList<Integer>();

        //here should go i< activities and activities should be taken from Data Base
        for (int i = 1; i < 13; i++) {
            numberOfItems.add(i);
        }

        //create and fill table with buttons and labels
        Label priceLabel;
        Table priceTable;
        Stack stack;
        ArrayList<Item> items = PlayerAccount.getStoreItems();
        int i = 0;
        for (Item item : items) {
            i++;
            imageButton = new Button(skin);
            imageButton.addListener(new ClickButton(item.getName(), item.getId()));

            stack = new Stack();
            stack.add(addImage("store_items/" + item.getImagePath() + ".png", item.getId()));

            int status = PlayerAccount.getItemStatus(item.getId());
            if (status == Item.STORE_ITEM) {
                priceLabel = new Label("" + item.getPrice(), skin, "price");
                priceLabel.setAlignment(Align.bottomRight);
                priceTable = new Table();
                priceTable.add(priceLabel).bottom().right().expand();
                stack.add(priceTable);
            }

            imageButton.add(stack);
            imageTable.add(imageButton).fill().expand().size(80, 80);//.pad(pad, pad, pad, pad);
            if (imageButton == null) {
                imageButton = new ImageButton(skin);
//                (addImage("store_items/sword_default.png", -1))
            }
            if(i%3==0)
                imageTable.row();

        }

        itemTable = new Table();
        Table upButtonsTable = new Table();
        upButtonsTable.add(earnPointsButton).fill().expandX();
        upButtonsTable.add(teamMembersButton).fill().expandX();
        //upButtonsTable.add(lastBattleButton).fill().expandX();

        scrollPane = new ScrollPane(imageTable);
        itemTable.add(upButtonsTable).fill().expandX();
        itemTable.row();
        itemTable.add(pointsLabel);
        itemTable.row();
        itemTable.add(scrollPane).fill().expand();
        itemTable.row();

        Table botButtonTable = new Table();
        botButtonTable.add(backButton).fill().expandX();
        botButtonTable.add(settingsButton).fill().expandX();
        if (PlayerAccount.isAdmin())
            botButtonTable.add(manageTeamButton).fill().expandX();

        itemTable.add(botButtonTable).fill().expandX();

        Table leftTable = new Table();
        leftTable.add(teamLabel).expandX();
        leftTable.row();
        leftTable.add(characterLabel).expandX();
        leftTable.row();
        leftTable.add(animation).expand();

        screenTable.setFillParent(true);
        screenTable.add(leftTable).fill().expand().uniform().pad(pad, pad, pad, pad / 2);
        screenTable.add(itemTable).fill().expand().uniform().pad(pad, pad / 2, pad, pad);
        stage.addActor(screenTable);
        Gdx.input.setInputProcessor(stage);
    }

    public void SelectDialog(String message) {
        final GDXButtonDialog bDialog = selectDialog.newDialog(GDXButtonDialog.class);
        bDialog.setTitle("Error");
        bDialog.setMessage(message);
        bDialog.addButton("Ok");

        bDialog.build().show();
    }

    public Image addImage(String imagePath, int itemId) {
        Pixmap pixmap;

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
//        textureRegion = new TextureRegion(textureImage);
//        textureRegionDrawable = new TextureRegionDrawable(textureRegion);
        pixmap.dispose();
//        return textureRegionDrawable;
        return new Image(textureImage);
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
        stage.getViewport().update(width, height, true);
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

    class ManageTeamButton extends ChangeListener {

        @Override
        public void changed(ChangeEvent changeEvent, Actor actor) {
            gameSounds.clickSound();
            if (PlayerAccount.isAdmin()) {
                parent.changeScreen(parent.getAdmin());
            } else {
                manageRefuse();
            }
        }

        private void manageRefuse() {
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

    class ClickButton extends ChangeListener {
        String itemType;
        int itemId;


        public void confirmDialog() {
            gameSounds.clickSound();

            final TextButton buyButton = new TextButton("buy", skin);
            final Label buyLabel = new Label("Buy \"" + itemType.replace('_', ' ') + "\" ?", skin, "big");
            Dialog dialog = new Dialog("Confirmation", skin) {
                public void result(Object obj) {
                    gameSounds.clickSound();
                    if (obj == "yes") {
                        try {
                            int status;
                            status = PlayerAccount.getItemStatus(itemId);
                            if (status == Item.STORE_ITEM) {
                                if (!PlayerAccount.buyItem(itemId)) {
                                    gameSounds.deniedSound();
                                    denyDialog();
                                    //buyLabel.setText("Not enough points");
                                } else {
                                    gameSounds.buySound();
                                    CharacterProfileScreen.this.show();
                                    //buyLabel.setText("Nice, go back");
                                }
                            } else {
                                //buyLabel.setText("Already bought!");
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                }
            };
            dialog.getContentTable().row();
            dialog.getContentTable().add(buyLabel);
            dialog.getContentTable().row();
            //dialog.getContentTable().add(buyButton);
            dialog.button("Yes", "yes");
            dialog.button("No", "no");
            dialog.show(stage);

            buyButton.addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    try {
                        int status;
                        status = PlayerAccount.getItemStatus(itemId);
                        if (status == Item.STORE_ITEM) {
                            if (!PlayerAccount.buyItem(itemId)) {
                                gameSounds.deniedSound();
                                denyDialog();
                                //buyLabel.setText("Not enough points");
                            } else {
                                gameSounds.buySound();
                                buyLabel.setText("Nice, go back");
                            }
                        } else {
                            buyLabel.setText("Already bought!");
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
            /*final GDXButtonDialog bDialog = buyItemDialog.newDialog(GDXButtonDialog.class);
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

            bDialog.build().show();*/
        }

        public void denyDialog() {
            gameSounds.clickSound();

            final TextButton okButton = new TextButton("Ok", skin);
            final Label buyLabel = new Label("Not enough points!", skin, "big");
            Dialog dialog = new Dialog("Denied", skin) {
                public void result(Object obj) {
                    gameSounds.clickSound();
                }
            };
            dialog.getContentTable().row();
            dialog.getContentTable().add(buyLabel);
            dialog.getContentTable().row();
            //dialog.getContentTable().add(okButton);
            dialog.button("ok", "ok");
            dialog.show(stage);
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
            } else if (status == Item.EQUIPPED_ITEM) {
                PlayerAccount.unequipItem(itemId);
                gameSounds.itemSound();
                CharacterProfileScreen.this.show();
            } else if (status == Item.UNEQUIPPED_ITEM) {
                System.out.println("Start equipping " + itemType + "(" + itemId + ")");
                PlayerAccount.equipItem(itemId);
                gameSounds.itemSound();
                CharacterProfileScreen.this.show();
            }
        }
    }
}

