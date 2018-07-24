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
import com.brashmonkey.spriter.Folder;
import com.brashmonkey.spriter.Loader;
import com.mygdx.game.requests.PlayerAccount;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

public class LoaderImplementation extends Loader<Sprite> implements Disposable {


    public HashMap<String, Sprite> imageMap = new HashMap<String, Sprite>(8);
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
    public void load(String root) {
        this.root = root;
        this.beginLoading();
        //get folder id and item id by character item
        List<String> list = PlayerAccount.getEquippedItems();

        //iron sword, steel sword, diamond sword
        //iron shield, steel shield, diamond shield
        //iron armor, steel armor, diamond armor
        //iron leggings, steel leggings, diamond leggings

        for(int i = 0; i < 8; i++){
            FileReference fileReference = new FileReference(0, i);
            this.resources.put(fileReference, this.loadResource(fileReference));
        }
        if(list == null){
            return;
        }
        for(String itemName: list){
            int folderId = 0;
            int fileId;
            //get folder id
            if(itemName.contains("steel")){
                folderId = 2;
            }
            if(itemName.contains("gold")){
                folderId = 0;
            }
            if(itemName.contains("iron")){
                folderId = 1;
            }

            if(itemName.contains("armor")){
                FileReference ref =  new FileReference(folderId, 0);
                this.resources.put(ref, this.loadResource(ref));
                FileReference arm1 = new FileReference(folderId, 2);
                FileReference arm2 = new FileReference(folderId, 3);
                this.resources.put(arm1, this.loadResource(arm1));
                this.resources.put(arm2, this.loadResource(arm2));

            }

            if(itemName.contains("sword")){
                FileReference ref = new FileReference(folderId, 4);
                this.resources.put(ref, this.loadResource(ref));
            }

            if(itemName.contains("leggins")) {
                FileReference ref = new FileReference(folderId, 6);
                this.resources.put(ref, this.loadResource(ref));
                FileReference ref2 = new FileReference(folderId, 7);
                this.resources.put(ref2, this.loadResource(ref2));
            }
            if(itemName.contains("shield")){
                FileReference ref = new FileReference(folderId, 5);
                this.resources.put(ref, this.loadResource(ref));
            }

        }

        /*for (Folder folder : data.folders) {
            for (com.brashmonkey.spriter.File file : folder.files) {
                //if(new java.io.File(root+"/"+file.name).exists()){
                FileReference ref = new FileReference(folder.id, file.id);
                this.resources.put(ref, this.loadResource(ref));
                //}
            }
        }
        */
        this.finishLoading();
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
        resources.put(ref, new Sprite(texRegion));
        pixmapsToDispose.put(image, true);
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