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
import org.newdawn.slick.geom.Ellipse;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.tiled.TiledMap;

public class Level03 extends BasicGameState {
	
	private TiledMap map, introMap;
	private int backgroundLayer, objectLayer, ladderLayer, enterStateLayer, ladderingLayer;
	private char last = 'd';
	private int tileWidth, tileHeight;
	private static int i = 0;
	private int totalTime = 0;
	private int timeOfWin = -1;
	private float barrelSpeed = .048f, barrelFallSpeed = .25f, jumpSpeed = .25f, jumpTime = 150, pandaWalkSpeed = .035f, pandaClimbSpeed = .14f;
	private final int barrelSpawnSpeed = 300;
	private float barrelLockFallSpeed = .35f;
	public static boolean quit, won;
	public static Music music;
	public static JumpMan panda;
	public static DK dk;
	public static Input input;
	public static final float zoomFactor = 2.5f, zoomFactor2 = 2f;
	private static ArrayList<Barrel> barrels;
	
	public Level03(int i) {
	}
	
	@Override
	public int getID() {
		return Game.lvl03;
	}
	
	@Override
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
		barrels = new ArrayList<Barrel>();
		panda = new JumpMan(5, 248);
		dk = new DK(3, 84);
		map = new TiledMap("res/maps/lvlDonkeyKong.tmx");
		introMap = new TiledMap("res/maps/lvlDonkeyKongPreview.tmx");
		objectLayer = map.getLayerIndex("Objects");
		backgroundLayer = map.getLayerIndex("Background");
		objectLayer = map.getLayerIndex("Object");
		enterStateLayer = map.getLayerIndex("EnterState");
		ladderLayer = map.getLayerIndex("Ladder");
		ladderingLayer = map.getLayerIndex("Laddering");
		music = new Music("res/oggs/music.ogg");
		panda.panda = Game.jmStillRight;
		input = gc.getInput();
		tileHeight = map.getTileHeight();
		tileWidth = map.getTileWidth();
		//Game.appgc.setTargetFrameRate(65);
	}
	
	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
		renderMap(gc, sbg, g);
		renderScene(gc, sbg, g);
		renderPanda(gc, sbg, g);
		renderDK(gc, sbg, g);
		renderInfo(gc, sbg, g);
		renderBarrels(gc, sbg, g);
	}
	
	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int t) throws SlickException {
		if(sbg.getCurrentStateID() == Game.lvl03) Game.appgc.setTargetFrameRate(200); else Game.appgc.setTargetFrameRate(Game.standardFPS);

		if (Game.isMusicOn) Game.unmuteAllMusic();
		else
			Game.muteAllMusic();
		totalTime++;
		if (won) {
			updateWinScene(gc, sbg, t);
		}
		if (panda.isDead) {
			updateDeathScene(gc, sbg, t);
		} else {
			if (totalTime == 1) {
				Game.dkBackground.loop();
				Game.dkWalking.loop(1, 0);
			}
			map.getTileId(0, 0, objectLayer);
			sleepHandling(gc, sbg, t);
			updateMovement(t);
			updateJumpingFalling();
			updateDK();
			updateBarrels();
			checkIsDead();
		}
	}
	
	private void updateDeathScene(GameContainer gc, StateBasedGame sbg, int t) {
		if (panda.timeOfDeath + 1000 == totalTime) {
			Game.pet3Found = true;
			Game.charLock3 = '6';
			resetState(gc, sbg, t);
		}
		
	}
	
	// supplementary methods
	private boolean intersects(JumpMan panda, Barrel barrel) {
		
		panda.collider = new Ellipse((panda.x * tileWidth), panda.y * tileHeight - panda.panda.getHeight() / 2f, (panda.panda.getWidth() / 2 - .2f), (panda.panda.getHeight() / 2) - .2f);
		
		barrel.collider = new Ellipse((barrel.x * tileWidth), barrel.y * tileHeight - barrel.barrel.getHeight() / 2f, (barrel.barrel.getWidth() / 2), (barrel.barrel.getHeight() / 2));
		
		try {
			return panda.collider.intersects(barrel.collider);
		} catch (NullPointerException e) {
			return false;
		}
		
	}
	
	private void checkIsDead() {
		for (int i = 0; i < barrels.size(); i++) {
			if (intersects(panda, barrels.get(i))) {
				die();
			}
		}
	}
	
	private void die() {
		Game.dkBackground.stop();
		Game.dkWalking.setVolume(0f);
		Game.dkDeath.play();
		panda.isDead = true;
		barrels.clear();
		panda.timeOfDeath = totalTime;
		panda.panda = Game.jmDead;
	}
	
	private void renderBarrels(GameContainer gc, StateBasedGame sbg, Graphics g) {
		g.scale(zoomFactor, zoomFactor);
		for (int i = 0; i < barrels.size(); i++) {
			Barrel bar = barrels.get(i);
			barrels.get(i).barrel.draw((bar.x) * tileWidth - bar.barrel.getWidth() / 2f, bar.y * tileHeight - bar.barrel.getHeight());
		}
		g.scale(1f / zoomFactor, 1f / zoomFactor);
		
	}
	
	private void renderDK(GameContainer gc, StateBasedGame sbg, Graphics g) {
		g.scale(zoomFactor2, zoomFactor2);
		dk.dk.draw((dk.x - .5f) * tileWidth * zoomFactor / zoomFactor2, (dk.y) * tileHeight * zoomFactor / zoomFactor2 - dk.dk.getHeight());
		g.scale(1f / zoomFactor2, 1f / zoomFactor2);
	}
	
	private void renderInfo(GameContainer gc, StateBasedGame sbg, Graphics g) {
		g.scale(zoomFactor, zoomFactor);
		g.setColor(Color.green);
		try {
			g.draw(panda.collider);
		} catch (NullPointerException e) {
		}
		for (int i = 0; i < barrels.size(); i++) {
			g.fillRect(barrels.get(i).x * tileWidth, barrels.get(i).y * tileHeight, 1, 1);
			g.fillRect(barrels.get(i).x * tileWidth, barrels.get(i).y * tileHeight, 1, 1);
			g.fillRect(barrels.get(i).x * tileWidth, barrels.get(i).y * tileHeight, 1, 1);
			g.fillRect(barrels.get(i).x * tileWidth, barrels.get(i).y * tileHeight, 1, 1);
			try {
				g.draw(barrels.get(i).collider);
			} catch (NullPointerException e) {
			}
		}
		
		g.scale(1f / zoomFactor, 1f / zoomFactor);
		
		g.drawString("isClimbing, isFalling, i : " + panda.isClimbing + " " + panda.isFalling + " " + i, 100, 15);
		g.drawString("x,y + level" + panda.x + " " + panda.y + " " + panda.level, 100, 30);
		
	}
	
	private void renderMap(GameContainer gc, StateBasedGame sbg, Graphics g) {
		g.scale(zoomFactor, zoomFactor);
		map.render(0, 0);
		g.scale(1f / zoomFactor, 1f / zoomFactor);
		
	}
	
	private void renderPanda(GameContainer gc, StateBasedGame sbg, Graphics g) {
		g.scale(zoomFactor2, zoomFactor2);
		panda.panda.draw((panda.x - .5f) * tileWidth * zoomFactor / zoomFactor2, (panda.y) * tileHeight * zoomFactor / zoomFactor2 - panda.panda.getHeight());
		g.scale(1f / zoomFactor2, 1f / zoomFactor2);
	}
	
	private void renderScene(GameContainer gc, StateBasedGame sbg, Graphics g) {
		g.scale(zoomFactor, zoomFactor);
		Game.barrellStack.draw(0, (84 - Game.barrellStack.getHeight()) * tileHeight);
		if (won) Game.giraffeLove.draw(11f * tileWidth, (56f - Game.girraffe.getHeight()) * tileHeight);
		else
			Game.girraffe.draw(11f * tileWidth, (56f - Game.girraffe.getHeight()) * tileHeight);
		g.scale(1f / zoomFactor, 1f / zoomFactor);
		g.scale(zoomFactor2, zoomFactor2);
		Game.oilFire.draw(2f * tileWidth * zoomFactor / zoomFactor2, (248f) * tileHeight * zoomFactor / zoomFactor2 - Game.oilFire.getHeight());
		g.scale(1f / zoomFactor2, 1f / zoomFactor2);
	}
	
	private void updateBarrels() {
		for (int i = 0; i < barrels.size(); i++) {
			try {
				if (barrels.get(i).isDownLock) {
					barrels.get(i).barrel = Game.dkBarrelRollDown;
					barrels.get(i).y += barrelLockFallSpeed;
					if ((barrels.get(i).y - 84 < 1 && barrels.get(i).y - 84 > 0) || (barrels.get(i).y - 119 < 1 && barrels.get(i).y - 119 > 0) || (barrels.get(i).y - 145 < 1 && barrels.get(i).y - 145 > 0) || (barrels.get(i).y - 145 < 1 && barrels.get(i).y - 145 > 0) || (barrels.get(i).y - 185 < 1 && barrels.get(i).y - 185 > 0)
							|| (barrels.get(i).y - 211 < 1 && barrels.get(i).y - 211 > 0) || (barrels.get(i).y - 248 < 1 && barrels.get(i).y - 248 > 0))
						barrels.get(i).y -= barrelLockFallSpeed - .05f;
				} else {
					if (barrels.get(i).isLaddering) {
						barrels.get(i).barrel = Game.dkBarrelRollDown;
						if (map.getTileId((int) (barrels.get(i).x + (barrels.get(i).isRight ? 0 : barrels.get(i).barrel.getWidth() / tileWidth)), (int) (barrels.get(i).y), objectLayer) == 0) {
							barrels.get(i).y += barrelFallSpeed;
						} else {
							barrels.get(i).isLaddering = false;
							barrels.get(i).level++;
							barrels.get(i).isRight = !barrels.get(i).isRight;
						}
					} else {
						barrels.get(i).barrel = (barrels.get(i).isRight ? Game.dkBarrelRollRight : Game.dkBarrelRollLeft);
						if (barrels.get(i).isFalling) {
							if (map.getTileId((int) (barrels.get(i).x + (barrels.get(i).isRight ? barrelSpeed : -barrelSpeed) + (barrels.get(i).isRight ? 0 : barrels.get(i).barrel.getWidth() / tileWidth)), (int) barrels.get(i).y, objectLayer) == 0) {
								barrels.get(i).y++;
								barrels.get(i).x += barrels.get(i).isRight ? barrelSpeed * .9 : -barrelSpeed;
								
							} else {
								barrels.get(i).level++;
								barrels.get(i).isFalling = false;
								if (barrels.get(i).level <= panda.level + 1) barrels.get(i).isRight = !barrels.get(i).isRight;
							}
						} else {
							if (map.getTileId((int) (barrels.get(i).x + (barrels.get(i).isRight ? 0 : barrels.get(i).barrel.getWidth() / tileWidth) + (barrels.get(i).isRight ? barrelSpeed : -barrelSpeed)), (int) barrels.get(i).y, objectLayer) != 0) barrels.get(i).x += barrels.get(i).isRight ? barrelSpeed : -barrelSpeed;
							else {
								if (barrels.get(i).x + (barrels.get(i).isRight ? 0 : barrels.get(i).barrel.getWidth() / tileWidth) + (barrels.get(i).isRight ? barrelSpeed : -barrelSpeed) < 27) if (map.getTileId((int) (barrels.get(i).x + (barrels.get(i).isRight ? barrelSpeed : -barrelSpeed)), (int) barrels.get(i).y + 1, objectLayer) != 0) {
									barrels.get(i).y++;
									barrels.get(i).x += barrels.get(i).isRight ? barrelSpeed : -barrelSpeed;
								}
								if ((int) barrels.get(i).x + (barrels.get(i).isRight ? 0 : barrels.get(i).barrel.getWidth() / tileWidth) + (barrels.get(i).isRight ? barrelSpeed : -barrelSpeed) < 27)
									if (map.getTileId((int) (barrels.get(i).x + (barrels.get(i).isRight ? 0 : barrels.get(i).barrel.getWidth() / tileWidth) + (barrels.get(i).isRight ? barrelSpeed : -barrelSpeed)), (int) barrels.get(i).y - 1, objectLayer) != 0) {
									barrels.get(i).y--;
									barrels.get(i).x += barrels.get(i).isRight ? barrelSpeed : -barrelSpeed;
								}
							}
							if (map.getTileId((int) (barrels.get(i).x + (barrels.get(i).isRight ? 0 : barrels.get(i).barrel.getWidth() / tileWidth) + (barrels.get(i).isRight ? barrelSpeed : -barrelSpeed)), (int) barrels.get(i).y, objectLayer) == 0
									&& map.getTileId((int) (barrels.get(i).x + (barrels.get(i).isRight ? 0 : barrels.get(i).barrel.getWidth() / tileWidth) + (barrels.get(i).isRight ? barrelSpeed : -barrelSpeed)), (int) barrels.get(i).y + 1, objectLayer) == 0) {
								barrels.get(i).isFalling = true;
							}
							if (map.getTileId((int) (barrels.get(i).x), (int) (barrels.get(i).y), ladderingLayer) != 0 && Math.abs(barrels.get(i).x - (int) barrels.get(i).x) < barrelSpeed) {
								Random rand = new Random();
								if (rand.nextBoolean() && (barrels.get(i).level <= panda.level)) {
									barrels.get(i).isLaddering = true;
									barrels.get(i).y++;
								}
							}
						}
					}
				}
			} catch (ArrayIndexOutOfBoundsException e) {
				barrels.get(i).isDead = true;
				barrels.remove(i);
			}
		}
	}
	
	private void updateDK() {
		if (totalTime % barrelSpawnSpeed < barrelSpawnSpeed / 3) dk.dk = Game.dkPickUpBarrel;
		if (totalTime % barrelSpawnSpeed >= barrelSpawnSpeed / 3 && totalTime % barrelSpawnSpeed < barrelSpawnSpeed * 2 / 3) {
			
			if (totalTime % barrelSpawnSpeed == barrelSpawnSpeed / 3) {
				barrels.add(new RegBarrel(6, 84));
				if (barrels.size() % 4 - 1 == 0) {
					dk.dk = Game.dkStill;
					barrels.get(barrels.size() - 1).isDownLock = true;
					barrels.get(barrels.size() - 1).x--;
				} else
					dk.dk = Game.dkRollRight;
			}
		}
		if (totalTime % barrelSpawnSpeed >= barrelSpawnSpeed * 2f / 3f) dk.dk = Game.dkPoundChest;
		
	}
	
	private void updateJumpingFalling() {
		if (i == 0 && !panda.isJumping) {
		} else {
			i %= jumpTime;
			i++;
			if (i < jumpTime / 2) {
				panda.isJumping = true;
				panda.isFalling = false;
				panda.y -= jumpSpeed;
			}
			if (i >= jumpTime / 2) {
				panda.isFalling = true;
				panda.isJumping = false;
				panda.y += jumpSpeed;
			}
		}
		
		if (!panda.isJumping) {
			if (i > jumpTime * .75) {
				if (map.getTileId((int) panda.x, (int) (panda.y), objectLayer) == 0) {
					panda.y += jumpSpeed;
					panda.isFalling = true;
				} else {
					panda.isFalling = false;
					i = 0;
				}
			}
		}
	}
	
	private void updateMovement(int t) {
		if ((input.isKeyDown(Input.KEY_UP) || input.isKeyDown(Input.KEY_DOWN)) && (input.isKeyDown(Input.KEY_RIGHT) || input.isKeyDown(Input.KEY_LEFT)) || (input.isKeyDown(Input.KEY_RIGHT) && input.isKeyDown(Input.KEY_LEFT)) || (input.isKeyDown(Input.KEY_UP) && input.isKeyDown(Input.KEY_DOWN))) {
			Game.dkWalking.setVolume(0);
			switch (last) {
				case 'L':
				case 'l':
					panda.panda = Game.jmStillLeft;
					last = 'l';
					break;
				case 'R':
				case 'r':
					panda.panda = Game.jmStillRight;
					last = 'r';
					break;
			}
		} else {
			// level handling
			panda.level = ((((int) panda.y - 88) > 0) ? ((int) panda.y - 88) / 33 + 1 : 0);
			// up (climb)
			if (input.isKeyDown(Input.KEY_UP) && map.getTileId((int) panda.x, (int) panda.y, ladderLayer) != 0 && !panda.isJumping && !panda.isFalling) {
				if (map.getTileId((int) panda.x, (int) (panda.y - pandaClimbSpeed), ladderLayer) != 0) {
					panda.isClimbing = true;
					panda.y -= pandaClimbSpeed;
					last = 'u';
					Game.jmClimb.update(t);
					panda.panda = Game.jmClimb;
					panda.isClimbing = true;
					
				}
				if (map.getTileId((int) panda.x, (int) panda.y, objectLayer) == 0 && map.getTileId((int) panda.x, (int) panda.y, ladderLayer) != 0) {
					Game.dkWalking.setVolume(1);
				}
				if (map.getTileId((int) panda.x, (int) (panda.y), objectLayer) != 0) {
					panda.isClimbing = false;
					Game.dkWalking.setVolume(0);
				}
				if (map.getTileId((int) panda.x, (int) (panda.y - pandaClimbSpeed), ladderLayer) == 0) {
					Game.dkWalking.setVolume(0);
				}
				if (map.getTileId((int) panda.x, (int) (panda.y - pandaClimbSpeed), enterStateLayer) != 0) {
					won = true;
					timeOfWin = totalTime;
				}
				
			}
			
			// down (climbdown)
			if (input.isKeyDown(Input.KEY_DOWN) && map.getTileId((int) panda.x, (int) panda.y, ladderLayer) != 0 && !panda.isJumping && !panda.isFalling) {
				if (map.getTileId((int) panda.x, (int) (panda.y + pandaClimbSpeed), ladderLayer) != 0) {
					panda.isClimbing = true;
					panda.y += pandaClimbSpeed;
					last = 'd';
					Game.jmClimb.update(250 - t);
					panda.panda = Game.jmClimb;
				}
				if (map.getTileId((int) panda.x, (int) panda.y, objectLayer) == 0) {
					Game.dkWalking.setVolume(1);
				}
				if (map.getTileId((int) panda.x, (int) (panda.y), objectLayer) != 0) {
					panda.isClimbing = false;
					Game.dkWalking.setVolume(0);
				}
				
				if (map.getTileId((int) panda.x, (int) (panda.y + pandaClimbSpeed), ladderLayer) == 0) {
					Game.dkWalking.setVolume(0);
				}
			}
			
			// left
			if (input.isKeyDown(Input.KEY_LEFT) && !input.isKeyDown(Input.KEY_UP) && !input.isKeyDown(Input.KEY_DOWN) && !(panda.isFalling || panda.isJumping || panda.isClimbing)) {
				Game.dkWalking.setVolume(1);
				Game.jmWalkLeft.update(t);
				last = 'L';
				
				if (!panda.isClimbing) panda.panda = Game.jmWalkLeft;
				if ((int) panda.x - .014 >= 0) if (map.getTileId((int) (panda.x - pandaWalkSpeed), (int) panda.y, objectLayer) != 0) panda.x -= pandaWalkSpeed;
				else {
					if (!panda.isClimbing && (int) panda.x - .014 >= 0) if (map.getTileId((int) (panda.x - pandaWalkSpeed), (int) panda.y + 1, objectLayer) != 0) {
						panda.y++;
						panda.x -= pandaWalkSpeed;
					}
					if ((int) panda.x - .014 >= 0) if (map.getTileId((int) (panda.x - pandaWalkSpeed), (int) panda.y - 1, objectLayer) != 0) {
						panda.y--;
						panda.x -= pandaWalkSpeed;
					}
				}
			}
			
			// right
			if (input.isKeyDown(Input.KEY_RIGHT) && !input.isKeyDown(Input.KEY_UP) && !input.isKeyDown(Input.KEY_DOWN) && !(panda.isFalling || panda.isJumping || panda.isClimbing)) {
				
				Game.dkWalking.setVolume(1);
				Game.jmWalkRight.update(t);
				last = 'R';
				panda.panda = Game.jmWalkRight;
				
				if ((int) panda.x + pandaWalkSpeed < 27) if (map.getTileId((int) (panda.x + pandaWalkSpeed), (int) panda.y, objectLayer) != 0) panda.x += pandaWalkSpeed;
				else {
					if (!panda.isClimbing && (int) panda.x + pandaWalkSpeed < 27) if (map.getTileId((int) (panda.x + pandaWalkSpeed), (int) panda.y + 1, objectLayer) != 0) {
						panda.y++;
						panda.x += pandaWalkSpeed;
					}
					if ((int) panda.x + pandaWalkSpeed < 27) if (map.getTileId((int) (panda.x + pandaWalkSpeed), (int) panda.y - 1, objectLayer) != 0) {
						panda.y--;
						panda.x += pandaWalkSpeed;
					}
				}
			}
			
			// jump
			if (input.isKeyPressed(Input.KEY_SPACE) && map.getTileId((int) panda.x, (int) panda.y, objectLayer) != 0 && !panda.isJumping) {
				panda.isJumping = true;
				Game.dkJump.play();
			}
			
			if (!(input.isKeyDown(Input.KEY_UP) || input.isKeyDown(Input.KEY_DOWN) || input.isKeyDown(Input.KEY_LEFT) || input.isKeyDown(Input.KEY_RIGHT)) && !(panda.isFalling || panda.isJumping || panda.isClimbing)) {
				Game.dkWalking.setVolume(0);
				switch (last) {
					case 'L':
					case 'l':
						panda.panda = Game.jmStillLeft;
						last = 'l';
						break;
					case 'R':
					case 'r':
						panda.panda = Game.jmStillRight;
						last = 'r';
						break;
				}
			}
			if (!input.isKeyDown(Input.KEY_DOWN) && !input.isKeyDown(Input.KEY_UP) && !input.isKeyDown(Input.KEY_LEFT) && !input.isKeyDown(Input.KEY_RIGHT)) {
				Game.dkWalking.setVolume(0f);
			}
			if (panda.isFalling || panda.isJumping) {
				Game.dkWalking.setVolume(0f);
				switch (last) {
					case 'l':
						panda.panda = Game.jmJumpL;
						break;
					case 'r':
						panda.panda = Game.jmJumpR;
						break;
					case 'L':
						panda.panda = Game.jmJumpL;
						if (panda.x - .014 > 0) panda.x -= pandaWalkSpeed / 2;
						break;
					case 'R':
						panda.panda = Game.jmJumpR;
						if (panda.x + .014 < 28) panda.x += pandaWalkSpeed / 2;
						break;
				}
			}
		}
	}
	
	private void updateWinScene(GameContainer gc, StateBasedGame sbg, int t) {
		if (totalTime == timeOfWin + 1) {
			barrels.clear();
			Game.dkWalking.stop();
			Game.dkBackground.stop();
			panda.panda = Game.jmStillLeft;
			dk.dk = Game.dkStill;
		}
		if (totalTime == timeOfWin + 2) Game.dkWin.play();
		if (!Game.dkWin.playing() && totalTime > timeOfWin + 1) {
			Game.pet3Found = true;
			sbg.enterState(Game.roam);
			Game.pollyWolly.loop();
		}
	}
	
	public static void resetState(GameContainer gc, StateBasedGame sbg, int t) {
		System.out.println("Reseted State");
		Game.dkDeath.stop();
		Game.dkBackground.loop();
		panda.panda = Game.jmStillRight;
		panda.isDead = false;
		panda.isJumping = false;
		panda.isClimbing = false;
		panda.isFalling = false;
		panda.timeOfDeath = -1;
		input.clearKeyPressedRecord();
		input = gc.getInput();
		panda.x = 5;
		panda.y = 248;
		i = 0;
		Game.jmDead.restart();
		quit = false;
	}
	
	private static void sleepHandling(GameContainer gc, StateBasedGame sbg, int t) throws SlickException {
		if (sbg.getCurrentStateID() == Game.lvl03) {
			Game.appgc.setDisplayMode((int) (224 * zoomFactor), (int) (256 * zoomFactor), false);
		}
	}
}

abstract class Barrel {
	public float x, y;
	public Animation barrel;
	public boolean isDead, isRight, isFalling, isLaddering, isDownLock;;
	public int type, level;
	public int[] path;
	public static final int REG = 0, BLUE = 1;
	public Shape collider;
	
	public Barrel(float x, float y) {
		this.x = x;
		this.y = y;
		isDead = false;
		isDownLock = false;
		isRight = true;
		isLaddering = false;
		level = 0;
		barrel = new Animation();
	}
	
}

class BlueBarrel extends Barrel {
	public BlueBarrel(float x, float y) {
		super(x, y);
		type = Barrel.BLUE;
	}
}

class DK {
	public float x, y;
	public Animation dk;
	
	public DK(float x, float y) {
		this.x = x;
		this.y = y;
		dk = Game.dkPickUpBarrel;
	}
}

class JumpMan {
	public float x, y;
	public Animation panda;
	public boolean isDead, isFalling, isJumping, isClimbing;
	public int level, timeOfDeath;
	public Shape collider;
	
	public JumpMan(float x, float y) {
		this.x = x;
		this.y = y;
		this.isClimbing = false;
		this.isDead = false;
		this.isJumping = false;
		this.isFalling = false;
		this.panda = new Animation();
		level = 1;
	}
}

class RegBarrel extends Barrel {
	public RegBarrel(float x, float y) {
		super(x, y);
		type = Barrel.REG;
	}
}