package cate.agustin.manuel;

import java.util.ArrayList;
import java.util.List;

import org.mini2Dx.core.game.GameContainer;
import org.mini2Dx.core.graphics.Graphics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.g2d.Sprite;
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

	private Vector2 position; //Posicion
	private Sprite sprite; //Sprite de la nave
	private Sprite bSprite; //Sprite de sus balas
	private int playerNr; //Numero del jugador
	private GameContainer gc; 
	private List<Bullet> bulletList; //Lista de balas
	private List<Bullet> removeList; //Lista de balas a ser borradas.
	private float fCounter; //Contador para disparar.

	public PlayerShip(int playerNr, Sprite sprite, float X, float Y, GameContainer gc, Sprite bSprite) {

		this.playerNr = playerNr;
		this.sprite = sprite;
		this.gc = gc;
		this.bSprite = bSprite;

		position = new Vector2(X - sprite.getWidth()/2.0f, Y - sprite.getHeight()/2.0f); 
		this.sprite.setPosition(position.x, position.y);
		this.sprite.flip(false, true);

		fCounter = 1;
		bulletList = new ArrayList<Bullet>();
		removeList = new ArrayList<Bullet>();

	}

	@Override
	public void updatePosition(float delta){

		for(Bullet bullet: bulletList){
			bullet.updatePosition(delta);
			
			if(bullet.getPosition().y < 0){
				removeList.add(bullet);
			}
		}
		
		for(Bullet bullet: removeList){
			bulletList.remove(bullet);
		}
		
		removeList.clear();

		if(Gdx.input.isKeyPressed(Keys.UP)){
			moveShip(UP, delta);
		}

		if(Gdx.input.isKeyPressed(Keys.DOWN)){
			moveShip(DOWN, delta);
		}

		if(Gdx.input.isKeyPressed(Keys.LEFT)){
			moveShip(LEFT, delta);
		}

		if(Gdx.input.isKeyPressed(Keys.RIGHT)){
			moveShip(RIGHT, delta);
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

		if(position.x + sprite.getWidth() > gc.getWidth()){
			position.x = gc.getWidth() - sprite.getWidth();
		}

		if(position.y + sprite.getHeight() > gc.getHeight()){
			position.y = gc.getHeight() - sprite.getHeight();
		}

		if(position.x < 0){
			position.x = 0;
		}

		if(position.y < 0){
			position.y = 0;
		}

		sprite.setPosition(position.x, position.y);

	}

	private void fire(float delta){

		if(fCounter > 0.08){
			Sprite bullet = new Sprite(bSprite);
			bulletList.add(new Bullet(position.x + sprite.getWidth()/2.0f, position.y, bullet, Bullet.UP, 1500));
			fCounter = 0;
		} else {
			fCounter = fCounter + delta;
		}

	}

	@Override
	public void renderObject(Graphics g){

		g.drawSprite(sprite);

		for(Bullet bullet: bulletList){
			bullet.renderObject(g);
		}
	}

	@Override
	public Vector2 getPosition(){
		return new Vector2(position.x + sprite.getWidth()/2.0f, position.y - sprite.getHeight()/2.0f);
	}

	@Override
  public Vector2 getRealPosition() {
	  return position;
  }

}
