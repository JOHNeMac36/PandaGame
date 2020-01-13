package game;

import java.io.IOException;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
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
	private static char last = 'd';
	public static float char1X, char1Y, char2X, char2Y, char3X, char3Y, char4X, char4Y;
	private static float x, y, penX, penY, walkI, pandaHeight, pandaWidth;;
	public static Input input;
	private static int lastChar;
	private static int i, oneHit, j, tileHeight, tileWidth;

	private static int mapXBound, mapYBound;
	private static int backgroundLayer, graphLayer, groundLayer1, groundLayer2, objectLayer, enterStateLayer,
			overheadLayer1, overheadLayer2, overheadLayer3, overheadLayer4;
	private static String strSpeak;
	// end of attributes
	private static int oneHit2;

	@Override
	public int getID() {
		// TODO Auto-generated method stub
		return Game.roam;
	}

	public Roam(int i) {
	}

	// implemented methods
	@Override
	public void init(GameContainer gc, StateBasedGame sbg) {
		try {
			map = new TiledMap("res/maps/mapRoam.tmx");
		} catch (Exception ex) {
		}

		Game.pet1Found = false;
		Game.pet2Found = false;
		Game.pet3Found = false;
		Game.pet4Found = false;

		panda = Game.pandaStillDown;
		penguin = Game.penguinStillDown;
		pandaHeight = (float) Game.len / (float) map.getTileHeight();
		pandaWidth = (float) Game.wid / (float) map.getTileWidth();

		char1 = Game.char1StillDown;
		char2 = Game.char2StillDown;
		char3 = Game.char3StillDown;
		char4 = Game.char4StillDown;

		tileWidth = map.getTileWidth();
		tileHeight = map.getTileHeight();

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

		walkI = .08f;
		x = 10;
		y = 10;
		penX = 93.0f;
		penY = 8.0f;
		i = 0;
		oneHit = 0;
		char1X = 20.0f;
		char1Y = 5.0f;
		char2X = 90.0f;
		char2Y = 10.0f;
		char3X = 10.0f;
		char3Y = 90.0f;
		char4X = 90.0f;
		char4Y = 90.0f;
		mapXBound = map.getHeight() - 1;
		mapYBound = map.getWidth() - 1;
		speaking = false;

		win = false;
		input = gc.getInput();
	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
		if (!(x > 0 && x < mapXBound)) {
			if (x <= 0)
				x = 0;
			if (x >= map.getHeight())
				x = mapXBound;
		}
		if (!(y > 0 && y < mapYBound)) {
			if (y <= 0)
				y = 0;
			if (y >= mapYBound)
				y = mapYBound;
		}

		/**
		 * x location to render at y location to render at the x tile location
		 * to start rendering the y tile location to start rendering width of
		 * section to render width of section to render
		 */
		renderGroundLayers(g);

		renderPets(g);

		panda.draw(320, 320);

		renderCharacters(g);

		renderOverHead(g);
		if (speaking)
			renderSpeech(g);

		if (win)
			renderConfetti(g);

		checkExitSpeech();

		g.drawString("" + Game.isMusicOn, 0, 0);
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int t) throws SlickException {
		if(sbg.getCurrentStateID() == Game.roam) Game.appgc.setTargetFrameRate(200);

		if (Game.isMusicOn)
			Game.unmuteAllMusic();
		else
			Game.muteAllMusic();
		sleepHandling(gc, sbg, t);
		j++;
		j %= 1500;
		if (speaking) {
		} else {
			checkUnlocking(gc, sbg, t);
			checkEnterNextLevel(gc, sbg, t);
			checkMenu(gc, sbg, t);
			checkPetsFound(gc, sbg, t);
			checkPandaMovement(gc, sbg, t);
			characterMovement(gc, sbg, t);
			checkTalk(gc, sbg, t);
		}
	}
	// end of implemented methods

	// supplementary methods
	private static void checkEnterNextLevel(GameContainer gc, StateBasedGame sbg, int t) throws SlickException {
		// enter next level
		if (map.getTileId((int) x + 10, (int) y + 10, enterStateLayer) != 0) {
			Game.stopAllMusic();
			sbg.enterState(Game.lvlLava);
			Game.metal11Intro.play();
			y += 1;
		}
		if (lastChar != 0) {
			Game.stopAllMusic();
			switch (lastChar) {
			case 1:
				sbg.enterState(Game.lvl01);
				Game.appgc.setDisplayMode(640, 560, false);
				break;
			case 2:
				Game.pollyWolly.loop();
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
	}

	private static void checkExitSpeech() {
		if (input.isKeyPressed(Input.KEY_SPACE) && speaking == true) {
			speaking = false;
		}
	}

	private static void checkMenu(GameContainer gc, StateBasedGame sbg, int t) {
		if (input.isKeyPressed(Input.KEY_ESCAPE)) {
			sbg.enterState(Game.menu);
			Game.pollyWolly.stop();
			Game.menuMusicIntro.play();
		}
	}

	private static void characterMovement(GameContainer gc, StateBasedGame sbg, int t) {
		// penguin walking
		i++;
		i %= 1000;
		if (i >= 0 && i < 250) {
			penguin = Game.penguinWalkLeft;
			penX -= walkI;
		}
		if (i >= 250 && i < 500) {
			penguin = Game.penguinWalkDown;
			penY += walkI;

		}
		if (i >= 500 && i < 750) {
			penguin = Game.penguinWalkRight;
			penX += walkI;

		}
		if (i >= 750 && i < 1000) {
			penguin = Game.penguinWalkUp;
			penY -= walkI;

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

	}

	private static void checkPandaMovement(GameContainer gc, StateBasedGame sbg, int t) {

		// up
		if (input.isKeyDown(Input.KEY_UP)) {
			panda = Game.pandaWalkUp;
			if ((map.getTileId((int) (x + 10f), (int) (y + 10f - walkI), objectLayer) == 0
					|| map.getTileId((int) (x + 10f), (int) (y + 10f - walkI), groundLayer2) != 0)

					&& ((map.getTileId((int) (x + pandaWidth + 10f), (int) (y + 10f - walkI), objectLayer) == 0
							|| map.getTileId((int) (x + pandaWidth + 10f), (int) (y + 10f - walkI), groundLayer2) != 0))

					&& (map.getTileId((int) (x + pandaWidth + 10f), (int) (y + pandaHeight + 10f - walkI),
							objectLayer) == 0
							|| map.getTileId((int) (x + pandaWidth + 10f), (int) (y + pandaHeight + 10f - walkI),
									groundLayer2) != 0)

					&& (map.getTileId((int) (x + 10f), (int) (y + pandaHeight + 10f - walkI), objectLayer) == 0
							|| map.getTileId((int) (x + 10f), (int) (y + pandaHeight + 10f - walkI), groundLayer2) != 0)

					&& (x != penX || y != penY - 1))
				y -= walkI;
			last = 'u';
		}

		// down
		if (input.isKeyDown(Input.KEY_DOWN)) {
			panda = Game.pandaWalkDown;
			if ((map.getTileId((int) (x + 10f), (int) (y + 10f + walkI), objectLayer) == 0
					|| map.getTileId((int) (x + 10f), (int) (y + 10 + walkI), groundLayer2) != 0)

					&& (map.getTileId((int) (x + pandaWidth + 10f), (int) (y + 10f + walkI), objectLayer) == 0
							|| map.getTileId((int) (x + pandaWidth + 10f), (int) (y + 10 + walkI), groundLayer2) != 0)

					&& (map.getTileId((int) (x + pandaWidth + 10f), (int) (y + pandaHeight + 10f + walkI),
							objectLayer) == 0
							|| map.getTileId((int) (x + pandaWidth + 10f), (int) (y + pandaHeight + 10 + walkI),
									groundLayer2) != 0)

					&& (map.getTileId((int) (x + 10f), (int) (y + pandaHeight + 10f + walkI), objectLayer) == 0
							|| map.getTileId((int) (x + 10f), (int) (y + pandaHeight + 10 + walkI), groundLayer2) != 0)

					&& (x != penX || y + 1 != penY))
				y += walkI;
			last = 'd';
		}

		// left
		if (input.isKeyDown(Input.KEY_LEFT)) {
			panda = Game.pandaWalkLeft;
			if ((map.getTileId((int) (x + 10f - walkI), (int) (y + 10f), objectLayer) == 0
					|| map.getTileId((int) (x + 10f - walkI), (int) (y + 10f), groundLayer2) != 0)

					&& (map.getTileId((int) (x + pandaWidth + 10f - walkI), (int) (y + 10f), objectLayer) == 0
							|| map.getTileId((int) (x + pandaWidth + 10f - walkI), (int) (y + 10f), groundLayer2) != 0)

					&& (map.getTileId((int) (x + pandaWidth + 10f - walkI), (int) (y + pandaHeight + 10f),
							objectLayer) == 0
							|| map.getTileId((int) (x + pandaWidth + 10f - walkI), (int) (y + pandaHeight + 10f),
									groundLayer2) != 0)

					&& (map.getTileId((int) (x + 10f - walkI), (int) (y + pandaHeight + 10f), objectLayer) == 0
							|| map.getTileId((int) (x + 10f - walkI), (int) (y + pandaHeight + 10f), groundLayer2) != 0)

					&& (x - 1 != penX || y != penY))
				x -= walkI;
			last = 'l';
		}

		// right
		if (input.isKeyDown(Input.KEY_RIGHT)) {
			panda = Game.pandaWalkRight;
			if ((map.getTileId((int) (x + 10f + walkI), (int) (y + 10f), objectLayer) == 0
					|| map.getTileId((int) (x + 10f + walkI), (int) (y + 10f), groundLayer2) != 0)

					&& (map.getTileId((int) (x + pandaWidth + 10f + walkI), (int) (y + 10f), objectLayer) == 0
							|| map.getTileId((int) (x + pandaWidth + 10f + walkI), (int) (y + 10f), groundLayer2) != 0)

					&& (map.getTileId((int) (x + pandaWidth + 10f + walkI), (int) (y + pandaHeight + 10f),
							objectLayer) == 0
							|| map.getTileId((int) (x + pandaWidth + 10f + walkI), (int) (y + pandaHeight + 10f),
									groundLayer2) != 0)

					&& (map.getTileId((int) (x + 10f + walkI), (int) (y + pandaHeight + 10f), objectLayer) == 0
							|| map.getTileId((int) (x + 10f + walkI), (int) (y + pandaHeight + 10f), groundLayer2) != 0)

					&& !(x + 1 == penX || y == penY))
				x += walkI;
			last = 'r';
		}

		if (!(input.isKeyDown(Input.KEY_UP) || input.isKeyDown(Input.KEY_DOWN) || input.isKeyDown(Input.KEY_LEFT)
				|| input.isKeyDown(Input.KEY_RIGHT))) {
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
	}

	private static void checkPetsFound(GameContainer gc, StateBasedGame sbg, int t) {
		if (Game.pet1Found)
			Game.charLock1 = '6';
		if (Game.pet2Found)
			Game.charLock2 = '9';
		if (Game.pet3Found)
			Game.charLock3 = '6';
		if (Game.pet4Found)
			Game.charLock4 = '9';
	}

	private static void checkUnlocking(GameContainer gc, StateBasedGame sbg, int t) {
		// unlocking
		if (oneHit == 1) {
			say("Wait! Something's happening!!");
			oneHit++;
		}

		if (Game.charLock1 != '_' && Game.charLock2 != '_' && Game.charLock3 != '_' && Game.charLock4 != '_'
				&& oneHit == 3) {
			map = null;
			try {
				map = new TiledMap("res/maps/mapRoamUnlocked.tmx");
			} catch (SlickException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			oneHit++;
		}
		if (oneHit == 2)
			oneHit++;
		// end unlocking

	}

	private static void checkTalk(GameContainer gc, StateBasedGame sbg, int t) {
		// talk
		if (input.isKeyPressed(Input.KEY_SPACE)) {
			if (last == 'r' && ((int) x == 49 && (int) y == 51) || ((int) x == 49 && (int) y + pandaHeight == 51)) {
				say("This is an ancient fountain...Look! There is a four digit lock:\n" + "                        "
						+ Game.charLock1 + "   " + Game.charLock2 + "   " + Game.charLock3 + "   " + Game.charLock4);
				if (Game.charLock1 != '_' && Game.charLock2 != '_' && Game.charLock3 != '_' && Game.charLock4 != '_'
						&& oneHit == 0)
					oneHit = 1;
			}
			if (last == 'd' && ((int) x == 50 && (int) y == 50) || ((int) x + pandaWidth == 50 && (int) y == 50)) {
				say("This is an ancient fountain...Look! There is a four digit lock:\n" + "                        "
						+ Game.charLock1 + "   " + Game.charLock2 + "   " + Game.charLock3 + "   " + Game.charLock4);
				if (Game.charLock1 != '_' && Game.charLock2 != '_' && Game.charLock3 != '_' && Game.charLock4 != '_'
						&& oneHit == 0)
					oneHit = 1;
			}
			if (last == 'l' && ((int) x == 51 && (int) y == 51) || ((int) x == 51 && (int) y + pandaHeight == 51)) {
				say("This is an ancient fountain...Look! There is a four digit lock:\n" + "                        "
						+ Game.charLock1 + "   " + Game.charLock2 + "   " + Game.charLock3 + "   " + Game.charLock4);
				if (Game.charLock1 != '_' && Game.charLock2 != '_' && Game.charLock3 != '_' && Game.charLock4 != '_'
						&& oneHit == 0)
					oneHit = 1;
			}
			if (last == 'u' && ((int) x == 50 && (int) y == 52) || ((int) x + pandaWidth == 50 && (int) y == 52)) {
				say("This is an ancient fountain...Look! There is a four digit lock:\n" + "                        "
						+ Game.charLock1 + "   " + Game.charLock2 + "   " + Game.charLock3 + "   " + Game.charLock4);
				if (Game.charLock1 != '_' && Game.charLock2 != '_' && Game.charLock3 != '_' && Game.charLock4 != '_'
						&& oneHit == 0)
					oneHit = 1;
			}

			// char1
			if (last == 'r' && ((int) x == 19 && (int) y == 5) || ((int) x == 19 && (int) y + pandaHeight == 5)) {
				char1 = Game.char1StillLeft;
				say("Hello there! I lost my wittle pet, Fluffly, yesturday!\n" + "Can you help me find him?");

				lastChar = 1;
				if (Game.pet1Found) {
					say("Thanks for your help!! Here Fluffly...");
					lastChar = 0;
				}

			}
			if (last == 'd' && ((int) x == 20 && (int) y == 4) || ((int) x + pandaWidth == 20 && (int) y == 4)) {
				char1 = Game.char1StillUp;
				say("Hello there! I lost my wittle pet, Fluffly, yesturday!\n" + "Can you help me find him?");
				lastChar = 1;
				if (Game.pet1Found) {
					say("Thanks for your help!! Here Fluffly...");
					lastChar = 0;
				}
			}
			if (last == 'l' && ((int) x == 21 && (int) y == 5) || ((int) x == 21 && (int) y + pandaHeight == 5)) {
				char1 = Game.char1StillRight;
				say("Hello there! I lost my wittle pet, Fluffly, yesturday!\n" + "Can you help me find him?");
				lastChar = 1;
				if (Game.pet1Found) {
					say("Thanks for your help!! Here Fluffly...");
					lastChar = 0;
				}
			}
			if (last == 'u' && ((int) x == 20 && (int) y == 6) || ((int) x + pandaWidth == 20 && (int) y == 6)) {
				char1 = Game.char1StillDown;
				say("Hello there! I lost my wittle pet, Fluffly, yesturday!\n" + "Can you help me find him?");
				lastChar = 1;
				if (Game.pet1Found) {
					say("Thanks for your help!! Here Fluffly...");
					lastChar = 0;
				}
			}
			// char2
			if (last == 'r' && ((int) x == 89 && (int) y == 10) || ((int) x == 89 && (int) y + pandaHeight == 10)) {
				char2 = Game.char2StillLeft;
				say("Hey Mr. Pandaman! My little penguin, Flappy, ran away yesturday!\n"
						+ "Can you find Flappy for me?");
				lastChar = 2;
				if (Game.pet2Found) {
					say("Thanks for your help!! Here Flappy...");
					lastChar = 0;
				}
			}
			if (last == 'd' && ((int) x == 90 && (int) y == 9) || ((int) x + pandaWidth == 90 && (int) y == 9)) {
				char2 = Game.char2StillUp;
				say("Hey Mr. Pandaman! My little penguin, Flappy, ran away yesturday!\n"
						+ "Can you find Flappy for me?");
				lastChar = 2;
				if (Game.pet2Found) {
					say("Thanks for your help!! Here Flappy...");
					lastChar = 0;
				}
			}
			if (last == 'l' && ((int) x == 91 && (int) y == 10) || ((int) x == 91 && (int) y + pandaHeight == 10)) {
				char2 = Game.char2StillRight;
				say("Hey Mr. Pandaman! My little penguin, Flappy, ran away yesturday!\n"
						+ "Can you find Flappy for me?");
				lastChar = 2;
				if (Game.pet2Found) {
					say("Thanks for your help!! Here Flappy...");
					lastChar = 0;
				}
			}
			if (last == 'u' && ((int) x == 90 && (int) y == 11) || ((int) x + pandaWidth == 90 && (int) y == 11)) {
				char2 = Game.char2StillDown;
				say("Hey Mr. Pandaman! My little penguin, Flappy, ran away yesturday!\n"
						+ "Can you find Flappy for me?");
				lastChar = 2;
				if (Game.pet2Found) {
					say("Thanks for your help!! Here Flappy...");
					lastChar = 0;
				}
			}
			// char3
			if (last == 'r' && ((int) x == 9 && (int) y == 90) || ((int) x == 9 && (int) y + pandaHeight == 90)) {
				char3 = Game.char3StillLeft;
				say("Pandaman! Good thing you're here! Yesturday I lost Shelly, my turtle!\n"
						+ "Can you help me find him?");
				lastChar = 3;
				if (Game.pet3Found) {
					say("Thanks for your help!! Here Shelly...");
					lastChar = 0;
				}
			}
			if (last == 'd' && ((int) x == 10 && (int) y == 89) || ((int) x + pandaWidth == 10 && (int) y == 89)) {
				char3 = Game.char3StillUp;
				say("Pandaman! Good thing you're here! Yesturday I lost Shelly, my turtle!\n"
						+ "Can you help me find him?");
				lastChar = 3;
				if (Game.pet3Found) {
					say("Thanks for your help!! Here Shelly...");
					lastChar = 0;
				}
			}
			if (last == 'l' && ((int) x == 11 && (int) y == 90) || ((int) x == 11 && (int) y + pandaHeight == 90)) {
				char3 = Game.char3StillRight;
				say("Pandaman! Good thing you're here! Yesturday I lost Shelly, my turtle!\n"
						+ "Can you help me find him?");
				lastChar = 3;
				if (Game.pet3Found) {
					say("Thanks for your help!! Here Shelly...");
					lastChar = 0;
				}
			}
			if (last == 'u' && ((int) x == 10 && (int) y == 91) || ((int) x + pandaWidth == 10 && (int) y == 91)) {
				char3 = Game.char3StillDown;
				say("Pandaman! Good thing you're here! Yesturday I lost Shelly, my turtle!\n"
						+ "Can you help me find him?");
				lastChar = 3;
				if (Game.pet3Found) {
					say("Thanks for your help!! Here Shelly...");
					lastChar = 0;
				}
			}
			// char4
			if (last == 'r' && ((int) x == 89 && (int) y == 90) || ((int) x == 89 && (int) y + pandaHeight == 90)) {
				char4 = Game.char4StillLeft;
				say("I can't find Woof-Woof! He ranaway yesturday!\n" + "Can you find him for me?");
				lastChar = 4;
				if (Game.pet4Found) {
					say("Thanks for your help!! Here Woof-Woof...");
					lastChar = 0;
				}
			}
			if (last == 'd' && ((int) x == 90 && (int) y == 89)
					|| ((int) x + pandaWidth == 90 + pandaWidth && (int) y == 89)) {
				char4 = Game.char4StillUp;
				say("I can't find Woof-Woof! He ranaway yesturday!\n" + "Can you find him for me?");
				lastChar = 4;
				if (Game.pet4Found) {
					say("Thanks for your help!! Here Woof-Woof...");
					lastChar = 0;
				}
			}
			if (last == 'l' && ((int) x == 91 && (int) y == 90) || ((int) x == 91 && (int) y + pandaHeight == 90)) {
				char4 = Game.char4StillRight;
				say("I can't find Woof-Woof! He ranaway yesturday!\n" + "Can you find him for me?");
				lastChar = 4;
				if (Game.pet4Found) {
					say("Thanks for your help!! Here Woof-Woof...");
					lastChar = 0;
				}
			}
			if (last == 'u' && ((int) x == 90 && (int) y == 91) || ((int) x + pandaWidth == 90 && (int) y == 91)) {
				char4 = Game.char4StillDown;
				say("I can't find Woof-Woof! He ranaway yesturday!\n" + "Can you find him for me?");
				lastChar = 4;
				if (Game.pet4Found) {
					say("Thanks for your help!! Here Woof-Woof...");
					lastChar = 0;
				}
			}
		}
	}

	@SuppressWarnings("unused")
	private static void resetState() {
		x = 10;
		y = 10;
		quit = false;
	}

	private static void renderCharacters(Graphics g) {

		char1.draw((320 - (x - char1X) * tileWidth), (320 - (y - char1Y) * tileHeight));
		char2.draw((320 - (x - char2X) * tileWidth), (320 - (y - char2Y) * tileHeight));
		char3.draw((320 - (x - char3X) * tileWidth), (320 - (y - char3Y) * tileHeight));
		char4.draw((320 - (x - char4X) * tileWidth), (320 - ((y - char4Y) * tileHeight)));
	}

	private static void renderConfetti(Graphics g) {
		Game.confetti.draw(-20, -150 + j);
		Game.confetti.draw(-60, -300 + j);
		Game.confetti.draw(-100, -450 + j);
		Game.confetti.draw(-140, -600 + j);
	}

	private static void renderGroundLayers(Graphics g) {
		g.translate((-x % 1) * tileWidth, (-y % 1) * tileHeight);
		map.render(0, 0, (int) x, (int) y, 21, 21, backgroundLayer, true);
		map.render(0, 0, (int) x, (int) y, 21, 21, graphLayer, true);
		map.render(0, 0, (int) x, (int) y, 21, 21, groundLayer1, true);
		map.render(0, 0, (int) x, (int) y, 21, 21, objectLayer, true);
		map.render(0, 0, (int) x, (int) y, 21, 21, groundLayer2, true);
		map.render(0, 0, (int) x, (int) y, 21, 21, enterStateLayer, true);
		g.translate((x % 1) * tileWidth, (y % 1) * tileHeight);
	}

	private static void renderOverHead(Graphics g) {
		g.translate((-x % 1) * tileWidth, (-y % 1) * tileHeight);
		map.render(0, 0, (int) x, (int) y, 21, 21, overheadLayer1, true);
		map.render(0, 0, (int) x, (int) y, 21, 21, overheadLayer2, true);
		map.render(0, 0, (int) x, (int) y, 21, 21, overheadLayer3, true);
		map.render(0, 0, (int) x, (int) y, 21, 21, overheadLayer4, true);
		g.translate((x % 1) * tileWidth, (y % 1) * tileHeight);
	}

	private static void renderPets(Graphics g) {
		if (Game.pet1Found) {
		}
		if (Game.pet2Found)
			penguin.draw((320 - (x - penX) * 32), (320 - (y - penY) * 32));
		if (Game.pet3Found) {
		}
		if (Game.pet4Found) {
		}
	}

	private static void renderSpeech(Graphics g) {
		g.setColor(Color.white);
		g.fillRect(20, 500, 600, 100);
		g.setColor(Color.black);
		g.drawString(strSpeak, 30, 510);
		g.setColor(Color.red);

	}

	private static void say(String str) {
		strSpeak = str;
		speaking = true;
	}

	private static void sleepHandling(GameContainer gc, StateBasedGame sbg, int t) {
		if (sbg.getCurrentStateID() == Game.roam) {
			oneHit2++;
			if (oneHit2 == 1)
				Game.pollyWolly.loop();
		} else {
			oneHit2 = 0;
			Game.pollyWolly.stop();
		}
	}

}
