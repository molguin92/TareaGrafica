package cate.agustin.manuel;

import java.util.ArrayList;
import java.util.List;

import org.mini2Dx.core.game.GameContainer;
import org.mini2Dx.core.graphics.Graphics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;

/**
 * 
 * @author arachnid92
 *
 * Representa la nave de un jugador en el juego.
 */

public class PlayerShip implements SpaceObject{

	public static final int UP = 0;
	public static final int DOWN = 1;
	public static final int LEFT = 2;
	public static final int RIGHT = 3;
	
	public int fireModifier;
	public int missileMod;

	private Vector2 position; //Posicion
	private SpaceObject target;
	private Sprite[] sprite; //Sprite de la nave
	private Sprite[] bSprite; //Sprite de sus balas
	private int playerNr; //Numero del jugador
	private GameContainer gc; 
	List<Bullet> bulletList; //Lista de balas
	List<Bullet> removeList; //Lista de balas a ser borradas.
	private float fCounter; //Contador para disparar.
	private float mCounter;
	private float renderCounter;
	private float LCounter;
	private float RCounter;
	private float width;
	private float height;
	private int spriteNr;
	protected Polygon poly;
	protected int integrity;
	protected EnemyManager eManager;

	public PlayerShip(int playerNr, Sprite[] sprite, float X, float Y, GameContainer gc, Sprite[] bSprite, EnemyManager eManager) {

		this.eManager = eManager;
		this.playerNr = playerNr;
		this.sprite = sprite;
		this.gc = gc;
		this.bSprite = new Sprite[bSprite.length];
		for(int i = 0; i < bSprite.length; i++){
			this.bSprite[i] = new Sprite(bSprite[i]);
			this.bSprite[i].flip(false, true);
		}
		this.width = sprite[1].getWidth();
		this.height = sprite[1].getHeight();
		this.poly = new Polygon(new float[]{0, this.height, this.width, this.height, this.width/2.0f, 0});
		
		this.fireModifier = 1;
		this.missileMod = 1;
		this.integrity = 24;
		
		position = new Vector2(X - width/2.0f, Y - height/2.0f);
		for(Sprite sub_sprite: sprite){
			sub_sprite.setPosition(position.x, position.y);
			sub_sprite.flip(false, true);
		}
		poly.setPosition(position.x, position.y);

		fCounter = 0;
		mCounter = 0;
		renderCounter = 0;
		LCounter = 0;
		RCounter = 0;
		bulletList = new ArrayList<Bullet>();
		removeList = new ArrayList<Bullet>();

	}

	@Override
	public void updatePosition(float delta){

		for(Bullet bullet: bulletList){
			bullet.updatePosition(delta);
			
			if(bullet.deleteThis()){
				removeList.add(bullet);
			}
		}
		
		for(Bullet bullet: removeList){
			bulletList.remove(bullet);
		}
		
		removeList.clear();
		
		if(renderCounter < 0.08){
			spriteNr = 0;
			renderCounter += delta;
		} else if(renderCounter < 0.16){
			spriteNr = 1;
			renderCounter += delta;
		} else if(renderCounter < 0.24){
			spriteNr = 2;
			renderCounter += delta;
		} else if(renderCounter < 0.32){
			spriteNr = 3;
			renderCounter = 0;
		} else {
			renderCounter += delta;
		}

		switch (playerNr) {
			case 1:
				if(Gdx.input.isKeyPressed(Keys.UP)){
					moveShip(UP, delta);
					spriteNr = 2;
					LCounter = 0;
					RCounter = 0;
				}

				if(Gdx.input.isKeyPressed(Keys.DOWN)){
					moveShip(DOWN, delta);
					LCounter = 0;
					RCounter = 0;
				}

				if(Gdx.input.isKeyPressed(Keys.LEFT) && !Gdx.input.isKeyPressed(Keys.RIGHT)){
					moveShip(LEFT, delta);
					RCounter = 0;
					if(LCounter < 0.2){
						spriteNr = 4;
						LCounter += delta;
					} else {
						spriteNr = 5;
					}
				}

				if(Gdx.input.isKeyPressed(Keys.RIGHT) && !Gdx.input.isKeyPressed(Keys.LEFT)){
					moveShip(RIGHT, delta);
					LCounter = 0;
					if(RCounter < 0.2){
						spriteNr = 6;
						RCounter += delta;
					} else {
						spriteNr = 7;
					}
				}
				break;
			case 2:
				if(Gdx.input.isKeyPressed(Keys.W)){
					moveShip(UP, delta);
					spriteNr = 2;
					LCounter = 0;
					RCounter = 0;
				}

				if(Gdx.input.isKeyPressed(Keys.S)){
					moveShip(DOWN, delta);
					LCounter = 0;
					RCounter = 0;
				}

				if(Gdx.input.isKeyPressed(Keys.A) && !Gdx.input.isKeyPressed(Keys.D)){
					moveShip(LEFT, delta);
					RCounter = 0;
					if(LCounter < 0.2){
						spriteNr = 4;
						LCounter += delta;
					} else {
						spriteNr = 5;
					}
				}

				if(Gdx.input.isKeyPressed(Keys.D) && !Gdx.input.isKeyPressed(Keys.A)){
					moveShip(RIGHT, delta);
					LCounter = 0;
					if(RCounter < 0.2){
						spriteNr = 6;
						RCounter += delta;
					} else {
						spriteNr = 7;
					}
				}
				break;
		}
		
		
		
		fire(delta);

	}

	private void moveShip(int direction, float delta){

		switch (direction) {
			case 0: //UP
				position.y = position.y - delta*500;
				break;
			case 1: //DOWN
				position.y = position.y + delta*500;
				break;
			case 2: //LEFT
				position.x = position.x - delta*500;
				break;
			case 3: //RIGHT
				position.x = position.x + delta*500;
				break;
		}

		if(position.x + width > gc.getWidth()){
			position.x = gc.getWidth() - width;
		}

		if(position.y + height > gc.getHeight()){
			position.y = gc.getHeight() - height;
		}

		if(position.x < 0){
			position.x = 0;
		}

		if(position.y < 0){
			position.y = 0;
		}
		
		poly.setPosition(position.x, position.y);
		for(Sprite sub_sprite: sprite){
			sub_sprite.setPosition(position.x, position.y);
		}

	}

	private void fire(float delta){

		for(SpaceObject enemy: eManager.getEnemies()){
			if(target == null || position.dst(enemy.getPosition()) < position.dst(target.getPosition())){
				target = enemy;
			}			
		}
		
		if(fCounter > 0.16){
		
			Sprite bulletl;
			Sprite bulletr;
			
			switch (fireModifier) {
				case 1:
					bulletl = new Sprite(bSprite[0]);
					bulletr = new Sprite(bSprite[0]);
					bulletList.add(new Bullet(position.x + width/6.0f, position.y + height/2.0f, bulletl, Bullet.UP, 1500, 1));
					bulletList.add(new Bullet(position.x + 5 * width/6.0f, position.y + height/2.0f, bulletr, Bullet.UP, 1500, 1));
					break;
				case 2:
					bulletl = new Sprite(bSprite[1]);
					bulletr = new Sprite(bSprite[1]);
					bulletList.add(new Bullet(position.x + width/6.0f, position.y + height/2.0f, bulletl, Bullet.UP, 1500, 3));
					bulletList.add(new Bullet(position.x + 5 * width/6.0f, position.y + height/2.0f, bulletr, Bullet.UP, 1500, 3));
					break;
				default:
					bulletl = new Sprite(bSprite[2]);
					bulletr = new Sprite(bSprite[2]);
					bulletList.add(new Bullet(position.x + width/6.0f, position.y + height/2.0f, bulletl, Bullet.UP, 1500, 6));
					bulletList.add(new Bullet(position.x + 5 * width/6.0f, position.y + height/2.0f, bulletr, Bullet.UP, 1500, 6));
					break;						
			}
			fCounter = 0;
		} else {
			fCounter += delta;
		}
		
		if(mCounter > 1){
			Sprite missileL;
			Sprite missileR;
			
			switch (missileMod) {
				case 0:
					break;
				case 1:
					missileL = new Sprite(bSprite[8]);
					missileR = new Sprite(bSprite[8]);
					bulletList.add(new HomingMissile(position.x + width/6.0f, position.y + 5*height/6.0f, missileL, target, 400, gc, 3, 20, eManager, 600));
					bulletList.add(new HomingMissile(position.x + 5*width/6.0f, position.y + 5*height/6.0f, missileR, target, 400, gc, 3, 20, eManager, 600));
					break;
			}
			mCounter = 0;
		} else {
			mCounter += delta;
		}

	}

	@Override
	public void renderObject(Graphics g){

		g.drawSprite(sprite[spriteNr]);
		
		for(Bullet bullet: bulletList){
			bullet.renderObject(g);
		}
		
	}

	@Override
	public Vector2 getPosition(){
		return new Vector2(position.x + width/2.0f, position.y + height/2.0f);
	}

	@Override
  public Vector2 getRealPosition() {
	  return position;
  }

	@Override
  public boolean deleteThis() {
	  return false;
  }

	@Override
  public void decIntegrity(int damage) {
	  
		integrity -= damage;
	  
  }

	@Override
  public void incIntegrity(int life) {
	  
		integrity += life;
	  
  }

}
