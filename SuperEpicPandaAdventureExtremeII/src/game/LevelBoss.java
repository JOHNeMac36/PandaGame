package game;

import java.util.Random;

import org.newdawn.slick.Animation;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.Music;
import org.newdawn.slick.MusicListener;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.tiled.TiledMap;

public class LevelBoss extends BasicGameState {
	
	public static float zoomFactor = 3f;
	TiledMap map;
	Input input;
	boolean won;
	LittleMac panda;
	
	public LevelBoss(int i) {
	}
	
	@Override
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
		map = new TiledMap("res/maps/ring.tmx");
		input = Game.appgc.getInput();
		panda = new LittleMac(115f, 130f);
	}
	
	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
		
		renderRing(gc, sbg, g);
		renderPanda(gc, sbg, g);
		
		renderInfo(gc, sbg, g);
	}
	
	private void renderInfo(GameContainer gc, StateBasedGame sbg, Graphics g) {
		g.drawString("coolDown: " + panda.coolDown + " punchCoolDown: " + panda.punchCoolDown, 15, 0);
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
	
	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int t) throws SlickException {
		
		if (panda.isPunching) {
			input.clearKeyPressedRecord();
			panda.panda.update(t);
			panda.punchCoolDown -= t;
			panda.coolDown = LittleMac.punchDelay;
			if (panda.punchCoolDown <= 0) panda.isPunching = false;
		} else {
			panda.coolDown -= t;
			panda.panda = Game.lmStill;
			resetAnimations();
			// up
			panda.isUp = input.isKeyDown(Input.KEY_UP);
			
			// down
			panda.isDown = input.isKeyDown(Input.KEY_DOWN);
			
			// left
			panda.isLeft = input.isKeyDown(Input.KEY_LEFT);
			
			// right
			panda.isRight = input.isKeyDown(Input.KEY_RIGHT);
			
			// punchL
			if (input.isKeyPressed(Input.KEY_Z) && panda.coolDown <= 0 && !panda.isPunching && panda.punchCoolDown <= 0) {
				// input.clearKeyPressedRecord();
				panda.isPunching = true;
				panda.punchCoolDown = LittleMac.jabPunchTime;
				panda.coolDown = LittleMac.punchDelay;
				panda.panda = Game.lmJabL;
				if (panda.isUp) {
					panda.panda = Game.lmUpperCutL;
					panda.punchCoolDown = LittleMac.upperCutPunchTime;
				}
				
			} else
			// punchR
			if (input.isKeyPressed(Input.KEY_X) && panda.coolDown <= 0 && panda.punchCoolDown <= 0) {
				// input.clearKeyPressedRecord();
				panda.isPunching = true;
				panda.punchCoolDown = LittleMac.jabPunchTime;
				panda.coolDown = LittleMac.punchDelay;
				panda.panda = Game.lmJabR;
				if (panda.isUp) {
					panda.panda = Game.lmUpperCutR;
					panda.punchCoolDown = LittleMac.upperCutPunchTime;
				}
			}
		}
	}
	
	private void resetAnimations() {
		Game.lmJabL.restart();
		Game.lmJabR.restart();
		Game.lmUpperCutL.restart();
		Game.lmUpperCutR.restart();
		Game.lmDodgeL.restart();
		Game.lmDodgeR.restart();
		
	}
	
	@Override
	public int getID() {
		// TODO Auto-generated method stub
		return Game.lvlBoss;
	}
	
	public static void resetState() {
	
	}
	
}

class LittleMac {
	
	public float x, y, health;
	public Animation panda;
	public boolean isRight, isLeft, isDown, isUp, isPunching, isHit, isKnockedDown, isReviving;
	public static float punchDelay, jabPunchTime, upperCutPunchTime;
	public float coolDown = 10f, punchCoolDown = 0f;
	
	public LittleMac(float x, float y) {
		this.x = x;
		this.y = y;
		isRight = false;
		isLeft = false;
		isDown = false;
		isUp = false;
		isPunching = false;
		isHit = false;
		isKnockedDown = false;
		isReviving = false;
		panda = Game.lmStill;
		jabPunchTime = getDuration(Game.lmJabL);
		upperCutPunchTime = getDuration(Game.lmUpperCutL);
		punchDelay = 0f;
	}
	
	float getDuration(Animation a) {
		float duration = 0f;
		for (int i : a.getDurations()) {
			duration += i;
		}
		return duration;
	}
	
}