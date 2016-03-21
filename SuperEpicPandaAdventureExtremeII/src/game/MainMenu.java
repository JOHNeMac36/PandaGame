package game;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.lwjgl.input.*;
import org.newdawn.slick.*;
import org.newdawn.slick.state.*;

public class MainMenu extends BasicGameState implements MusicListener {
	
	int song, i;
	
	@Override
	public int getID() {
		return Game.menu;
	}
	
	public MainMenu(int menu) {
	}
	
	@Override
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
		i = 0;
	}
	
	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
		
		Game.menuScene.draw(0, 0);
		Game.title.draw(0, 0);
		// 200 , 40
		Game.playNow.draw(320, 300);
		Game.exitGame.draw(320, 350);
	}
	
	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
		i++;
		int posX = Mouse.getX();
		int posY = Mouse.getY();
		
		// play button
		if ((posX >= 320) && (posX <= 520) && (posY >= 300) && (posY <= 340)) {
			if (Mouse.isButtonDown(0)) {
				Level01.resetState();
				sbg.enterState(Game.roam);
				Level01.music.loop();
			}
		}
		// exit button
		if ((posX >= 320) && (posX <= 520) && (posY >= 245) && (posY <= 285)) {
			if (Mouse.isButtonDown(0)) {
				System.exit(0);
			}
		}
		
		if (i == 1) {
			startMenuMusic();
		}
	}
	
	@Override
	public void musicEnded(Music m) {
		Game.menuMusicLoop.loop();
	}
	
	@Override
	public void musicSwapped(Music arg0, Music arg1) {
	}
	
	public void startMenuMusic() throws SlickException {
		
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
	
}
