package cate.agustin.manuel;

import org.mini2Dx.core.game.GameContainer;

public class LevelManager {
	protected GameContainer gc;
	protected EnemyManager eman;
	protected float delta;
	protected float counter;
	LevelManager(GameContainer gc, EnemyManager eman, float delta){
		this.gc=gc;
		this.eman=eman;
		this.delta=delta;
		counter=0;
	}
	public void startLevel(){
		
	}

}
