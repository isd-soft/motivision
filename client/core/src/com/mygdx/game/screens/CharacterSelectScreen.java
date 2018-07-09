package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.GameSets.GGame;
import com.mygdx.game.requests.PlayerAccount;

import org.json.JSONException;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class CharacterSelectScreen implements Screen {
    private GGame parent;
    private Stage stage;
    private Skin skin;

    ScrollPane scrollPane;
    private Viewport viewport;
    private Camera camera;

    public CharacterSelectScreen(GGame g) {
        parent = g;
        stage = new Stage();
        viewport = new StretchViewport(800, 480, stage.getCamera());
        stage.setViewport(viewport);
    }


    public Texture splitImages() throws IOException {
        int width;
        int height;
        BufferedImage result = new BufferedImage(
                466, 510, //work these out
                BufferedImage.TYPE_INT_RGB);
        Graphics g = result.getGraphics();

        BufferedImage bi;


        bi = ImageIO.read(new File("knight_3.png"));
        g.drawImage(bi, 0, 0, null);

        bi = ImageIO.read(new File("items/default_leggins.png"));
        g.drawImage(bi, 30, 353, null);

        bi = ImageIO.read(new File("items/default_armor.png"));
        g.drawImage(bi, 0, 188, null);

        bi = ImageIO.read(new File("items/default_sword.png"));
        g.drawImage(bi, 203, 165, null);


        bi = ImageIO.read(new File("items/default_fingers.png"));
        g.drawImage(bi, 219, 304, null);


//        bi = ImageIO.read(new File("items/default_shield.png"));
//        g.drawImage(bi, 17, 320, null);

        ImageIO.write(result, "png", new File("result.png"));

        return new Texture("result.png");
    }

    @Override
    public void show() {
        stage.clear();
        skin = new Skin(Gdx.files.internal("skin/glassy-ui.json"));

        float gameWidth = Gdx.graphics.getWidth();
        float gameHeight = Gdx.graphics.getHeight();
        float pad = 5;

        // add the character image
//        Texture texture = new Texture("default.png");
        Texture texture = null;
        try {
            texture = PlayerAccount.getProfileTexture("Vasea");
        } catch (IOException e) {
            e.printStackTrace();
            return;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (texture == null) {
            texture = new Texture("default.png");
        }
        Image image = new Image(texture);

        // remove and add buttons
        TextButton create = new TextButton("Create new +", skin, "square");
        TextButton logout = new TextButton("Logout", skin, "small");

        // add the list of already created characters
        ArrayList<String> strings = new ArrayList<String>();
        ArrayList<String> characterNames = PlayerAccount.getCharactersName();
        if (characterNames != null)
            strings = PlayerAccount.getCharactersName();
        else
            strings.add("No Characters");

        Table list = new Table();

        ArrayList<TextButton> characterNamesButtons = new ArrayList<TextButton>();
        ArrayList<TextButton> xButtons = new ArrayList<TextButton>();

        for (int i = 0; i < strings.size(); i++) {
            characterNamesButtons.add(new TextButton(strings.get(i), skin, "square"));
            xButtons.add(new TextButton("X", skin, "square"));

            list.add(characterNamesButtons.get(i)).fillX().expandX();
            list.add(xButtons.get(i)).fillX();
            list.row();

            characterNamesButtons.get(i).addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    System.out.println("Selected character " + actor.getName());
                }
            });

            xButtons.get(i).addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    System.out.println("Deleted character " + actor.getName());
                }
            });
        }
        list.add(create).fill().uniformY().colspan(2);

        scrollPane = new ScrollPane(list);
        scrollPane.setSmoothScrolling(false);
        scrollPane.setScrollingDisabled(true, false);
        scrollPane.setScrollbarsOnTop(true);

        // add the list and buttons table
        Table buttonTable = new Table();
        buttonTable.add(logout).fill().pad(0, 0, pad / 2, 0);
        buttonTable.row();
        buttonTable.add(scrollPane).fillX().expand().top().pad(pad / 2, 0, 0, 0);

        // add wrapper table
        Table screen = new Table();
        screen.setFillParent(true);
        screen.add(image).fill().uniform().pad(pad, pad, pad, pad / 2);
        screen.add(buttonTable).fill().uniform().pad(pad, pad / 2, pad, pad);

        stage.addActor(screen);
        stage.setDebugAll(true);

        // add event listeners
        logout.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                PlayerAccount.logOut();
                parent.changeScreen(parent.getLogin());
            }
        });

        create.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                //parent.changeScreen(parent.getMenu()); //go to create character screen
            }
        });

        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        //camera.update();
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


    class SelectCharacter extends ChangeListener {
        String name;

        public SelectCharacter(String elem) {
            this.name = elem;
        }

        @Override
        public void changed(ChangeListener.ChangeEvent event, Actor actor) {
            try {
                PlayerAccount.getProfileTexture(name);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
