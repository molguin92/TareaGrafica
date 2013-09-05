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
	private Sprite[] sprite; //Sprite de la nave
	private Sprite bSprite; //Sprite de sus balas
	private int playerNr; //Numero del jugador
	private GameContainer gc; 
	private List<Bullet> bulletList; //Lista de balas
	private List<Bullet> removeList; //Lista de balas a ser borradas.
	private float fCounter; //Contador para disparar.
	private float renderCounter;
	private float LCounter;
	private float RCounter;
	private float width;
	private float height;
	private int spriteNr;

	public PlayerShip(int playerNr, Sprite[] sprite, float X, float Y, GameContainer gc, Sprite bSprite) {

		this.playerNr = playerNr;
		this.sprite = sprite;
		this.gc = gc;
		this.bSprite = bSprite;
		this.bSprite.flip(false, true);
		this.width = sprite[1].getWidth();
		this.height = sprite[1].getHeight();

		position = new Vector2(X - width/2.0f, Y - height/2.0f);
		for(Sprite sub_sprite: sprite){
			sub_sprite.setPosition(position.x, position.y);
			sub_sprite.flip(false, true);
		}

		fCounter = 1;
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

		for(Sprite sub_sprite: sprite){
			sub_sprite.setPosition(position.x, position.y);
		}

	}

	private void fire(float delta){

		if(fCounter > 0.16){
			Sprite bulletl = new Sprite(bSprite);
			Sprite bulletr = new Sprite(bSprite);
			bulletList.add(new Bullet(position.x + width/6.0f, position.y + height/2.0f, bulletl, Bullet.UP, 1500));
			bulletList.add(new Bullet(position.x + 5 * width/6.0f, position.y + height/2.0f, bulletr, Bullet.UP, 1500));
			fCounter = 0;
		} else {
			fCounter += delta;
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

}
