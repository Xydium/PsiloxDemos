package asteroids;

import static psilox.math.Random.*;
import psilox.core.Psilox;
import psilox.graphics.Color;
import psilox.graphics.Draw;
import psilox.graphics.Shader;
import psilox.math.Vec;
import psilox.node.Node;

public class Asteroid extends Node {
	
	private static Shader distort;
	
	static {
		if(distort == null) {
			distort = new Shader("asteroids/asteroid.shd");
		}
	}
	
	public boolean small;
	private Vec velocity;
	private float spinRate;
	
	public void update() {
		position.add(velocity);
		rotation += spinRate;
		
		Vec viewSize = viewSize();
		position.x = (position.x < 0 ? viewSize.x - 1 : position.x) % viewSize.x;
		position.y = (position.y < 0 ? viewSize.y - 1 : position.y) % viewSize.y;
	}
	
	public void render() {
		distort.enable();
		distort.setUniform1f("time", Psilox.ticks() / 100f);
		Draw.ellipsef(Color.BROWN, Vec.ZERO, small ? 20 : 40, 10);
		distort.disable();
	}
	
	public void split() {
		if(!small) {
			Asteroid a = new Asteroid(), b = new Asteroid();
			a.position.set(position.sum(new Vec(10)));
			a.velocity = velocity.rot(45);
			a.spinRate = spinRate / 2;
			a.small = true;
			b.position.set(position.sum(new Vec(-10)));
			b.velocity = velocity.rot(-45);
			b.spinRate = spinRate / 3;
			b.small = true;
			getParent().addChildren(a, b);
		}
		freeSelf();
	}
	
	public static Asteroid spawn(Vec dim) {
		Asteroid a = new Asteroid();
		a.position.set(dim.pro(new Vec(floatVal(1), floatVal(1))));
		a.velocity = new Vec(floatVal(-5, 5), floatVal(-5, 5));
		a.spinRate = floatVal(-5, 5);
		return a;
	}
	
}
