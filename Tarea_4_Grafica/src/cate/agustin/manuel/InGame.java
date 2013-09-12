package cate.agustin.manuel;

import java.util.ArrayList;
import java.util.List;

import org.mini2Dx.core.game.GameContainer;
import org.mini2Dx.core.graphics.Graphics;
import org.mini2Dx.core.screen.GameScreen;
import org.mini2Dx.core.screen.ScreenManager;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class InGame implements GameScreen{

	public static int ID = 0; //Hay que cambiarlo despues

	private List<PlayerShip> playas;
	private AssetManager manager;
	private EnemyManager eManager;

	@Override
	public int getId() {
		return ID;
	}

	@Override
	public void initialise(GameContainer gc) {		

		manager = new AssetManager();
		manager.load("data/player2.png", Texture.class);
		manager.load("data/bullets/bullet1.png", Texture.class);
		manager.load("data/bullets/blast.png", Texture.class);
		manager.load("data/player1/player1.1.png", Texture.class);
		manager.load("data/player1/player1.2.png", Texture.class);
		manager.load("data/player1/player1.3.png", Texture.class);
		manager.load("data/player1/player1.4.png", Texture.class);
		manager.load("data/player1/player1.l1.png", Texture.class);
		manager.load("data/player1/player1.l2.png", Texture.class);
		manager.load("data/player1/player1.r1.png", Texture.class);
		manager.load("data/player1/player1.r2.png", Texture.class);
		manager.load("data/enemigos/boss2.png", Texture.class);
		manager.load("data/enemigos/kami.png", Texture.class);
		manager.load("data/enemigos/recto.png", Texture.class);
		manager.load("data/enemigos/seno.png", Texture.class);
		manager.load("data/enemigos/turret.png", Texture.class);
		for(int i = 1; i <= 8; i++){
			manager.load("data/explosion/exp"+i+".png", Texture.class);
		}
		manager.load("data/fondo2.jpg", Texture.class);
		manager.finishLoading();

		playas = new ArrayList<PlayerShip>();

		Texture[] shipTex = {manager.get("data/player1/player1.1.png", Texture.class),
				manager.get("data/player1/player1.2.png", Texture.class),
				manager.get("data/player1/player1.3.png", Texture.class),
				manager.get("data/player1/player1.4.png", Texture.class),
				manager.get("data/player1/player1.l1.png", Texture.class),
				manager.get("data/player1/player1.l2.png", Texture.class),
				manager.get("data/player1/player1.r1.png", Texture.class),
				manager.get("data/player1/player1.r2.png", Texture.class)				
		};

		Texture[] enemyTex = {manager.get("data/enemigos/recto.png", Texture.class),
				manager.get("data/enemigos/seno.png", Texture.class),
				manager.get("data/enemigos/kami.png", Texture.class),
				manager.get("data/enemigos/turret.png", Texture.class),
				manager.get("data/enemigos/boss2.png", Texture.class)};
		
		Texture[] projTex = {manager.get("data/bullets/bullet1.png", Texture.class),
				manager.get("data/bullets/blast.png", Texture.class)};
		
		Texture[] expTex = new Texture[8];
		for(int i = 1; i <= 8; i++){
			expTex[i-1] = manager.get("data/explosion/exp"+i+".png", Texture.class);
		}
		
		
		Sprite[] shipSprites = new Sprite[shipTex.length];
		Sprite[] enemySprites = new Sprite[enemyTex.length];
		Sprite[] projSprites = new Sprite[projTex.length];
		Sprite[] expSprites = new Sprite[expTex.length];

		for(int i = 0; i < shipSprites.length; i++){
			shipSprites[i] = new Sprite(shipTex[i]);
		}
		
		for(int i = 0; i < enemySprites.length; i++){
			enemySprites[i] = new Sprite(enemyTex[i]);
		}
		
		for(int i = 0; i < projSprites.length; i++){
			projSprites[i] = new Sprite(projTex[i]);
		}
		
		for(int i = 0; i < expSprites.length; i++){
			expSprites[i] = new Sprite(expTex[i]);
		}

		Texture bulletTex = manager.get("data/bullets/bullet1.png", Texture.class);
		playas.add(new PlayerShip(1, shipSprites, gc.getWidth()/2.0f, 
				gc.getHeight()/2.0f, gc, new Sprite(bulletTex)));

		eManager = new EnemyManager(gc, enemySprites, playas, projSprites, expSprites);
		eManager.directSpawn(600, -10, 5);

	}

	@Override
	public void render(GameContainer gc, Graphics g) {
		
		Sprite background = new Sprite(manager.get("data/fondo2.jpg", Texture.class));
		g.drawSprite(background);
		
		for(PlayerShip playa: playas){
			playa.renderObject(g);
		}
		eManager.renderEnemies(g);

		g.fillCircle(gc.getWidth()/2.0f, gc.getHeight()/2.0f, 2);

	}

	@Override
	public void update(GameContainer gc, ScreenManager sm, float delta) {

		for(PlayerShip playa: playas){
			playa.updatePosition(delta);
		}
		eManager.updateEnemies(delta);

	}

}
