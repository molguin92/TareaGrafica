package cate.agustin.manuel;

import org.mini2Dx.core.game.GameContainer;

import com.badlogic.gdx.graphics.g2d.Sprite;

public class Enemy_Boss extends Enemy_Simple {

	private int xMovementMod;
	protected Sprite blastSprite;
	protected float bCounter;

	public Enemy_Boss(Sprite sprite, float X, float Y, Sprite bSprite,
			Sprite blastSprite, GameContainer gc) {
		super(sprite, X, Y, bSprite, gc);

		xMovementMod = 1;
		this.blastSprite = blastSprite;
		bCounter = 0;
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

		if(position.x + width/2.0 >= gc.getWidth() - gc.getWidth()/8.0){
			xMovementMod = -1;
		} else if (position.x + width/2.0 <= gc.getWidth()/8.0){
			xMovementMod = 1;
		}

		if(position.y + height/2.0 < 200){
			position.y += delta*100;
		} else {
			fire(delta);
		}
		sprite.setPosition(position.x, position.y);

	}
	
	@Override
	protected void fire(float delta){
		
		if(fCounter > 0.3){
			Sprite bulletL = new Sprite(bSprite);
			Sprite bulletR = new Sprite(bSprite);
			bList.add(new Bullet(position.x + width/6.0f, position.y + 5*height/6, bulletL, Bullet.DOWN, 700));
			bList.add(new Bullet(position.x + 5*width/6.0f, position.y + 5*height/6, bulletR, Bullet.DOWN, 700));
			fCounter = 0;
		} else {
			fCounter = fCounter + delta;
		}
		
		
		if(bCounter > 0.7){
			Sprite bullet = new Sprite(blastSprite);
			bList.add(new Bullet(position.x + width/2.0f, position.y + height/2.0f, bullet, Bullet.DOWN, 700));
			bCounter = 0;
		} else {
			bCounter = bCounter + delta;
		}
		
	}

}
