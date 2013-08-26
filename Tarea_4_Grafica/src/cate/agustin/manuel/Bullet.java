package cate.agustin.manuel;

import org.mini2Dx.core.graphics.Graphics;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;

public class Bullet implements SpaceObject {

	/**
	 * Clase encargada de la bala basica.
	 */
	
	public static final int UP = 0;
	public static final int DOWN = 1;
	
	private Sprite sprite;
	private Vector2 position;
	private int direction;
	private int speed;
	
	public Bullet(float X, float Y, Sprite sprite, int direction, int speed) {
		
		position = new Vector2(X - sprite.getWidth()/2.0f, Y - sprite.getHeight()/2.0f);
		this.sprite = sprite;
		this.sprite.setPosition(position.x, position.y);
		this.direction = direction;
		this.speed = speed;
		
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
		
		sprite.setPosition(position.x, position.y);

	}

	@Override
	public Vector2 getPosition() {
		return new Vector2(position.x + sprite.getWidth()/2.0f, position.y - sprite.getHeight()/2.0f);
	}

	@Override
  public Vector2 getRealPosition() {
	  return position;
  }

}
