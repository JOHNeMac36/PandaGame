package game;

import java.util.Random;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Color;
import org.newdawn.slick.Font;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.tiled.TiledMap;

public class LevelBoss extends BasicGameState {

	final TiledMap map;
	Input input;
	boolean won;
	LittleMac panda;
	VonKaiser satan;
	RefereeMario mario;
	Random rand;
	Timer timer;
	TrueTypeFont font;
	public static float zoomFactor = 3f;
	boolean opening = true;
	float time = 0;
	final int overheadLayer;
	private boolean roundTransition = false;

	public LevelBoss(int i) throws SlickException {
		map = new TiledMap("res/maps/ring.tmx");
		overheadLayer = map.getLayerIndex("Overhead");
	}

	@Override
	public int getID() {
		// TODO Auto-generated method stub
		return Game.lvlBoss;
	}

	@Override
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
		input = Game.appgc.getInput();
		panda = new LittleMac(120f, 100f);
		satan = new VonKaiser(100f, 100f);
		mario = new RefereeMario(210f, 25f);
		rand = new Random();
		timer = new Timer(3, 0);
	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
		renderRing(gc, sbg, g);
		renderSatan(gc, sbg, g);
		renderMario(gc, sbg, g);
		renderPanda(gc, sbg, g);
		renderOverhead(gc, sbg, g);
		renderStats(gc, sbg, g);
		renderInfo(gc, sbg, g);
	}

	private void renderOverhead(GameContainer gc, StateBasedGame sbg, Graphics g) {
		// TODO Auto-generated method stub
		g.scale(zoomFactor, zoomFactor);
		map.render(0, 0, overheadLayer);
		g.scale(1f / zoomFactor, 1f / zoomFactor);
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int t) throws SlickException {
		time += t;
		if (roundTransition) {
		} else if (opening) {
			updateOpening(gc, sbg, t);
		} else {
			/*
			 * 
(idle animation)
                |
     [idle for "think" time]
  |       |        |               |
[jab]  [hook] [uppercut] [special (if under 50% HP, else go back to idle)]
                  |
          [go back to idle]

          [Stunned] ("Stun Amount" +1)
             			  |
        			wait 20 frames
             |                        |
[Attacked before 20 frames] [Not attacked before 20 frames]
        |                                  |
[ Is "Stunned amount" < 5?]           [go to "idle"]
   |                   |
[yes]                 [no]
   |                   |
[go to "Stunned"]   [go to "idle"]

			 */
			timer.updateTimer(t);
			// panda turn
			if(panda.stunAmount < 5) {
				//updateAnimation(panda);
				getPandaInput();
			}else {
				// stunned
				updateStunnedAnimation(panda);
			}
		}

	}

	private void updateAnimation(Fighter fighter, FighterState fs) {
		// TODO Auto-generated method stub
		
	}

	private void updateStunnedAnimation(Fighter fighter) {
		// TODO Auto-generated method stub
		
	}

	private void getPandaInput() {
		// TODO Auto-generated method stub
		
	}


	// supplementary methods
	private void checkEndOfRound(GameContainer gc, StateBasedGame sbg, int t) {
		// TODO Auto-generated method stub

	}

	private void checkWin(GameContainer gc, StateBasedGame sbg, int t) {
		// TODO Auto-generated method stub

	}

	private void updateOpening(GameContainer gc, StateBasedGame sbg, int t) {
		// TODO Auto-generated method stub
		if (time < 3000) {
			satan.x = 202f;
			satan.y = 15f;
			satan.satan = Game.vkStill;

			panda.x = 50f;
			panda.y = 150f;
			panda.panda = Game.lmStill;

			mario.x = 200f;
			mario.y = 115f;
			mario.mario = Game.rmStill;
		}
		if (time >= 3000 && time < 7000) {
			walkTo(satan, 100f, 65f, t, 30f, 15f);
			walkTo(panda, 105f, 100f, t, 15f, 15f);
			mario.mario = Game.rmWalk;
			walkTo(mario, 150f, 115f, t, 30f, 0f);

		}
		if (time >= 7000 && time < 8000) {
			mario.mario = Game.rmFight;

		}
		if (time >= 8000 && time < 10000) {
			mario.mario = Game.rmWalk;
			walkTo(mario, 500f, 115f, t, 60f, 0f);
		}
		if (time >= 10000) {
			opening = false;
		}
	}

	private void walkTo(Sprite sprite, float targetX, float targetY, int t, float velX, float velY) {
		// TODO Auto-generated method stub
		if (sprite.x == targetX)
			return;
		else {
			sprite.x += velX * ((sprite.x < targetX) ? t / 1000f : -t / 1000f);
			sprite.y += velY * ((sprite.y < targetY) ? t / 1000f : -t / 1000f);
			if (Math.abs(sprite.x - targetX) < Math.abs(velX * t / 1000f)) {
				sprite.x = targetX;
			}
			if (Math.abs(sprite.y - targetY) < Math.abs(velY * t / 1000f)) {
				sprite.y = targetY;
			}
		}
	}

	private void renderInfo(GameContainer gc, StateBasedGame sbg, Graphics g) {

	}

	private void renderMario(GameContainer gc, StateBasedGame sbg, Graphics g) {
		// TODO Auto-generated method stub
		g.scale(zoomFactor, zoomFactor);
		mario.mario.draw(mario.x, mario.y);
		g.scale(1f / zoomFactor, 1f / zoomFactor);

	}

	private void renderPanda(GameContainer gc, StateBasedGame sbg, Graphics g) {
		g.scale(zoomFactor, zoomFactor);
		panda.panda.draw(panda.x, panda.y);
		g.scale(1f / zoomFactor, 1f / zoomFactor);
	}

	private void renderRing(GameContainer gc, StateBasedGame sbg, Graphics g) {
		g.scale(zoomFactor, zoomFactor);
		map.render(0, 0);
		g.scale(1f / zoomFactor, 1f / zoomFactor);
	}

	private void renderSatan(GameContainer gc, StateBasedGame sbg, Graphics g) {
		g.scale(zoomFactor, zoomFactor);

		satan.satan.draw(satan.x, satan.y);

		g.scale(1f / zoomFactor, 1f / zoomFactor);

	}

	private void renderStats(GameContainer gc, StateBasedGame sbg, Graphics g) {
		g.scale(zoomFactor, zoomFactor);

		Color col = g.getColor();
		g.setColor(Color.black);
		g.fillRect(88, 16, 48 * (panda.health / panda.maxHealth), 7);
		g.fillRect(144, 16, 48 * (satan.health / satan.maxHealth), 7);
		g.scale(1f / zoomFactor, 1f / zoomFactor);

		Font f = g.getFont();
		g.setColor(Color.white);
		g.setFont(Game.ttFont);
		g.drawString(timer.getMinutes(), 208 * zoomFactor, 17 * zoomFactor);
		g.drawString(timer.getSeconds(), 224 * zoomFactor, 17 * zoomFactor);
		g.setColor(col);
		g.setFont(f);

	}

	private void resetPandaAnimations() {
		Game.lmJabL.restart();
		Game.lmJabR.restart();
		Game.lmUpperCutL.restart();
		Game.lmUpperCutR.restart();
		Game.lmDodgeL.restart();
		Game.lmDodgeR.restart();
	}

	private void resetSatanAnimations() {
		Game.vkJab.restart();
		Game.vkUpperCut.restart();
		Game.vkDodgeL.restart();
		Game.vkDodgeR.restart();

	}

}

abstract class Sprite {
	public float x, y;

	public Sprite(float x, float y) {
		this.x = x;
		this.y = y;
	}
}

class RefereeMario extends Sprite {
	public int count;
	public Animation mario;

	public RefereeMario(float x, float y) {
		super(x, y);
		mario = Game.rmStill;
	}
}

abstract class Fighter extends Sprite {
	public float health;
	public final float maxHealth = 100f;
	public float stunAmount = 0f;
	
	
	public Fighter(float x, float y) {
		super(x, y);
	}

	float getDuration(Animation a) {
		float duration = 0f;
		for (int i : a.getDurations()) {
			duration += i;
		}
		return duration;
	}
}

// class Attack {
// public float damage;
// // public float energy;
// public float animationTime;
// // public float attackWindUp;
// }

class LittleMac extends Fighter {
	public Animation panda;
	public boolean isRight, isLeft, isDown, isUp;

	public LittleMac(float x, float y) {
		super(x, y);
		panda = Game.lmStill;

		isRight = false;
		isLeft = false;
		isDown = false;
		isUp = false;
		health = super.maxHealth;
		// jabAnimationTime = getDuration(Game.lmJabL);
		// upperCutAnimationTime = getDuration(Game.lmUpperCutL);
	}

	public float getDuration(Animation a) {
		float duration = 0f;
		for (int i : a.getDurations()) {
			duration += i;
		}
		return duration;
	}

}

class VonKaiser extends Fighter {
	public Animation satan;

	public VonKaiser(float x, float y) {
		super(x, y);
		satan = Game.vkStill;
		health = super.maxHealth;
		// jabAnimationTime = getDuration(Game.vkJab);
		// upperCutAnimationTime = getDuration(Game.vkUpperCut);

	}

}

class Timer {
	private float minutes, seconds;
	public boolean timerDone = false;

	public Timer(int minutes, int seconds) {
		this.minutes = minutes;
		this.seconds = seconds;
	}

	public String getMinutes() {
		return "" + (int) minutes;
	}

	public String getSeconds() {
		return String.format("%02d", (int) seconds);
	}

	public boolean isDone() {
		return minutes == 0 && seconds == 0;
	}

	public void updateTimer(float delta) {
		seconds -= delta * 1f / 1000f;
		if (seconds < 0) {
			if (minutes < 1) {
				timerDone = true;
			} else {
				minutes--;
				seconds += 60;
			}
		}

	}
}

class FighterState {
	enum State{IDLE,JR,JL,UR,UL,DAMAGED,DOWN_FOR_COUNT,KNOCKED_OUT}
	public FighterState(State state) {
		
	}
}