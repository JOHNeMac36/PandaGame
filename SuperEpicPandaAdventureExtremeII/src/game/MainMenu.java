package game;

import org.lwjgl.input.*;
import org.newdawn.slick.*;
import org.newdawn.slick.state.*;

public class MainMenu extends BasicGameState implements MusicListener {

	int i;
	boolean playClicked = false;
	private static Input input;

	@Override
	public int getID() {
		return Game.menu;
	}

	public MainMenu(int menu) {
	}

	@Override
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
		input = gc.getInput();
		i = 0;
	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
		Game.menuScene.draw(0, 0);
		Game.title.draw(0, 0);
		// 200 , 40
		Game.playNow.draw(320, 300);
		Game.exitGame.draw(320, 350);
		if (Game.isMusicOn)
			Game.musicOn.draw(565, 565);
		else
			Game.musicOff.draw(565, 565);
		g.drawString(Mouse.getX() + " , " + Mouse.getY(), 0, 0);
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
		if (Game.isMusicOn) Game.unmuteAllMusic();
		else
			Game.muteAllMusic();
		i++;
		int posX = input.getMouseX();
		int posY = input.getMouseY();
		
		// play button
		if ((posX >= 320) && (posX <= 520) && (posY >= 300) && (posY <= 340)) {
			if (Mouse.isButtonDown(0)) {
				playClicked = true;
				Game.menuMusicIntro.stop();
				Game.menuMusicLoop.stop();
				Game.pollyWolly.loop();
				sbg.enterState(Game.roam);
			}
		}
		
		// toggle music
		// if ((posX >= 573) && (posX <= 627) && (posY >= 15) && (posY <= 67)) {
		if (input.isKeyPressed(Input.KEY_M)) {
			Game.isMusicOn = !Game.isMusicOn;
		}
		
		// exit button
		if ((posX >= 320) && (posX <= 520) && (posY >= 355) && (posY <= 395)) {
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
		Game.menuMusicIntro.addListener(this);
		Game.menuMusicIntro.play();

	}

}
