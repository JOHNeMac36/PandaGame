package game;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.newdawn.slick.Animation;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Image;
import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;
import org.newdawn.slick.state.StateBasedGame;

public class Game extends StateBasedGame {
	
	public static int song;
	// attributes
	public static final int splash = -1, menu = 0, roam = -99, lvl01 = 1, lvl02 = 2, lvl03 = 3, lvlBoss = 100, lvl04 = 4;
	public static int currentState;
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
	public static Animation powerUp;
	public static Animation qBlockStill, qBlockHit, qBlockHitDown, qBlockDeadStill, qBlockDeadHit, qBlockDeadHitDown, bBlockStill,
			bBlockHit, bBlockBreak, bBlockHitDown;
			
	public static boolean pet1Found, pet2Found, pet3Found, pet4Found;
	public static char charLock1, charLock2, charLock3, charLock4;
	public static boolean isMusicOn;
	public static GameContainer gc;
	public static Image playNow, exitGame, menuScene, title, musicOn, musicOff, confetti, marioTitle, marioCursor;
	public static Music menuMusicIntro, menuMusicLoop, pollyWolly;
	public static Music marioTheme, marioUnderworld, marioCastle, marioStarman, marioLvlComplete, marioCastleComplete, marioDead,
			marioGameOver, marioHurryUp;
	public static Sound pandaPunch, sadPanda, itsPanda, pandaIcecream, marioBump, marioCoin, marioFireball, marioJump, marioKick, mario1Up,
			marioPause, marioPowerUp, marioStageClear, marioFlag;
	public static Music metal01Intro, metal01Loop, metal02Intro, metal02Loop, metal03Intro, metal03Loop, metal04Intro, metal04Loop,
			metal05Intro, metal05Loop, metal06Intro, metal06Loop, metal07Intro, metal07Loop, metal08Intro, metal08Loop, metal09Intro,
			metal09Loop, metal10Intro, metal10Loop, metal11Intro, metal11Loop;
	public static String gamename = "Super Epic Panda Adventure Extreme 2!";
	
	public static final int len = 30, wid = 28, xSpace = wid + 1, ySpace = len + 1;
	
	public static AppGameContainer appgc;
	// end of attributes
	public static Animation pwrCoin, pwrFlower, pwrMush, pwrStar;
	
	public static void main(String[] args) {
		
		try {
			appgc = new AppGameContainer(new Game(gamename));
			appgc.setDisplayMode(640, 640, false);
			appgc.setShowFPS(true);
			appgc.setTargetFrameRate(350);
			appgc.setUpdateOnlyWhenVisible(false);
			appgc.setSmoothDeltas(false);
			appgc.start();
			
		} catch (SlickException e) {
		}
	}
	
	public Game(String gamename) {
		super(gamename);
		this.addState(new Splash(splash));
		this.addState(new MainMenu(menu));
		this.addState(new Roam(roam));
		this.addState(new Level01(lvl01));
		this.addState(new Level02(lvl02));
		this.addState(new Level03(lvl03));
		this.addState(new Level04(lvl04));
		this.addState(new LevelBoss(lvl01));
		
	}
	
	@Override
	public void initStatesList(GameContainer gc) {
		initAnimations();
		initSounds();
		initImages();
		isMusicOn = true;
		
		pet1Found = false;
		pet2Found = false;
		pet3Found = false;
		pet4Found = false;
		
		charLock1 = '_';
		charLock2 = '_';
		charLock3 = '_';
		charLock4 = '_';
		
		try {
			this.getState(splash).init(gc, this);
			this.getState(roam).init(gc, this);
			this.getState(lvl01).init(gc, this);
			this.getState(lvl02).init(gc, this);
			this.getState(lvl03).init(gc, this);
			this.getState(lvl04).init(gc, this);
			this.getState(lvlBoss).init(gc, this);
			this.getState(menu).init(gc, this);
			this.enterState(menu);
		} catch (SlickException e) {
		}
		
	}
	
	public void initSounds() {
		try {
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
			
			marioBump = new Sound("res/oggs/marioBump.ogg");
			marioCoin = new Sound("res/oggs/marioCoin.ogg");
			marioFireball = new Sound("res/oggs/marioFireball.ogg");
			marioJump = new Sound("res/oggs/marioJump.ogg");
			marioKick = new Sound("res/oggs/marioKick.ogg");
			mario1Up = new Sound("res/oggs/marioNewLife.ogg");
			marioPause = new Sound("res/oggs/marioPause.ogg");
			marioPowerUp = new Sound("res/oggs/marioPowerUp.ogg");
			marioStageClear = new Sound("res/oggs/marioStageClear.ogg");
			marioFlag = new Sound("res/oggs/marioFlag.ogg");
			
			metal01Intro = new Music("res/oggs/metal01Intro.ogg");
			metal01Loop = new Music("res/oggs/metal01Loop.ogg");
			metal02Intro = new Music("res/oggs/metal02Intro.ogg");
			metal02Loop = new Music("res/oggs/metal02Loop.ogg");
			metal03Intro = new Music("res/oggs/metal03Intro.ogg");
			metal03Loop = new Music("res/oggs/metal03Loop.ogg");
			metal04Intro = new Music("res/oggs/metal04Intro.ogg");
			metal04Loop = new Music("res/oggs/metal04Loop.ogg");
			metal05Intro = new Music("res/oggs/metal05Intro.ogg");
			metal05Loop = new Music("res/oggs/metal05Loop.ogg");
			metal06Intro = new Music("res/oggs/metal06Intro.ogg");
			metal06Loop = new Music("res/oggs/metal06Loop.ogg");
			metal07Intro = new Music("res/oggs/metal07Intro.ogg");
			metal07Loop = new Music("res/oggs/metal07Loop.ogg");
			metal08Intro = new Music("res/oggs/metal08Intro.ogg");
			metal08Loop = new Music("res/oggs/metal08Loop.ogg");
			metal09Intro = new Music("res/oggs/metal09Intro.ogg");
			metal09Loop = new Music("res/oggs/metal09Loop.ogg");
			metal10Intro = new Music("res/oggs/metal10Intro.ogg");
			metal10Loop = new Music("res/oggs/metal10Loop.ogg");
			metal11Intro = new Music("res/oggs/metal11Intro.ogg");
			metal11Loop = new Music("res/oggs/metal11Loop.ogg");
			
			DateFormat df = new SimpleDateFormat("dd");
			Date date = new Date();
			song = Integer.parseInt(df.format(date));
			switch (song % 11) {
				
				case 1:
					Game.menuMusicIntro = new Music("res/oggs/Metal01Intro.ogg");
					Game.menuMusicLoop = new Music("res/oggs/Metal01Loop.ogg");
					break;
				case 2:
					Game.menuMusicIntro = new Music("res/oggs/Metal02Intro.ogg");
					Game.menuMusicLoop = new Music("res/oggs/Metal02Loop.ogg");
					break;
				case 3:
					Game.menuMusicIntro = new Music("res/oggs/Metal03Intro.ogg");
					Game.menuMusicLoop = new Music("res/oggs/Metal03Loop.ogg");
					break;
				case 4:
					Game.menuMusicIntro = new Music("res/oggs/Metal04Intro.ogg");
					Game.menuMusicLoop = new Music("res/oggs/Metal04Loop.ogg");
					break;
				case 5:
					Game.menuMusicIntro = new Music("res/oggs/Metal05Intro.ogg");
					Game.menuMusicLoop = new Music("res/oggs/Metal05Loop.ogg");
					break;
				case 6:
					Game.menuMusicIntro = new Music("res/oggs/Metal06Intro.ogg");
					Game.menuMusicLoop = new Music("res/oggs/Metal06Loop.ogg");
					break;
				case 7:
					Game.menuMusicIntro = new Music("res/oggs/Metal07Intro.ogg");
					Game.menuMusicLoop = new Music("res/oggs/Metal07Loop.ogg");
					break;
				case 8:
					Game.menuMusicIntro = new Music("res/oggs/Metal08Intro.ogg");
					Game.menuMusicLoop = new Music("res/oggs/Metal08Loop.ogg");
					break;
				case 9:
					Game.menuMusicIntro = new Music("res/oggs/Metal09Intro.ogg");
					Game.menuMusicLoop = new Music("res/oggs/Metal09Loop.ogg");
					break;
				case 10:
					Game.menuMusicIntro = new Music("res/oggs/Metal10Intro.ogg");
					Game.menuMusicLoop = new Music("res/oggs/Metal10Loop.ogg");
					break;
				case 11:
					Game.menuMusicIntro = new Music("res/oggs/Metal11Intro.ogg");
					Game.menuMusicLoop = new Music("res/oggs/Metal11Loop.ogg");
					break;
				default:
					Game.menuMusicIntro = new Music("res/oggs/Metal01Intro.ogg");
					Game.menuMusicLoop = new Music("res/oggs/Metal01Loop.ogg");
			}
		} catch (SlickException e) {
		}
	}
	
	private static void initImages() {
		try {
			menuScene = new Image("res/pngs/menuScene.png");
			playNow = new Image("res/pngs/playNow.png");
			exitGame = new Image("res/pngs/exitGame.png");
			title = new Image("res/pngs/pandaTitle.png");
			confetti = new Image("res/pngs/confetti.png");
			marioTitle = new Image("res/pngs/Level 01 Menu.png");
			marioCursor = new Image("res/pngs/Level01MenuCursor.png");
			musicOn = new Image("res/pngs/music_on_off.png").getSubImage(0, 0, 64, 64);
			musicOff = new Image("res/pngs/music_on_off.png").getSubImage(65, 0, 64, 64);
		} catch (SlickException e) {
		
		}
	}
	
	public static void stopAllMusic() {
		menuMusicIntro.stop();
		menuMusicLoop.stop();
		pollyWolly.stop();
		marioTheme.stop();
		marioUnderworld.stop();
		marioCastle.stop();
		marioStarman.stop();
		marioLvlComplete.stop();
		marioCastleComplete.stop();
		marioDead.stop();
		marioGameOver.stop();
		marioHurryUp.stop();
		menuMusicIntro.stop();
		menuMusicLoop.stop();
		pollyWolly.stop();
		metal01Intro.stop();
		metal01Loop.stop();
		metal02Intro.stop();
		metal02Loop.stop();
		metal03Intro.stop();
		metal03Loop.stop();
		metal04Intro.stop();
		metal04Loop.stop();
		metal05Intro.stop();
		metal05Loop.stop();
		metal06Intro.stop();
		metal06Loop.stop();
		metal07Intro.stop();
		metal07Loop.stop();
		metal08Intro.stop();
		metal08Loop.stop();
		metal09Intro.stop();
		metal09Loop.stop();
		metal10Intro.stop();
		metal10Loop.stop();
		metal11Intro.stop();
		metal11Loop.stop();
		
	}
	
	public static void muteAllMusic() {
		menuMusicIntro.setVolume(0);
		menuMusicLoop.setVolume(0);
		pollyWolly.setVolume(0);
		marioTheme.setVolume(0);
		marioUnderworld.setVolume(0);
		marioCastle.setVolume(0);
		marioStarman.setVolume(0);
		marioLvlComplete.setVolume(0);
		marioCastleComplete.setVolume(0);
		marioDead.setVolume(0);
		marioGameOver.setVolume(0);
		marioHurryUp.setVolume(0);
		menuMusicIntro.setVolume(0);
		menuMusicLoop.setVolume(0);
		pollyWolly.setVolume(0);
		metal01Intro.setVolume(0);
		metal01Loop.setVolume(0);
		metal02Intro.setVolume(0);
		metal02Loop.setVolume(0);
		metal03Intro.setVolume(0);
		metal03Loop.setVolume(0);
		metal04Intro.setVolume(0);
		metal04Loop.setVolume(0);
		metal05Intro.setVolume(0);
		metal05Loop.setVolume(0);
		metal06Intro.setVolume(0);
		metal06Loop.setVolume(0);
		metal07Intro.setVolume(0);
		metal07Loop.setVolume(0);
		metal08Intro.setVolume(0);
		metal08Loop.setVolume(0);
		metal09Intro.setVolume(0);
		metal09Loop.setVolume(0);
		metal10Intro.setVolume(0);
		metal10Loop.setVolume(0);
		metal11Intro.setVolume(0);
		metal11Loop.setVolume(0);
		
	}
	
	public static void unmuteAllMusic() {
		menuMusicIntro.setVolume(1);
		menuMusicLoop.setVolume(1);
		pollyWolly.setVolume(1);
		marioTheme.setVolume(1);
		marioUnderworld.setVolume(1);
		marioCastle.setVolume(1);
		marioStarman.setVolume(1);
		marioLvlComplete.setVolume(1);
		marioCastleComplete.setVolume(1);
		marioDead.setVolume(1);
		marioGameOver.setVolume(1);
		marioHurryUp.setVolume(1);
		menuMusicIntro.setVolume(1);
		menuMusicLoop.setVolume(1);
		pollyWolly.setVolume(1);
		metal01Intro.setVolume(1);
		metal01Loop.setVolume(1);
		metal02Intro.setVolume(1);
		metal02Loop.setVolume(1);
		metal03Intro.setVolume(1);
		metal03Loop.setVolume(1);
		metal04Intro.setVolume(1);
		metal04Loop.setVolume(1);
		metal05Intro.setVolume(1);
		metal05Loop.setVolume(1);
		metal06Intro.setVolume(1);
		metal06Loop.setVolume(1);
		metal07Intro.setVolume(1);
		metal07Loop.setVolume(1);
		metal08Intro.setVolume(1);
		metal08Loop.setVolume(1);
		metal09Intro.setVolume(1);
		metal09Loop.setVolume(1);
		metal10Intro.setVolume(1);
		metal10Loop.setVolume(1);
		metal11Intro.setVolume(1);
		metal11Loop.setVolume(1);
		
	}
	
	private void initAnimations() {
		try {
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
			
			int charsWid = 32, charsLen = 32, X, Y;
			
			X = 0;
			Y = 0;
			char1StillDown.addFrame(chars.getSubImage(X * charsWid + X, Y * charsLen + Y, charsWid, charsLen), 150);
			Y = 1;
			char1StillRight.addFrame(chars.getSubImage(X * charsWid + X, Y * charsLen + Y, charsWid, charsLen), 150);
			char1StillLeft.addFrame(chars.getSubImage(X * charsWid + X, Y * charsLen + Y, charsWid, charsLen).getFlippedCopy(true, false),
					150);
			Y = 2;
			char1StillUp.addFrame(chars.getSubImage(X * charsWid + X, Y * charsLen + Y + 1, charsWid, charsLen), 150);
			// end char1
			
			X = 1;
			Y = 0;
			char2StillDown.addFrame(chars.getSubImage(X * charsWid + X, Y * charsLen + Y, charsWid, charsLen), 150);
			Y = 1;
			char2StillRight.addFrame(chars.getSubImage(X * charsWid + X, Y * charsLen + Y, charsWid, charsLen), 150);
			char2StillLeft.addFrame(chars.getSubImage(X * charsWid + X, Y * charsLen + Y, charsWid, charsLen).getFlippedCopy(true, false),
					150);
			Y = 2;
			char2StillUp.addFrame(chars.getSubImage(X * charsWid + X, Y * charsLen + Y + 1, charsWid, charsLen), 150);
			// end char2
			
			X = 2;
			Y = 0;
			char3StillDown.addFrame(chars.getSubImage(X * charsWid + X, Y * charsLen + Y, charsWid, charsLen), 150);
			Y = 1;
			char3StillRight.addFrame(chars.getSubImage(X * charsWid + X, Y * charsLen + Y, charsWid, charsLen), 150);
			char3StillLeft.addFrame(chars.getSubImage(X * charsWid + X, Y * charsLen + Y, charsWid, charsLen).getFlippedCopy(true, false),
					150);
			Y = 2;
			char3StillUp.addFrame(chars.getSubImage(X * charsWid + X, Y * charsLen + Y + 1, charsWid, charsLen), 150);
			// end char3
			X = 4;
			Y = 0;
			char4StillDown.addFrame(chars.getSubImage(X * charsWid + X, Y * charsLen + Y, charsWid, charsLen), 150);
			Y = 1;
			char4StillRight.addFrame(chars.getSubImage(X * charsWid + X, Y * charsLen + Y, charsWid, charsLen), 150);
			char4StillLeft.addFrame(chars.getSubImage(X * charsWid + X, Y * charsLen + Y, charsWid, charsLen).getFlippedCopy(true, false),
					150);
			Y = 2;
			char4StillUp.addFrame(chars.getSubImage(X * charsWid + X, Y * charsLen + Y + 1, charsWid, charsLen), 150);
			// end char4
			
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
			int ys = 1;
			marioPandaStillR.addFrame(marioPanda.getSubImage(0, 32 + ys, 16, 16), 150);
			marioPandaStillL.addFrame(marioPanda.getSubImage(0, 32 + ys, 16, 16).getFlippedCopy(true, false), 150);
			
			marioPandaWalkRight.addFrame(marioPanda.getSubImage(3 * 16, 32 + ys, 16, 16), 150);
			marioPandaWalkLeft.addFrame(marioPanda.getSubImage(3 * 16, 32 + ys, 16, 16).getFlippedCopy(true, false), 150);
			marioPandaBigWalkRight.addFrame(marioPanda.getSubImage(3 * 16, 0, 16, 32), 150);
			marioPandaBigWalkLeft.addFrame(marioPanda.getSubImage(3 * 16, 0, 16, 32).getFlippedCopy(true, false), 150);
			
			for (int x = 1; x < 4; x++)
			
			{
				marioPandaWalkRight.addFrame(marioPanda.getSubImage(x * 16, 2 * 16 + ys, 15, 16), 150);
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
			mushroom.addFrame(mush.getSubImage(1, 1, 16, 16), 150);
			mushroom.addFrame(mush.getSubImage(18, 1, 16, 16), 150);
			mushroomDead = new Animation();
			mushroomDead.addFrame(mush.getSubImage(35, 1, 16, 16), 150);
			
			// blocks
			qBlockStill = new Animation();
			qBlockHit = new Animation();
			qBlockHitDown = new Animation();
			qBlockDeadHit = new Animation();
			qBlockDeadStill = new Animation();
			qBlockDeadHitDown = new Animation();
			bBlockStill = new Animation();
			bBlockHit = new Animation();
			bBlockBreak = new Animation();
			bBlockHitDown = new Animation();
			
			Image blocks = new Image("res/pngs/marioBlocks.png");
			
			bBlockHitDown.addFrame(blocks.getSubImage(1, 1, 16, 16 + 8), 150);
			bBlockHit.addFrame(blocks.getSubImage(1, 16 + 1 + 8, 16, 16 + 8), 150);
			bBlockStill.addFrame(blocks.getSubImage(1, 9, 16, 16), 150);
			bBlockBreak.addFrame(blocks.getSubImage(1, 33, 16, 17), 150);
			bBlockBreak.addFrame(blocks.getSubImage(1, 33, 16, 17), 150);
			bBlockBreak.addFrame(blocks.getSubImage(19, 33, 17, 18), 150);
			bBlockBreak.addFrame(blocks.getSubImage(37, 33, 19, 19), 150);
			
			qBlockDeadHitDown.addFrame(blocks.getSubImage(18, 1, 16, 16 + 8), 150);
			qBlockDeadHit.addFrame(blocks.getSubImage(18, 16 + 1 + 8, 16, 16 + 8), 150);
			qBlockDeadStill.addFrame(blocks.getSubImage(18, 9, 16, 16), 150);
			
			qBlockHitDown.addFrame(blocks.getSubImage(35, 1, 16, 16 + 8), 150);
			qBlockHit.addFrame(blocks.getSubImage(35, 9, 16, 16 + 8), 150);
			qBlockStill.addFrame(blocks.getSubImage(35, 9, 16, 16), 270);
			qBlockStill.addFrame(blocks.getSubImage(35 + 17, 9, 16, 16), 100);
			qBlockStill.addFrame(blocks.getSubImage(35 + 17 * 2, 9, 16, 16), 100);
			
			// powerUps
			pwrCoin = new Animation();
			pwrFlower = new Animation();
			pwrMush = new Animation();
			pwrStar = new Animation();
			
			pwrCoin.addFrame(new Image("res/pngs/marioCoins.png").getSubImage(1, 2, 16, 16), 100);
			pwrCoin.addFrame(new Image("res/pngs/marioCoins.png").getSubImage(15, 2, 16, 16), 100);
			pwrCoin.addFrame(new Image("res/pngs/marioCoins.png").getSubImage(28, 2, 16, 16), 100);
			pwrCoin.addFrame(new Image("res/pngs/marioCoins.png").getSubImage(45, 2, 16, 16), 100);
			
			Image pwrUps = new Image("res/pngs/powerUps.png");
			
			pwrFlower.addFrame(pwrUps.getSubImage(19, 2, 16, 16), 150);
			pwrFlower.addFrame(pwrUps.getSubImage(38, 2, 16, 16), 150);
			pwrFlower.addFrame(pwrUps.getSubImage(57, 2, 16, 16), 150);
			pwrFlower.addFrame(pwrUps.getSubImage(76, 2, 16, 16), 150);
			
			pwrMush.addFrame(pwrUps.getSubImage(2, 2, 16, 16), 150);
			
			pwrStar.addFrame(pwrUps.getSubImage(93, 2, 14, 16), 150);
			pwrStar.addFrame(pwrUps.getSubImage(112, 2, 14, 16), 150);
			pwrStar.addFrame(pwrUps.getSubImage(131, 2, 14, 16), 150);
			pwrStar.addFrame(pwrUps.getSubImage(150, 2, 14, 16), 150);
			
			
		} catch (SlickException e) {
		}
		
	}
	
}