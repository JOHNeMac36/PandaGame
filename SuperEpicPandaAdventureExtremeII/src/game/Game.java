package game;

import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Formatter;

import org.newdawn.slick.Animation;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Image;
import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.state.StateBasedGame;

public class Game extends StateBasedGame {

	public static int song;
	public static int standardFPS = 60;
	public static final int splash = -1, menu = 0, roam = -99, lvl01 = 1, lvl02 = 2, lvl03 = 3, lvl04 = 4, lvlLava = 99,
			lvlBoss = 100;
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
	public static Animation SmarioPandaDownFallR, SmarioPandaDownFallL, SmarioPandaDownBigFallR,
			SmarioPandaDownBigFallL;
	public static Animation SmarioPandaBigStillL, SmarioPandaBigWalkLeft, SmarioPandaBigJumpL;

	public static Animation FmarioPandaStillR, FmarioPandaWalkRight, FmarioPandaJumpR, FmarioPandaDown;
	public static Animation FmarioPandaStillL, FmarioPandaWalkLeft, FmarioPandaJumpL;
	public static Animation FmarioPandaBigStillR, FmarioPandaBigWalkRight, FmarioPandaBigJumpR;
	public static Animation FmarioPandaDownR, FmarioPandaDownL, FmarioPandaBigDownR, FmarioPandaBigDownL;
	public static Animation FmarioPandaDownFallR, FmarioPandaDownFallL, FmarioPandaDownBigFallR,
			FmarioPandaDownBigFallL;
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
	public static Animation jmStillRight, jmStillLeft, jmWalkRight, jmWalkLeft, jmClimb, jmPushUp, jmStillForward,
			jmDead;
	public static Animation HjmStillRight, HjmStillLeft, HjmWalkRight, HjmWalkLeft, jmJumpR, jmJumpL;

	public static Animation invader1, invader2, invader3, ufo, invaderExplosion, player, playerExplosion, invaderShot,
			playerShot;
	public static boolean pet1Found, pet2Found, pet3Found, pet4Found;
	public static char charLock1, charLock2, charLock3, charLock4;
	public static boolean isMusicOn;
	public static GameContainer gc;
	public static Image playNow, exitGame, menuScene, title, musicOn, musicOff, confetti, marioTitle, marioCursor;
	public static Music menuMusicIntro, menuMusicLoop, pollyWolly;
	public static Music marioTheme, marioUnderworld, marioCastle, marioStarman, marioLvlComplete, marioCastleComplete,
			marioDead, marioGameOver, marioHurryUp;
	public static Sound pandaPunchInPuss, sadPanda, itsPanda, pandaIcecream, marioBump, marioCoin, marioFireball,
			marioJump, marioKick, mario1Up, marioPause, marioPowerUp, marioStageClear, marioFlag, marioApearingPowerUp,
			marioBlockBreak, marioPowerDown, marioStomp;
	public static Music metal01Intro, metal01Loop, metal02Intro, metal02Loop, metal03Intro, metal03Loop, metal04Intro,
			metal04Loop, metal05Intro, metal05Loop, metal06Intro, metal06Loop, metal07Intro, metal07Loop, metal08Intro,
			metal08Loop, metal09Intro, metal09Loop, metal10Intro, metal10Loop, metal11Intro, metal11Loop;
	public static Sound dkBackground;
	public static Sound dkDeath, dkHammer, dkHowHighMusic, dkIntro, dkItemGet, dkJump, dkJumpBarrel, dkWin;
	public static Music dkWalking;
	public static String gamename = "Super Epic Panda Adventure Extreme 2!";
	public static Sound invaderMove1, invaderMove2, invaderMove3, invaderMove4, shipExplosion, playerShoot,
			invaderShipExplosion, ufoSound;
	public static final int len = 30, wid = 28, xSpace = wid + 1, ySpace = len + 1;

	public static Animation lmStill, lmDodgeR, lmDodgeL, lmSheild, lmJabL, lmJabR, lmUpperCutL, lmUpperCutR, lmHitL,
			lmHitR, lmLose, lmWin, lmFatigue;
	public static Animation lmCutScene1, lmCutScene2, lmCutScene3;

	public static Animation vkStill, vkDodgeR, vkDodgeL, vkSheild, vkJab, vkUpperCut, vkHitLow, vkHitHighL, vkHitHighR;
	public static Animation vkCutScene1, vkCutScene2, vkCutScene3;

	public static Animation rmStill, rmWalk, rmFight, rmCount, rmKO, rmTKO, rmSatanWin, rmYouWin;

	public static Music panda;
	public static AppGameContainer appgc;
	// end of attributes
	public static Animation pwrCoin, pwrFlower, pwrMush, pwrStar;

	public static Font font;
	public static TrueTypeFont ttFont;

	public static void main(String[] args) {

		try {
			appgc = new AppGameContainer(new Game(gamename));
			appgc.setDisplayMode(640, 640, false);
			appgc.setShowFPS(true);
			appgc.setTargetFrameRate(standardFPS);
			appgc.setUpdateOnlyWhenVisible(false);
			appgc.setSmoothDeltas(false);
			appgc.start();

		} catch (SlickException e) {
		}
	}

	public Game(String gamename) {
		super(gamename);
		this.addState(new Splash(splash));
	}

	@Override
	public void initStatesList(GameContainer gc) throws SlickException {
		// try {
		// GraphicsEnvironment ge =
		// GraphicsEnvironment.getLocalGraphicsEnvironment();
		// ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new
		// File("res/po.ttf")));
		// } catch (IOException | FontFormatException e) {
		// System.exit(0);
		// }
		// font = new Font("Punch-Out!! NES", Font.PLAIN, 20);
		// ttFont = new TrueTypeFont(font, false);
		//
		Image blocks = new Image("res/pngs/marioBlocks.png");

		bBlockBreak = new Animation();
		bBlockBreak.addFrame(blocks.getSubImage(1, 65, 90, 90), 150);
		bBlockBreak.addFrame(blocks.getSubImage(81, 65, 90, 90), 150);
		bBlockBreak.addFrame(blocks.getSubImage(192, 65, 90, 90), 300);
		bBlockBreak.addFrame(blocks.getSubImage(1, 1, 1, 1), 100000);

		try {
			this.getState(splash).init(gc, this);
			this.enterState(splash);

		} catch (SlickException e) {
		}

	}

	public static void initSounds1() {
		try {
			pandaPunchInPuss = new Sound("res/oggs/PandaPunch.ogg");
			sadPanda = new Sound("res/oggs/Sad Panda.ogg");
			itsPanda = new Sound("res/oggs/itsPanda.ogg");
			pandaIcecream = new Sound("res/oggs/pandaIcecream.ogg");
			pollyWolly = new Music("res/oggs/music.ogg");
		} catch (SlickException e) {
		}
	}

	public static void initSounds2() {
		try {
			marioTheme = new Music("res/oggs/mario01-ThemeSong.ogg");
			marioUnderworld = new Music("res/oggs/mario02-underworld.ogg");
			marioCastle = new Music("res/oggs/mario04-castle.ogg");
			marioStarman = new Music("res/oggs/mario05-starman.ogg");
			marioLvlComplete = new Music("res/oggs/mario06-level-complete.ogg");
			marioCastleComplete = new Music("res/oggs/mario07-castle-complete.ogg");
			marioDead = new Music("res/oggs/mario08-you-re-dead.ogg");
			marioGameOver = new Music("res/oggs/mario09-game-over.ogg");
			marioHurryUp = new Music("res/oggs/mario14-hurry-underground-.ogg");
		} catch (SlickException e) {
		}
	}

	public static void initSounds3() {
		try {
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
		} catch (SlickException e) {
		}
	}

	public static void initSounds4() {
		try {
			metal11Intro = new Music("res/oggs/Metal11Intro.ogg");
			metal11Loop = new Music("res/oggs/Metal11Loop.ogg");

			DateFormat df = new SimpleDateFormat("dd");
			Date date = new Date();
			song = Integer.parseInt(df.format(date));
			System.out.println("res/oggs/metal" + String.format("%02d", song % 11 + 1) + "Intro.ogg");
			Game.menuMusicIntro = new Music("res/oggs/Metal" + String.format("%02d", song % 11 + 1) + "Intro.ogg");

			Game.menuMusicLoop = new Music("res/oggs/Metal" + String.format("%02d", song % 11 + 1) + "Loop.ogg");

		} catch (SlickException e) {
		}
	}

	public static void initSounds5() {
		try {
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

	public static void initSounds6() {
		try {
			invaderMove1 = new Sound("res/oggs/fastinvader1.wav");
			invaderMove2 = new Sound("res/oggs/fastinvader2.wav");
			invaderMove3 = new Sound("res/oggs/fastinvader3.wav");
			invaderMove4 = new Sound("res/oggs/fastinvader4.wav");
			shipExplosion = new Sound("res/oggs/explosion.ogg");
			playerShoot = new Sound("res/oggs/shoot.ogg");
			invaderShipExplosion = new Sound("res/oggs/invaderkilled.ogg");
			ufoSound = new Sound("res/oggs/ufo_highpitch.wav");

			panda = new Music("res/oggs/panda.ogg");
		} catch (SlickException e) {
		}
	}

	public static void initImages() {
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
		metal11Intro.setVolume(1);
		metal11Loop.setVolume(1);
	}

	public static void initAnimations() {
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
			panda.setFilter(Image.FILTER_NEAREST);
			sD = panda.getSubImage(xSpace, 0 * ySpace, wid, len);
			sR = panda.getSubImage(xSpace, 1 * ySpace, wid, len);
			sL = panda.getSubImage(xSpace, 1 * ySpace, wid, len).getFlippedCopy(true, false);
			sU = panda.getSubImage(xSpace, 2 * ySpace, wid, len);

			for (int x = 0; x < 3; x++) {
				pandaWalkDown.addFrame(panda.getSubImage(x * xSpace, 0 * ySpace, wid, len), 150);
				pandaWalkRight.addFrame(panda.getSubImage(x * xSpace, 1 * ySpace, wid, len), 150);
				pandaWalkLeft.addFrame(panda.getSubImage(x * xSpace, 1 * ySpace, wid, len).getFlippedCopy(true, false),
						150);
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
			pen.setFilter(Image.FILTER_NEAREST);
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
					if (Y == 0)
						penguinWalkDown.addFrame(pen.getSubImage(X, Y, 32, 32), 150);

					if (Y == 32)
						penguinWalkLeft.addFrame(pen.getSubImage(X, Y, 32, 32), 150);

					if (Y == 64)
						penguinWalkRight.addFrame(pen.getSubImage(X, Y, 32, 32), 150);

					if (Y == 96)
						penguinWalkUp.addFrame(pen.getSubImage(X, Y, 32, 32), 150);

				}
			}

			// 4 characters
			Image chars = new Image("res/sprites/chars.png");
			chars.setFilter(Image.FILTER_NEAREST);
			int charsWid = 32, charsLen = 32, X, Y;

			X = 0;
			Y = 0;
			char1StillDown.addFrame(chars.getSubImage(X * charsWid + X, Y * charsLen + Y, charsWid, charsLen), 150);
			Y = 1;
			char1StillRight.addFrame(chars.getSubImage(X * charsWid + X, Y * charsLen + Y, charsWid, charsLen), 150);
			char1StillLeft.addFrame(chars.getSubImage(X * charsWid + X, Y * charsLen + Y, charsWid, charsLen)
					.getFlippedCopy(true, false), 150);
			Y = 2;
			char1StillUp.addFrame(chars.getSubImage(X * charsWid + X, Y * charsLen + Y + 1, charsWid, charsLen), 150);
			// end char1

			X = 1;
			Y = 0;
			char2StillDown.addFrame(chars.getSubImage(X * charsWid + X, Y * charsLen + Y, charsWid, charsLen), 150);
			Y = 1;
			char2StillRight.addFrame(chars.getSubImage(X * charsWid + X, Y * charsLen + Y, charsWid, charsLen), 150);
			char2StillLeft.addFrame(chars.getSubImage(X * charsWid + X, Y * charsLen + Y, charsWid, charsLen)
					.getFlippedCopy(true, false), 150);
			Y = 2;
			char2StillUp.addFrame(chars.getSubImage(X * charsWid + X, Y * charsLen + Y + 1, charsWid, charsLen), 150);
			// end char2

			X = 2;
			Y = 0;
			char3StillDown.addFrame(chars.getSubImage(X * charsWid + X, Y * charsLen + Y, charsWid, charsLen), 150);
			Y = 1;
			char3StillRight.addFrame(chars.getSubImage(X * charsWid + X, Y * charsLen + Y, charsWid, charsLen), 150);
			char3StillLeft.addFrame(chars.getSubImage(X * charsWid + X, Y * charsLen + Y, charsWid, charsLen)
					.getFlippedCopy(true, false), 150);
			Y = 2;
			char3StillUp.addFrame(chars.getSubImage(X * charsWid + X, Y * charsLen + Y + 1, charsWid, charsLen), 150);
			// end char3
			X = 4;
			Y = 0;
			char4StillDown.addFrame(chars.getSubImage(X * charsWid + X, Y * charsLen + Y, charsWid, charsLen), 150);
			Y = 1;
			char4StillRight.addFrame(chars.getSubImage(X * charsWid + X, Y * charsLen + Y, charsWid, charsLen), 150);
			char4StillLeft.addFrame(chars.getSubImage(X * charsWid + X, Y * charsLen + Y, charsWid, charsLen)
					.getFlippedCopy(true, false), 150);
			Y = 2;
			char4StillUp.addFrame(chars.getSubImage(X * charsWid + X, Y * charsLen + Y + 1, charsWid, charsLen), 150);
			// end char4

			// marioPanda
			Image marioPanda = new Image("res/sprites/marioPanda.png");
			marioPanda.setFilter(Image.FILTER_NEAREST);
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
			SmarioPandaBigJumpL.addFrame(marioPanda.copy().getSubImage(86, 141, 16, 32).getFlippedCopy(true, false),
					150);

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
			FmarioPandaBigJumpL.addFrame(marioPanda.copy().getSubImage(86, 71, 16, 32).getFlippedCopy(true, false),
					150);

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
			mush.setFilter(Image.FILTER_NEAREST);
			mushroom = new Animation();
			mushroom.addFrame(mush.getSubImage(1, 1, 16, 16), 150);
			mushroom.addFrame(mush.getSubImage(18, 1, 16, 16), 150);
			mushroomDead = new Animation();
			mushroomDead.addFrame(mush.getSubImage(35, 1, 16, 16), 150);

			// blocks
			qBlock = new Animation();
			qBlockDead = new Animation();
			bBlock = new Animation();

			Image blocks = new Image("res/pngs/marioBlocks.png");
			// bBlockBreak = new Animation();
			// bBlockBreak.addFrame(blocks.getSubImage(1, 65, 90, 90), 150);
			// bBlockBreak.addFrame(blocks.getSubImage(81, 65, 90, 90), 150);
			// bBlockBreak.addFrame(blocks.getSubImage(192, 65, 90, 90), 300);
			// bBlockBreak.addFrame(blocks.getSubImage(1, 1, 1, 1), 100000);
			//

			blocks.setFilter(Image.FILTER_NEAREST);
			bBlock.addFrame(blocks.getSubImage(1, 1, 40, 40), 150);
			qBlockDead.addFrame(blocks.getSubImage(42, 1, 40, 40), 150);

			qBlock.addFrame(blocks.getSubImage(83, 1, 40, 40), 270 * 2);
			qBlock.addFrame(blocks.getSubImage(124, 1, 40, 40), 200);
			qBlock.addFrame(blocks.getSubImage(165, 1, 40, 40), 200);

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
			pwrUps.setFilter(Image.FILTER_NEAREST);
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
			fireballs.setFilter(Image.FILTER_NEAREST);
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
			dkSprites.setFilter(Image.FILTER_NEAREST);
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

			invader1 = new Animation();
			invader2 = new Animation();
			invader3 = new Animation();
			ufo = new Animation();
			invaderExplosion = new Animation();
			player = new Animation();
			playerExplosion = new Animation();
			invaderShot = new Animation();
			playerShot = new Animation();
			Image si = new Image("res/sprites/SpaceInvadersSprites.png");

			invader1.addFrame(si.getSubImage(21, 3, 12, 8), 1);
			invader1.addFrame(si.getSubImage(36, 3, 12, 8), 1);

			invader2.addFrame(si.getSubImage(51, 3, 11, 8), 1);
			invader2.addFrame(si.getSubImage(65, 3, 11, 8), 1);

			invader3.addFrame(si.getSubImage(79, 3, 8, 8), 1);
			invader3.addFrame(si.getSubImage(91, 3, 8, 8), 1);

			ufo.addFrame(si.getSubImage(2, 4, 16, 7), 1);

			invaderExplosion.addFrame(si.getSubImage(102, 3, 13, 8), 1);

			player.addFrame(si.getSubImage(36, 18, 13, 8), 1);

			playerExplosion.addFrame(si.getSubImage(52, 18, 16, 8), 250);
			playerExplosion.addFrame(si.getSubImage(69, 18, 16, 8), 2500);

			invaderShot.addFrame(si.getSubImage(100, 19, 3, 7), 25);
			invaderShot.addFrame(si.getSubImage(105, 19, 3, 7), 25);

			playerShot.addFrame(si.getSubImage(31, 21, 1, 4), 2500);

			invader1.setAutoUpdate(false);
			invader2.setAutoUpdate(false);
			invader3.setAutoUpdate(false);
			ufo.setAutoUpdate(false);

			lmStill = new Animation();
			lmDodgeR = new Animation();
			lmDodgeL = new Animation();
			lmSheild = new Animation();
			lmJabL = new Animation();
			lmJabR = new Animation();
			lmUpperCutL = new Animation();
			lmUpperCutR = new Animation();
			lmHitL = new Animation();
			lmHitR = new Animation();
			lmLose = new Animation();
			lmWin = new Animation();
			lmFatigue = new Animation();
			lmCutScene1 = new Animation();
			lmCutScene2 = new Animation();
			lmCutScene3 = new Animation();

			vkStill = new Animation();
			vkDodgeR = new Animation();
			vkDodgeL = new Animation();
			vkSheild = new Animation();
			vkJab = new Animation();
			vkUpperCut = new Animation();
			vkHitLow = new Animation();
			vkHitHighL = new Animation();
			vkHitHighR = new Animation();
			vkCutScene1 = new Animation();
			vkCutScene2 = new Animation();
			vkCutScene3 = new Animation();

			Image lm = new Image("res/tilesets/PunchOutTileset/lm.png");
			lm.setFilter(Image.FILTER_NEAREST);
			Image vk = new Image("res/tilesets/PunchOutTileset/vk.png");
			vk.setFilter(Image.FILTER_NEAREST);

			int w = 31, h = 81;
			int punchFrames = 100;

			lmDodgeL.addFrame(lm.getSubImage(w, h, w, h).getFlippedCopy(false, false), 250);
			lmDodgeL.addFrame(lm.getSubImage(2 * w, h, w, h).getFlippedCopy(false, false), 250);

			lmDodgeL.setAutoUpdate(false);

			lmDodgeR.addFrame(lm.getSubImage(w, h, w, h).getFlippedCopy(true, false), 250);
			lmDodgeR.addFrame(lm.getSubImage(2 * w, h, w, h).getFlippedCopy(true, false), 250);
			lmDodgeR.setAutoUpdate(false);

			lmStill.addFrame(lm.getSubImage(0, 0, 31, 81), 250);
			lmStill.addFrame(lm.getSubImage(w, 0, 31, h), 250);

			lmSheild.addFrame(lm.getSubImage(w, 2 * h, w, h), 250);

			lmJabL.addFrame(lm.getSubImage(0, 3 * h, w, h), punchFrames);
			lmJabL.addFrame(lm.getSubImage(w, 3 * h, w, h), punchFrames);
			lmJabL.addFrame(lm.getSubImage(2 * w, 3 * h, w, h), punchFrames);
			lmJabL.addFrame(lm.getSubImage(w, 3 * h, w, h), punchFrames);
			lmJabL.addFrame(lm.getSubImage(0, 3 * h, w, h), punchFrames);

			lmJabL.setAutoUpdate(false);
			lmJabL.setLooping(false);

			lmJabR.addFrame(lm.getSubImage(0, 3 * h, w, h).getFlippedCopy(true, false), punchFrames);
			lmJabR.addFrame(lm.getSubImage(w, 3 * h, w, h).getFlippedCopy(true, false), punchFrames);
			lmJabR.addFrame(lm.getSubImage(2 * w, 3 * h, w, h).getFlippedCopy(true, false), punchFrames);
			lmJabR.addFrame(lm.getSubImage(w, 3 * h, w, h).getFlippedCopy(true, false), punchFrames);
			lmJabR.addFrame(lm.getSubImage(0, 3 * h, w, h).getFlippedCopy(true, false), punchFrames);

			lmJabR.setAutoUpdate(false);
			lmJabR.setLooping(false);

			lmUpperCutL.addFrame(lm.getSubImage(0, 4 * h, w, h), punchFrames);
			lmUpperCutL.addFrame(lm.getSubImage(w, 4 * h, w, h), punchFrames);
			lmUpperCutL.addFrame(lm.getSubImage(2 * w, 4 * h, w, h), punchFrames);
			lmUpperCutL.addFrame(lm.getSubImage(w, 4 * h, w, h), punchFrames);
			lmUpperCutL.addFrame(lm.getSubImage(0, 4 * h, w, h), punchFrames);

			lmUpperCutL.setAutoUpdate(false);
			lmUpperCutL.setLooping(false);

			lmUpperCutR.addFrame(lm.getSubImage(0, 4 * h, w, h).getFlippedCopy(true, false), punchFrames);
			lmUpperCutR.addFrame(lm.getSubImage(w, 4 * h, w, h).getFlippedCopy(true, false), punchFrames);
			lmUpperCutR.addFrame(lm.getSubImage(2 * w, 4 * h, w, h).getFlippedCopy(true, false), punchFrames);
			lmUpperCutR.addFrame(lm.getSubImage(w, 4 * h, w, h).getFlippedCopy(true, false), punchFrames);
			lmUpperCutR.addFrame(lm.getSubImage(0, 4 * h, w, h).getFlippedCopy(true, false), punchFrames);

			lmUpperCutR.setAutoUpdate(false);
			lmUpperCutR.setLooping(false);

			lmHitL.addFrame(lm.getSubImage(0, 8 * h, w, h).getFlippedCopy(false, false), 250);
			lmHitL.addFrame(lm.getSubImage(w, 8 * h, w, h).getFlippedCopy(false, false), 2500000);

			lmHitR.addFrame(lm.getSubImage(0, 8 * h, w, h).getFlippedCopy(true, false), 250);
			lmHitR.addFrame(lm.getSubImage(w, 8 * h, w, h).getFlippedCopy(true, false), 2500000);

			lmLose.addFrame(lm.getSubImage(3 * w, 9 * h, w, h).getFlippedCopy(false, false), 2500);

			lmWin.addFrame(lm.getSubImage(0, 10 * h, w, h).getFlippedCopy(false, false), 2500);
			lmWin.addFrame(lm.getSubImage(w, 10 * h, w, h).getFlippedCopy(false, false), 2500);

			lmFatigue.addFrame(lm.getSubImage(0, 6 * h, w, h).getFlippedCopy(false, false), 150);
			lmFatigue.addFrame(lm.getSubImage(w, 6 * h, w, h).getFlippedCopy(false, false), 150);

			w = 80;
			h = 68;
			int o1 = 973, o2 = 3;

			lmCutScene1.addFrame(lm.getSubImage(0 * (w + o2), h * 0 + o1 + o2, w, h), 250);
			lmCutScene1.addFrame(lm.getSubImage(1 * (w + o2), h * 0 + o1 + o2, w, h), 250);
			lmCutScene1.addFrame(lm.getSubImage(2 * (w + o2), h * 0 + o1 + o2, w, h), 250);
			lmCutScene1.addFrame(lm.getSubImage(3 * (w + o2), h * 0 + o1 + o2, w, h), 250);

			lmCutScene2.addFrame(lm.getSubImage(0 * (w + o2), h + o1 + o2, w, h), 250);
			lmCutScene2.addFrame(lm.getSubImage(1 * (w + o2), h + o1 + o2, w, h), 250);
			lmCutScene2.addFrame(lm.getSubImage(2 * (w + o2), h + o1 + o2, w, h), 250);
			lmCutScene2.addFrame(lm.getSubImage(3 * (w + o2), h + o1 + o2, w, h), 250);

			lmCutScene3.addFrame(lm.getSubImage(0 * (w + o2), 2 * h + o1 + o2, w, h), 250);
			lmCutScene3.addFrame(lm.getSubImage(1 * (w + o2), 2 * h + o1 + o2, w, h), 250);
			lmCutScene3.addFrame(lm.getSubImage(2 * (w + o2), 2 * h + o1 + o2, w, h), 250);
			vkCutScene3.addFrame(vk.getSubImage(3 * (w + o2), 2 * h + o1 + o2, w, h), 250);

			w = 45;
			h = 104;

			vkStill.addFrame(vk.getSubImage(0, h, w, h), 2 * punchFrames);
			vkStill.addFrame(vk.getSubImage(w, h, w, h), 2 * punchFrames);
			vkStill.addFrame(vk.getSubImage(w * 2, h, w, h), 2 * punchFrames);
			vkStill.addFrame(vk.getSubImage(w * 3, h, w, h), 2 * punchFrames);

			vkDodgeL.addFrame(vk.getSubImage(0, 11 * h, w, h), punchFrames * 2);

			vkDodgeL.setAutoUpdate(false);

			vkDodgeR.addFrame(vk.getSubImage(0, 11 * h, w, h).getFlippedCopy(true, false), punchFrames * 2);

			vkDodgeR.setAutoUpdate(false);

			vkJab.addFrame(vk.getSubImage(2 * w, 4 * h, w, h), punchFrames * 3);
			vkJab.addFrame(vk.getSubImage(3 * w, 4 * h, w, h), punchFrames * 3);
			vkJab.addFrame(vk.getSubImage(4 * w, 4 * h, w, h), punchFrames * 3);
			vkJab.addFrame(vk.getSubImage(5 * w, 4 * h, w, h), punchFrames * 2);
			vkJab.addFrame(vk.getSubImage(4 * w, 4 * h, w, h), punchFrames * 3);
			vkJab.addFrame(vk.getSubImage(3 * w, 4 * h, w, h), punchFrames * 3);
			vkJab.addFrame(vk.getSubImage(2 * w, 4 * h, w, h), punchFrames * 3);

			vkJab.setAutoUpdate(false);
			vkJab.setLooping(false);

			vkUpperCut.addFrame(vk.getSubImage(0 * w, 5 * h, w, h), punchFrames * 4);
			vkUpperCut.addFrame(vk.getSubImage(1 * w, 5 * h, w, h), punchFrames * 4);
			vkUpperCut.addFrame(vk.getSubImage(2 * w, 5 * h, w, h), punchFrames * 4);
			vkUpperCut.addFrame(vk.getSubImage(3 * w, 5 * h, w, h), punchFrames * 4);
			vkUpperCut.setAutoUpdate(false);
			vkUpperCut.setLooping(false);

			vkHitLow.addFrame(vk.getSubImage(3 * w, 8 * h, w, h), punchFrames);

			vkHitHighL.addFrame(vk.getSubImage(1 * w, 8 * h, w, h), punchFrames);

			vkHitHighR.addFrame(vk.getSubImage(2 * w, 8 * h, w, h), punchFrames);

			w = 80;
			h = 77;
			o1 = 1280;
			o2 = 3;

			vkCutScene1.addFrame(vk.getSubImage(0 * (w + o2), h * 0 + o1 + o2, w, h), punchFrames);
			vkCutScene1.addFrame(vk.getSubImage(1 * (w + o2), h * 0 + o1 + o2, w, h), punchFrames);
			vkCutScene1.addFrame(vk.getSubImage(2 * (w + o2), h * 0 + o1 + o2, w, h), punchFrames);
			vkCutScene1.addFrame(vk.getSubImage(3 * (w + o2), h * 0 + o1 + o2, w, h), punchFrames);

			vkCutScene2.addFrame(vk.getSubImage(0 * (w + o2), h + o1 + o2, w, h), punchFrames);
			vkCutScene2.addFrame(vk.getSubImage(1 * (w + o2), h + o1 + o2, w, h), punchFrames);
			vkCutScene2.addFrame(vk.getSubImage(2 * (w + o2), h + o1 + o2, w, h), punchFrames);
			vkCutScene2.addFrame(vk.getSubImage(3 * (w + o2), h + o1 + o2, w, h), punchFrames);

			vkCutScene3.addFrame(vk.getSubImage(0 * (w + o2), 2 * h + o1 + o2, w, h), punchFrames);
			vkCutScene3.addFrame(vk.getSubImage(1 * (w + o2), 2 * h + o1 + o2, w, h), punchFrames);
			vkCutScene3.addFrame(vk.getSubImage(2 * (w + o2), 2 * h + o1 + o2, w, h), punchFrames);
			vkCutScene3.addFrame(vk.getSubImage(3 * (w + o2), 2 * h + o1 + o2, w, h), punchFrames);

			w = 62;
			h = 48;
			int x = 37;
			Image rm = new Image("res/tilesets/PunchOutTileset/rm.png");

			rmWalk = new Animation();
			rmFight = new Animation();
			rmCount = new Animation();
			rmKO = new Animation();
			rmTKO = new Animation();
			rmSatanWin = new Animation();
			rmYouWin = new Animation();
			rmStill = new Animation();

			rm.setFilter(Image.FILTER_NEAREST);
			rmStill.addFrame(rm.getSubImage(0, 0, w, h), 250);

			rmWalk.addFrame(rm.getSubImage(0, 0, w, h), 150);
			rmWalk.addFrame(rm.getSubImage(w, 0, w, h), 150);

			rmFight.addFrame(rm.getSubImage(0, 3 * h, w, h), 250);

			rmCount.addFrame(rm.getSubImage(3 * w, h, w, h), 1000);
			rmCount.addFrame(rm.getSubImage(4 * w, h, w, h), 1000);
			rmCount.addFrame(rm.getSubImage(5 * w, h, w, h), 1000);
			rmCount.addFrame(rm.getSubImage(6 * w, h, w, h), 1000);
			rmCount.addFrame(rm.getSubImage(0 * w, 2 * h, w, h), 1000);
			rmCount.addFrame(rm.getSubImage(1 * w, 2 * h, w, h), 1000);
			rmCount.addFrame(rm.getSubImage(2 * w, 2 * h, w, h), 1000);
			rmCount.addFrame(rm.getSubImage(3 * w, 2 * h, w, h), 1000);
			rmCount.addFrame(rm.getSubImage(4 * w, 2 * h, w, h), 1000);
			rmCount.addFrame(rm.getSubImage(5 * w, 2 * h, w, h), 1000);

			rmKO.addFrame(rm.getSubImage(2 * w, 4 * h, w + x, h), 1000);

			rmTKO.addFrame(rm.getSubImage(2 * w, 4 * h, w + x, h), 1000);

			rmSatanWin.addFrame(rm.getSubImage(w, 4 * h, w, h), 1000);
			rmYouWin.addFrame(rm.getSubImage(0, 5 * h, w, h), 1000);

		} catch (SlickException e) {
		}
	}
}