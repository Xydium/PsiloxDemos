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
	private int targetPointer;
	private FloatPointer timeScale; 
	private FloatPointer gravScale;
	private FloatPointer[] pointers;
	private FloatPointer simTime;
	
	private Font basic;
	
	public void enteredTree() {
		particles = new ArrayList<Particle>();
		
		basic = new Font("Verdana", 0, 16);
		
		Label l = new Label(Color.CYAN, basic, "TimeScale: %.2f", timeScale = new FloatPointer(10));
		l.position.set(10, viewSize().y - 20, 1);
		addChild(l);
		
		l = new Label(Color.GREEN, basic, "GravScale: %.2f", gravScale = new FloatPointer(250));
		l.position.set(10, viewSize().y - 40, 1);
		addChild(l);
		
		l = new Label(Color.WHITE, basic, "Simulation Time: %.2f", simTime = new FloatPointer(0));
		l.position.set(viewSize().x / 2, viewSize().y - 20, 1);
		l.setAnchor(Anchor.MM);
		addChild(l);
		
		pointers = new FloatPointer[] {timeScale, gravScale};
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
		if(Input.keyDown(Input.LEFT)) {
			pointers[targetPointer].sub(0.5f);
		} else if(Input.keyDown(Input.RIGHT)) {
			pointers[targetPointer].add(0.5f);
		}
		if(Input.keyTap(Input.DOWN)) {
			targetPointer = (targetPointer + 1) % pointers.length;
			List<Label> labels = getChildren(Label.class);
			for(Label l : labels) {
				if(l.getColor() != Color.WHITE) {
					l.setColor(Color.GREEN);
				}
			}
			labels.get(targetPointer).setColor(Color.CYAN);
		}
		if(Input.keyTap(Input.SPACE)) {
			timeScale.set(0f);
		}
	}
	
	public void render() {
		Psilox.deltaTime *= timeScale.get();
		
		simTime.add(Psilox.deltaTime);
		
		Vec ip = viewSize().half();
		
		for(int i = 0; i < particles.size(); i++) {
			Particle p = particles.get(i);
			if(p != null) {
				p.render(ip);
			}
		}
		
		Draw.pushTransform(Mat4.translate(new Vec(0, 0, 1)));
		
		Draw.ellipsef(Color.GREEN, ip, 100, 30);
		Draw.ellipsef(Color.MAGENTA.aAdj(.25f), ip, 115, 30);
		
		Draw.popTransform();
		
		Psilox.deltaTime /= timeScale.get();
	}
	
	private class Particle {
		private static final float MAX_LIFE = 3600;
		private final Color DEAD_COLOR = Color.BLUE.aAdj(0);
		private final Color LIVE_COLOR = Color.WHITE.rAdj(.9f).gAdj(.9f);
		
		public Vec pos = new Vec(0);
		public Vec vel = new Vec(0);
		public float lifetime;
		
		public void render(Vec gravPoint) {
			if(lifetime >= 0) {
				Color c = mix(LIVE_COLOR, DEAD_COLOR, lifetime / MAX_LIFE);
				GL11.glLineWidth(3);
				Draw.line(c, pos, pos.dif(vel.scl(Psilox.deltaTime)));
				GL11.glLineWidth(1);
				GL11.glPointSize(5);
				Draw.point(c, pos);
				GL11.glPointSize(1);
			}
			
			Vec gravEffect = pos.dif(gravPoint);
			
			float r = Mathf.pow(gravEffect.mag(), 2);
			r = r < 1 ? 1 : r;
			gravEffect.mul(-gravScale.get() / r / r);
			vel.add(gravEffect.scl(Psilox.deltaTime));
							
			pos.add(vel.scl(Psilox.deltaTime));
			
			lifetime = Mathf.clm(lifetime + Psilox.deltaTime, -Integer.MAX_VALUE, MAX_LIFE);
		}
		
		private Color mix(Color a, Color b, float t) {
			return new Color((a.r * (1 - t)) + (b.r * t),
					(a.g * (1 - t)) + (b.g * t),
					(a.b * (1 - t)) + (b.b * t),
					(a.a * (1 - t)) + (b.a * t));
		}
		
	}
	
}
