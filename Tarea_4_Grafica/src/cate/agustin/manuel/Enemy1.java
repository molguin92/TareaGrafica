package cate.agustin.manuel;

import java.util.ArrayList;
import java.util.List;

import org.mini2Dx.core.game.GameContainer;
import org.mini2Dx.core.graphics.Graphics;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;

public class Enemy1 implements SpaceObject{
	
	private Sprite sprite;
	private Sprite bSprite;
	private Vector2 position;
	private GameContainer gc;
	private List<Bullet> bList;
	private List<Bullet> removeList;
	private float fCounter;
	private float iX;
	private boolean deleteMe;
	
	public Enemy1(Sprite sprite, float X, float Y, Sprite bSprite, GameContainer gc){
		
		this.sprite = sprite;
		this.bSprite = bSprite;
		this.iX = X;
		this.gc = gc;
		
		position = new Vector2(X - sprite.getWidth()/2.0f, Y - sprite.getHeight()/2.0f);
		bList = new ArrayList<Bullet>();
		removeList = new ArrayList<Bullet>();
		fCounter = 1;
		
		sprite.setPosition(position.x, position.y);
		
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
			
			if(bullet.getPosition().y > gc.getHeight()){
				removeList.add(bullet);
			}
		}
		
		for(Bullet bullet: removeList){
			bList.remove(bullet);
		}
		
		removeList.clear();
		
		position.y += delta*50;
		position.x = iX + (float) Math.sin(position.y/100.0f) * 300;
		
		sprite.setPosition(position.x, position.y);
		
		if(position.y > gc.getHeight()){
			deleteMe = true;
		}
	  
		fire(delta);
		
  }
	
	private void fire(float delta){
		
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
