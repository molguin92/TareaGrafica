package cate.agustin.manuel;

import org.mini2Dx.core.game.ScreenBasedGame;

public class MainGame extends ScreenBasedGame {

	@Override
	public int getInitialScreenId() {
		return InGame.ID;
	}

	@Override
	public void initialise() {
		this.addScreen(new InGame());
	}

}
