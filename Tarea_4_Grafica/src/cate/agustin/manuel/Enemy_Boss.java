package cate.agustin.manuel;

import org.mini2Dx.core.game.GameContainer;

import com.badlogic.gdx.graphics.g2d.Sprite;

public class Enemy_Boss extends Enemy_Simple {
	
	private int xMovementMod;

	public Enemy_Boss(Sprite sprite, float X, float Y, Sprite bSprite,
			GameContainer gc) {
		super(sprite, X, Y, bSprite, gc);
		
		xMovementMod = 1;
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
		
		position.x += xMovementMod*delta*100;
		
		if(position.x + width/2.0 >= gc.getWidth() - gc.getWidth()/6.0){
			xMovementMod = -1;
		} else if (position.x + width/2.0 <= gc.getWidth()/6.0){
			xMovementMod = 1;
		}

		if(position.y != 100 ){

			if(position.y + height/2.0 < 100){
				position.y += delta*100;
			} else if (position.y + height/2.0 > gc.getHeight()){
				position.y -= delta*100;
			}

		} else {

			fire(delta);

		}
		
		sprite.setPosition(position.x, position.y);
		
	}

}
