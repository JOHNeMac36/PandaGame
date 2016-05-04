package game;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Point;
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
	private static int i, totalTime, menuSelection, oneHit = 0, timeOfWin,
			oneHit3 = 0, oneHitWin = 0, oneHitWin2 = 0;
	private static int objectLayer, enterStateLayer;
	private static float mapXL;
	private static float powerUpX, powerUpY;
	public static Block[] blocks;
	private static float x, y, cursorX = 76.6f, cursorY = 143f;

	private static Point[] enemyCoords;
	private static boolean[] isEnemyDead, isEnemyFalling, enemyRight;

	public static char still = 's', hit = 'h', hitDown = 'd', broke = 'b',
			deadStill = 'S', deadHit = 'H', deadHitDown = 'D';

	private static boolean isDead, isJumping, isFalling, isUnderfoot,
			isStalled = false, isBig = false;
	public static boolean isMenuUp = true;

	private static boolean won = false;
	private static float zoomFactor2 = 2f;
	private static boolean isPowerUpAvailable;
	private static int timeOfDeath = 0;
	private static boolean[] powerUps;
	private static final float minWalkVel = toHex('0', '0', '1', '3', '0'),
			walkAccel = toHex('0', '0', '0', '9', '8'), runAccel = toHex('0',
					'0', '0', 'E', '4'), releaseDecel = toHex('0', '0', '0',
					'D', '0'), skidDecel = toHex('0', '0', '1', 'A', '0'),
			maxWalkVel = toHex('0', '2', '9', '0', '0'), maxRunVel = toHex('0',
					'2', '9', '0', '0'), skidTurnaroundVel = toHex('0', '0',
					'9', '0', '0');
	private static float xV, yV;

	public Level01(int lvl01) {

	}

	private static float toHex(char b, char p, char s, char ss, char sss) {
		int block = 0, pixel = 0, subPix = 0, subSubPix = 0, subSubSubPix = 0;
		switch (b) {
		case '0':
			block = 0;
			break;
		case '1':
			block = 1;
			break;
		case '2':
			block = 2;
			break;
		case '3':
			block = 3;
			break;
		case '4':
			block = 4;
			break;
		case '5':
			block = 5;
			break;
		case '6':
			block = 6;
			break;
		case '7':
			block = 7;
			break;
		case '8':
			block = 8;
			break;
		case '9':
			block = 9;
			break;
		case 'A':
			block = 10;
			break;
		case 'B':
			block = 11;
			break;
		case 'C':
			block = 12;
			break;
		case 'D':
			block = 13;
			break;
		case 'E':
			block = 14;
			break;
		case 'F':
			block = 15;
			break;
		}
		switch (p) {
		case '0':
			pixel = 0;
			break;
		case '1':
			pixel = 1;
			break;
		case '2':
			pixel = 2;
			break;
		case '3':
			pixel = 3;
			break;
		case '4':
			pixel = 4;
			break;
		case '5':
			pixel = 5;
			break;
		case '6':
			pixel = 6;
			break;
		case '7':
			pixel = 7;
			break;
		case '8':
			pixel = 8;
			break;
		case '9':
			pixel = 9;
			break;
		case 'A':
			pixel = 10;
			break;
		case 'B':
			pixel = 11;
			break;
		case 'C':
			pixel = 12;
			break;
		case 'D':
			pixel = 13;
			break;
		case 'E':
			pixel = 14;
			break;
		case 'F':
			pixel = 15;
			break;
		}
		switch (s) {
		case '0':
			subPix = 0;
			break;
		case '1':
			subPix = 1;
			break;
		case '2':
			subPix = 2;
			break;
		case '3':
			subPix = 3;
			break;
		case '4':
			subPix = 4;
			break;
		case '5':
			subPix = 5;
			break;
		case '6':
			subPix = 6;
			break;
		case '7':
			subPix = 7;
			break;
		case '8':
			subPix = 8;
			break;
		case '9':
			subPix = 9;
			break;
		case 'A':
			subPix = 10;
			break;
		case 'B':
			subPix = 11;
			break;
		case 'C':
			subPix = 12;
			break;
		case 'D':
			subPix = 13;
			break;
		case 'E':
			subPix = 14;
			break;
		case 'F':
			subPix = 15;
			break;
		}
		switch (ss) {
		case '0':
			subSubPix = 0;
			break;
		case '1':
			subSubPix = 1;
			break;
		case '2':
			subSubPix = 2;
			break;
		case '3':
			subSubPix = 3;
			break;
		case '4':
			subSubPix = 4;
			break;
		case '5':
			subSubPix = 5;
			break;
		case '6':
			subSubPix = 6;
			break;
		case '7':
			subSubPix = 7;
			break;
		case '8':
			subSubPix = 8;
			break;
		case '9':
			subSubPix = 9;
			break;
		case 'A':
			subSubPix = 10;
			break;
		case 'B':
			subSubPix = 11;
			break;
		case 'C':
			subSubPix = 12;
			break;
		case 'D':
			subSubPix = 13;
			break;
		case 'E':
			subSubPix = 14;
			break;
		case 'F':
			subSubPix = 15;
			break;
		}
		switch (sss) {
		case '0':
			subSubSubPix = 0;
			break;
		case '1':
			subSubSubPix = 1;
			break;
		case '2':
			subSubSubPix = 2;
			break;
		case '3':
			subSubSubPix = 3;
			break;
		case '4':
			subSubSubPix = 4;
			break;
		case '5':
			subSubSubPix = 5;
			break;
		case '6':
			subSubSubPix = 6;
			break;
		case '7':
			subSubSubPix = 7;
			break;
		case '8':
			subSubSubPix = 8;
			break;
		case '9':
			subSubSubPix = 9;
			break;
		case 'A':
			subSubSubPix = 10;
			break;
		case 'B':
			subSubSubPix = 11;
			break;
		case 'C':
			subSubSubPix = 12;
			break;
		case 'D':
			subSubSubPix = 13;
			break;
		case 'E':
			subSubSubPix = 14;
			break;
		case 'F':
			subSubSubPix = 15;
			break;
		}

		return (float) (block * 16 + pixel + (float) subPix / 16
				+ (float) subSubPix / (16 * 2) + (float) subSubSubPix - (16 * 3));
	}

	@Override
	public int getID() {
		return Game.lvl01;
	}

	@Override
	public void init(GameContainer gc, StateBasedGame sbg)
			throws SlickException {
		map = new TiledMap("res/maps/lvlMario.tmx");
		tileHeight = 8;
		tileWidth = 16;
		zoomFactor = 2.5f;
		pandaHeight = 30;
		pandaWidth = 22;
		input = gc.getInput();
		initBlocks();
		initEnemies();
		setState();
	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g)
			throws SlickException {
		renderMap(gc, sbg, g);
		renderBlocks(gc, sbg, g);
		renderEnemies(gc, sbg, g);
		panda.draw((x - (1f / 6f) - (int) mapXL) * tileWidth * zoomFactor, y
				* tileHeight * zoomFactor - pandaHeight, 32, 32);

		renderInfo(gc, sbg, g);
		if (isMenuUp)
			renderMenu(gc, sbg, g);

	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int t)
			throws SlickException {
		if (Game.isMusicOn)
			Game.unmuteAllMusic();
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
			if ((isMenuUp || isStalled) && !isDead
					&& (timeOfDeath == 0 || timeOfDeath + 1000 == totalTime)) {
				oneHit++;
				if (totalTime == 1)
					Game.marioStarman.loop();
				else {
					if (oneHit == 1)
						Game.marioDead.play();
					if (oneHit == 1000 && totalTime != 1000)
						Game.marioStarman.loop();
				}
				updateMenu(gc, sbg, t);
			} else {
				checkInGameMenu();
				updateMovement(gc, sbg, t);
				updateIncrements(gc, sbg, t);
				updateJumpingFalling(gc, sbg, t);
				updateBlocks();
				checkIsDead(gc, sbg, t);
				updateEnemyMovement();
				checkEnemyDeath();
			}
		}

	}

	// supplementary methods
	private static void checkEnemyDeath() {
		for (int i = 0; i < enemyCoords.length; i++) {
			if (!isEnemyDead[i])
				if (x >= enemyCoords[i].getX() - .56
						&& x <= enemyCoords[i].getX() + 1
						&& y - .5f < enemyCoords[i].getY()
						&& enemyCoords[i].getY() - y <= 1.5 && isFalling)
					isEnemyDead[i] = true;
		}
	}

	private static void checkEnterState(GameContainer gc, StateBasedGame sbg,
			int t) throws SlickException {
		// enterState
		if (map.getTileId((int) x, (int) y, enterStateLayer) != 0) {
			panda = Game.marioPandaDownFallR;
			updateWinScene(gc, sbg, t);
		}
	}

	private static void checkInGameMenu() {
		if (input.isKeyPressed(Input.KEY_ESCAPE)) {
			isMenuUp = true;
			if (oneHit3 == 0 & Game.isMusicOn)
				Game.marioPause.play();
			oneHit3 = -1;
		}
	}

	private static void checkIsDead(GameContainer gc, StateBasedGame sbg, int t)
			throws SlickException {
		// check isDead
		for (int i = 0; i < isEnemyDead.length; i++) {
			if (!isEnemyDead[i] && x >= enemyCoords[i].getX() - .56
					&& x <= enemyCoords[i].getX() + .87
					&& (int) y == (int) enemyCoords[i].getY()) {
				isDead = true;
			}
		}
	}

	private static void initBlocks() {
		blocks = new Block[43];
		blocks[0] = new Block(16, 17, Block.Q);
		blocks[1] = new Block(21, 17, Block.Q);
		blocks[2] = new Block(22, 9, Block.Q);
		blocks[3] = new Block(23, 17, Block.Q);
		blocks[4] = new Block(78, 17, Block.Q);
		blocks[5] = new Block(94, 9, Block.Q);
		blocks[6] = new Block(106, 17, Block.Q);
		blocks[7] = new Block(109, 9, Block.Q);
		blocks[8] = new Block(109, 17, Block.Q);
		blocks[9] = new Block(112, 17, Block.Q);
		blocks[10] = new Block(129, 9, Block.Q);
		blocks[11] = new Block(130, 9, Block.Q);
		blocks[12] = new Block(170, 17, Block.Q);

		blocks[13] = new Block(20, 17, Block.B);
		blocks[14] = new Block(22, 17, Block.B);
		blocks[15] = new Block(24, 17, Block.B);
		blocks[16] = new Block(77, 17, Block.B);
		blocks[17] = new Block(79, 17, Block.B);
		blocks[18] = new Block(80, 9, Block.B);
		blocks[19] = new Block(81, 9, Block.B);
		blocks[20] = new Block(82, 9, Block.B);
		blocks[21] = new Block(83, 9, Block.B);
		blocks[22] = new Block(84, 9, Block.B);
		blocks[23] = new Block(85, 9, Block.B);
		blocks[24] = new Block(86, 9, Block.B);
		blocks[25] = new Block(87, 9, Block.B);
		blocks[26] = new Block(91, 9, Block.B);
		blocks[27] = new Block(92, 9, Block.B);
		blocks[28] = new Block(93, 9, Block.B);
		blocks[29] = new Block(94, 17, Block.B);
		blocks[30] = new Block(100, 17, Block.B);
		blocks[31] = new Block(101, 17, Block.B);
		blocks[32] = new Block(118, 17, Block.B);
		blocks[33] = new Block(121, 9, Block.B);
		blocks[34] = new Block(122, 9, Block.B);
		blocks[35] = new Block(123, 9, Block.B);
		blocks[36] = new Block(128, 9, Block.B);
		blocks[37] = new Block(129, 17, Block.B);
		blocks[38] = new Block(130, 17, Block.B);
		blocks[39] = new Block(131, 9, Block.B);
		blocks[40] = new Block(168, 17, Block.B);
		blocks[41] = new Block(169, 17, Block.B);
		blocks[42] = new Block(171, 17, Block.B);
	}

	private static void initEnemies() {
		enemyCoords = new Point[10];
		enemyCoords[0] = new Point(15f, 24.999f);
		enemyCoords[1] = new Point(30f, 24.999f);
		enemyCoords[2] = new Point(35f, 24.999f);
		enemyCoords[3] = new Point(45f, 24.999f);
		enemyCoords[4] = new Point(55f, 24.999f);
		enemyCoords[5] = new Point(65f, 24.999f);
		enemyCoords[6] = new Point(75f, 24.999f);
		enemyCoords[7] = new Point(171f, 16.9f);
		enemyCoords[8] = new Point(138f, 24.999f);
		enemyCoords[9] = new Point(102f, 17.1999f);

		isEnemyDead = new boolean[10];
		for (int i = 0; i < isEnemyDead.length; i++)
			isEnemyDead[i] = false;

		isEnemyFalling = new boolean[10];
		enemyRight = new boolean[10];
		for (int i = 0; i < enemyRight.length; i++)
			enemyRight[i] = false;

	}

	private static void renderBlocks(GameContainer gc, StateBasedGame sbg,
			Graphics g) {
		g.scale(zoomFactor, zoomFactor);

		for (int i = 0; i < blocks.length; i++) {
			switch (blocks[i].type) {
			case Block.Q:
				if (blocks[i].x >= mapXL - 1 && blocks[i].x <= mapXL + 25) {
					switch (blocks[i].state) {
					case Block.STILL:
						Game.qBlockStill.draw((blocks[i].x - (int) mapXL)
								* tileWidth * zoomFactor / zoomFactor,
								(blocks[i].y) * tileHeight * zoomFactor
										/ zoomFactor);
						break;
					case Block.HIT:
						Game.qBlockDeadStill.draw((blocks[i].x - (int) mapXL)
								* tileWidth * zoomFactor / zoomFactor,
								(blocks[i].y) * tileHeight * zoomFactor
										/ zoomFactor);
						break;
					}
				}
				break;
			case Block.B:
				if (blocks[i].x >= mapXL - 1 && blocks[i].x <= mapXL + 25)
					Game.bBlockStill.draw((blocks[i].x - (int) mapXL)
							* tileWidth * zoomFactor / zoomFactor,
							(blocks[i].y) * tileHeight * zoomFactor
									/ zoomFactor);
				break;
			}
		}
		for (int i = 0; i < blocks.length; i++) {

		}
		g.scale(1 / zoomFactor, 1 / zoomFactor);
	}

	private static void renderEnemies(GameContainer gc, StateBasedGame sbg,
			Graphics g) {

		g.scale(zoomFactor2, zoomFactor2);
		for (int i = 0; i < isEnemyDead.length; i++) {
			if (!isEnemyDead[i])
				Game.mushroom.draw((enemyCoords[i].getX() - (int) mapXL)
						* tileWidth * zoomFactor / zoomFactor2,
						(enemyCoords[i].getY()) * tileHeight * zoomFactor
								/ zoomFactor2 - Game.mushroom.getHeight());
		}

		g.scale(1 / zoomFactor2, 1 / zoomFactor2);
	}

	private static void renderInfo(GameContainer gc, StateBasedGame sbg,
			Graphics g) {
		g.setColor(Color.white);
		String str;
		str = "( "
				+ (mapXL + (float) input.getMouseX() / (float) tileWidth
						/ (float) zoomFactor) + " , "
				+ (float) input.getMouseY() / (float) tileHeight
				/ (float) zoomFactor + " )";
		g.drawString(str, 100, 15);
		g.drawString("v : " + xV, 100, 30);
	}

	private static void renderMap(GameContainer gc, StateBasedGame sbg,
			Graphics g) {
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

	private static void renderMenu(GameContainer gc, StateBasedGame sbg,
			Graphics g) {
		g.scale(zoomFactor, zoomFactor);
		Game.marioTitle.draw();
		g.setColor(Color.white);

		Game.marioCursor.draw(cursorX, cursorY);
		g.scale(1 / zoomFactor, 1 / zoomFactor);

	}

	private static void resetState(GameContainer gc, StateBasedGame sbg)
			throws SlickException {
		isMenuUp = true;
		isStalled = false;
		isDead = false;
		won = false;
		isStalled = true;
		initBlocks();
		initEnemies();
		input = gc.getInput();
		isUnderfoot = true;
		oneHit = 0;
		oneHit3 = 0;
		oneHitWin = 0;
		timeOfWin = 0;
		timeOfDeath = 0;
		xV = 0;
		yV = 0;
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
		isUnderfoot = true;
	}

	private static void sleepHandling(GameContainer gc, StateBasedGame sbg,
			int t) throws SlickException {
		if (sbg.getCurrentStateID() == Game.lvl01) {
			Game.appgc.setDisplayMode(640, 560, false);
		} else {
			Game.appgc.setDisplayMode(640, 640, false);
			oneHit = 0;
		}
	}

	private static void updateBlocks() {
		for (int i = 0; i < blocks.length; i++) {
			if ((int) (y - 3.01f) == blocks[i].y
					&& ((int) x == blocks[i].x || (int) (x + .5) == blocks[i].x)
					&& !isFalling) {
				blocks[i].state = Block.HIT;
			}
			if (isFalling
					&& input.isKeyDown(Input.KEY_DOWN)
					&& (int) (y + .2f) == blocks[i].y
					&& (((int) x == blocks[i].x) || (int) (x + .5) == blocks[i].x)) {
				blocks[i].state = Block.HIT;
			}
		}
	}

	private static void updateEnemyMovement() {
		for (int i = 0; i < isEnemyFalling.length; i++) {
			if (!isEnemyDead[i]) {
				if (enemyCoords[i].getX() <= mapXL + 16f) {
					if (!isEnemyFalling[i]) {
						if (enemyRight[i]) {
							if (map.getTileId(
									(int) (enemyCoords[i].getX() + .005f),
									(int) enemyCoords[i].getY(), objectLayer) == 0)
								enemyCoords[i]
										.setX(enemyCoords[i].getX() + .005f);
							if (enemyRight[i]
									&& map.getTileId(
											(int) (enemyCoords[i].getX() + .805f),
											(int) enemyCoords[i].getY(),
											objectLayer) != 0)
								enemyRight[i] = false;
						} else {
							if (map.getTileId(
									(int) (enemyCoords[i].getX() - .005f),
									(int) enemyCoords[i].getY(), objectLayer) == 0)
								enemyCoords[i]
										.setX(enemyCoords[i].getX() - .005f);
							if (!enemyRight[i]
									&& map.getTileId(
											(int) (enemyCoords[i].getX() - .005f),
											(int) enemyCoords[i].getY(),
											objectLayer) != 0)
								enemyRight[i] = true;
						}

					}

					if (enemyCoords[i].getX() + .75 <= mapXL)
						isEnemyDead[i] = true;

					if (!isEnemyDead[i]) {
						if (enemyCoords[i].getY() >= 28)
							isEnemyDead[i] = true;
						else if (map.getTileId((int) enemyCoords[i].getX(),
								(int) (enemyCoords[i].getY() + 0.07),
								objectLayer) == 0
								&& map.getTileId(
										(int) (enemyCoords[i].getX() + (Game.mushroom
												.getWidth() / (tileWidth * zoomFactor2))),
										(int) (enemyCoords[i].getY() + 0.07f),
										objectLayer) == 0) {
							enemyCoords[i].setY(enemyCoords[i].getY() + .07f);
						} else
							isEnemyFalling[i] = false;
					}
				}
			}
		}

	}

	private static void updateIncrements(GameContainer gc, StateBasedGame sbg,
			int t) {
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

		if (isFalling)
			isJumping = false;

		// powerUp
		if (isPowerUpAvailable) {
			powerUpX += .03f;
			if (map.getTileId((int) (powerUpX), (int) (powerUpY + .03f),
					objectLayer) != 0) {
				powerUpY += .03f;
			}
		}
	}

	private static void updateJumpingFalling(GameContainer gc,
			StateBasedGame sbg, int t) {
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
			} else if (!won
					&& map.getTileId(
							(int) x,
							(int) (y
									- (pandaHeight / (tileHeight * zoomFactor)) - .07f),
							objectLayer) == 0
					&& map.getTileId(
							(int) (x + (pandaWidth / (tileWidth * zoomFactor))),
							(int) (y
									- (pandaHeight / (tileHeight * zoomFactor)) - .07f),
							objectLayer) == 0) {
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
			if (y >= 28)
				isDead = true;
			else if (map.getTileId((int) x, (int) (y + 0.07f), objectLayer) == 0
					&& map.getTileId(
							(int) (x + (pandaWidth / (tileWidth * zoomFactor))),
							(int) (y + 0.07f), objectLayer) == 0) {
				switch (last) {
				case 'r':
					panda = Game.marioPandaJumpR;
					if (input.isKeyDown(Input.KEY_DOWN))
						panda = Game.marioPandaDownFallR;
					break;
				case 'l':
					panda = Game.marioPandaJumpL;
					if (input.isKeyDown(Input.KEY_DOWN))
						panda = Game.marioPandaDownFallL;
					break;
				}
				isUnderfoot = false;
				y += .07f;
			} else
				isFalling = false;
		}
		if (map.getTileId((int) x, (int) (y + 0.07f), objectLayer) != 0
				|| map.getTileId(
						(int) (x - .1 + (pandaWidth / (tileWidth * zoomFactor))),
						(int) (y + 0.07f), objectLayer) != 0)
			isUnderfoot = true;

		if (isJumping)
			isUnderfoot = false;
		if (isFalling)
			isUnderfoot = false;

	}

	private static void updateMenu(GameContainer gc, StateBasedGame sbg, int t)
			throws SlickException {
		if (input.isKeyDown(Input.KEY_ENTER)
				|| input.isKeyDown(Input.KEY_SPACE)) {
			switch (menuSelection) {
			case 1:
				Game.marioStarman.stop();
				if (oneHit3 == 0)
					Game.marioTheme.play();
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

	private static void updateMovement(GameContainer gc, StateBasedGame sbg,
			int t) {

		// release
		if (xV > 0 && !isJumping && !isFalling && isUnderfoot
				&& !input.isKeyDown(Input.KEY_LEFT)
				&& !input.isKeyDown(Input.KEY_RIGHT))
			xV -= releaseDecel * t;
		if (xV < 0 && !isJumping && !isFalling && isUnderfoot)
			xV += releaseDecel * t;
		if (!(input.isKeyDown(Input.KEY_LEFT) || input
				.isKeyDown(Input.KEY_RIGHT)))
			if (Math.abs(xV) < .00001f)
				xV = 0f;

		// jump Button
		if (input.isKeyPressed(Input.KEY_SPACE) && !isJumping && !isFalling
				&& isUnderfoot) {
			i = 1;
			isJumping = true;
			if (Game.isMusicOn)
				Game.marioJump.play();
		}

		// walk left
		if (input.isKeyDown(Input.KEY_LEFT) && !input.isKeyDown(Input.KEY_DOWN)) {

			if (map.getTileId(
					(int) (x - (xV * t) + (pandaWidth / (tileWidth * zoomFactor))),
					(int) (y), objectLayer) == 0
					&& map.getTileId(
							(int) (x - (xV * t) + (pandaWidth / (tileWidth * zoomFactor))),
							(int) (y - (pandaHeight / (tileHeight * zoomFactor))),
							objectLayer) == 0)
				if (xV == 0)
					xV = -minWalkVel;
				else
					xV -= walkAccel * t;
		}

		// walk right
		if (input.isKeyDown(Input.KEY_RIGHT)
				&& !input.isKeyDown(Input.KEY_DOWN)) {

			if (map.getTileId(
					(int) (x + (xV * t) + (pandaWidth / (tileWidth * zoomFactor))),
					(int) (y), objectLayer) == 0
					&& map.getTileId(
							(int) (x + (xV * t) + (pandaWidth / (tileWidth * zoomFactor))),
							(int) (y - (pandaHeight / (tileHeight * zoomFactor))),
							objectLayer) == 0)
				if (xV == 0)
					xV = minWalkVel;
				else
					xV += walkAccel * t;
		}

		// down
		if (input.isKeyDown(Input.KEY_DOWN) && !isFalling && !isJumping) {
			if (last == 'l')
				panda = Game.marioPandaDownFallL;
			if (last == 'r')
				panda = Game.marioPandaDownFallR;
		}

		// limit max walk velocity
		if (Math.abs(xV) > maxWalkVel) {
			System.out.println("maxSpeed Reached");
			if (xV < 0)
				xV = -maxWalkVel;
			if (xV > 0)
				xV = maxWalkVel;
		}
		
		// left screen border
		if (x - .029f <= mapXL) {
			x = mapXL + .05f;
			xV = 0;
		}

		// collision testing right
		if (xV > 0)
			if (map.getTileId(
					(int) (x + .02f + (pandaWidth / (tileWidth * zoomFactor))),
					(int) (y), objectLayer) != 0
					|| map.getTileId(
							(int) (x + .02f + (pandaWidth / (tileWidth * zoomFactor))),
							(int) (y - (pandaHeight / (tileHeight * zoomFactor))),
							objectLayer) != 0) {
				xV = 0;
			}

		// collision testing left
		if (xV < 0)
			if (map.getTileId(
					(int) (x - .02f + (pandaWidth / (tileWidth * zoomFactor))),
					(int) (y), objectLayer) != 0
					|| map.getTileId(
							(int) (x - .02f + (pandaWidth / (tileWidth * zoomFactor))),
							(int) (y - (pandaHeight / (tileHeight * zoomFactor))),
							objectLayer) != 0) {
				xV = 0;
			}

		// update mapXL
		if (x > 7.5 + mapXL)
			mapXL += xV * t;

		x += xV * t;
		y += yV * t;

	}

	private static void updateWinScene(GameContainer gc, StateBasedGame sbg,
			int t) throws SlickException {
		won = true;
		oneHitWin++;

		if (oneHitWin == 1) {
			if (Game.isMusicOn)
				Game.marioFlag.play();
			Game.marioTheme.stop();
		}

		if (!isUnderfoot) {
			if (oneHitWin == 1)
				isJumping = false;
			updateJumpingFalling(gc, sbg, t);
			panda = Game.marioPandaBigDownFallR;
		} else {
			oneHitWin2++;
			if (oneHitWin2 == 1) {
				timeOfWin = totalTime;
				Game.marioFlag.stop();
				Game.marioCastleComplete.play();
			}
			if (map.getTileId(
					(int) (x + 0.03f + (pandaWidth / (tileWidth * zoomFactor))),
					(int) (y), objectLayer) == 0
					&& map.getTileId(
							(int) (x + 0.03f + (pandaWidth / (tileWidth * zoomFactor))),
							(int) (y - (pandaHeight / (tileHeight * zoomFactor))),
							objectLayer) == 0) {
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

class PowerUp {
	public float x, y;
	public int type;
	public boolean isDead;
	public static final int COIN = 0, MUSHROOM = 1, FLOWER = 2, STAR = 3;

	public PowerUp(float x, float y, char type) {
		this.x = x;
		this.y = y;
		this.isDead = true;
	}
}

class Block {
	public float x, y;
	public int type, state;
	public static final int Q = 0, B = 1, STILL = 2, HIT = 3, HITDOWN = 4,
			BROKE = 5, DEADSTILL = 6, DEADHIT = 7, DEADHITDOWN = 9;

	public Block(float x, float y, int type) {
		this.x = x;
		this.y = y;
		this.type = type;
		this.state = STILL;
	}
}