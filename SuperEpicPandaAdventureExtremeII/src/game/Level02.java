package game;

import java.util.Random;

import org.newdawn.slick.*;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.tiled.TiledMap;

public class Level02 extends BasicGameState {
	
	private static TiledMap map;
	private static int iceLayer, holeLayer, finishLayer;
	private static int x, y, subLevel;
	public static boolean quit, dead, flag, flag2;
	public static Music music;
	public static Animation panda;
	public static Input input;
	public int i, j;
	public static boolean[][] iceTiles;
	
	public Level02(int i) {
	}
	
	@Override
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
		subLevel = 1;
		setMap();
		iceLayer = map.getLayerIndex("Ice");
		holeLayer = map.getLayerIndex("Hole");
		finishLayer = map.getLayerIndex("Finish");
		flag = false;
		flag2 = false;
		music = new Music("res/oggs/music.ogg");
		panda = Game.pandaStillUp;
		x = 3;
		y = 16;
		input = gc.getInput();
		initIceTiles();
		i = 0;
		j = 0;
	}
	
	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
		/**
		 * x location to render at, y location to render at, the x tile
		 * location,to start rendering, the y tile location to start rendering,
		 * width of section to render, height of section to render
		 */
		
		map.render(0, 0, 0, 0, 20, 20, holeLayer, true);
		for (int i = 0; i < 20; i++) {
			for (int j = 0; j < 20; j++) {
				if (iceTiles[i][j]) map.render(i * 32, j * 32, i, j, 1, 1, iceLayer, true);
			}
		}
		map.render(0, 0, 0, 0, 20, 20, finishLayer, true);
		
		panda.draw((int) x * 32, (int) y * 32, 32, 32);
		g.setColor(Color.red);
		g.drawString("" + flag2, 0, 0);
		// g.drawString("" + j, 0, 30);
		
		if (dead) {
			g.drawString("FAILED!!!", 280, 310);
			if (j == i) {
				j = 0;
				i = 0;
				dead = false;
			}
		}
	}
	
	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int t) throws SlickException {
		i++;
		i %= 100;
		// up
		if (input.isKeyPressed(Input.KEY_UP)) {
			iceTiles[x][y] = false;
			if ((int) y > 0) y -= 1;
			panda = Game.pandaStillUp;
		}
		
		// down
		if (input.isKeyPressed(Input.KEY_DOWN)) {
			iceTiles[x][y] = false;
			if ((int) y < map.getHeight() - 1) y += 1;
			panda = Game.pandaStillDown;
		}
		
		// left
		if (input.isKeyPressed(Input.KEY_LEFT)) {
			iceTiles[x][y] = false;
			if ((int) x > 0) x -= 1;
			panda = Game.pandaStillLeft;
		}
		
		// right
		if (input.isKeyPressed(Input.KEY_RIGHT)) {
			iceTiles[x][y] = false;
			if ((int) x < map.getWidth() - 1) x += 1;
			panda = Game.pandaStillRight;
		}
		
		// punch
		if (input.isKeyPressed(Input.KEY_SPACE)) {
			Random rand = new Random();
			int i = rand.nextInt(40);
			if (i == 0) Game.pandaPunch.play();
			if (i == 1) Game.sadPanda.play();
			if (i == 2) Game.itsPanda.play();
		}
		
		// enter next level
		if (map.getTileId((int) x, (int) y, finishLayer) != 0) {
			flag = false;
			for (int x = 0; x < 20; x++) {
				for (int y = 0; y < 20; y++) {
					if (iceTiles[x][y]) flag = true;
					if (flag) flag2 = true;
				}
			}
			if (!flag) {
				if (subLevel != 3) {
					subLevel++;
					setMap();
					resetState();
				} else {
					Game.pet2Found = true;
					sbg.enterState(Game.roam);
					x = 0;
					y = 0;
					resetState();
					music.loop();
				}
			}
		}
		
		// check for dead
		if (!iceTiles[(int) x][(int) y] || flag) {
			dead = true;
			flag = false;
			resetState();
			i = 1;
			j = 0;
			panda = Game.pandaStillUp;
		}
		
	}
	
	@Override
	public int getID() {
		// TODO Auto-generated method stub
		return Game.lvl02;
	}
	
	public static void resetState() {
		music.stop();
		x = 3;
		y = 16;
		quit = false;
		panda = Game.pandaStillUp;
		initIceTiles();
	}
	
	public static void initIceTiles() {
		iceTiles = new boolean[20][20];
		for (int x = 0; x < 20; x++) {
			for (int y = 0; y < 20; y++)
				if (map.getTileId(x, y, iceLayer) != 0) iceTiles[x][y] = true;
				else
					iceTiles[x][y] = false;
					
		}
	}
	
	public static void setMap() throws SlickException {
		map = null;
		switch (subLevel) {
			case 1:
				map = new TiledMap("res/maps/Level02-01.tmx");
				break;
			case 2:
				map = new TiledMap("res/maps/Level02-02.tmx");
				break;
			case 3:
				map = new TiledMap("res/maps/Level02-03.tmx");
				break;
		}
		initIceTiles();
	}
}
