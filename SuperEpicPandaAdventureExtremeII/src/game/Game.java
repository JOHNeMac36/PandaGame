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
	
	// attributes
	public static final int splash = -1, menu = 0, roam = -99, lvl01 = 1, lvl02 = 2, lvl03 = 3,
			lvlBoss = 100, lvl04 = 4;
	public static Animation pandaStillDown, pandaStillUp, pandaStillLeft, pandaStillRight;
	public static Animation pandaWalkUp, pandaWalkDown, pandaWalkLeft, pandaWalkRight;
	public static Animation penguinStillDown, penguinStillUp, penguinStillLeft, penguinStillRight;
	public static Animation penguinWalkUp, penguinWalkDown, penguinWalkLeft, penguinWalkRight;
	public static Animation char1StillDown, char1StillUp, char1StillLeft, char1StillRight;
	public static Animation char2StillDown, char2StillUp, char2StillLeft, char2StillRight;
	public static Animation char3StillDown, char3StillUp, char3StillLeft, char3StillRight;
	public static Animation char4StillDown, char4StillUp, char4StillLeft, char4StillRight;
	public static GameContainer gc;
	public static Image playNow, exitGame, menuScene, title, confetti;
	public static Music menuMusicIntro, menuMusicLoop, pollyWolly;
	public static Sound pandaPunch, sadPanda, itsPanda, pandaIcecream;
	public static String gamename = "Super Epic Panda Adventure Extreme 2!";
	
	public static AppGameContainer appgc;
	// end of attributes
	
	public static void main(String[] args) {
		
		try {
			appgc = new AppGameContainer(new Game(gamename));
			appgc.setDisplayMode(640, 640, false);
			appgc.setShowFPS(true);
			appgc.setTargetFrameRate(340);
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
		this.getState(menu).init(gc, this);
		this.getState(roam).init(gc, this);
		this.getState(lvl01).init(gc, this);
		this.getState(lvl02).init(gc, this);
		this.getState(lvl03).init(gc, this);
		this.getState(lvl04).init(gc, this);
		this.getState(lvlBoss).init(gc, this);
		this.enterState(lvl01);
		initAnimations();
		initSounds();
		initImages();
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
		Image wU, wD, wL, wR, sU, sD, sL, sR;
		
		wU = new Image("res/sprites/pandaWalkUp.png");
		wD = new Image("res/sprites/pandaWalkDown.png");
		wL = new Image("res/sprites/pandaWalkLeft.png");
		wR = new Image("res/sprites/pandaWalkRight.png");
		sU = new Image("res/sprites/pandaStillUp.png");
		sD = new Image("res/sprites/pandaStillDown.png");
		sL = new Image("res/sprites/pandaStillLeft.png");
		sR = new Image("res/sprites/pandaStillRight.png");
		
		for (int X = 0; X < 128; X += 32) {
			pandaWalkUp.addFrame(wU.getSubImage(X, 0, 32, 32), 150);
			pandaWalkDown.addFrame(wD.getSubImage(X, 0, 32, 32), 150);
			pandaWalkLeft.addFrame(wL.getSubImage(X, 0, 32, 32), 150);
			pandaWalkRight.addFrame(wR.getSubImage(X, 0, 32, 32), 150);
		}
		
		pandaStillUp.addFrame(sU, 100);
		pandaStillDown.addFrame(sD, 100);
		pandaStillLeft.addFrame(sL, 100);
		pandaStillRight.addFrame(sR, 100);
		
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
			}
		}
	}
	
	public void initSounds() throws SlickException {
		pandaPunch = new Sound("res/oggs/PandaPunch.ogg");
		sadPanda = new Sound("res/oggs/Sad Panda.ogg");
		itsPanda = new Sound("res/oggs/itsPanda.ogg");
		pandaIcecream = new Sound("res/oggs/pandaIcecream.ogg");
		pollyWolly = new Music("res/oggs/music.ogg");
	}
	
	private static void initImages() throws SlickException {
		menuScene = new Image("res/pngs/menuScene.png");
		playNow = new Image("res/pngs/playNow.png");
		exitGame = new Image("res/pngs/exitGame.png");
		title = new Image("res/pngs/pandaTitle.png");
		confetti = new Image("res/pngs/confetti.png");
	}
	
}
