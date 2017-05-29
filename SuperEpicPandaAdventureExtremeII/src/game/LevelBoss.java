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
			timer.updateTimer(t);
			sendAttacks();
			updatePandaMovement(gc, sbg, t);
			updateSatanMovement(gc, sbg, t);
			checkWin(gc, sbg, t);
			checkEndOfRound(gc, sbg, t);
			panda.attackCountdown -= t;
			satan.attackCountdown -= t;
		}

	}

	// supplementary methods
	private void sendAttacks() {
		if (panda.attackCountdown <= 0) {
			if (panda.attack.type != Attack.Type.NILL) {
				satan.health -= panda.attack.damage;
				satan.isHit = true;
				satan.attackCountdown = satan.attack.attackWindUp;

			}
		}
		if (satan.attackCountdown <= 0) {
			if (satan.attack.type != Attack.Type.NILL) {
				panda.health -= satan.attack.damage;
				panda.isHit = true;
				panda.attackCountdown = panda.attack.attackWindUp;
			}
		}
	}

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

	private void updatePandaMovement(GameContainer gc, StateBasedGame sbg, int t) {
		if (input.isKeyPressed(Input.KEY_X)) {
			if (input.isKeyDown(Input.KEY_UP)) {
				panda.attack = new Attack(Attack.Type.UR);
				panda.panda = Game.lmUpperCutR;
			} else {
				panda.attack = new Attack(Attack.Type.JR);
				panda.panda = Game.lmJabR;
			}
			panda.attackCountdown = panda.attack.attackWindUp;
		}
		if (input.isKeyPressed(Input.KEY_Z)) {

			if (input.isKeyDown(Input.KEY_UP)) {
				panda.attack = new Attack(Attack.Type.UL);
				panda.panda = Game.lmUpperCutL;
			} else {
				panda.attack = new Attack(Attack.Type.JL);
				panda.panda = Game.lmJabL;
			}
			panda.attackCountdown = panda.attack.attackWindUp;
		}
	}
	/*
	 * if (panda.isDown) { panda.panda = Game.lmFatigue; } else { // punchL if
	 * (input.isKeyPressed(Input.KEY_Z) && panda.coolDown <= 0 &&
	 * !panda.isPunching && panda.punchCoolDown <= 0) { //
	 * input.clearKeyPressedRecord(); satan.isHitL = true; panda.isAttacking=
	 * true; panda.punchCoolDown = LittleMac.jabAnimationTime; panda.coolDown =
	 * LittleMac.punchDelay; panda.panda = Game.lmJabL; if (panda.isUp) {
	 * panda.panda = Game.lmUpperCutL; panda.punchCoolDown =
	 * LittleMac.upperCutAnimationTime; satan.health -= panda.uppercutDamage; }
	 * 
	 * } else { // punchR if (input.isKeyPressed(Input.KEY_X) && panda.coolDown
	 * <= 0 && panda.punchCoolDown <= 0) { // input.clearKeyPressedRecord();
	 * satan.isHitR = true; panda.isPunching = true; panda.punchCoolDown =
	 * LittleMac.jabPunchTime; panda.coolDown = LittleMac.punchDelay;
	 * panda.panda = Game.lmJabR; if (panda.isUp) { panda.panda =
	 * Game.lmUpperCutR; panda.punchCoolDown = LittleMac.upperCutPunchTime;
	 * satan.health -= panda.uppercutDamage; } } else {
	 * 
	 * satan.isHitL = false; satan.isHitR = false;
	 * 
	 * } } if (input.isKeyPressed(Input.KEY_RIGHT)) { panda.panda =
	 * Game.lmDodgeR; panda.punchCoolDown = panda.getDuration(panda.panda);
	 * panda.isPunching = true; satan.health -= panda.jabDamage; } if
	 * (input.isKeyPressed(Input.KEY_LEFT)) { panda.panda = Game.lmDodgeL;
	 * panda.punchCoolDown = panda.getDuration(panda.panda); panda.isPunching =
	 * true; satan.health -= panda.jabDamage; } } } } }
	 */

	private void updateSatanMovement(GameContainer gc, StateBasedGame sbg, int t) {
		/*
		 * if (satan.isPunching) {
		 * 
		 * satan.satan.update(t);
		 * 
		 * satan.punchCoolDown -= t; satan.coolDown = VonKaiser.punchDelay; if
		 * (satan.punchCoolDown <= 0) satan.isPunching = false; } else { if
		 * (satan.isHitL || satan.isHitR) { satan.isPunching = false;
		 * satan.coolDown = VonKaiser.punchDelay; if (satan.isHitL) satan.satan
		 * = Game.vkHitHighL; else satan.satan = Game.vkHitHighR; } else {
		 * panda.coolDown -= t; resetSatanAnimations(); if (rand.nextInt(100) ==
		 * 5) { if (rand.nextBoolean()) { // jab satan.satan = Game.vkJab;
		 * satan.isAttacking= true; satan.punchCoolDown =
		 * satan.getDuration(satan.satan); panda.health -= satan.jabDamage;
		 * panda.isHitL = true; } else { // uppercut satan.satan =
		 * Game.vkUpperCut; satan.isPunching = true; satan.punchCoolDown =
		 * satan.getDuration(satan.satan); panda.health -= satan.uppercutDamage;
		 * panda.isHitR = true; } } } }
		 * 
		 * }
		 * 
		 * public static void resetState() {
		 * 
		 * }
		 * 
		 * }
		 */
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
	public float maxHealth = 100f;
	public float stamina;
	public float maxStamina = 100f;
	public float attackCountdown = 0f;
	public float punchDelay;
	public Attack attack;
	public float recoverTime;
	public boolean isHit = false;
	public boolean isKnockedDown = false;
	public boolean isRecovering = false;
	public boolean isAttacking = false;
	// public Attack[] pendingAttacks;

	public Fighter(float x, float y) {
		super(x, y);
		attack = new Attack(Attack.Type.NILL);
	}

	float getDuration(Animation a) {
		float duration = 0f;
		for (int i : a.getDurations()) {
			duration += i;
		}
		return duration;
	}

	private boolean isAttackable(Attack attack) {
		return recoverTime <= 0 && stamina >= attack.energy;
	}

	public void sendAttack(Fighter opponent, Attack attack) {
		opponent.health -= attack.damage;
	}
}
/*
 * ATTACK LOGIC: fighter attempts attack > attack takes time > end of attack
 * time, attack is sent to opponent, fighter losses stamina > opponent takes
 * damage > opponent's pending attack is cancelled > opponent recovers for
 * alloted time... and is susceptible to more attacks
 */

class Attack {
	public float damage;
	public float energy;
	public float animationTime;
	public float attackWindUp;
	public Type type;

	public enum Type {
		UL, UR, JL, JR, NILL,
	};

	public Attack(Type type) {
		this.type = type;
		switch (type) {
		case UL:
		case UR:
			damage = 10f;
			energy = 25f;

		case JL:
		case JR:
			damage = 5f;
			energy = 15f;
		default:
			damage = 0f;
			energy = 0f;
		}
	}
}

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
		punchDelay = 0f;
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
		punchDelay = 200f;
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
