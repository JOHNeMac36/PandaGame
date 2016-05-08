package game;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.tiled.TiledMap;

public class Level01 extends BasicGameState {
	
	private static TiledMap map;
	private static Input input;
	private static Animation panda;
	public static char last;
	public static int tileHeight, tileWidth, pandaHeight, pandaWidth;
	public static float zoomFactor;
	private static int i, totalTime, menuSelection, oneHit = 0, timeOfWin, oneHit3 = 0, oneHitWin = 0, oneHitWin2 = 0;
	private static int objectLayer, enterStateLayer;
	private static float mapXL;
	private static float powerUpX, powerUpY;
	public static Block[] blocks;
	private static float x, y, cursorX, cursorY;
	@SuppressWarnings("unused")
	private final static float enemySpeed = .0096f, powerUpSpeed = .0096f;
	private static Enemy[] enemies;
	public static char still = 's', hit = 'h', hitDown = 'd', broke = 'b', deadStill = 'S', deadHit = 'H', deadHitDown = 'D';
	
	private static boolean isDead, isJumping, isFalling, isUnderfoot, isStalled = false, isBig = false;
	public static boolean isMenuUp = true;
	
	private static boolean won = false;
	private static float zoomFactor2 = 2f;
	private static boolean isPowerUpAvailable;
	private static int timeOfDeath = 0;
	private static PowerUp[] powerUps;
	
	public Level01(int lvl01) {
	
	}
	
	@Override
	public int getID() {
		return Game.lvl01;
	}
	
	@Override
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
		map = new TiledMap("res/maps/lvlMario.tmx");
		tileHeight = 8;
		tileWidth = 16;
		zoomFactor = 2.5f;
		pandaHeight = 30;
		pandaWidth = 22;
		cursorX = 76.6f;
		cursorY = 143f;
		input = gc.getInput();
		initBlocks();
		initEnemies();
		initPowerUps();
		setState();
	}
	
	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
		renderMap(gc, sbg, g);
		renderPowerUps(gc, sbg, g);
		renderBlocks(gc, sbg, g);
		renderEnemies(gc, sbg, g);
		panda.draw((x - (1f / 6f) - (int) mapXL) * tileWidth * zoomFactor, y * tileHeight * zoomFactor - pandaHeight, 32, 32);
		
		renderInfo(gc, sbg, g);
		if (isMenuUp) renderMenu(gc, sbg, g);
		
	}
	
	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int t) throws SlickException {
		if (Game.isMusicOn) Game.unmuteAllMusic();
		else
			Game.muteAllMusic();
		totalTime++;
		if (won) {
			updateWinScene(gc, sbg, t);
		} else {
			if (isDead) {
				timeOfDeath = totalTime;
				resetState(gc, sbg);
				Game.stopAllMusic();
				Game.marioDead.play();
			}
			checkEnterState(gc, sbg, t);
			sleepHandling(gc, sbg, t);
			if ((isMenuUp || isStalled) && !isDead && (timeOfDeath == 0 || timeOfDeath + 1000 == totalTime)) {
				oneHit++;
				if (totalTime == 1) Game.marioStarman.loop();
				else {
					if (oneHit == 1) Game.marioDead.play();
					if (oneHit == 1000 && totalTime != 1000) Game.marioStarman.loop();
				}
				updateMenu(gc, sbg, t);
			} else {
				checkInGameMenu();
				updateMovement(gc, sbg, t);
				updateIncrements(gc, sbg, t);
				updateJumpingFalling(gc, sbg, t);
				updateBlocks();
				updatePowerUpMovement();
				checkIsDead(gc, sbg, t);
				updateEnemyMovement();
				checkEnemyDeath();
				checkPowerUp();
			}
		}
		
	}
	
	// supplementary methods
	private static void checkEnemyDeath() {
		for (int i = 0; i < enemies.length; i++) {
			if (!enemies[i].isDead)
				if (x >= enemies[i].x - .56 && x <= enemies[i].x + 1 && y - .5f < enemies[i].y && enemies[i].y - y <= 1.5 && isFalling)
					enemies[i].isDead = true;
		}
	}
	
	private static void checkEnterState(GameContainer gc, StateBasedGame sbg, int t) throws SlickException {
		// enterState
		if (map.getTileId((int) x, (int) y, enterStateLayer) != 0) {
			panda = Game.marioPandaDownFallR;
			updateWinScene(gc, sbg, t);
		}
	}
	
	private static void checkInGameMenu() {
		if (input.isKeyPressed(Input.KEY_ESCAPE)) {
			isMenuUp = true;
			if (oneHit3 == 0 & Game.isMusicOn) {
				Game.marioPause.play();
				cursorX = 76.6f;
			}
			oneHit3 = -1;
		}
	}
	
	private static void checkIsDead(GameContainer gc, StateBasedGame sbg, int t) throws SlickException {
		// check isDead
		for (int i = 0; i < enemies.length; i++) {
			if (!enemies[i].isDead && x >= enemies[i].x - .01 && x <= enemies[i].x + .01 && (int) y == (int) enemies[i].y) {
				isDead = true;
			}
		}
	}
	
	private static void checkPowerUp() {
		for (int i = 0; i < powerUps.length; i++) {
			if (!powerUps[i].isDead && x >= powerUps[i].x - .56 && x <= powerUps[i].x + .87 && Math.abs(y - powerUps[i].y) < 0.06) {
				powerUps[i].isDead = true;
			}
		}
	}
	
	private static void initBlocks() {
		blocks = new Block[43];
		blocks[0] = new QBlock(16, 17);
		blocks[1] = new QBlock(21, 17);
		blocks[2] = new QBlock(22, 9);
		blocks[3] = new QBlock(23, 17);
		blocks[4] = new QBlock(78, 17);
		blocks[5] = new QBlock(94, 9);
		blocks[6] = new QBlock(106, 17);
		blocks[7] = new QBlock(109, 9);
		blocks[8] = new QBlock(109, 17);
		blocks[9] = new QBlock(112, 17);
		blocks[10] = new QBlock(129, 9);
		blocks[11] = new QBlock(130, 9);
		blocks[12] = new QBlock(170, 17);
		
		blocks[13] = new BBlock(20, 17);
		blocks[14] = new BBlock(22, 17);
		blocks[15] = new BBlock(24, 17);
		blocks[16] = new BBlock(77, 17);
		blocks[17] = new BBlock(79, 17);
		blocks[18] = new BBlock(80, 9);
		blocks[19] = new BBlock(81, 9);
		blocks[20] = new BBlock(82, 9);
		blocks[21] = new BBlock(83, 9);
		blocks[22] = new BBlock(84, 9);
		blocks[23] = new BBlock(85, 9);
		blocks[24] = new BBlock(86, 9);
		blocks[25] = new BBlock(87, 9);
		blocks[26] = new BBlock(91, 9);
		blocks[27] = new BBlock(92, 9);
		blocks[28] = new BBlock(93, 9);
		blocks[29] = new BBlock(94, 17);
		blocks[30] = new BBlock(100, 17);
		blocks[31] = new BBlock(101, 17);
		blocks[32] = new BBlock(118, 17);
		blocks[33] = new BBlock(121, 9);
		blocks[34] = new BBlock(122, 9);
		blocks[35] = new BBlock(123, 9);
		blocks[36] = new BBlock(128, 9);
		blocks[37] = new BBlock(129, 17);
		blocks[38] = new BBlock(130, 17);
		blocks[39] = new BBlock(131, 9);
		blocks[40] = new BBlock(168, 17);
		blocks[41] = new BBlock(169, 17);
		blocks[42] = new BBlock(171, 17);
	}
	
	private static void initEnemies() {
		enemies = new Enemy[17];
		enemies[0] = new Goomba(22f, 24.999f);
		enemies[1] = new Goomba(40f, 24.999f);
		enemies[2] = new Goomba(51f, 24.999f);
		enemies[3] = new Goomba(52.5f, 24.999f);
		enemies[4] = new Goomba(80f, 8f);
		enemies[5] = new Goomba(82f, 8f);
		enemies[6] = new Goomba(97f, 24.999f);
		enemies[7] = new Goomba(98.5f, 24.999f);
		enemies[8] = new Goomba(107f, 24.999f); // koopa
		enemies[9] = new Goomba(114f, 24.999f);
		enemies[10] = new Goomba(115.5f, 24.999f);
		enemies[11] = new Goomba(124f, 24.999f);
		enemies[12] = new Goomba(125.5f, 24.999f);
		enemies[13] = new Goomba(128f, 24.999f);
		enemies[14] = new Goomba(129.5f, 24.999f);
		enemies[15] = new Goomba(174f, 24.999f);
		enemies[16] = new Goomba(175.5f, 24.999f);
		
	}
	
	private static void initPowerUps() {
		powerUps = new PowerUp[13];
		for (int i = 0; i < powerUps.length; i++) {
			switch (i) {
				case 2:
				case 5:
				case 6:
				case 11:
					powerUps[i] = new Mushroom(blocks[i].x, blocks[i].y - .06f);
					break;
				case 9:
					powerUps[i] = new Star(blocks[i].x, blocks[i].y - .06f);
					break;
				default:
					powerUps[i] = new Coin(blocks[i].x, blocks[i].y - .06f);
					break;
			}
		}
	}
	
	private static void renderBlocks(GameContainer gc, StateBasedGame sbg, Graphics g) {
		g.scale(zoomFactor, zoomFactor);
		
		for (int i = 0; i < blocks.length; i++) {
			switch (blocks[i].type) {
				case Block.Q:
					if (blocks[i].x >= mapXL - 1 && blocks[i].x <= mapXL + 25) {
						switch (blocks[i].state) {
							case Block.STILL:
								Game.qBlockStill.draw((blocks[i].x - (int) mapXL) * tileWidth * zoomFactor / zoomFactor,
										(blocks[i].y) * tileHeight * zoomFactor / zoomFactor);
								break;
							case Block.HIT:
								Game.qBlockDeadStill.draw((blocks[i].x - (int) mapXL) * tileWidth * zoomFactor / zoomFactor,
										(blocks[i].y) * tileHeight * zoomFactor / zoomFactor);
								break;
						}
					}
					break;
				case Block.B:
					if (blocks[i].x >= mapXL - 1 && blocks[i].x <= mapXL + 25)
						Game.bBlockStill.draw((blocks[i].x - (int) mapXL) * tileWidth * zoomFactor / zoomFactor,
								(blocks[i].y) * tileHeight * zoomFactor / zoomFactor);
					break;
			}
		}
		for (int i = 0; i < blocks.length; i++) {
		
		}
		g.scale(1 / zoomFactor, 1 / zoomFactor);
	}
	
	private static void renderEnemies(GameContainer gc, StateBasedGame sbg, Graphics g) {
		
		g.scale(zoomFactor2, zoomFactor2);
		for (int i = 0; i < enemies.length; i++) {
			if (!enemies[i].isDead) {
				Game.mushroom.draw((enemies[i].x - (int) mapXL) * tileWidth * zoomFactor / zoomFactor2,
						(enemies[i].y) * tileHeight * zoomFactor / zoomFactor2 - Game.mushroom.getHeight());
			}
		}
		
		g.scale(1 / zoomFactor2, 1 / zoomFactor2);
	}
	
	private static void renderInfo(GameContainer gc, StateBasedGame sbg, Graphics g) {
		g.setColor(Color.white);
		String str;
		str = "( " + (mapXL + (float) input.getMouseX() / (float) tileWidth / zoomFactor) + " , "
				+ (float) input.getMouseY() / (float) tileHeight / zoomFactor + " )";
		g.drawString(str, 100, 15);
		g.drawString("(x,y) : ( " + x + " , " + y + " )", 100, 90);
		g.drawString("(Math.abs(y - powerUps[1].y) ) : ( " + Math.abs(y - powerUps[1].y) + " )", 100, 50);
		
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
	
	private static void renderMenu(GameContainer gc, StateBasedGame sbg, Graphics g) {
		g.scale(zoomFactor, zoomFactor);
		Game.marioTitle.draw();
		g.setColor(Color.white);
		
		Game.marioCursor.draw(cursorX, cursorY);
		g.scale(1 / zoomFactor, 1 / zoomFactor);
		
	}
	
	private static void renderPowerUps(GameContainer gc, StateBasedGame sbg, Graphics g) {
		g.scale(zoomFactor, zoomFactor);
		for (int i = 0; i < powerUps.length; i++) {
			if (!powerUps[i].isDead) {
				switch (powerUps[i].type) {
					case PowerUp.COIN:
						Game.pwrCoin.draw((powerUps[i].x - (int) mapXL) * tileWidth * zoomFactor / zoomFactor,
								(powerUps[i].y) * tileHeight * zoomFactor / zoomFactor - Game.pwrCoin.getHeight());
						break;
					case PowerUp.FLOWER:
						Game.pwrFlower.draw((powerUps[i].x - (int) mapXL) * tileWidth * zoomFactor / zoomFactor,
								(powerUps[i].y) * tileHeight * zoomFactor / zoomFactor - Game.pwrFlower.getHeight());
						break;
					case PowerUp.MUSHROOM:
						Game.pwrMush.draw((powerUps[i].x - (int) mapXL) * tileWidth * zoomFactor / zoomFactor,
								(powerUps[i].y) * tileHeight * zoomFactor / zoomFactor - Game.pwrMush.getHeight());
						break;
					case PowerUp.STAR:
						Game.pwrStar.draw((powerUps[i].x - (int) mapXL) * tileWidth * zoomFactor / zoomFactor,
								(powerUps[i].y) * tileHeight * zoomFactor / zoomFactor - Game.pwrStar.getHeight());
						break;
				}
			}
		}
		g.scale(1 / zoomFactor, 1 / zoomFactor);
	}
	
	private static void resetState(GameContainer gc, StateBasedGame sbg) throws SlickException {
		isMenuUp = true;
		isStalled = false;
		isDead = false;
		won = false;
		isStalled = true;
		initBlocks();
		initEnemies();
		initPowerUps();
		input = gc.getInput();
		isUnderfoot = true;
		oneHit = 0;
		oneHit3 = 0;
		oneHitWin = 0;
		timeOfWin = 0;
		timeOfDeath = 0;
		setState();
	}
	
	private static void setState() {
		panda = Game.marioPandaStillR;
		objectLayer = map.getLayerIndex("Objects");
		enterStateLayer = map.getLayerIndex("EnterState");
		mapXL = 0;
		i = 0;
		x = 2;
		y = 24.99f;
		cursorY = 143f;
		isUnderfoot = true;
	}
	
	private static void sleepHandling(GameContainer gc, StateBasedGame sbg, int t) throws SlickException {
		if (sbg.getCurrentStateID() == Game.lvl01) {
			Game.appgc.setDisplayMode(640, 560, false);
		} else {
			Game.appgc.setDisplayMode(640, 640, false);
			oneHit = 0;
		}
	}
	
	private static void updateBlocks() {
		for (int i = 0; i < 13; i++) {
			if ((int) (y - 3.01f) == blocks[i].y && ((int) x == blocks[i].x || (int) (x + .5) == blocks[i].x) && !isFalling) {
				if (blocks[i].state == Block.STILL) {
					powerUps[i].isDead = false;
					powerUps[i].timeOfLife = totalTime;
					powerUps[i].isUp = true;
				}
				blocks[i].state = Block.HIT;
			}
			if (isFalling && input.isKeyDown(Input.KEY_DOWN) && (int) (y + .2f) == blocks[i].y
					&& (((int) x == blocks[i].x) || (int) (x + .5) == blocks[i].x)) {
					
				if (blocks[i].state == Block.STILL) {
					powerUps[i].isDead = false;
					powerUps[i].timeOfLife = totalTime;
					powerUps[i].isUp = false;
					powerUps[i].y += 1.9f;
				}
				blocks[i].state = Block.HIT;
			}
		}
	}
	
	private static void updateEnemyMovement() {
		for (int i = 0; i < enemies.length; i++) {
			if (!enemies[i].isDead && enemies[i].type == Enemy.GOOMBA) {
				if (enemies[i].x <= mapXL + 16f) {
					if (!enemies[i].isFalling) {
						if (enemies[i].isRight) {
							if (map.getTileId((int) (enemies[i].x + enemySpeed), (int) enemies[i].y, objectLayer) == 0)
								enemies[i].x += enemySpeed;
							if (enemies[i].isRight
									&& map.getTileId((int) (enemies[i].x + .8f + enemySpeed), (int) enemies[i].y, objectLayer) != 0)
								enemies[i].isRight = false;
						} else {
							if (map.getTileId((int) (enemies[i].x - enemySpeed), (int) enemies[i].y, objectLayer) == 0)
								enemies[i].x -= enemySpeed;
							if (!enemies[i].isRight
									&& map.getTileId((int) (enemies[i].x - enemySpeed), (int) enemies[i].y, objectLayer) != 0)
								enemies[i].isRight = true;
						}
						
					}
					
					if (enemies[i].x + .75 <= mapXL) enemies[i].isDead = true;
					
					if (!enemies[i].isDead) {
						if (enemies[i].y >= 28) enemies[i].isDead = true;
						else if (map.getTileId((int) enemies[i].x, (int) (enemies[i].y + 0.07), objectLayer) == 0
								&& map.getTileId((int) (enemies[i].x + (Game.mushroom.getWidth() / (tileWidth * zoomFactor2))),
										(int) (enemies[i].y + 0.07f), objectLayer) == 0) {
							enemies[i].y += .07f;
						} else
							enemies[i].isFalling = false;
					}
				}
			}
		}
		
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
		
		// powerUp
		if (isPowerUpAvailable) {
			powerUpX += .03f;
			if (map.getTileId((int) (powerUpX), (int) (powerUpY + .03f), objectLayer) != 0) {
				powerUpY += .03f;
			}
		}
	}
	
	private static void updateJumpingFalling(GameContainer gc, StateBasedGame sbg, int t) {
		// isJumping
		if (isJumping) {
			if (!won && (y <= 1 || map.getTileId((int) x, (int) (y - (pandaHeight / (tileHeight * zoomFactor)) - .07f), objectLayer) == 0
					&& map.getTileId((int) (x + (pandaWidth / (tileWidth * zoomFactor))),
							(int) (y - (pandaHeight / (tileHeight * zoomFactor)) - .07f), objectLayer) == 0)) {
				switch (last) {
					case 'l':
						panda = Game.marioPandaJumpL;
						break;
					case 'r':
						panda = Game.marioPandaJumpR;
						break;
				}
				y -= .07;
			} else if (won) {
				isJumping = false;
				isFalling = true;
				y += .07;
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
		if (isUnderfoot) {
			isFalling = false;
			isJumping = false;
		}
		
	}
	
	private static void updateMenu(GameContainer gc, StateBasedGame sbg, int t) throws SlickException {
		if (input.isKeyDown(Input.KEY_ENTER) || input.isKeyDown(Input.KEY_SPACE)) {
			switch (menuSelection) {
				case 1:
					Game.marioStarman.stop();
					if (oneHit3 == 0) Game.marioTheme.loop();
					isMenuUp = false;
					isStalled = false;
					break;
				case 2:
					Game.marioStarman.stop();
					sbg.enterState(Game.roam);
					Game.appgc.setDisplayMode(640, 640, false);
					break;
			}
		}
		if (input.isKeyPressed(Input.KEY_UP)) {
			cursorY = 143f;
			menuSelection = 1;
		}
		if (input.isKeyPressed(Input.KEY_DOWN)) {
			cursorY = 159f;
			menuSelection = 2;
		}
		input.clearKeyPressedRecord();
	}
	
	private static void updateMovement(GameContainer gc, StateBasedGame sbg, int t) {
		if (input.isKeyPressed(Input.KEY_SPACE) && !isJumping && !isFalling && isUnderfoot) {
			i = 1;
			isJumping = true;
			if (Game.isMusicOn) Game.marioJump.play();
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
				mapXL += 0.03f;
			}
		}
		// down
		if (input.isKeyDown(Input.KEY_DOWN) && !isFalling && !isJumping) {
			if (last == 'l') panda = Game.marioPandaDownFallL;
			if (last == 'r') panda = Game.marioPandaDownFallR;
		}
	}
	
	private static void updatePowerUpMovement() {
		for (int i = 0; i < powerUps.length; i++) {
			if (!powerUps[i].isDead) {
				switch (powerUps[i].type) {
					case PowerUp.COIN:
						if (powerUps[i].isUp) {
							if (powerUps[i].timeOfLife > totalTime - 75) powerUps[i].y -= .05f;
							if (powerUps[i].timeOfLife < totalTime - 75) powerUps[i].y += .05f;
							if (powerUps[i].timeOfLife == totalTime - 150) powerUps[i].isDead = true;
						} else {
							if (powerUps[i].timeOfLife > totalTime - 75) powerUps[i].y += .05f;
							if (powerUps[i].timeOfLife < totalTime - 75) powerUps[i].y -= .05f;
							if (powerUps[i].timeOfLife == totalTime - 150) powerUps[i].isDead = true;
						}
						break;
					case PowerUp.FLOWER:
						if (powerUps[i].isUp) {
							if (powerUps[i].timeOfLife > totalTime - 75) powerUps[i].y -= .05f;
						} else {
							if (powerUps[i].timeOfLife > totalTime - 75) powerUps[i].y += .05f;
						}
						break;
					default:
						if (map.getTileId((int) (powerUps[i].x), (int) (powerUps[i].y - .07), objectLayer) != 0) {
							powerUps[i].y += .07;
						} else {
							if (powerUps[i].x <= mapXL + 16f) {
								if (!powerUps[i].isFalling) {
									if (powerUps[i].isRight) {
										if (map.getTileId((int) (powerUps[i].x + powerUpSpeed), (int) powerUps[i].y, objectLayer) == 0)
											powerUps[i].x += powerUpSpeed;
										if (powerUps[i].isRight && map.getTileId((int) (powerUps[i].x + .8 + powerUpSpeed),
												(int) powerUps[i].y, objectLayer) != 0)
											powerUps[i].isRight = false;
									} else {
										if (map.getTileId((int) (powerUps[i].x - powerUpSpeed), (int) powerUps[i].y, objectLayer) == 0)
											powerUps[i].x -= powerUpSpeed;
										if (!powerUps[i].isRight && map.getTileId((int) (powerUps[i].x - powerUpSpeed), (int) powerUps[i].y,
												objectLayer) != 0)
											powerUps[i].isRight = true;
									}
									
								}
								
								if (powerUps[i].x + .75 <= mapXL || powerUps[i].x >= mapXL + 15.975) powerUps[i].isDead = true;
								
								if (!powerUps[i].isDead) {
									if (powerUps[i].y >= 28) powerUps[i].isDead = true;
									else if (map.getTileId((int) powerUps[i].x, (int) (powerUps[i].y + 0.07), objectLayer) == 0
											&& map.getTileId((int) (powerUps[i].x + (Game.mushroom.getWidth() / (tileWidth * zoomFactor2))),
													(int) (powerUps[i].y + 0.07f), objectLayer) == 0) {
										powerUps[i].y += .07f;
									} else
										powerUps[i].isFalling = false;
								}
							}
						}
				}
			}
		}
	}
	
	private static void updateWinScene(GameContainer gc, StateBasedGame sbg, int t) throws SlickException {
		won = true;
		oneHitWin++;
		
		if (oneHitWin == 1) {
			if (Game.isMusicOn) Game.marioFlag.play();
			Game.marioTheme.stop();
		}
		
		if (!isUnderfoot) {
			if (oneHitWin == 1) isJumping = false;
			updateJumpingFalling(gc, sbg, t);
			panda = Game.marioPandaBigDownFallR;
		} else {
			oneHitWin2++;
			if (oneHitWin2 == 1) {
				timeOfWin = totalTime;
				Game.marioFlag.stop();
				Game.marioCastleComplete.play();
			}
			if (map.getTileId((int) (x + 0.03f + (pandaWidth / (tileWidth * zoomFactor))), (int) (y), objectLayer) == 0
					&& map.getTileId((int) (x + 0.03f + (pandaWidth / (tileWidth * zoomFactor))),
							(int) (y - (pandaHeight / (tileHeight * zoomFactor))), objectLayer) == 0) {
				x += .03f;
				panda = Game.marioPandaWalkRight;
				updateJumpingFalling(gc, sbg, t);
			} else {
				panda = Game.marioPandaStillR;
			}
		}
		
		if (timeOfWin == totalTime - 2000) {
			sbg.enterState(Game.roam);
			Game.pet1Found = true;
			Game.charLock1 = '6';
			Game.pollyWolly.loop();
			Game.appgc.setDisplayMode(640, 640, false);
			resetState(gc, sbg);
		}
	}
}

// subclasses
class BBlock extends Block {
	
	public BBlock(float x, float y) {
		super(x, y);
		type = Block.B;
	}
	
}

abstract class Block {
	public float x, y;
	public int type, state;
	public static final int Q = 0, B = 1, STILL = 2, HIT = 3, HITDOWN = 4, BROKE = 5, DEADSTILL = 6, DEADHIT = 7, DEADHITDOWN = 9;
	
	public Block(float x, float y) {
		this.x = x;
		this.y = y;
		this.state = STILL;
	}
	
}

class Coin extends PowerUp {
	@SuppressWarnings("static-access")
	public Coin(float x, float y) {
		super(x, y);
		type = super.COIN;
	}
}

abstract class Enemy {
	public float x, y;
	public boolean isFalling, isDead, isRight;
	public int type;
	public static final int GOOMBA = 0, KOOPA_TROOPA = 1;
	
	public Enemy(float x, float y) {
		this.x = x;
		this.y = y;
		isFalling = true;
		isDead = false;
		isRight = false;
	}
	
	@Override
	public String toString() {
		return "Enemy x=" + x + ", y" + y + ",type" + type + "]";
	}
}

abstract class PowerUp {
	public float x, y, timeOfLife;
	public int type;
	public boolean isDead, isFalling, isRight, isUp;
	public static final int COIN = 0, MUSHROOM = 1, FLOWER = 2, STAR = 3;
	
	public PowerUp(float x, float y) {
		this.x = x;
		this.y = y;
		this.isDead = true;
		this.isFalling = false;
		this.isRight = true;
		this.isUp = true;
	}
}

class Flower extends PowerUp {
	@SuppressWarnings("static-access")
	
	public Flower(float x, float y) {
		super(x, y);
		type = super.FLOWER;
	}
	
}

class Goomba extends Enemy {
	
	public Goomba(float x, float y) {
		super(x, y);
		type = super.GOOMBA;
	}
	
}

class Mushroom extends PowerUp {
	@SuppressWarnings("static-access")
	public Mushroom(float x, float y) {
		super(x, y);
		type = super.MUSHROOM;
	}
}

class QBlock extends Block {
	
	public QBlock(float x, float y) {
		super(x, y);
		type = Block.Q;
	}
}

class Star extends PowerUp {
	@SuppressWarnings("static-access")
	public Star(float x, float y) {
		super(x, y);
		type = super.STAR;
	}
}

class KoopaTroopa extends Enemy {
	public boolean isShell;
	
	public KoopaTroopa(float x, float y) {
		super(x, y);
		type = Enemy.KOOPA_TROOPA;
		isShell = false;
	}
	
}