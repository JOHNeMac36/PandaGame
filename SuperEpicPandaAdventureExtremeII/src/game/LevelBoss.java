package game;

import java.util.Random;

import org.newdawn.slick.Animation;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.tiled.TiledMap;

public class LevelBoss extends BasicGameState {
	
	private TiledMap map;
	private int objectLayer, overheadLayer, enterStateLayer;
	private char last = 'd';
	private static double x, y;
	public static boolean quit;
	public static Music music;
	public static Animation panda;
	
	public LevelBoss(int i) {
	}
	
	@Override
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
		map = new TiledMap("res/maps/mapLava.tmx");
		objectLayer = map.getLayerIndex("Objects");
		overheadLayer = map.getLayerIndex("Overhead");
		enterStateLayer = map.getLayerIndex("EnterState");
		music = new Music("res/oggs/music.ogg");
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
		Input input = gc.getInput();
		
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
			Random rand = new Random();
			int i = rand.nextInt(40);
			if (i == 0) Game.pandaPunch.play();
			if (i == 1) Game.sadPanda.play();
			if (i == 2) Game.itsPanda.play();
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
			Roam.charLock4 = '9';
			Roam.win = true;
			sbg.enterState(Game.roam);
			resetState();
		}
	}
	
	@Override
	public int getID() {
		// TODO Auto-generated method stub
		return Game.lvlBoss;
	}
	
	public static void resetState() {
		music.stop();
		x = 0;
		y = 0;
		quit = false;
	}
}
