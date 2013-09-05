package cate.agustin.manuel;

import java.util.List;

import org.mini2Dx.core.game.GameContainer;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;

public class Enemy_Kamikaze extends Enemy_Simple {

	protected List<PlayerShip>	playas;
	protected Vector2 playa_pos;

	public Enemy_Kamikaze(Sprite sprite, float X, float Y, Sprite bSprite,
	    GameContainer gc, List<PlayerShip> playas) {
		super(sprite, X, Y, bSprite, gc);
		this.playas = playas;
		this.playa_pos = null;
		this.speed = 100;
	}

	@Override
	public void updatePosition(float delta) {
		
		for(PlayerShip playa: playas){
			if(playa_pos == null || position.dst(playa.getPosition()) < position.dst(playa_pos)){
				playa_pos = playa.getPosition();
			}			
		}
		
		if(playa_pos.x > position.x + sprite.getWidth()/2.0f){
			position.x += speed*delta;
		} else if(playa_pos.x < position.x + sprite.getWidth()/2.0f){
			position.x -= speed*delta;
		}
		
		if(playa_pos.y > position.y + sprite.getHeight()/2.0f){
			position.y += speed*delta;
		} else if(playa_pos.y < position.y + sprite.getHeight()/2.0f){
			position.y -= speed*delta;
		}

		sprite.setPosition(position.x, position.y);
		
		playa_pos = null;

	}

}
