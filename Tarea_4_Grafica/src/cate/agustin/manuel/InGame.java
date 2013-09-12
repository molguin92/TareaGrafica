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

		levelManager(2);
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
	
	public int timeToLevel(long itime, long ctime){
		long transtime=ctime-itime;
		int newlevel=0;
		if (transtime>10*1000){
			newlevel=1;}
		else if (transtime>25*1000){
			newlevel=2;}
		else if (transtime>40*1000){
			newlevel=3;}
		else if (transtime>60*1000){
			newlevel=4;}
		else if (transtime>90*1000){
			newlevel=5;}
		else{
			
			}
		return newlevel;
		
	}
	public void levelManager(int level){
		switch(level){
		case 1:
			eManager.directSpawn(200, -150, 1);
			eManager.directSpawn(400, -100, 1);
			eManager.directSpawn(600, -50, 1);
			eManager.directSpawn(800, -50, 1);
			eManager.directSpawn(1000, -100, 1);
			eManager.directSpawn(1200, -150, 1);
			
			eManager.directSpawn(200, -650, 1);
			eManager.directSpawn(400, -600, 1);
			eManager.directSpawn(600, -550, 1);
			eManager.directSpawn(800, -550, 1);
			eManager.directSpawn(1000, -600, 1);
			eManager.directSpawn(1200, -650, 1);
			
			eManager.directSpawn(200, -1150, 1);
			eManager.directSpawn(400, -1100, 1);
			eManager.directSpawn(600, -1050, 1);
			eManager.directSpawn(800, -1050, 1);
			eManager.directSpawn(1000, -1100, 1);
			eManager.directSpawn(1200, -1150, 1);
			break;
		case 2:
			eManager.directSpawn(600, -50, 2);
			
			eManager.directSpawn(200, -250, 2);
			eManager.directSpawn(800, -250, 2);
			
			eManager.directSpawn(850, -450, 2);
			eManager.directSpawn(300, -450, 2);
			eManager.directSpawn(1200, -450, 2);
			
			eManager.directSpawn(300, -650, 2);
			eManager.directSpawn(600, -650, 2);
			eManager.directSpawn(900, -650, 2);
			eManager.directSpawn(1200, -650, 2);
			
			eManager.directSpawn(200, -850, 2);
			eManager.directSpawn(400, -850, 2);
			eManager.directSpawn(600, -850, 2);
			eManager.directSpawn(800, -850, 2);
			eManager.directSpawn(1000, -850, 2);
			eManager.directSpawn(1200, -850, 2);
			
			break;
		case 3:
			eManager.directSpawn(600, -50, 3);
			
			eManager.directSpawn(200, -1250, 3);
			eManager.directSpawn(400, -1250, 3);
			eManager.directSpawn(600, -1250, 3);
			eManager.directSpawn(800, -1250, 3);
			eManager.directSpawn(1000, -1250, 3);
			eManager.directSpawn(1200, -1250, 3);
			
			eManager.directSpawn(7000, 600, 3);
			eManager.directSpawn(2000, 500, 3);
			eManager.directSpawn(3000, 400, 3);
			eManager.directSpawn(4000, 300,3);
			eManager.directSpawn(5000, 200, 3);
			eManager.directSpawn(6000, 100, 3);
			
			eManager.directSpawn(200, -650, 1);
			eManager.directSpawn(400, -600, 1);
			eManager.directSpawn(600, -550, 1);
			eManager.directSpawn(800, -550, 1);
			eManager.directSpawn(1000, -600, 1);
			eManager.directSpawn(1200, -650, 1);
			
			eManager.directSpawn(-2000, 600, 3);
			eManager.directSpawn(-3000, 500, 3);
			eManager.directSpawn(-4000, 400, 3);
			eManager.directSpawn(-5000, 300,3);
			eManager.directSpawn(-6000, 200, 3);
			eManager.directSpawn(-7000, 100, 3);
			
			eManager.directSpawn(400, -250, 2);
			eManager.directSpawn(600, -250, 2);
			eManager.directSpawn(800, -250, 2);
			
			
			eManager.directSpawn(700, 7000, 3);
			eManager.directSpawn(700, 2000, 3);
			eManager.directSpawn(700, 3000, 3);
			eManager.directSpawn(700, 4000, 3);
			eManager.directSpawn(700, 5000, 3);
			eManager.directSpawn(700, 6000, 3);
			
			
			
			break;
		case 4:
			eManager.directSpawn(100, -50, 4);
			eManager.directSpawn(100, -200, 4);
			eManager.directSpawn(100, -350, 4);
			eManager.directSpawn(100, -500, 4);
			eManager.directSpawn(1200, -50, 4);
			eManager.directSpawn(1200, -200, 4);
			eManager.directSpawn(1200, -350, 4);
			eManager.directSpawn(1200, -500, 4);
			eManager.directSpawn(100, -650, 4);
			eManager.directSpawn(100, -800, 4);
			eManager.directSpawn(100, -950, 4);
			eManager.directSpawn(100, -1100, 4);
			eManager.directSpawn(1200, -650, 4);
			eManager.directSpawn(1200, -800, 4);
			eManager.directSpawn(1200, -950, 4);
			eManager.directSpawn(1200, -1100, 4);
			
			eManager.directSpawn(7000, 600, 3);
			eManager.directSpawn(2000, 500, 3);
			eManager.directSpawn(3000, 400, 3);
			eManager.directSpawn(4000, 300,3);
			eManager.directSpawn(5000, 200, 3);
			eManager.directSpawn(6000, 100, 3);
			
		
			eManager.directSpawn(400, -550, 1);
			eManager.directSpawn(600, -550, 1);
			eManager.directSpawn(800, -550, 1);
			eManager.directSpawn(400, -750, 1);
			eManager.directSpawn(600, -750, 1);
			eManager.directSpawn(800, -750, 1);
			
			eManager.directSpawn(400, -650, 2);
			eManager.directSpawn(600, -650, 2);
			eManager.directSpawn(800, -650, 2);
			
			eManager.directSpawn(-2000, 600, 3);
			eManager.directSpawn(-3000, 500, 3);
			eManager.directSpawn(-4000, 400, 3);
			eManager.directSpawn(-5000, 300,3);
			eManager.directSpawn(-6000, 200, 3);
			eManager.directSpawn(-7000, 100, 3);
			
			eManager.directSpawn(400, -1050, 1);
			eManager.directSpawn(600, -1050, 1);
			eManager.directSpawn(800, -1050, 1);
			eManager.directSpawn(400, -1050, 1);
			eManager.directSpawn(600, -1050, 1);
			eManager.directSpawn(800, -1050, 1);
			
			eManager.directSpawn(400, -1250, 2);
			eManager.directSpawn(600, -1250, 2);
			eManager.directSpawn(800, -1250, 2);
			break;
		case 5:
			eManager.directSpawn(600, -2000, 5);
			eManager.directSpawn(7000, 600, 3);
			eManager.directSpawn(2000, 500, 3);
			eManager.directSpawn(3000, 400, 3);
			eManager.directSpawn(4000, 300,3);
			eManager.directSpawn(5000, 200, 3);
			eManager.directSpawn(6000, 100, 3);
			
		
			eManager.directSpawn(400, -550, 1);
			eManager.directSpawn(600, -550, 1);
			eManager.directSpawn(800, -550, 1);
			eManager.directSpawn(400, -750, 1);
			eManager.directSpawn(600, -750, 1);
			eManager.directSpawn(800, -750, 1);
			
			eManager.directSpawn(400, -650, 2);
			eManager.directSpawn(600, -650, 2);
			eManager.directSpawn(800, -650, 2);
			
			eManager.directSpawn(-2000, 600, 3);
			eManager.directSpawn(-3000, 500, 3);
			eManager.directSpawn(-4000, 400, 3);
			eManager.directSpawn(-5000, 300,3);
			eManager.directSpawn(-6000, 200, 3);
			eManager.directSpawn(-7000, 100, 3);
			
			eManager.directSpawn(400, -1050, 1);
			eManager.directSpawn(600, -1050, 1);
			eManager.directSpawn(800, -1050, 1);
			eManager.directSpawn(400, -1050, 1);
			eManager.directSpawn(600, -1050, 1);
			eManager.directSpawn(800, -1050, 1);
			
			eManager.directSpawn(400, -1250, 2);
			eManager.directSpawn(600, -1250, 2);
			eManager.directSpawn(800, -1250, 2);
			
			eManager.directSpawn(200, -150, 1);
			eManager.directSpawn(400, -100, 1);
			eManager.directSpawn(600, -50, 1);
			eManager.directSpawn(800, -50, 1);
			eManager.directSpawn(1000, -100, 1);
			eManager.directSpawn(1200, -150, 1);
			
			eManager.directSpawn(200, -650, 1);
			eManager.directSpawn(400, -600, 1);
			eManager.directSpawn(600, -550, 1);
			eManager.directSpawn(800, -550, 1);
			eManager.directSpawn(1000, -600, 1);
			eManager.directSpawn(1200, -650, 1);
			
			eManager.directSpawn(200, -1150, 1);
			eManager.directSpawn(400, -1100, 1);
			eManager.directSpawn(600, -1050, 1);
			eManager.directSpawn(800, -1050, 1);
			eManager.directSpawn(1000, -1100, 1);
			eManager.directSpawn(1200, -1150, 1);
			break;
		}
	}

}
