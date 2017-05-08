package demo;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.opengl.GL11;

import psilox.core.Psilox;
import psilox.graphics.Color;
import psilox.graphics.Draw;
import psilox.input.Input;
import psilox.math.Mathf;
import psilox.math.Random;
import psilox.math.Vec;
import psilox.node.Node;

public class Particles extends Node {
	
	private List<Particle> particles;
	
	public void enteredTree() {
		particles = new ArrayList<Particle>();
	}
	
	public void update() {
		if(Random.intVal(1000) < 30) {
			int c = Random.intVal(3, 20);
			Vec pos = new Vec(Random.floatVal(1) * viewSize().x, Random.floatVal(1) * viewSize().y);
			for(int i = 0; i < c; i++) {
				Particle p = new Particle();
				p.pos.set(pos);
				p.vel = Vec.angMag(Random.floatVal(0, 360), Random.intVal(0, 30));
				particles.add(p);
			}
		}
	}
	
	public void render() {
		for(int i = 0; i < particles.size(); i++) {
			Particle p = particles.get(i);
			if(p != null) {
				p.render(Input.position);
				if(p.dead) {
					particles.remove(i);
					i--;
				}
			}
		}
	}
	
	private class Particle {
		private static final float MAX_LIFE = 3;
		private final Color DEAD_COLOR = Color.ORANGE.aAdj(0);
		private final Color LIVE_COLOR = Color.WHITE.rAdj(.9f).gAdj(.9f);
		
		public Vec pos = new Vec(0);
		public Vec vel = new Vec(0);
		public boolean dead;
		public float lifetime;
		
		public void render(Vec gravPoint) {
			GL11.glLineWidth(3);
			Draw.line(mix(LIVE_COLOR, DEAD_COLOR, lifetime / MAX_LIFE), pos, pos.dif(vel));
			GL11.glLineWidth(1);
			
			lifetime = Mathf.clm(lifetime + Psilox.deltaTime, 0, MAX_LIFE);
			//vel.y -= 9.8f * Psilox.deltaTime * 5;
			
			Vec gravEffect = pos.dif(gravPoint);
			gravEffect.mul(-1 / gravEffect.mag());
			
			vel.add(gravEffect);
			
			pos.add(vel);
			dead = pos.y < 0 || lifetime > MAX_LIFE;
		}
		
		private Color mix(Color a, Color b, float t) {
			return new Color((a.r * (1 - t)) + (b.r * t),
					(a.g * (1 - t)) + (b.g * t),
					(a.b * (1 - t)) + (b.b * t),
					(a.a * (1 - t)) + (b.a * t));
		}
		
	}
	
}
