package cate.agustin.manuel;

import java.util.ArrayList;
import java.util.List;

import org.mini2Dx.core.game.GameContainer;
import org.mini2Dx.core.graphics.Graphics;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Polygon;

public class EnemyManager {

	/**
	 * Se encarga de generar enemigos y administrarlos.
	 */

	private GameContainer	     gc;
	private Sprite[]	         sprites;
	private List<Enemy_Simple>	eList;
	private List<Enemy_Simple>	 removeList;
	private List<Bullet>	     bList;
	private List<Bullet>	     removeBList;
	private List<PlayerShip>	 playas;
	private List<Explosion>		 explosions;
	private List<Explosion>		 removeExp;
	private Sprite[]	         projSprites;
	private Sprite[]					 expSprites;

	public EnemyManager(GameContainer gc, Sprite[] sprites,
	    List<PlayerShip> playas, Sprite[] projectileSprites, Sprite[] expSprites) {
		this.gc = gc;
		this.sprites = sprites;
		this.projSprites = projectileSprites;
		this.expSprites = expSprites;
		this.playas = playas;

		bList = new ArrayList<Bullet>();
		removeBList = new ArrayList<Bullet>();

		eList = new ArrayList<Enemy_Simple>();
		removeList = new ArrayList<Enemy_Simple>();
		
		explosions = new ArrayList<Explosion>();
		removeExp = new ArrayList<Explosion>();
	}

	public void randomSpawn() {
	}

	public void directSpawn(float X, float Y, int type) {

		switch (type) {
			case 1:
				eList.add(new Enemy_Simple(new Sprite(sprites[0]), X, Y, new Sprite(
				    projSprites[0]), gc, bList));
				break;
			case 2:
				eList.add(new Enemy_Sin(new Sprite(sprites[1]), X, Y, new Sprite(
				    projSprites[0]), gc, bList));
				break;
			case 3:
				eList.add(new Enemy_Kamikaze(new Sprite(sprites[2]), X, Y, new Sprite(
				    projSprites[0]), gc, playas, bList));
				break;
			case 4:
				eList.add(new Enemy_Turret(new Sprite(sprites[3]), X, Y, new Sprite(
				    projSprites[0]), gc, playas, bList));
				break;
			case 5:
				eList.add(new Enemy_Boss(new Sprite(sprites[4]), X, Y, new Sprite(
				    projSprites[0]), new Sprite(projSprites[1]), gc, playas, bList));
			default:
				break;
		}
	}

	public void updateEnemies(float delta) {
		
		for (Enemy_Simple enemy : eList) {
			enemy.updatePosition(delta);

			if (enemy.deleteThis()) {
				removeList.add(enemy);
			}
		}

		for (Enemy_Simple enemy : removeList) {
			Sprite[] spritesExp = new Sprite[expSprites.length];
			for(int i = 0; i < expSprites.length; i++){
				spritesExp[i] = new Sprite(expSprites[i]);
			}
			explosions.add(new Explosion(enemy.getPosition().x, enemy.getPosition().y, spritesExp, enemy.scale));
			eList.remove(enemy);
		}

		removeList.clear();

		for (Bullet bullet : bList) {
			bullet.updatePosition(delta);

			if (bullet.deleteThis()) {
				removeBList.add(bullet);
			}
		}

		for (Bullet bullet : removeBList) {
			bList.remove(bullet);
		}

		removeBList.clear();
		
		for(Explosion explosion: explosions){
			explosion.updatePosition(delta);
			
			if(explosion.deleteThis()){
				removeExp.add(explosion);
			}
		}
		
		for(Explosion explosion: removeExp){
			explosions.remove(explosion);
		}
		
		removeExp.clear();
		
		checkCollisionsInEnemies();
		checkCollisionsInPlayers();
		checkCollisionsEnemiesPlayers();

	}

	public void renderEnemies(Graphics g) {
		for (SpaceObject enemy : eList) {
			enemy.renderObject(g);
		}

		for (Bullet bullet : bList) {
			bullet.renderObject(g);
		}
		
		for(Explosion explosion: explosions) {
			explosion.renderObject(g);
		}
	}

	public void checkCollisionsInEnemies() {
		Polygon poly1, poly2;
		for (PlayerShip playa : playas) {
			for (Bullet bullet : playa.bulletList) {
				for (Enemy_Simple malote : eList) {
					poly1 = malote.poly;
					poly2 = bullet.poly;
					if (Intersector.overlapConvexPolygons(poly1, poly2)) {
						malote.decIntegrity(bullet.damage);
						bullet.delete = true;
						System.out.println(malote.integrity);
					}

				}
			}
		}
	}

	public void checkCollisionsInPlayers() {
		Polygon poly1, poly2;
		for (Bullet bullet : bList) {
			for (PlayerShip playa : playas) {
				poly1 = playa.poly;
				poly2 = bullet.poly;
				if (Intersector.overlapConvexPolygons(poly1, poly2)) {
					playa.decIntegrity(bullet.damage);
					bullet.delete = true;
				}
			}
		}
	}

	public void checkCollisionsEnemiesPlayers() {
		Polygon poly1, poly2;
		for (Enemy_Simple malote : eList) {
			for (PlayerShip playa : playas) {
				poly1 = playa.poly;
				poly2 = malote.poly;
				if (Intersector.overlapConvexPolygons(poly1, poly2)) {
					malote.deleteMe = true;
				}
			}
		}
	}
}