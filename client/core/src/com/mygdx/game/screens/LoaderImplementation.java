package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.PixmapPacker;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.brashmonkey.spriter.Data;
import com.brashmonkey.spriter.FileReference;
import com.brashmonkey.spriter.Loader;
import com.mygdx.game.loader.AssetsManager;
import com.mygdx.game.requests.PlayerAccount;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Set;

public class LoaderImplementation extends Loader<Sprite> implements Disposable {

    public AssetsManager assetsManager = AssetsManager.getInstance();
    public HashMap<String, Sprite> ourResources = new HashMap<String, Sprite>();
    private ArrayList<Sprite> heads = new ArrayList<Sprite>(15);
    public static int standardAtlasWidth = 2048, standardAtlasHeight = 2048;

    private PixmapPacker packer;
    private HashMap<FileReference, Pixmap> pixmaps;
    private HashMap<Pixmap, Boolean> pixmapsToDispose;
    private boolean pack;
    private int atlasWidth, atlasHeight;
    private TextureFilter textureFilter;

    public LoaderImplementation(Data data) {
        this(data, true);
    }

    public LoaderImplementation(Data data, boolean pack) {
        this(data, standardAtlasWidth, standardAtlasHeight);
        this.pack = pack;
    }

    public LoaderImplementation(Data data, int atlasWidth, int atlasHeight) {
        super(data);
        this.pack = true;
        this.atlasWidth = atlasWidth;
        this.atlasHeight = atlasHeight;
        this.pixmaps = new HashMap<FileReference, Pixmap>();
        this.pixmapsToDispose = new HashMap<Pixmap, Boolean>();
        this.textureFilter = TextureFilter.Linear;
    }

    public void setTextureFilter(TextureFilter textureFilter) {
        this.textureFilter = textureFilter;
    }

    @Override
    protected Sprite loadResource(FileReference ref) {

        FileHandle f;
        String pathPrefix;
        if (super.root == null || super.root.equals("")) {
            pathPrefix = "";
        } else {
            pathPrefix = super.root + File.separator;
        }
        String path = pathPrefix + data.getFile(ref).name;
        switch (Gdx.app.getType()) {
            case iOS:
                f = Gdx.files.absolute(path);
                break;
            default:
                f = Gdx.files.internal(path);
                break;
        }

        if (!f.exists())
            throw new GdxRuntimeException("Could not find file handle " + path + "! Please check your paths.");
        if (this.packer == null && this.pack)
            this.packer = new PixmapPacker(this.atlasWidth, this.atlasHeight, Pixmap.Format.RGBA8888, 2, true);
        final Pixmap pix = new Pixmap(f);
        this.pixmaps.put(ref, pix);
        return null;
    }


    public Sprite get(String spriteName) {
        return ourResources.get(spriteName);
    }

    public Sprite getHead(int i){
        return heads.get(i-1);
    }


    protected void loadRecourse() {
        ourResources.put("armor_1", new Sprite((Texture) assetsManager.aManager.get("Body/body_1.png")));
        ourResources.put("armor_2", new Sprite((Texture) assetsManager.aManager.get("Body/body_2.png")));
        ourResources.put("armor_3", new Sprite((Texture) assetsManager.aManager.get("Body/body_3.png")));
        ourResources.put("armor_4", new Sprite((Texture) assetsManager.aManager.get("Body/body_4.png")));

        ourResources.put("left_arm_1", new Sprite((Texture) assetsManager.aManager.get("LeftArm/left_arm_1.png")));
        ourResources.put("left_arm_2", new Sprite((Texture) assetsManager.aManager.get("LeftArm/left_arm_2.png")));
        ourResources.put("left_arm_3", new Sprite((Texture) assetsManager.aManager.get("LeftArm/left_arm_3.png")));
        ourResources.put("left_arm_4", new Sprite((Texture) assetsManager.aManager.get("LeftArm/left_arm_4.png")));

        ourResources.put("right_arm_1", new Sprite((Texture) assetsManager.aManager.get("RightArm/right_arm_1.png")));
        ourResources.put("right_arm_2", new Sprite((Texture) assetsManager.aManager.get("RightArm/right_arm_2.png")));
        ourResources.put("right_arm_3", new Sprite((Texture) assetsManager.aManager.get("RightArm/right_arm_3.png")));
        ourResources.put("right_arm_4", new Sprite((Texture) assetsManager.aManager.get("RightArm/right_arm_4.png")));

        ourResources.put("left_leg_1", new Sprite((Texture) assetsManager.aManager.get("LeftLeg/left_leg_1.png")));
        ourResources.put("left_leg_2", new Sprite((Texture) assetsManager.aManager.get("LeftLeg/left_leg_2.png")));
        ourResources.put("left_leg_3", new Sprite((Texture) assetsManager.aManager.get("LeftLeg/left_leg_3.png")));
        ourResources.put("left_leg_4", new Sprite((Texture) assetsManager.aManager.get("LeftLeg/left_leg_4.png")));

        ourResources.put("right_leg_1", new Sprite((Texture) assetsManager.aManager.get("RightLeg/right_leg_1.png")));
        ourResources.put("right_leg_2", new Sprite((Texture) assetsManager.aManager.get("RightLeg/right_leg_2.png")));
        ourResources.put("right_leg_3", new Sprite((Texture) assetsManager.aManager.get("RightLeg/right_leg_3.png")));
        ourResources.put("right_leg_4", new Sprite((Texture) assetsManager.aManager.get("RightLeg/right_leg_4.png")));

        ourResources.put("shield_1", new Sprite((Texture) assetsManager.aManager.get("Shield/shield_1.png")));
        ourResources.put("shield_2", new Sprite((Texture) assetsManager.aManager.get("Shield/shield_2.png")));
        ourResources.put("shield_3", new Sprite((Texture) assetsManager.aManager.get("Shield/shield_3.png")));
        ourResources.put("shield_4", new Sprite((Texture) assetsManager.aManager.get("Shield/shield_4.png")));
        ourResources.put("shield_5", new Sprite((Texture) assetsManager.aManager.get("Shield/shield_5.png")));
        ourResources.put("shield_6", new Sprite((Texture) assetsManager.aManager.get("Shield/shield_6.png")));
        ourResources.put("shield_7", new Sprite((Texture) assetsManager.aManager.get("Shield/shield_7.png")));


        ourResources.put("weapon_1", new Sprite((Texture) assetsManager.aManager.get("Weapon/weapon_1.png")));
        ourResources.put("weapon_2", new Sprite((Texture) assetsManager.aManager.get("Weapon/weapon_2.png")));
        ourResources.put("weapon_3", new Sprite((Texture) assetsManager.aManager.get("Weapon/weapon_3.png")));
        ourResources.put("weapon_4", new Sprite((Texture) assetsManager.aManager.get("Weapon/weapon_4.png")));
        ourResources.put("weapon_5", new Sprite((Texture) assetsManager.aManager.get("Weapon/weapon_5.png")));
        ourResources.put("weapon_6", new Sprite((Texture) assetsManager.aManager.get("Weapon/weapon_6.png")));
        ourResources.put("weapon_7", new Sprite((Texture) assetsManager.aManager.get("Weapon/weapon_7.png")));
        ourResources.put("weapon_8", new Sprite((Texture) assetsManager.aManager.get("Weapon/weapon_8.png")));
        ourResources.put("weapon_9", new Sprite((Texture) assetsManager.aManager.get("Weapon/weapon_9.png")));
        ourResources.put("weapon_10", new Sprite((Texture) assetsManager.aManager.get("Weapon/weapon_10.png")));
        ourResources.put("weapon_11", new Sprite((Texture) assetsManager.aManager.get("Weapon/weapon_11.png")));
        ourResources.put("weapon_12", new Sprite((Texture) assetsManager.aManager.get("Weapon/weapon_12.png")));

        heads.add(new Sprite((Texture) assetsManager.aManager.get("HeadTypes/head_1.png")));
        heads.add(new Sprite((Texture) assetsManager.aManager.get("HeadTypes/head_2.png")));
        heads.add(new Sprite((Texture) assetsManager.aManager.get("HeadTypes/head_3.png")));

        heads.add(new Sprite((Texture) assetsManager.aManager.get("HeadTypes/head_4.png")));
        heads.add(new Sprite((Texture) assetsManager.aManager.get("HeadTypes/head_5.png")));
        heads.add(new Sprite((Texture) assetsManager.aManager.get("HeadTypes/head_6.png")));

        heads.add(new Sprite((Texture) assetsManager.aManager.get("HeadTypes/head_7.png")));
        heads.add(new Sprite((Texture) assetsManager.aManager.get("HeadTypes/head_8.png")));
        heads.add(new Sprite((Texture) assetsManager.aManager.get("HeadTypes/head_9.png")));

        heads.add(new Sprite((Texture) assetsManager.aManager.get("HeadTypes/head_10.png")));
        heads.add(new Sprite((Texture) assetsManager.aManager.get("HeadTypes/head_11.png")));
        heads.add(new Sprite((Texture) assetsManager.aManager.get("HeadTypes/head_12.png")));


        heads.add(new Sprite((Texture) assetsManager.aManager.get("HeadTypes/head_13.png")));
        heads.add(new Sprite((Texture) assetsManager.aManager.get("HeadTypes/head_14.png")));
        heads.add(new Sprite((Texture) assetsManager.aManager.get("HeadTypes/head_15.png")));

        ourResources.put("empty", new Sprite((Texture) assetsManager.aManager.get("default.png")));

    }


    /**
     * Packs all loaded sprites into an atlas. Has to called after loading all sprites.
     */
    protected void generatePackedSprites() {
        if (this.packer == null) return;
        TextureAtlas tex = this.packer.generateTextureAtlas(textureFilter, textureFilter, false);
        Set<FileReference> keys = this.resources.keySet();
        this.disposeNonPackedTextures();
        for (FileReference ref : keys) {
            TextureRegion texReg = tex.findRegion(data.getFile(ref).name);
            texReg.setRegionWidth((int) data.getFile(ref).size.width);
            texReg.setRegionHeight((int) data.getFile(ref).size.height);
            super.resources.put(ref, new Sprite(texReg));
        }
    }

    private void disposeNonPackedTextures() {
        for (Entry<FileReference, Sprite> entry : super.resources.entrySet())
            entry.getValue().getTexture().dispose();
    }

    @Override
    public void dispose() {
        if (this.pack && this.packer != null) this.packer.dispose();
        else this.disposeNonPackedTextures();
        super.dispose();
    }

    protected void finishLoading() {
        Set<FileReference> refs = this.resources.keySet();
        for (FileReference ref : refs) {
            Pixmap pix = this.pixmaps.get(ref);
            this.pixmapsToDispose.put(pix, false);
            this.createSprite(ref, pix);

            if (this.packer != null) packer.pack(data.getFile(ref).name, pix);
        }
        if (this.pack) generatePackedSprites();
        this.disposePixmaps();
    }

    protected void createSprite(FileReference ref, Pixmap image) {
        Texture tex = new Texture(image);
        tex.setFilter(textureFilter, textureFilter);
        int width = (int) data.getFile(ref.folder, ref.file).size.width;
        int height = (int) data.getFile(ref.folder, ref.file).size.height;
        TextureRegion texRegion = new TextureRegion(tex, width, height);
        super.resources.put(ref, new Sprite(texRegion));
        pixmapsToDispose.put(image, true);
    }

    @Override
    public void load(String root) {
        assetsManager.loadItemsSprite();
        loadRecourse();
        super.load(root);
    }

    protected void disposePixmaps() {
        Pixmap[] maps = new Pixmap[this.pixmapsToDispose.size()];
        this.pixmapsToDispose.keySet().toArray(maps);
        for (Pixmap pix : maps) {
            try {
                while (pixmapsToDispose.get(pix)) {
                    pix.dispose();
                    pixmapsToDispose.put(pix, false);
                }
            } catch (GdxRuntimeException e) {
                System.err.println("Pixmap was already disposed!");
            }
        }
        pixmapsToDispose.clear();
    }

}