package cate.agustin.manuel;

import org.mini2Dx.core.game.GameContainer;
import org.mini2Dx.core.graphics.Graphics;
import org.mini2Dx.core.screen.GameScreen;
import org.mini2Dx.core.screen.ScreenManager;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class InGame implements GameScreen{

	public static int ID = 0; //Hay que cambiarlo despues
	
	private PlayerShip pOne;
	private AssetManager manager;
	private EnemyManager eManager;
	
	@Override
  public int getId() {
	  return ID;
  }

	@Override
  public void initialise(GameContainer gc) {		
		
		manager = new AssetManager();
		manager.load("data/player1.png", Texture.class);
		manager.load("data/player2.png", Texture.class);
		manager.load("data/bullet1.png", Texture.class);
		manager.finishLoading();
		
		eManager = new EnemyManager(gc, manager);
		
		Texture shipTex = manager.get("data/player1.png", Texture.class);
		Texture bulletTex = manager.get("data/bullet1.png", Texture.class);
		pOne = new PlayerShip(1, new Sprite(shipTex), gc.getWidth()/2.0f, 
				gc.getHeight()/2.0f, gc, new Sprite(bulletTex));
		
		eManager.directSpawn(400, -10, 1);
		
  }

	@Override
  public void render(GameContainer gc, Graphics g) {
		
		pOne.renderObject(g);
		eManager.renderEnemies(g);
		
		g.fillCircle(gc.getWidth()/2.0f, gc.getHeight()/2.0f, 2);
		
  }

	@Override
  public void update(GameContainer gc, ScreenManager sm, float delta) {
		
		pOne.updatePosition(delta);
		eManager.updateEnemies(delta);
		
  }

}
