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


    public Sprite get(String spriteName){
        return ourResources.get(spriteName);
    }



    protected void loadRecourse(){
        ourResources.put("body_iron",new Sprite((Texture) assetsManager.aManager.get("IronKnight/body_.png")));
        ourResources.put("body_steel",new Sprite((Texture) assetsManager.aManager.get("BronzeKnight/body_.png")));
        ourResources.put("body_gold",new Sprite((Texture) assetsManager.aManager.get("GoldKnight/body_.png")));

        ourResources.put("head_iron",new Sprite((Texture) assetsManager.aManager.get("IronKnight/head_.png")));
        ourResources.put("head_steel",new Sprite((Texture) assetsManager.aManager.get("BronzeKnight/head_.png")));
        ourResources.put("head_gold",new Sprite((Texture) assetsManager.aManager.get("GoldKnight/head_.png")));

        ourResources.put("left_arm_iron",new Sprite((Texture) assetsManager.aManager.get("IronKnight/left_arm_.png")));
        ourResources.put("left_arm_steel",new Sprite((Texture) assetsManager.aManager.get("BronzeKnight/left_arm_.png")));
        ourResources.put("left_arm_gold",new Sprite((Texture) assetsManager.aManager.get("GoldKnight/left_arm_.png")));

        ourResources.put("right_arm_iron",new Sprite((Texture) assetsManager.aManager.get("IronKnight/right_arm_.png")));
        ourResources.put("right_arm_steel",new Sprite((Texture) assetsManager.aManager.get("BronzeKnight/right_arm_.png")));
        ourResources.put("right_arm_gold",new Sprite((Texture) assetsManager.aManager.get("GoldKnight/right_arm_.png")));

        ourResources.put("left_leg_iron",new Sprite((Texture) assetsManager.aManager.get("IronKnight/left_leg_.png")));
        ourResources.put("left_leg_steel",new Sprite((Texture) assetsManager.aManager.get("BronzeKnight/left_leg_.png")));
        ourResources.put("left_leg_gold",new Sprite((Texture) assetsManager.aManager.get("GoldKnight/left_leg_.png")));

        ourResources.put("right_leg_iron",new Sprite((Texture) assetsManager.aManager.get("IronKnight/right_leg_.png")));
        ourResources.put("right_leg_steel",new Sprite((Texture) assetsManager.aManager.get("BronzeKnight/right_leg_.png")));
        ourResources.put("right_leg_gold",new Sprite((Texture) assetsManager.aManager.get("GoldKnight/right_leg_.png")));

        ourResources.put("shield_iron",new Sprite((Texture) assetsManager.aManager.get("IronKnight/shield_.png")));
        ourResources.put("shield_steel",new Sprite((Texture) assetsManager.aManager.get("BronzeKnight/shield_.png")));
        ourResources.put("shield_gold",new Sprite((Texture) assetsManager.aManager.get("GoldKnight/shield_.png")));

        ourResources.put("weapon_iron",new Sprite((Texture) assetsManager.aManager.get("IronKnight/weapon_.png")));
        ourResources.put("weapon_steel",new Sprite((Texture) assetsManager.aManager.get("BronzeKnight/weapon_.png")));
        ourResources.put("weapon_gold",new Sprite((Texture) assetsManager.aManager.get("GoldKnight/weapon_.png")));

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