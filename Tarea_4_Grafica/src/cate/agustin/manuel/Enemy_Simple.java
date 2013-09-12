package cate.agustin.manuel;

import java.util.List;

import org.mini2Dx.core.game.GameContainer;
import org.mini2Dx.core.graphics.Graphics;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;

public class Enemy_Simple implements SpaceObject{

	protected Sprite sprite;
	protected Sprite bSprite;
	protected Vector2 position;
	protected GameContainer gc;
	protected List<Bullet> bList;
	protected float fCounter;
	protected boolean deleteMe;
	protected float speed;
	protected float width;
	protected float height;
	protected Polygon poly;
	public int integrity;
	public float scale;
	
	public Enemy_Simple(Sprite sprite, float X, float Y, Sprite bSprite, GameContainer gc, List<Bullet> bList){
		
		this.speed = 100;
		this.integrity = 6;
		this.scale = 1;
		this.sprite = sprite;
		this.bSprite = bSprite;
		this.gc = gc;
		this.width = sprite.getWidth();
		this.height = sprite.getHeight();
		this.bList = bList;
		this.poly = new Polygon(new float[]{0,0,this.width,0,this.width/2.0f,this.height/2.0f});
		
		 
		position = new Vector2(X - width/2.0f, Y - height/2.0f);
		fCounter = 1;
		
		sprite.setPosition(position.x, position.y);
		sprite.flip(false, false);
		//
		poly.setPosition(position.x, position.y);
		
		deleteMe = false;
		
	}

	@Override
  public void renderObject(Graphics g) {
		g.drawSprite(sprite);
  }

	@Override
  public void updatePosition(float delta) {
		
		position.y += delta*speed;
		
		sprite.setPosition(position.x, position.y);
		
		if(position.y > gc.getHeight()){
			deleteMe = true;
		}
		
		poly.setPosition(position.x, position.y);
		fire(delta);
		
  }
	
	protected void fire(float delta){
		
		if(fCounter > 0.7){
			Sprite bullet = new Sprite(bSprite);
			bList.add(new Bullet(position.x + width/2.0f, position.y + height, bullet, Bullet.DOWN, 700, 1));
			fCounter = 0;
		} else {
			fCounter = fCounter + delta;
		}
		
	}

	@Override
  public Vector2 getPosition() {
	  return new Vector2(position.x + width/2.0f, position.y + height/2.0f);
  }
	
	@Override
	public Vector2 getRealPosition() {
		return position;
	}

	@Override
  public boolean deleteThis() {
	  return deleteMe;
  }

	@Override
  public void decIntegrity(int damage) {
	  
		integrity -= damage;
		if(integrity <= 0){
			deleteMe = true;
		}
	  
  }

	@Override
  public void incIntegrity(int life) {
	  // TODO Auto-generated method stub
	  
  }

}
