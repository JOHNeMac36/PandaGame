package game;

import java.util.Random;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.openal.SoundStore;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.tiled.TiledMap;

public class Level03 extends BasicGameState {
	
	private TiledMap map, introMap;
	private int backgroundLayer, objectLayer, ladderLayer, enterStateLayer;
	private char last = 'd';
	public static boolean quit, won;
	public static Music music;
	public static JumpMan panda;
	public static DK dk;
	public static Input input;
	public static final float zoomFactor = 2.5f, zoomFactor2 = 2f;
	private int tileWidth, tileHeight;
	private int i = 0, totalTime = 0, backMusicLoc = 0, timeOfWin = -1;
	
	public Level03(int i) {
	}
	
	@Override
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
		
		panda = new JumpMan(2, 248);
		dk = new DK(2, 84);
		map = new TiledMap("res/maps/lvlDonkeyKong.tmx");
		introMap = new TiledMap("res/maps/lvlDonkeyKongPreview.tmx");
		objectLayer = map.getLayerIndex("Objects");
		backgroundLayer = map.getLayerIndex("Background");
		objectLayer = map.getLayerIndex("Object");
		enterStateLayer = map.getLayerIndex("EnterState");
		ladderLayer = map.getLayerIndex("Ladder");
		music = new Music("res/oggs/music.ogg");
		panda.panda = Game.jmStillRight;
		input = gc.getInput();
		tileHeight = map.getTileHeight();
		tileWidth = map.getTileWidth();
	}
	
	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
		renderMap(gc, sbg, g);
		renderPanda(gc, sbg, g);
		renderDK(gc, sbg, g);
		renderInfo(gc, sbg, g);
	}
	
	private void renderDK(GameContainer gc, StateBasedGame sbg, Graphics g) {
		g.scale(zoomFactor2, zoomFactor2);
		dk.dk.draw((dk.x - .5f) * tileWidth * zoomFactor / zoomFactor2, (dk.y) * tileHeight * zoomFactor / zoomFactor2 - dk.dk.getHeight());
		g.scale(1f / zoomFactor2, 1f / zoomFactor2);
	}
	
	private void renderInfo(GameContainer gc, StateBasedGame sbg, Graphics g) {
		g.scale(zoomFactor, zoomFactor);
		g.setColor(Color.green);
		g.fillRect(panda.x * tileWidth, panda.y * tileHeight, 1, 1);
		g.scale(1f / zoomFactor, 1f / zoomFactor);
		
		g.drawString("isClimbing, isFalling, i : " + panda.isClimbing + " " + panda.isFalling + " " + i, 100, 15);
		g.drawString("x,y" + panda.x + " " + panda.y, 100, 30);
		
	}
	
	private void renderPanda(GameContainer gc, StateBasedGame sbg, Graphics g) {
		g.scale(zoomFactor2, zoomFactor2);
		panda.panda.draw((panda.x - .5f) * tileWidth * zoomFactor / zoomFactor2,
				(panda.y) * tileHeight * zoomFactor / zoomFactor2 - panda.panda.getHeight());
		g.scale(1f / zoomFactor2, 1f / zoomFactor2);
	}
	
	private void renderMap(GameContainer gc, StateBasedGame sbg, Graphics g) {
		g.scale(zoomFactor, zoomFactor);
		map.render(0, 0);
		g.scale(1f / zoomFactor, 1f / zoomFactor);
		
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
			if (totalTime == 1) {
				Game.dkBackground.loop();
				Game.dkWalking.loop(1, 0);
			}
			map.getTileId(0, 0, objectLayer);
			sleepHandling(gc, sbg, t);
			updateMovement(t);
			updateJumpingFalling();
			
		}
	}
	
	private void updateWinScene(GameContainer gc, StateBasedGame sbg, int t) {
		if (totalTime == timeOfWin + 1) {
			Game.dkWin.play();
			Game.dkBackground.stop();
			panda.panda = Game.jmStillLeft;
		}
	}
	
	private void updateJumpingFalling() {
		if (i == 0 && !panda.isJumping) {
		} else {
			i %= 150;
			i++;
			if (i < 75) {
				panda.isJumping = true;
				panda.isFalling = false;
				panda.y -= .2f;
			}
			if (i >= 75) {
				panda.isFalling = true;
				panda.isJumping = false;
				panda.y += .2f;
			}
		}
		
		if (!panda.isJumping) {
			if (i > 130) {
				if (map.getTileId((int) panda.x, (int) (panda.y), objectLayer) == 0) {
					panda.y += .2f;
					panda.isFalling = true;
				} else {
					panda.isFalling = false;
					i = 0;
				}
			}
		}
	}
	
	private void updateMovement(int t) {
		if ((input.isKeyDown(Input.KEY_UP) || input.isKeyDown(Input.KEY_DOWN))
				&& (input.isKeyDown(Input.KEY_RIGHT) || input.isKeyDown(Input.KEY_LEFT))
				|| (input.isKeyDown(Input.KEY_RIGHT) && input.isKeyDown(Input.KEY_LEFT))
				|| (input.isKeyDown(Input.KEY_UP) && input.isKeyDown(Input.KEY_DOWN))) {
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
			// up (climb)
			if (input.isKeyDown(Input.KEY_UP) && map.getTileId((int) panda.x, (int) panda.y, ladderLayer) != 0 && !panda.isJumping
					&& !panda.isFalling) {
				if (map.getTileId((int) panda.x, (int) (panda.y - .14), ladderLayer) != 0) {
					panda.isClimbing = true;
					panda.y -= 0.14;
					last = 'u';
					Game.jmClimb.update(t);
					panda.panda = Game.jmClimb;
					panda.isClimbing = true;
					
				}
				if (map.getTileId((int) panda.x, (int) panda.y, objectLayer) == 0
						&& map.getTileId((int) panda.x, (int) panda.y, ladderLayer) != 0) {
					Game.dkWalking.setVolume(1);
				}
				if (map.getTileId((int) panda.x, (int) (panda.y), objectLayer) != 0) {
					panda.isClimbing = false;
					Game.dkWalking.setVolume(0);
				}
				if (map.getTileId((int) panda.x, (int) (panda.y - .14), ladderLayer) == 0) {
					Game.dkWalking.setVolume(0);
				}
				if (map.getTileId((int) panda.x, (int) (panda.y - .14), enterStateLayer) != 0) {
					won = true;
					timeOfWin = totalTime;
				}
				
			}
			
			// down (climbdown)
			if (input.isKeyDown(Input.KEY_DOWN) && map.getTileId((int) panda.x, (int) panda.y, ladderLayer) != 0 && !panda.isJumping
					&& !panda.isFalling) {
				if (map.getTileId((int) panda.x, (int) (panda.y + .14), ladderLayer) != 0) {
					panda.isClimbing = true;
					panda.y += 0.14;
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
				
				if (map.getTileId((int) panda.x, (int) (panda.y + .14), ladderLayer) == 0) {
					Game.dkWalking.setVolume(0);
				}
			}
			
			// left
			if (input.isKeyDown(Input.KEY_LEFT) && !input.isKeyDown(Input.KEY_UP) && !input.isKeyDown(Input.KEY_DOWN)
					&& !(panda.isFalling || panda.isJumping || panda.isClimbing)) {
				Game.dkWalking.setVolume(1);
				Game.jmWalkLeft.update(t);
				last = 'L';
				
				if (!panda.isClimbing) panda.panda = Game.jmWalkLeft;
				if ((int) panda.x - .014 >= 0) if (map.getTileId((int) (panda.x - .03), (int) panda.y, objectLayer) != 0) panda.x -= 0.03;
				else {
					if (!panda.isClimbing && (int) panda.x - .014 >= 0)
						if (map.getTileId((int) (panda.x - .03), (int) panda.y + 1, objectLayer) != 0) {
						panda.y++;
						panda.x -= 0.03;
					}
					if ((int) panda.x - .014 >= 0) if (map.getTileId((int) (panda.x - .03), (int) panda.y - 1, objectLayer) != 0) {
						panda.y--;
						panda.x -= 0.03;
					}
				}
			}
			
			// right
			if (input.isKeyDown(Input.KEY_RIGHT) && !input.isKeyDown(Input.KEY_UP) && !input.isKeyDown(Input.KEY_DOWN)
					&& !(panda.isFalling || panda.isJumping || panda.isClimbing)) {
					
				Game.dkWalking.setVolume(1);
				Game.jmWalkRight.update(t);
				last = 'R';
				panda.panda = Game.jmWalkRight;
				
				if ((int) panda.x + .03 < 27) if (map.getTileId((int) (panda.x + .03), (int) panda.y, objectLayer) != 0) panda.x += 0.03;
				else {
					if (!panda.isClimbing && (int) panda.x + .03 < 27)
						if (map.getTileId((int) (panda.x + .03), (int) panda.y + 1, objectLayer) != 0) {
						panda.y++;
						panda.x += 0.03;
					}
					if ((int) panda.x + .03 < 27) if (map.getTileId((int) (panda.x + .03), (int) panda.y - 1, objectLayer) != 0) {
						panda.y--;
						panda.x += 0.03;
					}
				}
			}
			
			// jump
			if (input.isKeyPressed(Input.KEY_SPACE) && map.getTileId((int) panda.x, (int) panda.y, objectLayer) != 0 && !panda.isJumping) {
				panda.isJumping = true;
				Game.dkJump.play();
			}
			
			if (!(input.isKeyDown(Input.KEY_UP) || input.isKeyDown(Input.KEY_DOWN) || input.isKeyDown(Input.KEY_LEFT)
					|| input.isKeyDown(Input.KEY_RIGHT)) && !(panda.isFalling || panda.isJumping || panda.isClimbing)) {
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
			if (!input.isKeyDown(Input.KEY_DOWN) && !input.isKeyDown(Input.KEY_UP) && !input.isKeyDown(Input.KEY_LEFT)
					&& !input.isKeyDown(Input.KEY_RIGHT)) {
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
						if (panda.x - .014 > 0) panda.x -= .014;
						break;
					case 'R':
						panda.panda = Game.jmJumpR;
						if (panda.x + .014 < 28) panda.x += .014;
						break;
				}
			}
		}
	}
	
	private static void sleepHandling(GameContainer gc, StateBasedGame sbg, int t) throws SlickException {
		if (sbg.getCurrentStateID() == Game.lvl03) {
			Game.appgc.setDisplayMode((int) (224 * zoomFactor), (int) (256 * zoomFactor), false);
		}
	}
	
	@Override
	public int getID() {
		return Game.lvl03;
	}
	
	public static void resetState() {
		music.stop();
		panda.x = 2;
		panda.y = 248;
		quit = false;
	}
}

class JumpMan {
	public float x, y;
	public Animation panda;
	public boolean isDead, isFalling, isJumping, isClimbing;
	
	public JumpMan(float x, float y) {
		this.x = x;
		this.y = y;
		this.isClimbing = false;
		this.isDead = false;
		this.isJumping = false;
		this.isFalling = false;
		this.panda = new Animation();
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