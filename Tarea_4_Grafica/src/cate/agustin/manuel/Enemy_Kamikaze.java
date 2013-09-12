package cate.agustin.manuel;

import java.util.List;

import org.mini2Dx.core.game.GameContainer;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;

public class Enemy_Kamikaze extends Enemy_Simple {

	protected List<PlayerShip>	playas;
	protected Vector2 playa_pos;
	protected Vector2 direction_v;

	public Enemy_Kamikaze(Sprite sprite, float X, float Y, Sprite bSprite,
	    GameContainer gc, List<PlayerShip> playas, List<Bullet> bList) {
		super(sprite, X, Y, bSprite, gc, bList);
		this.playas = playas;
		this.playa_pos = null;
		this.speed = 300;
		this.integrity = 12;
	}

	@Override
	public void updatePosition(float delta) {
		
		for(PlayerShip playa: playas){
			if(playa_pos == null || position.dst(playa.getPosition()) < position.dst(playa_pos)){
				playa_pos = playa.getPosition();
			}			
		}
	
		this.direction_v = playa_pos.cpy().add(position.cpy().mul(-1)).nor();
		
		position.add(direction_v.cpy().mul(delta * speed));

		poly.setPosition(position.x, position.y);
		sprite.setPosition(position.x, position.y);
		
		playa_pos = null;

	}

}
