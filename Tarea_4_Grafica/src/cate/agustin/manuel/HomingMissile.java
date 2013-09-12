package cate.agustin.manuel;

import org.mini2Dx.core.game.GameContainer;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;

public class HomingMissile extends DirectionalBullet{
	
	protected PlayerShip target;
	protected float lifeTime;

	public HomingMissile(float X, float Y, Sprite sprite, PlayerShip target, float speed, GameContainer gc, int damage){
		super(X, Y, sprite, target.getPosition(), speed, gc, damage);
		
		this.target = target;
		this.lifeTime = 5;
		
	}
	
	@Override
	public void updatePosition(float delta) {
		
		this.direction_v = target.getPosition().cpy().add(position.cpy().mul(-1)).nor();
		
		position.add(direction_v.cpy().mul(delta * speed));
		sprite.setPosition(position.x, position.y);
		poly.setPosition(position.x, position.y);
		
		if((lifeTime -= delta) <= 0){
			delete = true;
		}

	}

}
