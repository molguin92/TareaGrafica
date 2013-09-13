package cate.agustin.manuel;

import java.util.List;

import org.mini2Dx.core.game.GameContainer;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Polygon;

public class Enemy_Sin extends Enemy_Simple{
	
	protected float iX;

	public Enemy_Sin(Sprite sprite, float X, float Y, Sprite bSprite,
      GameContainer gc, List<Bullet> bList) {
	  super(sprite, X, Y, bSprite, gc, bList);
	  speed = 50;
	  iX = X;
	  integrity = 36;
	  this.size = 30;
	  this.poly = new Polygon(new float[]{0,0,this.width,0,this.width/2.0f,this.height});
	  this.poly.setPosition(position.x, position.y);
	  this.damage = 10;
  }
	
	@Override
  public void updatePosition(float delta) {
		
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
