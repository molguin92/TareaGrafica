package cate.agustin.manuel;

import java.util.List;

import org.mini2Dx.core.game.GameContainer;

import com.badlogic.gdx.graphics.g2d.Sprite;

public class Enemy_Sin extends Enemy_Simple{
	
	protected float iX;

	public Enemy_Sin(Sprite sprite, float X, float Y, Sprite bSprite,
      GameContainer gc, List<Bullet> bList) {
	  super(sprite, X, Y, bSprite, gc, bList);
	  speed = 50;
	  iX = X;
	  integrity = 36;
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
		
		position.y += delta*speed;
		position.x = iX + (float) Math.sin(position.y/100.0f) * 300;
		
		sprite.setPosition(position.x, position.y);
		poly.setPosition(position.x, position.y);
		
		if(position.y > gc.getHeight()){
			deleteMe = true;
		}
	  
		fire(delta);
		
  }
	
}
