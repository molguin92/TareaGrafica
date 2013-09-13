package cate.agustin.manuel;

import java.util.ArrayList;
import java.util.List;

import org.mini2Dx.core.game.GameContainer;
import org.mini2Dx.core.graphics.Graphics;
import org.mini2Dx.core.screen.GameScreen;
import org.mini2Dx.core.screen.ScreenManager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class InGame implements GameScreen{

	public static int ID = 0; //Hay que cambiarlo despues

	private List<PlayerShip> playas;
	private AssetManager manager;
	private EnemyManager eManager;
	private int level;
	private long itimelevel;
	private long currenttime;
	private long transtime;

	@Override
	public int getId() {
		return ID;
	}

	@Override
	public void initialise(GameContainer gc) {		

		manager = new AssetManager();
		manager.load("data/player2.png", Texture.class);
		manager.load("data/bullets/bullet1.png", Texture.class);
		manager.load("data/bullets/bullet2.png", Texture.class);
		manager.load("data/bullets/bullet3.png", Texture.class);
		manager.load("data/bullets/bulletEnemy.png", Texture.class);
		manager.load("data/bullets/bulletEnemy2.png", Texture.class);
		manager.load("data/bullets/blast.png", Texture.class);
		manager.load("data/bullets/blast2.png", Texture.class);
		manager.load("data/bullets/bossHoming.png", Texture.class);
		manager.load("data/bullets/playerHoming1.png", Texture.class);
		manager.load("data/bullets/playerHoming2.png", Texture.class);
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
		manager.load("data/powerups/life.png", Texture.class);
		manager.load("data/powerups/firepower.png", Texture.class);
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
				manager.get("data/bullets/bullet2.png", Texture.class),
				manager.get("data/bullets/bullet3.png", Texture.class),
				manager.get("data/bullets/bulletEnemy.png", Texture.class),
				manager.get("data/bullets/bulletEnemy2.png", Texture.class),
				manager.get("data/bullets/blast.png", Texture.class),
				manager.get("data/bullets/blast2.png", Texture.class),
				manager.get("data/bullets/bossHoming.png", Texture.class),
				manager.get("data/bullets/playerHoming1.png", Texture.class),
				manager.get("data/bullets/playerHoming2.png", Texture.class)};
		
		Texture[] expTex = new Texture[8];
		for(int i = 1; i <= 8; i++){
			expTex[i-1] = manager.get("data/explosion/exp"+i+".png", Texture.class);
		}
		
		Texture[] pUpsTex = {manager.get("data/powerups/life.png", Texture.class),
		manager.get("data/powerups/firepower.png", Texture.class)};		
		
		Sprite[] shipSprites = new Sprite[shipTex.length];
		Sprite[] enemySprites = new Sprite[enemyTex.length];
		Sprite[] projSprites = new Sprite[projTex.length];
		Sprite[] expSprites = new Sprite[expTex.length];
		Sprite[] pUpsSprites = new Sprite[pUpsTex.length];

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
		
		for(int i = 0; i < pUpsSprites.length; i++){
			pUpsSprites[i] = new Sprite(pUpsTex[i]);
		}

		eManager = new EnemyManager(gc, enemySprites, playas, projSprites, expSprites, pUpsSprites);
		
		playas.add(new PlayerShip(1, shipSprites, gc.getWidth()/2.0f, 
				gc.getHeight()/2.0f, gc, projSprites, eManager));

		level=1;
		itimelevel=System.currentTimeMillis();
		levelManager(level);
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
		
		if(Gdx.input.isKeyPressed(Keys.ESCAPE)){
			gc.dispose();
		}
		
		if(Gdx.input.isKeyPressed(Keys.P)){
			gc.pause();
		}

		for(PlayerShip playa: playas){
			playa.updatePosition(delta);
		}
		eManager.updateEnemies(delta);
		currenttime=System.currentTimeMillis();
		transtime=currenttime-itimelevel;
		if (level!=timeToLevel(transtime, level)){
			level=timeToLevel(transtime,level);
			levelManager(level);
			itimelevel=System.currentTimeMillis();
			System.out.println("nivel:"+level);
		}
	}
	
	public int timeToLevel(long transtime, int level){
		if (level==1 && transtime>20*1000){
			level++;
			}
		else if (level==2 && transtime>45*1000){
			level++;
			}
		else if (level==3 && transtime>30*1000){
			level++;
			}
		else if (level==4 && transtime>30*1000){
			level++;
		}

		return level;
		
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
			
			eManager.directSpawn(650, -650, 2);
			eManager.directSpawn(300, -650, 2);
			eManager.directSpawn(1000, -650, 2);
			
			eManager.directSpawn(400, -1050, 2);
			eManager.directSpawn(100, -1050, 2);
			eManager.directSpawn(700, -1050, 2);
			eManager.directSpawn(1000, -1050, 2);
			
			break;
		case 3:
			eManager.directSpawn(800, -50, 3);
			
			eManager.directSpawn(7000, 600, 3);
			eManager.directSpawn(2000, 500, 3);
			eManager.directSpawn(3000, 400, 3);
			eManager.directSpawn(4000, 300,3);
			eManager.directSpawn(5000, 200, 3);
			eManager.directSpawn(6000, 100, 3);
			
			eManager.directSpawn(-7000, 600, 3);
			eManager.directSpawn(-2000, 500, 3);
			eManager.directSpawn(-3000, 400, 3);
			eManager.directSpawn(-4000, 300,3);
			eManager.directSpawn(-5000, 200, 3);
			eManager.directSpawn(-6000, 100, 3);
			
			eManager.directSpawn(400, -700, 1);
			eManager.directSpawn(600, -750, 1);
			eManager.directSpawn(800, -750, 1);
			eManager.directSpawn(1000, -700, 1);
			
			eManager.directSpawn(400, -1500, 1);
			eManager.directSpawn(600, -1550, 1);
			eManager.directSpawn(800, -1550, 1);
			eManager.directSpawn(1000, -1500, 1);			
			
			break;
		case 4:
			eManager.directSpawn(100, -50, 4);
			eManager.directSpawn(100, -350, 4);
			eManager.directSpawn(1200, -50, 4);
			eManager.directSpawn(1200, -350, 4);
			eManager.directSpawn(100, -650, 4);
			eManager.directSpawn(100, -950, 4);
			eManager.directSpawn(1200, -650, 4);
			eManager.directSpawn(1200, -950, 4);
			
			eManager.directSpawn(2000, 600, 3);
			eManager.directSpawn(4000, 400,3);
			eManager.directSpawn(6000, 200, 3);
			
			eManager.directSpawn(400, -550, 1);
			eManager.directSpawn(700, -550, 1);
			eManager.directSpawn(1000, -550, 1);
			
			eManager.directSpawn(-2000, 600, 3);
			eManager.directSpawn(-4000, 400, 3);
			eManager.directSpawn(-6000, 200, 3);

			eManager.directSpawn(400, -1050, 1);
			eManager.directSpawn(700, -1050, 1);
			eManager.directSpawn(1000, -1050, 1);
			break;
			
		case 5:
			eManager.directSpawn(600, -2100, 5);
			
			eManager.directSpawn(100, -50, 4);
			eManager.directSpawn(100, -350, 4);
			eManager.directSpawn(1200, -50, 4);
			eManager.directSpawn(1200, -350, 4);
			
			eManager.directSpawn(650, -650, 2);
			eManager.directSpawn(300, -650, 2);
			eManager.directSpawn(1000, -650, 2);
			
			eManager.directSpawn(2000, 600, 3);
			eManager.directSpawn(4000, 400,3);
			eManager.directSpawn(6000, 200, 3);
			
			eManager.directSpawn(-2000, 600, 3);
			eManager.directSpawn(-4000, 400, 3);
			eManager.directSpawn(-6000, 200, 3);
			

			break;
		}
	}

}
