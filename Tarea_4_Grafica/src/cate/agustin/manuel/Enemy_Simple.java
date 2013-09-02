package cate.agustin.manuel;

import java.util.ArrayList;
import java.util.List;

import org.mini2Dx.core.game.GameContainer;
import org.mini2Dx.core.graphics.Graphics;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;

public class Enemy_Simple implements SpaceObject{

	protected Sprite sprite;
	protected Sprite bSprite;
	protected Vector2 position;
	protected GameContainer gc;
	protected List<Bullet> bList;
	protected List<Bullet> removeList;
	protected float fCounter;
	protected boolean deleteMe;
	protected float speed;
	
	public Enemy_Simple(Sprite sprite, float X, float Y, Sprite bSprite, GameContainer gc){
		
		this.speed = 100;
		
		this.sprite = sprite;
		this.bSprite = bSprite;
		this.gc = gc;
		
		position = new Vector2(X - sprite.getWidth()/2.0f, Y - sprite.getHeight()/2.0f);
		bList = new ArrayList<Bullet>();
		removeList = new ArrayList<Bullet>();
		fCounter = 1;
		
		sprite.setPosition(position.x, position.y);
		sprite.flip(false, true);
		
		deleteMe = false;
		
	}

	@Override
  public void renderObject(Graphics g) {
		g.drawSprite(sprite);
		
		for(Bullet bullet: bList){
			bullet.renderObject(g);
		}
  }

	@Override
  public void updatePosition(float delta) {
		
		for(Bullet bullet: bList){
			bullet.updatePosition(delta);
			
			if(bullet.deleteThis()){
				removeList.add(bullet);
			}
		}
		
		for(Bullet bullet: removeList){
			bList.remove(bullet);
		}
		
		removeList.clear();
		
		position.y += delta*speed;
		
		sprite.setPosition(position.x, position.y);
		
		if(position.y > gc.getHeight()){
			deleteMe = true;
		}
	  
		fire(delta);
		
  }
	
	protected void fire(float delta){
		
		if(fCounter > 0.7){
			Sprite bullet = new Sprite(bSprite);
			bList.add(new Bullet(position.x + sprite.getWidth()/2.0f, position.y + sprite.getHeight(), bullet, Bullet.DOWN, 700));
			fCounter = 0;
		} else {
			fCounter = fCounter + delta;
		}
		
	}

	@Override
  public Vector2 getPosition() {
	  return new Vector2(position.x + sprite.getWidth()/2.0f, position.y - sprite.getHeight()/2.0f);
  }
	
	@Override
	public Vector2 getRealPosition() {
		return position;
	}

	@Override
  public boolean deleteThis() {
	  return deleteMe;
  }

}
