package game;

import org.newdawn.slick.*;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.tiled.TiledMap;
import game.Game;

public class Level01 extends BasicGameState {
	
	private static TiledMap map;
	private static Input input;
	private static Animation panda;
	public static char last;
	private static Image im;
	
	public static int tileHeight, tileWidth, pandaHeight, pandaWidth;
	public static float zoomFactor;
	private static int i, totalTime, timeOfDeath, iMush01, menuSelection, oneHit = 0;
	private static int mapXBound, mapYBound;
	private static int objectLayer, enterStateLayer;
	private static float mapXL, mapXR;
	private static float mush01X, mush01Y;
	
	private static float x, y, cursorX = 76.6f, cursorY = 143f;
	
	private static boolean isDead, isJumping, isFalling, isMush01Dead, isMush01Falling, isUnderfoot;
	public static boolean isMenuUp = true;
	
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
		map = new TiledMap("res/maps/lvlMario.tmx");
		im = new Image("res/maps/lvlMario.png");
		input = gc.getInput();
		Game.appgc.setDisplayMode((640), (560), false);
		setState();
		totalTime = 0;
		mush01X = 5;
		mush01Y = 5;
		isUnderfoot = true;
	}
	
	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
		
		renderMap(gc, sbg, g);
		renderEnemies(gc, sbg, g);
		panda.draw((x - (1f / 6f) - (int) mapXL) * tileWidth * zoomFactor, y * tileHeight * zoomFactor - pandaHeight, 32, 32);
		
		drawInfo(gc, sbg, g);
		if (isMenuUp) renderMenu(gc, sbg, g);
	}
	
	public void renderMenu(GameContainer gc, StateBasedGame sbg, Graphics g) {
		g.scale(zoomFactor, zoomFactor);
		Game.marioTitle.draw();
		g.setColor(Color.white);
		
		Game.marioCursor.draw(cursorX, cursorY);
		g.scale(1 / zoomFactor, 1 / zoomFactor);
		
	}
	
	private void renderEnemies(GameContainer gc, StateBasedGame sbg, Graphics g) {
		
		// g.scale(zoomFactor, zoomFactor);
		Game.mushroom.draw((mush01X - (int) mapXL) * tileWidth * zoomFactor,
				(mush01Y) * tileHeight * zoomFactor - Game.mushroom.getHeight());
				
		// g.scale(1 / zoomFactor, 1 / zoomFactor);
	}
	
	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int t) throws SlickException {
		totalTime++;
		if (timeOfDeath != 0) {
			if (timeOfDeath == totalTime - 1000) Game.marioTheme.loop();
			if (timeOfDeath < totalTime - 1001) timeOfDeath = 0;
		}
		if (isMenuUp) {
			oneHit++;
			if (oneHit == 1) Game.marioStarman.loop();
			updateMenu(gc, sbg, t);
		} else {
			checkInGameMenu();
			
			checkMovement(gc, sbg, t);
			updateIncrements(gc, sbg, t);
			updateJumpingFalling(gc, sbg, t);
			checkEnterState(gc, sbg, t);
			checkIsDead(gc, sbg, t);
			enemyMovement();
		}
	}
	
	private void checkInGameMenu() {
		if (input.isKeyPressed(Input.KEY_ESCAPE)) {
			isMenuUp = true;
		}
	}
	
	private void checkIsDead(GameContainer gc, StateBasedGame sbg, int t) {
		// check isDead
		if (isDead) {
			Game.marioTheme.stop();
			Game.marioDead.play();
			resetState();
			timeOfDeath = totalTime;
			sbg.enterState(Game.lvl01);
		}
	}
	
	private static void checkEnterState(GameContainer gc, StateBasedGame sbg, int t) throws SlickException {
		// enterState
		if (map.getTileId((int) x, (int) y, enterStateLayer) != 0) {
			resetState();
			Game.pet1Found = true;
			sbg.enterState(Game.roam);
			Game.appgc.setDisplayMode(640, 640, false);
		}
	}
	
	private static void updateJumpingFalling(GameContainer gc, StateBasedGame sbg, int t) {
		// isJumping
		if (isJumping && !isFalling) {
			if (y <= 1) {
				switch (last) {
					case 'r':
						panda = Game.marioPandaJumpR;
						break;
					case 'l':
						panda = Game.marioPandaJumpL;
						break;
				}
				y -= .07;
			} else if (map.getTileId((int) x, (int) (y - (pandaHeight / (tileHeight * zoomFactor)) - .07f), objectLayer) == 0
					&& map.getTileId((int) (x + (pandaWidth / (tileWidth * zoomFactor))),
							(int) (y - (pandaHeight / (tileHeight * zoomFactor)) - .07f), objectLayer) == 0) {
				switch (last) {
					case 'l':
						panda = Game.marioPandaJumpL;
						break;
					case 'r':
						panda = Game.marioPandaJumpR;
						break;
				}
				y -= .07;
			} else
				i = 125;
		}
		// isUnderFoot
		if (!isJumping) {
			if (y >= 28) isDead = true;
			else if (map.getTileId((int) x, (int) (y + 0.07f), objectLayer) == 0
					&& map.getTileId((int) (x + (pandaWidth / (tileWidth * zoomFactor))), (int) (y + 0.07f), objectLayer) == 0) {
				switch (last) {
					case 'r':
						panda = Game.marioPandaJumpR;
						if (input.isKeyDown(Input.KEY_DOWN)) panda = Game.marioPandaDownFallR;
						break;
					case 'l':
						panda = Game.marioPandaJumpL;
						if (input.isKeyDown(Input.KEY_DOWN)) panda = Game.marioPandaDownFallL;
						break;
				}
				isUnderfoot = false;
				y += .07f;
			} else
				isFalling = false;
		}
		if (map.getTileId((int) x, (int) (y + 0.07f), objectLayer) != 0
				|| map.getTileId((int) (x - .1 + (pandaWidth / (tileWidth * zoomFactor))), (int) (y + 0.07f), objectLayer) != 0)
			isUnderfoot = true;
			
		if (isJumping) isUnderfoot = false;
		if (isFalling) isUnderfoot = false;
		
	}
	
	private static void updateIncrements(GameContainer gc, StateBasedGame sbg, int t) {
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
		
		if (isFalling) isJumping = false;
		
	}
	
	private static void updateMenu(GameContainer gc, StateBasedGame sbg, int t) throws SlickException {
		
		if (input.isKeyDown(Input.KEY_UP)) {
			cursorY = 143f;
			menuSelection = 1;
		}
		if (input.isKeyDown(Input.KEY_DOWN)) {
			cursorY = 159f;
			menuSelection = 2;
		}
		if (input.isKeyDown(Input.KEY_ENTER) || input.isKeyPressed(Input.KEY_SPACE)) {
			switch (menuSelection) {
				case 1:
					Game.marioStarman.stop();
					Game.marioTheme.play();
					isMenuUp = false;
					break;
				case 2:
					Game.marioStarman.stop();
					sbg.enterState(Game.roam);
					Game.appgc.setDisplayMode(640, 640, false);
					break;
			}
		}
		
	}
	
	private static void enemyMovement() {
		
		if (!isMush01Falling && totalTime % 1000 < 500) {
			mush01X += .001f;
		}
		if (!isMush01Falling && totalTime % 1000 >= 500) {
			mush01X -= .001f;
		}
		
		// isMush01Falling
		
		if (mush01Y >= 28) isDead = true;
		else if (map.getTileId((int) mush01X, (int) (mush01Y + 0.07), objectLayer) == 0
				&& map.getTileId((int) (mush01X + (Game.mushroom.getWidth() / (tileWidth * zoomFactor))), (int) (mush01Y + 0.07f),
						objectLayer) == 0) {
			mush01Y += .07;
		} else
			isMush01Falling = false;
	}
	
	public static void resetState() {
		setState();
		isDead = false;
		isMenuUp = true;
	}
	
	public static void setState() {
		panda = Game.marioPandaStillR;
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
	
	private static void drawInfo(GameContainer gc, StateBasedGame sbg, Graphics g) {
		g.setColor(Color.white);
		/*
		 * g.drawString("(x,y): ( " + x + " , " + y + ")", 100, 0);
		 * g.drawString("((int)x,(int)y): ( " + (int) x + " , " + (int) y + ")",
		 * 100, 15); g.drawString("[mapBound,mapYBound]: [ " + mapXBound + " , "
		 * + mapYBound + " ]", 100, 30); g.drawString("{mapXL,mapXR}: { " +
		 * mapXL + " , " + mapXR + " }", 100, 45); g.drawString("i = " + i, 100,
		 * 60); g.drawString(" iMush01 = " + iMush01, 100, 90); g.drawString(
		 * "isFalling = " + isFalling, 400, 0); g.drawString("isJumping = " +
		 * isJumping, 400, 15); g.drawString("mapXL % 1 = " + mapXL % 1, 415,
		 * 30); g.drawString("|mush01X,mush01Y|: | " + mush01X + " , " + mush01Y
		 * + " |", 200, 60); g.drawString("|isMush01Falling|: | " +
		 * isMush01Falling + " |", 100, 75); g.drawString("isUnderfoot = " +
		 * isUnderfoot, 100, 100);
		 */
		g.drawString("(cursorX,cursorY): ( " + cursorX + " , " + cursorY + ")", 100, 0);
		g.drawString("(totalTime,timeOD): ( " + totalTime + " , " + timeOfDeath + ")", 100, 15);
	}
	
	private static void renderMap(GameContainer gc, StateBasedGame sbg, Graphics g) {
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
	
	private static void checkMovement(GameContainer gc, StateBasedGame sbg, int t) {
		// jump Button
		if (input.isKeyPressed(Input.KEY_SPACE) && !isJumping && !isFalling && isUnderfoot) {
			i = 1;
			isJumping = true;
		}
		if (!(input.isKeyDown(Input.KEY_LEFT) || input.isKeyDown(Input.KEY_RIGHT) || isJumping || isFalling)) {
			switch (last) {
				case 'l':
					panda = Game.marioPandaStillL;
					break;
				case 'r':
					panda = Game.marioPandaStillR;
					break;
			}
		}
		
		// left
		if (input.isKeyDown(Input.KEY_LEFT) && !input.isKeyDown(Input.KEY_DOWN)) {
			panda = Game.marioPandaWalkLeft;
			last = 'l';
			if (y <= 1) x -= .03f;
			else if (x - .03f >= mapXL && map.getTileId((int) (x - 0.03f), (int) (y), objectLayer) == 0
					&& map.getTileId((int) (x - 0.03f), (int) (y - (pandaHeight / 20f)), objectLayer) == 0
					&& map.getTileId((int) (x - 0.03f), (int) (y - (pandaHeight / (tileHeight * zoomFactor))), objectLayer) == 0)
				x -= 0.03f;
		}
		// right
		if (input.isKeyDown(Input.KEY_RIGHT) && !input.isKeyDown(Input.KEY_DOWN)) {
			panda = Game.marioPandaWalkRight;
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
		// down
		if (input.isKeyDown(Input.KEY_DOWN) && !isFalling && !isJumping) {
			if (last == 'l') panda = Game.marioPandaDownFallL;
			if (last == 'r') panda = Game.marioPandaDownFallR;
		}
	}
	
}
