package cate.agustin.manuel;

import java.util.List;

import org.mini2Dx.core.game.GameContainer;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;

public class Enemy_Turret extends Enemy_Simple {

	protected List<PlayerShip>	playas;
	protected Vector2 playa_pos;

	public Enemy_Turret(Sprite sprite, float X, float Y, Sprite bSprite,
			GameContainer gc, List<PlayerShip> playas, List<Bullet> bList) {
		super(sprite, X, Y, bSprite, gc, bList);
		this.playas = playas;
		this.playa_pos = null;
		this.speed = 50;
		this.integrity = 36;
		this.size = 40;
		this.poly = new Polygon(new float[]{0,0,this.width,0,this.width/2.0f,this.height});
		this.poly.setPosition(position.x, position.y);
	}

	@Override
	protected void fire(float delta){

		for(PlayerShip playa: playas){
			if(playa_pos == null || position.dst(playa.getPosition()) < position.dst(playa_pos)){
				playa_pos = playa.getPosition();
			}			
		}

		if(fCounter > 2.0){
			Sprite bullet = new Sprite(bSprite);
			bList.add(new DirectionalBullet(position.x + sprite.getWidth()/2.0f, position.y + sprite.getHeight()/2.0f, bullet, playa_pos, 150, gc, 1));
			fCounter = 0;
		} else {
			fCounter = fCounter + delta;
		}

		playa_pos = null;

	}

	@Override
	public void updatePosition(float delta) {
		super.updatePosition(delta);		
	}

}
