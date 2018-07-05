package com.mygdx.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.mygdx.game.screens.GGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "forsenKek";
		config.height = 480;
		config.width = 800;

//		try {
//			if (Player.registerNewPlayer("alex7", "123") == null)
//				System.out.println(JsonHandler.errorMessage);
//		} catch (IOException e) {
//			e.printStackTrace();
//		} catch (JSONException e) {
//			e.printStackTrace();
//		}
//		System.out.println("Finished");
		new LwjglApplication(new GGame(), config);
	}
}
