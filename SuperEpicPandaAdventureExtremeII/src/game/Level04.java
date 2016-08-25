package game;

import java.util.ArrayList;
import java.util.Random;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Point;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.tiled.TiledMap;

public class Level04 extends BasicGameState {
	
	private static TiledMap map;
	private static int shieldLayer;
	private static int backgroundLayer;
	public static Ship panda;
	public ArrayList<Invader> enemies;
	public static ArrayList<Invader> enemies0, enemies1, enemies2, enemies3, enemies4;
	public ArrayList<Shot> shots;
	public float shotSpeed = 4f, zoomFactorX, zoomFactorY;
	int zf = 2, totalTime = 0;
	public boolean[][] shields;
	boolean enemiesRight = true;
	int frameNum = 0, soundNum = 1;
	public long time = 0, soundDelay, updateEnemyRate = 0;
	final float SOUND_FACTOR = 2f, MOVE_FACTOR = .96f;
	ArrayList<Integer> killShots, killEnemies;
	public static Music music;
	public static Input input;
	float waveDelay = 140f;
	float enemyShotPercentage = .01f;
	public static int enemyShootCoolDown = 50;
	
	public Level04(int i) {
	}
	
	@Override
	public int getID() {
		// TODO Auto-generated method stub
		return Game.lvl04;
	}
	
	@Override
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
		Game.appgc.setSoundOn(true);
		map = new TiledMap("res/maps/Level04.tmx");
		
		shieldLayer = map.getLayerIndex("Shield");
		backgroundLayer = map.getLayerIndex("Background");
		music = new Music("res/oggs/music.ogg");
		panda = new Ship(103, 208);
		input = gc.getInput();
		zoomFactorX = 224f / 217f;
		zoomFactorY = 256f / 248f;
		shields = new boolean[map.getWidth()][map.getHeight()];
		shots = new ArrayList<Shot>();
		killShots = new ArrayList<Integer>();
		killEnemies = new ArrayList<Integer>();
		
		initShields();
		initEnemies();
	}
	
	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
		renderMap(gc, sbg, g);
		renderShots(gc, sbg, g);
		renderShip(gc, sbg, g);
		renderInvaders(gc, sbg, g);
		if (panda.isDead) {
			g.drawString("GAME OVER", Game.appgc.getWidth() / 2 - 100, Game.appgc.getHeight() / 2 - 5);
		}
	}
	
	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int t) throws SlickException {
		time += t;
		totalTime++;
		if (panda.won) {
			updateWinScene(gc, sbg, t);
		} else if (panda.isDead) {
			updateDeathScene(gc, sbg, t);
		} else {
			sleepHandling(gc, sbg, t);
			totalTime++;
			panda.shootCoolDown--;
			updateMovement(gc, sbg, t);
			updateEnemies(gc, sbg, t);
			updateColliders();
			updateShots(t);
			checkWin();
			checkIsDead();
		}
	}
	
	// supplementary methods
	private void updateDeathScene(GameContainer gc, StateBasedGame sbg, int t) throws SlickException {
		if (panda.timeOfDeath + 1 == totalTime) {
			panda.panda = Game.playerExplosion;
			Game.shipExplosion.play(1, .5f);
			enemies.clear();
			shots.clear();
			
		}
		if (panda.timeOfDeath + 60 == totalTime) {
			resetState(gc, sbg, t);
		}
	}
	
	private void die() {
		panda.panda = Game.playerExplosion;
		Game.shipExplosion.play(1f, .75f);
		panda.isDead = true;
		panda.timeOfDeath = totalTime;
	}
	
	private void updateWinScene(GameContainer gc, StateBasedGame sbg, int t) {
		
		if (totalTime == panda.timeOfWin + 1) Game.dkWin.play(1f, .75f);
		if (!Game.dkWin.playing() && totalTime > panda.timeOfWin + 1) {
			sbg.enterState(Game.roam);
			Game.pet4Found = true;
			Game.charLock4 = '9';
			try {
				Game.appgc.setDisplayMode(640, 640, false);
			} catch (SlickException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
	
	private void checkIsDead() {
		for (Invader i : enemies) {
			if (i.y >= 208) die();
		}
		for (Shot i : shots) {
			if (i.type == Shot.ENEMY) {
				if (i.collider.intersects(panda.collider)) {
					i.isDead = true;
					die();
				}
			}
		}
	}
	
	private void checkWin() {
		if (enemies.size() == 0 && !panda.isDead && totalTime > 11) {
			win();
		}
	}
	
	private void win() {
		panda.won = true;
		panda.timeOfWin = totalTime;
		enemies.clear();
		shots.clear();
	}
	
	private void initEnemies() {
		enemies0 = new ArrayList<Invader>();
		enemies1 = new ArrayList<Invader>();
		enemies2 = new ArrayList<Invader>();
		enemies3 = new ArrayList<Invader>();
		enemies4 = new ArrayList<Invader>();
		
		enemies0.add(new Invader1(30, 128));
		enemies0.add(new Invader1(46, 128));
		enemies0.add(new Invader1(62, 128));
		enemies0.add(new Invader1(78, 128));
		enemies0.add(new Invader1(94, 128));
		enemies0.add(new Invader1(110, 128));
		enemies0.add(new Invader1(126, 128));
		enemies0.add(new Invader1(142, 128));
		enemies0.add(new Invader1(158, 128));
		enemies0.add(new Invader1(174, 128));
		enemies0.add(new Invader1(190, 128));
		
		enemies1.add(new Invader1(30, 112));
		enemies1.add(new Invader1(46, 112));
		enemies1.add(new Invader1(62, 112));
		enemies1.add(new Invader1(78, 112));
		enemies1.add(new Invader1(94, 112));
		enemies1.add(new Invader1(110, 112));
		enemies1.add(new Invader1(126, 112));
		enemies1.add(new Invader1(142, 112));
		enemies1.add(new Invader1(158, 112));
		enemies1.add(new Invader1(174, 112));
		enemies1.add(new Invader1(190, 112));
		
		enemies2.add(new Invader2(30, 96));
		enemies2.add(new Invader2(46, 96));
		enemies2.add(new Invader2(62, 96));
		enemies2.add(new Invader2(78, 96));
		enemies2.add(new Invader2(94, 96));
		enemies2.add(new Invader2(110, 96));
		enemies2.add(new Invader2(126, 96));
		enemies2.add(new Invader2(142, 96));
		enemies2.add(new Invader2(158, 96));
		enemies2.add(new Invader2(174, 96));
		enemies2.add(new Invader2(190, 96));
		
		enemies3.add(new Invader2(30, 80));
		enemies3.add(new Invader2(46, 80));
		enemies3.add(new Invader2(62, 80));
		enemies3.add(new Invader2(78, 80));
		enemies3.add(new Invader2(94, 80));
		enemies3.add(new Invader2(110, 80));
		enemies3.add(new Invader2(126, 80));
		enemies3.add(new Invader2(142, 80));
		enemies3.add(new Invader2(158, 80));
		enemies3.add(new Invader2(174, 80));
		enemies3.add(new Invader2(190, 80));
		
		enemies4.add(new Invader3(30, 64));
		enemies4.add(new Invader3(46, 64));
		enemies4.add(new Invader3(62, 64));
		enemies4.add(new Invader3(78, 64));
		enemies4.add(new Invader3(94, 64));
		enemies4.add(new Invader3(110, 64));
		enemies4.add(new Invader3(126, 64));
		enemies4.add(new Invader3(142, 64));
		enemies4.add(new Invader3(158, 64));
		enemies4.add(new Invader3(174, 64));
		enemies4.add(new Invader3(190, 64));
		
		enemies = new ArrayList<Invader>();
		enemies.addAll(enemies0);
		enemies.addAll(enemies1);
		enemies.addAll(enemies2);
		enemies.addAll(enemies3);
		enemies.addAll(enemies4);
	}
	
	private void initShields() {
		for (int x = 0; x < map.getWidth(); x++) {
			for (int y = 0; y < map.getHeight(); y++) {
				try {
					shields[x][y] = (map.getTileId(x, y, shieldLayer) != 0);
					// System.out.println(x + " " + y);
				} catch (ArrayIndexOutOfBoundsException e) {
					// System.out.println("ERR: " + x + " " + y);
				}
			}
		}
	}
	
	private void renderInvaders(GameContainer gc, StateBasedGame sbg, Graphics g) {
		g.scale(zoomFactorX * zf, zoomFactorY * zf);
		
		for (int i = 0; i < enemies.size(); i++) {
			try {
				Invader inv = enemies.get(i);
				inv.invader.draw(inv.x, inv.y);
			} catch (IndexOutOfBoundsException e) {
			}
			// g.draw(inv.sheildCollider);
		}
		g.scale(1f / (zoomFactorX * zf), 1f / (zoomFactorY * zf));
	}
	
	private void renderMap(GameContainer gc, StateBasedGame sbg, Graphics g) {
		g.scale(zoomFactorX * zf, zoomFactorY * zf);
		map.render(0, 0, backgroundLayer);
		for (int x = 30; x <= 186; x++) {
			for (int y = 184; y <= 199; y++) {
				if (shields[x][y]) map.render(x, y, x, y, 1, 1, shieldLayer, true);
			}
		}
		g.scale(1f / (zoomFactorX * zf), 1f / (zoomFactorY * zf));
	}
	
	private void renderShip(GameContainer gc, StateBasedGame sbg, Graphics g) {
		g.scale(zoomFactorX * zf, zoomFactorY * zf);
		
		panda.panda.draw(panda.x, panda.y);
		g.scale(1f / (zoomFactorX * zf), 1f / (zoomFactorY * zf));
		
	}
	
	private void renderShots(GameContainer gc, StateBasedGame sbg, Graphics g) {
		g.scale((zoomFactorX * zf), (zoomFactorY * zf));
		
		for (Shot i : shots) {
			i.shot.draw(i.x, i.y);
		}
		g.scale(1f / (zoomFactorX * zf), 1f / (zoomFactorY * zf));
		
	}
	
	private void sleepHandling(GameContainer gc, StateBasedGame sbg, int t) throws SlickException {
		if (sbg.getCurrentStateID() == Game.lvl04) Game.appgc.setDisplayMode(224 * zf, 256 * zf, false);
	}
	
	private void updateColliders() {
		panda.collider = new Rectangle(panda.x, panda.y + 3, 13, 5);
		for (Shot i : shots) {
			i.collider = new Rectangle(i.x, i.y, 1, 4);
			i.pointCollider = i.type == Shot.PLAYER ? new Rectangle(i.x + .05f, i.y - 4, .1f, 2f) : new Rectangle(i.x + 1f, i.y + 4, .1f, 7f);
			
		}
		for (Invader i : enemies) {
			try {
				i.collider = new Rectangle(i.x, i.y, i.invader.getWidth(), i.invader.getHeight());
				i.sheildCollider = new Rectangle(i.x - 2, i.y - 8, 16, 16);
			} catch (IndexOutOfBoundsException e) {
			}
		}
	}
	
	private void updateEnemies(GameContainer gc, StateBasedGame sbg, int t) {
		if (soundDelay <= 0) {
			switch (soundNum) {
				case 1:
					Game.invaderMove1.play(1f, .75f);
					soundNum++;
					break;
				case 2:
					Game.invaderMove2.play(1f, .75f);
					soundNum++;
					break;
				case 3:
					Game.invaderMove3.play(1f, .75f);
					soundNum++;
					break;
				case 4:
					Game.invaderMove4.play(1f, .75f);
					soundNum = 1;
					break;
			}
			
			soundDelay = (long) (SOUND_FACTOR * 1000 * (.5973 * Math.pow(Math.E, -.05 * time / 1000)));
		}
		soundDelay -= t;
		boolean boundryReached = false;
		// wave0
		if (updateEnemyRate <= 0) {
			frameNum = frameNum == 0 ? 1 : 0;
			updateEnemyRate = (long) (MOVE_FACTOR * 1000 * (.5973 * Math.pow(Math.E, -.05 * time / 1000)));
			
			for (Invader i : enemies) {
				if (i.x <= 1 || i.x >= 204) boundryReached = true;
				i.shootCoolDown--;
				// enemyShoot
				if (i.shootCoolDown <= 0) {
					Random rand = new Random();
					if (rand.nextFloat() <= enemyShotPercentage) {
						Game.playerShoot.play(.8f, 1f);
						shots.add(new EnemyShot(i.x, i.y));
						i.shootCoolDown = enemyShootCoolDown;
					}
				}
			}
			if (boundryReached) {
				enemiesRight = !enemiesRight;
				for (int i = 0; i < enemies.size(); i++) {
					enemies.get(i).y += 8;
				}
			}
			if (enemiesRight) {
				for (int i = 0; i < enemies.size(); i++) {
					Invader inv = enemies.get(i);
					inv.invader.setCurrentFrame(frameNum);
					inv.x += 1.1;
				}
			} else {
				for (int i = 0; i < enemies.size(); i++) {
					Invader inv = enemies.get(i);
					inv.invader.setCurrentFrame(frameNum);
					enemies.get(i).x -= 1.1;
				}
				
			}
			
		}
		
		updateEnemyRate -= t;
		// wave1
		// wave2
		// wave3
		// wave4
		
	}
	
	private void updateMovement(GameContainer gc, StateBasedGame sbg, int t) {
		
		// left
		if (input.isKeyDown(Input.KEY_LEFT)) {
			if (panda.x - 1 > 0) panda.x -= 1;
			
		}
		
		// right
		if (input.isKeyDown(Input.KEY_RIGHT)) {
			if (panda.x + 1 < map.getWidth()) panda.x += 1;
			
		}
		
		// shoot
		if (input.isKeyPressed(Input.KEY_SPACE)) {
			if (panda.shootCoolDown <= 0) {
				Shot shot = new PlayerShot(panda.x + 6, panda.y);
				shot.collider = new Rectangle(shot.x, shot.y, 1, 4);
				shot.pointCollider = new Point(shot.x, shot.y);
				shots.add(shot);
				panda.shootCoolDown = Ship.maxShootCoolDown;
				Game.playerShoot.play(1f, .75f);
			}
		}
		
	}
	
	private void updateShots(int t) {
		for (int i = 0; i < shots.size(); i++) {
			if (shots.get(i).y <= 0 || shots.get(i).y >= 216) shots.get(i).isDead = true;
			if (shots.get(i).isDead) {
				shots.remove(i);
			}
		}
		
		for (int i = 0; i < enemies.size(); i++) {
			if (enemies.get(i).isDead) {
				Game.invaderShipExplosion.play(1f, .5f);
				enemies.remove(i);
			}
		}
		for (int i = 0; i < shots.size(); i++) {
			if (shots.get(i).type == Shot.ENEMY) {
				for (int j = 0; j < shots.size(); j++) {
					Rectangle cancelCollider = (Rectangle) shots.get(i).collider;
					cancelCollider.setWidth(5);
					cancelCollider.setX(shots.get(i).collider.getX() - .5f);
					cancelCollider.setHeight(7f);
					int oneHit = 0;
					if (i != j && cancelCollider.intersects(shots.get(j).collider) && shots.get(j).type == Shot.PLAYER) {
						shots.get(i).isDead = true;
						shots.get(j).isDead = true;
						if (oneHit == 0) Game.invaderShipExplosion.play(.5f, .5f);
						oneHit++;
					}
				}
				if (!shots.get(i).isDead) shots.get(i).y += shotSpeed;
				// test collisions
				if (shots.get(i).collider.intersects(panda.collider)) {
					shots.get(i).isDead = true;
					panda.isDead = true;
				}
			} else {
				for (int j = 0; j < enemies.size(); j++) {
					if (shots.get(i).collider.intersects(enemies.get(j).collider)) {
						shots.get(i).isDead = true;
						enemies.get(j).isDead = true;
						enemies.get(j).invader = Game.invaderExplosion;
					}
				}
				if (!shots.get(i).isDead) shots.get(i).y -= shotSpeed;
			}
			
			// test for sheildHit
			for (int x = 0; x < shields.length; x++) {
				for (int y = 0; y < shields[x].length; y++) {
					if (shields[x][y]) {
						if (new Rectangle(x, y, 1, 1).intersects(shots.get(i).pointCollider)) {
							shields[x][y] = false;
							shields[x - 1][y] = false;
							shields[x + 1][y] = false;
							shields[x][y + 1] = false;
							shields[x][y + 2] = false;
							
							shots.get(i).isDead = true;
						}
						for (Invader enemy : enemies) {
							if (enemy.sheildCollider.intersects(new Rectangle(x, y, 1, 1))) {
								shields[x][y] = false;
								shots.get(i).isDead = true;
							}
						}
					}
				}
				
			}
		}
		
	}
	
	public void resetState(GameContainer gc, StateBasedGame sbg, int t) throws SlickException {
		music.stop();
		Game.appgc.setSoundOn(true);
		map = new TiledMap("res/maps/Level04.tmx");
		
		shieldLayer = map.getLayerIndex("Shield");
		backgroundLayer = map.getLayerIndex("Background");
		music = new Music("res/oggs/music.ogg");
		panda = new Ship(103, 208);
		input = gc.getInput();
		zoomFactorX = 224f / 217f;
		zoomFactorY = 256f / 248f;
		shields = new boolean[map.getWidth()][map.getHeight()];
		shots = new ArrayList<Shot>();
		killShots = new ArrayList<Integer>();
		killEnemies = new ArrayList<Integer>();
		totalTime = 0;
		frameNum = 0;
		time = 0;
		updateEnemyRate = 0;
		waveDelay = 140f;
		soundNum = 1;
		initShields();
		initEnemies();
	}
	
}

abstract class Invader {
	public float x, y;
	public int level;
	public int shootCoolDown = 3;
	public boolean isDead;
	public Shape collider, sheildCollider;
	Animation invader;
}

class Invader1 extends Invader {
	public Invader1(float x, float y) {
		this.x = x;
		this.y = y;
		isDead = false;
		invader = Game.invader1;
		invader.setAutoUpdate(false);
	}
}

class Invader2 extends Invader {
	public Invader2(float x, float y) {
		this.x = x;
		this.y = y;
		isDead = false;
		invader = Game.invader2;
		invader.setAutoUpdate(false);
	}
}

class Invader3 extends Invader {
	public Invader3(float x, float y) {
		this.x = x;
		this.y = y;
		isDead = false;
		invader = Game.invader3;
		invader.setAutoUpdate(false);
	}
}

class InvaderShot extends Shot {
	
	public InvaderShot(float x, float y) {
		this.x = x;
		this.y = y;
		shot = Game.invaderShot;
		this.type = Shot.ENEMY;
		
	}
}

class PlayerShot extends Shot {
	
	public PlayerShot(float x, float y) {
		this.x = x;
		this.y = y;
		this.type = Shot.PLAYER;
		this.shot = Game.playerShot;
	}
}

class EnemyShot extends Shot {
	public EnemyShot(float x, float y) {
		this.x = x;
		this.y = y;
		this.type = Shot.ENEMY;
		this.shot = Game.invaderShot;
	}
}

class Ship {
	public float x, y;
	public long shootCoolDown;
	public boolean isDead, won;
	public Animation panda;
	public Shape collider;
	public static long maxShootCoolDown = 5;
	public int timeOfDeath = -1, timeOfWin = -1;
	
	public Ship(float x, float y) {
		this.x = x;
		this.y = y;
		isDead = false;
		won = false;
		panda = Game.player;
		shootCoolDown = maxShootCoolDown;
	}
}

abstract class Shot {
	public float x, y;
	public Shape collider, pointCollider;
	public Animation shot;
	public int type;
	public final static int PLAYER = 0, ENEMY = 1;
	public boolean isDead = false;
}

class UFO extends Invader {
	public UFO(float x, float y) {
		this.x = x;
		this.y = y;
		isDead = false;
		invader = Game.ufo;
	}
}
