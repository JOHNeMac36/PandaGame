package game;

import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Music;
import org.newdawn.slick.MusicListener;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.Animation;
import org.newdawn.slick.Color;

public class Splash extends BasicGameState implements MusicListener {
	Image splash;
	private int song, loadingLength = 0;
	private float steps = 10;
	int count = 0;
	String message = "";
	
	public Splash(int i) {
		song = 0;
	}
	
	@Override
	public int getID() {
		return Game.splash;
	}
	
	@Override
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
		splash = new Image("res/pngs/menuScene.png");
		
	}
	
	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
		g.drawImage(splash, 0, 0);
		// x, y, wid, height
		g.setColor(Color.blue);
		g.drawString(message + " (" + twoDecPrecent(count / steps) + "%)", 400, 558);
		g.drawRect(20, 578, 600, 58);
		g.setColor(Color.red);
		g.fillRect(20, 578, loadingLength, 58);
	}
	
	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
		count++;
		loadingLength = (int) ((float) count / steps * 600f);
		
		switch (count) {
			case 1:
				Game.pet1Found = true;
				Game.pet2Found = true;
				Game.pet3Found = true;
				Game.pet4Found = true;
				message = "Loading Fonts";
				break;
			case 2:
				Game.charLock1 = '6';
				Game.charLock2 = '9';
				Game.charLock3 = '6';
				Game.charLock4 = '9';
				
				try {
					GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
					ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("res/po.ttf")));
				} catch (IOException | FontFormatException e) {
					System.exit(0);
				}
				Game.font = new Font("Punch-Out!! NES", Font.PLAIN, 20);
				Game.ttFont = new TrueTypeFont(Game.font, false);
				message = "Loading Animations";
				break;
			case 3:
				Game.initAnimations();
				message = "Loading Sounds";
				break;
			case 4:
				Game.initSounds1();
				message = "Loading Sounds";
				break;
			case 5:
				Game.initSounds2();
				message = "Loading Sounds";
				break;
			case 6:
				Game.initSounds3();
				message = "Loading Sounds";
				break;
			case 7:
				Game.initSounds4();
				message = "Loading Sounds";
				break;
			case 8:
				Game.initSounds5();
				message = "Loading Sounds";
				break;
			case 9:
				Game.initSounds6();
				message = "Loading Images";
				break;
			case 10:
				Game.initImages();
				message = "Loading Levels";
				break;
			case 11:
				sbg.addState(new MainMenu(Game.menu));
				sbg.addState(new Roam(Game.roam));
				sbg.addState(new Level01(Game.lvl01));
				sbg.addState(new Level02(Game.lvl02));
				sbg.addState(new Level03(Game.lvl03));
				sbg.addState(new Level04(Game.lvl04));
				sbg.addState(new Lava(Game.lvlLava));
				sbg.addState(new LevelBoss(Game.lvlBoss));
				
				sbg.getState(Game.menu).init(gc, sbg);
				sbg.getState(Game.roam).init(gc, sbg);
				sbg.getState(Game.lvl01).init(gc, sbg);
				sbg.getState(Game.lvl02).init(gc, sbg);
				sbg.getState(Game.lvl03).init(gc, sbg);
				sbg.getState(Game.lvl04).init(gc, sbg);
				sbg.getState(Game.lvlLava).init(gc, sbg);
				sbg.getState(Game.lvlBoss).init(gc, sbg);
				message = "Loading Menu";
				sbg.enterState(Game.menu);
				startMusic();
				Game.isMusicOn = true;
		}
	}
	
	private void startMusic() throws SlickException {
		Game.menuMusicIntro.addListener(this);
		Game.menuMusicIntro.play();
	}
	
	@Override
	public void musicEnded(Music m) {
		Game.menuMusicLoop.loop();
	}
	
	@Override
	public void musicSwapped(Music arg0, Music arg1) {
	}
	
	String twoDecPrecent(float f) {
		return (int) (f * 100f) + "";
	}
}
