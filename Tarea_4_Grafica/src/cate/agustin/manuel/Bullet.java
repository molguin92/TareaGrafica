package cate.agustin.manuel;

import org.mini2Dx.core.graphics.Graphics;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;

public class Bullet implements SpaceObject {

	/**
	 * Clase encargada de la bala basica.
	 */
	
	public static final int UP = 0;
	public static final int DOWN = 1;
	
	protected Sprite sprite;
	protected Vector2 position;
	protected int direction;
	protected float speed;
	protected Polygon poly;
	private float width;
	private float height;
	protected boolean delete;
	public int damage;
	
	public Bullet(float X, float Y, Sprite sprite, int direction, float speed, int damage) {
		
		this.position = new Vector2(X - sprite.getWidth()/2.0f, Y - sprite.getHeight()/2.0f);
		this.sprite = sprite;
		this.sprite.setPosition(position.x, position.y);
		this.width = sprite.getWidth();
		this.height = sprite.getHeight();
		this.direction = direction;
		this.speed = speed;
		this.delete = false;
		this.poly=new Polygon(new float[]{0,0,this.width,0,this.width,this.height,this.height,0});
		poly.setPosition(position.x, position.y);
		
		this.damage = damage;
	}

	@Override
	public void renderObject(Graphics g) {
		
		g.drawSprite(sprite);

	}

	@Override
	public void updatePosition(float delta) {
		
		switch (direction) {
			case 0:
				position.y -= delta*speed;
				break;
			case 1:
				position.y += delta*speed;
				break;
		}
		poly.setPosition(position.x, position.y);
		sprite.setPosition(position.x, position.y);
		if(position.y < 0){
			delete = true;
		}

	}

	@Override
	public Vector2 getPosition() {
		return new Vector2(position.x + sprite.getWidth()/2.0f, position.y + sprite.getHeight()/2.0f);
	}

	@Override
  public Vector2 getRealPosition() {
	  return position;
  }

	@Override
  public boolean deleteThis() {
	  // TODO Auto-generated method stub
	  return delete;
  }

	@Override
  public void decIntegrity(int damage) {
	  // TODO Auto-generated method stub
	  
  }

	@Override
  public void incIntegrity(int life) {
	  // TODO Auto-generated method stub
	  
  }

}
