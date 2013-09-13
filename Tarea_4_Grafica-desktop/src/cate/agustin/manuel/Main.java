package cate.agustin.manuel;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import org.mini2Dx.core.game.Mini2DxGame;

public class Main {
	public static void main(String[] args) {
		LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
		cfg.title = "Tarea_4_Grafica";
		cfg.useGL20 = true;
		cfg.width = 1366;
		cfg.height = 768;
		cfg.useCPUSynch = false;
		cfg.vSyncEnabled = true;
		cfg.fullscreen = true;
		
		new LwjglApplication(new Mini2DxGame(new MainGame()), cfg);
	}
}
