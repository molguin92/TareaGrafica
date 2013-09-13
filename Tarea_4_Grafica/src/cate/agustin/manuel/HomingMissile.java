package cate.agustin.manuel;

import java.util.List;

import org.mini2Dx.core.game.GameContainer;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;

public class HomingMissile extends DirectionalBullet{
	
	protected float lifeTime;
	protected List<SpaceObject> targetList;
	protected Vector2 target_pos;
	protected EnemyManager eManager;
	private int managerPresent;
	private int range;

	public HomingMissile(float X, float Y, Sprite sprite, SpaceObject firsttarget, float speed, GameContainer gc, 
			int damage, int lifeTime, List<SpaceObject> targetList, int range){
		super(X, Y, sprite, firsttarget.getPosition(), speed, gc, damage);
		
		this.target_pos = firsttarget.getPosition();
		this.lifeTime = lifeTime;
		this.targetList = targetList;
		this.managerPresent = 0;
		this.range = range;
		
	}
	
	public HomingMissile(float X, float Y, Sprite sprite, SpaceObject firsttarget, float speed, GameContainer gc, 
			int damage, int lifeTime, EnemyManager eManager, int range){
		super(X, Y, sprite, firsttarget.getPosition(), speed, gc, damage);
		
		this.target_pos = firsttarget.getPosition();
		this.lifeTime = lifeTime;
		this.eManager = eManager;
		this.managerPresent = 1;
		this.range = range;
		
	}
	
	@Override
	public void updatePosition(float delta) {
		switch (managerPresent) {
			case 0:
				for(SpaceObject newtarget: targetList){
					if(target_pos == null || position.dst(newtarget.getPosition()) < position.dst(target_pos)){
						target_pos = newtarget.getPosition();
					}			
				}
				break;
			case 1:
				for(SpaceObject newtarget: eManager.getEnemies()){
					if(target_pos == null || position.dst(newtarget.getPosition()) < position.dst(target_pos)){
						target_pos = newtarget.getPosition();
					}			
				}
				break;
		}
		
		if(target_pos == null || position.dst(target_pos) > range){
			this.delete = true;
			return;
		}
		
		this.direction_v = target_pos.cpy().add(position.cpy().mul(-1)).nor();
		
		position.add(direction_v.cpy().mul(delta * speed));
		sprite.setPosition(position.x, position.y);
		poly.setPosition(position.x, position.y);
		
		if((lifeTime -= delta) <= 0){
			delete = true;
		}
		
		target_pos = null;

	}

}
