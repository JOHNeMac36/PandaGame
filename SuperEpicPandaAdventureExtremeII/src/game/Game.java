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
	public static Animation marioPandaBigStillR, marioPandaBigWalkRight, marioPandaBigJumpR;
	public static Animation marioPandaDownR, marioPandaDownL, marioPandaBigDownR, marioPandaBigDownL;
	public static Animation marioPandaDownFallR, marioPandaDownFallL, marioPandaDownBigFallR, marioPandaDownBigFallL;
	public static Animation marioPandaBigStillL, marioPandaBigWalkLeft, marioPandaBigJumpL;
	
	public static Animation SmarioPandaStillR, SmarioPandaWalkRight, SmarioPandaJumpR, SmarioPandaDown;
	public static Animation SmarioPandaStillL, SmarioPandaWalkLeft, SmarioPandaJumpL;
	public static Animation SmarioPandaBigStillR, SmarioPandaBigWalkRight, SmarioPandaBigJumpR;
	public static Animation SmarioPandaDownR, SmarioPandaDownL, SmarioPandaBigDownR, SmarioPandaBigDownL;
	public static Animation SmarioPandaDownFallR, SmarioPandaDownFallL, SmarioPandaDownBigFallR, SmarioPandaDownBigFallL;
	public static Animation SmarioPandaBigStillL, SmarioPandaBigWalkLeft, SmarioPandaBigJumpL;
	
	public static Animation FmarioPandaStillR, FmarioPandaWalkRight, FmarioPandaJumpR, FmarioPandaDown;
	public static Animation FmarioPandaStillL, FmarioPandaWalkLeft, FmarioPandaJumpL;
	public static Animation FmarioPandaBigStillR, FmarioPandaBigWalkRight, FmarioPandaBigJumpR;
	public static Animation FmarioPandaDownR, FmarioPandaDownL, FmarioPandaBigDownR, FmarioPandaBigDownL;
	public static Animation FmarioPandaDownFallR, FmarioPandaDownFallL, FmarioPandaDownBigFallR, FmarioPandaDownBigFallL;
	public static Animation FmarioPandaBigStillL, FmarioPandaBigWalkLeft, FmarioPandaBigJumpL;
	
	public static Animation powerUp;
	public static Animation qBlock, qBlockDead, bBlock;
	public Animation bBlockBreak;
	public static Animation fireballShoot, fireballHit, barrellStack, oilFire, girraffe, giraffeLove;
	
	public static Animation dkHowHigh, dkRollRight;
	public static Animation dkPickUpBarrel;
	public static Animation dkStill;
	public static Animation dkStillSmile;
	public static Animation dkBlueRollDown;
	public static Animation dkCarryUp;
	public static Animation dkQuake;
	public static Animation dkPoundChest;
	public static Animation dkRollDown;
	public static Animation dkBarrelRollRight, dkBarrelRollLeft, dkBarrelRollDown, dkFire;
	public static Animation dkBlueBarrelRollRight, dkBluePickUpBarrel, dkBlueBarrelRollDown, dkBlueFire;
	public static Animation jmStillRight, jmStillLeft, jmWalkRight, jmWalkLeft, jmClimb, jmPushUp, jmStillForward, jmDead;
	public static Animation HjmStillRight, HjmStillLeft, HjmWalkRight, HjmWalkLeft, jmJumpR, jmJumpL;
	
	public static boolean pet1Found, pet2Found, pet3Found, pet4Found;
	public static char charLock1, charLock2, charLock3, charLock4;
	public static boolean isMusicOn;
	public static GameContainer gc;
	public static Image playNow, exitGame, menuScene, title, musicOn, musicOff, confetti, marioTitle, marioCursor;
	public static Music menuMusicIntro, menuMusicLoop, pollyWolly;
	public static Music marioTheme, marioUnderworld, marioCastle, marioStarman, marioLvlComplete, marioCastleComplete, marioDead, marioGameOver, marioHurryUp;
	public static Sound pandaPunch, sadPanda, itsPanda, pandaIcecream, marioBump, marioCoin, marioFireball, marioJump, marioKick, mario1Up, marioPause, marioPowerUp,
			marioStageClear, marioFlag, marioApearingPowerUp, marioBlockBreak, marioPowerDown, marioStomp;
	public static Music metal01Intro, metal01Loop, metal02Intro, metal02Loop, metal03Intro, metal03Loop, metal04Intro, metal04Loop, metal05Intro, metal05Loop,
			metal06Intro, metal06Loop, metal07Intro, metal07Loop, metal08Intro, metal08Loop, metal09Intro, metal09Loop, metal10Intro, metal10Loop, metal11Intro,
			metal11Loop;
	public static Sound dkBackground;
	public static Sound dkDeath, dkHammer, dkHowHighMusic, dkIntro, dkItemGet, dkJump, dkJumpBarrel, dkWin;
	public static Music dkWalking;
	public static String gamename = "Super Epic Panda Adventure Extreme 2!";
	
	public static final int len = 30, wid = 28, xSpace = wid + 1, ySpace = len + 1;
	
	public static AppGameContainer appgc;
	// end of attributes
	public static Animation pwrCoin, pwrFlower, pwrMush, pwrStar;
	
	public static void main(String[] args) {
		
		try {
			appgc = new AppGameContainer(new Game(gamename));
			appgc.setDisplayMode(640, 640, false);
			appgc.setShowFPS(false);
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
		this.addState(new LevelBoss(lvlBoss));
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
			this.getState(menu).init(gc, this);
			this.getState(splash).init(gc, this);
			this.getState(roam).init(gc, this);
			this.getState(lvl01).init(gc, this);
			this.getState(lvl02).init(gc, this);
			this.getState(lvl03).init(gc, this);
			this.getState(lvl04).init(gc, this);
			this.getState(lvlBoss).init(gc, this);
			this.enterState(lvl03);
			
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
			marioApearingPowerUp = new Sound("res/oggs/marioApearingPowerUp.ogg");
			marioBlockBreak = new Sound("res/oggs/marioBlockBreak.ogg");
			marioPowerDown = new Sound("res/oggs/marioPowerDown.ogg");
			marioStomp = new Sound("res/oggs/marioStomp.ogg");
			
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
			dkBackground = new Sound("res/oggs/DKbacmusic.ogg");
			dkDeath = new Sound("res/oggs/DKdeath.ogg");
			dkHammer = new Sound("res/oggs/DKhammer.ogg");
			dkHowHighMusic = new Sound("res/oggs/DKhowhigh.ogg");
			dkIntro = new Sound("res/oggs/DKintro1_long.ogg");
			dkItemGet = new Sound("res/oggs/DKitemget.ogg");
			dkJump = new Sound("res/oggs/DKjump.ogg");
			dkJumpBarrel = new Sound("res/oggs/DKjumpbar.ogg");
			dkWalking = new Music("res/oggs/DKwalking.ogg");
			dkWin = new Sound("res/oggs/DKwin1.ogg");
			
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
			char1StillLeft.addFrame(chars.getSubImage(X * charsWid + X, Y * charsLen + Y, charsWid, charsLen).getFlippedCopy(true, false), 150);
			Y = 2;
			char1StillUp.addFrame(chars.getSubImage(X * charsWid + X, Y * charsLen + Y + 1, charsWid, charsLen), 150);
			// end char1
			
			X = 1;
			Y = 0;
			char2StillDown.addFrame(chars.getSubImage(X * charsWid + X, Y * charsLen + Y, charsWid, charsLen), 150);
			Y = 1;
			char2StillRight.addFrame(chars.getSubImage(X * charsWid + X, Y * charsLen + Y, charsWid, charsLen), 150);
			char2StillLeft.addFrame(chars.getSubImage(X * charsWid + X, Y * charsLen + Y, charsWid, charsLen).getFlippedCopy(true, false), 150);
			Y = 2;
			char2StillUp.addFrame(chars.getSubImage(X * charsWid + X, Y * charsLen + Y + 1, charsWid, charsLen), 150);
			// end char2
			
			X = 2;
			Y = 0;
			char3StillDown.addFrame(chars.getSubImage(X * charsWid + X, Y * charsLen + Y, charsWid, charsLen), 150);
			Y = 1;
			char3StillRight.addFrame(chars.getSubImage(X * charsWid + X, Y * charsLen + Y, charsWid, charsLen), 150);
			char3StillLeft.addFrame(chars.getSubImage(X * charsWid + X, Y * charsLen + Y, charsWid, charsLen).getFlippedCopy(true, false), 150);
			Y = 2;
			char3StillUp.addFrame(chars.getSubImage(X * charsWid + X, Y * charsLen + Y + 1, charsWid, charsLen), 150);
			// end char3
			X = 4;
			Y = 0;
			char4StillDown.addFrame(chars.getSubImage(X * charsWid + X, Y * charsLen + Y, charsWid, charsLen), 150);
			Y = 1;
			char4StillRight.addFrame(chars.getSubImage(X * charsWid + X, Y * charsLen + Y, charsWid, charsLen), 150);
			char4StillLeft.addFrame(chars.getSubImage(X * charsWid + X, Y * charsLen + Y, charsWid, charsLen).getFlippedCopy(true, false), 150);
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
			marioPandaBigStillR = new Animation();
			marioPandaBigWalkRight = new Animation();
			marioPandaBigJumpR = new Animation();
			marioPandaBigDownR = new Animation();
			marioPandaBigDownL = new Animation();
			marioPandaDownR = new Animation();
			marioPandaDownL = new Animation();
			marioPandaBigStillL = new Animation();
			marioPandaBigWalkLeft = new Animation();
			marioPandaBigJumpL = new Animation();
			marioPandaDownFallR = new Animation();
			marioPandaDownFallL = new Animation();
			marioPandaDownBigFallR = new Animation();
			marioPandaDownBigFallL = new Animation();
			
			SmarioPandaStillR = new Animation();
			SmarioPandaWalkRight = new Animation();
			SmarioPandaJumpR = new Animation();
			SmarioPandaDown = new Animation();
			SmarioPandaStillL = new Animation();
			SmarioPandaWalkLeft = new Animation();
			SmarioPandaJumpL = new Animation();
			SmarioPandaBigStillR = new Animation();
			SmarioPandaBigWalkRight = new Animation();
			SmarioPandaBigJumpR = new Animation();
			SmarioPandaBigDownR = new Animation();
			SmarioPandaBigDownL = new Animation();
			SmarioPandaDownR = new Animation();
			SmarioPandaDownL = new Animation();
			SmarioPandaBigStillL = new Animation();
			SmarioPandaBigWalkLeft = new Animation();
			SmarioPandaBigJumpL = new Animation();
			SmarioPandaDownFallR = new Animation();
			SmarioPandaDownFallL = new Animation();
			SmarioPandaDownBigFallR = new Animation();
			SmarioPandaDownBigFallL = new Animation();
			
			FmarioPandaStillR = new Animation();
			FmarioPandaWalkRight = new Animation();
			FmarioPandaJumpR = new Animation();
			FmarioPandaDown = new Animation();
			FmarioPandaStillL = new Animation();
			FmarioPandaWalkLeft = new Animation();
			FmarioPandaJumpL = new Animation();
			FmarioPandaBigStillR = new Animation();
			FmarioPandaBigWalkRight = new Animation();
			FmarioPandaBigJumpR = new Animation();
			FmarioPandaBigDownR = new Animation();
			FmarioPandaBigDownL = new Animation();
			FmarioPandaDownR = new Animation();
			FmarioPandaDownL = new Animation();
			FmarioPandaBigStillL = new Animation();
			FmarioPandaBigWalkLeft = new Animation();
			FmarioPandaBigJumpL = new Animation();
			FmarioPandaDownFallR = new Animation();
			FmarioPandaDownFallL = new Animation();
			FmarioPandaDownBigFallR = new Animation();
			FmarioPandaDownBigFallL = new Animation();
			
			// panda still
			marioPandaStillR.addFrame(marioPanda.getSubImage(1, 34, 16, 32), 150);
			marioPandaStillL.addFrame(marioPanda.getSubImage(1, 34, 16, 32).getFlippedCopy(true, false), 150);
			marioPandaBigStillR.addFrame(marioPanda.getSubImage(1, 1, 16, 32), 150);
			marioPandaBigStillL.addFrame(marioPanda.getSubImage(1, 1, 16, 32).getFlippedCopy(true, false), 150);
			
			// big Panda Right and Left
			marioPandaBigWalkRight.addFrame(marioPanda.getSubImage(35, 1, 16, 32), 150);
			marioPandaBigWalkRight.addFrame(marioPanda.getSubImage(18, 1, 16, 32), 150);
			marioPandaBigWalkRight.addFrame(marioPanda.getSubImage(35, 1, 16, 32), 150);
			marioPandaBigWalkRight.addFrame(marioPanda.getSubImage(52, 1, 16, 32), 150);
			
			marioPandaBigWalkLeft.addFrame(marioPanda.getSubImage(35, 1, 16, 32).getFlippedCopy(true, false), 150);
			marioPandaBigWalkLeft.addFrame(marioPanda.getSubImage(18, 1, 16, 32).getFlippedCopy(true, false), 150);
			marioPandaBigWalkLeft.addFrame(marioPanda.getSubImage(35, 1, 16, 32).getFlippedCopy(true, false), 150);
			marioPandaBigWalkLeft.addFrame(marioPanda.getSubImage(52, 1, 16, 32).getFlippedCopy(true, false), 150);
			
			// small Panda right and left
			marioPandaWalkRight.addFrame(marioPanda.getSubImage(35, 34, 16, 32), 150);
			marioPandaWalkRight.addFrame(marioPanda.getSubImage(18, 34, 16, 32), 150);
			marioPandaWalkRight.addFrame(marioPanda.getSubImage(35, 34, 16, 32), 150);
			marioPandaWalkRight.addFrame(marioPanda.getSubImage(52, 34, 16, 32), 150);
			
			marioPandaWalkLeft.addFrame(marioPanda.getSubImage(35, 34, 16, 32).getFlippedCopy(true, false), 150);
			marioPandaWalkLeft.addFrame(marioPanda.getSubImage(18, 34, 16, 32).getFlippedCopy(true, false), 150);
			marioPandaWalkLeft.addFrame(marioPanda.getSubImage(35, 34, 16, 32).getFlippedCopy(true, false), 150);
			marioPandaWalkLeft.addFrame(marioPanda.getSubImage(52, 34, 16, 32).getFlippedCopy(true, false), 150);
			
			// panda jump
			marioPandaJumpR.addFrame(marioPanda.copy().getSubImage(86, 34, 16, 32), 150);
			marioPandaJumpL.addFrame(marioPanda.copy().getSubImage(86, 34, 16, 32).getFlippedCopy(true, false), 150);
			marioPandaBigJumpR.addFrame(marioPanda.copy().getSubImage(86, 1, 16, 32), 150);
			marioPandaBigJumpL.addFrame(marioPanda.copy().getSubImage(86, 1, 16, 32).getFlippedCopy(true, false), 150);
			
			// panda down
			marioPandaDownR.addFrame(marioPanda.getSubImage(120, 34, 16, 32), 150);
			marioPandaDownL.addFrame(marioPanda.getSubImage(120, 34, 16, 32).getFlippedCopy(true, false), 150);
			marioPandaBigDownR.addFrame(marioPanda.getSubImage(103, 1, 16, 32), 150);
			marioPandaBigDownL.addFrame(marioPanda.getSubImage(103, 1, 16, 32).getFlippedCopy(true, false), 150);
			
			// panda downFall
			marioPandaDownFallR.addFrame(marioPanda.getSubImage(139, 34, 16, 32), 150);
			marioPandaDownFallL.addFrame(marioPanda.getSubImage(139, 34, 16, 32).getFlippedCopy(true, false), 150);
			marioPandaDownBigFallR.addFrame(marioPanda.getSubImage(138, 1, 16, 32), 150);
			marioPandaDownBigFallL.addFrame(marioPanda.getSubImage(138, 1, 16, 32).getFlippedCopy(true, false), 150);
			
			// panda still
			SmarioPandaStillR.addFrame(marioPanda.getSubImage(1, 174, 16, 32), 150);
			SmarioPandaStillL.addFrame(marioPanda.getSubImage(1, 174, 16, 32).getFlippedCopy(true, false), 150);
			SmarioPandaBigStillR.addFrame(marioPanda.getSubImage(1, 141, 16, 32), 150);
			SmarioPandaBigStillL.addFrame(marioPanda.getSubImage(1, 141, 16, 32).getFlippedCopy(true, false), 150);
			
			// big Panda Right and Left
			SmarioPandaBigWalkRight.addFrame(marioPanda.getSubImage(35, 141, 16, 32), 150);
			SmarioPandaBigWalkRight.addFrame(marioPanda.getSubImage(18, 141, 16, 32), 150);
			SmarioPandaBigWalkRight.addFrame(marioPanda.getSubImage(35, 141, 16, 32), 150);
			SmarioPandaBigWalkRight.addFrame(marioPanda.getSubImage(52, 141, 16, 32), 150);
			
			SmarioPandaBigWalkLeft.addFrame(marioPanda.getSubImage(35, 141, 16, 32).getFlippedCopy(true, false), 150);
			SmarioPandaBigWalkLeft.addFrame(marioPanda.getSubImage(18, 141, 16, 32).getFlippedCopy(true, false), 150);
			SmarioPandaBigWalkLeft.addFrame(marioPanda.getSubImage(35, 141, 16, 32).getFlippedCopy(true, false), 150);
			SmarioPandaBigWalkLeft.addFrame(marioPanda.getSubImage(52, 141, 16, 32).getFlippedCopy(true, false), 150);
			
			// small Panda right and left
			SmarioPandaWalkRight.addFrame(marioPanda.getSubImage(35, 174, 16, 32), 150);
			SmarioPandaWalkRight.addFrame(marioPanda.getSubImage(18, 174, 16, 32), 150);
			SmarioPandaWalkRight.addFrame(marioPanda.getSubImage(35, 174, 16, 32), 150);
			SmarioPandaWalkRight.addFrame(marioPanda.getSubImage(52, 174, 16, 32), 150);
			
			SmarioPandaWalkLeft.addFrame(marioPanda.getSubImage(35, 174, 16, 32).getFlippedCopy(true, false), 150);
			SmarioPandaWalkLeft.addFrame(marioPanda.getSubImage(18, 174, 16, 32).getFlippedCopy(true, false), 150);
			SmarioPandaWalkLeft.addFrame(marioPanda.getSubImage(35, 174, 16, 32).getFlippedCopy(true, false), 150);
			SmarioPandaWalkLeft.addFrame(marioPanda.getSubImage(52, 174, 16, 32).getFlippedCopy(true, false), 150);
			
			// panda jump
			SmarioPandaJumpR.addFrame(marioPanda.copy().getSubImage(86, 174, 16, 32), 150);
			SmarioPandaJumpL.addFrame(marioPanda.copy().getSubImage(86, 174, 16, 32).getFlippedCopy(true, false), 150);
			SmarioPandaBigJumpR.addFrame(marioPanda.copy().getSubImage(86, 141, 16, 32), 150);
			SmarioPandaBigJumpL.addFrame(marioPanda.copy().getSubImage(86, 141, 16, 32).getFlippedCopy(true, false), 150);
			
			// panda down
			SmarioPandaDownR.addFrame(marioPanda.getSubImage(120, 174, 16, 32), 150);
			SmarioPandaDownL.addFrame(marioPanda.getSubImage(120, 174, 16, 32).getFlippedCopy(true, false), 150);
			SmarioPandaBigDownR.addFrame(marioPanda.getSubImage(103, 141, 16, 32), 150);
			SmarioPandaBigDownL.addFrame(marioPanda.getSubImage(103, 141, 16, 32).getFlippedCopy(true, false), 150);
			
			// panda downFall
			SmarioPandaDownFallR.addFrame(marioPanda.getSubImage(139, 174, 16, 32), 150);
			SmarioPandaDownFallL.addFrame(marioPanda.getSubImage(139, 174, 16, 32).getFlippedCopy(true, false), 150);
			SmarioPandaDownBigFallR.addFrame(marioPanda.getSubImage(138, 141, 16, 32), 150);
			SmarioPandaDownBigFallL.addFrame(marioPanda.getSubImage(138, 141, 16, 32).getFlippedCopy(true, false), 150);
			
			// panda still
			FmarioPandaStillR.addFrame(marioPanda.getSubImage(1, 104, 16, 32), 150);
			FmarioPandaStillL.addFrame(marioPanda.getSubImage(1, 104, 16, 32).getFlippedCopy(true, false), 150);
			FmarioPandaBigStillR.addFrame(marioPanda.getSubImage(1, 71, 16, 32), 150);
			FmarioPandaBigStillL.addFrame(marioPanda.getSubImage(1, 71, 16, 32).getFlippedCopy(true, false), 150);
			
			// big Panda Right and Left
			FmarioPandaBigWalkRight.addFrame(marioPanda.getSubImage(35, 71, 16, 32), 150);
			FmarioPandaBigWalkRight.addFrame(marioPanda.getSubImage(18, 71, 16, 32), 150);
			FmarioPandaBigWalkRight.addFrame(marioPanda.getSubImage(35, 71, 16, 32), 150);
			FmarioPandaBigWalkRight.addFrame(marioPanda.getSubImage(52, 71, 16, 32), 150);
			
			FmarioPandaBigWalkLeft.addFrame(marioPanda.getSubImage(35, 71, 16, 32).getFlippedCopy(true, false), 150);
			FmarioPandaBigWalkLeft.addFrame(marioPanda.getSubImage(18, 71, 16, 32).getFlippedCopy(true, false), 150);
			FmarioPandaBigWalkLeft.addFrame(marioPanda.getSubImage(35, 71, 16, 32).getFlippedCopy(true, false), 150);
			FmarioPandaBigWalkLeft.addFrame(marioPanda.getSubImage(52, 71, 16, 32).getFlippedCopy(true, false), 150);
			
			// small Panda right and left
			FmarioPandaWalkRight.addFrame(marioPanda.getSubImage(35, 104, 16, 32), 150);
			FmarioPandaWalkRight.addFrame(marioPanda.getSubImage(18, 104, 16, 32), 150);
			FmarioPandaWalkRight.addFrame(marioPanda.getSubImage(35, 104, 16, 32), 150);
			FmarioPandaWalkRight.addFrame(marioPanda.getSubImage(52, 104, 16, 32), 150);
			
			FmarioPandaWalkLeft.addFrame(marioPanda.getSubImage(35, 104, 16, 32).getFlippedCopy(true, false), 150);
			FmarioPandaWalkLeft.addFrame(marioPanda.getSubImage(18, 104, 16, 32).getFlippedCopy(true, false), 150);
			FmarioPandaWalkLeft.addFrame(marioPanda.getSubImage(35, 104, 16, 32).getFlippedCopy(true, false), 150);
			FmarioPandaWalkLeft.addFrame(marioPanda.getSubImage(52, 104, 16, 32).getFlippedCopy(true, false), 150);
			
			// panda jump
			FmarioPandaJumpR.addFrame(marioPanda.copy().getSubImage(86, 104, 16, 32), 150);
			FmarioPandaJumpL.addFrame(marioPanda.copy().getSubImage(86, 104, 16, 32).getFlippedCopy(true, false), 150);
			FmarioPandaBigJumpR.addFrame(marioPanda.copy().getSubImage(86, 71, 16, 32), 150);
			FmarioPandaBigJumpL.addFrame(marioPanda.copy().getSubImage(86, 71, 16, 32).getFlippedCopy(true, false), 150);
			
			// panda down
			FmarioPandaDownR.addFrame(marioPanda.getSubImage(120, 104, 16, 32), 150);
			FmarioPandaDownL.addFrame(marioPanda.getSubImage(120, 104, 16, 32).getFlippedCopy(true, false), 150);
			FmarioPandaBigDownR.addFrame(marioPanda.getSubImage(104, 71, 16, 32), 150);
			FmarioPandaBigDownL.addFrame(marioPanda.getSubImage(104, 71, 16, 32).getFlippedCopy(true, false), 150);
			
			// panda downFall
			FmarioPandaDownFallR.addFrame(marioPanda.getSubImage(139, 104, 16, 32), 150);
			FmarioPandaDownFallL.addFrame(marioPanda.getSubImage(139, 104, 16, 32).getFlippedCopy(true, false), 150);
			FmarioPandaDownBigFallR.addFrame(marioPanda.getSubImage(138, 71, 16, 32), 150);
			FmarioPandaDownBigFallL.addFrame(marioPanda.getSubImage(138, 71, 16, 32).getFlippedCopy(true, false), 150);
			
			// mushroom enemy
			Image mush = new Image("res/sprites/mushroom.png");
			mushroom = new Animation();
			mushroom.addFrame(mush.getSubImage(1, 1, 16, 16), 150);
			mushroom.addFrame(mush.getSubImage(18, 1, 16, 16), 150);
			mushroomDead = new Animation();
			mushroomDead.addFrame(mush.getSubImage(35, 1, 16, 16), 150);
			
			// blocks
			qBlock = new Animation();
			qBlockDead = new Animation();
			bBlock = new Animation();
			bBlockBreak = new Animation();
			
			Image blocks = new Image("res/pngs/marioBlocks.png");
			
			bBlock.addFrame(blocks.getSubImage(1, 1, 40, 40), 150);
			qBlockDead.addFrame(blocks.getSubImage(42, 1, 40, 40), 150);
			
			qBlock.addFrame(blocks.getSubImage(83, 1, 40, 40), 270 * 2);
			qBlock.addFrame(blocks.getSubImage(124, 1, 40, 40), 200);
			qBlock.addFrame(blocks.getSubImage(165, 1, 40, 40), 200);
			
			bBlockBreak.addFrame(blocks.getSubImage(1, 65, 90, 90), 150);
			bBlockBreak.addFrame(blocks.getSubImage(81, 65, 90, 90), 150);
			bBlockBreak.addFrame(blocks.getSubImage(192, 65, 90, 90), 300);
			bBlockBreak.addFrame(blocks.getSubImage(1, 1, 1, 1), 100000);
			
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
			
			fireballShoot = new Animation();
			fireballHit = new Animation();
			
			Image fireballs = new Image("res/pngs/fireballs.png");
			
			fireballShoot.addFrame(fireballs.getSubImage(1, 1, 16, 16), 80);
			fireballShoot.addFrame(fireballs.getSubImage(17 + 1, 1, 16, 16), 80);
			fireballShoot.addFrame(fireballs.getSubImage(2 * 17 + 1, 1, 16, 16), 80);
			fireballShoot.addFrame(fireballs.getSubImage(3 * 17 + 1, 1, 16, 16), 80);
			
			fireballHit.addFrame(fireballs.getSubImage(4 * 17 + 1, 1, 16, 16), 150);
			fireballHit.addFrame(fireballs.getSubImage(5 * 17 + 1, 1, 16, 16), 150);
			fireballHit.addFrame(fireballs.getSubImage(6 * 17 + 1, 1, 16, 16), 150);
			
			/**
			 * Donkey Kong
			 */
			
			dkHowHigh = new Animation();
			dkRollRight = new Animation();
			dkPickUpBarrel = new Animation();
			dkCarryUp = new Animation();
			dkStill = new Animation();
			dkStillSmile = new Animation();
			dkRollDown = new Animation();
			dkBlueRollDown = new Animation();
			dkBarrelRollRight = new Animation();
			dkBarrelRollLeft = new Animation();
			dkBarrelRollDown = new Animation();
			dkFire = new Animation();
			dkBlueBarrelRollRight = new Animation();
			dkBluePickUpBarrel = new Animation();
			dkBlueBarrelRollDown = new Animation();
			dkBlueFire = new Animation();
			dkPoundChest = new Animation();
			
			jmStillRight = new Animation();
			jmStillLeft = new Animation();
			jmWalkRight = new Animation();
			jmWalkLeft = new Animation();
			jmClimb = new Animation();
			jmPushUp = new Animation();
			jmStillForward = new Animation();
			HjmStillRight = new Animation();
			HjmStillLeft = new Animation();
			HjmWalkRight = new Animation();
			HjmWalkLeft = new Animation();
			jmJumpR = new Animation();
			jmJumpL = new Animation();
			jmDead = new Animation();
			
			barrellStack = new Animation();
			oilFire = new Animation();
			girraffe = new Animation();
			giraffeLove = new Animation();
			
			Image dkSprites = new Image("res/sprites/DKsprites.png");
			
			dkHowHigh.addFrame(dkSprites.getSubImage(16, 206, 46, 33), 150);
			dkRollRight.addFrame(dkSprites.getSubImage(70, 138, 43, 32), 1000);
			dkPickUpBarrel.addFrame(dkSprites.getSubImage(70, 138, 43, 32).getFlippedCopy(true, false), 1000);
			dkBluePickUpBarrel.addFrame(dkSprites.getSubImage(209, 191, 16, 16), 125);
			dkCarryUp.addFrame(dkSprites.getSubImage(25, 67, 37, 36), 500);
			dkCarryUp.addFrame(dkSprites.getSubImage(66, 67, 37, 36), 500);
			dkStill.addFrame(dkSprites.getSubImage(25, 1, 39, 32), 1000);
			dkStillSmile.addFrame(dkSprites.getSubImage(26, 105, 41, 30).getFlippedCopy(false, true), 1000);
			dkRollDown.addFrame(dkSprites.getSubImage(21, 172, 40, 32), 1000);
			dkBlueRollDown.addFrame(dkSprites.getSubImage(72, 1, 40, 32), 1000);
			dkBarrelRollRight.addFrame(dkSprites.getSubImage(154, 101, 12, 10), 50);
			dkBarrelRollRight.addFrame(dkSprites.getSubImage(170, 101, 12, 10), 50);
			dkBarrelRollRight.addFrame(dkSprites.getSubImage(186, 101, 12, 10), 50);
			dkBarrelRollRight.addFrame(dkSprites.getSubImage(203, 101, 12, 10), 50);
			
			dkBarrelRollLeft.addFrame(dkSprites.getSubImage(203, 101, 12, 10), 50);
			dkBarrelRollLeft.addFrame(dkSprites.getSubImage(186, 101, 12, 10), 50);
			dkBarrelRollLeft.addFrame(dkSprites.getSubImage(170, 101, 12, 10), 50);
			dkBarrelRollLeft.addFrame(dkSprites.getSubImage(154, 101, 12, 10), 50);
			
			dkBarrelRollDown.addFrame(dkSprites.getSubImage(133, 101, 16, 10), 50);
			dkBarrelRollDown.addFrame(dkSprites.getSubImage(116, 101, 16, 10), 50);
			
			dkBlueBarrelRollDown.addFrame(dkSprites.getSubImage(133, 116, 16, 10), 50);
			dkBlueBarrelRollDown.addFrame(dkSprites.getSubImage(116, 116, 16, 10), 50);
			
			dkFire.addFrame(dkSprites.getSubImage(218, 95, 15, 16), 500);
			dkFire.addFrame(dkSprites.getSubImage(218, 95, 15, 16).getFlippedCopy(true, false), 500);
			
			dkBlueFire.addFrame(dkSprites.getSubImage(218, 115, 15, 16), 500);
			dkBlueFire.addFrame(dkSprites.getSubImage(218, 115, 15, 16).getFlippedCopy(true, false), 500);
			
			dkPoundChest.addFrame(dkSprites.getSubImage(73, 35, 46, 32), 500);
			
			barrellStack.addFrame(dkSprites.getSubImage(1, 40, 20, 32), 500);
			oilFire.addFrame(dkSprites.getSubImage(3, 1, 16, 30), 500);
			girraffe.addFrame(dkSprites.getSubImage(138, 11, 35, 24), 500);
			girraffe.addFrame(dkSprites.getSubImage(177, 10, 35, 24), 500);
			
			giraffeLove.addFrame(dkSprites.getSubImage(138, 64, 27, 30), 500);
			
			jmStillRight.addFrame(dkSprites.getSubImage(162, 193, 10, 16).getFlippedCopy(true, false), 125);
			jmStillLeft.addFrame(dkSprites.getSubImage(162, 193, 10, 16), 125);
			
			jmJumpL.addFrame(dkSprites.getSubImage(176, 193, 16, 16), 125);
			jmJumpR.addFrame(dkSprites.getSubImage(176, 193, 16, 16).getFlippedCopy(true, false), 125);
			
			jmWalkLeft.addFrame(dkSprites.getSubImage(158, 193, 16, 16), 125);
			jmWalkLeft.addFrame(dkSprites.getSubImage(175, 193, 16, 16), 125);
			jmWalkLeft.addFrame(dkSprites.getSubImage(158, 193, 16, 16), 125);
			jmWalkLeft.addFrame(dkSprites.getSubImage(191, 207, 16, 16), 125);
			
			jmWalkRight.addFrame(dkSprites.getSubImage(158, 193, 16, 16).getFlippedCopy(true, false), 125);
			jmWalkRight.addFrame(dkSprites.getSubImage(175, 193, 16, 16).getFlippedCopy(true, false), 125);
			jmWalkRight.addFrame(dkSprites.getSubImage(158, 193, 16, 16).getFlippedCopy(true, false), 125);
			jmWalkRight.addFrame(dkSprites.getSubImage(191, 207, 16, 16).getFlippedCopy(true, false), 125);
			
			jmClimb.addFrame(dkSprites.getSubImage(209, 193, 13, 16), 125);
			jmClimb.addFrame(dkSprites.getSubImage(209, 193, 13, 16).getFlippedCopy(true, false), 125);
			
			jmPushUp.addFrame(dkSprites.getSubImage(224, 193, 14, 16), 125);
			jmPushUp.addFrame(dkSprites.getSubImage(239, 193, 16, 16), 125);
			jmPushUp.addFrame(dkSprites.getSubImage(209, 193, 13, 16), 125);
			jmPushUp.addFrame(dkSprites.getSubImage(209, 193, 13, 16).getFlippedCopy(true, false), 125);
			jmPushUp.addFrame(dkSprites.getSubImage(209, 191, 16, 16), 125);
			
			jmStillForward.addFrame(dkSprites.getSubImage(209, 191, 16, 16), 125);
			
			for (int i = 0; i < 3; i++) {
				jmDead.addFrame(dkSprites.getSubImage(291, 192, 16, 16), 250);
				jmDead.addFrame(dkSprites.getSubImage(308, 192, 16, 16), 250);
				jmDead.addFrame(dkSprites.getSubImage(291, 192, 16, 16).getFlippedCopy(false, true), 250);
				jmDead.addFrame(dkSprites.getSubImage(308, 192, 16, 16).getFlippedCopy(true, false), 250);
			}
			jmDead.addFrame(dkSprites.getSubImage(274, 192, 16, 16), 6000);
			
			jmStillRight.setAutoUpdate(false);
			jmStillLeft.setAutoUpdate(false);
			jmWalkRight.setAutoUpdate(false);
			jmWalkLeft.setAutoUpdate(false);
			jmClimb.setAutoUpdate(false);
			jmPushUp.setAutoUpdate(false);
			jmStillForward.setAutoUpdate(false);
			HjmStillRight.setAutoUpdate(false);
			HjmStillLeft.setAutoUpdate(false);
			HjmWalkRight.setAutoUpdate(false);
			HjmWalkLeft.setAutoUpdate(false);
			
			// dkStill.addFrame(dkSprites.getSubImage(24, 33, 40, 32), 125);
			// dkStillSmile.addFrame(dkSprites.getSubImage(26, 105, 41,
			// 30).getFlippedCopy(false, true), 125);
			// dkHowHigh.addFrame(dkSprites.getSubImage(17, 206, 46, 32), 125);
			// dkRollRight.addFrame(dkSprites.getSubImage(70, 138, 43, 32),
			// 125);
			// dkPickUpBarrel.addFrame(dkSprites.getSubImage(70, 138, 43,
			// 32).getFlippedCopy(true, false), 125);
			// dkCarryUp.addFrame(dkSprites.getSubImage(25, 68, 38, 36), 125);
			// dkCarryUp.addFrame(dkSprites.getSubImage(66, 68, 43, 36), 125);
			//
			// dkRollDown.addFrame(dkSprites.getSubImage(21, 172, 40, 32), 125);
			// dkBlueRollDown.addFrame(dkSprites.getSubImage(72, 1, 40, 32),
			// 125);
			//
			// dkBarrelRollRight.addFrame(dkSprites.getSubImage(209, 191, 16,
			// 16), 125);
			// dkBarrelRollLeft.addFrame(dkSprites.getSubImage(209, 191, 16,
			// 16), 125);
			// dkBarrelRollDown.addFrame(dkSprites.getSubImage(209, 191, 16,
			// 16), 125);
			// dkFire.addFrame(dkSprites.getSubImage(209, 191, 16, 16), 125);
			// dkBlueBarrelRollRight.addFrame(dkSprites.getSubImage(209, 191,
			// 16, 16), 125);
			// dkBluePickUpBarrel.addFrame(dkSprites.getSubImage(209, 191, 16,
			// 16), 125);
			// dkBlueBarrelRollDown.addFrame(dkSprites.getSubImage(209, 191, 16,
			// 16), 125);
			// dkBlueFire.addFrame(dkSprites.getSubImage(209, 191, 16, 16),
			// 125);
			
		} catch (SlickException e) {
		}
		
	}
	
}