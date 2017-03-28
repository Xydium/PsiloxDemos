package demo;

import org.lwjgl.opengl.GL11;

import psilox.graphics.Color;
import psilox.graphics.Draw;
import psilox.input.Input;
import psilox.math.Random;
import psilox.math.Vec;
import psilox.node.Node;

public class Particles extends Node {
	
	private Particle[] particles;
	
	public void enteredTree() {
		particles = new Particle[32];
	}
	
	public void update() {
		if(Input.buttonTap(Input.BUTTON_LEFT)) {
			for(int i = 0; i < particles.length; i++) {
				particles[i] = new Particle();
				particles[i].pos.set(Input.position);
				particles[i].vel = Vec.angMag((360.0f / 32 * i) + Random.floatVal(-5, 5), Random.intVal(10, 20));
			}
		}
	}
	
	public void render() {
		for(Particle p : particles) {
			if(p != null) p.render();
		}
	}
	
	private class Particle {
		public Vec pos = new Vec(0);
		public Vec vel = new Vec(0);
		
		public void render() {
			GL11.glPointSize(3);
			Draw.point(Color.WHITE, pos);
			GL11.glPointSize(1);
			pos.add(vel);
		}
	}
	
}
