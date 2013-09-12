package cate.agustin.manuel;

import org.mini2Dx.core.graphics.Graphics;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;

public class Explosion implements SpaceObject{
	
	protected Sprite[] sprites;
	protected float animCounter;
	protected boolean deleteMe;

	public Explosion(float X, float Y, Sprite[] sprites, float scale) {
		
		for(Sprite sprite: sprites){
			sprite.scale(scale);
			sprite.setPosition(X - sprite.getWidth()/2.0f, Y - sprite.getHeight()/2.0f);
		}
		this.sprites = sprites;
		animCounter = 0;
		deleteMe = false;
	}

	@Override
  public boolean deleteThis() {
	  return deleteMe;
  }

	@Override
  public void renderObject(Graphics g) {
	  
		if(animCounter < 0.1){
			g.drawSprite(sprites[0]);
		} else if(animCounter < 0.2){
			g.drawSprite(sprites[1]);
		} else if(animCounter < 0.3){
			g.drawSprite(sprites[2]);
		} else if(animCounter < 0.4){
			g.drawSprite(sprites[3]);
		} else if(animCounter < 0.5){
			g.drawSprite(sprites[4]);
		} else if(animCounter < 0.6){
			g.drawSprite(sprites[5]);
		} else if(animCounter < 0.7){
			g.drawSprite(sprites[6]);
		} else {
			g.drawSprite(sprites[7]);
		}
		
		if(animCounter >= 0.8){
			deleteMe = true;
		}
	  
  }

	@Override
  public void updatePosition(float delta) {
		animCounter += delta;
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
