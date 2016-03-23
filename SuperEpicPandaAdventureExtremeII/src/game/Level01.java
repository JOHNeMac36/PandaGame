package game;

import org.newdawn.slick.*;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.tiled.TiledMap;

public class Level01 extends BasicGameState {
	
	private TiledMap map;
	private Input input;
	private Animation panda;
	
	public static Music music;
	
	private int objectLayer, overheadLayer, enterStateLayer, i;
	private char last = 'd';
	private static double x, y;
	
	@Override
	public int getID() {
		return Game.lvl01;
	}
	
	public Level01(int lvl01) {
	}
	
	@Override
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
		map = new TiledMap("res/maps/mapHay.tmx");
		objectLayer = map.getLayerIndex("Objects");
		overheadLayer = map.getLayerIndex("Overhead");
		enterStateLayer = map.getLayerIndex("EnterState");
		input = gc.getInput();
		music = Game.pollyWolly;
		panda = Game.pandaStillDown;
		x = 0;
		y = 0;
	}
	
	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
		map.render(0, 0);
		panda.draw((int) x * 32, (int) y * 32, 32, 32);
		map.render(0, 0, overheadLayer);
	}
	
	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int t) throws SlickException {
		map.getTileId(0, 0, objectLayer);
		// up
		if (input.isKeyDown(Input.KEY_UP)) {
			panda = Game.pandaWalkUp;
			if ((int) y > 0) if (map.getTileId((int) x, (int) y - 1, objectLayer) == 0) y -= 0.014;
			last = 'u';
			
		}
		// down
		if (input.isKeyDown(Input.KEY_DOWN)) {
			panda = Game.pandaWalkDown;
			if ((int) y < map.getHeight() - 1)
				if (map.getTileId((int) x, (int) y + 1, objectLayer) == 0) y += 0.014;
			last = 'd';
		}
		// left
		if (input.isKeyDown(Input.KEY_LEFT)) {
			panda = Game.pandaWalkLeft;
			if ((int) x > 0) if (map.getTileId((int) x - 1, (int) y, objectLayer) == 0) x -= 0.014;
			last = 'l';
		}
		// right
		if (input.isKeyDown(Input.KEY_RIGHT)) {
			panda = Game.pandaWalkRight;
			if ((int) x < map.getWidth() - 1)
				if (map.getTileId((int) x + 1, (int) y, objectLayer) == 0) x += 0.014;
			last = 'r';
			
		}
		
		// punch
		if (input.isKeyPressed(Input.KEY_SPACE)) {
			i %= 16;
			if (i == 0) Game.pandaPunch.play();
			if (i == 4) Game.sadPanda.play();
			if (i == 8) Game.itsPanda.play();
			if (i == 12) Game.pandaIcecream.play();
			i++;
		}
		
		if (!(input.isKeyDown(Input.KEY_UP) || input.isKeyDown(Input.KEY_DOWN)
				|| input.isKeyDown(Input.KEY_LEFT) || input.isKeyDown(Input.KEY_RIGHT))) {
			switch (last) {
				case 'u':
					panda = Game.pandaStillUp;
					break;
				case 'd':
					panda = Game.pandaStillDown;
					break;
				case 'l':
					panda = Game.pandaStillLeft;
					break;
				case 'r':
					panda = Game.pandaStillRight;
					break;
			}
		}
		
		// enter next level
		if (map.getTileId((int) x, (int) y, enterStateLayer) != 0) {
			Roam.charLock1 = '6';
			Roam.pet1Found = true;
			sbg.enterState(Game.roam);
			resetState();
			music.loop();
		}
	}
	
	public static void resetState() {
		x = 0;
		y = 0;
	}
}
