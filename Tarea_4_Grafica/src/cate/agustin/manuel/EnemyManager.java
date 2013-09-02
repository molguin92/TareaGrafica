package cate.agustin.manuel;

import java.util.ArrayList;
import java.util.List;

import org.mini2Dx.core.game.GameContainer;
import org.mini2Dx.core.graphics.Graphics;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class EnemyManager {
	
	/**
	 * Se encarga de generar enemigos y administrarlos.
	 */
	
	private GameContainer gc;
	private AssetManager am;
	private List<SpaceObject> eList;
	private List<SpaceObject> removeList;
	private List<PlayerShip> playas;

	public EnemyManager(GameContainer gc, AssetManager am, List<PlayerShip> playas) {
		this.gc = gc;
		this.am = am;
		this.playas = playas;
		
		eList = new ArrayList<SpaceObject>();
		removeList = new ArrayList<SpaceObject>();
	}
	
	public void randomSpawn(){}
	
	public void directSpawn(float X, float Y, int type){
		
		Texture tex = am.get("data/player2.png", Texture.class);
		Texture bullet = am.get("data/bullet1.png", Texture.class);
		
		switch (type) {
			case 1:
				eList.add(new Enemy_Simple(new Sprite(tex), X, Y, new Sprite(bullet), gc));
				break;
			case 2:
				eList.add(new Enemy_Sin(new Sprite(tex), X, Y, new Sprite(bullet), gc));
				break;
			case 3:
				eList.add(new Enemy_Kamikaze(new Sprite(tex), X, Y, new Sprite(bullet), gc, playas));
				break;
			case 4:
				eList.add(new Enemy_Turret(new Sprite(tex), X, Y, new Sprite(bullet), gc, playas));
				break;
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
}