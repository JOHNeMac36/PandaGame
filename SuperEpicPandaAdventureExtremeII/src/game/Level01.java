package game;

import java.util.ArrayList;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.tiled.TiledMap;

public class Level01 extends BasicGameState {
	
	private Animation bBlockBreak;
	private static Animation[] a;
	private static Mario panda;
	private static TiledMap map;
	private static Input input;
	public static int tileHeight, tileWidth, points = 0;
	public static float zoomFactor;
	private static int i;
	public static int totalTime;
	public static ArrayList<Fireball> fireballs;
	private static int menuSelection;
	private static int oneHit = 0;
	private static int timeOfWin;
	private static int oneHit3 = 0;
	private static int oneHitWin = 0;
	private static int oneHitWin2 = 0;
	private static int objectLayer, enterStateLayer;
	private static float mapXL;
	private static float powerUpX, powerUpY;
	public static Block[] blocks;
	private static float cursorX, cursorY;
	private final static float enemySpeed = .0096f, powerUpSpeed = .0096f, fireballSpeed = .0096f * 3;
	private static Enemy[] enemies;
	// public static char still = 's', hit = 'h', hitDown = 'd', broke = 'b',
	// deadStill = 'S', deadHit = 'H', deadHitDown = 'D';
	
	private static boolean isStalled = false;
	public static boolean isMenuUp = true;
	private static boolean won = false;
	private static float zoomFactor2 = 2f;
	private static boolean isPowerUpAvailable;
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
		cursorX = 76.6f;
		cursorY = 143f;
		input = gc.getInput();
		fireballs = new ArrayList<Fireball>();
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
		renderBlockBreaks(gc, sbg, g);
		renderEnemies(gc, sbg, g);
		renderFireball(gc, sbg, g);
		panda.panda.draw((panda.x - (int) mapXL) * tileWidth * zoomFactor, panda.y * tileHeight * zoomFactor - 64, 32, 64);
		// renderInfo(gc, sbg, g);
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
			if (panda.isDead) {
				panda.timeOfDeath = totalTime;
				resetState(gc, sbg);
				Game.stopAllMusic();
				Game.marioDead.play();
			}
			checkEnterState(gc, sbg, t);
			sleepHandling(gc, sbg, t);
			if ((isMenuUp || isStalled) && !panda.isDead && (panda.timeOfDeath == 0 || panda.timeOfDeath + 1000 == totalTime)) {
				oneHit++;
				if (totalTime == 1) Game.marioStarman.loop();
				else {
					if (oneHit == 1) Game.marioDead.play();
					if (oneHit == 1000 && totalTime != 1000) Game.marioStarman.loop();
				}
				updateMenu(gc, sbg, t);
			} else {
				checkInGameMenu();
				updateIncrements(gc, sbg, t);
				updateMovement(gc, sbg, t);
				updateFireball(gc, sbg, t);
				updateJumpingFalling(gc, sbg, t);
				updateBlocks(t);
				updatePowerUpMovement(t);
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
			if (!enemies[i].isDead) if (panda.x >= enemies[i].x - .56 && panda.x <= enemies[i].x + 1
					&& (panda.y - .5f < enemies[i].y && (enemies[i].y - panda.y <= 2 && panda.isFalling)
							|| (panda.isStarred && panda.isUnderfoot))) {
				enemies[i].isDead = true;
				Game.marioKick.play();
				enemies[i].timeOfDeath = totalTime;
			}
		}
	}
	
	private void checkEnterState(GameContainer gc, StateBasedGame sbg, int t) throws SlickException {
		// enterState
		if (map.getTileId((int) panda.x, (int) panda.y, enterStateLayer) != 0) {
			panda.panda = Game.marioPandaDown;
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
			if (panda.timeOfInvincibility == 0) {
				if (panda.isSmall && !panda.isFlower) {
					if (!enemies[i].isDead) {
						if (((panda.x - .825f <= enemies[i].x && panda.x >= enemies[i].x)
								|| (panda.x + .8f >= enemies[i].x && panda.x + .8 <= enemies[i].x + .825f))
								&& ((panda.y <= enemies[i].y && panda.y >= enemies[i].y - 1.65f) || (!panda.isFalling
										&& panda.y - 1.65f <= enemies[i].y && panda.y - 1.65f >= enemies[i].y - 1.65))) {
							panda.isDead = true;
						}
					}
				}
				if (!panda.isSmall || panda.isFlower) {
					if (!enemies[i].isDead) {
						if (((panda.x - .825f <= enemies[i].x && panda.x >= enemies[i].x)
								|| (panda.x + .8f >= enemies[i].x && panda.x + .8 <= enemies[i].x + .825f))
								&& (!panda.isFalling && (panda.y <= enemies[i].y && panda.y >= enemies[i].y - 3.15f)
										|| (panda.y - 1.65f <= enemies[i].y && panda.y - 3.15f >= enemies[i].y - 1.65))) {
							panda.isSmall = true;
							panda.isFlower = false;
							panda.timeOfInvincibility = 400;
							Game.marioPowerDown.play();
						}
					}
				}
			}
		}
	}
	
	private static void checkPowerUp() {
		for (int i = 0; i < powerUps.length; i++) {
			
			if (panda.isSmall && powerUps[i].type == PowerUp.FLOWER && powerUps[i].timeOfLife == -1) powerUps[i].type = PowerUp.MUSHROOM;
			if (!panda.isSmall && powerUps[i].type == PowerUp.MUSHROOM && powerUps[i].timeOfLife == -1) powerUps[i].type = PowerUp.FLOWER;
			
			if (!powerUps[i].isDead && panda.x >= powerUps[i].x - .56 && panda.x <= powerUps[i].x + .87
					&& (Math.abs(panda.y - powerUps[i].y) < 0.06
							|| (powerUps[i].type == PowerUp.FLOWER && Math.abs(panda.y - powerUps[i].y - 2) < 0.06))) {
				powerUps[i].isDead = true;
				switch (powerUps[i].type) {
					case PowerUp.MUSHROOM:
						Game.marioPowerUp.play();
						panda.isSmall = false;
						points += 200;
						if (!panda.isStarred) panda.timeOfInvincibility = 15;
						break;
					case PowerUp.FLOWER:
						Game.marioPowerUp.play();
						panda.isFlower = true;
						if (!panda.isStarred) panda.timeOfInvincibility = 15;
						points += 250;
						break;
					case PowerUp.STAR:
						panda.isStarred = true;
						points += 250;
						panda.timeOfInvincibility = 2000;
						Game.marioStarman.loop();
						break;
					case PowerUp.COIN:
						points += 100;
						break;
				}
			}
		}
	}
	
	private void initBlocks() throws SlickException {
		a = new Animation[30];
		Image im = new Image("res/pngs/marioBlocks.png");
		bBlockBreak = new Animation();
		bBlockBreak.addFrame(im.getSubImage(1, 65, 88, 90), 50);
		bBlockBreak.addFrame(im.getSubImage(81, 65, 90, 90), 50);
		bBlockBreak.addFrame(im.getSubImage(192, 65, 90, 90), 50);
		bBlockBreak.addFrame(im.getSubImage(1, 1, 1, 1), 100000);
		for (int i = 0; i < a.length; i++) {
			a[i] = bBlockBreak;
		}
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
		// g.scale(zoomFactor, zoomFactor);
		
		for (int i = 0; i < blocks.length; i++) {
			if (blocks[i].x >= mapXL - 1 && blocks[i].x <= mapXL + 25) {
				switch (blocks[i].type) {
					case Block.B:
						if (blocks[i].x >= mapXL - 1 && blocks[i].x <= mapXL + 25) if (blocks[i].state != Block.BROKE)
							Game.bBlock.draw((blocks[i].x - (int) mapXL) * tileWidth * zoomFactor, (blocks[i].y) * tileHeight * zoomFactor);
						break;
					case Block.Q:
						if (blocks[i].isDead) Game.qBlockDead.draw((blocks[i].x - (int) mapXL) * tileWidth * zoomFactor,
								(blocks[i].y) * tileHeight * zoomFactor);
						else
							Game.qBlock.draw((blocks[i].x - (int) mapXL) * tileWidth * zoomFactor, (blocks[i].y) * tileHeight * zoomFactor);
				}
			}
		}
		
		// g.scale(1f / zoomFactor, 1f / zoomFactor);
	}
	
	private void renderBlockBreaks(GameContainer gc, StateBasedGame sbg, Graphics g) {
		for (int i = 0; i < blocks.length; i++) {
			if (blocks[i].x >= mapXL - 1 && blocks[i].x <= mapXL + 25) {
				switch (blocks[i].type) {
					case Block.B:
						if (blocks[i].x >= mapXL - 1 && blocks[i].x <= mapXL + 25)
							if (blocks[i].state == Block.BROKE && totalTime - blocks[i].timeOfHit < 151) {
							a[i - 13].draw((blocks[i].x - (int) mapXL) * tileWidth * zoomFactor, (blocks[i].y) * tileHeight * zoomFactor);
						}
						break;
				}
			}
		}
	}
	
	private static void renderEnemies(GameContainer gc, StateBasedGame sbg, Graphics g) {
		
		g.scale(zoomFactor2, zoomFactor2);
		for (int i = 0; i < enemies.length; i++) {
			if (!enemies[i].isDead) {
				Game.mushroom.draw((enemies[i].x - (int) mapXL) * tileWidth * zoomFactor / zoomFactor2,
						(enemies[i].y) * tileHeight * zoomFactor / zoomFactor2 - Game.mushroom.getHeight());
			}
			if (enemies[i].isDead && enemies[i].timeOfDeath + 75 > totalTime) {
				Game.mushroomDead.draw((enemies[i].x - (int) mapXL) * tileWidth * zoomFactor / zoomFactor2,
						(enemies[i].y) * tileHeight * zoomFactor / zoomFactor2 - Game.mushroom.getHeight());
			}
		}
		
		g.scale(1 / zoomFactor2, 1 / zoomFactor2);
	}
	
	private static void renderFireball(GameContainer gc, StateBasedGame sbg, Graphics g) {
		g.scale(zoomFactor, zoomFactor);
		for (int i = 0; i < fireballs.size(); i++) {
			if (!fireballs.get(i).isDead)
				Game.fireballShoot.draw((fireballs.get(i).x - mapXL) * tileWidth, (fireballs.get(i).y - 1) * tileHeight);
			else if (fireballs.get(i).timeOfLife + 70 > totalTime)
				Game.fireballHit.draw((fireballs.get(i).x - mapXL) * tileWidth, (fireballs.get(i).y - 1) * tileHeight);
			else
				fireballs.remove(i);
		}
		g.scale(1f / zoomFactor, 1f / zoomFactor);
	}
	
	@SuppressWarnings("unused")
	private static void renderInfo(GameContainer gc, StateBasedGame sbg, Graphics g) {
		g.setColor(Color.white);
		String str;
		str = "( " + (mapXL + (float) input.getMouseX() / (float) tileWidth / zoomFactor) + " , "
				+ (float) input.getMouseY() / (float) tileHeight / zoomFactor + " )";
		g.drawString(str, 100, 15);
		g.drawString("timeOfInvisibility : ( " + panda.timeOfInvincibility + " )", 100, 90);
		g.drawString("PandaHeight " + panda.pandaWidth, 100, 75);
		g.drawString("x,y [Mush02x,y]: " + panda.x + " , " + panda.y + " [ " + enemies[1].x + " , " + enemies[1].y + " ]", 100, 30);
		g.drawString("isUnderFoot, isFalling, isJumping : " + panda.isUnderfoot + " , " + panda.isFalling + " , " + panda.isJumping, 100,
				45);
		g.drawString("i: " + i, 100, 60);
		
		g.setColor(Color.red);
		g.fillRect((panda.x - (int) mapXL) * tileWidth * zoomFactor, panda.y * tileHeight * zoomFactor - 64, 1, 64);
		g.fillRect((panda.x + .8f - (int) mapXL) * tileWidth * zoomFactor, panda.y * tileHeight * zoomFactor - 64, 1, 64);
		g.setColor(Color.yellow);
		g.fillRect((panda.x + .8f - (int) mapXL) * tileWidth * zoomFactor, 32 + panda.y * tileHeight * zoomFactor - 64, 1, 32);
		
		g.setColor(Color.black);
		g.fillRect((panda.x - (int) mapXL) * tileWidth * zoomFactor, (panda.y) * tileHeight * zoomFactor, 3, 3);
		g.setColor(Color.red);
		if (fireballs.size() > 0) g.fillRect((fireballs.get(0).x - .01f - (int) mapXL) * tileWidth * zoomFactor,
				fireballs.get(0).y * tileHeight * zoomFactor, 3, 3);
		g.setColor(Color.cyan);
		g.fillRect((panda.x - (int) mapXL) * tileWidth * zoomFactor, panda.y * tileHeight * zoomFactor - 64 - 3.01f, 3, 3);
		g.setColor(Color.white);
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
						if (powerUps[i].isUp) Game.pwrFlower.draw((powerUps[i].x - (int) mapXL) * tileWidth * zoomFactor / zoomFactor,
								(powerUps[i].y) * tileHeight * zoomFactor / zoomFactor - Game.pwrFlower.getHeight());
						else
							Game.pwrFlower.getCurrentFrame().getFlippedCopy(false, true).draw(
									(powerUps[i].x - (int) mapXL) * tileWidth * zoomFactor / zoomFactor,
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
	
	private void resetState(GameContainer gc, StateBasedGame sbg) throws SlickException {
		isMenuUp = true;
		isStalled = false;
		panda.isDead = false;
		won = false;
		isStalled = true;
		initBlocks();
		initEnemies();
		initPowerUps();
		input = gc.getInput();
		panda.isUnderfoot = true;
		oneHit = 0;
		oneHit3 = 0;
		oneHitWin = 0;
		timeOfWin = 0;
		panda.timeOfDeath = 0;
		setState();
	}
	
	private static void setState() {
		panda = new Mario(2, 24.99f);
		objectLayer = map.getLayerIndex("Objects");
		enterStateLayer = map.getLayerIndex("EnterState");
		mapXL = 0;
		i = 0;
		cursorY = 143f;
		panda.isUnderfoot = true;
	}
	
	private static void sleepHandling(GameContainer gc, StateBasedGame sbg, int t) throws SlickException {
		if (sbg.getCurrentStateID() == Game.lvl01) {
			Game.appgc.setDisplayMode(640, 560, false);
		} else {
			Game.appgc.setDisplayMode(640, 640, false);
			oneHit = 0;
		}
	}
	
	private static void updateBlocks(int t) {
		for (int i = 0; i < blocks.length; i++) {
			if ((int) (panda.isSmall ? panda.y - 3.01f : panda.y - 6.02f) == blocks[i].y
					&& (((int) panda.x == blocks[i].x || (int) (panda.x + .5f) == blocks[i].x)) && !panda.isFalling) {
				if (blocks[i].type == Block.Q) {
					if (!blocks[i].isDead) {
						powerUps[i].isDead = false;
						powerUps[i].timeOfLife = totalTime;
						powerUps[i].isUp = true;
					}
					blocks[i].state = Block.HIT;
					blocks[i].isDead = true;
					blocks[i].timeOfHit = totalTime;
					for (int j = 0; j < enemies.length; j++) {
						if (!enemies[j].isDead && Math.abs((int) enemies[j].x - blocks[i].x) < .8f
								&& Math.abs((int) enemies[j].x - blocks[i].x) < .8f)
							enemies[j].isDead = true;
					}
				}
				if (blocks[i].type == Block.B && !blocks[i].isDead) {
					blocks[i].state = panda.isSmall ? Block.HIT : Block.BROKE;
					if (blocks[i].state == Block.BROKE) blocks[i].isDead = true;
					blocks[i].timeOfHit = totalTime;
					if (panda.isSmall) Game.marioBump.play();
					else
						Game.marioBlockBreak.play();
					if (blocks[i].timeOfHit == totalTime && blocks[i].type == Block.B) a[i - 13].restart();
					if (!panda.isSmall) {
						map.setTileId((int) blocks[i].x, (int) blocks[i].y, objectLayer, 0);
						map.setTileId((int) blocks[i].x, (int) blocks[i].y + 1, objectLayer, 0);
						panda.isFalling = true;
						panda.isJumping = false;
					}
					for (int j = 0; j < enemies.length; j++) {
						if (!enemies[j].isDead && Math.abs((int) enemies[j].x - blocks[i].x) < .8f
								&& Math.abs((int) enemies[j].x - blocks[i].x) < .8f)
							enemies[j].isDead = true;
					}
				}
			}
			if (panda.isFalling && input.isKeyDown(Input.KEY_DOWN) && (int) (panda.y + .8f) == blocks[i].y
					&& (((int) panda.x == blocks[i].x) || (int) (panda.x + .8f) == blocks[i].x)) {
				if (blocks[i].type == Block.Q && blocks[i].state == Block.STILL) {
					if (!blocks[i].isDead) {
						powerUps[i].isDead = false;
						powerUps[i].timeOfLife = totalTime;
						powerUps[i].isUp = false;
						powerUps[i].y += 1.9f;
					}
					blocks[i].state = Block.HITDOWN;
					blocks[i].isDead = true;
					blocks[i].timeOfHit = totalTime;
					for (int j = 0; j < enemies.length; j++) {
						if (!enemies[j].isDead && Math.abs((int) enemies[j].x - blocks[i].x) < .8f
								&& Math.abs((int) enemies[j].x - blocks[i].x) < .8f)
							enemies[j].isDead = true;
					}
				}
				if (blocks[i].type == Block.B && !blocks[i].isDead) {
					blocks[i].state = panda.isSmall ? Block.HITDOWN : Block.BROKE;
					if (blocks[i].state == Block.BROKE) blocks[i].isDead = true;
					blocks[i].timeOfHit = totalTime;
					if (panda.isSmall) Game.marioBump.play();
					else
						Game.marioBlockBreak.play();
					if (blocks[i].timeOfHit == totalTime) a[i - 13].restart();
					if (!panda.isSmall) {
						map.setTileId((int) blocks[i].x, (int) blocks[i].y, objectLayer, 0);
						map.setTileId((int) blocks[i].x, (int) blocks[i].y + 1, objectLayer, 0);
					}
					for (int j = 0; j < enemies.length; j++) {
						if (!enemies[j].isDead && Math.abs((int) enemies[j].x - blocks[i].x) < .8f
								&& Math.abs((int) enemies[j].x - blocks[i].x) < .8f)
							enemies[j].isDead = true;
					}
				}
			}
			
			if (blocks[i].timeOfHit != -1) {
				if (blocks[i].state != Block.BROKE && totalTime - blocks[i].timeOfHit < 20) {
					if (blocks[i].state == Block.HIT) blocks[i].y -= .02f;
					if (blocks[i].state == Block.HITDOWN) blocks[i].y += .02f;
				}
				if (blocks[i].state != Block.BROKE && totalTime - blocks[i].timeOfHit >= 20 && totalTime - blocks[i].timeOfHit < 40) {
					if (blocks[i].state == Block.HIT) blocks[i].y += .02f;
					if (blocks[i].state == Block.HITDOWN) blocks[i].y -= .02f;
				}
				if (blocks[i].state != Block.BROKE && totalTime - blocks[i].timeOfHit == 100) {
					blocks[i].timeOfHit = -1;
					blocks[i].state = Block.STILL;
				}
			}
			
			// if (i > 0 && blocks[i].timeOfHit != -1 && blocks[i - 1].timeOfHit
			// != -1) {
			// blocks[i - 1].timeOfHit = -1;
			// blocks[i - 1].state = Block.STILL;
			// }
			if (blocks[i].type == Block.B && blocks[i].state == Block.BROKE) {
				blocks[i].y += fireballSpeed;
				blocks[i].isDead = true;
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
	
	private static void updateFireball(GameContainer gc, StateBasedGame sbg, int t) {
		
		for (int i = 0; i < fireballs.size(); i++) {
			try {
				if (!fireballs.get(i).isDead && !won) {
					
					// update bounce
					if (fireballs.get(i).isFalling) {
						
						if (map.getTileId((int) fireballs.get(i).x, (int) (fireballs.get(i).y + .07f), objectLayer) == 0) {
							fireballs.get(i).y += .07f;
							fireballs.get(i).distFromGround -= .07f;
							
						} else {
							fireballs.get(i).isFalling = false;
							fireballs.get(i).distFromGround = 0;
						}
						
					} else {
						if (map.getTileId((int) fireballs.get(i).x, (int) (fireballs.get(i).y - .07f), objectLayer) == 0)
							fireballs.get(i).y -= .07f;
						fireballs.get(i).distFromGround += .07f;
					}
					if (fireballs.get(i).distFromGround >= 2) fireballs.get(i).isFalling = true;
					if ((map.getTileId((int) (fireballs.get(i).x), (int) (fireballs.get(i).y - .01), objectLayer) != 0)
							&& (map.getTileId((int) (fireballs.get(i).x), (int) (fireballs.get(i).y + .01), objectLayer) != 0))
						fireballs.get(i).isDead = true;
				}
				
				// check enemyKill
				if (!fireballs.get(i).isDead) {
					fireballs.get(i).x += (fireballs.get(i).isRight) ? fireballSpeed : -fireballSpeed;
					for (int j = 0; j < enemies.length; j++) {
						if (!enemies[j].isDead && fireballs.get(i).x - .7 <= enemies[j].x && fireballs.get(i).x + .7 >= enemies[j].x
								&& fireballs.get(i).y - .1 <= enemies[j].y && fireballs.get(i).y + .1 >= enemies[j].y) {
							enemies[j].isDead = true;
							fireballs.get(i).isDead = true;
						}
					}
				}
				
			} catch (ArrayIndexOutOfBoundsException e) {
				fireballs.remove(i);
			}
		}
	}
	
	private static void updateIncrements(GameContainer gc, StateBasedGame sbg, int t) {
		// i increment
		if (i == 0) panda.isJumping = false;
		
		if (i != 0) {
			i++;
			i %= 250;
		}
		if (i >= 125) {
			panda.isJumping = false;
			panda.isFalling = true;
		}
		if (i > 0 && i < 125) {
			panda.isJumping = true;
			panda.isFalling = false;
		}
		
		if (panda.isFalling) panda.isJumping = false;
		// powerUp
		if (isPowerUpAvailable) {
			powerUpX += .03f;
			if (map.getTileId((int) (powerUpX), (int) (powerUpY + .03f), objectLayer) != 0) {
				powerUpY += .03f;
			}
		}
		
		// invincible
		if (panda.timeOfInvincibility != 0) {
			panda.timeOfInvincibility--;
			if (panda.isStarred && panda.timeOfInvincibility == 1) Game.marioTheme.loop();
		} else if (panda.isStarred) {
			Game.marioStarman.stop();
			panda.isStarred = false;
		}
	}
	
	private static void updateJumpingFalling(GameContainer gc, StateBasedGame sbg, int t) {
		try {
			// isJumping
			if (panda.isJumping) {
				if (!won && (panda.y <= 1
						|| (map.getTileId((int) panda.x, (int) (panda.y - .07f - (panda.isSmall ? 1.65 : 3.15)), objectLayer) == 0
								&& map.getTileId((int) (panda.x + .8f), (int) (panda.y - .07f - (panda.isSmall ? 1.65 : 3.15)),
										objectLayer) == 0))) {
					switch (Mario.last) {
						case 'l':
							if (panda.isSmall) {
								if (panda.isStarred) panda.panda = Game.SmarioPandaJumpL;
								else {
									if (panda.isFlower) panda.panda = Game.FmarioPandaJumpL;
									else
										panda.panda = Game.marioPandaJumpL;
								}
							} else {
								panda.panda = Game.marioPandaBigJumpL;
								if (panda.isStarred) panda.panda = Game.SmarioPandaBigJumpL;
								else {
									if (panda.isFlower) panda.panda = Game.FmarioPandaBigJumpL;
									else
										panda.panda = Game.marioPandaBigJumpL;
								}
							}
							break;
						case 'r':
							if (panda.isSmall) {
								if (panda.isStarred) panda.panda = Game.SmarioPandaJumpR;
								else {
									if (panda.isFlower) panda.panda = Game.FmarioPandaJumpR;
									else
										panda.panda = Game.marioPandaJumpR;
								}
							} else {
								panda.panda = Game.marioPandaBigJumpR;
								if (panda.isStarred) panda.panda = Game.SmarioPandaBigJumpR;
								else {
									if (panda.isFlower) panda.panda = Game.FmarioPandaBigJumpR;
									else
										panda.panda = Game.marioPandaBigJumpR;
								}
							}
							break;
					}
					panda.y -= .07;
					panda.isUnderfoot = false;
					panda.isFalling = false;
				} else if (won) {
					panda.isJumping = false;
					panda.isFalling = true;
					panda.y += .07;
				} else
					i = 125;
			}
			// isUnderFoot
			if (!panda.isJumping) {
				if (panda.y >= 28) panda.isDead = true;
				else if (map.getTileId((int) (panda.x), (int) (panda.y + .07f), objectLayer) == 0
						&& map.getTileId((int) (panda.x + .8f), (int) (panda.y + .07f), objectLayer) == 0) {
					switch (Mario.last) {
						case 'r':
							if (input.isKeyDown(Input.KEY_DOWN)) {
								if (panda.isSmall) {
									panda.panda = Game.marioPandaDownFallR;
									if (panda.isFlower) {
										panda.panda = Game.FmarioPandaDownFallR;
									}
									if (panda.isStarred) {
										panda.panda = Game.SmarioPandaDownFallR;
									}
								} else {
									panda.panda = Game.marioPandaDownBigFallR;
									if (panda.isFlower) {
										panda.panda = Game.FmarioPandaDownBigFallR;
									}
									if (panda.isStarred) {
										panda.panda = Game.SmarioPandaDownBigFallR;
									}
								}
							} else {
								if (panda.isSmall) {
									panda.panda = Game.marioPandaJumpR;
									if (panda.isFlower) {
										panda.panda = Game.FmarioPandaJumpR;
									}
									if (panda.isStarred) {
										panda.panda = Game.SmarioPandaJumpR;
									}
								} else {
									panda.panda = Game.marioPandaBigJumpR;
									if (panda.isFlower) {
										panda.panda = Game.FmarioPandaBigJumpR;
									}
									if (panda.isStarred) {
										panda.panda = Game.SmarioPandaBigJumpR;
									}
								}
							}
							break;
						case 'l':
							if (input.isKeyDown(Input.KEY_DOWN)) {
								if (panda.isSmall) {
									panda.panda = Game.marioPandaDownFallL;
									if (panda.isFlower) {
										panda.panda = Game.FmarioPandaDownFallL;
									}
									if (panda.isStarred) {
										panda.panda = Game.SmarioPandaDownFallL;
									}
								} else {
									panda.panda = Game.marioPandaDownBigFallL;
									if (panda.isFlower) {
										panda.panda = Game.FmarioPandaDownBigFallL;
									}
									if (panda.isStarred) {
										panda.panda = Game.SmarioPandaDownBigFallL;
									}
								}
							} else {
								if (panda.isSmall) {
									panda.panda = Game.marioPandaJumpL;
									if (panda.isFlower) {
										panda.panda = Game.FmarioPandaJumpL;
									}
									if (panda.isStarred) {
										panda.panda = Game.SmarioPandaJumpL;
									}
								} else {
									panda.panda = Game.marioPandaBigJumpL;
									if (panda.isFlower) {
										panda.panda = Game.FmarioPandaBigJumpL;
									}
									if (panda.isStarred) {
										panda.panda = Game.SmarioPandaBigJumpL;
									}
								}
							}
							break;
					}
					panda.y += .07f;
					panda.isUnderfoot = false;
					panda.isFalling = true;
				} else {
					panda.isFalling = false;
					panda.isUnderfoot = true;
					panda.isJumping = false;
				}
			}
			
			if (panda.isJumping) panda.isUnderfoot = false;
			if (panda.isFalling) panda.isUnderfoot = false;
			if (panda.isUnderfoot) i = 0;
			if (panda.isUnderfoot) {
				panda.isFalling = false;
				panda.isJumping = false;
			}
			
		} catch (ArrayIndexOutOfBoundsException e) {
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
		
		if (input.isKeyDown(Input.KEY_SPACE) && !panda.isJumping && !panda.isFalling && panda.isUnderfoot) {
			i = 1;
			panda.isJumping = true;
			if (Game.isMusicOn) Game.marioJump.play();
		}
		
		if (input.isKeyPressed(Input.KEY_Z) && panda.isFlower) {
			fireballs.add(new Fireball(panda.x + .8f, panda.y - (panda.isSmall ? 1 : 2), (panda.isSmall ? 1 : 2)));
			Game.marioFireball.play();
		}
		if (!(input.isKeyDown(Input.KEY_LEFT) || input.isKeyDown(Input.KEY_RIGHT) || panda.isJumping || panda.isFalling)) {
			switch (Mario.last) {
				case 'l':
					if (panda.isSmall) {
						panda.panda = Game.marioPandaStillL;
						if (panda.isStarred) panda.panda = Game.SmarioPandaStillL;
						else {
							if (panda.isFlower) panda.panda = Game.FmarioPandaStillL;
							else
								panda.panda = Game.marioPandaStillL;
						}
						
					} else {
						panda.panda = Game.marioPandaBigStillL;
						if (panda.isStarred) panda.panda = Game.SmarioPandaBigStillL;
						else {
							if (panda.isFlower) panda.panda = Game.FmarioPandaBigStillL;
							else
								panda.panda = Game.marioPandaBigStillL;
						}
					}
					break;
				case 'r':
					if (panda.isSmall) {
						panda.panda = Game.marioPandaStillR;
						if (panda.isStarred) panda.panda = Game.SmarioPandaStillR;
						else {
							if (panda.isFlower) panda.panda = Game.FmarioPandaStillR;
							else
								panda.panda = Game.marioPandaStillR;
						}
						
					} else {
						panda.panda = Game.marioPandaBigStillR;
						if (panda.isStarred) panda.panda = Game.SmarioPandaBigStillR;
						else {
							if (panda.isFlower) panda.panda = Game.FmarioPandaBigStillR;
							else
								panda.panda = Game.marioPandaBigStillR;
						}
					}
					break;
			}
		}
		
		// left
		if (input.isKeyDown(Input.KEY_LEFT) && !input.isKeyDown(Input.KEY_DOWN)) {
			if (panda.isSmall) {
				panda.panda = Game.marioPandaWalkLeft;
				if (panda.isStarred) panda.panda = Game.SmarioPandaWalkLeft;
				else {
					if (panda.isFlower) panda.panda = Game.FmarioPandaWalkLeft;
					else
						panda.panda = Game.marioPandaWalkLeft;
				}
				
			} else {
				panda.panda = Game.marioPandaBigWalkLeft;
				if (panda.isStarred) panda.panda = Game.SmarioPandaBigWalkLeft;
				else {
					if (panda.isFlower) panda.panda = Game.FmarioPandaBigWalkLeft;
					else
						panda.panda = Game.marioPandaBigWalkLeft;
				}
			}
			
			Mario.last = 'l';
			if (panda.y <= 1) panda.x -= .03f;
			else if (panda.x - .03f >= mapXL
					&& map.getTileId((int) (panda.x - 0.03f), (int) (panda.isSmall ? panda.y - 1f / 6f : panda.y - 1f / 6f),
							objectLayer) == 0
					&& map.getTileId((int) (panda.x - 0.03f), (int) (panda.isSmall ? panda.y - 1.65 : panda.y - 1.65), objectLayer) == 0
					&& map.getTileId((int) (panda.x - 0.03f), (int) (panda.isSmall ? panda.y - 1.65 : panda.y - 3.15), objectLayer) == 0)
				panda.x -= 0.03f;
		}
		// right
		if (input.isKeyDown(Input.KEY_RIGHT) && !input.isKeyDown(Input.KEY_DOWN)) {
			if (panda.isSmall) {
				panda.panda = Game.marioPandaWalkRight;
				if (panda.isStarred) panda.panda = Game.SmarioPandaWalkRight;
				else if (panda.isFlower) panda.panda = Game.FmarioPandaWalkRight;
			} else {
				panda.panda = Game.marioPandaBigWalkRight;
				if (panda.isStarred) panda.panda = Game.SmarioPandaBigWalkRight;
				else {
					if (panda.isFlower) panda.panda = Game.FmarioPandaBigWalkRight;
					else
						panda.panda = Game.marioPandaBigWalkRight;
				}
			}
			Mario.last = 'r';
			if (panda.y <= 1) panda.x += .03f;
			else if (map.getTileId((int) (panda.x + .8f + 0.03f), (int) (panda.isSmall ? panda.y - .5 : panda.y - 1f / 6f),
					objectLayer) == 0
					&& map.getTileId((int) (panda.x + .8f + 0.03f), (int) (panda.isSmall ? panda.y - 1 : panda.y - .5), objectLayer) == 0
					&& map.getTileId((int) (panda.x + .8f + 0.03f), (int) (panda.isSmall ? panda.y - 1 : panda.y - 1), objectLayer) == 0)
				panda.x += 0.03f;
			if (panda.x >= 7.5 + mapXL) {
				mapXL += 0.03f;
			}
		}
		// down
		if (input.isKeyDown(Input.KEY_DOWN)) {
			if (Mario.last == 'l') {
				if (panda.isSmall) {
					panda.panda = Game.marioPandaDownL;
					if (panda.isFlower) panda.panda = Game.FmarioPandaDownL;
					if (panda.isStarred) panda.panda = Game.SmarioPandaDownL;
					
				} else {
					panda.panda = Game.marioPandaBigDownL;
					if (panda.isFlower) panda.panda = Game.FmarioPandaBigDownL;
					if (panda.isStarred) panda.panda = Game.SmarioPandaBigDownL;
				}
			}
			
			if (Mario.last == 'r') {
				if (panda.isSmall) {
					panda.panda = Game.marioPandaDownR;
					if (panda.isFlower) panda.panda = Game.FmarioPandaDownR;
					if (panda.isStarred) panda.panda = Game.SmarioPandaDownR;
					
				} else {
					panda.panda = Game.marioPandaBigDownR;
					if (panda.isFlower) panda.panda = Game.FmarioPandaBigDownR;
					if (panda.isStarred) panda.panda = Game.SmarioPandaBigDownR;
				}
			}
		}
	}
	
	private static void updatePowerUpMovement(long t) {
		Game.pwrFlower.update(t);
		for (int i = 0; i < powerUps.length; i++) {
			if (!powerUps[i].isDead) {
				switch (powerUps[i].type) {
					case PowerUp.COIN:
						if (powerUps[i].timeOfLife == totalTime) Game.marioCoin.play();
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
						if (powerUps[i].timeOfLife == totalTime) Game.marioApearingPowerUp.play();
						if (powerUps[i].isUp) {
							if (powerUps[i].timeOfLife + 75 > totalTime) powerUps[i].y -= .0008f;
						} else {
							if (powerUps[i].timeOfLife == totalTime) powerUps[i].y += 2;
							if (powerUps[i].timeOfLife + 75 > totalTime) powerUps[i].y += .0018f;
						}
						break;
					case PowerUp.MUSHROOM:
						if (powerUps[i].timeOfLife == totalTime) Game.marioApearingPowerUp.play();
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
	
	private void updateWinScene(GameContainer gc, StateBasedGame sbg, int t) throws SlickException {
		won = true;
		oneHitWin++;
		
		if (oneHitWin == 1) {
			if (Game.isMusicOn) Game.marioFlag.play();
			Game.marioTheme.stop();
			panda.isFalling = true;
			panda.isJumping = false;
			panda.isUnderfoot = false;
		}
		
		if (!panda.isUnderfoot) {
			if (oneHitWin == 1) panda.isJumping = false;
			updateJumpingFalling(gc, sbg, t);
			if (panda.isSmall) {
				panda.panda = Game.marioPandaDownFallR;
				if (panda.isFlower) panda.panda = Game.FmarioPandaDownFallR;
				if (panda.isStarred) panda.panda = Game.SmarioPandaDownFallR;
			} else {
				panda.panda = Game.marioPandaDownBigFallR;
				if (panda.isFlower) panda.panda = Game.FmarioPandaDownBigFallR;
				if (panda.isStarred) panda.panda = Game.SmarioPandaDownBigFallR;
			}
		} else {
			oneHitWin2++;
			if (oneHitWin2 == 1) {
				timeOfWin = totalTime;
				Game.marioFlag.stop();
				Game.marioCastleComplete.play();
			}
			if (map.getTileId((int) (panda.x + 0.03f + (panda.pandaWidth / (tileWidth * zoomFactor))), (int) (panda.y), objectLayer) == 0
					&& map.getTileId((int) (panda.x + 0.03f + (panda.pandaWidth / (tileWidth * zoomFactor))),
							(int) (panda.y - (panda.pandaHeight / (tileHeight * zoomFactor))), objectLayer) == 0) {
				panda.x += .03f;
				if (panda.isSmall) {
					panda.panda = Game.marioPandaWalkRight;
					if (panda.isFlower) panda.panda = Game.FmarioPandaWalkRight;
					if (panda.isStarred) panda.panda = Game.SmarioPandaWalkRight;
				} else {
					panda.panda = Game.marioPandaBigWalkRight;
					if (panda.isFlower) panda.panda = Game.FmarioPandaBigWalkRight;
					if (panda.isStarred) panda.panda = Game.SmarioPandaBigWalkRight;
				}
				updateJumpingFalling(gc, sbg, t);
			} else {
				if (panda.isSmall) {
					panda.panda = Game.marioPandaStillR;
					if (panda.isFlower) panda.panda = Game.FmarioPandaStillR;
					if (panda.isStarred) panda.panda = Game.SmarioPandaStillR;
				} else {
					panda.panda = Game.marioPandaBigStillR;
					if (panda.isFlower) panda.panda = Game.FmarioPandaBigStillR;
					if (panda.isStarred) panda.panda = Game.SmarioPandaBigStillR;
				}
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
	public int type, state, timeOfHit;
	public static final int Q = 0, B = 1, STILL = 2, HIT = 3, HITDOWN = 4, BROKE = 5;
	public boolean isDead;
	
	public Block(float x, float y) {
		this.x = x;
		this.y = y;
		this.state = STILL;
		this.timeOfHit = -1;
		this.isDead = false;
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
	public int type, timeOfDeath;
	public static final int GOOMBA = 0, KOOPA_TROOPA = 1;
	
	public Enemy(float x, float y) {
		this.x = x;
		this.y = y;
		isFalling = true;
		isDead = false;
		isRight = false;
		timeOfDeath = -1;
	}
	
	@Override
	public String toString() {
		return "Enemy x=" + x + ", y" + y + ",type" + type + "]";
	}
}

class Fireball {
	public float x, y, distFromGround;
	public Animation fireball;
	public boolean isDead, isRight, isFalling;
	public int timeOfLife;
	
	public Fireball(float x, float y, float distFromGround) {
		this.x = x;
		this.y = y;
		this.distFromGround = distFromGround;
		isDead = false;
		isRight = (Mario.last == 'r') ? true : false;
		timeOfLife = Level01.totalTime;
		isFalling = true;
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
	
	@SuppressWarnings("static-access")
	public Goomba(float x, float y) {
		super(x, y);
		type = super.GOOMBA;
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

class Mario {
	public float x, y;
	public Animation panda;
	public int timeOfDeath = 0, timeOfInvincibility = 0, fireballCoolDown = 0;
	public boolean isFalling, isUnderfoot, isJumping, isDead, isStarred, isSmall, isFlower;
	public final int pandaHeight = 32, pandaWidth = 32;
	public static char last;
	
	public Mario(float x, float y) {
		this.x = x;
		this.y = y;
		panda = Game.marioPandaStillR;
		last = 'r';
		isSmall = true;
	}
}

class Mushroom extends PowerUp {
	@SuppressWarnings("static-access")
	public Mushroom(float x, float y) {
		super(x, y);
		type = super.MUSHROOM;
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
		this.timeOfLife = -1;
		this.isDead = true;
		this.isFalling = false;
		this.isRight = true;
		this.isUp = true;
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