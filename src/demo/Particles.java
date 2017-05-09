package demo;

import java.awt.Font;
import java.util.ArrayList;
import java.util.List;

import org.lwjgl.opengl.GL11;

import psilox.core.Psilox;
import psilox.graphics.Color;
import psilox.graphics.Draw;
import psilox.input.Input;
import psilox.math.Mat4;
import psilox.math.Mathf;
import psilox.math.Random;
import psilox.math.Vec;
import psilox.node.Node;
import psilox.node.ui.Label;
import psilox.utils.Pointer.FloatPointer;

public class Particles extends Node {
	
	private List<Particle> particles;
	private FloatPointer timeScale; 
	
	public void enteredTree() {
		particles = new ArrayList<Particle>();
		printTree(Psilox.root);
		
		Label l = new Label(Color.GREEN, new Font("Verdana", 0, 16), "TimeScale: %.2f", timeScale = new FloatPointer(10));
		l.position.set(10, viewSize().y - 20, 1);
		addChild(l);
	}
	
	public void update() {
		if(Input.buttonTap(Input.BUTTON_LEFT)) {
			int c = Random.intVal(3, 20);
			for(int i = 0; i < c; i++) {
				Particle p = new Particle();
				p.pos.set(Input.position);
				p.vel = Vec.angMag(Random.floatVal(0, 360), Random.intVal(0, 30));
				particles.add(p);
			}
		}
		if(Input.keyDown(Input.LEFT) && timeScale.get() > 0) {
			timeScale.sub(0.5f);
		} else if(Input.keyDown(Input.RIGHT) && timeScale.get() < 100) {
			timeScale.add(0.5f);
		}
	}
	
	public void render() {
		Psilox.deltaTime *= timeScale.get();
		
		Vec ip = viewSize().half();
		
		for(int i = 0; i < particles.size(); i++) {
			Particle p = particles.get(i);
			if(p != null) {
				p.render(ip);
				if(p.dead) {
					particles.remove(i);
					i--;
				}
			}
		}
		
		Draw.pushTransform(Mat4.translate(new Vec(0, 0, 1)));
		
		Draw.ellipsef(Color.GREEN, ip, 100, 30);
		Draw.ellipsef(Color.MAGENTA.aAdj(.25f), ip, 115, 30);
		
		Draw.popTransform();
		
		Psilox.deltaTime /= timeScale.get();
	}
	
	private class Particle {
		private static final float MAX_LIFE = 360;
		private final Color DEAD_COLOR = Color.BLUE.aAdj(0);
		private final Color LIVE_COLOR = Color.WHITE.rAdj(.9f).gAdj(.9f);
		
		public Vec pos = new Vec(0);
		public Vec vel = new Vec(0);
		public boolean dead;
		public float lifetime;
		
		public void render(Vec gravPoint) {
			GL11.glLineWidth(3);
			Draw.line(mix(LIVE_COLOR, DEAD_COLOR, lifetime / MAX_LIFE), pos, pos.dif(vel.scl(Psilox.deltaTime)));
			GL11.glLineWidth(1);
			
			Vec gravEffect = pos.dif(gravPoint);
			if(gravEffect.mag() < 100) { dead = true; return; }
			else if(gravEffect.mag() < 115) vel.mul(.97f);
			float r = Mathf.pow(gravEffect.mag(), 2);
			gravEffect.mul(-250 / r);
			vel.add(gravEffect.scl(Psilox.deltaTime));
							
			lifetime = Mathf.clm(lifetime + Psilox.deltaTime, 0, MAX_LIFE);
			
			pos.add(vel.scl(Psilox.deltaTime));
			dead = lifetime > MAX_LIFE || !pos.btn(Vec.ZERO, new Vec(Psilox.config().width, Psilox.config().height));
		}
		
		private Color mix(Color a, Color b, float t) {
			return new Color((a.r * (1 - t)) + (b.r * t),
					(a.g * (1 - t)) + (b.g * t),
					(a.b * (1 - t)) + (b.b * t),
					(a.a * (1 - t)) + (b.a * t));
		}
		
	}
	
}
