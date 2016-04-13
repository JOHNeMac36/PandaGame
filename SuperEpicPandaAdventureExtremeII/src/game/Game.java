package game;

import org.newdawn.slick.Animation;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Image;
import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;
import org.newdawn.slick.state.StateBasedGame;

public class Game extends StateBasedGame {
	
	int ys = 1;
	// attributes
	public static final int splash = -1, menu = 0, roam = -99, lvl01 = 1, lvl02 = 2, lvl03 = 3, lvlBoss = 100, lvl04 = 4;
	public static Animation pandaStillDown, pandaStillUp, pandaStillLeft, pandaStillRight;
	public static Animation pandaWalkUp, pandaWalkDown, pandaWalkLeft, pandaWalkRight;
	public static Animation penguinStillDown, penguinStillUp, penguinStillLeft, penguinStillRight;
	public static Animation penguinWalkUp, penguinWalkDown, penguinWalkLeft, penguinWalkRight;
	public static Animation char1StillDown, char1StillUp, char1StillLeft, char1StillRight;
	public static Animation char2StillDown, char2StillUp, char2StillLeft, char2StillRight;
	public static Animation char3StillDown, char3StillUp, char3StillLeft, char3StillRight;
	public static Animation char4StillDown, char4StillUp, char4StillLeft, char4StillRight;
	public static Animation mushroom, mushroomDead;
	public static Animation marioPandaStillR, marioPandaWalkRight, marioPandaJumpR, marioPandaDown;
	public static Animation marioPandaStillL, marioPandaWalkLeft, marioPandaJumpL;
	public static Animation marioPandaBigRStill, marioPandaBigWalkRight, marioPandaBigJumpR, marioPandaBigDown;
	public static Animation marioPandaBigLStill, marioPandaBigWalkLeft, marioPandaBigJumpL;
	public static Animation marioPandaDownFallR, marioPandaDownFallL, marioPandaBigDownFallR, marioPandaBigDownFallL;
	
	public static boolean pet1Found, pet2Found, pet3Found, pet4Found;
	public static char charLock1, charLock2, charLock3, charLock4;
	
	public static GameContainer gc;
	public static Image playNow, exitGame, menuScene, title, confetti, marioTitle, marioCursor;
	public static Music menuMusicIntro, menuMusicLoop, pollyWolly;
	public static Sound pandaPunch, sadPanda, itsPanda, pandaIcecream;
	public static Music marioTheme, marioUnderworld, marioCastle, marioStarman, marioLvlComplete, marioCastleComplete, marioDead,
			marioGameOver, marioHurryUp;
	public static String gamename = "Super Epic Panda Adventure Extreme 2!";
	
	public static final int len = 30, wid = 28, xSpace = wid + 1, ySpace = len + 1;
	
	public static AppGameContainer appgc;
	// end of attributes
	
	public static void main(String[] args) {
		
		try {
			appgc = new AppGameContainer(new Game(gamename));
			appgc.setDisplayMode(640, 640, false);
			appgc.setShowFPS(true);
			appgc.setTargetFrameRate(350);
			appgc.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public Game(String gamename) throws SlickException {
		super(gamename);
		this.addState(new Splash(splash));
		this.addState(new MainMenu(menu));
		this.addState(new Roam(roam));
		this.addState(new Level01(lvl01));
		this.addState(new Level02(lvl02));
		this.addState(new Level03(lvl03));
		this.addState(new Level04(lvl04));
		this.addState(new LevelBoss(lvlBoss));
		
	}
	
	@Override
	public void initStatesList(GameContainer gc) throws SlickException {
		this.getState(splash).init(gc, this);
		
		this.getState(roam).init(gc, this);
		
		this.getState(lvl01).init(gc, this);
		this.getState(lvl02).init(gc, this);
		this.getState(lvl03).init(gc, this);
		this.getState(lvl04).init(gc, this);
		this.getState(lvlBoss).init(gc, this);
		this.getState(menu).init(gc, this);
		this.enterState(lvl01);
		initAnimations();
		initSounds();
		initImages();
		
		pet1Found = false;
		pet2Found = false;
		pet3Found = false;
		pet4Found = false;
		
		charLock1 = '_';
		charLock2 = '_';
		charLock3 = '_';
		charLock4 = '_';
		
	}
	
	private void initAnimations() throws SlickException {
		pandaWalkUp = new Animation();
		pandaWalkDown = new Animation();
		pandaWalkLeft = new Animation();
		pandaWalkRight = new Animation();
		pandaStillUp = new Animation();
		pandaStillDown = new Animation();
		pandaStillLeft = new Animation();
		pandaStillRight = new Animation();
		
		penguinWalkUp = new Animation();
		penguinWalkDown = new Animation();
		penguinWalkLeft = new Animation();
		penguinWalkRight = new Animation();
		penguinStillUp = new Animation();
		penguinStillDown = new Animation();
		penguinStillLeft = new Animation();
		penguinStillRight = new Animation();
		
		char1StillUp = new Animation();
		char1StillDown = new Animation();
		char1StillLeft = new Animation();
		char1StillRight = new Animation();
		
		char2StillUp = new Animation();
		char2StillDown = new Animation();
		char2StillLeft = new Animation();
		char2StillRight = new Animation();
		
		char3StillUp = new Animation();
		char3StillDown = new Animation();
		char3StillLeft = new Animation();
		char3StillRight = new Animation();
		
		char4StillUp = new Animation();
		char4StillDown = new Animation();
		char4StillLeft = new Animation();
		char4StillRight = new Animation();
		
		// panda
		
		Image panda, sU, sD, sL, sR;
		panda = new Image("res/sprites/panda.png");
		sD = panda.getSubImage(xSpace, 0 * ySpace, wid, len);
		sR = panda.getSubImage(xSpace, 1 * ySpace, wid, len);
		sL = panda.getSubImage(xSpace, 1 * ySpace, wid, len).getFlippedCopy(true, false);
		sU = panda.getSubImage(xSpace, 2 * ySpace, wid, len);
		
		for (int x = 0; x < 3; x++) {
			pandaWalkDown.addFrame(panda.getSubImage(x * xSpace, 0 * ySpace, wid, len), 150);
			pandaWalkRight.addFrame(panda.getSubImage(x * xSpace, 1 * ySpace, wid, len), 150);
			pandaWalkLeft.addFrame(panda.getSubImage(x * xSpace, 1 * ySpace, wid, len).getFlippedCopy(true, false), 150);
			pandaWalkUp.addFrame(panda.getSubImage(x * xSpace, 2 * ySpace, wid, len), 150);
		}
		
		pandaWalkUp.addFrame(sU, 150);
		pandaWalkDown.addFrame(sD, 150);
		pandaWalkLeft.addFrame(sL, 150);
		pandaWalkRight.addFrame(sR, 150);
		
		pandaStillUp.addFrame(sU, 150);
		pandaStillDown.addFrame(sD, 150);
		pandaStillLeft.addFrame(sL, 150);
		pandaStillRight.addFrame(sR, 150);
		
		// penguin
		Image pen = new Image("res/sprites/Penguin.png");
		penguinWalkDown.addFrame(pen.getSubImage(32, 0, 32, 32), 150);
		penguinStillDown.addFrame(pen.getSubImage(32, 0, 32, 32), 150);
		penguinWalkLeft.addFrame(pen.getSubImage(32, 32, 32, 32), 150);
		penguinStillLeft.addFrame(pen.getSubImage(32, 32, 32, 32), 150);
		penguinWalkRight.addFrame(pen.getSubImage(32, 64, 32, 32), 150);
		penguinStillRight.addFrame(pen.getSubImage(32, 64, 32, 32), 150);
		penguinWalkUp.addFrame(pen.getSubImage(32, 96, 32, 32), 150);
		penguinStillUp.addFrame(pen.getSubImage(32, 96, 32, 32), 150);
		
		for (int X = 0; X < 96; X += 32) {
			for (int Y = 0; Y < 128; Y += 32) {
				if (Y == 0) penguinWalkDown.addFrame(pen.getSubImage(X, Y, 32, 32), 150);
				
				if (Y == 32) penguinWalkLeft.addFrame(pen.getSubImage(X, Y, 32, 32), 150);
				
				if (Y == 64) penguinWalkRight.addFrame(pen.getSubImage(X, Y, 32, 32), 150);
				
				if (Y == 96) penguinWalkUp.addFrame(pen.getSubImage(X, Y, 32, 32), 150);
				
			}
		}
		
		// 4 characters
		Image chars = new Image("res/sprites/chars.png");
		for (int X = 0; X < 128; X += 32) {
			for (int Y = 0; Y < 128; Y += 32) {
				switch (X) {
					case 0:
						switch (Y) {
							case 0:
								char1StillDown.addFrame(chars.getSubImage(X, Y, 32, 32), 150);
								break;
							case 32:
								char1StillLeft.addFrame(chars.getSubImage(X, Y, 32, 32), 150);
								break;
							case 64:
								char1StillRight.addFrame(chars.getSubImage(X, Y, 32, 32), 150);
								break;
							case 96:
								char1StillUp.addFrame(chars.getSubImage(X, Y, 32, 32), 150);
								break;
						}
						break;
						
					case 32:
						switch (Y) {
							case 0:
								char2StillDown.addFrame(chars.getSubImage(X, Y, 32, 32), 150);
								break;
							case 32:
								char2StillLeft.addFrame(chars.getSubImage(X, Y, 32, 32), 150);
								break;
							case 64:
								char2StillRight.addFrame(chars.getSubImage(X, Y, 32, 32), 150);
								break;
							case 96:
								char2StillUp.addFrame(chars.getSubImage(X, Y, 32, 32), 150);
								break;
						}
						break;
						
					case 64:
						switch (Y) {
							case 0:
								char3StillDown.addFrame(chars.getSubImage(X, Y, 32, 32), 150);
								break;
							case 32:
								char3StillLeft.addFrame(chars.getSubImage(X, Y, 32, 32), 150);
								break;
							case 64:
								char3StillRight.addFrame(chars.getSubImage(X, Y, 32, 32), 150);
								break;
							case 96:
								char3StillUp.addFrame(chars.getSubImage(X, Y, 32, 32), 150);
								break;
						}
						break;
						
					case 96:
						switch (Y) {
							case 0:
								char4StillDown.addFrame(chars.getSubImage(X, Y, 32, 32), 150);
								break;
							case 32:
								char4StillLeft.addFrame(chars.getSubImage(X, Y, 32, 32), 150);
								break;
							case 64:
								char4StillRight.addFrame(chars.getSubImage(X, Y, 32, 32), 150);
								break;
							case 96:
								char4StillUp.addFrame(chars.getSubImage(X, Y, 32, 32), 150);
								break;
						}
						break;
				}
				
				// marioPanda
				Image marioPanda = new Image("res/sprites/marioPanda.png");
				
				marioPandaStillR = new Animation();
				marioPandaWalkRight = new Animation();
				marioPandaJumpR = new Animation();
				marioPandaDown = new Animation();
				marioPandaStillL = new Animation();
				marioPandaWalkLeft = new Animation();
				marioPandaJumpL = new Animation();
				marioPandaBigRStill = new Animation();
				marioPandaBigWalkRight = new Animation();
				marioPandaBigJumpR = new Animation();
				marioPandaBigDown = new Animation();
				marioPandaBigLStill = new Animation();
				marioPandaBigWalkLeft = new Animation();
				marioPandaBigJumpL = new Animation();
				
				marioPandaDownFallR = new Animation();
				marioPandaDownFallL = new Animation();
				marioPandaBigDownFallR = new Animation();
				marioPandaBigDownFallL = new Animation();
				
				marioPandaStillR.addFrame(marioPanda.getSubImage(0, 32 + ys, 16, 16), 150);
				marioPandaStillL.addFrame(marioPanda.getSubImage(0, 32 + ys, 16, 16).getFlippedCopy(true, false), 150);
				
				marioPandaWalkRight.addFrame(marioPanda.getSubImage(3 * 16, 32 + ys, 16, 16), 150);
				marioPandaWalkLeft.addFrame(marioPanda.getSubImage(3 * 16, 32 + ys, 16, 16).getFlippedCopy(true, false), 150);
				marioPandaBigWalkRight.addFrame(marioPanda.getSubImage(3 * 16, 0, 16, 32), 150);
				marioPandaBigWalkLeft.addFrame(marioPanda.getSubImage(3 * 16, 0, 16, 32).getFlippedCopy(true, false), 150);
				
				for (int x = 1; x < 4; x++) {
					marioPandaWalkRight.addFrame(marioPanda.getSubImage(x * 16, 2 * 16 + ys, 16, 16), 150);
					marioPandaBigWalkRight.addFrame(marioPanda.getSubImage(x * 16, 0, 16, 32), 150);
					marioPandaWalkLeft.addFrame(marioPanda.getSubImage(x * 16, 2 * 16 + ys, 16, 16).getFlippedCopy(true, false), 150);
					marioPandaBigWalkLeft.addFrame(marioPanda.getSubImage(x * 16, 0, 16, 32).getFlippedCopy(true, false), 150);
				}
				
				marioPandaJumpR.addFrame(marioPanda.copy().getSubImage(5 * 16, 2 * 16 + ys, 16, 16), 150);
				marioPandaJumpL.addFrame(marioPanda.copy().getSubImage(5 * 16, 2 * 16 + ys, 16, 16).getFlippedCopy(true, false), 150);
				marioPandaBigJumpR.addFrame(marioPanda.copy().getSubImage(5 * 16, 0, 16, 32), 150);
				marioPandaBigJumpL.addFrame(marioPanda.copy().getSubImage(5 * 16, 0, 16, 32).getFlippedCopy(true, false), 150);
				
				marioPandaDown.addFrame(marioPanda.getSubImage(96, 32 + ys, 16, 16), 150);
				marioPandaBigDown.addFrame(marioPanda.getSubImage(96, 0, 16, 32), 150);
				
				marioPandaDownFallR.addFrame(marioPanda.getSubImage(128, 32 + ys, 16, 16), 150);
				marioPandaDownFallL.addFrame(marioPanda.getSubImage(128, 32 + ys, 16, 16).getFlippedCopy(true, false), 150);
				marioPandaBigDownFallR.addFrame(marioPanda.getSubImage(128, 0, 16, 32), 150);
				marioPandaBigDownFallL.addFrame(marioPanda.getSubImage(128, 0, 16, 32).getFlippedCopy(true, false), 150);
				
				// mushroom enemy
				Image mush = new Image("res/sprites/mushroom.png");
				mushroom = new Animation();
				mushroom.addFrame(mush.getSubImage(0, 0, 16, 16), 150);
				mushroom.addFrame(mush.getSubImage(16, 0, 16, 16), 150);
				mushroomDead = new Animation();
				mushroomDead.addFrame(mush.getSubImage(32, 32, 16, 16), 150);
			}
		}
	}
	
	public void initSounds() throws SlickException {
		pandaPunch = new Sound("res/oggs/PandaPunch.ogg");
		sadPanda = new Sound("res/oggs/Sad Panda.ogg");
		itsPanda = new Sound("res/oggs/itsPanda.ogg");
		pandaIcecream = new Sound("res/oggs/pandaIcecream.ogg");
		pollyWolly = new Music("res/oggs/music.ogg");
		
		marioTheme = new Music("res/oggs/mario01-ThemeSong.ogg");
		marioUnderworld = new Music("res/oggs/mario02-underworld.ogg");
		marioCastle = new Music("res/oggs/mario04-castle.ogg");
		marioStarman = new Music("res/oggs/mario05-starman.ogg");
		marioLvlComplete = new Music("res/oggs/mario06-level-complete.ogg");
		marioCastleComplete = new Music("res/oggs/mario07-castle-complete.ogg");
		marioDead = new Music("res/oggs/mario08-you-re-dead.ogg");
		marioGameOver = new Music("res/oggs/mario09-game-over.ogg");
		marioHurryUp = new Music("res/oggs/mario14-hurry-underground-.ogg");
	}
	
	private static void initImages() throws SlickException {
		menuScene = new Image("res/pngs/menuScene.png");
		playNow = new Image("res/pngs/playNow.png");
		exitGame = new Image("res/pngs/exitGame.png");
		title = new Image("res/pngs/pandaTitle.png");
		confetti = new Image("res/pngs/confetti.png");
		marioTitle = new Image("res/pngs/Level 01 Menu.png");
		marioCursor = new Image("res/pngs/Level01MenuCursor.png");
	}
	
}