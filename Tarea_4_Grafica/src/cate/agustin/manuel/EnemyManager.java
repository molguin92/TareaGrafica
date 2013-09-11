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
	
	private GameContainer gc;
	private Sprite[] sprites;
	private List<Enemy_Simple> eList;
	private List<SpaceObject> removeList;
	private List<PlayerShip> playas;
	private Sprite[] projSprites;

	public EnemyManager(GameContainer gc, Sprite[] sprites, List<PlayerShip> playas, Sprite[] projectileSprites) {
		this.gc = gc;
		this.sprites = sprites;
		this.projSprites = projectileSprites;
		this.playas = playas;
		
		eList = new ArrayList<Enemy_Simple>();
		removeList = new ArrayList<SpaceObject>();
	}
	
	public void randomSpawn(){}
	
	public void directSpawn(float X, float Y, int type){
		
		switch (type) {
			case 1:
				eList.add(new Enemy_Simple(new Sprite(sprites[0]), X, Y, new Sprite(projSprites[0]), gc));
				break;
			case 2:
				eList.add(new Enemy_Sin(new Sprite(sprites[1]), X, Y, new Sprite(projSprites[0]), gc));
				break;
			case 3:
				eList.add(new Enemy_Kamikaze(new Sprite(sprites[2]), X, Y, new Sprite(projSprites[0]), gc, playas));
				break;
			case 4:
				eList.add(new Enemy_Turret(new Sprite(sprites[0]), X, Y, new Sprite(projSprites[0]), gc, playas));
				break;
			case 5:
				eList.add(new Enemy_Boss(new Sprite(sprites[3]), X, Y, new Sprite(projSprites[0]), new Sprite(projSprites[1]), gc, playas));
			default:
				break;
		}
	}
	
	public void updateEnemies(float delta){
		for(SpaceObject enemy: eList){
			enemy.updatePosition(delta);
			
			if(enemy.deleteThis()){
				removeList.add(enemy);
			}
		}
		
		for(SpaceObject enemy: removeList){
			eList.remove(enemy);
		}
		
		removeList.clear();
	}
	
	public void renderEnemies(Graphics g){
		for(SpaceObject enemy: eList){
			enemy.renderObject(g);
		}
	}
//	public void checkCollisionsInEnemies(){
//		Polygon poly1, poly2;
//		for(PlayerShip playa: playas){
//			for(Bullet bullet: playa.bulletList){
//				for(Enemy_Simple malote: eList){
//					poly1=malote.poly;
//					poly2=bullet.poly;
//					if(Intersector.overlapConvexPolygons(poly1, poly2)){
//						//HAY UN CHOQUE
//						// vida enemigo--
//					}
//
//				}
//			}
//		}
//	}
//	public void checkCollisionsInPlayers(){
//		for(Enemy_Simple malote: eList){
//			for(Bullet bullet: malote.bulletList){
//				for(PlayerShip playa: playas){
//					poly1=playa.poly;
//					poly2=bullet.poly;
//					if(Intersector.overlapConvexPolygons(poly1, poly2)){
//						//HAY UN CHOQUE
//						// vida jugador--
//					}
//
//				}
//			}
//		}
//		
//	}
}