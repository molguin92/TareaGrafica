package cate.agustin.manuel;

import org.mini2Dx.core.game.GameContainer;
import org.mini2Dx.core.graphics.Graphics;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;

public class PowerUp implements SpaceObject{
	
	public Polygon poly;
	private Sprite sprite;
	private Vector2 position;
	private float height;
	private float width;
	public boolean deleteMe;
	public int type;
	private GameContainer gc;

	public PowerUp(float X, float Y, int type, Sprite sprite, GameContainer gc) {
		
		this.height = sprite.getHeight();
		this.width = sprite.getWidth();
		this.position = new Vector2(X - sprite.getWidth()/2.0f, Y - sprite.getHeight()/2.0f);
		this.sprite = new Sprite(sprite);
		this.sprite.flip(false, true);
		this.sprite.setPosition(X - width/2.0f, Y - height/2.0f);
		this.poly = new Polygon(new float[]{width/2.0f, 0, width, height/2.0f, width/2.0f, height, 0, height/2.0f});
		this.poly.setPosition(X - width/2.0f, Y - height/2.0f);
		this.gc = gc;
		this.deleteMe = false;
		this.type = type;
		
	}

	@Override
  public boolean deleteThis() {
	  return deleteMe;
  }

	@Override
  public void renderObject(Graphics g) {
	  
		g.drawSprite(sprite);
	  
  }

	@Override
  public void updatePosition(float delta) {
		if(position.y < 0 || position.x < 0 || position.y > gc.getHeight() || position.x > gc.getWidth()){
			deleteMe = true;
		}
	}

	@Override
  public Vector2 getPosition() {
	  // TODO Auto-generated method stub
	  return null;
  }

	@Override
  public Vector2 getRealPosition() {
	  // TODO Auto-generated method stub
	  return null;
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
