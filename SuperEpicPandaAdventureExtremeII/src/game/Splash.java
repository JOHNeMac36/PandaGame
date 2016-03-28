package game;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Music;
import org.newdawn.slick.MusicListener;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.Color;

public class Splash extends BasicGameState implements MusicListener {
	Image splash;
	private int elapsedTime;
	private final int DELAY = 1000;
	private int song, loadingLength;
	
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
		g.drawRect(20, 578, 600, 58);
		g.setColor(Color.red);
		g.fillRect(20, 578, loadingLength, 58);
	}
	
	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
		elapsedTime += delta;
		
		if (elapsedTime <= DELAY) {
			loadingLength = (int) ((double) elapsedTime * 600) / DELAY;
		}
		if (elapsedTime > DELAY) {
			sbg.enterState(Game.menu);
			startMusic();
		}
	}
	
	private void startMusic() throws SlickException {
		
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
	
}
