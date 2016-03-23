package game;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.tiled.TiledMap;

public class Roam extends BasicGameState {
	// attributes
	public static TiledMap map;
	public static Animation panda;
	public static Animation penguin;
	public static Animation char1, char2, char3, char4;
	public static boolean quit, speaking, win;
	public static boolean pet1Found, pet2Found, pet3Found, pet4Found;
	public static char charLock1, charLock2, charLock3, charLock4;
	private char last = 'd';
	public static double char1X, char1Y, char2X, char2Y, char3X, char3Y, char4X, char4Y;
	private static double x, y, penX, penY;
	public Input input;
	private int lastChar;
	private int i, oneHit, j;
	private int mapXBound, mapYBound;
	private int backgroundLayer, graphLayer, groundLayer1, groundLayer2, objectLayer,
			enterStateLayer, overheadLayer1, overheadLayer2, overheadLayer3, overheadLayer4;
	public static Music music;
	private static String strSpeak;
	// end of attributes
	
	@Override
	public int getID() {
		// TODO Auto-generated method stub
		return Game.roam;
	}
	
	public Roam(int i) {
	}
	
	public void init(GameContainer gc, StateBasedGame sbg) {
		try {
			map = new TiledMap("res/maps/mapRoam.tmx");
		} catch (SlickException e) {
		}
		
		backgroundLayer = map.getLayerIndex("Background");
		graphLayer = map.getLayerIndex("GraphLayer");
		groundLayer1 = map.getLayerIndex("Ground1");
		objectLayer = map.getLayerIndex("Objects");
		groundLayer2 = map.getLayerIndex("Ground2");
		enterStateLayer = map.getLayerIndex("EnterState");
		overheadLayer1 = map.getLayerIndex("Overhead1");
		overheadLayer2 = map.getLayerIndex("Overhead2");
		overheadLayer3 = map.getLayerIndex("Overhead3");
		overheadLayer4 = map.getLayerIndex("Overhead4");
		
		panda = Game.pandaStillDown;
		penguin = Game.penguinStillDown;
		x = 10;
		y = 10;
		penX = 44.0;
		penY = 31.0;
		i = 0;
		oneHit = 0;
		char1X = 20.0;
		char1Y = 5.0;
		char2X = 90.0;
		char2Y = 10.0;
		char3X = 10.0;
		char3Y = 90.0;
		char4X = 90.0;
		char4Y = 90.0;
		charLock1 = '_';
		charLock2 = '_';
		charLock3 = '_';
		charLock4 = '_';
		mapXBound = map.getHeight() - 1;
		mapYBound = map.getWidth() - 1;
		speaking = false;
		pet1Found = true;
		pet2Found = true;
		pet3Found = true;
		pet4Found = true;
		
		win = false;
		input = gc.getInput();
	}
	
	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
		if (!(x > 0 && x < mapXBound)) {
			if (x <= 0) x = 0;
			if (x >= map.getHeight()) x = mapXBound;
		}
		if (!(y > 0 && y < mapYBound)) {
			if (y <= 0) y = 0;
			if (y >= mapYBound) y = mapYBound;
		}
		
		/**
		 * x location to render at y location to render at the x tile location
		 * to start rendering the y tile location to start rendering width of
		 * section to render width of section to render
		 */
		
		map.render(0, 0, (int) x, (int) y, 20, 20, backgroundLayer, true);
		map.render(0, 0, (int) x, (int) y, 20, 20, graphLayer, true);
		map.render(0, 0, (int) x, (int) y, 20, 20, groundLayer1, true);
		
		map.render(0, 0, (int) x, (int) y, 20, 20, objectLayer, true);
		
		map.render(0, 0, (int) x, (int) y, 20, 20, groundLayer2, true);
		map.render(0, 0, (int) x, (int) y, 20, 20, enterStateLayer, true);
		
		if (pet1Found) {
		}
		if (pet2Found)
			penguin.draw((320 - ((int) x - (int) penX) * 32), (320 - ((int) y - (int) penY) * 32));
		if (pet3Found) {
		}
		if (pet4Found) {
		}
		
		panda.draw(320, 320);
		
		char1.draw((320 - ((int) x - (int) char1X) * 32), (320 - ((int) y - (int) char1Y) * 32));
		char2.draw((320 - ((int) x - (int) char2X) * 32), (320 - ((int) y - (int) char2Y) * 32));
		char3.draw((320 - ((int) x - (int) char3X) * 32), (320 - ((int) y - (int) char3Y) * 32));
		char4.draw((320 - ((int) x - (int) char4X) * 32), (320 - ((int) y - (int) char4Y) * 32));
		
		map.render(0, 0, (int) x, (int) y, 20, 20, overheadLayer1, true);
		map.render(0, 0, (int) x, (int) y, 20, 20, overheadLayer2, true);
		map.render(0, 0, (int) x, (int) y, 20, 20, overheadLayer3, true);
		map.render(0, 0, (int) x, (int) y, 20, 20, overheadLayer4, true);
		
		// g.drawString("( " + (int) char1X + " , " + (int) char1Y + " )", 50,
		// 50);
		// g.drawString("( " + (int) penX + " , " + (int) penY + " )", 50, 150);
		// g.drawString("" + i, 50, 150);
		if (speaking) {
			g.setColor(Color.white);
			g.fillRect(20, 500, 600, 100);
			g.setColor(Color.black);
			g.drawString(strSpeak, 30, 510);
			g.setColor(Color.red);
		}
		
		if (win) {
			Game.confetti.draw(-20, -150 + j);
			Game.confetti.draw(-60, -300 + j);
			Game.confetti.draw(-100, -450 + j);
			Game.confetti.draw(-140, -600 + j);
			
		}
		if (input.isKeyPressed(Input.KEY_SPACE) && speaking == true) {
			speaking = false;
		}
		
	}
	
	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int t) throws SlickException {
		map.getTileId(0, 0, objectLayer);
		j++;
		j %= 1500;
		if (speaking) {
		} else {
			// unlocking
			if (oneHit == 1) {
				say("Wait! Something's happening!!");
				oneHit++;
			}
			
			if (charLock1 != '_' && charLock2 != '_' && charLock3 != '_' && charLock4 != '_'
					&& oneHit == 3) {
				map = null;
				map = new TiledMap("res/maps/mapRoamUnlocked.tmx");
				oneHit++;
			}
			if (oneHit == 2) oneHit++;
			// end unlocking
			
			// enter next level
			if (map.getTileId((int) x + 10, (int) y + 10, enterStateLayer) != 0) {
				sbg.enterState(Game.lvlBoss);
				y += 1;
			}
			if (lastChar != 0) {
				switch (lastChar) {
					case 1:
						sbg.enterState(Game.lvl01);
						break;
					case 2:
						sbg.enterState(Game.lvl02);
						break;
					case 3:
						sbg.enterState(Game.lvl03);
						break;
					case 4:
						sbg.enterState(Game.lvl04);
						break;
				}
				lastChar = 0;
			}
			// up
			if (input.isKeyDown(Input.KEY_UP)) {
				panda = Game.pandaWalkUp;
				if (map.getTileId((int) x + 10, (int) y + 9, objectLayer) == 0
						|| map.getTileId((int) x + 10, (int) y + 9, groundLayer2) != 0
								&& ((int) x != (int) penX || (int) y != (int) penY - 1))
					y -= 0.02;
				last = 'u';
			}
			
			// down
			if (input.isKeyDown(Input.KEY_DOWN)) {
				panda = Game.pandaWalkDown;
				if (map.getTileId((int) x + 10, (int) y + 11, objectLayer) == 0
						|| map.getTileId((int) x + 10, (int) y + 11, groundLayer2) != 0
								&& ((int) x != (int) penX || (int) y + 1 != (int) penY))
					y += 0.02;
				last = 'd';
			}
			
			// left
			if (input.isKeyDown(Input.KEY_LEFT)) {
				panda = Game.pandaWalkLeft;
				if (map.getTileId((int) x + 9, (int) y + 10, objectLayer) == 0
						|| map.getTileId((int) x + 9, (int) y + 10, groundLayer2) != 0
								&& ((int) x - 1 != (int) penX || y != (int) penY))
					x -= 0.02;
				last = 'l';
			}
			
			// right
			if (input.isKeyDown(Input.KEY_RIGHT)) {
				panda = Game.pandaWalkRight;
				if (map.getTileId((int) x + 11, (int) y + 10, objectLayer) == 0
						|| map.getTileId((int) x + 11, (int) y + 10, groundLayer2) != 0
								&& !((int) x + 1 == (int) penX || (int) y == (int) penY))
					x += 0.02;
				last = 'r';
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
			
			// penguin walking
			i++;
			i %= 1000;
			if (i >= 0 && i < 250) {
				penguin = Game.penguinWalkRight;
				penX = 90;
				penY = 9;
				
			}
			if (i >= 250 && i < 500) {
				penguin = Game.penguinWalkDown;
				penX = 91;
				penY = 9;
				
			}
			if (i >= 500 && i < 750) {
				penguin = Game.penguinWalkLeft;
				penX = 91;
				penY = 10;
				
			}
			if (i >= 750 && i < 1000) {
				penguin = Game.penguinWalkUp;
				penX = 90;
				penY = 10;
				
			}
			
			// 4 characters
			if (i >= 0 && i < 250) {
				char1 = Game.char1StillRight;
				char2 = Game.char2StillRight;
				char3 = Game.char3StillRight;
				char4 = Game.char4StillRight;
			}
			if (i >= 250 && i < 500) {
				char1 = Game.char1StillDown;
				char2 = Game.char2StillDown;
				char3 = Game.char3StillDown;
				char4 = Game.char4StillDown;
			}
			if (i >= 500 && i < 750) {
				char1 = Game.char1StillLeft;
				char2 = Game.char2StillLeft;
				char3 = Game.char3StillLeft;
				char4 = Game.char4StillLeft;
			}
			if (i >= 750 && i < 1000) {
				char1 = Game.char1StillUp;
				char2 = Game.char2StillUp;
				char3 = Game.char3StillUp;
				char4 = Game.char4StillUp;
			}
			
			// talk
			if (input.isKeyPressed(Input.KEY_SPACE)) {
				if (last == 'r' && (int) x == 49 && (int) y == 51) {
					say("This is an ancient fountain...Look! There is a four digit lock:\n"
							+ "                        " + charLock1 + "   " + charLock2 + "   "
							+ charLock3 + "   " + charLock4);
					if (charLock1 != '_' && charLock2 != '_' && charLock3 != '_' && charLock4 != '_'
							&& oneHit == 0)
						oneHit = 1;
				}
				if (last == 'd' && (int) x == 50 && (int) y == 50) {
					say("This is an ancient fountain...Look! There is a four digit lock:\n"
							+ "                        " + charLock1 + "   " + charLock2 + "   "
							+ charLock3 + "   " + charLock4);
					if (charLock1 != '_' && charLock2 != '_' && charLock3 != '_' && charLock4 != '_'
							&& oneHit == 0)
						oneHit = 1;
				}
				if (last == 'l' && (int) x == 51 && (int) y == 51) {
					say("This is an ancient fountain...Look! There is a four digit lock:\n"
							+ "                        " + charLock1 + "   " + charLock2 + "   "
							+ charLock3 + "   " + charLock4);
					if (charLock1 != '_' && charLock2 != '_' && charLock3 != '_' && charLock4 != '_'
							&& oneHit == 0)
						oneHit = 1;
				}
				if (last == 'u' && (int) x == 50 && (int) y == 52) {
					say("This is an ancient fountain...Look! There is a four digit lock:\n"
							+ "                        " + charLock1 + "   " + charLock2 + "   "
							+ charLock3 + "   " + charLock4);
					if (charLock1 != '_' && charLock2 != '_' && charLock3 != '_' && charLock4 != '_'
							&& oneHit == 0)
						oneHit = 1;
				}
				
				// char1
				if (last == 'r' && (int) x == 19 && (int) y == 5) {
					char1 = Game.char1StillLeft;
					say("Hello there! I lost my wittle pet, Fluffly, yesturday!\n"
							+ "Can you help me find him?");
					lastChar = 1;
					
				}
				if (last == 'd' && (int) x == 20 && (int) y == 4) {
					char1 = Game.char1StillUp;
					say("Hello there! I lost my wittle pet, Fluffly, yesturday!\n"
							+ "Can you help me find him?");
					lastChar = 1;
				}
				if (last == 'l' && (int) x == 21 && (int) y == 5) {
					char1 = Game.char1StillRight;
					say("Hello there! I lost my wittle pet, Fluffly, yesturday!\n"
							+ "Can you help me find him?");
					lastChar = 1;
				}
				if (last == 'u' && (int) x == 20 && (int) y == 6) {
					char1 = Game.char1StillDown;
					say("Hello there! I lost my wittle pet, Fluffly, yesturday!\n"
							+ "Can you help me find him?");
					lastChar = 1;
				}
				// char2
				if (last == 'r' && (int) x == 89 && (int) y == 10) {
					char2 = Game.char2StillLeft;
					say("Hey Mr. Pandaman! My little penguin, Flappy, ran away yesturday!\n"
							+ "Can you find Flappy for me?");
					lastChar = 2;
					if (pet2Found) {
						say("Thanks for youre help!! Here Flappy...");
						lastChar = 0;
					}
				}
				if (last == 'd' && (int) x == 90 && (int) y == 9) {
					char2 = Game.char2StillUp;
					say("Hey Mr. Pandaman! My little penguin, Flappy, ran away yesturday!\n"
							+ "Can you find Flappy for me?");
					lastChar = 2;
					if (pet2Found) {
						say("Thanks for youre help!! Here Flappy...");
						lastChar = 0;
					}
				}
				if (last == 'l' && (int) x == 91 && (int) y == 10) {
					char2 = Game.char2StillRight;
					say("Hey Mr. Pandaman! My little penguin, Flappy, ran away yesturday!\n"
							+ "Can you find Flappy for me?");
					lastChar = 2;
					if (pet2Found) {
						say("Thanks for youre help!! Here Flappy...");
						lastChar = 0;
					}
				}
				if (last == 'u' && (int) x == 90 && (int) y == 11) {
					char2 = Game.char2StillDown;
					say("Hey Mr. Pandaman! My little penguin, Flappy, ran away yesturday!\n"
							+ "Can you find Flappy for me?");
					lastChar = 2;
					if (pet2Found) {
						say("Thanks for youre help!! Here Flappy...");
						lastChar = 0;
					}
				}
				// char3
				if (last == 'r' && (int) x == 9 && (int) y == 90) {
					char3 = Game.char3StillLeft;
					say("Pandaman! Good thing you're here! Yesturday I lost Shelly, my turtle!\n"
							+ "Can you help me find him?");
					lastChar = 3;
				}
				if (last == 'd' && (int) x == 10 && (int) y == 89) {
					char3 = Game.char3StillUp;
					say("Pandaman! Good thing you're here! Yesturday I lost Shelly, my turtle!\n"
							+ "Can you help me find him?");
					lastChar = 3;
				}
				if (last == 'l' && (int) x == 11 && (int) y == 90) {
					char3 = Game.char3StillRight;
					say("Pandaman! Good thing you're here! Yesturday I lost Shelly, my turtle!\n"
							+ "Can you help me find him?");
					lastChar = 3;
				}
				if (last == 'u' && (int) x == 10 && (int) y == 91) {
					char3 = Game.char3StillDown;
					say("Pandaman! Good thing you're here! Yesturday I lost Shelly, my turtle!\n"
							+ "Can you help me find him?");
					lastChar = 3;
				}
				// char4
				if (last == 'r' && (int) x == 89 && (int) y == 90) {
					char4 = Game.char4StillLeft;
					say("I can't find Woof-Woof! He ranaway yesturday!\n"
							+ "Can you find him for me?");
					lastChar = 4;
				}
				if (last == 'd' && (int) x == 90 && (int) y == 89) {
					char4 = Game.char4StillUp;
					say("I can't find Woof-Woof! He ranaway yesturday!\n"
							+ "Can you find him for me?");
					lastChar = 4;
				}
				if (last == 'l' && (int) x == 91 && (int) y == 90) {
					char4 = Game.char4StillRight;
					say("I can't find Woof-Woof! He ranaway yesturday!\n"
							+ "Can you find him for me?");
					lastChar = 4;
				}
				if (last == 'u' && (int) x == 90 && (int) y == 91) {
					char4 = Game.char4StillDown;
					say("I can't find Woof-Woof! He ranaway yesturday!\n"
							+ "Can you find him for me?");
					lastChar = 4;
				}
			}
		}
	}
	
	private void say(String str) {
		strSpeak = str;
		speaking = true;
	}
	
	public static void resetState() {
		music.stop();
		x = 10;
		y = 10;
		quit = false;
	}
	
}
