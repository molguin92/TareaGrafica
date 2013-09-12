package cate.agustin.manuel;

import org.mini2Dx.core.game.GameContainer;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;

public class DirectionalBullet extends Bullet{

	/** Bala avanzada, capaz de moverse en diagonal. */
	
	protected Vector2 direction_v;
	protected GameContainer gc;

	public DirectionalBullet(float X, float Y, Sprite sprite, Vector2 target, float speed, GameContainer gc, int damage) {
		super(X, Y, sprite, 1, speed, damage);
		
		this.direction_v = target.cpy().add(-1 * X, -1 * Y).nor();
		this.gc = gc;

	}
	
	@Override
	public void updatePosition(float delta) {
		
		position.add(direction_v.cpy().mul(delta * speed));
		sprite.setPosition(position.x, position.y);
		poly.setPosition(position.x, position.y);
		
		if(position.y < 0 || position.x < 0 || position.y > gc.getHeight() || position.x > gc.getWidth()){
			delete = true;
		}

	}

}
