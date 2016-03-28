package game;

import org.newdawn.slick.*;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.tiled.TiledMap;
import game.Game;

public class Level01 extends BasicGameState {
	
	private TiledMap map;
	private Input input;
	private Animation panda;
	public static Music music;
	public char last;
	private Image im;
	
	public final int tileHeight, tileWidth, pandaHeight, pandaWidth;
	public final float zoomFactor;
	private int i;
	private int mapXBound, mapYBound;
	private int objectLayer, enterStateLayer;
	private float mapXL, mapXR;
	
	private float x, y;
	
	private boolean isDead, isJumping, isFalling;
	
	@Override
	public int getID() {
		return Game.lvl01;
	}
	
	public Level01(int lvl01) {
		tileHeight = 8;
		tileWidth = 16;
		zoomFactor = 2.5f;
		pandaHeight = 30;
		pandaWidth = 21;
	}
	
	@Override
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
		Game.appgc.setDisplayMode((640), (560), false);
		map = new TiledMap("res/maps/lvlMario.tmx");
		im = new Image("res/maps/lvlMario.png");
		input = gc.getInput();
		music = Game.pollyWolly;
		setState();
	}
	
	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
		renderMap(gc, sbg, g);
		// g.setColor(Color.white);
		// g.fillRect((x - (int) mapXL) * tileWidth * zoomFactor, y * tileHeight
		// * zoomFactor - 40, 40, 40);
		// g.setColor(Color.yellow);
		// g.fillRect((x - (int) mapXL) * tileWidth * zoomFactor, y * tileHeight
		// * zoomFactor - 32, 32, 32);
		// g.setColor(Color.red);
		// g.fillRect((x - (int) mapXL) * tileWidth * zoomFactor, y * tileHeight
		// * zoomFactor - pandaHeight, pandaWidth, pandaHeight);
		
		panda.draw((x - (1f / 6f) - (int) mapXL) * tileWidth * zoomFactor, y * tileHeight * zoomFactor - pandaHeight, 32, 32);
		
		drawInfo(gc, sbg, g);
	}
	
	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int t) throws SlickException {
		
		// left
		if (input.isKeyDown(Input.KEY_LEFT)) {
			panda = Game.pandaWalkLeft;
			last = 'l';
			if (y <= 1) x -= .03f;
			else if (x - .03f >= mapXL && map.getTileId((int) (x - 0.03f), (int) (y), objectLayer) == 0
					&& map.getTileId((int) (x - 0.03f), (int) (y - (pandaHeight / 20f)), objectLayer) == 0
					&& map.getTileId((int) (x - 0.03f), (int) (y - (pandaHeight / (tileHeight * zoomFactor))), objectLayer) == 0)
				x -= 0.03f;
		}
		// right
		if (input.isKeyDown(Input.KEY_RIGHT)) {
			panda = Game.pandaWalkRight;
			last = 'r';
			if (y <= 1) x += .03f;
			else if (map.getTileId((int) (x + 0.03f + (pandaWidth / (tileWidth * zoomFactor))), (int) (y), objectLayer) == 0
					&& map.getTileId((int) (x + 0.03f + (pandaWidth / (tileWidth * zoomFactor))),
							(int) (y - (pandaHeight / (tileHeight * zoomFactor))), objectLayer) == 0)
				x += 0.03f;
			if (x >= 7.5 + mapXL) {
				mapXR += 0.03f;
				mapXL += 0.03f;
			}
		}
		
		// i increment
		if (i == 0) {
			isJumping = false;
		}
		if (i != 0) {
			i++;
			i %= 250;
		}
		if (i >= 125) {
			isJumping = false;
			isFalling = true;
		}
		if (i > 0 && i < 125) {
			isJumping = true;
			isFalling = false;
		}
		// isJumping
		if (isJumping && !isFalling) {
			if (y <= 1) {
				y -= .07;
			} else if (map.getTileId((int) x, (int) (y - (pandaHeight / (tileHeight * zoomFactor)) - .07f), objectLayer) == 0
					&& map.getTileId((int) (x + (pandaWidth / (tileWidth * zoomFactor))),
							(int) (y - (pandaHeight / (tileHeight * zoomFactor)) - .07f), objectLayer) == 0) {
				y -= .07;
			}
		}
		// isFalling
		if (!isJumping) {
			if (y >= 28) isDead = true;
			else if (map.getTileId((int) x, (int) (y + 0.07), objectLayer) == 0
					&& map.getTileId((int) (x + (pandaWidth / (tileWidth * zoomFactor))), (int) (y + 0.07f), objectLayer) == 0) {
				y += .07;
			} else
				isFalling = false;
		}
		// jump Button
		if (input.isKeyPressed(Input.KEY_SPACE) && !isJumping && !isFalling) {
			i = 1;
			isJumping = true;
		}
		
		if (!(input.isKeyDown(Input.KEY_LEFT) || input.isKeyDown(Input.KEY_RIGHT))) {
			switch (last) {
				case 'l':
					panda = Game.pandaStillLeft;
					break;
				case 'r':
					panda = Game.pandaStillRight;
					break;
			}
		}
		/*
		 * if (!(x > 0 && x < mapXBound)) { if (x <= 0) x = 0; if (x >=
		 * map.getHeight()) x = mapXBound; } if (!(y > 0 && y < mapYBound)) { if
		 * (y <= 0) isDead = true; if (y >= mapYBound) y = mapYBound; }
		 */
		
		// enterState
		if (map.getTileId((int) x, (int) y, enterStateLayer) != 0) {
			Roam.pet1Found = true;
			Roam.charLock1 = '6';
			sbg.enterState(Game.roam);
			Game.appgc.setDisplayMode(640, 640, false);
		}
		
		// check isDead
		if (isDead) {
			resetState();
			sbg.enterState(Game.lvl01);
		}
	}
	
	public void resetState() {
		setState();
		isDead = false;
	}
	
	public void setState() {
		panda = Game.pandaStillDown;
		objectLayer = map.getLayerIndex("Objects");
		enterStateLayer = map.getLayerIndex("EnterState");
		mapXBound = 211;
		mapYBound = 29;
		mapXL = 0;
		mapXR = 17;
		i = 0;
		x = 2;
		y = 24;
	}
	
	private void drawInfo(GameContainer gc, StateBasedGame sbg, Graphics g) {
		g.setColor(Color.white);
		g.drawString("(x,y): ( " + x + " , " + y + ")", 100, 0);
		g.drawString("((int)x,(int)y): ( " + (int) x + " , " + (int) y + ")", 100, 15);
		g.drawString("[mapBound,mapYBound]: [ " + mapXBound + " , " + mapYBound + " ]", 100, 30);
		g.drawString("{mapXL,mapXR}: { " + mapXL + " , " + mapXR + " }", 100, 45);
		g.drawString("i = " + i, 100, 60);
		g.drawString("isFalling = " + isFalling, 400, 0);
		g.drawString("isJumping = " + isJumping, 400, 15);
		g.drawString("mapXL % 1 = " + mapXL % 1, 415, 30);
	}
	
	private void renderMap(GameContainer gc, StateBasedGame sbg, Graphics g) {
		/**
		 * x location to render at y location to render at the x tile location
		 * to start rendering the y tile location to start rendering width of
		 * section to render width of section to render
		 */
		g.scale(zoomFactor, zoomFactor);
		g.translate((-mapXL % 1) * tileWidth, 0);
		map.render(0, 0, (int) mapXL, 0, 21, 28);
		g.scale(1 / zoomFactor, 1 / zoomFactor);
	}
	
}
