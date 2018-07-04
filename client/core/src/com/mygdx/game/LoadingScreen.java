package com.mygdx.game;

import com.badlogic.gdx.Screen;


public class LoadingScreen implements Screen{
    
    private GGame parent;
    
    public LoadingScreen(GGame game){
        parent = game;
    }
    
    
    @Override
	public void show() {
		// TODO Auto-generated method stub
	}
 
	@Override
	public void render(float delta) {
		parent.changeScreen(parent.getLogin());
	}
 
	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
	}
 
	@Override
	public void pause() {
		// TODO Auto-generated method stub
	}
 
	@Override
	public void resume() {
		// TODO Auto-generated method stub
	}
 
	@Override
	public void hide() {
		// TODO Auto-generated method stub
	}
 
	@Override
	public void dispose() {
		// TODO Auto-generated method stub
	}
}
    

