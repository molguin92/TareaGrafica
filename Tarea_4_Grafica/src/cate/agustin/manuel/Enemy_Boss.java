package cate.agustin.manuel;

import java.util.List;

import org.mini2Dx.core.game.GameContainer;

import com.badlogic.gdx.graphics.g2d.Sprite;

public class Enemy_Boss extends Enemy_Simple {

	private int xMovementMod;
	protected Sprite blastSprite;
	protected float bCounter;
	protected List<PlayerShip>	playas;
	protected PlayerShip target;

	public Enemy_Boss(Sprite sprite, float X, float Y, Sprite bSprite,
			Sprite blastSprite, GameContainer gc, List<PlayerShip> playas, List<Bullet> bList) {
		super(sprite, X, Y, bSprite, gc, bList);
		
		xMovementMod = 1;
		bCounter = 0;
		target = null;
		
		this.playas = playas;
		this.blastSprite = blastSprite;
		this.integrity = 600;
	}

	@Override
	public void updatePosition(float delta) {

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
		poly.setPosition(position.x, position.y);
		sprite.setPosition(position.x, position.y);

	}
	
	@Override
	protected void fire(float delta){
		
		for(PlayerShip playa: playas){
			if(target == null || position.dst(playa.getPosition()) < position.dst(target.getPosition())){
				target = playa;
			}			
		}
		
		if(fCounter > 5){
			Sprite bulletL = new Sprite(bSprite);
			Sprite bulletR = new Sprite(bSprite);
			bList.add(new HomingMissile(position.x + width/6.0f, position.y + 5*height/6.0f, bulletL, target, 200, gc, 10));
			bList.add(new HomingMissile(position.x + 5*width/6.0f, position.y + 5*height/6.0f, bulletR, target, 200, gc, 10));
			fCounter = 0;
		} else {
			fCounter = fCounter + delta;
		}

		if(bCounter > 0.7){
			Sprite bullet = new Sprite(blastSprite);
			bList.add(new DirectionalBullet(position.x + width/2.0f, position.y + height/2.0f, bullet, target.getPosition(), 700, gc, 10));
			bCounter = 0;
		} else {
			bCounter = bCounter + delta;
		}
		
		target = null;
		
	}

}
